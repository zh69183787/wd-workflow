/**
 * 
 */
package com.wonders.project.workflow.process.discard.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.project.workflow.common.model.vo.ResultInfo;
import com.wonders.project.workflow.common.model.vo.UserInfo;
import com.wonders.project.workflow.common.service.CommonService;
import com.wonders.project.workflow.common.util.LoginUtil;
import com.wonders.project.workflow.constants.UltimusConstants;
import com.wonders.project.workflow.external.service.ExternalService;
import com.wonders.project.workflow.process.discard.constants.DiscardMainConstants;
import com.wonders.project.workflow.process.discard.constants.DiscardMainMessage;
import com.wonders.project.workflow.process.discard.constants.DiscardMainStepConstants;
import com.wonders.project.workflow.process.discard.dao.DiscardMainDao;
import com.wonders.project.workflow.process.discard.model.bo.DiscardAssetBo;
import com.wonders.project.workflow.process.discard.model.bo.DiscardMainBo;
import com.wonders.project.workflow.process.discard.model.vo.DiscardMainParamVo;
import com.wonders.project.workflow.process.discard.model.vo.DiscardMainVo;
import com.wonders.project.workflow.process.discard.service.DiscardMainApprovedService;
import com.wonders.project.workflow.process.discard.service.DiscardMainMessageService;
import com.wonders.project.workflow.process.discard.service.DiscardMainService;
import com.wonders.project.workflow.process.util.ProcessUtil;
import com.wonders.project.workflow.process.util.TextUtil;
import com.wonders.project.workflow.util.FlowUtil;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: DiscardMainServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("project-discardMainService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DiscardMainServiceImpl implements DiscardMainService{
	
	private CommonService commonService;
	private DiscardMainApprovedService discardMainApprovedService;
	private DiscardMainMessageService discardMainMessageService;
	//private DcpSimulateFutureService dcpSimluateFutureService;
	private ExternalService externalService;
	private DiscardMainParamVo params;
	private DiscardMainVo vo;
	private UserInfo userInfo;
	private DiscardMainDao dao;
	private ResultInfo resultInfo;
	
	@Override
	public void init(DiscardMainParamVo params) {
		this.params = params;
		this.vo = params.vo;
		this.userInfo = params.userInfo;
		//this.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
		this.resultInfo = params.resultInfo;
	}
	
	
	private DiscardMainVo forward(DiscardMainParamVo params){
		//init(params);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
//		String cname = params.getProcessParamValue("cname");
//		String cincident = params.getProcessParamValue("cincident");
//		String steplabel = params.getProcessParamValue("steplabel");
		DiscardMainVo vo = new DiscardMainVo();
		pname = "项目销项流程";
		DiscardMainBo bo = this.dao.findBoByParam(pname, pincident);
		BeanUtils.copyProperties(bo, vo);
		
		
		return vo;
	}
	
	@Override
	public DiscardMainVo flowStepForward(DiscardMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public DiscardMainVo viewStepForward(DiscardMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public DiscardMainVo print(DiscardMainParamVo params){
		return this.forward(params);
	}
	
	
	@Override
	public void flowStepRegister(DiscardMainParamVo params){
		if(resultInfo.getOperateFlag()){
			String pname = UltimusConstants.PROJECT_DISCARD_DICTIONARY_NAME;
			//init(params);
			DiscardMainBo bo = new DiscardMainBo();
			BeanUtils.copyProperties(vo, bo);
			bo.setOperator(userInfo.getLogin_Name());
			bo.setOperatorName(userInfo.getUserName());
			this.commonService.save(bo);
			
			List<DiscardAssetBo> assets = vo.getAssets();
			if(assets != null && assets.size() > 0){
				for(DiscardAssetBo asset : assets){
					asset.setMainId(bo.getId());
				}
				this.commonService.saveBatch(assets);
			}
			
			int incidentNo = flowRegister(bo.getId());
			if(incidentNo > 0){	
				bo.setModleId(pname);
				bo.setInstanceId(incidentNo+"");
				bo.setOperateTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
				this.commonService.update(bo);
				params.addProcessParam("pname", pname);
				params.addProcessParam("pincident", incidentNo+"");
				discardMainApprovedService.saveApprovedInfo(params);
				//dcpSimluateFutureService.saveFutureInfo(params);
				//resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DiscardMainMessage.FAIL_TO_LAUNCH_PROCESS);
				throw new RuntimeException(DiscardMainMessage.FAIL_TO_LAUNCH_PROCESS.textCn);
			}
		}
	}
	
	@Override
	public void flowStepDeptLeader(DiscardMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowDeptLeader()){
				discardMainApprovedService.saveApprovedInfo(params);
				//dcpSimluateFutureService.saveFutureInfo(params);
				if(!DiscardMainConstants.CHOICE_DEPT_LEADER_REGISTER_CANCEL.equals(vo.getChoice())){
					resultInfo.url = TextUtil.generateMainUrl(params);	
				}
			}else{
				resultInfo.addErrors(DiscardMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DiscardMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepRegisterModify(DiscardMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowRegisterModify()){
				discardMainApprovedService.saveApprovedInfo(params);
				//dcpSimluateFutureService.saveFutureInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DiscardMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DiscardMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepPersonDeal(DiscardMainParamVo params){	
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowPersonDeal()){
				discardMainApprovedService.saveApprovedInfo(params);
				//dcpSimluateFutureService.saveFutureInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DiscardMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DiscardMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepPreTrial(DiscardMainParamVo params){	
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowPreTrial()){
				discardMainApprovedService.saveApprovedInfo(params);
				//dcpSimluateFutureService.saveFutureInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DiscardMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DiscardMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	
	
	@Override
	public void flowStepDealFinish(DiscardMainParamVo params){	
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowDealFinish()){
				discardMainApprovedService.saveApprovedInfo(params);
				//dcpSimluateFutureService.saveFutureInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DiscardMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DiscardMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
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
		DiscardMainBo bo = (DiscardMainBo) this.commonService.load(tableId, DiscardMainBo.class);
		String pname = UltimusConstants.PROJECT_DISCARD_DICTIONARY_NAME;
		String summary = pname+" "+bo.getProjectName();
		
		Map map = new HashMap();
		map.put("项目销项.发起人账号", StringUtil.getNotNullValueString(taskUserLoginName));
		map.put("项目销项.发起部门领导账号", StringUtil.getNotNullValueString(vo.getDeptLeader()));

		map.put("项目销项.发起人账号拼接", 
				FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(taskUserLoginName),
						StringUtil.getNotNullValueString(userInfo.getDeptId())));
		map.put("项目销项.发起部门领导账号拼接", 
				FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(vo.getDeptLeader()),
						StringUtil.getNotNullValueString(userInfo.getDeptId())));
		
		Map<String,Object> preMap = 
				FlowUtil.getNodeUsersByConfig(pname, 
						DiscardMainStepConstants.STEPNAME_TH_RECV, 
						UltimusConstants.PROJECT_DISCARD_DICTIONARY_CODE);
		map.put("项目销项.投资部收发员账号", StringUtil.getNotNullValueString(preMap.get("username")));
		map.put("项目销项.投资部收发员账号拼接", 
				FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(preMap.get("username")),
						StringUtil.getNotNullValueString(preMap.get("parent_dept"))));
		
		//map.put("短信内容", summary);
		
		int incidentNo = ProcessUtil.launchProcess(pname, taskUserLoginName, summary, map);
		return incidentNo;
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
		DiscardMainBo bo = (DiscardMainBo) this.commonService.load(vo.getId(), DiscardMainBo.class);
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		Map map = new HashMap();

		map.put("项目销项.退回或者结束流程", choice);
		
		if(DiscardMainConstants.CHOICE_DEPT_LEADER_REGISTER_CANCEL.equals(choice)){
			bo.setFlag("3");
			bo.setRemoved(1);
			bo.setProjectName(bo.getProjectName()+"<font style='display:inline;color:red;'>（此流程已终止）</font>");
			this.commonService.update(bo);
		}

		String summary =  FlowUtil.getSummaryByProcessInfo(pname, pincident);
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	
	/** 
	* @Title: flowRegisterModify 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowRegisterModify() {
		DiscardMainBo bo = (DiscardMainBo) this.commonService.load(vo.getId(), DiscardMainBo.class);
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		Map map = new HashMap();
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		
		map.put("项目销项.销项是否取消", choice);
		if(DiscardMainConstants.CHOICE_MODIFY_CANCEL.equals(choice)){
			bo.setFlag("3");
			bo.setRemoved(1);
			bo.setProjectName(bo.getProjectName()+"<font style='display:inline;color:red;'>（此流程已终止）</font>");
		}else{
			BeanUtils.copyProperties(vo, bo);
						
			summary = pname+" "+bo.getProjectName();
			map.put("项目销项.发起部门领导账号", StringUtil.getNotNullValueString(vo.getDeptLeader()));
			map.put("纪委收文.发起部门领导账号拼接", 
					FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(vo.getDeptLeader()),
							StringUtil.getNotNullValueString(userInfo.getDeptId())));
		}
		map.put("短信内容", summary);
		bo.setOperator(userInfo.getLogin_Name());
		bo.setOperatorName(userInfo.getUserName());
		bo.setOperateTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		this.commonService.update(bo);
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	
	/** 
	* @Title: flowPersonDeal 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowPersonDeal() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		
		Map map = new HashMap();
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	/** 
	* @Title: flowPreTrial
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowPreTrial() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		this.vo.initList();
		Map map = new HashMap();
		
		map.put("项目销项.退回或者结束流程", choice);
		map.put("项目销项.投资部领导账号", vo.getDealLeaderStr());
		map.put("项目销项.投资部领导账号拼接", 
				FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(vo.getDealLeaderStr()),
						StringUtil.getNotNullValueString(userInfo.getDeptId())));

		map.put("协办部门数量", vo.getDepsIdList().size());
		FlowUtil.putUltimusMap("协办部门ID", vo.getDepsIdList(),map);
		FlowUtil.putUltimusMap("协办部门名",vo.getDepsNameList(),map);
		FlowUtil.putUltimusMap("部门接收人帐号数组", vo.getDepRecvsIdList(),map);
		FlowUtil.putUltimusMap("部门领导帐号数组", vo.getDepLdsIdList(),map);
		
		if(DiscardMainConstants.CHOICE_PRETRIAL_SUMBIT_DEPT.equals(choice)){
			//xieban
		}else if(DiscardMainConstants.CHOICE_PRETRIAL_SUMBIT_OPERATOR.equals(choice)){
			FlowUtil.putUltimusMap("项目销项.投资部经办人账号数组",vo.getDealPersonList(),map);
			map.put("项目销项.投资部经办人账号拼接", 
					FlowUtil.getLoginNameLink(vo.getDealPersonList(),userInfo.getDeptId()));
		}
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	
	/** 
	* @Title: flowDealFinish 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowDealFinish() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		this.vo.initList();
		Map map = new HashMap();
		
		map.put("项目销项.退回或者结束流程", choice);
		
		if(DiscardMainConstants.CHOICE_DEAL_FINISH_OVER.equals(choice)){
			DiscardMainBo bo = (DiscardMainBo) this.commonService.load(vo.getId(), DiscardMainBo.class);
			bo.setFlag("1");
			bo.setOperateTime(DateUtil.getNowTime());
			this.commonService.update(bo);
			
			doOnFinish(bo);
		}else if(DiscardMainConstants.CHOICE_DEAL_FINISH_SUMBIT_DEPT.equals(choice)){
			map.put("协办部门数量", vo.getDepsIdList().size());
			FlowUtil.putUltimusMap("协办部门ID", vo.getDepsIdList(),map);
			FlowUtil.putUltimusMap("协办部门名",vo.getDepsNameList(),map);
			FlowUtil.putUltimusMap("部门接收人帐号数组", vo.getDepRecvsIdList(),map);
			FlowUtil.putUltimusMap("部门领导帐号数组", vo.getDepLdsIdList(),map);			
		}
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	private void doOnFinish(DiscardMainBo bo){
		this.discardMainMessageService.sendMessageOnFinish(bo,userInfo);
	}
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired(required=false)
	public void setCommonService(@Qualifier(value="project-commonService")CommonService commonService) {
		this.commonService = commonService;
	}

	public DiscardMainApprovedService getDiscardMainApprovedService() {
		return discardMainApprovedService;
	}

	@Autowired(required=false)
	public void setDiscardMainApprovedService(
			@Qualifier(value="project-discardMainApprovedService") DiscardMainApprovedService discardMainApprovedService) {
		this.discardMainApprovedService = discardMainApprovedService;
	}
	
	public DiscardMainMessageService getDiscardMainMessageService() {
		return discardMainMessageService;
	}

	@Autowired(required=false)
	public void setDiscardMainMessageService(
			@Qualifier(value="project-discardMainMessageService") DiscardMainMessageService discardMainMessageService) {
		this.discardMainMessageService = discardMainMessageService;
	}

	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired(required=false)
	public void setExternalService(@Qualifier("project-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}

	public DiscardMainDao getDao() {
		return dao;
	}

	@Autowired(required=false)
	public void setDao(@Qualifier("project-discardMainDao")DiscardMainDao dao) {
		this.dao = dao;
	}
	
	
	
}
