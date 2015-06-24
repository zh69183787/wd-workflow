/**
 * 
 */
package com.wonders.receive.workflow.process.redv.service.impl;

import com.wonders.receive.workflow.common.model.vo.ResultInfo;
import com.wonders.receive.workflow.common.model.vo.UserInfo;
import com.wonders.receive.workflow.common.service.CommonService;
import com.wonders.receive.workflow.common.util.LoginUtil;
import com.wonders.receive.workflow.constants.UltimusConstants;
import com.wonders.receive.workflow.process.redv.constants.RedvMainConstants;
import com.wonders.receive.workflow.process.redv.constants.RedvMainMessage;
import com.wonders.receive.workflow.process.redv.constants.RedvMainStepConstants;
import com.wonders.receive.workflow.process.redv.dao.RedvMainDao;
import com.wonders.receive.workflow.process.redv.model.bo.RedvMainBo;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainParamVo;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainVo;
import com.wonders.receive.workflow.process.redv.service.RedvMainApprovedService;
import com.wonders.receive.workflow.process.redv.service.RedvMainService;
import com.wonders.receive.workflow.process.redv.util.RedvMainUtil;
import com.wonders.receive.workflow.process.util.ProcessUtil;
import com.wonders.receive.workflow.process.util.TextUtil;
import com.wonders.receive.workflow.util.FlowUtil;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName: RedvMainServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("receive-redvMainService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class RedvMainServiceImpl implements RedvMainService{
	
	private CommonService commonService;
	private RedvMainApprovedService redvMainApprovedService;
	private RedvMainParamVo params;
	private RedvMainVo vo;
	private UserInfo userInfo;
	private RedvMainDao dao;
	private ResultInfo resultInfo;
	
	@Override
	public void init(RedvMainParamVo params) {
		this.params = params;
		this.vo = params.vo;
		this.userInfo = params.userInfo;
		//this.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
		this.resultInfo = params.resultInfo;
	}
	
	
	private RedvMainVo forward(RedvMainParamVo params){
		//init(params);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
//		String cname = params.getProcessParamValue("cname");
//		String cincident = params.getProcessParamValue("cincident");
//		String steplabel = params.getProcessParamValue("steplabel");
		RedvMainVo vo = new RedvMainVo();
		RedvMainBo bo = this.dao.findBoByParam(pname, pincident);
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
	public RedvMainVo flowStepForward(RedvMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public RedvMainVo viewStepForward(RedvMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public RedvMainVo print(RedvMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public void flowStepRegister(RedvMainParamVo params){
		if(resultInfo.getOperateFlag()){
			String pname = UltimusConstants.REDV_DICTIONARY_NAME;
			//init(params);
			RedvMainBo bo = new RedvMainBo();
			try {
				BeanUtils.copyProperties(bo, vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			bo.setOperator(userInfo.getUserName());
			this.commonService.save(bo);
			int incidentNo = flowRegister(vo.getChiefPerson(),bo.getId());
			if(incidentNo > 0){	
				bo.setActiveId(pname);
				bo.setProcessInstanceid(incidentNo+"");
				this.commonService.update(bo);
				params.addProcessParam("pname", pname);
				params.addProcessParam("pincident", incidentNo+"");
				redvMainApprovedService.saveApprovedInfo(params);
			}else{
				resultInfo.addErrors(RedvMainMessage.FAIL_TO_LAUNCH_PROCESS);
				throw new RuntimeException(RedvMainMessage.FAIL_TO_LAUNCH_PROCESS.textCn);
			}
		}
	}
	
	@Override
	public void flowStepLeaderCheck(RedvMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowLeaderCheck()){
				redvMainApprovedService.saveApprovedInfo(params);
			}else{
				
			}
		}
	}
	
	@Override
	public void flowStepRegisterModify(RedvMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowRegisterModify()){
				redvMainApprovedService.saveApprovedInfo(params);
			}else{
				resultInfo.addErrors(RedvMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(RedvMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepRecord(RedvMainParamVo params){	
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowRecord()){
				redvMainApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(RedvMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(RedvMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
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
		RedvMainBo bo = (RedvMainBo) this.commonService.load(tableId, RedvMainBo.class);
		//String steplabel = params.getProcessParamValue("steplabel");
		//String pname = FlowUtil.getCodeName(UltimusConstants.ULTIMUS_DICTIONARY, typeId);
		String pname = UltimusConstants.REDV_DICTIONARY_NAME;
		String summary = pname+" "+bo.getTitle();
		Map<String,Object> nodeMap = 
				FlowUtil.getNodeUsersByConfig(pname, RedvMainStepConstants.STEPNAME_SIMULATE, typeId);
		Map map = new HashMap();
		map.put("业务相关人员帐号", nodeMap.get("username"));
		map.put("业务业务相关人员姓名", nodeMap.get("userfullname"));
		map.put("当前步骤名", RedvMainStepConstants.STEPNAME_SIMULATE);
		map.put("业务表单ID1", "$"+typeId);
		map.put("业务表ID", tableId);
		map.put("最终领导账号", taskUserLoginName);
		map.put("当前处理人部门领导帐号", bo.getNbOpinion());
		map.put("发起人部门ID", userInfo.getDeptId());		
		map.put("短信内容", summary);
		
		int incidentNo = ProcessUtil.launchProcess(pname, taskUserLoginName, summary, map);
		return incidentNo;
	}
	
	
	/** 
	* @Title: flowLeaderCheck 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowLeaderCheck() {
		RedvMainBo bo = (RedvMainBo) this.commonService.load(vo.getId(), RedvMainBo.class);
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = StringUtil.getNotNullValueString(RedvMainUtil.getOptionCode(vo));	
		if(RedvMainConstants.CHOICE_LEADER_SUBMIT_SIMULATE.equals(choice)
				|| RedvMainConstants.CHOICE_LEADER_REGISTER_MODIFY.equals(choice)){
			try {
				BeanUtils.copyProperties(bo, vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			bo.setDeptMaster(userInfo.getUserName()+("["+DateUtil.getCurrDate("yyyy-MM-dd")+"]"));
			this.commonService.update(bo);
		}else if(RedvMainConstants.CHOICE_LEADER_CANCEL_REDV.equals(choice)){
			bo.setFlag("3");
			bo.setRemoved(1);
			bo.setTitle(bo.getTitle()+"<font style='display:inline;color:red;'>（此流程已终止）</font>");
			this.commonService.update(bo);
		}
		String summary = pname+" "+bo.getTitle();
		Map map = new HashMap();
		map.put("当前操作选择", optionCode);
		map.put("当前处理人部门领导帐号", bo.getNbOpinion());
		
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
		RedvMainBo bo = (RedvMainBo) this.commonService.load(vo.getId(), RedvMainBo.class);
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = StringUtil.getNotNullValueString(RedvMainUtil.getOptionCode(vo));
		if(RedvMainConstants.CHOICE_MODIFY_SUBMIT_LEADER.equals(choice)){
			try {
				BeanUtils.copyProperties(bo, vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			bo.setOperator(userInfo.getUserName());
			this.commonService.update(bo);
		}else if(RedvMainConstants.CHOICE_MODIFY_CANCEL_REDV.equals(choice)){
			bo.setFlag("3");
			bo.setRemoved(1);
			bo.setTitle(bo.getTitle()+"<font style='display:inline;color:red;'>（此流程已终止）</font>");
			this.commonService.update(bo);
		}
		String summary = pname+" "+bo.getTitle();
		Map map = new HashMap();
		map.put("当前操作选择", optionCode);
		map.put("当前处理人部门领导帐号", bo.getNbOpinion());
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	
	/** 
	* @Title: flowRecord 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowRecord() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
        RedvMainBo bo = (RedvMainBo) this.commonService.load(vo.getId(), RedvMainBo.class);
        bo.setFlag("1");
        this.commonService.update(bo);
		Map map = new HashMap();
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired(required=false)
	public void setCommonService(@Qualifier(value="receive-commonService")CommonService commonService) {
		this.commonService = commonService;
	}

	public RedvMainApprovedService getRedvMainApprovedService() {
		return redvMainApprovedService;
	}

	@Autowired(required=false)
	public void setRedvMainApprovedService(
			@Qualifier(value="receive-redvMainApprovedService")RedvMainApprovedService redvMainApprovedService) {
		this.redvMainApprovedService = redvMainApprovedService;
	}

	
	public RedvMainDao getDao() {
		return dao;
	}

	@Autowired(required=false)
	public void setDao(@Qualifier("receive-redvMainDao")RedvMainDao dao) {
		this.dao = dao;
	}
	
	
}
