package com.wonders.contact.deptContact.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.contact.common.model.bo.ResultInfo;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.util.CommonUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.constant.CommonConstants;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.constant.DeptContactMessage;
import com.wonders.contact.deptContact.model.vo.DeptContactMainVo;
import com.wonders.contact.deptContact.model.vo.DeptContactOperateVo;
import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;
import com.wonders.contact.deptContact.service.DeptContactCheckService;
import com.wonders.contact.deptContact.util.ContactDateUtil;
import com.wonders.contact.deptContact.util.FlowUtil;
import com.wonders.contact.deptSubProcess.constant.DeptSubProcessMessage;
import com.wonders.contact.model.bo.MessageBo;
import com.wonders.contact.processController.model.bo.DeptContactResult;
import com.wonders.contact.processController.service.DeptContactController;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
@Service("contact-deptContactCheckService")
@Scope("prototype")
public class DeptContactCheckServiceImpl implements DeptContactCheckService {
	
	private DeptContactController controller;
	
	private ResultInfo resultInfo = new ResultInfo();
	private DeptContactMainVo mainVo;
	private DeptContactOperateVo operateVo;
	private DeptContactParamVo params;
	
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	@Override
	public void init(DeptContactParamVo params) {
		this.params = params;
		if(params!=null){
			this.operateVo = params.operateVo;
			this.mainVo = params.mainVo;
			this.resultInfo = params.resultInfo;
		}
	}
	
	/*流程提交信息检查*/
	
	@Override
	public void checkForward() {
//log.debug("检查流程相关信息");

		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		
		//String steplabel = params.getProcessParamValue("steplabel");
		
		if(isNull(cname)&&isNull(cincident)){
			resultInfo.addErrors(DeptContactMessage.CHECK_WRONG_PROCESS_INFO);
			throw new RuntimeException(DeptContactMessage.CHECK_WRONG_PROCESS_INFO.textCn);
		}
		
		if(cname.equals(pname)&&cincident.equals(pincident)){
//log.debug("多级工作联系单-主流程");		
		}else{
//log.debug("多级工作联系单-子流程");
		}
	}
	
	@Override
	public void checkAdd() {
//log.debug("检查添加信息！");
		CheckMainUnit();
		CheckCopyUnit();
		
		CheckTheme();
		CheckContent();
		
		checkDate();
		
		checkSignLeader();
		//ShowResultInfo();
	}
	
	
	
	@Override
	public void checkUpdate(){
		if(!checkFlowIsInProcess()){
			return;
		}
		
//log.debug("检查修改信息！");
		if(isLowerProcess()){
			checkUpdateLower();
		}else{
			checkUpdateMain();
		}
	}
	
	/**
	 * 修改（主流程）
	 */
	public void checkUpdateMain(){
		CheckMainUnit();
		CheckCopyUnit();
		
		CheckTheme();
		CheckContent();
		
		checkDate();
	}
	
	/**
	 * 修改（下级流程）
	 */
	public void checkUpdateLower(){
		//CheckMainUnit();
		//CheckCopyUnit();
		
		CheckTheme();
		CheckContent();
		
		checkDate();
	}
	
	

	@Override
	public void checkApply() {
//log.debug("检查申请信息！");
		if(!checkFlowIsInProcess()){
			return;
		}
		
		if(isLowerProcess()){
			checkApplyFlowChoiceLower();
		}else{
			checkApplyFlowChoiceMain();
		}
	}
	
	/**
	 * 申请（主流程）
	 */
	public void checkApplyMain(){
		checkApplyFlowChoiceMain();
	}
	
	/**
	 * 申请（下级流程）
	 */
	public void checkApplyLower(){
		checkApplyFlowChoiceLower();
	}
	
	
	@Override
	public void checkSign() {
//log.debug("检查签发信息！");
		if(!checkFlowIsInProcess()){
			return;
		}
		
		if(isLowerProcess()){
			checkSignLower();
		}else{
			checkSignMain();
		}
		
		
		
		
		
	}
	
	/**
	 *签发（主流程）
	 */
	public void checkSignMain() {
		checkSignFlowChoiceMain();
	}
	
	/**
	 * 签发（下级流程）
	 */
	public void checkSignLower() {
		checkSignFlowChoiceLower();
	}
	
	
	/**
	 *检查发起下级流程、子流程人员信息
	 */
	public void checkInitProcess(){
		String mainBoId = operateVo.getId();
		
		DeptContactResult result = controller.getInitProcess(mainBoId);
		
//log.debug(result.lower.deptId.size());
//log.debug(result.lower.deptUser.size());
//log.debug(result.sub.deptId.size());
//log.debug(result.sub.deptUser.size());
		
		List<String> errorLowerList = new ArrayList<String>();
		for(int i=0;i<result.lower.deptId.size();i++){
			String deptName = StringUtil.getNotNullValueString(result.lower.deptName.get(i));
			String receiver = StringUtil.getNotNullValueString(result.lower.deptUser.get(i));
			if("".equals(receiver)){
				errorLowerList.add(deptName);
			}
		}
		if(errorLowerList.size()>0){
			resultInfo.addErrors(new MessageBo("未配置（"+CommonUtil.listToStringBySplit(errorLowerList, "、")+"）下级流程接收人员！请联系管理员。","mainUnit",CommonConstants.MESSAGE_ERROR_PROCESS));
		}
		
		List<String> errorSubList = new ArrayList<String>();
		for(int i=0;i<result.sub.deptId.size();i++){
			String deptName = StringUtil.getNotNullValueString(result.sub.deptName.get(i));
			String receiver = StringUtil.getNotNullValueString(result.sub.deptUser.get(i));
			if("".equals(receiver)){
				errorSubList.add(deptName);
			}
		}
		if(errorSubList.size()>0){
			resultInfo.addErrors(new MessageBo("未配置（"+CommonUtil.listToStringBySplit(errorSubList, "、")+"）部门收发员！请联系管理员。","mainUnit",CommonConstants.MESSAGE_ERROR_PROCESS));
		}
		
		if(result.sub.count==0&&result.lower.count==0){
			resultInfo.addErrors(DeptContactMessage.CHECK_NO_MAINUNIT);
		}
		
		//if(result.resultInfo.getErrors().)
		resultInfo.mergeResultInfo(result.resultInfo);
	}
	
	
	public boolean checkFlowIsInProcess(){
		String processname = params.getProcessParamValue("cname");
		String incident = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String taskuser = params.getProcessParamValue("taskuser");
		
		UserInfo userInfo = params.userInfo;
		Map<String,TaskUserVo> map = userInfo.getMap();
		if(map != null && map.containsKey(taskuser)){
			boolean flag = FlowUtil.checkActiveProcess(processname, incident, steplabel, userInfo);
			
			if(!flag){
				//找不到待办事项信息，操作已完成！
				params.addProcessParam("operateType", "detail");
				resultInfo.addErrors(DeptContactMessage.CHECK_IS_FINISH);
				resultInfo.refresh = true;
			}
			return flag;
		}
		
//System.out.println("checkFlowIsInProcess:"+flag);
		params.addProcessParam("operateType", "detail");
		resultInfo.addErrors(DeptContactMessage.CHECK_IS_FINISH);
		resultInfo.refresh = true;
		return false;
	}
	
	
	
	
	
	
	/*单项信息检查*/
	
	/**
	 * 检查主送部门
	 */
	public void CheckMainUnit(){
		String mainUnitId = StringUtil.getNotNullValueString(mainVo.getMainUnitId());
		String mainUnit = StringUtil.getNotNullValueString(mainVo.getMainUnit());

		
		Map<String,List<String>> map = FlowUtil.filterDeptInfo(mainUnitId, mainUnit, "", "", DeptContactConstants.DEPT_SPLIT_STR);
		
		List<String> mainUnitIds = map.get("mainDeptIdsList");
		List<String> mainUnits = map.get("mainDeptNamesList");
		
		mainVo.setMainUnitId(CommonUtil.listToStringBySplit(mainUnitIds, DeptContactConstants.DEPT_SPLIT_STR));
		mainVo.setMainUnit(CommonUtil.listToStringBySplit(mainUnits, DeptContactConstants.DEPT_SPLIT_STR));
		
		if(mainVo.getMainUnitId().length()==0||mainVo.getMainUnit().length()==0){
			resultInfo.addErrors(DeptContactMessage.CHECK_NO_MAINUNIT);
			return;
		}
		
	}
	
	/**
	 * 检查抄送部门
	 */
	public void CheckCopyUnit(){
		String copyUnitId = StringUtil.getNotNullValueString(mainVo.getCopyUnitId());
		String copyUnit = StringUtil.getNotNullValueString(mainVo.getCopyUnit());
		
		if(copyUnitId.length()==0||copyUnit.length()==0){
			return;
		}
		
		Map<String,List<String>> map = FlowUtil.filterDeptInfo("", "", copyUnitId, copyUnit, DeptContactConstants.DEPT_SPLIT_STR);
		
		List<String> copyUnitIds = map.get("copyDeptIdsList");
		List<String> copyUnits = map.get("copyDeptNamesList");
		
		mainVo.setCopyUnitId(CommonUtil.listToStringBySplit(copyUnitIds, DeptContactConstants.DEPT_SPLIT_STR));
		mainVo.setCopyUnit(CommonUtil.listToStringBySplit(copyUnits, DeptContactConstants.DEPT_SPLIT_STR));
		
	}

	/**
	 * 检查主题
	 */
	public void CheckTheme(){
		mainVo.setTheme(StringUtil.getNotNullValueString(mainVo.getTheme()));
		if(mainVo.getTheme().length()==0){
			resultInfo.addErrors(DeptContactMessage.CHECK_NO_THEME);
			return;
		}
	}
	
	/**
	 * 检查内容
	 */
	public void CheckContent(){
		mainVo.setContent(StringUtil.getNotNullValueString(mainVo.getContent()));
		if(mainVo.getContent().length()==0){
			resultInfo.addErrors(DeptContactMessage.CHECK_NO_CONTENT);
			return;
		}
		
		/*长度限制1000*/
		if(StringUtil.getStrLength(mainVo.getContent())>1000){
			resultInfo.addErrors(DeptContactMessage.CHECK_CONTENT_LIMIT_1000);
			return;
		}
	}
	
	/**
	 * 检查日期及范围
	 */
	public void checkDate(){
		mainVo.setContactDate(StringUtil.getNotNullValueString(mainVo.getContactDate()));
		mainVo.setReplyDate(StringUtil.getNotNullValueString(mainVo.getReplyDate()));
		
		
		
		if(mainVo.getContactDate().length()==0){
			resultInfo.addErrors(DeptContactMessage.CHECK_NO_CONTACTDATE);
		}else{
			if(!ContactDateUtil.checkDateFormat(mainVo.getContactDate(), DateUtil.DATE_FORMAT)){
				resultInfo.addErrors(DeptContactMessage.CHECK_WRONG_CONTACTDATE);
			}
		}
		
		if(mainVo.getReplyDate().length()==0){
			resultInfo.addErrors(DeptContactMessage.CHECK_NO_REPLYDATE);
		}else{
			if(!ContactDateUtil.checkDateFormat(mainVo.getReplyDate(), DateUtil.DATE_FORMAT)){
				resultInfo.addErrors(DeptContactMessage.CHECK_WRONG_REPLYDATE);
			}
		}
		
		if(mainVo.getContactDate().length()>0&&mainVo.getReplyDate().length()>0){
			mainVo.setTimeDiff(ContactDateUtil.DateMinus(mainVo.getReplyDate(), mainVo.getContactDate()));
//log.debug("timeDiff:"+mainVo.getTimeDiff());
			
			if(mainVo.getTimeDiff()<=0){
				resultInfo.addErrors(DeptContactMessage.CHECK_WRONG_DATE_COMPARE);
			}
		}
	}
	
	/**
	 * 检查签发领导
	 */
	public void checkSignLeader(){
		String signLeader = StringUtil.getNotNullValueString(this.params.getParamObject(DeptContactConstants.PARAMS_KEY_SIGN_LEADER));
//log.debug("signLeader="+signLeader);
		if(signLeader.length()==0){
			resultInfo.addErrors(DeptContactMessage.SIGN_NO_LEADER);
			return;
		}
	}
	
	/**
	 * 检查意见
	 */
	public void checkSuggest(){
		if(operateVo.getSuggest().length()==0){
			resultInfo.addErrors(DeptSubProcessMessage.COMMON_NO_SUGGEST);
			return;
		}
	}
	
	
	/*检查流程办理信息*/
	
	/**
	 * 检查申请节点表单（主流程）
	 */
	public void checkApplyFlowChoiceMain(){
		String choice = StringUtil.getNotNullValueString(operateVo.getChoice());
		
		if(!CommonUtil.targetIsInArray(choice, DeptContactConstants.OPTIONS_APPLY_MAIN)){
			resultInfo.addErrors(DeptContactMessage.APPLY_NO_CHOICE);
			return;
		}
		
		if(DeptContactConstants.CHOICE_APPLY_TO_SIGN.equals(choice)){
			checkSignLeader();
		}
	}
	
	/**
	 * 检查申请节点表单（下级流程）
	 */
	public void checkApplyFlowChoiceLower(){
		String choice = StringUtil.getNotNullValueString(operateVo.getChoice());
		
		if(!CommonUtil.targetIsInArray(choice, DeptContactConstants.OPTIONS_APPLY_LOWER)){
			resultInfo.addErrors(DeptContactMessage.APPLY_NO_CHOICE);
			return;
		}
		
		if(DeptContactConstants.CHOICE_APPLY_TO_SIGN.equals(choice)){
			checkSignLeader();
		}
	}
	
	
	
	/**
	 * 检查签发节点表单（主流程）
	 */
	public void checkSignFlowChoiceMain(){
		String choice = StringUtil.getNotNullValueString(operateVo.getChoice());
		
		if(!CommonUtil.targetIsInArray(choice, DeptContactConstants.OPTIONS_SIGN_MAIN)){
			resultInfo.addErrors(DeptContactMessage.SIGN_NO_CHOICE);
		}
		
		//检查流程信息
		if(DeptContactConstants.CHOICE_SIGN_AGREE.equals(choice)){
			checkInitProcess();
		}
	}
	
	/**
	 * 检查签发节点表单（下级流程）
	 */
	public void checkSignFlowChoiceLower(){
		String choice = StringUtil.getNotNullValueString(operateVo.getChoice());
		
		if(!CommonUtil.targetIsInArray(choice, DeptContactConstants.OPTIONS_SIGN_LOWER)){
			resultInfo.addErrors(DeptContactMessage.SIGN_NO_CHOICE);
		}
		
		if(DeptContactConstants.CHOICE_SIGN_FINISH.equals(choice)){
			checkSuggest();
		}
		
		//检查流程信息
		if(DeptContactConstants.CHOICE_SIGN_AGREE.equals(choice)){
			checkInitProcess();
		}
	}
	
	
	/**是否下级流程
	 * @return
	 */
	private boolean isLowerProcess(){
		String flowType = params.getProcessParamValue(DeptContactConstants.PARAMS_FLOW_TYPE);
		if(flowType.equals(DeptContactConstants.STATUS_LOWER_STR)){
			return true;
		}
		return false;
	}
	
	/**判断空字符串
	 * @param str
	 * @return
	 */
	private boolean isNull(String str){
		if(StringUtil.getNotNullValueString(str).length()==0) return true;
		return false;
	}

	
	
	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

	public DeptContactMainVo getMainVo() {
		return mainVo;
	}

	public void setMainVo(DeptContactMainVo mainVo) {
		this.mainVo = mainVo;
	}

	public DeptContactParamVo getParams() {
		return params;
	}
	
	public void setParams(DeptContactParamVo params) {
		this.params = params;
	}

	public DeptContactOperateVo getOperateVo() {
		return operateVo;
	}

	public void setOperateVo(DeptContactOperateVo operateVo) {
		this.operateVo = operateVo;
	}

	public DeptContactController getController() {
		return controller;
	}
	
	@Autowired
	public void setController(@Qualifier("contact-deptContactController")DeptContactController controller) {
		this.controller = controller;
	}


}
