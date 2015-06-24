package com.wonders.contact.processController.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.contact.approvedInfo.model.bo.TApprovedinfo;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.service.CommonService;
import com.wonders.contact.common.util.CommonUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.constant.DeptContactFlowConstants;
import com.wonders.contact.deptContact.dao.DeptContactDao;
import com.wonders.contact.deptContact.model.bo.TDeptContactConfig;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.bo.TDeptContactReference;
import com.wonders.contact.deptContact.model.bo.TDeptContactTree;
import com.wonders.contact.deptContact.model.vo.DeptContactOperateVo;
import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;
import com.wonders.contact.deptContact.service.DeptContactCommonService;
import com.wonders.contact.deptContact.util.DeptContactUtil;
import com.wonders.contact.deptContact.util.FlowUtil;
import com.wonders.contact.processController.adapter.DeptContactAdapter;
import com.wonders.contact.processController.model.bo.DeptContactResult;
import com.wonders.contact.processController.model.vo.DeptContactControllerVo;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

/**多级工作联系单流程控制器
 * @author XFZ
 * @version 1.0 2012-6-15
 */
@Service("contact-deptContactController")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DeptContactController{
	private CommonService commonService;
	private DeptContactAdapter deptContactAdapter;
	private DeptContactDao deptContactDao;
	private DeptContactCommonService deptContactCommonService;
	private Logger log = SimpleLogger.getLogger(this.getClass());
	
	private String time = DateUtil.getNowTime();

	
	/**统计多级工作联系单数量状态
	 * @param vo
	 * @return
	 */
	public DeptContactResult getCount(DeptContactControllerVo vo){
		DeptContactResult result = new DeptContactResult();
		
		String[] types = {DeptContactConstants.STATUS_SUB_STR,DeptContactConstants.STATUS_LOWER_STR};//需要计数的流程类型
		
		Map<String,Integer> map = deptContactAdapter.getCount(vo,types);
		
		int countSub = map.get(DeptContactConstants.STATUS_SUB_STR);
		int countLower = map.get(DeptContactConstants.STATUS_LOWER_STR);
		
		result.flag = true;
		result.sub.count = countSub;
		result.lower.count = countLower;
		
		return result;
	}
	
	public DeptContactResult getInitProcess(DeptContactControllerVo vo){
//log.debug("getInitProcess");
		String key = StringUtil.getNotNullValueString(vo.getKey());
		
		DeptContactUtil.checkKey(key);
		
		TDeptContactMain mainBo = deptContactDao.getSelfMainBoByKey(key);
		
		DeptContactUtil.checkMainBo(mainBo);
		
		TDeptContactTree treeBo = deptContactDao.getTreeBo(key);
		
//log.debug("deptContactAdapter="+deptContactAdapter);
		
		
		DeptContactResult result = deptContactAdapter.getInitProcess(vo, mainBo, treeBo);
		
		if(result.lower.count==0){
			log.warn("无下级流程！");
		}
		if(result.sub.count==0){
			log.warn("无子流程！");
		}
		
		result.flag = true;
		
		return result;
	}
	
	
	public DeptContactResult getInitProcess(String mainBoId){
//log.debug("getInitProcessCheck");
		
		TDeptContactMain mainBo = deptContactDao.getMainBo(mainBoId);
		
		DeptContactUtil.checkMainBo(mainBo);
		
		TDeptContactTree treeBo = deptContactDao.getTreeBoByMainBoId(mainBoId);
		
		DeptContactResult result = deptContactAdapter.getInitProcess(null, mainBo, treeBo);
		
		if(result.lower.count==0){
			log.warn("无下级流程！");
		}
		if(result.sub.count==0){
			log.warn("无子流程！");
		}
		//result.flag = true;
		return result;
	}
	
	
	/**发起下级流程操作
	 * @param vo
	 * @return
	 */
	public DeptContactResult launchLowerProcess(DeptContactControllerVo vo){
		DeptContactResult result = new DeptContactResult();
		
		String key = StringUtil.getNotNullValueString(vo.getKey());
		
		DeptContactUtil.checkKey(key);
		
		TDeptContactMain mainBo = deptContactDao.getSelfMainBoByKey(key);
		
		DeptContactUtil.checkMainBo(mainBo);
		
		
		//TDeptContactTree parentTreeBo = deptContactDao.getTreeBoByMainBoId(parentMainBo.getId());
		TDeptContactTree parentTreeBo = deptContactDao.getTreeBo(key);
		
		String deptId = vo.getDeptId();
		TDeptContactTree treeBo = deptContactAdapter.addTreeBo(vo,deptId);
		
		TDeptContactConfig config = deptContactAdapter.getConfigInfo(DeptContactConstants.PROCESSNAME,DeptContactFlowConstants.STEPNAME_LOWER,deptId);
		
		String summary = FlowUtil.getSummaryByProcessInfo(mainBo.getProcessname(), mainBo.getIncidentno());
		
		//String summary = mainBo.getTheme()+"("+config.getDeptname()+"-下级流程)";
		String incident = deptContactAdapter.launchLowerProcess(vo,config,summary,treeBo);
		
		
		TDeptContactMain cBo = deptContactAdapter.copyFromMainBo(mainBo);
		cBo.setProcessname(DeptContactConstants.PROCESSNAME);
		cBo.setIncidentno(incident);
		cBo.setSerial(null);
		
		cBo.setTheme(summary + "("+config.getDeptname()+"-下级流程)");
		
		cBo.setCreateDeptid(config.getDeptid());
		cBo.setCreateDeptname(config.getDeptname());
		
		cBo.setInitiator(config.getInitiator());
		cBo.setInitiatorName(config.getInitiatorName());
		
		deptContactAdapter.saveLowerProcessBo(cBo,parentTreeBo.getDeptids());
		
		//增加相关资料关联
		String refIds = deptContactCommonService.getReferenceIds(mainBo.getId());
		List<String> refList = new ArrayList<String>();
		refList = CommonUtil.stringsToList(refIds);
		for(int i=0;i<refList.size();i++){
			String refId = StringUtil.getNotNullValueString(refList.get(i));
			if(refId.length()>0){
				TDeptContactReference refBo = new TDeptContactReference(); 	
				refBo.setMainId(cBo.getId());
				refBo.setRefId(refId);
				refBo.setOrders(Long.valueOf(i));		
				refBo.setOperateDate(cBo.getOperateDate());
				refBo.setOperateUser(cBo.getOperateUser());
				refBo.setOperateName(cBo.getOperateName());
				commonService.save(refBo);
			}
		}
		
		/**更新关联信息*/
		DeptContactUtil.copyOperateInfo(treeBo, cBo);
		treeBo.setCId(cBo.getId());
		treeBo.setCincident(incident);
		treeBo.setRemoved(0);
		commonService.update(treeBo);
		
		
		/**添加意见*/
		UserInfo userInfo = new UserInfo();
		userInfo.setDeptId(cBo.getCreateDeptid());
		userInfo.setDeptName(cBo.getCreateDeptname());
		userInfo.setLoginName(cBo.getInitiator());
		userInfo.setUserName(cBo.getInitiatorName());
		
		
		DeptContactParamVo params = new DeptContactParamVo();
		params.addProcessParam("cname", cBo.getProcessname());
		params.addProcessParam("cincident", cBo.getIncidentno());
		params.setUserInfo(userInfo);
		DeptContactOperateVo operateVo = new DeptContactOperateVo();
		operateVo.setFlowType(DeptContactConstants.STATUS_LOWER_STR);
		operateVo.setSteplabel("Begin");
		params.operateVo = operateVo;
		
		TApprovedinfo tai = deptContactAdapter.generateLowerProcessApprovedInfo(params);
		deptContactAdapter.saveLowerProcessApprovedInfo(tai);
		
		return result;
	}
	
	/**下级流程结束，激活上级节点
	 * @param vo
	 * @return
	 */
	public DeptContactResult endLowerProcess(DeptContactControllerVo vo){
		DeptContactResult result = new DeptContactResult();
		
		String key = StringUtil.getNotNullValueString(vo.getKey());
		
		DeptContactUtil.checkKey(key);
		
		TDeptContactTree treeBo = deptContactDao.getTreeBo(key);
		TDeptContactMain mainBo = deptContactDao.getMainBo(treeBo.getCId());
		TDeptContactMain parentMainBo = deptContactDao.getMainBo(treeBo.getPId());
		
		if(mainBo==null){
			log.warn("找不到符合的流程信息！key="+key);
			return null;
		}
		
		TDeptContactConfig config = deptContactAdapter.getConfigInfo(
				DeptContactConstants.PROCESSNAME,DeptContactFlowConstants.STEPNAME_LOWER,treeBo.getMainUnitId());
		
		boolean ret = deptContactAdapter.endLowerProcess(vo,treeBo,config,parentMainBo.getTheme());

		mainBo.setUpdateTime(time);
		commonService.update(mainBo);
		
	
		result.flag = ret;
		return result;
	}
	
	public DeptContactResult linkSubProcess(DeptContactControllerVo vo){
		
		DeptContactResult result = new DeptContactResult();
		
		String pid = FlowUtil.getMainBoIdBySubProcessInfo(vo.getProcessname(),vo.getIncident());
		
		TDeptContactMain mainBo = deptContactDao.getMainBo(pid);
		
		DeptContactUtil.checkMainBo(mainBo);
		
		TDeptContactTree treeBo = DeptContactUtil.generateTreeBoByParentMainBo(mainBo);
		
		String ret = deptContactAdapter.linkSubProcess(vo, treeBo);
		
		result.flag = true;
		result.key = ret;
		
		return result;
	}
	
	public DeptContactResult finishProcess(DeptContactControllerVo vo){
		DeptContactResult result = new DeptContactResult();
		
		String key = StringUtil.getNotNullValueString(vo.getKey());
		
		DeptContactUtil.checkKey(key);
		
		TDeptContactTree treeBo = deptContactDao.getTreeBo(key);
		
		boolean ret = deptContactAdapter.finishProcess(treeBo);
		
		result.flag = ret;
		
		return result;
	}

	
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired
	public void setCommonService(@Qualifier(value="contact-commonService")CommonService commonService) {
		this.commonService = commonService;
	}
		
	public DeptContactAdapter getDeptContactAdapter() {
		return deptContactAdapter;
	}

	@Autowired
	public void setDeptContactAdapter(@Qualifier(value="contact-deptContactAdapter")DeptContactAdapter deptContactAdapter) {
		this.deptContactAdapter = deptContactAdapter;
	}

	public DeptContactDao getDeptContactDao() {
		return deptContactDao;
	}
	
	@Autowired
	public void setDeptContactDao(@Qualifier(value="contact-deptContactDao")DeptContactDao deptContactDao) {
		this.deptContactDao = deptContactDao;
	}
	
	public DeptContactCommonService getDeptContactCommonService() {
		return deptContactCommonService;
	}
	
	@Autowired
	public void setDeptContactCommonService(@Qualifier("contact-deptContactCommonService")DeptContactCommonService deptContactCommonService) {
		this.deptContactCommonService = deptContactCommonService;
	}
}
