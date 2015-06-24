/**
 * 
 */
package com.wonders.receive.workflow.process.redv.service.impl;


import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.receive.workflow.common.model.vo.ResultInfo;
import com.wonders.receive.workflow.common.model.vo.UserInfo;
import com.wonders.receive.workflow.process.redv.constants.RedvMainConstants;
import com.wonders.receive.workflow.process.redv.constants.RedvMainMessage;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainParamVo;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainVo;
import com.wonders.receive.workflow.process.redv.service.RedvMainCheckService;
import com.wonders.receive.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;
/** 
 * @ClassName: RedvMainServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("receive-redvMainCheckService")
@Scope("prototype")
public class RedvMainCheckServiceImpl implements RedvMainCheckService{
	private ResultInfo resultInfo = new ResultInfo();
	private RedvMainParamVo params;
	private RedvMainVo mainVo;
	
	@Override
	public void init(RedvMainParamVo params) {
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
			resultInfo.addErrors(RedvMainMessage.CHECK_WRONG_PROCESS_INFO);
			throw new RuntimeException(RedvMainMessage.CHECK_WRONG_PROCESS_INFO.textCn);
		}
		
		if(cname.equals(pname)&&cincident.equals(pincident)){
	
		}else{

		}
	}
	
	public void checkRegister(){
		CheckTitle();
		CheckRedvDate();
		CheckDeptLeader();
		CheckKeyWord();
		CheckContent();
		CheckRedvDept();
	}

	public void checkLeaderCheck(){
		if(RedvMainConstants.CHOICE_LEADER_SUBMIT_SIMULATE.equals(mainVo.getChoice())){
			CheckTitle();
			CheckRedvDate();
			CheckKeyWord();
			CheckSuggest();
			CheckContent();
		}else if(RedvMainConstants.CHOICE_LEADER_REGISTER_MODIFY.equals(mainVo.getChoice())){
			CheckSuggest();
		}
	}
	
	public void checkRegisterModify(){
		if(RedvMainConstants.CHOICE_MODIFY_SUBMIT_LEADER.equals(mainVo.getChoice())){
			CheckTitle();
			CheckRedvDate();
			CheckDeptLeader();
			CheckKeyWord();
			CheckSuggest();
			CheckContent();
		}
	}
	
	public void checkRecord(){
		CheckSuggest();
	}
	
	
	
	private void CheckSuggest(){
		mainVo.setSuggest(StringUtil.getNotNullValueString(mainVo.getSuggest()));
		if(mainVo.getSuggest().length()==0){
			resultInfo.addErrors(RedvMainMessage.CHECK_NO_SUGGEST);
			return;
		}else if(StringUtil.length(mainVo.getSuggest())>400){
			resultInfo.addErrors(RedvMainMessage.CHECK_SUGGES_LIMIT_400);
			return;
		}
	}
	
	
	private void CheckContent(){
		mainVo.setContent(StringUtil.getNotNullValueString(mainVo.getContent()));
		/*长度限制1000*/
		if(StringUtil.length(mainVo.getContent())>1000){
			resultInfo.addErrors(RedvMainMessage.CHECK_CONTENT_LIMIT_1000);
			return;
		}
	}
	
	private void CheckKeyWord(){
		mainVo.setKeyword(StringUtil.getNotNullValueString(mainVo.getKeyword()));
		/*长度限制50*/
		if(StringUtil.length(mainVo.getKeyword())>200){
			resultInfo.addErrors(RedvMainMessage.CHECK_KEYWORD_LIMIT_200);
			return;
		}
	}
	
	private void CheckTitle(){
		mainVo.setTitle(StringUtil.getNotNullValueString(mainVo.getTitle()));
		if(mainVo.getTitle().length()==0){
			resultInfo.addErrors(RedvMainMessage.CHECK_NO_REDV_TITLE);
			return;
		}
		/*长度限制200*/
		if(StringUtil.length(mainVo.getTitle())>200){
			resultInfo.addErrors(RedvMainMessage.CHECK_TITLE_LIMIT_200);
			return;
		}
	}
	
	private void CheckDeptLeader(){
		mainVo.setNbOpinion(StringUtil.getNotNullValueString(mainVo.getNbOpinion()));
		if(mainVo.getNbOpinion().length()==0){
			resultInfo.addErrors(RedvMainMessage.CHECK_NO_DEPT_LEADER);
			return;
		}
	}
	
	private void CheckRedvDate(){
		mainVo.setSubmitDate(StringUtil.getNotNullValueString(mainVo.getSubmitDate()));
		if(mainVo.getSubmitDate().length()==0){
			resultInfo.addErrors(RedvMainMessage.CHECK_NO_REDV_DATE);
			return;
		}
	}
	
	private void CheckRedvDept(){
		mainVo.setSubmitDept(StringUtil.getNotNullValueString(mainVo.getSubmitDept()));
		if(mainVo.getSubmitDept().length()==0){
			resultInfo.addErrors(RedvMainMessage.CHECK_NO_DEPT_SERIAL);
			return;
		}
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
