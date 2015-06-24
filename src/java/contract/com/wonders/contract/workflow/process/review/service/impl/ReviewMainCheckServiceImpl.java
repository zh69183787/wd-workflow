/**
 * 
 */
package com.wonders.contract.workflow.process.review.service.impl;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.contract.workflow.common.model.vo.ResultInfo;
import com.wonders.contract.workflow.common.model.vo.UserInfo;
import com.wonders.contract.workflow.process.review.constants.ReviewMainConstants;
import com.wonders.contract.workflow.process.review.constants.ReviewMainMessage;
import com.wonders.contract.workflow.process.review.model.vo.ReviewMainParamVo;
import com.wonders.contract.workflow.process.review.model.vo.ReviewMainVo;
import com.wonders.contract.workflow.process.review.service.ReviewMainCheckService;
import com.wonders.contract.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;
/** 
 * @ClassName: ReviewMainServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("contract-reviewMainCheckService")
@Scope("prototype")
public class ReviewMainCheckServiceImpl implements ReviewMainCheckService{
	private ResultInfo resultInfo = new ResultInfo();
	private ReviewMainParamVo params;
	private ReviewMainVo mainVo;
	
	@Override
	public void init(ReviewMainParamVo params) {
		this.params = params;
		if(params!=null){
			this.mainVo = params.vo;
			this.resultInfo = params.resultInfo;
		}
		
	}
	
	public boolean checkFlowIsInProcess(){
		String processname = params.getProcessParamValue("cname");
		String incident = params.getProcessParamValue("cincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String taskuser = params.getProcessParamValue("taskuser");
		
		UserInfo userInfo = params.userInfo;
		Map<String,TaskUserVo> map = userInfo.getMap();
		if(map != null && map.containsKey(taskuser)){
			boolean flag = FlowUtil.checkActiveProcess(processname, incident, steplabel, taskuser);
			if(!flag){
				//找不到待办事项信息，操作已完成！
				params.addProcessParam("operateType", "view");
			}
			return flag;
		}
		params.addProcessParam("operateType", "view");
		return false;
		
		
	}
	
	@Override
	public void checkForward() {
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		
		//String steplabel = params.getProcessParamValue("steplabel");
		
		if(isNull(cname)&&isNull(cincident)){
			resultInfo.addErrors(ReviewMainMessage.CHECK_WRONG_PROCESS_INFO);
			throw new RuntimeException(ReviewMainMessage.CHECK_WRONG_PROCESS_INFO.textCn);
		}
		
		if(cname.equals(pname)&&cincident.equals(pincident)){
	
		}else{

		}
	}
	
	public void checkRegister(){
		CheckProjectInfo();
		CheckContractIdentifier();
		CheckContractSelfNumRegister();
		CheckContractName();
		CheckContractMoney();
		CheckContractMoneyType();
		CheckContractBudget();
		CheckPurchaseType();
		CheckContractType1();
		CheckContractType2();
		CheckContractGroup();
		CheckOppositeCompany();
		CheckDealPerson();
		CheckRegPerson();
		CheckPassTime();
		CheckSignTime();
		CheckExecPeriodStart();
		CheckExecPeriodEnd();
		CheckAttach();
	}
	
	public void checkModify(){
		if(ReviewMainConstants.CHOICE_MODIFY_SUBMIT_DEPT_LEADER.equals(mainVo.getChoice())){
			CheckProjectInfo();
			CheckContractIdentifier();
			CheckContractSelfNumModify();
			CheckContractName();
			CheckContractMoney();
			CheckContractMoneyType();
			CheckContractBudget();
			CheckPurchaseType();
			CheckContractType1();
			CheckContractType2();
			CheckContractGroup();
			CheckOppositeCompany();
			CheckDealPerson();
			CheckRegPerson();
			CheckPassTime();
			CheckSignTime();
			CheckExecPeriodStart();
			CheckExecPeriodEnd();
			CheckAttach();
			CheckSuggest();
		}
		
	}

	public void checkDeptLeader(){
		CheckSuggest();
	}
	
	public void checkContractPreTrial(){
		if(ReviewMainConstants.CHOICE_PRE_TRIAL_CHOOSE_PERSON.equals(mainVo.getChoice())){
			CheckChooseContractPerson();
			CheckSuggest();
		}
		if(ReviewMainConstants.CHOICE_PRE_TRIAL_BACK_MODIFY.equals(mainVo.getChoice())){
			CheckSuggest();
		}
	}
	
	public void checkContractDealPerson(){
		CheckStructuredSuggest();
		CheckSuggest();
	}
	
	public void checkContractSimulate(){
		if(ReviewMainConstants.CHOICE_SIMULATE_CHOOSE_LEADER_DEPT.equals(mainVo.getChoice())){
			CheckChooseLeaderDept();
		}
		CheckSuggest();
	}
	
	public void checkComLeader(){
		CheckSuggest();
	}
	
	public void checkContractFinish(){
		if(ReviewMainConstants.CHOICE_FINISH_RE_SIMULATE.equals(mainVo.getChoice())){
			CheckChooseLeaderDept();
		}
		CheckSuggest();
	}
	
	
	private void CheckProjectInfo(){
		mainVo.setProjectId(StringUtil.getNotNullValueString(mainVo.getProjectId()));
		if(mainVo.getProjectId().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_PROJECT_INFO);
			return;
		}
	}
	
	private void CheckContractIdentifier(){
		mainVo.setContractIdentifier(StringUtil.getNotNullValueString(mainVo.getContractIdentifier()));
		if(mainVo.getContractIdentifier().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CONTRACT_IDENTIFIER);
			return;
		}
	}
	
	private void CheckContractSelfNumRegister(){
		mainVo.setContractSelfNum(StringUtil.getNotNullValueString(mainVo.getContractSelfNum()));
		if(mainVo.getContractSelfNum().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CONTRACT_NUM);
			return;
		}
		//是否重复
		if(FlowUtil.isExistsSelfNum(mainVo.getContractSelfNum()) > 0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_DUPLICATE_CONTRACT_NUM);
			return;
		}
	}
	
	private void CheckContractSelfNumModify(){
		mainVo.setContractSelfNum(StringUtil.getNotNullValueString(mainVo.getContractSelfNum()));
		if(mainVo.getContractSelfNum().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CONTRACT_NUM);
			return;
		}
		//是否重复
		if(FlowUtil.isExistsSelfNum(mainVo.getContractSelfNum()) > 1){
			resultInfo.addErrors(ReviewMainMessage.CHECK_DUPLICATE_CONTRACT_NUM);
			return;
		}
	}
	
	private void CheckPurchaseType(){
		mainVo.setPurchaseType(StringUtil.getNotNullValueString(mainVo.getPurchaseType()));
		if(mainVo.getPurchaseTypeId().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_PURCHASE_TYPE);
			return;
		}
	}
	
	private void CheckContractType1(){
		mainVo.setContractType1(StringUtil.getNotNullValueString(mainVo.getContractType1()));
		if(mainVo.getContractType1Id().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CONTRACT_TYPE1);
			return;
		}
	}
	
	private void CheckContractType2(){
		mainVo.setContractType2(StringUtil.getNotNullValueString(mainVo.getContractType2()));
		mainVo.setContractType(mainVo.getContractType1()+"-"+mainVo.getContractType2());
		if(mainVo.getContractType2Id().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CONTRACT_TYPE2);
			return;
		}
	}
	
	private void CheckContractGroup(){
		mainVo.setContractGroup(StringUtil.getNotNullValueString(mainVo.getContractGroup()));
		if(mainVo.getContractGroupId().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CONTRACT_GROUP);
			return;
		}
	}
	
	private void CheckRegPerson(){
		mainVo.setRegPerson(StringUtil.getNotNullValueString(mainVo.getRegPerson()));
		if(mainVo.getRegPerson().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_REG_PERSON);
			return;
		}
	}
	
	private void CheckPassTime(){
		mainVo.setPassTime(StringUtil.getNotNullValueString(mainVo.getPassTime()));
		if(mainVo.getPassTime().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_PASS_TIME);
			return;
		}
	}
	
	private void CheckExecPeriodStart(){
		mainVo.setExecPeriodStart(StringUtil.getNotNullValueString(mainVo.getExecPeriodStart()));
		if(mainVo.getExecPeriodStart().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_EXEC_START);
			return;
		}
	}
	
	private void CheckExecPeriodEnd(){
		mainVo.setExecPeriodEnd(StringUtil.getNotNullValueString(mainVo.getExecPeriodEnd()));
		if(mainVo.getExecPeriodEnd().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_EXEC_END);
			return;
		}
	}
	
	private void CheckChooseContractPerson(){
		mainVo.setDealPersonStr(StringUtil.getNotNullValueString(mainVo.getDealPersonStr()));
		if(mainVo.getDealPersonStr().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CHOOSE_PERSON);
			return;
		}
	}
	
	private void CheckChooseLeaderDept(){
		mainVo.setDepsId(StringUtil.getNotNullValueString(mainVo.getDepsId()));
		mainVo.setSignLdsId(StringUtil.getNotNullValueString(mainVo.getSignLdsId()));
		
		if(mainVo.getDepsId().length()==0 && mainVo.getSignLdsId().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CHOOSE_LEADER_DEPT);
			return;
		}
	}
	
	private void CheckStructuredSuggest(){
		String count = StringUtil.getNotNullValueString(mainVo.getCount());
		String checkCount = StringUtil.getNotNullValueString(mainVo.getCheckCount());
		mainVo.setCount(count);
		mainVo.setCheckCount(checkCount);
		mainVo.setSuggest(StringUtil.getNotNullValueString(
				mainVo.getSuggest().replaceAll("\\r\\n", "<br>")
				.replaceAll("\\n", "<br>").replaceAll("\\r", "<br>")));
		if(!count.equals(checkCount)){
			resultInfo.addErrors(ReviewMainMessage.CHECK_STRUCTURED_SUGGEST);
			return;
		}
	}
	
	private void CheckSuggest(){
		mainVo.setSuggest(StringUtil.getNotNullValueString(mainVo.getSuggest()));
		if(mainVo.getSuggest().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_SUGGEST);
			return;
		}else if(StringUtil.length(mainVo.getSuggest())>400){
			resultInfo.addErrors(ReviewMainMessage.CHECK_SUGGES_LIMIT_400);
			return;
		}
	}
	
	private void CheckContractName(){
		mainVo.setContractName(StringUtil.getNotNullValueString(mainVo.getContractName()));
		if(mainVo.getContractName().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CONTRACT_NAME);
			return;
		}
	}
	
	
	private void CheckContractMoney(){
		mainVo.setContractMoney(StringUtil.getNotNullValueString(mainVo.getContractMoney()));
		if(mainVo.getContractMoney().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CONTRACT_MONEY);
			return;
		}else if(!isRealNumber(mainVo.getContractMoney())){
			resultInfo.addErrors(ReviewMainMessage.CHECK_ERROR_CONTRACT_MONEY);
			return;
		}
	}
	
	private void CheckContractMoneyType(){
		mainVo.setContractMoneyType(StringUtil.getNotNullValueString(mainVo.getContractMoneyType()));
		if(mainVo.getContractMoneyTypeId().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CONTRACT_MONEY_TYPE);
			return;
		}
	}

	private void CheckOppositeCompany(){
		mainVo.setOppositeCompany(StringUtil.getNotNullValueString(mainVo.getOppositeCompany()));
		if(mainVo.getOppositeCompany().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_OPPOSITE_COMPANY);
			return;
		}
	}
	
	private void CheckContractBudget(){
		mainVo.setContractBudget(StringUtil.getNotNullValueString(mainVo.getContractBudget()));
		if(mainVo.getContractBudget().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_CONTRACT_BUDGET);
			return;
		}else if(!isRealNumber(mainVo.getContractBudget())){
			resultInfo.addErrors(ReviewMainMessage.CHECK_ERROR_CONTRACT_BUDGET);
			return;
		}
	}
	
	private void CheckDealPerson(){
		mainVo.setDealPerson(StringUtil.getNotNullValueString(mainVo.getDealPerson()));
		if(mainVo.getDealPerson().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_DEAL_PERSON);
			return;
		}
	}
	
	private void CheckSignTime(){
		mainVo.setSignTime(StringUtil.getNotNullValueString(mainVo.getSignTime()));
		if(mainVo.getSignTime().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_SIGN_TIME);
			return;
		}
	}
	
	private void CheckAttach(){
		mainVo.setAttach(StringUtil.getNotNullValueString(mainVo.getAttach()));
		if(mainVo.getAttach().length()==0){
			resultInfo.addErrors(ReviewMainMessage.CHECK_NO_ATTACH);
			return;
		}
	}

	 private static boolean isMatch(String regex, String orginal){  
	        if (orginal == null || orginal.trim().equals("")) {  
	            return false;  
	        }  
	        Pattern pattern = Pattern.compile(regex);  
	        Matcher isNum = pattern.matcher(orginal);  
	        return isNum.matches();  
	    }  
	  
	    public static boolean isPositiveInteger(String orginal) {  
	        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);  
	    }  
	  
	    public static boolean isNegativeInteger(String orginal) {  
	        return isMatch("^-[1-9]\\d*", orginal);  
	    }  
	  
	    public static boolean isWholeNumber(String orginal) {  
	        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);  
	    }  
	      
	    public static boolean isPositiveDecimal(String orginal){  
	        return isMatch("\\+{0,1}[0]\\.\\d*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);  
	    }  
	      
	    public static boolean isNegativeDecimal(String orginal){  
	        return isMatch("^-[0]\\.\\d*|^-[1-9]\\d*\\.\\d*", orginal);  
	    }  
	      
	    public static boolean isDecimal(String orginal){  
	        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);  
	    }  
	      
	    public static boolean isRealNumber(String orginal){  
	        return isWholeNumber(orginal) || isDecimal(orginal);  
	    }  
	
	/**判断空字符串
	 * @param str
	 * @return
	 */
	private boolean isNull(String str){
		if(StringUtil.getNotNullValueString(str).length()==0) return true;
		return false;
	}
	
	public static void main(String[] args){
	//	String s = "[1-9]{1}\\d{0,1}|100";
        String s1 = "-0.1111";
        System.out.println(isNegativeDecimal(s1));
        
        System.out.println(isPositiveInteger("+01"));
        
        String aaa = "aaaa\r\nbbb\r\ncccc";
        System.out.println(aaa.replaceAll("\\r\\n", ""));
	}
}
