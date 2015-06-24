/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.service.impl;

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

import com.wonders.receive.workflow.common.model.vo.ResultInfo;
import com.wonders.receive.workflow.common.model.vo.UserInfo;
import com.wonders.receive.workflow.common.service.CommonService;
import com.wonders.receive.workflow.common.util.LoginUtil;
import com.wonders.receive.workflow.common.util.SimpleLogger;
import com.wonders.receive.workflow.external.service.ExternalService;
import com.wonders.receive.workflow.organTree.model.bo.OrganUserBo;
import com.wonders.receive.workflow.process.simulate.constants.SimulateSubConstants;
import com.wonders.receive.workflow.process.simulate.constants.SimulateSubMessage;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubParamVo;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubVo;
import com.wonders.receive.workflow.process.simulate.service.SimulateSubApprovedService;
import com.wonders.receive.workflow.process.simulate.service.SimulateSubFutureService;
import com.wonders.receive.workflow.process.simulate.service.SimulateSubService;
import com.wonders.receive.workflow.process.simulate.util.SimulateSubUtil;
import com.wonders.receive.workflow.process.util.ProcessUtil;
import com.wonders.receive.workflow.process.util.TextUtil;
import com.wonders.receive.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: SimulateSubServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 上午11:42:32 
 *  
 */

@Service("receive-simulateSubService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class SimulateSubServiceImpl implements SimulateSubService{
	Logger log = SimpleLogger.getLogger(this.getClass());
	
	private CommonService commonService;
	private SimulateSubApprovedService simulateSubApprovedService;
	private SimulateSubFutureService simulateSubFutureService;
	private ExternalService externalService;
	private SimulateSubParamVo params;
	private SimulateSubVo vo;
	private UserInfo userInfo;
	private ResultInfo resultInfo;
	
	@Override
	public void init(SimulateSubParamVo params) {
		this.params = params;
		this.vo = params.vo;
		this.userInfo = params.userInfo;
		//this.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
		this.externalService.setToken(userInfo.getToken());
		this.resultInfo = params.resultInfo;
	}

	@Override
	public void flowStepSimulate(SimulateSubParamVo params) {
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowSimulate()){
				simulateSubApprovedService.saveApprovedInfo(params);
				simulateSubFutureService.saveFutureInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(SimulateSubMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(SimulateSubMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}

	@Override
	public void flowStepLeaderSimulate(SimulateSubParamVo params) {
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowLeaderSimulate()){
				simulateSubApprovedService.saveApprovedInfo(params);
				simulateSubFutureService.saveFutureInfo(params);
				/*更新待办事项状态*/
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(SimulateSubMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(SimulateSubMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}

	
	@Override
	public void flowStepSuggest(SimulateSubParamVo params) {
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowSuggest()){
				simulateSubApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(SimulateSubMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(SimulateSubMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowSimulate(){
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		//String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());
		String type = StringUtil.getNotNullValueString(vo.getType());
		//String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = StringUtil.getNotNullValueString(SimulateSubUtil.getOptionCode(vo));
		//String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		String summary = FlowUtil.getSummaryByProcessInfo(cname, cincident);		
		this.vo.initList();
		Map map = new HashMap();
		
		map.put("当前操作选择", optionCode);
		//psbx
		FlowUtil.putUltimusMap("批示领导帐号",vo.getInsSignLdsIdList(),map);
		FlowUtil.putUltimusDeptMap("批示领导部门ID", vo.getInsSignLdsDepIdList(),map);
		FlowUtil.putUltimusMap("批示领导秘书帐号", vo.getInsSignSecsIdList(),map);
		FlowUtil.putUltimusMap("批示领导秘书名称", vo.getInsSignSecsNameList(),map);
		FlowUtil.putUltimusDeptMap("批示领导秘书部门ID", vo.getInsSignSecsDepIdList(),map);
		//pscx
		FlowUtil.putUltimusMap("批示领导帐号串行", vo.getInsInsLdsIdList(),map);
		FlowUtil.putUltimusDeptMap("批示领导部门id串行", vo.getInsInsLdsDepIdList(),map);
		FlowUtil.putUltimusMap("批示领导秘书帐号串行", vo.getInsInsSecsIdList(),map);
		FlowUtil.putUltimusMap("批示领导秘书姓名串行", vo.getInsInsSecsNameList(),map);
		FlowUtil.putUltimusDeptMap("批示领导秘书部门id串行", vo.getInsInsSecsDepIdList(),map);
		
		//nbjy
		FlowUtil.putUltimusMap("拟办建议人帐号", vo.getSugDepLdsIdList(),map);
		FlowUtil.putUltimusMap("拟办子流程部门id11", vo.getSugDepLdsDepIdList(),map);
		
		//zhuban
		map.put("主办部门ID号", vo.getMainDepId());
		map.put("主办部门名", vo.getMainDepName());
		if(vo.getMainDepIdList().size()==0){
			map.put("主办部门数量",0);
		}
		map.put("主办部门接收人帐号", vo.getMainDepRecvId());
		map.put("主办部门领导帐号", vo.getMainDepLdId());
		
		//xieban
		FlowUtil.putUltimusMap("协办部门ID", vo.getDepsIdList(),map);
		FlowUtil.putUltimusMap("协办部门名",vo.getDepsNameList(),map);
		FlowUtil.putUltimusMap("部门接收人帐号数组", vo.getDepRecvsIdList(),map);
		FlowUtil.putUltimusMap("部门领导帐号数组", vo.getDepLdsIdList(),map);
		
		//hqld
		FlowUtil.putUltimusMap("会签领导帐号", vo.getSignLdsIdList(),map);
		FlowUtil.putUltimusDeptMap("会签领导部门ID", vo.getSignLdsDepIdList(),map);
		FlowUtil.putUltimusMap("会签领导秘书帐号", vo.getSignSecsIdList(),map);
		FlowUtil.putUltimusMap("会签领导秘书姓名", vo.getSignSecsNameList(),map);
		FlowUtil.putUltimusDeptMap("会签领导秘书部门ID", vo.getSignSecsDepIdList(),map);
		
		//shld
		FlowUtil.putUltimusMap("审核领导帐号", vo.getChkLdsIdList(),map);
		FlowUtil.putUltimusDeptMap("审核领导部门ID", vo.getChkLdsDepIdList(),map);
		FlowUtil.putUltimusMap("审核领导秘书帐号", vo.getChkSecsIdList(),map);
		FlowUtil.putUltimusMap("审核领导秘书姓名", vo.getChkSecsNameList(),map);
		FlowUtil.putUltimusDeptMap("审核领导秘书部门ID", vo.getChkSecsDepIdList(),map);
		
		//bj
		map.put("办结人帐号", vo.getFinPersonId());
		map.put("部门ID2", vo.getFinPersonDepId());
		
		if(SimulateSubConstants.SUMBIT_LEADER.equals(type)){
			String leaderId = userInfo.getLoginName();
			List<OrganUserBo> userBoList = externalService.getDeptSingleLeader(userInfo.getDeptId());
			if(userBoList != null &&userBoList.size()>0){
				leaderId = userBoList.get(0).loginName;
			}
			map.put("当前处理人部门领导帐号", leaderId);
		}
		
		boolean flag=ProcessUtil.launchProcessStep(cname, taskUserLoginName, Integer.parseInt(cincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean flowLeaderSimulate(){
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		//String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());
		//String type = StringUtil.getNotNullValueString(vo.getType());
		//String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = StringUtil.getNotNullValueString(SimulateSubUtil.getOptionCode(vo));
		//String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		String summary = FlowUtil.getSummaryByProcessInfo(cname, cincident);		
		this.vo.initList();
		Map map = new HashMap();
		
		map.put("当前操作选择", optionCode);
		//psbx
		FlowUtil.putUltimusMap("批示领导帐号",vo.getInsSignLdsIdList(),map);
		FlowUtil.putUltimusDeptMap("批示领导部门ID", vo.getInsSignLdsDepIdList(),map);
		FlowUtil.putUltimusMap("批示领导秘书帐号", vo.getInsSignSecsIdList(),map);
		FlowUtil.putUltimusMap("批示领导秘书名称", vo.getInsSignSecsNameList(),map);
		FlowUtil.putUltimusDeptMap("批示领导秘书部门ID", vo.getInsSignSecsDepIdList(),map);
		//pscx
		FlowUtil.putUltimusMap("批示领导帐号串行", vo.getInsInsLdsIdList(),map);
		FlowUtil.putUltimusDeptMap("批示领导部门id串行", vo.getInsInsLdsDepIdList(),map);
		FlowUtil.putUltimusMap("批示领导秘书帐号串行", vo.getInsInsSecsIdList(),map);
		FlowUtil.putUltimusMap("批示领导秘书姓名串行", vo.getInsInsSecsNameList(),map);
		FlowUtil.putUltimusDeptMap("批示领导秘书部门id串行", vo.getInsInsSecsDepIdList(),map);
		
		//nbjy
		FlowUtil.putUltimusMap("拟办建议人帐号", vo.getSugDepLdsIdList(),map);
		FlowUtil.putUltimusMap("拟办子流程部门id11", vo.getSugDepLdsDepIdList(),map);
		
		//zhuban
		map.put("主办部门ID号", vo.getMainDepId());
		map.put("主办部门名", vo.getMainDepName());
		if(vo.getMainDepIdList().size()==0){
			map.put("主办部门数量",0);
		}
		map.put("主办部门接收人帐号", vo.getMainDepRecvId());
		map.put("主办部门领导帐号", vo.getMainDepLdId());
		
		//xieban
		FlowUtil.putUltimusMap("协办部门ID", vo.getDepsIdList(),map);
		FlowUtil.putUltimusMap("协办部门名",vo.getDepsNameList(),map);
		FlowUtil.putUltimusMap("部门接收人帐号数组", vo.getDepRecvsIdList(),map);
		FlowUtil.putUltimusMap("部门领导帐号数组", vo.getDepLdsIdList(),map);
		
		//hqld
		FlowUtil.putUltimusMap("会签领导帐号", vo.getSignLdsIdList(),map);
		FlowUtil.putUltimusDeptMap("会签领导部门ID", vo.getSignLdsDepIdList(),map);
		FlowUtil.putUltimusMap("会签领导秘书帐号", vo.getSignSecsIdList(),map);
		FlowUtil.putUltimusMap("会签领导秘书姓名", vo.getSignSecsNameList(),map);
		FlowUtil.putUltimusDeptMap("会签领导秘书部门ID", vo.getSignSecsDepIdList(),map);
		
		//shld
		FlowUtil.putUltimusMap("审核领导帐号", vo.getChkLdsIdList(),map);
		FlowUtil.putUltimusDeptMap("审核领导部门ID", vo.getChkLdsDepIdList(),map);
		FlowUtil.putUltimusMap("审核领导秘书帐号", vo.getChkSecsIdList(),map);
		FlowUtil.putUltimusMap("审核领导秘书姓名", vo.getChkSecsNameList(),map);
		FlowUtil.putUltimusDeptMap("审核领导秘书部门ID", vo.getChkSecsDepIdList(),map);
		
		//bj
		map.put("办结人帐号", vo.getFinPersonId());
		map.put("部门ID2", vo.getFinPersonDepId());
		
		boolean flag=ProcessUtil.launchProcessStep(cname, taskUserLoginName, Integer.parseInt(cincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	private boolean flowSuggest(){
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(cname, cincident);
		
		boolean flag=ProcessUtil.launchProcessStep(cname, taskUserLoginName, Integer.parseInt(cincident), steplabel, summary, "", new HashMap<String,Object>());
		
		return flag;
	
	}
	
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired(required=false)
	public void setCommonService(@Qualifier(value="receive-commonService")CommonService commonService) {
		this.commonService = commonService;
	}

	public SimulateSubApprovedService getSimulateSubApprovedService() {
		return simulateSubApprovedService;
	}

	@Autowired(required=false)
	public void setSimulateSubApprovedService(
			@Qualifier(value="receive-simulateSubApprovedService")SimulateSubApprovedService simulateSubApprovedService) {
		this.simulateSubApprovedService = simulateSubApprovedService;
	}
	
	public SimulateSubFutureService getSimulateSubFutureService() {
		return simulateSubFutureService;
	}

	@Autowired(required=false)
	public void setSimulateSubFutureService(
			@Qualifier(value="receive-simulateSubFutureService")SimulateSubFutureService simulateSubFutureService) {
		this.simulateSubFutureService = simulateSubFutureService;
	}

	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired(required=false)
	public void setExternalService(@Qualifier("receive-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}
	
}
