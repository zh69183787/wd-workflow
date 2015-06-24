/**
 * 
 */
package com.wonders.receive.workflow.process.instruct.service.impl;

import java.util.HashMap;
import java.util.Map;

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
import com.wonders.receive.workflow.constants.CommonConstants;
import com.wonders.receive.workflow.external.service.ExternalService;
import com.wonders.receive.workflow.process.instruct.constants.InstructSubConstants;
import com.wonders.receive.workflow.process.instruct.constants.InstructSubMessage;
import com.wonders.receive.workflow.process.instruct.constants.InstructSubStepConstants;
import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubParamVo;
import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubVo;
import com.wonders.receive.workflow.process.instruct.service.InstructSubApprovedService;
import com.wonders.receive.workflow.process.instruct.service.InstructSubService;
import com.wonders.receive.workflow.process.instruct.util.InstructSubUtil;
import com.wonders.receive.workflow.process.util.ProcessUtil;
import com.wonders.receive.workflow.process.util.TextUtil;
import com.wonders.receive.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: InstructSubServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-3-18 上午09:31:10 
 *  
 */

@Service("receive-instructSubService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class InstructSubServiceImpl implements InstructSubService{

	private CommonService commonService;
	private InstructSubApprovedService instructSubApprovedService;
	private ExternalService externalService;
	private InstructSubParamVo params;
	private InstructSubVo vo;
	private UserInfo userInfo;
	private ResultInfo resultInfo;
	
	@Override
	public void init(InstructSubParamVo params) {
		this.params = params;
		this.vo = params.vo;
		this.userInfo = params.userInfo;
		//this.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
		this.resultInfo = params.resultInfo;
	}
	
	@Override
	public void flowStepSecretaryDeal(InstructSubParamVo params) {
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowSecretaryDeal()){
				instructSubApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(InstructSubMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(InstructSubMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}

	
	@Override
	public void flowStepLeaderDeal(InstructSubParamVo params) {
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowLeaderDeal()){
				instructSubApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(InstructSubMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(InstructSubMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean flowSecretaryDeal() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		//String pname = params.getProcessParamValue("pname");
		//String pincident = params.getProcessParamValue("pincident");
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(cname, cincident);
		Map map = new HashMap();
		
		String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());
		String type = StringUtil.getNotNullValueString(vo.getType());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = StringUtil.getNotNullValueString(InstructSubUtil.getOptionCode(vo));
		Long disAgree = InstructSubUtil.getDisAgree(vo);
		String sign = StringUtil.getNotNullValueString(InstructSubUtil.getSign(vo));
		String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		if(InstructSubStepConstants.STEPNAME_SECRETARY_DEAL.equals(steplabel) &&
				InstructSubConstants.SELF_DEAL.equals(type)	&&
				!(InstructSubConstants.CHOICE_SECRETARY_BACK_OFFICE.equals(choice))){
			suggest = StringUtil.getNotNullValueString(suggest);
		}else{
			suggest = StringUtil.getNotNullValueString(InstructSubUtil.getSign(vo)+suggest);
		}
		
		
		if(InstructSubConstants.SELF_DEAL.equals(type)){
			map.put("操作按钮", sign);
			map.put("是否同意操作", disAgree+"");
			map.put("当前人的名字", userInfo.getUserName());
			map.put("部门编号", userInfo.getDeptId());
			map.put("附件值", fileGroupId);
			map.put("部门名称", userInfo.getDeptName());
			map.put("结点名称", steplabel);
			if(InstructSubConstants.CHOICE_SECRETARY_BACK_OFFICE.equals(choice)){
				String info = ProcessUtil.getLeaderInfo(cname, cincident);
				if(info.length() > 0 && (info.split(",").length>0)){
					taskUserLoginName = CommonConstants.LOGINNAME_PREFIX + info.split(",")[0];
				}	
				steplabel = ProcessUtil.getStepInfo(taskUserLoginName, cname, cincident);
				
				map.put("意见", suggest);
				map.put("是否返回秘书", 8);				
				map.put("选项代码", optionCode);
				map.put("流程的实例号", cincident);
				map.put("流程的名称", cname);
			}else{
				map.put("意见", suggest);
				map.put("流程的实例号", cincident);
				map.put("流程的名称", cname);
				map.put("是否返回秘书", 0);
			}
			
		}else if(InstructSubConstants.INSTEAD_DEAL.equals(type)){
			String info = ProcessUtil.getLeaderInfo(cname, cincident);
			if(info.length() > 0 && (info.split(",").length>0)){
				taskUserLoginName = CommonConstants.LOGINNAME_PREFIX + info.split(",")[0];
			}	
			steplabel = ProcessUtil.getStepInfo(taskUserLoginName, cname, cincident);
			
			map.put("是否返回秘书", 0);
		}
		
		
		
		boolean flag=ProcessUtil.launchProcessStep(cname, taskUserLoginName, Integer.parseInt(cincident), steplabel, summary, "", map);
		return flag;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean flowLeaderDeal() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		this.vo.initList();
		String summary = FlowUtil.getSummaryByProcessInfo(cname, cincident);	
		Map map = new HashMap();
		
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		if(InstructSubConstants.CHOICE_LEADER_BACK_SECRETARY.equals(choice)){
			map.put("是否返回秘书", 1);
		}else{
			map.put("是否返回秘书", 0);
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

	public InstructSubApprovedService getInstructSubApprovedService() {
		return instructSubApprovedService;
	}

	@Autowired(required=false)
	public void setInstructSubApprovedService(
			@Qualifier(value="receive-instructSubApprovedService")InstructSubApprovedService instructSubApprovedService) {
		this.instructSubApprovedService = instructSubApprovedService;
	}

	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired(required=false)
	public void setExternalService(@Qualifier("receive-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}

}
