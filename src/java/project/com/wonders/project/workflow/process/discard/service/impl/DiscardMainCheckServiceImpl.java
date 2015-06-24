/**
 * 
 */
package com.wonders.project.workflow.process.discard.service.impl;


import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.project.workflow.common.model.vo.ResultInfo;
import com.wonders.project.workflow.common.model.vo.UserInfo;
import com.wonders.project.workflow.process.discard.constants.DiscardMainConstants;
import com.wonders.project.workflow.process.discard.constants.DiscardMainMessage;
import com.wonders.project.workflow.process.discard.model.bo.DiscardAssetBo;
import com.wonders.project.workflow.process.discard.model.vo.DiscardMainParamVo;
import com.wonders.project.workflow.process.discard.model.vo.DiscardMainVo;
import com.wonders.project.workflow.process.discard.service.DiscardMainCheckService;
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
@Service("project-discardMainCheckService")
@Scope("prototype")
public class DiscardMainCheckServiceImpl implements DiscardMainCheckService{
	private ResultInfo resultInfo = new ResultInfo();
	private DiscardMainParamVo params;
	private DiscardMainVo mainVo;
	
	@Override
	public void init(DiscardMainParamVo params) {
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
			resultInfo.addErrors(DiscardMainMessage.CHECK_WRONG_PROCESS_INFO);
			throw new RuntimeException(DiscardMainMessage.CHECK_WRONG_PROCESS_INFO.textCn);
		}
		
		if(cname.equals(pname)&&cincident.equals(pincident)){
	
		}else{

		}
	}
	
	//登记
	public void checkRegister(){
		CheckProjectInfo();
		//CheckMoneySource();
		checkDate();
		CheckMobile();
		CheckAssets();
		CheckAttach();
	}

	//发起部门领导
	public void checkDeptLeader(){
		CheckSuggest();
	}
	
	//返回修改
	public void checkRegisterModify(){
		checkDate();
		CheckMobile();
		CheckAssets();
		CheckAttach();
	}
	
	//投资部收发员
	public void checkPreTrial(){
		if(DiscardMainConstants.CHOICE_PRETRIAL_SUMBIT_OPERATOR.equals(mainVo.getChoice())){
			CheckOperator();
			CheckNoDept();
			CheckLeader();
		}else if(DiscardMainConstants.CHOICE_PRETRIAL_SUMBIT_DEPT.equals(mainVo.getChoice())){
			CheckNoDept();
			CheckLeader();
		}
		CheckSuggest();
	}
	
	//投资部经办人处理
	public void checkPersonDeal(){
		CheckSuggest();
	}
	
	//办结人(投资部领导)
	public void checkDealFinish(){
		if(DiscardMainConstants.CHOICE_DEAL_FINISH_OVER.equals(mainVo.getChoice())){
			
		}else if(DiscardMainConstants.CHOICE_DEAL_FINISH_RETURN.equals(mainVo.getChoice())){
			
		}else if(DiscardMainConstants.CHOICE_DEAL_FINISH_SUMBIT_DEPT.equals(mainVo.getChoice())){
			CheckNoDept();
		}
		
		CheckSuggest();
	}
	
	private void CheckProjectInfo(){
		mainVo.setProjectId(StringUtil.getNotNullValueString(mainVo.getProjectId()));
		if(mainVo.getProjectId().length()==0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_NO_PROJECT_INFO);
			return;
		}
		//是否重复
		if(FlowUtil.isExistsProjectId(mainVo.getProjectId()) > 0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_DUPLICATE_PROJECT);
			return;
		}
	}
	
	private void CheckMoneySource(){
		mainVo.setMoneySource(StringUtil.getNotNullValueString(mainVo.getMoneySource()));
		if(mainVo.getMoneySource().length()==0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_NO_MONEY_SOURCE);
		}
	}
	
	private void checkDate(){
		mainVo.setStartDate(StringUtil.getNotNullValueString(mainVo.getStartDate()));
		if(mainVo.getStartDate().length()==0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_NO_START_DATE);
		}
		
		mainVo.setFinishDate(StringUtil.getNotNullValueString(mainVo.getFinishDate()));
		if(mainVo.getFinishDate().length()==0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_NO_FINISH_DATE);
		}
		
		if(mainVo.getStartDate().length() > 0 && mainVo.getFinishDate().length() > 0){
			if(DateUtil.getDate(mainVo.getStartDate(), DateUtil.DATE_FORMAT).getTime() > DateUtil.getDate(mainVo.getFinishDate(), DateUtil.DATE_FORMAT).getTime()){
				resultInfo.addErrors(DiscardMainMessage.CHECK_WRONG_DATE_COMPARE);	
			}
		}
	}
	
	private void CheckMobile(){
		mainVo.setOperatorMobile(StringUtil.getNotNullValueString(mainVo.getOperatorMobile()));
		if(mainVo.getOperatorMobile().length()==0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_NO_MOBILE);
		}
		
/*		if(mainVo.getOperatorMobile().length() > 0){
			if(!isPhone(mainVo.getOperatorMobile())){
				resultInfo.addErrors(DiscardMainMessage.CHECK_WRONG_PHONE);
			}
		}*/
	}
	
	private void CheckAttach(){
		mainVo.setAttach(StringUtil.getNotNullValueString(mainVo.getAttach()));
		if(mainVo.getAttach().length() == 0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_NO_ATTACH);
		}
	}
	
	private void CheckSuggest(){
		mainVo.setSuggest(StringUtil.getNotNullValueString(mainVo.getSuggest()));
		if(mainVo.getSuggest().length()==0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_NO_SUGGEST);
			return;
		}else if(StringUtil.getStrLength(mainVo.getSuggest())>400){
			resultInfo.addErrors(DiscardMainMessage.CHECK_SUGGES_LIMIT_400);
			return;
		}
	}
	
	private void CheckOperator(){
		mainVo.setDealPersonStr(StringUtil.getNotNullValueString(mainVo.getDealPersonStr()));
		if(mainVo.getDealPersonStr().length()==0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_NO_CHOOSE_PERSON);
		}
	}
	
	private void CheckLeader(){
		mainVo.setDealLeaderStr(StringUtil.getNotNullValueString(mainVo.getDealLeaderStr()));
		if(mainVo.getDealLeaderStr().length()==0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_NO_CHOOSE_LEADER);
			return;
		}
	}	
	
	private void CheckAssets(){
		boolean hasAssets = false;
		if(mainVo.getAssets() != null){
			for(DiscardAssetBo asset : mainVo.getAssets()){
				if(asset != null && !"1".equals(asset.getRemoved())){
					hasAssets = true;
					break;
				}
			}				
		}
		if(!hasAssets){
			resultInfo.addErrors(DiscardMainMessage.CHECK_NO_ASSETS);
			return;	
		}
		checkLifeExtend();
		checkOriginalValue();
		CheckAssetContent();
	}
	
	private void checkLifeExtend(){
		for(DiscardAssetBo asset : mainVo.getAssets()){
			if(asset != null && !"1".equals(asset.getRemoved())){
				if(!StringUtils.isEmpty(asset.getLifeExtend()) && !isNonNegInteger(asset.getLifeExtend())){
					resultInfo.addErrors(DiscardMainMessage.CHECK_LIFE_EXTEND_INT);
					return;
				}
			}
		}
	}
	
	private void checkOriginalValue(){
		BigDecimal sum = new BigDecimal(0);
		BigDecimal finishPrice = parseDecimal(mainVo.getFinishPrice());
		for(DiscardAssetBo asset : mainVo.getAssets()){
			if(asset != null && !"1".equals(asset.getRemoved())){
				if(!StringUtils.isEmpty(asset.getMaintainCost())){
					sum = sum.add(parseDecimal(asset.getMaintainCost()));
				}
			}
		}
		if(sum.compareTo(finishPrice) != 0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_PRICE_SUM_EQUAL);
			return;
		}
	}
	
	private void CheckAssetContent(){
		for(DiscardAssetBo asset : mainVo.getAssets()){
			if(asset != null){
				if(StringUtil.getStrLength(asset.getContent())>500){
					resultInfo.addErrors(DiscardMainMessage.CHECK_ASSET_CONTENT_LIMIT_500);
					return;
				}			
			}
		}		
	}
	
	private void CheckNoDept(){
		mainVo.setDepsId(StringUtil.getNotNullValueString(mainVo.getDepsId()));
		if(mainVo.getDepsId().length() == 0){
			resultInfo.addErrors(DiscardMainMessage.CHECK_NO_DEPT);
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
	
	private static boolean isNonNegInteger(String orginal) {  
        return isMatch("0|^[1-9]\\d*", orginal);  
    }  
    
    private static boolean isPhone(String orginal){
    	return isMatch("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}", orginal); 
    }
    
    public static BigDecimal parseDecimal(String orginal){  
    	BigDecimal result = new BigDecimal(0);
    	if(orginal == null){
    		return result;
    	}
		try {
			result = new BigDecimal(orginal);
		} catch (NumberFormatException e) {
		}
        return result;  
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
		String s = "[1-9]{1}\\d{0,1}|100";
        String s1 = "101";
        if(s1.matches(s)) {
            System.out.println("ok");
        }

	}
}
