/**
 * 
 */
package com.wonders.dept.workflow.process.pass.service.impl;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.dept.workflow.common.model.vo.ResultInfo;
import com.wonders.dept.workflow.common.model.vo.UserInfo;
import com.wonders.dept.workflow.common.util.LoginUtil;
import com.wonders.dept.workflow.process.pass.constants.PassMainConstants;
import com.wonders.dept.workflow.process.pass.constants.PassMainMessage;
import com.wonders.dept.workflow.process.pass.model.vo.PassMainParamVo;
import com.wonders.dept.workflow.process.pass.model.vo.PassMainVo;
import com.wonders.dept.workflow.process.pass.service.PassMainCheckService;
import com.wonders.dept.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;
/** 
 * @ClassName: PassMainServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("dept-passMainCheckService")
@Scope("prototype")
public class PassMainCheckServiceImpl implements PassMainCheckService{
	private ResultInfo resultInfo = new ResultInfo();
	private PassMainParamVo params;
	private PassMainVo mainVo;
	
	@Override
	public void init(PassMainParamVo params) {
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
	//1确认 当前session与人员是否匹配
	//2确认是否已签收
	public void checkAddSign(){
		String mainId = StringUtil.getNotNullValueString(params.vo.getMainId());
		UserInfo userInfo = params.userInfo;
		if(mainId.length() > 0 ){
			String taskuser = params.getProcessParamValue("taskuser");
			String loginName = LoginUtil.getUserLoginName(userInfo);
			if (!taskuser.equals(loginName)){
				resultInfo.addErrors(PassMainMessage.FAIL_TO_WRONG_PERSON);
				return;
			}
			if(FlowUtil.checkExistData(mainId, loginName)){
				resultInfo.addErrors(PassMainMessage.FAIL_TO_SIGN_ADD);
				return;
			}
		}
	}
	
	@Override
	public void checkForward() {
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		
		//String steplabel = params.getProcessParamValue("steplabel");
		
		if(isNull(cname)&&isNull(cincident)){
			resultInfo.addErrors(PassMainMessage.CHECK_WRONG_PROCESS_INFO);
			throw new RuntimeException(PassMainMessage.CHECK_WRONG_PROCESS_INFO.textCn);
		}
		
		if(cname.equals(pname)&&cincident.equals(pincident)){
	
		}else{

		}
	}
	
	public void checkRegister(){
		CheckTitle();
		if(PassMainConstants.CHOICE_SELECT_DEPT_PERSON_FINISH .equals(mainVo.getChoice())
				||PassMainConstants.CHOICE_SELECT_DEPT_PERSON_SELF_CHECK.equals(mainVo.getChoice())){
			CheckOfficer();
		}
		CheckSuggest();
	}
	
	public void checkDealPerson(){
		CheckSuggest();
	}
	
	public void checkDeptLeader(){
		
		if(PassMainConstants.CHOICE_SELECT_DEPT_PERSON_FINISH .equals(mainVo.getChoice())
				||PassMainConstants.CHOICE_SELECT_DEPT_PERSON_SELF_CHECK.equals(mainVo.getChoice())){
			CheckOfficer();
		}
		CheckSuggest();
	}
	
	
	private void CheckSuggest(){
		mainVo.setSuggest(StringUtil.getNotNullValueString(mainVo.getSuggest()));
		if(mainVo.getSuggest().length()==0){
			resultInfo.addErrors(PassMainMessage.CHECK_NO_SUGGEST);
			return;
		}else if(StringUtil.length(mainVo.getSuggest())>400){
			resultInfo.addErrors(PassMainMessage.CHECK_SUGGES_LIMIT_400);
			return;
		}
	}
	
	private void CheckTitle(){
		mainVo.setTitle(StringUtil.getNotNullValueString(mainVo.getTitle()));
		if(mainVo.getTitle().length()==0){
			resultInfo.addErrors(PassMainMessage.CHECK_NO_TITLE);
			return;
		}
	}
	
	
	
	
	private void CheckOfficer(){
		mainVo.setDealPersonStr(StringUtil.getNotNullValueString(mainVo.getDealPersonStr()));
		if(mainVo.getDealPersonStr().length()==0){
			resultInfo.addErrors(PassMainMessage.CHECK_NO_OFFICER);
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
	        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);  
	    }  
	      
	    public static boolean isNegativeDecimal(String orginal){  
	        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);  
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
        String s1 = "-2132.123";
        System.out.println(isRealNumber(s1));
        
        System.out.println(isPositiveInteger("+1"));
	}

}
