/**
 * 
 */
package com.wonders.contract.workflow.process.dept.service.impl;

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

import com.wonders.contract.workflow.common.model.vo.ResultInfo;
import com.wonders.contract.workflow.common.model.vo.UserInfo;
import com.wonders.contract.workflow.common.service.CommonService;
import com.wonders.contract.workflow.common.util.LoginUtil;
import com.wonders.contract.workflow.common.util.SimpleLogger;
import com.wonders.contract.workflow.external.service.ExternalService;
import com.wonders.contract.workflow.organTree.model.bo.OrganUserBo;
import com.wonders.contract.workflow.process.dept.constants.DeptSubConstants;
import com.wonders.contract.workflow.process.dept.constants.DeptSubMessage;
import com.wonders.contract.workflow.process.dept.constants.DeptSubStepConstants;
import com.wonders.contract.workflow.process.dept.model.vo.DeptSubParamVo;
import com.wonders.contract.workflow.process.dept.model.vo.DeptSubVo;
import com.wonders.contract.workflow.process.dept.service.DeptSubApprovedService;
import com.wonders.contract.workflow.process.dept.service.DeptSubService;
import com.wonders.contract.workflow.process.util.ProcessUtil;
import com.wonders.contract.workflow.process.util.TextUtil;
import com.wonders.contract.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: DeptSubServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 上午11:42:32 
 *  
 */

@Service("contract-deptSubService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DeptSubServiceImpl implements DeptSubService{
	Logger log = SimpleLogger.getLogger(this.getClass());
	
	private CommonService commonService;
	private DeptSubApprovedService deptSubApprovedService;
	private ExternalService externalService;
	private DeptSubParamVo params;
	private DeptSubVo vo;
	private UserInfo userInfo;
	private ResultInfo resultInfo;
	
	@Override
	public void init(DeptSubParamVo params) {
		this.params = params;
		this.vo = params.vo;
		this.userInfo = params.userInfo;
		//this.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
		this.externalService.setToken(userInfo.getToken());
		this.resultInfo = params.resultInfo;
	}

	@Override
	public void flowStepDispatcher(DeptSubParamVo params) {
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowDispatcher()){
				deptSubApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DeptSubMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DeptSubMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}

	@Override
	public void flowStepDeal(DeptSubParamVo params) {
		if(resultInfo.getOperateFlag()){
		//init(params);
			if(flowDeal()){
				deptSubApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DeptSubMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DeptSubMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}

	
	@Override
	public void flowStepLeaderDeal(DeptSubParamVo params) {
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowLeaderDeal()){
				deptSubApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DeptSubMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DeptSubMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowDispatcher(){
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
//log.debug("processParams.size="+params.processParam.size());
		this.vo.initList();
		String summary = FlowUtil.getSummaryByProcessInfo(cname, cincident);
		
		Map map = new HashMap();

		if(vo.getDealPersonList().size()>0){
			FlowUtil.putUltimusMap("部门处理人帐号",vo.getDealPersonList(),map);
		}
		map.put("部门处理人姓名汇总",vo.getDealPersonList().size());
		
		map.put("部门领导帐号",vo.getDealLeaderList());
		map.put("部门领导姓名",vo.getDealLeaderList().size());
		
		map.put("当前步骤名", steplabel);
		
		boolean flag=ProcessUtil.launchProcessStep(cname, taskUserLoginName, Integer.parseInt(cincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	private boolean flowDeal(){
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(cname, cincident);
		
		boolean flag=ProcessUtil.launchProcessStep(cname, taskUserLoginName, Integer.parseInt(cincident), steplabel, summary, "", new HashMap<String,Object>());
		
		return flag;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean flowLeaderDeal(){
//log.debug("flowLeaderDeal");
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String choice2 = StringUtil.getNotNullValueString(vo.getChoice2());
		String summary = FlowUtil.getSummaryByProcessInfo(cname, cincident);
		this.vo.initList();
		Map map = new HashMap();
		
		String recvId = "";
		List<OrganUserBo> userBoList = externalService.getProcessReceivers(pname,DeptSubStepConstants.STEPNAME_DISPATCHER,userInfo.getDeptId());
		if(userBoList != null &&userBoList.size()>0){
			recvId = userBoList.get(0).loginName;
		}
		if(DeptSubConstants.CHOICE_LEADER_PASS.equals(choice)){
			map.put("部门处理人姓名汇总", 0);
			map.put("部门领导姓名", 0);
		}else if(DeptSubConstants.CHOICE_LEADER_FAILED.equals(choice)){
			map.put("部门处理人姓名汇总", 0);
			map.put("部门领导姓名", 0);
		}else if(DeptSubConstants.CHOICE_LEADER_CONTINUE.equals(choice)){
			if(DeptSubConstants.CHOICE_LEADER_CONTINUE_DEAL.equals(choice2)){
				map.put("返回修改", "1");
				map.put("部门接收人帐号", recvId);
			}else if(DeptSubConstants.CHOICE_LEADER_CONTINUE_CHOICE_PERSON.equals(choice2)){
				if(vo.getDealPersonList().size()>0){
					FlowUtil.putUltimusMap("部门处理人帐号",vo.getDealPersonList(),map);
				}
				map.put("部门处理人姓名汇总", vo.getDealPersonList().size());
					
				if(vo.getDealLeaderList().size()>0){
					map.put("部门领导帐号", vo.getDealLeaderStr());
				}
				map.put("部门领导姓名", vo.getDealLeaderList().size());
				map.put("部门接收人帐号", recvId);
			}
		}
		
		//boolean flag = false;

		boolean flag = ProcessUtil.launchProcessStep(cname, taskUserLoginName, Integer.parseInt(cincident), steplabel, summary, "", map);

		return flag;
	
	}
	
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired(required=false)
	public void setCommonService(@Qualifier(value="contract-commonService")CommonService commonService) {
		this.commonService = commonService;
	}

	public DeptSubApprovedService getDeptSubApprovedService() {
		return deptSubApprovedService;
	}

	@Autowired(required=false)
	public void setDeptSubApprovedService(
			@Qualifier(value="contract-deptSubApprovedService")DeptSubApprovedService deptSubApprovedService) {
		this.deptSubApprovedService = deptSubApprovedService;
	}

	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired(required=false)
	public void setExternalService(@Qualifier("contract-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}
	
}
