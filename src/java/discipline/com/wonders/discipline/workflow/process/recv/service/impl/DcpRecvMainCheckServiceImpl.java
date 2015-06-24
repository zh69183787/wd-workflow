/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.service.impl;


import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.discipline.workflow.common.model.vo.ResultInfo;
import com.wonders.discipline.workflow.common.model.vo.UserInfo;
import com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainConstants;
import com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainMessage;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainParamVo;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainVo;
import com.wonders.discipline.workflow.process.recv.service.DcpRecvMainCheckService;
import com.wonders.discipline.workflow.process.util.SerialUtil;
import com.wonders.discipline.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;
/** 
 * @ClassName: DcpRecvMainServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("discipline-dcpRecvMainCheckService")
@Scope("prototype")
public class DcpRecvMainCheckServiceImpl implements DcpRecvMainCheckService{
	private ResultInfo resultInfo = new ResultInfo();
	private DcpRecvMainParamVo params;
	private DcpRecvMainVo mainVo;
	
	@Override
	public void init(DcpRecvMainParamVo params) {
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
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_WRONG_PROCESS_INFO);
			throw new RuntimeException(DcpRecvMainMessage.CHECK_WRONG_PROCESS_INFO.textCn);
		}
		
		if(cname.equals(pname)&&cincident.equals(pincident)){
	
		}else{

		}
	}
	
	//登记
	public void checkRegister(){
		CheckTitle();
		CheckFileDate();
		CheckRecvDate();
		CheckFileUnit();
		CheckFileNum();
		CheckKeyWord();
		CheckRemark();	
		CheckRecvSerial();
		CheckNum();		
		CheckAttach();
		//CheckSimulater();
		if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_ONE.equals(mainVo.getChoice())){
			CheckNoPerson();
		}else if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_TWO.equals(mainVo.getChoice())){
			CheckNoComLeaderAndDcp();
		}	
		CheckSuggest();
	}

	//部门领导
	public void checkDeptLeader(){
		if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_ONE.equals(mainVo.getChoice())){
			CheckTitle();
			CheckFileDate();
			CheckRecvDate();
			CheckFileUnit();
			CheckFileNum();
			CheckKeyWord();
			CheckRemark();	
			CheckNum();	
			CheckAttach();
			CheckRecvSerial();
			CheckSameDoc();
			CheckNoPerson();
		}else if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_TWO.equals(mainVo.getChoice())){
			CheckTitle();
			CheckFileDate();
			CheckRecvDate();
			CheckFileUnit();
			CheckFileNum();
			CheckKeyWord();
			CheckRemark();	
			CheckNum();	
			CheckAttach();
			CheckRecvSerial();
			CheckSameDoc();
			CheckNoComLeaderAndDcp();
		}else if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_BACK.equals(mainVo.getChoice())){
			
		}
		CheckSuggest();
	}
	
	//返回修改
	public void checkRegisterModify(){
		if(DcpRecvMainConstants.CHOICE_MODIFY_SUBMIT_DEPT_LEADER.equals(mainVo.getChoice())){
			CheckTitle();
			CheckFileDate();
			CheckRecvDate();
			CheckFileUnit();
			CheckFileNum();
			CheckKeyWord();
			CheckRemark();	
			CheckNum();	
			CheckAttach();
			CheckRecvSerial();
			//CheckSimulater();
			if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_ONE.equals(mainVo.getChoice())){
				CheckNoPerson();
			}else if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_TWO.equals(mainVo.getChoice())){
				CheckNoComLeaderAndDcp();
			}
		}
		CheckSuggest();
	}
	
	//人员处理
	public void checkPersonDeal(){
		CheckSuggest();
	}
	
	//汇总人
	public void checkSummary(){
		if(DcpRecvMainConstants.CHOICE_SUMMARY_PERSON_SUMBIT_DEPT.equals(mainVo.getChoice())){
			CheckNoDept();
			CheckSuggest();
		}else if(DcpRecvMainConstants.CHOICE_SUMMARY_PERSON_SUMBIT_FINISH.equals(mainVo.getChoice())){
			CheckSuggest();
			//CheckFinisher();
		}
	}
	
	
	//办结人
	public void checkDealFinish(){
		if(DcpRecvMainConstants.CHOICE_DEAL_FINISH_OVER.equals(mainVo.getChoice())){
			
		}else if(DcpRecvMainConstants.CHOICE_DEAL_FINISH_RETURN_SUMMARY_PERSON.equals(mainVo.getChoice())){
			
		}else if(DcpRecvMainConstants.CHOICE_DEAL_FINISH_SUMBIT_DEPT.equals(mainVo.getChoice())){
			CheckNoDept();
		}
		
		CheckSuggest();
	}
	
	private void CheckSimulater(){
		mainVo.setSimulateLoginName(StringUtil.getNotNullValueString(mainVo.getSimulateLoginName()));
		if(mainVo.getSimulateLoginName().length() == 0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_SIMULATER);
		}
	}
	
	private void CheckFinisher(){
		mainVo.setFinishLoginName(StringUtil.getNotNullValueString(mainVo.getFinishLoginName()));
		if(mainVo.getFinishLoginName().length() == 0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_FINISHER);
		}
	}
	
	private void CheckAttach(){
		mainVo.setAttach(StringUtil.getNotNullValueString(mainVo.getAttach()));
		if(mainVo.getAttach().length() == 0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_ATTACH);
		}
	}
	
	private void CheckSuggest(){
		mainVo.setSuggest(StringUtil.getNotNullValueString(mainVo.getSuggest()));
		if(mainVo.getSuggest().length()==0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_SUGGEST);
			return;
		}else if(StringUtil.getStrLength(mainVo.getSuggest())>400){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_SUGGES_LIMIT_400);
			return;
		}
	}
	
	
	private void CheckRemark(){
		mainVo.setRemark(StringUtil.getNotNullValueString(mainVo.getRemark()));
		/*长度限制1000*/
		if(StringUtil.getStrLength(mainVo.getRemark())>1000){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_REMARK_LIMIT_1000);
			return;
		}
	}
	
	private void CheckKeyWord(){
		mainVo.setKeyword(StringUtil.getNotNullValueString(mainVo.getKeyword()));
		/*长度限制50*/
		if(StringUtil.getStrLength(mainVo.getKeyword())>50){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_KEYWORD_LIMIT_50);
			return;
		}
	}
	
	private void CheckTitle(){
		mainVo.setTitle(StringUtil.getNotNullValueString(mainVo.getTitle()));
		if(mainVo.getTitle().length()==0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_TITLE);
			return;
		}
		/*长度限制200*/
		if(StringUtil.getStrLength(mainVo.getTitle())>100){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_TITLE_LIMIT_100);
			return;
		}
	}
	
	private void CheckFileDate(){
		mainVo.setFileDate(StringUtil.getNotNullValueString(mainVo.getFileDate()));
		if(mainVo.getFileDate().length()==0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_FILE_DATE);
			return;
		}
	}
	
	private void CheckFileUnit(){
		mainVo.setSwUnit(StringUtil.getNotNullValueString(mainVo.getSwUnit()));
		if(mainVo.getSwUnit().length()==0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_FILE_UNIT);
			return;
		}
	}
	
	private void CheckFileNum(){
		mainVo.setFilezh(StringUtil.getNotNullValueString(mainVo.getFilezh()));
		//if(mainVo.getFilezh().length()==0){
		//	resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_FILE_NUM);
		//	return;
		//}		
		if(StringUtil.getStrLength(mainVo.getFilezh()) > 50){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_FILE_NUM_50);
			return;
		}
	}
	
	private void CheckRecvDate(){
		mainVo.setSwDate(StringUtil.getNotNullValueString(mainVo.getSwDate()));
		if(mainVo.getSwDate().length()==0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_RECV_DATE);
			return;
		}
	}
	
	private void CheckRecvSerial(){
		mainVo.setSwSerial(StringUtil.getNotNullValueString(mainVo.getSwSerial()));
		if(mainVo.getSwSerial().length()==0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_RECV_SERIAL);
			return;
		}
	}
	
	private void CheckNum(){
		
		mainVo.setNum(StringUtil.getNotNullValueString(mainVo.getNum()));
		if(mainVo.getNum().length()==0 || !(isNum(mainVo.getNum())) ){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_RECV_NUM_NUMBER);
			return;
		}
	}
	
	private void CheckSameDoc(){
		int count = SerialUtil.hasSameDoc(StringUtil.getNotNullValueString(mainVo.getTitle()).trim(),
				StringUtil.getNotNullValueString(mainVo.getSwUnit()).trim(),
				StringUtil.getNotNullValueString(mainVo.getFilezh()).trim());
		if(count > 1){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_RECV_SAME_DOC);
			return;
		}
		
	}
	
	private void CheckNoPerson(){
		mainVo.setComLeaderLoginName(StringUtil.getNotNullValueString(mainVo.getComLeaderLoginName()));
		mainVo.setDcpLoginName(StringUtil.getNotNullValueString(mainVo.getDcpLoginName()));
		
		if(mainVo.getComLeaderLoginName().length() == 0 &&
				mainVo.getDcpLoginName().length() == 0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_COM_LEADER);
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_DCP);
			return;
		}
	}
	
	private void CheckNoComLeaderAndDcp(){
		mainVo.setComLeaderLoginName(StringUtil.getNotNullValueString(mainVo.getComLeaderLoginName()));
		mainVo.setDcpLoginName(StringUtil.getNotNullValueString(mainVo.getDcpLoginName()));
		
		if(mainVo.getComLeaderLoginName().length() == 0 ||
				mainVo.getDcpLoginName().length() == 0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_COM_LEADER);
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_DCP);
			return;
		}
	}
	
	private void CheckNoDept(){
		mainVo.setDepsId(StringUtil.getNotNullValueString(mainVo.getDepsId()));
		if(mainVo.getDepsId().length() == 0){
			resultInfo.addErrors(DcpRecvMainMessage.CHECK_NO_DEPT);
			return;
		}
	}
	
	private boolean isNum(String s){
		boolean flag = false;
		String m = "[1-9]{1}\\d{0,1}|100";
        if(s.matches(m)) {
           flag = true;
        }
        return flag;
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
