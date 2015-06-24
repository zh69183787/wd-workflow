/**
 * 
 */
package com.wonders.receive.workflow.process.finish.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.constants.WorkflowConstants;
import com.wonders.deptDoc.service.DeptDocService;
import com.wonders.receive.oa.service.OaService;
import com.wonders.receive.workflow.common.model.vo.ResultInfo;
import com.wonders.receive.workflow.common.model.vo.UserInfo;
import com.wonders.receive.workflow.common.service.CommonService;
import com.wonders.receive.workflow.common.util.LoginUtil;
import com.wonders.receive.workflow.common.util.SimpleLogger;
import com.wonders.receive.workflow.external.service.ExternalService;
import com.wonders.receive.workflow.process.finish.constants.FinishSubConstants;
import com.wonders.receive.workflow.process.finish.constants.FinishSubMessage;
import com.wonders.receive.workflow.process.finish.model.vo.FinishSubParamVo;
import com.wonders.receive.workflow.process.finish.model.vo.FinishSubVo;
import com.wonders.receive.workflow.process.finish.service.FinishSubApprovedService;
import com.wonders.receive.workflow.process.finish.service.FinishSubFutureService;
import com.wonders.receive.workflow.process.finish.service.FinishSubService;
import com.wonders.receive.workflow.process.finish.util.FinishSubUtil;
import com.wonders.receive.workflow.process.util.ProcessUtil;
import com.wonders.receive.workflow.process.util.TextUtil;
import com.wonders.receive.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: FinishSubServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 上午11:42:32 
 *  
 */

@Service("receive-finishSubService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class FinishSubServiceImpl implements FinishSubService{
	Logger log = SimpleLogger.getLogger(this.getClass());
	
	//2014-10-18 zhoushun
	@Autowired
	@Qualifier("docRecvDeptDocService")
	private DeptDocService docRecvDeptDocService;
	
	@Autowired
	@Qualifier("docRedvDeptDocService")
	private DeptDocService docRedvDeptDocService;
	
	private CommonService commonService;
	private FinishSubApprovedService finishSubApprovedService;
	private FinishSubFutureService finishSubFutureService;
	private ExternalService externalService;
	private FinishSubParamVo params;
	private FinishSubVo vo;
	private UserInfo userInfo;
	private ResultInfo resultInfo;
	private OaService oaService;
	
	@Override
	public void init(FinishSubParamVo params) {
		this.params = params;
		this.vo = params.vo;
		this.userInfo = params.userInfo;
		//this.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
		this.resultInfo = params.resultInfo;
	}

	@Override
	public void flowStepFinish(FinishSubParamVo params) {
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowFinish()){
				finishSubApprovedService.saveApprovedInfo(params);
				finishSubFutureService.saveFutureInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(FinishSubMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(FinishSubMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowFinish(){
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		//String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = StringUtil.getNotNullValueString(FinishSubUtil.getOptionCode(vo));
		//String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		String summary = FlowUtil.getSummaryByProcessInfo(cname, cincident);		
		this.vo.initList();
		Map map = new HashMap();
		
		map.put("当前操作选择", optionCode);
		
		if(FinishSubConstants.CHOICE_FINISH.equals(choice)){
			try{
				//发送通知
				oaService.init(params);
				oaService.addProcessMsg(params);
				oaService.addDocsFile(params);
			}catch(Exception e){}
			
			//部门文件柜 zhoushun 2014-10-18
			if(WorkflowConstants.NEW_RECV.equals(params.getProcessParamValue("pname"))){
				docRecvDeptDocService.addDocs(params.getProcessParamValue("pname"), 
						params.getProcessParamValue("pincident"), taskUserLoginName, userInfo.getUserName(),
						userInfo.getDeptId());
			}else if(WorkflowConstants.NEW_REDV.equals(params.getProcessParamValue("pname"))){
				docRedvDeptDocService.addDocs(params.getProcessParamValue("pname"), 
						params.getProcessParamValue("pincident"), taskUserLoginName, userInfo.getUserName(),
						userInfo.getDeptId());
			}
			
		}else if(FinishSubConstants.CHOICE_SIMULATE_AGAIN.equals(choice)
				||FinishSubConstants.CHOICE_DEPT_LEADER_CONCURRENCE.equals(choice)){
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
			
		}
		
		boolean flag=ProcessUtil.launchProcessStep(cname, taskUserLoginName, Integer.parseInt(cincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired(required=false)
	public void setCommonService(@Qualifier(value="receive-commonService")CommonService commonService) {
		this.commonService = commonService;
	}

	public FinishSubApprovedService getFinishSubApprovedService() {
		return finishSubApprovedService;
	}

	@Autowired(required=false)
	public void setFinishSubApprovedService(
			@Qualifier(value="receive-finishSubApprovedService")FinishSubApprovedService finishSubApprovedService) {
		this.finishSubApprovedService = finishSubApprovedService;
	}

	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired(required=false)
	public void setExternalService(@Qualifier("receive-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}

	public OaService getOaService() {
		return oaService;
	}
	
	@Autowired(required=false)
	public void setOaService(@Qualifier("oaService")OaService oaService) {
		this.oaService = oaService;
	}

	public FinishSubFutureService getFinishSubFutureService() {
		return finishSubFutureService;
	}

	@Autowired(required=false)
	public void setFinishSubFutureService(
			@Qualifier("receive-finishSubFutureService")FinishSubFutureService finishSubFutureService) {
		this.finishSubFutureService = finishSubFutureService;
	}
	
	
}
