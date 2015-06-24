/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.discipline.workflow.common.model.vo.ResultInfo;
import com.wonders.discipline.workflow.common.model.vo.UserInfo;
import com.wonders.discipline.workflow.common.service.CommonService;
import com.wonders.discipline.workflow.common.util.LoginUtil;
import com.wonders.discipline.workflow.constants.UltimusConstants;
import com.wonders.discipline.workflow.external.service.ExternalService;
import com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainConstants;
import com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainMessage;
import com.wonders.discipline.workflow.process.recv.dao.DcpRecvMainDao;
import com.wonders.discipline.workflow.process.recv.model.bo.DcpRecvMainBo;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainParamVo;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainVo;
import com.wonders.discipline.workflow.process.recv.service.DcpRecvMainApprovedService;
import com.wonders.discipline.workflow.process.recv.service.DcpRecvMainService;
import com.wonders.discipline.workflow.process.recv.service.DcpSimulateFutureService;
import com.wonders.discipline.workflow.process.util.ProcessUtil;
import com.wonders.discipline.workflow.process.util.SerialUtil;
import com.wonders.discipline.workflow.process.util.TextUtil;
import com.wonders.discipline.workflow.util.FlowUtil;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: DcpRecvMainServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("discipline-dcpRecvMainService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DcpRecvMainServiceImpl implements DcpRecvMainService{
	
	private CommonService commonService;
	private DcpRecvMainApprovedService dcpRecvMainApprovedService;
	private DcpSimulateFutureService dcpSimluateFutureService;
	private ExternalService externalService;
	private DcpRecvMainParamVo params;
	private DcpRecvMainVo vo;
	private UserInfo userInfo;
	private DcpRecvMainDao dao;
	private ResultInfo resultInfo;
	
	@Override
	public void init(DcpRecvMainParamVo params) {
		this.params = params;
		this.vo = params.vo;
		this.userInfo = params.userInfo;
		//this.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
		this.resultInfo = params.resultInfo;
	}
	
	
	private DcpRecvMainVo forward(DcpRecvMainParamVo params){
		//init(params);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
//		String cname = params.getProcessParamValue("cname");
//		String cincident = params.getProcessParamValue("cincident");
//		String steplabel = params.getProcessParamValue("steplabel");
		DcpRecvMainVo vo = null;
		DcpRecvMainBo bo = this.dao.findBoByParam(pname, pincident);
		if(bo!=null){
			vo = new DcpRecvMainVo();
			BeanUtils.copyProperties(bo, vo);
		}
		
		return vo;
	}
	
	@Override
	public DcpRecvMainVo flowStepForward(DcpRecvMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public DcpRecvMainVo viewStepForward(DcpRecvMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public DcpRecvMainVo print(DcpRecvMainParamVo params){
		return this.forward(params);
	}
	
	
	@Override
	public void flowStepRegister(DcpRecvMainParamVo params){
		if(resultInfo.getOperateFlag()){
			String pname = UltimusConstants.DISCIPLINE_RECV_DICTIONARY_NAME;
			//init(params);
			DcpRecvMainBo bo = new DcpRecvMainBo();
			BeanUtils.copyProperties(vo, bo);
			bo.setOperator(userInfo.getLogin_Name());
			bo.setOrdinaryDep(userInfo.getDeptName());
			bo.setSwId(StringUtil.getNotNullValueString(vo.getSwId())+"-"+StringUtil.getNotNullValueString(vo.getSwSerial()));
			SerialUtil.delSerialReserve(vo.getSwId(), vo.getSwType(), vo.getChiefPerson(),vo.getSwSerial());					
			this.commonService.save(bo);
			int incidentNo = flowRegister(vo.getChiefPerson(),bo.getId());
			if(incidentNo > 0){	
				bo.setModleId(pname);
				bo.setInstanceId(incidentNo+"");
				bo.setOperateTime(DateUtil.getCurrDate("yyyy-MM-dd"));
				this.commonService.update(bo);
				params.addProcessParam("pname", pname);
				params.addProcessParam("pincident", incidentNo+"");
				dcpRecvMainApprovedService.saveApprovedInfo(params);
				dcpSimluateFutureService.saveFutureInfo(params);
				//resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DcpRecvMainMessage.FAIL_TO_LAUNCH_PROCESS);
				throw new RuntimeException(DcpRecvMainMessage.FAIL_TO_LAUNCH_PROCESS.textCn);
			}
		}
	}
	
	@Override
	public void flowStepDeptLeader(DcpRecvMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowDeptLeader()){
				dcpRecvMainApprovedService.saveApprovedInfo(params);
				dcpSimluateFutureService.saveFutureInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DcpRecvMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DcpRecvMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepRegisterModify(DcpRecvMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowRegisterModify()){
				dcpRecvMainApprovedService.saveApprovedInfo(params);
				dcpSimluateFutureService.saveFutureInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DcpRecvMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DcpRecvMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepPersonDeal(DcpRecvMainParamVo params){	
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowPersonDeal()){
				dcpRecvMainApprovedService.saveApprovedInfo(params);
				dcpSimluateFutureService.saveFutureInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DcpRecvMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DcpRecvMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepSummary(DcpRecvMainParamVo params){	
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowSummary()){
				dcpRecvMainApprovedService.saveApprovedInfo(params);
				dcpSimluateFutureService.saveFutureInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DcpRecvMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DcpRecvMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	
	
	@Override
	public void flowStepDealFinish(DcpRecvMainParamVo params){	
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowDealFinish()){
				dcpRecvMainApprovedService.saveApprovedInfo(params);
				dcpSimluateFutureService.saveFutureInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(DcpRecvMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(DcpRecvMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
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
	private int flowRegister(String typeId, String tableId) {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		//String pname = params.getProcessParamValue("pname");
		//String pincident = params.getProcessParamValue("pincident");
		DcpRecvMainBo bo = (DcpRecvMainBo) this.commonService.load(tableId, DcpRecvMainBo.class);
		//String steplabel = params.getProcessParamValue("steplabel");
		//String pname = FlowUtil.getCodeName(UltimusConstants.ULTIMUS_DICTIONARY, typeId);
		String pname = UltimusConstants.DISCIPLINE_RECV_DICTIONARY_NAME;
		String summary = pname+" "+bo.getSwId()+" "+bo.getTitle();
		
		Map map = new HashMap();
		map.put("纪委收文.部门领导账号", StringUtil.getNotNullValueString(vo.getSimulateLoginName()));
		map.put("纪委收文.发起人账号", StringUtil.getNotNullValueString(taskUserLoginName));
		map.put("纪委收文.汇总人账号", StringUtil.getNotNullValueString(taskUserLoginName));
		
		map.put("纪委收文.部门领导账号拼接", 
				FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(vo.getSimulateLoginName()),
						StringUtil.getNotNullValueString(vo.getSimulateDeptId())));
		map.put("纪委收文.发起人账号拼接", 
				FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(taskUserLoginName),
				StringUtil.getNotNullValueString(userInfo.getDeptId())));
		map.put("纪委收文.汇总人账号拼接", 
				FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(taskUserLoginName),
						StringUtil.getNotNullValueString(userInfo.getDeptId())));
		
		
		map.put("短信内容", summary);
		
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
		DcpRecvMainBo bo = (DcpRecvMainBo) this.commonService.load(vo.getId(), DcpRecvMainBo.class);
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		Map map = new HashMap();
		if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_TWO.equals(choice)
				||DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_ONE.equals(choice)){
			this.vo.initList();
			BeanUtils.copyProperties(vo, bo);
			
			bo.setSwId(StringUtil.getNotNullValueString(vo.getSwId())+"-"+StringUtil.getNotNullValueString(vo.getSwSerial()));
			map.put("纪委收文.是否返回修改", "0");
			FlowUtil.putUltimusMap("纪委收文.集团领导账号",vo.getComLeaderLoginNameList(),map);
			map.put("纪委收文.集团领导账号拼接", 
			FlowUtil.getLoginNameLink(vo.getComLeaderLoginNameList(),vo.getComLeaderDeptIdList()));
			FlowUtil.putUltimusMap("纪委收文.纪委委员账号",vo.getDcpLoginNameList(),map);
			map.put("纪委收文.纪委委员账号拼接", 
			FlowUtil.getLoginNameLink(vo.getDcpLoginNameList(),vo.getDcpDeptIdList()));
			map.put("纪委收文.集团领导数量",vo.getComLeaderLoginNameList().size());
			map.put("纪委收文.纪委委员数量",vo.getDcpLoginNameList().size());
			if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_TWO.equals(choice)){
				map.put("纪委收文.处理方式",2);
			}else if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_ONE.equals(choice)){
				map.put("纪委收文.处理方式",1);
			}
			
		}else if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_BACK.equals(choice)){
			map.put("纪委收文.是否返回修改", "1");
		}
		bo.setOperator(userInfo.getLogin_Name());
		bo.setOrdinaryDep(userInfo.getDeptName());
		bo.setOperateTime(DateUtil.getCurrDate("yyyy-MM-dd"));
		this.commonService.update(bo);
		String summary = pname+" "+bo.getSwId()+" "+bo.getTitle();
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
		DcpRecvMainBo bo = (DcpRecvMainBo) this.commonService.load(vo.getId(), DcpRecvMainBo.class);
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		Map map = new HashMap();
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		if(DcpRecvMainConstants.CHOICE_MODIFY_CANCEL_RECV.equals(choice)){
			map.put("纪委收文.是否取消", "1");
			bo.setFlag("3");
			bo.setRemoved(1);
			bo.setTitle(bo.getTitle()+"<font style='display:inline;color:red;'>（此流程已终止）</font>");
		}else{
			BeanUtils.copyProperties(vo, bo);
						
			bo.setSwId(StringUtil.getNotNullValueString(vo.getSwId())+"-"+StringUtil.getNotNullValueString(vo.getSwSerial()));
			summary = pname+" "+bo.getSwId()+" "+bo.getTitle();
			map.put("纪委收文.是否取消", "0");
			map.put("纪委收文.部门领导账号", StringUtil.getNotNullValueString(vo.getSimulateLoginName()));
			map.put("纪委收文.部门领导账号拼接", 
					FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(vo.getSimulateLoginName()),
							StringUtil.getNotNullValueString(vo.getSimulateDeptId())));
		}
		map.put("短信内容", summary);
		bo.setOperator(userInfo.getLogin_Name());
		bo.setOrdinaryDep(userInfo.getDeptName());
		bo.setOperateTime(DateUtil.getCurrDate("yyyy-MM-dd"));
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
	* @Title: flowSummary 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowSummary() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		this.vo.initList();
		Map map = new HashMap();
		
		if(DcpRecvMainConstants.CHOICE_SUMMARY_PERSON_SUMBIT_DEPT.equals(choice)){
			map.put("纪委收文.是否提交办结人", "0");
			//xieban
			map.put("协办部门数量", vo.getDepsIdList().size());
			FlowUtil.putUltimusMap("协办部门ID", vo.getDepsIdList(),map);
			FlowUtil.putUltimusMap("协办部门名",vo.getDepsNameList(),map);
			FlowUtil.putUltimusMap("部门接收人帐号数组", vo.getDepRecvsIdList(),map);
			FlowUtil.putUltimusMap("部门领导帐号数组", vo.getDepLdsIdList(),map);
			
		}else if(DcpRecvMainConstants.CHOICE_SUMMARY_PERSON_SUMBIT_FINISH.equals(choice)){
			map.put("纪委收文.是否提交办结人", "1");
			map.put("纪委收文.办结人账号", StringUtil.getNotNullValueString(vo.getFinishLoginName()));
			map.put("纪委收文.办结人账号拼接", 
					FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(vo.getFinishLoginName()),
					StringUtil.getNotNullValueString(vo.getFinishDeptId())));
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
		
		if(DcpRecvMainConstants.CHOICE_DEAL_FINISH_OVER.equals(choice)){
			map.put("纪委收文.是否办结", "1");
			//xieban
			
		}else if(DcpRecvMainConstants.CHOICE_DEAL_FINISH_SUMBIT_DEPT.equals(choice)){
			map.put("纪委收文.是否办结", "2");
			map.put("协办部门数量", vo.getDepsIdList().size());
			FlowUtil.putUltimusMap("协办部门ID", vo.getDepsIdList(),map);
			FlowUtil.putUltimusMap("协办部门名",vo.getDepsNameList(),map);
			FlowUtil.putUltimusMap("部门接收人帐号数组", vo.getDepRecvsIdList(),map);
			FlowUtil.putUltimusMap("部门领导帐号数组", vo.getDepLdsIdList(),map);
			
		}else if(DcpRecvMainConstants.CHOICE_DEAL_FINISH_RETURN_SUMMARY_PERSON.equals(choice)){
			map.put("纪委收文.是否办结", "0");
		}
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired(required=false)
	public void setCommonService(@Qualifier(value="discipline-commonService")CommonService commonService) {
		this.commonService = commonService;
	}

	public DcpRecvMainApprovedService getDcpRecvMainApprovedService() {
		return dcpRecvMainApprovedService;
	}

	@Autowired(required=false)
	public void setDcpRecvMainApprovedService(
			@Qualifier(value="discipline-dcpRecvMainApprovedService") DcpRecvMainApprovedService dcpRecvMainApprovedService) {
		this.dcpRecvMainApprovedService = dcpRecvMainApprovedService;
	}

	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired(required=false)
	public void setExternalService(@Qualifier("discipline-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}

	
	public DcpSimulateFutureService getDcpSimluateFutureService() {
		return dcpSimluateFutureService;
	}

	@Autowired(required=false)
	public void setDcpSimluateFutureService(
			@Qualifier(value="discipline-dcpSimulateFutureService") DcpSimulateFutureService dcpSimluateFutureService) {
		this.dcpSimluateFutureService = dcpSimluateFutureService;
	}

	public DcpRecvMainDao getDao() {
		return dao;
	}

	@Autowired(required=false)
	public void setDao(@Qualifier("discipline-dcpRecvMainDao")DcpRecvMainDao dao) {
		this.dao = dao;
	}
	
	
	
}
