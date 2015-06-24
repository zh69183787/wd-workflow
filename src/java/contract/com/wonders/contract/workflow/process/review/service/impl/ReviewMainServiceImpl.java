/**
 * 
 */
package com.wonders.contract.workflow.process.review.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
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
import com.wonders.contract.workflow.constants.UltimusConstants;
import com.wonders.contract.workflow.external.service.ExternalService;
import com.wonders.contract.workflow.process.review.constants.ReviewMainConstants;
import com.wonders.contract.workflow.process.review.constants.ReviewMainMessage;
import com.wonders.contract.workflow.process.review.constants.ReviewMainStepConstants;
import com.wonders.contract.workflow.process.review.dao.ReviewMainDao;
import com.wonders.contract.workflow.process.review.model.bo.ReviewMainBo;
import com.wonders.contract.workflow.process.review.model.vo.ReviewMainParamVo;
import com.wonders.contract.workflow.process.review.model.vo.ReviewMainVo;
import com.wonders.contract.workflow.process.review.service.ReviewMainApprovedService;
import com.wonders.contract.workflow.process.review.service.ReviewMainService;
import com.wonders.contract.workflow.process.util.ProcessUtil;
import com.wonders.contract.workflow.util.FlowUtil;
import com.wonders.contract.workflow.process.util.TextUtil;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: ReviewMainServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("contract-reviewMainService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class ReviewMainServiceImpl implements ReviewMainService{
	
	private CommonService commonService;
	private ReviewMainApprovedService reviewMainApprovedService;
	private ExternalService externalService;
	private ReviewMainParamVo params;
	private ReviewMainVo vo;
	private UserInfo userInfo;
	private ReviewMainDao dao;
	private ResultInfo resultInfo;
	
	@Override
	public void init(ReviewMainParamVo params) {
		this.params = params;
		this.vo = params.vo;
		this.userInfo = params.userInfo;
		//this.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
		this.resultInfo = params.resultInfo;
	}
	
	
	private ReviewMainVo forward(ReviewMainParamVo params){
		//init(params);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
//		String cname = params.getProcessParamValue("cname");
//		String cincident = params.getProcessParamValue("cincident");
//		String steplabel = params.getProcessParamValue("steplabel");
		ReviewMainVo vo = new ReviewMainVo();
		ReviewMainBo bo = this.dao.findBoByParam(pname, pincident);
		try {
			BeanUtils.copyProperties(vo, bo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		
		return vo;
	}
	
	@Override
	public ReviewMainVo flowStepForward(ReviewMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public ReviewMainVo viewStepForward(ReviewMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public ReviewMainVo print(ReviewMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public void flowStepRegister(ReviewMainParamVo params){
		if(resultInfo.getOperateFlag()){
			String pname = UltimusConstants.REVIEW_DICTIONARY_NAME;
			//init(params);
			ReviewMainBo bo = new ReviewMainBo();
			try {
				BeanUtils.copyProperties(bo, vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			this.commonService.save(bo);
			int incidentNo = flowRegister(bo.getId());
			if(incidentNo > 0){	
				bo.setProcess(pname);
				bo.setIncident(incidentNo+"");
				//bo.setRegPerson(userInfo.getUserName());
				bo.setRegLoginName(userInfo.getLoginName());
				bo.setTypeId(UltimusConstants.REVIEW_DICTIONARY_CODE);
				bo.setRegTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
				this.commonService.update(bo);
				params.addProcessParam("pname", pname);
				params.addProcessParam("pincident", incidentNo+"");
				reviewMainApprovedService.saveApprovedInfo(params);
			}else{
				resultInfo.addErrors(ReviewMainMessage.FAIL_TO_LAUNCH_PROCESS);
				throw new RuntimeException(ReviewMainMessage.FAIL_TO_LAUNCH_PROCESS.textCn);
			}
		}
	}
	
	@Override
	public void flowStepModify(ReviewMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowModify()){
				reviewMainApprovedService.saveApprovedInfo(params);
			}else{
				resultInfo.addErrors(ReviewMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(ReviewMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepDeptLeader(ReviewMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowDeptLeader()){
				reviewMainApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(ReviewMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(ReviewMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepContractPreTrial(ReviewMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowContractPreTrial()){
				reviewMainApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(ReviewMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(ReviewMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepContractDealPerson(ReviewMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowContractDealPerson()){
				reviewMainApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(ReviewMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(ReviewMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepContractSimulate(ReviewMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowContractSimulate()){
				reviewMainApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(ReviewMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(ReviewMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepComLeader(ReviewMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowComLeader()){
				reviewMainApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(ReviewMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(ReviewMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepContractFinish(ReviewMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowContractFinish()){
				reviewMainApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(ReviewMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(ReviewMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	
	
	
	
	
	
	

	/** 
	* @Title: flowRegister 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int flowRegister(String tableId) {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		//String pname = params.getProcessParamValue("pname");
		//String pincident = params.getProcessParamValue("pincident");
		ReviewMainBo bo = (ReviewMainBo) this.commonService.load(tableId, ReviewMainBo.class);
		//String steplabel = params.getProcessParamValue("steplabel");
		//String pname = FlowUtil.getCodeName(UltimusConstants.ULTIMUS_DICTIONARY, typeId);
		String pname = UltimusConstants.REVIEW_DICTIONARY_NAME;
		String summary = bo.getContractIdentifier()+" "+bo.getContractName();
		String deptLeader = StringUtil.getNotNullValueString(vo.getDeptLeader());
		Map<String,Object> preMap = 
				FlowUtil.getNodeUsersByConfig(pname, 
						ReviewMainStepConstants.STEPNAME_PRE_TRIAL, 
						UltimusConstants.REVIEW_DICTIONARY_CODE);
		Map<String,Object> simulateMap = 
				FlowUtil.getNodeUsersByConfig(pname, 
						ReviewMainStepConstants.STEPNAME_CONTRACT_SIMULATE, 
						UltimusConstants.REVIEW_DICTIONARY_CODE);
		Map<String,Object> finishMap = 
				FlowUtil.getNodeUsersByConfig(pname, 
						ReviewMainStepConstants.STEPNAME_CONTRACT_FINISH, 
						UltimusConstants.REVIEW_DICTIONARY_CODE);
		
		Map map = new HashMap();
		map.put("后审发起人账号", userInfo.getLoginName());
		map.put("后审发起人账号拼接", FlowUtil.getLoginNameLink(userInfo.getLoginName(),userInfo.getDeptId()));
		map.put("后审申报部门领导账号", deptLeader);
		map.put("后审申报部门领导账号拼接", FlowUtil.getLoginNameLink(deptLeader,userInfo.getDeptId()));
		map.put("后审合约部初审账号", StringUtil.getNotNullValueString(preMap.get("username")));
		map.put("后审合约部初审账号拼接", FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(preMap.get("username")), 
				StringUtil.getNotNullValueString(preMap.get("parent_dept"))));
		map.put("后审合约部拟办账号", StringUtil.getNotNullValueString(simulateMap.get("username")));
		map.put("后审合约部拟办账号拼接", FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(simulateMap.get("username")), 
				StringUtil.getNotNullValueString(simulateMap.get("parent_dept"))));
		map.put("后审合约部办结账号", StringUtil.getNotNullValueString(finishMap.get("username")));
		map.put("后审合约部办结账号拼接", FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(finishMap.get("username")), 
				StringUtil.getNotNullValueString(finishMap.get("parent_dept"))));
		
		int incidentNo = ProcessUtil.launchProcess(pname, taskUserLoginName, summary, map);
		return incidentNo;
	}
	
	
	/** 
	* @Title: flowGetSerialNo 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowModify() {
		ReviewMainBo bo = (ReviewMainBo) this.commonService.load(vo.getId(), ReviewMainBo.class);
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String deptLeader = StringUtil.getNotNullValueString(vo.getDeptLeader());
		Map map = new HashMap();
		if(ReviewMainConstants.CHOICE_MODIFY_SUBMIT_DEPT_LEADER.equals(choice)){
			map.put("后审是否取消", "0");
			map.put("后审申报部门领导账号", deptLeader);
			map.put("后审申报部门领导账号拼接", FlowUtil.getLoginNameLink(deptLeader,userInfo.getDeptId()));
			
			try {
				BeanUtils.copyProperties(bo, vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			bo.setOperateTime(DateUtil.getNowTime());
			this.commonService.update(bo);
		}else{
			map.put("后审是否取消", "1");
			bo.setFlag("3");
			bo.setRemoved(1);
			bo.setContractName(bo.getContractName()+"<font style='display:inline;color:red;'>（此流程已终止）</font>");
			this.commonService.update(bo);
		}
		String summary = bo.getContractIdentifier()+" "+bo.getContractName();
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	
	
	/** 
	* @Title: flowDeptLeader 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowDeptLeader() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		Map map = new HashMap();
		
		if(ReviewMainConstants.CHOICE_DEPT_LEADER_SUBMIT_CONTRACT.equals(choice)){
			map.put("后审是否返回修改", "0");
		}else{
			map.put("后审是否返回修改", "1");
		}
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowContractPreTrial() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		this.vo.initList();
		Map map = new HashMap();
		
		if(ReviewMainConstants.CHOICE_END_PROCESS.equals(choice)){
			map.put("后审直接入库", "1");
			ReviewMainBo bo = (ReviewMainBo) this.commonService.load(vo.getId(), ReviewMainBo.class);
			bo.setFlag("1");
			bo.setOperateTime(DateUtil.getNowTime());
			this.commonService.update(bo);
		}else if(ReviewMainConstants.CHOICE_PRE_TRIAL_SUBMIT_SIMULATE.equals(choice)){
			map.put("后审是否返回修改", "0");
			map.put("后审是否直接拟办", "1");
		}else if(ReviewMainConstants.CHOICE_PRE_TRIAL_BACK_MODIFY.equals(choice)){
			map.put("后审是否返回修改", "1");
			map.put("后审是否直接拟办", "0");
		}else{
			map.put("后审是否返回修改", "0");
			map.put("后审是否直接拟办", "0");
			FlowUtil.putUltimusMap("后审合约部经办人账号",vo.getDealPersonList(),map);
			map.put("后审合约部经办人账号拼接", 
					FlowUtil.getLoginNameLink(vo.getDealPersonList(),userInfo.getDeptId())
					);
		}
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	//合约部业务人员
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowContractDealPerson() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		Map map = new HashMap();
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowContractSimulate() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		this.vo.initList();
		Map map = new HashMap();
		
		if(ReviewMainConstants.CHOICE_SIMULATE_END_PROCESS.equals(choice)){
			map.put("后审是否直接办结", "1");
			map.put("后审是否返回修改", "0");
			ReviewMainBo bo = (ReviewMainBo) this.commonService.load(vo.getId(), ReviewMainBo.class);
			bo.setFlag("1");
			bo.setOperateTime(DateUtil.getNowTime());
			this.commonService.update(bo);
		}else if(ReviewMainConstants.CHOICE_SIMULATE_CHOOSE_LEADER_DEPT.equals(choice)){
			map.put("后审是否直接办结", "0");
			map.put("后审是否返回修改", "0");
			//xieban
			map.put("协办部门数量", vo.getDepsIdList().size());
			FlowUtil.putUltimusMap("协办部门ID", vo.getDepsIdList(),map);
			FlowUtil.putUltimusMap("协办部门名",vo.getDepsNameList(),map);
			FlowUtil.putUltimusMap("部门接收人帐号数组", vo.getDepRecvsIdList(),map);
			FlowUtil.putUltimusMap("部门领导帐号数组", vo.getDepLdsIdList(),map);
			
			//hqld
			map.put("后审集团领导数量", vo.getSignLdsIdList().size());
			FlowUtil.putUltimusMap("后审集团领导账号", vo.getSignLdsIdList(),map);
			map.put("后审集团领导账号拼接", 
					FlowUtil.getLoginNameLink(vo.getSignLdsIdList(), vo.getSignLdsDepIdList()));			
		}else{
			map.put("后审是否直接办结", "0");
			map.put("后审是否返回修改", "1");
		}
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowComLeader() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		Map map = new HashMap();
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowContractFinish() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		this.vo.initList();
		Map map = new HashMap();
		
		if(ReviewMainConstants.CHOICE_FINISH_END_PROCESS.equals(choice)){
			map.put("后审是否重新拟办", "0");
			ReviewMainBo bo = (ReviewMainBo) this.commonService.load(vo.getId(), ReviewMainBo.class);
			bo.setFlag("1");
			bo.setOperateTime(DateUtil.getNowTime());
			this.commonService.update(bo);
		}else{
			map.put("后审是否重新拟办", "1");
			//xieban
			map.put("协办部门数量", vo.getDepsIdList().size());
			FlowUtil.putUltimusMap("协办部门ID", vo.getDepsIdList(),map);
			FlowUtil.putUltimusMap("协办部门名",vo.getDepsNameList(),map);
			FlowUtil.putUltimusMap("部门接收人帐号数组", vo.getDepRecvsIdList(),map);
			FlowUtil.putUltimusMap("部门领导帐号数组", vo.getDepLdsIdList(),map);
			
			//hqld
			map.put("后审集团领导数量", vo.getSignLdsIdList().size());
			FlowUtil.putUltimusMap("后审集团领导账号", vo.getSignLdsIdList(),map);
			map.put("后审集团领导账号拼接", 
					FlowUtil.getLoginNameLink(vo.getSignLdsIdList(), vo.getSignLdsDepIdList()));			

		}
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	
	
	
	
	
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired(required=false)
	public void setCommonService(@Qualifier(value="contract-commonService")CommonService commonService) {
		this.commonService = commonService;
	}

	public ReviewMainApprovedService getReviewMainApprovedService() {
		return reviewMainApprovedService;
	}

	@Autowired(required=false)
	public void setReviewMainApprovedService(
			@Qualifier(value="contract-reviewMainApprovedService")ReviewMainApprovedService reviewMainApprovedService) {
		this.reviewMainApprovedService = reviewMainApprovedService;
	}

	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired(required=false)
	public void setExternalService(@Qualifier("contract-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}

	
	public ReviewMainDao getDao() {
		return dao;
	}

	@Autowired(required=false)
	public void setDao(@Qualifier("contract-reviewMainDao")ReviewMainDao dao) {
		this.dao = dao;
	}
	
	
}
