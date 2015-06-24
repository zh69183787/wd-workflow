/**
 * 
 */
package com.wonders.receive.workflow.process.recv.service.impl;

import com.wonders.receive.workflow.common.model.vo.ResultInfo;
import com.wonders.receive.workflow.common.model.vo.UserInfo;
import com.wonders.receive.workflow.common.service.CommonService;
import com.wonders.receive.workflow.common.util.LoginUtil;
import com.wonders.receive.workflow.constants.UltimusConstants;
import com.wonders.receive.workflow.external.service.ExternalService;
import com.wonders.receive.workflow.process.recv.constants.RecvMainConstants;
import com.wonders.receive.workflow.process.recv.constants.RecvMainMessage;
import com.wonders.receive.workflow.process.recv.constants.RecvMainStepConstants;
import com.wonders.receive.workflow.process.recv.dao.RecvMainDao;
import com.wonders.receive.workflow.process.recv.model.bo.RecvMainBo;
import com.wonders.receive.workflow.process.recv.model.vo.RecvMainParamVo;
import com.wonders.receive.workflow.process.recv.model.vo.RecvMainVo;
import com.wonders.receive.workflow.process.recv.service.RecvMainApprovedService;
import com.wonders.receive.workflow.process.recv.service.RecvMainService;
import com.wonders.receive.workflow.process.recv.util.RecvMainUtil;
import com.wonders.receive.workflow.process.util.ProcessUtil;
import com.wonders.receive.workflow.process.util.SerialUtil;
import com.wonders.receive.workflow.process.util.TextUtil;
import com.wonders.receive.workflow.util.FlowUtil;
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
 * @ClassName: RecvMainServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("receive-recvMainService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class RecvMainServiceImpl implements RecvMainService{
	
	private CommonService commonService;
	private RecvMainApprovedService recvMainApprovedService;
	private ExternalService externalService;
	private RecvMainParamVo params;
	private RecvMainVo vo;
	private UserInfo userInfo;
	private RecvMainDao dao;
	private ResultInfo resultInfo;
	
	@Override
	public void init(RecvMainParamVo params) {
		this.params = params;
		this.vo = params.vo;
		this.userInfo = params.userInfo;
		//this.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
		this.resultInfo = params.resultInfo;
	}
	
	
	private RecvMainVo forward(RecvMainParamVo params){
		//init(params);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
//		String cname = params.getProcessParamValue("cname");
//		String cincident = params.getProcessParamValue("cincident");
//		String steplabel = params.getProcessParamValue("steplabel");
		RecvMainVo vo = new RecvMainVo();
		RecvMainBo bo = this.dao.findBoByParam(pname, pincident);
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
	public RecvMainVo flowStepForward(RecvMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public RecvMainVo viewStepForward(RecvMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public RecvMainVo print(RecvMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public void flowStepRegister(RecvMainParamVo params){
		if(resultInfo.getOperateFlag()){
			String pname = UltimusConstants.RECV_DICTIONARY_NAME;
			//init(params);
			RecvMainBo bo = new RecvMainBo();
			try {
				BeanUtils.copyProperties(bo, vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			bo.setOperator(userInfo.getLogin_Name());
			bo.setOrdinaryDep(userInfo.getDeptName());
			this.commonService.save(bo);
			int incidentNo = flowRegister(vo.getChiefPerson(),bo.getId());
			if(incidentNo > 0){	
				bo.setModleId(pname);
				bo.setInstanceId(incidentNo+"");
				this.commonService.update(bo);
				params.addProcessParam("pname", pname);
				params.addProcessParam("pincident", incidentNo+"");
				recvMainApprovedService.saveApprovedInfo(params);
			}else{
				resultInfo.addErrors(RecvMainMessage.FAIL_TO_LAUNCH_PROCESS);
				throw new RuntimeException(RecvMainMessage.FAIL_TO_LAUNCH_PROCESS.textCn);
			}
		}
	}
	
	@Override
	public void flowStepGetSerialNo(RecvMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowGetSerialNo()){
				recvMainApprovedService.saveApprovedInfo(params);
			}else{
				
			}
		}
	}
	
	@Override
	public void flowStepRegisterModify(RecvMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowRegisterModify()){
				recvMainApprovedService.saveApprovedInfo(params);
			}else{
				resultInfo.addErrors(RecvMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(RecvMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepRecord(RecvMainParamVo params){	
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowRecord()){
				recvMainApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(RecvMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(RecvMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
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
		RecvMainBo bo = (RecvMainBo) this.commonService.load(tableId, RecvMainBo.class);
		//String steplabel = params.getProcessParamValue("steplabel");
		//String pname = FlowUtil.getCodeName(UltimusConstants.ULTIMUS_DICTIONARY, typeId);
		String pname = UltimusConstants.RECV_DICTIONARY_NAME;
		String summary = pname+" "+bo.getSwId()+" "+bo.getTitle();
		Map<String,Object> nodeMap = 
				FlowUtil.getNodeUsersByConfig(pname, RecvMainStepConstants.STEPNAME_GET_SERIALNO, typeId);
		Map map = new HashMap();
		map.put("最终领导秘书账号", nodeMap.get("username"));
		map.put("业务表单ID1", typeId);
		map.put("业务表ID", tableId);
		map.put("最终领导账号", taskUserLoginName);
		map.put("发起人部门ID", userInfo.getDeptId());
		map.put("最终领导姓名", userInfo.getUserName());
		map.put("短信内容", summary);
		
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
	private boolean flowGetSerialNo() {
		RecvMainBo bo = (RecvMainBo) this.commonService.load(vo.getId(), RecvMainBo.class);
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = StringUtil.getNotNullValueString(RecvMainUtil.getOptionCode(vo));	
		if(RecvMainConstants.CHOICE_SERIALNO_SUBMIT_SIMULATE.equals(choice)){
			try {
				BeanUtils.copyProperties(bo, vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			bo.setOperator(userInfo.getLogin_Name());
			bo.setSwId(StringUtil.getNotNullValueString(vo.getSwId())+"-"+StringUtil.getNotNullValueString(vo.getSwSerial()));
			this.commonService.update(bo);
			//删除预留号码
			SerialUtil.delSerialReserve(vo.getSwId(), vo.getSwType(), vo.getChiefPerson(),vo.getSwSerial());			
		}
		String summary = pname+" "+bo.getSwId()+" "+bo.getTitle();
		Map map = new HashMap();
		map.put("当前操作选择", optionCode);
		
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
		RecvMainBo bo = (RecvMainBo) this.commonService.load(vo.getId(), RecvMainBo.class);
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = StringUtil.getNotNullValueString(RecvMainUtil.getOptionCode(vo));
		if(RecvMainConstants.CHOICE_MODIFY_SUBMIT_SERIALNO.equals(choice)){
			try {
				BeanUtils.copyProperties(bo, vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			bo.setOperator(userInfo.getLogin_Name());
			this.commonService.update(bo);
		}else{
			bo.setFlag("3");
			bo.setRemoved(1);
			bo.setTitle(bo.getTitle()+"<font style='display:inline;color:red;'>（此流程已终止）</font>");
			this.commonService.update(bo);
		}
		String summary = pname+" "+bo.getSwId()+" "+bo.getTitle();
		Map map = new HashMap();
		map.put("当前操作选择", optionCode);
		
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
        RecvMainBo bo = (RecvMainBo) this.commonService.load(vo.getId(), RecvMainBo.class);
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

	public RecvMainApprovedService getRecvMainApprovedService() {
		return recvMainApprovedService;
	}

	@Autowired(required=false)
	public void setRecvMainApprovedService(
			@Qualifier(value="receive-recvMainApprovedService")RecvMainApprovedService recvMainApprovedService) {
		this.recvMainApprovedService = recvMainApprovedService;
	}

	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired(required=false)
	public void setExternalService(@Qualifier("receive-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}

	
	public RecvMainDao getDao() {
		return dao;
	}

	@Autowired(required=false)
	public void setDao(@Qualifier("receive-recvMainDao")RecvMainDao dao) {
		this.dao = dao;
	}
	
	
}
