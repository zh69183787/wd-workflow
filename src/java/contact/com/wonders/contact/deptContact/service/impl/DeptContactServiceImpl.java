package com.wonders.contact.deptContact.service.impl;

import java.lang.reflect.InvocationTargetException;
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

import com.wonders.contact.common.model.bo.ResultInfo;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.service.CommonService;
import com.wonders.contact.common.util.CommonUtil;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.constant.DeptContactFlowConstants;
import com.wonders.contact.deptContact.constant.DeptContactMessage;
import com.wonders.contact.deptContact.dao.DeptContactDao;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.bo.TDeptContactTree;
import com.wonders.contact.deptContact.model.vo.DeptContactMainVo;
import com.wonders.contact.deptContact.model.vo.DeptContactOperateVo;
import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;
import com.wonders.contact.deptContact.service.DeptContactCommonService;
import com.wonders.contact.deptContact.service.DeptContactService;
import com.wonders.contact.deptContact.util.DeptContactUtil;
import com.wonders.contact.deptContact.util.FlowUtil;
import com.wonders.contact.deptContact.util.TextUtil;
import com.wonders.contact.todo.constant.TodoConstants;
import com.wonders.contact.todo.model.bo.TodoItem;
import com.wonders.util.DateUtil;
import com.wonders.util.DbUtil;
import com.wonders.util.IpUtil;
import com.wonders.util.PWSUtil;
import com.wonders.util.StringUtil;

@Service("contact-deptContactService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DeptContactServiceImpl implements DeptContactService {
	
	
	private DeptContactCommonService deptContactCommonService;
	private CommonService commonService;
	private DeptContactDao deptContactDao;
	
	private DbUtil dbUtil;
	
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	private String time = DateUtil.getNowTime();
	
	private DeptContactParamVo params;
	
	private ResultInfo resultInfo;
	private TDeptContactMain mainBo;
	private UserInfo userInfo;
	private DeptContactMainVo mainVo;
	private DeptContactOperateVo operateVo;
	
	@Override
	public void init(DeptContactParamVo params){
		this.params = params;
		this.userInfo = params.userInfo;
		this.mainVo = params.mainVo;
		this.operateVo = params.operateVo;
		this.resultInfo = params.resultInfo;
	}
	
	
	/**forward 业务逻辑
	 * @param list
	 * @param params
	 * @return
	 */
	private DeptContactMainVo forward(List<Map<String, Object>> list, DeptContactParamVo params) {
		if (list.size() > 0) {
			String id = "";
			Map<String, Object> map = list.get(0);

			String pid = StringUtil.getNotNullValueString(map.get("p_id"));
			String cid = StringUtil.getNotNullValueString(map.get("c_id"));
			String type = StringUtil.getNotNullValueString(map.get("type"));

			// log.debug(pid + "_" +cid + "_"+ type );

			if (type.length() == 0 && cid.length() == 0 && pid.length() == 0) {
				resultInfo.addErrors(DeptContactMessage.CHECK_WRONG_PROCESS_INFO);
			} else {

				String[] c_array = new String[] { DeptContactConstants.STATUS_MAIN_STR, DeptContactConstants.STATUS_LOWER_STR };
				String[] p_array = new String[] { DeptContactConstants.STATUS_SUB_STR, DeptContactConstants.STATUS_MAIN_BACKSUB_STR };

				if (CommonUtil.targetIsInArray(type, c_array)) {
					id = cid;
				} else if (CommonUtil.targetIsInArray(type, p_array)) {
					id = pid;
				} else {
					// log.debug("no type"+type);
				}

				params.addProcessParam(DeptContactConstants.PARAMS_FLOW_TYPE, type);

				if (id.length() == 0) {
					resultInfo.addErrors(DeptContactMessage.CHECK_WRONG_PROCESS_INFO);
				} else {

					try {
						TDeptContactMain mainBo = new TDeptContactMain();
						DeptContactMainVo mainVo = new DeptContactMainVo();

						mainBo = (TDeptContactMain) commonService.load(id, TDeptContactMain.class);

						params.mainBo = mainBo;
						params.treeBo = deptContactDao.getTreeBoByMainBoId(id);

						BeanUtils.copyProperties(mainVo, mainBo);

						params.addParam("mainBo.initiatorName", mainBo.getInitiatorName());
						params.addParam("mainBo.createDeptname", mainBo.getCreateDeptname());
						params.addParam("mainBo.serial", deptContactCommonService.getSerialNumberText(mainBo));

						params.addParam(DeptContactConstants.PARAMS_KEY_REF_ID, deptContactCommonService.getReferenceIds(mainBo.getId()));

						return mainVo;
					} catch (Exception e) {
						resultInfo.addErrors(DeptContactMessage.CHECK_WRONG_PROCESS_INFO);
					}
				}
			}
		} else {
			resultInfo.addErrors(DeptContactMessage.CHECK_WRONG_PROCESS_INFO);
		}

		return null;

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public DeptContactMainVo flowStepForward(DeptContactParamVo params) {
		if(!resultInfo.checkFlag){
			return null;
		}
		
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		
		String sql = "select t.p_id,t.c_id,t.type from t_dept_contact_tree t where t.cname=? and t.cincident=? and t.removed != 1";
//log.debug("cname:"+cname + " cincident:" +cincident);
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql,new Object[]{cname,cincident});
		
		return this.forward(list, params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DeptContactMainVo view(String pname,String pincident,DeptContactParamVo params) {
		
		TDeptContactMain mainBo = new TDeptContactMain();
		DeptContactMainVo mainVo = new DeptContactMainVo();
		mainBo = this.deptContactDao.getMainBo(pname, pincident);
		try {
			BeanUtils.copyProperties(mainVo, mainBo);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.mainBo = mainBo;
		params.treeBo = deptContactDao.getTreeBoByMainBoId(mainBo.getId());

		params.addParam("mainBo.initiatorName", mainBo.getInitiatorName());
		params.addParam("mainBo.createDeptname", mainBo.getCreateDeptname());
		params.addParam("mainBo.serial", deptContactCommonService.getSerialNumberText(mainBo));

		params.addParam(DeptContactConstants.PARAMS_KEY_REF_ID, deptContactCommonService.getReferenceIds(mainBo.getId()));
		return mainVo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DeptContactMainVo viewForward(DeptContactParamVo params) {
		
		String sql = "select t.p_id,t.c_id,t.type from t_dept_contact_tree t where t.c_id=? and t.removed != 1";
//log.debug("cname:"+cname + " cincident:" +cincident);
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql,new Object[]{params.mainVo.getId()});
		
		return this.forward(list, params);
	}
	
	@Override
	public void flowStepAdd(DeptContactParamVo params){
//log.debug(resultInfo.checkFlag+" "+resultInfo.checkOnly);
		if(resultInfo.getOperateFlag()){
			
			String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
			String processName = DeptContactConstants.PROCESSNAME;
			
			/**创建mainBo*/
			mainBo = new TDeptContactMain();
			try {
				BeanUtils.copyProperties(mainBo, mainVo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			mainBo.setCreateDeptid(userInfo.getDeptId());
			mainBo.setCreateDeptname(userInfo.getDeptName());
			
			mainBo.setInitiator(userInfo.getLoginName());
			mainBo.setInitiatorName(userInfo.getUserName());
			
			mainBo.setStartTime(time);
			
			mainBo.setOperateDate(time);
			mainBo.setOperateUser(taskUserLoginName);
			mainBo.setOperateName(userInfo.getUserName());
			
			mainBo.setProcessname(processName);
			
			/**添加关联信息*/
			
			TDeptContactTree treeBo = DeptContactUtil.generateTreeBo();
			params.treeBo = treeBo;
			
			treeBo.setCname(processName);
			treeBo.setType(DeptContactConstants.STATUS_MAIN);
			
			commonService.save(treeBo);
			
			
			/**发起流程*/
			int incidentNo = flowBegin();
			
			if(incidentNo>0){
				
				String incidentNoStr = String.valueOf(incidentNo);
				mainBo.setIncidentno(incidentNoStr);
				commonService.save(mainBo);
				
				String id = StringUtil.getNotNullValueString(mainBo.getId());
				if(id.length()==0){
					log.warn(DeptContactMessage.FAIL_TO_SAVE_DATA);
					resultInfo.addErrors(DeptContactMessage.FAIL_TO_SAVE_DATA);
					throw new RuntimeException(DeptContactMessage.FAIL_TO_SAVE_DATA.textCn);
				}
				
				DeptContactUtil.copyOperateInfo(treeBo, mainBo);
				/**更新关联信息*/
				treeBo.setCId(id);
				treeBo.setCincident(incidentNoStr);
				commonService.update(treeBo);
				
				try{
					
					params.addProcessParam("cname", mainBo.getProcessname());
					params.addProcessParam("cincident", mainBo.getIncidentno());
					
					params.mainBo = mainBo;
					deptContactCommonService.saveReferences(params);
					
					deptContactCommonService.saveApprovedInfo(params);
					
				}catch(Exception e)	{
					e.printStackTrace();
				}
				
				/**跳转URL*/
				resultInfo.url = TextUtil.generateDeptContactUrl(mainBo.getProcessname(), incidentNoStr, mainBo.getProcessname(), incidentNoStr,"");
			}else{
				log.warn(DeptContactMessage.FAIL_TO_LAUNCH_PROCESS);
				resultInfo.addErrors(DeptContactMessage.FAIL_TO_LAUNCH_PROCESS);
				throw new RuntimeException(DeptContactMessage.FAIL_TO_LAUNCH_PROCESS.textCn);
			}
		}
	}
	
	@Override
	public void flowStepUpdate(DeptContactParamVo params){
		if(resultInfo.getOperateFlag()){
			DeptContactMainVo mainVo = params.mainVo;
			
			TDeptContactMain mainBo = (TDeptContactMain) commonService.load(mainVo.getId(), TDeptContactMain.class);
			
			String taskUserLoginName = userInfo.getLoginName();
			
			try {
				BeanUtils.copyProperties(mainBo, mainVo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			mainBo.setUpdateTime(time);
			
			mainBo.setOperateDate(time);
			mainBo.setOperateUser(taskUserLoginName);
			mainBo.setOperateName(userInfo.getUserName());
			
			params.mainBo = mainBo;
			deptContactCommonService.saveReferences(params);
			
			commonService.update(mainBo);
			
		}
	}
	
	@Override
	public void flowStepApply(DeptContactParamVo params){
		if(resultInfo.getOperateFlag()){
			String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
			//mainBo = (TDeptContactMain) params.get("mainBo");
			mainBo = (TDeptContactMain) commonService.load(operateVo.getId(), TDeptContactMain.class);
			params.addProcessParam("cname", mainBo.getProcessname());
			params.addProcessParam("cincident", mainBo.getIncidentno());
			
			boolean flag = flowApply();
			
			if(flag){
				/**相关资料*/
				params.mainBo = mainBo;
				deptContactCommonService.saveReferences(params);
				
				/**取消*/
				if(DeptContactConstants.CHOICE_APPLY_TO_CANCEL.equals(operateVo.getChoice())){
					mainBo.setRemoved(1);
				}
				
				/**更新操作人及更新信息*/
				mainBo.setUpdateTime(time);
				mainBo.setOperateDate(time);
				mainBo.setOperateUser(taskUserLoginName);
				mainBo.setOperateName(userInfo.getUserName());
				commonService.update(mainBo);
				
				/**保存意见*/
				deptContactCommonService.saveApprovedInfo(params);
				
				/**更新待办事项状态*/
				updateTaskStatus(params.operateVo.getTaskId());
				
				/**跳转URL*/
				resultInfo.url = TextUtil.generateDeptContactUrl(mainBo,operateVo.getSteplabel());
			}else{
				resultInfo.addErrors(DeptContactMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DeptContactMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepSign(DeptContactParamVo params){	
		if(resultInfo.getOperateFlag()){
			mainBo = (TDeptContactMain) commonService.load(operateVo.getId(), TDeptContactMain.class);
			
			//log.debug("id:"+operateVo.getId());
			//log.debug(params);
			
			params.addProcessParam("cname", mainBo.getProcessname());
			params.addProcessParam("cincident", mainBo.getIncidentno());

			boolean flag = flowSign();
			
			if(flag){
				String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
				
				/**更新操作人及更新信息*/
				mainBo.setUpdateTime(time);
				mainBo.setOperateDate(time);
				mainBo.setOperateUser(taskUserLoginName);
				mainBo.setOperateName(userInfo.getUserName());
				commonService.update(mainBo);
				
				/**保存意见*/
				deptContactCommonService.saveApprovedInfo(params);
				
				/**更新待办事项状态*/
				updateTaskStatus(params.operateVo.getTaskId());
				
				/**跳转URL*/
				resultInfo.url = TextUtil.generateDeptContactUrl(mainBo,operateVo.getSteplabel());
			}else{
				resultInfo.addErrors(DeptContactMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DeptContactMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}

	/**Begin节点
	 * @param mainVo
	 * @param userInfo
	 * @param params_dc
	 * @throws Exception
	 */
	public int flowBegin(){
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		String sign_leader = StringUtil.getNotNullValueString(params.getParamObject(DeptContactConstants.PARAMS_KEY_SIGN_LEADER));
		
		String ip = IpUtil.getIpByStartString(DeptContactConstants.IP_PREFIX);
		String port = StringUtil.getNotNullValueString(params.getParamObject(DeptContactConstants.IP_PORT));
		String contextPath = StringUtil.getNotNullValueString(params.getParamObject(DeptContactConstants.IP_CONTEXT_PATH));
		
		map.put(DeptContactFlowConstants.SERVERIP, "http://"+ip+":"+port+"/"+contextPath);
		
		map.put(DeptContactFlowConstants.USER_BEGIN, taskUserLoginName);
		map.put(DeptContactFlowConstants.USER_APPLY, taskUserLoginName);
		map.put(DeptContactFlowConstants.USER_SIGN, sign_leader);

		map.put(DeptContactFlowConstants.DEPTID, userInfo.getDeptId());
		map.put(DeptContactFlowConstants.TARGET, DeptContactFlowConstants.STEP_SIGN);
		map.put("短信内容", mainBo.getTheme());
		
		//log.debug("发起流程开始");
		map.put(DeptContactFlowConstants.LINK_PROCESS_KEY, params.treeBo.getId());
		
		int incidentNo = this.launchProcess(mainBo.getProcessname(), taskUserLoginName, mainBo.getTheme(), map);
		
	
		log.debug("发起流程结束，incidentNo="+incidentNo);
		
		return incidentNo;
	}
	
	/**申请节点
	 * @param mainVo
	 * @param userInfo
	 * @param params_dc
	 * @throws Exception
	 */
	public boolean flowApply(){
		//UserVo userVo = params.userVo;
		
		String processName = StringUtil.getNotNullValueString(mainBo.getProcessname());
		String incidentNo = StringUtil.getNotNullValueString(mainBo.getIncidentno());
		//String summary = StringUtil.getNotNullValueString(mainBo.getTheme());
		String summary = FlowUtil.getSummaryByProcessInfo(processName, incidentNo);
		
		Map<String,Object> map = new HashMap<String,Object>();

		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);

		/**Ultimus签发节点信息*/
		String sign_leader = StringUtil.getNotNullValueString(params.getParamObject(DeptContactConstants.PARAMS_KEY_SIGN_LEADER));
		
//log.debug(params.getParamObject(DeptcontactConstants.PARAMS_KEY_SIGN_LEADER));
//log.debug("param.size()="+params.param.size());
//log.debug("sign_leader:"+sign_leader);
		
		map.put(DeptContactFlowConstants.USER_SIGN, sign_leader);
		

		/**签发*/
		if(DeptContactConstants.CHOICE_APPLY_TO_SIGN.equals(operateVo.getChoice())){
			map.put(DeptContactFlowConstants.TARGET, DeptContactFlowConstants.STEPNAME_SIGN);
		}
		/**取消*/
		if(DeptContactConstants.CHOICE_APPLY_TO_CANCEL.equals(operateVo.getChoice())){
			map.put(DeptContactFlowConstants.TARGET, DeptContactFlowConstants.STEPNAME_END);
		}
		/**/
		boolean flag=PWSUtil.completeStepTest(processName, taskUserLoginName, Integer.parseInt(incidentNo), operateVo.getSteplabel(), summary, "", map);
		
		return flag;
		
		//return false;
	}
	
	/**签发节点
	 * @param mainVo
	 * @param userInfo
	 * @param params_dc
	 * @throws Exception
	 */
	public boolean flowSign(){
		
		String processName = StringUtil.getNotNullValueString(mainBo.getProcessname());
		String incidentNo = StringUtil.getNotNullValueString(mainBo.getIncidentno());
		//String summary = StringUtil.getNotNullValueString(mainBo.getTheme());
		String summary = FlowUtil.getSummaryByProcessInfo(processName, incidentNo);
		
		Map<String,Object> map = new HashMap<String,Object>();
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		
		/**同意签发*/
		if(DeptContactConstants.CHOICE_SIGN_AGREE.equals(operateVo.getChoice())){
			map.put(DeptContactFlowConstants.TARGET, DeptContactFlowConstants.STEP_LOWER_SUB);
			
			/*签发后生成编号*/
			mainBo.setSerial(deptContactCommonService.getNextSerialNumber(mainBo));
		}
		/**返回修改*/
		if(DeptContactConstants.CHOICE_SIGN_TO_APPLY.equals(operateVo.getChoice())){
			map.put(DeptContactFlowConstants.TARGET, DeptContactFlowConstants.STEP_APPLY);
		}
		/**结束下级流程*/
		if(DeptContactConstants.CHOICE_SIGN_FINISH.equals(operateVo.getChoice())){
			map.put(DeptContactFlowConstants.TARGET, DeptContactFlowConstants.STEP_END);
		}
		
		//boolean flag = false;
		
		boolean flag = this.launchProcessStep(processName, taskUserLoginName, Integer.parseInt(incidentNo), operateVo.getSteplabel(), summary,"", map);
		
		return flag;
	}
	
	
	
	/**发起新流程
	 * @param processName
	 * @param taskUserLoginName
	 * @param summary
	 * @param map
	 * @return
	 */
	private int launchProcess(String processName, String taskUserLoginName, String summary, Map<String,Object> map){
log.debug("发起流程开始");
		int incidentNo = PWSUtil.launchIncident(processName, taskUserLoginName, summary, map);
		return incidentNo;
	}
	
	/**激活流程节点
	 * @param processName
	 * @param taskUserLoginName
	 * @param incidentno
	 * @param steplabel
	 * @param summary
	 * @param memo
	 * @param map
	 * @return
	 */
	private boolean launchProcessStep(String processName, String taskUserLoginName, int incidentno, String steplabel, String summary,String memo, Map<String,Object> map){
log.debug("激活流程开始");
		boolean flag=PWSUtil.completeStepTest(processName, taskUserLoginName, incidentno, steplabel, summary, memo, map);
log.debug("激活流程结束,flag="+flag);
		return flag;
	}
	
	
	
	private void updateTaskStatus(String taskId){
		try{
			taskId = StringUtil.getNotNullValueString(taskId);
			if(taskId.length()>0){
				TodoItem todoItem = (TodoItem) commonService.load(taskId, TodoItem.class);
				todoItem.setStatus(TodoConstants.STATUS_DONE);
				commonService.update(todoItem);
			}
		}catch(Exception e){
			
		}
	}
	
	

	/**getter and setter*/
	
	public TDeptContactMain getMainBo() {
		return mainBo;
	}

	public void setMainBo(TDeptContactMain mainBo) {
		this.mainBo = mainBo;
	}
	
	public ResultInfo getResultInfo() {
		return resultInfo;
	}
	
	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
	
	public DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

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
	
	public DeptContactDao getDeptContactDao() {
		return deptContactDao;
	}
	
	@Autowired
	public void setDeptContactDao(@Qualifier(value="contact-deptContactDao")DeptContactDao deptContactDao) {
		this.deptContactDao = deptContactDao;
	}
}
