package com.wonders.contact.deptSubProcess.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.contact.common.model.bo.ResultInfo;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.service.CommonService;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.util.FlowUtil;
import com.wonders.contact.deptContact.util.TextUtil;
import com.wonders.contact.deptSubProcess.constant.DeptSubProcessConstants;
import com.wonders.contact.deptSubProcess.constant.DeptSubProcessFlowConstants;
import com.wonders.contact.deptSubProcess.constant.DeptSubProcessMessage;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessParamVo;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessVo;
import com.wonders.contact.deptSubProcess.service.DeptSubProcessCommonService;
import com.wonders.contact.deptSubProcess.service.DeptSubProcessService;
import com.wonders.contact.external.service.ExternalService;
import com.wonders.contact.organTree.model.bo.OrganUserBo;
import com.wonders.contact.todo.constant.TodoConstants;
import com.wonders.contact.todo.model.bo.TodoItem;
import com.wonders.util.PWSUtil;
import com.wonders.util.StringUtil;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
@Service("contact-deptSubProcessService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DeptSubProcessServiceImpl implements DeptSubProcessService {
	
	private CommonService commonService;
	private DeptSubProcessCommonService deptSubProcessCommonService;
	
	private ExternalService externalService;
	
	Logger log = SimpleLogger.getLogger(this.getClass());
	
	private DeptSubProcessParamVo params;
	private DeptSubProcessVo vo;
	private UserInfo userInfo;
	private ResultInfo resultInfo = new ResultInfo();
	
	@Override
	public void init(DeptSubProcessParamVo params){
		this.params = params;
		this.vo = params.vo;
		this.resultInfo = params.resultInfo;
		this.userInfo = params.userInfo;
	}
	
	@Override
	public void flowStepDispatcher(DeptSubProcessParamVo params){
		//log.debug(this.vo.toString());
		if(resultInfo.getOperateFlag()){
			//boolean flag = flowDispatcher();
			if(flowDispatcher()){
				deptSubProcessCommonService.saveApprovedInfo(params);
				
				/**更新待办事项状态*/
				updateTaskStatus(params.vo.getTaskId());
				
				resultInfo.url = TextUtil.generateDeptContactUrl(params);
			}else{
				resultInfo.addErrors(DeptSubProcessMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DeptSubProcessMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepDeal(DeptSubProcessParamVo params){
		if(resultInfo.getOperateFlag()){
			//boolean flag = flowDeal();
			if(flowDeal()){
				deptSubProcessCommonService.saveApprovedInfo(params);
				
				/**更新待办事项状态*/
				updateTaskStatus(params.vo.getTaskId());
				
				resultInfo.url = TextUtil.generateDeptContactUrl(params);
			}else{
				resultInfo.addErrors(DeptSubProcessMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DeptSubProcessMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}

	@Override
	public void flowStepLeaderDeal(DeptSubProcessParamVo params){
		if(resultInfo.getOperateFlag()){
			//boolean flag = flowLeaderDeal();
			if(flowLeaderDeal()){
				deptSubProcessCommonService.saveApprovedInfo(params);
				
				/**更新待办事项状态*/
				updateTaskStatus(params.vo.getTaskId());
				
				resultInfo.url = TextUtil.generateDeptContactUrl(params);
			}else{
				resultInfo.addErrors(DeptSubProcessMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DeptSubProcessMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean flowDispatcher(){
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String processName = params.getProcessParamValue("cname");
		String incidentNo = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
//log.debug("processParams.size="+params.processParam.size());
		
		String summary = FlowUtil.getSummaryByProcessInfo(processName, incidentNo);
		
		Map map = new HashMap();

		if(vo.getDealPersonList().size()>0){
			FlowUtil.putUltimusMap("部门处理人帐号",vo.getDealPersonList(),map);
		}
		map.put("部门处理人姓名汇总",vo.getDealPersonList().size());
		
		map.put("部门领导帐号",vo.getDealLeaderStr());
		map.put("部门领导姓名",vo.getDealLeaderList().size());
		
		map.put("当前步骤名", steplabel);
		
		boolean flag=this.launchProcessStep(processName, taskUserLoginName, Integer.parseInt(incidentNo), steplabel, summary, "", map);
		
		return flag;
	}
	
	private boolean flowDeal(){
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String processName = params.getProcessParamValue("cname");
		String incidentNo = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		
		String summary = FlowUtil.getSummaryByProcessInfo(processName, incidentNo);
		
		boolean flag=this.launchProcessStep(processName, taskUserLoginName, Integer.parseInt(incidentNo), steplabel, summary, "", new HashMap<String,Object>());
		
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	private boolean flowLeaderDeal(){
//log.debug("flowLeaderDeal");
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String processName = params.getProcessParamValue("cname");
		String incidentNo = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		
		String summary = FlowUtil.getSummaryByProcessInfo(processName, incidentNo);
		
		Map map = new HashMap();
		
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		if(DeptSubProcessConstants.CHOICE_LEADER_PASS.equals(choice)){
			map.put("部门处理人姓名汇总", 0);
			map.put("部门领导姓名", 0);
		}	
		if(DeptSubProcessConstants.CHOICE_LEADER_FAILED.equals(choice)){
			map.put("部门处理人姓名汇总", 0);
			map.put("部门领导姓名", 0);
		}
		if(DeptSubProcessConstants.CHOICE_LEADER_CONTINUE.equals(choice)){
			String choice2 = StringUtil.getNotNullValueString(vo.getChoice2());

			try{
				externalService.setToken(userInfo.getToken());
				List<OrganUserBo> list = externalService.getProcessReceivers(pname, DeptSubProcessFlowConstants.STEPNAME_DISPATCHER,userInfo.getDeptId());
				if(list.size()>0){
					OrganUserBo user = list.get(0);
					map.put("主办部门接收人帐号", user.loginName);
					map.put("部门接收人帐号", user.loginName);
log.debug("user.loginName="+user.loginName);
				}
			}catch(Exception e){
				//e.printStackTrace();
log.error(e.getMessage());
			}
			
			
			if(DeptSubProcessConstants.CHOICE_LEADER_CONTINUE_DEAL.equals(choice2)){
				map.put("返回修改", "1");
			}
			
			if(DeptSubProcessConstants.CHOICE_LEADER_CONTINUE_CHOICE_PERSON.equals(choice2)){
				if(vo.getDealPersonList().size()>0){
					FlowUtil.putUltimusMap("部门处理人帐号",vo.getDealPersonList(),map);
				}
				map.put("部门处理人姓名汇总", vo.getDealPersonList().size());
					
				if(vo.getDealLeaderList().size()>0){
					map.put("部门领导帐号", vo.getDealLeaderList());
				}
				map.put("部门领导姓名", vo.getDealLeaderList().size());
				
//log.debug("vo.getDealPersonList().size()="+vo.getDealPersonList().size());
//log.debug("vo.getDealPersonList()="+vo.getDealPersonList());
//log.debug("vo.getDealLeaderList().size()="+vo.getDealLeaderList().size());
//log.debug("vo.getDealLeaderList()="+vo.getDealLeaderList());
			}
		}
		
		//boolean flag = false;

		boolean flag = this.launchProcessStep(processName, taskUserLoginName, Integer.parseInt(incidentNo), steplabel, summary, "", map);

		return flag;
	
	}
	

	
	private boolean launchProcessStep(String processName, String taskUserLoginName, int incidentno, String steplabel, String summary,String memo, Map<String,Object> map){
//log.debug("触发流程开始");
		//boolean flag=true;
		boolean flag=PWSUtil.completeStepTest(processName, taskUserLoginName, incidentno, steplabel, summary, memo, map);
log.debug("触发流程结束,flag="+flag);
		return flag;
	}
			
	
	/**更新待办事项状态
	 * @param taskId
	 */
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
	
	
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired
	public void setCommonService(@Qualifier(value="contact-commonService")CommonService commonService) {
		this.commonService = commonService;
	}

	public DeptSubProcessCommonService getDeptSubProcessCommonService() {
		return deptSubProcessCommonService;
	}

	@Autowired
	public void setDeptSubProcessCommonService(@Qualifier(value="contact-deptSubProcessCommonService")DeptSubProcessCommonService deptSubProcessCommonService) {
		this.deptSubProcessCommonService = deptSubProcessCommonService;
	}

	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired
	public void setExternalService(@Qualifier("contact-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}
}
