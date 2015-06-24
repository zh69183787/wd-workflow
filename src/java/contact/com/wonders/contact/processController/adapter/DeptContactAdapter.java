package com.wonders.contact.processController.adapter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.wonders.contact.approvedInfo.model.bo.TApprovedinfo;
import com.wonders.contact.common.exception.ProcessException;
import com.wonders.contact.common.service.CommonService;
import com.wonders.contact.common.util.CommonUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.constant.CommonConstants;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.constant.DeptContactFlowConstants;
import com.wonders.contact.deptContact.dao.DeptContactDao;
import com.wonders.contact.deptContact.model.bo.TDeptContactConfig;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.bo.TDeptContactTree;
import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;
import com.wonders.contact.deptContact.service.DeptContactCommonService;
import com.wonders.contact.deptContact.service.DeptContactTreeService;
import com.wonders.contact.deptContact.util.DeptContactUtil;
import com.wonders.contact.deptContact.util.FlowUtil;
import com.wonders.contact.deptSubProcess.constant.DeptSubProcessFlowConstants;
import com.wonders.contact.external.service.ExternalService;
import com.wonders.contact.model.bo.MessageBo;
import com.wonders.contact.organTree.model.bo.OrganDeptBo;
import com.wonders.contact.processController.constant.ProcessControllerConstants;
import com.wonders.contact.processController.model.bo.DeptContactResult;
import com.wonders.contact.processController.model.vo.ControllerVo;
import com.wonders.contact.processController.model.vo.DeptContactControllerVo;
import com.wonders.contact.tree.model.bo.TreeNode;
import com.wonders.contact.tree.model.vo.TreeModel;
import com.wonders.contact.tree.util.TreeUtil;
import com.wonders.util.DateUtil;
import com.wonders.util.IpUtil;
import com.wonders.util.PWSUtil;
import com.wonders.util.StringUtil;

@Service("contact-deptContactAdapter")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DeptContactAdapter {
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	private CommonService commonService;
	private DeptContactCommonService deptContactCommonService;
	private DeptContactTreeService deptContactTreeService;
	private ExternalService externalService;
	
	private DeptContactDao deptContactDao;
	
	private Gson gson = new Gson();
	
	private String time = DateUtil.getNowTime();
	
	
	/**多级工作联系单 子流程、下级流程 初始化信息
	 * @param vo
	 * @param mainBo
	 * @param treeBo
	 * @return
	 */
	public DeptContactResult getInitProcess(DeptContactControllerVo vo,TDeptContactMain mainBo,TDeptContactTree treeBo){
		DeptContactResult result = new DeptContactResult();
		
		String treeBoMainUnitId = StringUtil.getNotNullValueString(treeBo.getMainUnitId());
		String mainUnitId = mainBo.getMainUnitId();
		
		if(mainUnitId==null||mainUnitId.length()==0){
			return result;
		}
		
		
		TreeNode nodeMain = null;
		List<String> stopNodes = new ArrayList<String>();
		try{
			nodeMain = TreeUtil.getTreeBo(externalService.getNodesInfo(mainBo.getCreateDeptid()).get(0));
			if(nodeMain!=null){
				List<OrganDeptBo> list = externalService.getRelatedNodes(mainBo.getCreateDeptid());
				for(int i=0;i<list.size();i++){
//log.debug("stopNodes add:"+list.get(i).id);
					stopNodes.add(list.get(i).id);
				}
			}
			deptContactTreeService.setStopNodes(stopNodes);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		TreeModel treeModel = TreeUtil.generateTree(externalService.getRelatedNodes(mainUnitId));
//log.debug("--original tree--");
//TreeUtil.showTree(treeModel);
//log.debug("--original tree--");
		if(treeBoMainUnitId.length()>0){
			treeModel = TreeUtil.getSubTreeByRootNode(treeModel.getTreeNode(treeBoMainUnitId));
		}
		deptContactTreeService.setTreeModel(treeModel);
//TreeUtil.showTree(treeModel);
		
		//获得下级流程相关信息
		List<TDeptContactConfig> configInfoList = deptContactCommonService.getDeptContactConfigInfo(DeptContactConstants.PROCESSNAME, DeptContactFlowConstants.STEPNAME_LOWER);
		
		List<String> configIdList = new ArrayList<String>();
		Map<String,TDeptContactConfig> configMap = new HashMap<String,TDeptContactConfig>();
		
		//初始化下级流程信息集合
		for(int i=0;i<configInfoList.size();i++){
			TDeptContactConfig c = configInfoList.get(i);
			configMap.put(c.getDeptid(),c);
			configIdList.add(c.getDeptid());
		}
		
//log.debug("configMap.size()="+configMap.size());
		
		
		//标记下级流程
		deptContactTreeService.setMarkNodeId(configIdList);
		//boolean hasLowerFlag = 
		deptContactTreeService.markNodeOnTreeModel();
//log.debug("--marked tree--");
//TreeUtil.showTree(treeModel);
//log.debug("--marked tree--");
		deptContactTreeService.setMainUnitId(mainUnitId);
		deptContactTreeService.setRootId(treeBo.getMainUnitId());
		List<TreeNode> resultList = deptContactTreeService.getResultNodes();
		
		Map<String,List<String>> lowerProcessInfoMap = new HashMap<String,List<String>>();//下级流程默认主送部门信息
		
		//越级发送部门名单
		List<String> levelErrorList = new ArrayList<String>();
		
		for(int i=0;i<resultList.size();i++){
			TreeNode node = resultList.get(i);
			String id = node.id;
			Boolean flag = node.marked;
			
//log.debug(node);
			
			if(flag!=null&&flag){
				List<String> list = null;
				if(lowerProcessInfoMap.containsKey(id)){
					list =lowerProcessInfoMap.get(id);
				}else{
					list = new ArrayList<String>();
					lowerProcessInfoMap.put(id, list);
				}
				
				List<TreeNode> includeNodes = node.getParamByName(DeptContactConstants.TREE_PARAMS_INCLUDE_NODE);
				if(includeNodes!=null){
					for(int j=0;j<includeNodes.size();j++){
						list.add(includeNodes.get(j).id);
					}
				}
				
				result.lower.deptId.add(node.id);
				result.lower.deptName.add(node.name);
				result.lower.count++;
			}else{
				if(nodeMain!=null){
					int nodeLevel = nodeMain.level;
log.debug("level compare: nodeMain"+nodeMain.level+" current:"+node.level);
					if(node.level>=nodeLevel){
log.debug("add sub:"+node.id+" "+node.name);
						result.sub.deptId.add(node.id);
						result.sub.deptName.add(node.name);
						result.sub.count++;
					}else{
						levelErrorList.add(node.name);
					}
					
				}else{
					result.sub.deptId.add(node.id);
					result.sub.deptName.add(node.name);
					result.sub.count++;
				}
			}
		}
log.debug(result.lower.count+" "+result.sub.count);
		
		if(levelErrorList.size()>0){
			result.resultInfo.addErrors(new MessageBo("主送部门（"+CommonUtil.listToStringBySplit(levelErrorList)+"）层级高于发起部门！","mainUnit",CommonConstants.MESSAGE_ERROR_PROCESS));
		}
		
		String deptids = gson.toJson(lowerProcessInfoMap);
		treeBo.setDeptids(deptids);
		commonService.update(treeBo);
		
//System.out.println(new Gson().toJson(lowerProcessInfoMap));
		
		for(int i=0;i<result.lower.deptId.size();i++){
			String deptId = result.lower.deptId.get(i);
			String lowerReceiver = configMap.get(deptId).getInitiator();
			result.lower.deptUser.add(lowerReceiver);
		}
		
//log.debug(bo.DeptIdSub.size());
//log.debug(externalService);
		
		Map<String, String> subReceiverMap = externalService.getDeptReceiversMap(mainBo.getProcessname(),DeptSubProcessFlowConstants.STEPNAME_DISPATCHER,result.sub.deptId);
		for(int i=0;i<result.sub.deptId.size();i++){
			String subReciever = StringUtil.getNotNullValueString(subReceiverMap.get(result.sub.deptId.get(i)));
			result.sub.deptUser.add(subReciever);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	/**添加关联表BO
	 * @param vo
	 * @param deptId
	 * @return
	 */
	public TDeptContactTree addTreeBo(ControllerVo vo,String deptId){
		TDeptContactTree parentTreeBo = (TDeptContactTree)commonService.load(vo.getKey(), TDeptContactTree.class);
		
		TDeptContactTree treeBo = DeptContactUtil.generateTreeBoByParentTreeBo(parentTreeBo);
		treeBo.setType(DeptContactConstants.STATUS_LOWER);
		treeBo.setMainUnitId(deptId);
		commonService.save(treeBo);
		
		return treeBo;
	}
	
	/**获得下级流程配置信息
	 * @param deptId
	 * @return
	 */
	public TDeptContactConfig getConfigInfo(String processname,String stepname,String deptId){
		//获得下级流程相关信息
		List<TDeptContactConfig> configInfoList = deptContactCommonService.getDeptContactConfigInfo(processname,stepname);
		
		Map<String,TDeptContactConfig> configMap = new HashMap<String,TDeptContactConfig>();
		
		//初始化下级流程信息集合
		for(int i=0;i<configInfoList.size();i++){
			TDeptContactConfig c = configInfoList.get(i);
			configMap.put(c.getDeptid(),c);
		}
		
		TDeptContactConfig config = configMap.get(deptId);
		
		return config;
	}
	
	/**发起下级流程
	 * @param vo
	 * @param config
	 * @param summary
	 * @param deptId
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String launchLowerProcess(ControllerVo vo,TDeptContactConfig config,String summary,TDeptContactTree treeBo){
		//String deptId = vo.getDeptId();
		//发起下级流程
		/*初始化关联信息*/
		
		String operator = config.getInitiator();
		//String operatorName = config.getInitiatorName();
		//String deptName = config.getDeptname();
		String receiver = config.getReceiver();
		
		Map tmpMap = new HashMap();
		
		String ip = IpUtil.getIpByStartString(DeptContactConstants.IP_PREFIX);
		String port = StringUtil.getNotNullValueString(vo.getPort());
		String contextPath = StringUtil.getNotNullValueString(vo.getContextPath());
		
		tmpMap.put(DeptContactFlowConstants.SERVERIP, "http://"+ip+":"+port+"/"+contextPath);
		
		//tmpMap.put(DeptContactFlowConstants.SERVERIP, "http://"+IpUtil.getIpByStartString(DeptContactConstants.IP_PREFIX));
		
		tmpMap.put(DeptContactFlowConstants.USER_BEGIN, operator);
		tmpMap.put(DeptContactFlowConstants.USER_APPLY, receiver);
		
		tmpMap.put(DeptContactFlowConstants.TARGET, DeptContactFlowConstants.STEP_APPLY);
		
		tmpMap.put(DeptContactFlowConstants.DEPTID, treeBo.getMainUnitId());
		
		tmpMap.put(DeptContactFlowConstants.LINK_PROCESS_TYPE, DeptContactConstants.STATUS_LOWER);
		tmpMap.put(DeptContactFlowConstants.NEED_LINK, "1");
		
		tmpMap.put(DeptContactFlowConstants.LINK_PROCESS_KEY, treeBo.getId());
		
		//String summary = mainBo.getTheme()+"("+deptName+"-下级流程)";
		
log.debug("开始发起下级流程");
		/**/
		int lowerInciNo = PWSUtil.launchIncident(DeptContactConstants.PROCESSNAME, operator, summary, tmpMap);

		if(lowerInciNo<0){
			throw new RuntimeException("下级流程发起失败！");
		}else{
log.debug("发起下级流程结束，实例号："+lowerInciNo);
		}
		
		return String.valueOf(lowerInciNo);
	}
	
	/**从父mainBO获取基本信息
	 * @param mainBo
	 * @return
	 */
	public TDeptContactMain copyFromMainBo(TDeptContactMain mainBo){
		TDeptContactMain cBo = new TDeptContactMain();
		try {
//log.debug("copy from mainBo");
			BeanUtils.copyProperties(cBo, mainBo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return cBo;
	}
	
	/**保存下级流程mainBo信息
	 * @param cBo
	 * @param config
	 * @param incident
	 * @param defaultMainUnitId 默认选中部门(JSON格式)
	 * @param summary
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void saveLowerProcessBo(TDeptContactMain cBo,String defaultMainUnitId){
		if(cBo==null){
			//throw new 
			return;
		}
		
//log.debug("defaultMainUnitId="+defaultMainUnitId);
//log.debug("deptId="+cBo.getCreateDeptid());
		
		cBo.setId(null);
		cBo.setStartTime(time);
		
		cBo.setMainUnitId("");
		cBo.setMainUnit("");
		
		//默认主送部门
		String deptids = StringUtil.getNotNullValueString(defaultMainUnitId);
		if(deptids.length()>0){
			Map<String,List<String>> lowerProcessInfoMap = new HashMap<String,List<String>>();
			lowerProcessInfoMap = gson.fromJson(deptids, lowerProcessInfoMap.getClass());//下级流程默认主送部门信息
//log.debug(lowerProcessInfoMap.size());
			List<String> lowerDeptIdList = lowerProcessInfoMap.get(cBo.getCreateDeptid());
			
			if(lowerDeptIdList!=null&&lowerDeptIdList.size()>0){
//log.debug(lowerDeptIdList.size());
				List<OrganDeptBo> deptNameList = externalService.getNodesInfo(lowerDeptIdList);
				
				Map<String,String> nameMap = new HashMap<String,String>();
				for(int i=0;i<deptNameList.size();i++){
					OrganDeptBo bo = deptNameList.get(i);
					nameMap.put(StringUtil.getNotNullValueString(bo.id), bo.name);
				}
//log.debug("nameMap.size()="+nameMap.size());
				List<String> lowerDeptNameList = CommonUtil.translateListByMap(lowerDeptIdList, nameMap);
				
				String lower_mainUnitId = CommonUtil.listToStringBySplit(lowerDeptIdList);
				String lower_mainUnitName = CommonUtil.listToStringBySplit(lowerDeptNameList);
				
log.debug("默认主送部门："+lower_mainUnitId+" "+lower_mainUnitName);
				cBo.setMainUnitId(lower_mainUnitId);
				cBo.setMainUnit(lower_mainUnitName);
			}
		}
					
		cBo.setCopyUnitId("");
		cBo.setCopyUnit("");
		
		
		DeptContactUtil.setOperateInfo(cBo, cBo.getInitiator(), cBo.getInitiatorName(), time);
		
		commonService.save(cBo);
	}
	
	
	

	
	/**统计当前流程数量状态
	 * @param vo
	 * @param types 需要统计的类型
	 * @return
	 */
	public Map<String,Integer> getCount(ControllerVo vo,String[] types){
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		
		/*初始化map*/
		for(int i=0;i<types.length;i++){
			map.put(types[i], 0);
		}
		
		List<Map<String,Object>> list = FlowUtil.getStatusInfoByProcess(vo.getKey(), "");
		
		if(list.size()==0){
			return null;
		}
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> values = list.get(i);

			String type = StringUtil.getNotNullValueString(values.get("type"));
			String status = StringUtil.getNotNullValueString(values.get("status"));
			
			if(CommonUtil.targetIsInArray(type, types)){
				if(String.valueOf(ProcessControllerConstants.STATUS_ACTIVE).equals(status)){
					map.put(type, map.get(type)+1);							
				}
			}else{
				//log.warn("未知流程类型,type="+type);
			}
		}
		
		return map;
	}
	
	
	
	/**结束下级流程，激活上级节点
	 * @param vo
	 * @param treeBo
	 * @param config
	 * @param summary
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean endLowerProcess(ControllerVo vo,TDeptContactTree treeBo,TDeptContactConfig config,String summary){
		String operator = config.getInitiator();
		
		Map map = new HashMap();

//log.debug("流程处理人："+operator);
		
		log.debug("开始激活上级流程");
		
		boolean flag = PWSUtil.completeStepTest(treeBo.getPname(), operator, Integer.parseInt(treeBo.getPincident()), DeptContactFlowConstants.STEPNAME_LOWER, summary,"", map);
		if(flag){
			log.debug("激活上级流程："+flag);
		}else{
			throw new RuntimeException("上级流程激活失败！");
		}
		
		return flag;
	}
	
	//public void saveLowerProcessApproveInfo	
	
	/**建立子流程关联
	 * @param vo
	 * @param treeBo
	 * @return
	 */
	public String linkSubProcess(DeptContactControllerVo vo,TDeptContactTree treeBo){
		try{
			treeBo.setCname(vo.getProcessname());
			treeBo.setCincident(vo.getIncident());
			
			treeBo.setType(Integer.parseInt(vo.getSubType()));
			treeBo.setStatus(ProcessControllerConstants.STATUS_ACTIVE);
			treeBo.setMainUnitId(vo.getDeptId());
			
			//String time = DateUtil.getNowTime();
			
			treeBo.setOperateDate(time);
			
			commonService.save(treeBo);
			
//log.debug("添加子流程关联成功id="+treeBo.getId());
		}catch(Exception e){
//e.printStackTrace();
			throw new ProcessException("添加下级流程关联失败！");
			//log.warn("添加下级流程关联失败：");
		}
		
		return treeBo.getId();
	}

	/**设置结束status
	 * @param treeBo
	 * @return
	 */
	public boolean finishProcess(TDeptContactTree treeBo){
		
		treeBo.setStatus(ProcessControllerConstants.STATUS_FINISH);
		treeBo.setOperateDate(time);
		
		commonService.update(treeBo);
		
		//log.debug("流程关联表status设置成功：id="+treeBo.getId());	
		
		return true;
	}
	
	
	/**生成下级流程意见
	 * @param cBo
	 */
	public TApprovedinfo generateLowerProcessApprovedInfo(DeptContactParamVo params){
		return deptContactCommonService.generateApprovedInfo(params);
	}
	
	/**保存下级流程意见
	 * @param cBo
	 */
	public void saveLowerProcessApprovedInfo(TApprovedinfo tai){
		commonService.save(tai);
	}
	
	
	
	/**获得下级流程最终意见
	 * @param vo
	 * @return
	@SuppressWarnings("unchecked")
	public List<TApprovedinfo> getLowerResultApprovedInfo(String mainBoId,String filterWhere){
		
		String hql = "select t from TApprovedinfo t,TDeptContactTree tree " +
					" where t.status=1 and tree.removed!=1" +
					" and tree.PId = ?" +
					" and ((t.process = tree.pname and t.incidentno = tree.pincident)" +
					" or" +
					" (t.process = tree.cname and t.incidentno = tree.cincident))" +
					filterWhere +
					"";
		
		List<TApprovedinfo> list = commonService.ListByHql(hql, new Object[]{mainBoId});
		
		
		return list;
	}
	*/
	
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired
	public void setCommonService(@Qualifier(value="contact-commonService")CommonService commonService) {
		this.commonService = commonService;
	}
	
	public DeptContactCommonService getDeptContactCommonService() {
		return deptContactCommonService;
	}
	
	@Autowired
	public void setDeptContactCommonService(@Qualifier("contact-deptContactCommonService")DeptContactCommonService deptContactCommonService) {
		this.deptContactCommonService = deptContactCommonService;
	}

	public DeptContactTreeService getDeptContactTreeService() {
		return deptContactTreeService;
	}
	
	@Autowired
	public void setDeptContactTreeService(@Qualifier("contact-deptContactTreeService")DeptContactTreeService deptContactTreeService) {
		this.deptContactTreeService = deptContactTreeService;
	}
	
	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired
	public void setExternalService(@Qualifier("contact-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}
	
	public DeptContactDao getDeptContactDao() {
		return deptContactDao;
	}
	
	@Autowired
	public void setDeptContactDao(@Qualifier(value="contact-deptContactDao")DeptContactDao deptContactDao) {
		this.deptContactDao = deptContactDao;
	}
}
