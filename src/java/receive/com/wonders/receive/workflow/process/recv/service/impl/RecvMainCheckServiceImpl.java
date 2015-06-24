/**
 * 
 */
package com.wonders.receive.workflow.process.recv.service.impl;


import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.receive.workflow.common.model.vo.ResultInfo;
import com.wonders.receive.workflow.common.model.vo.UserInfo;
import com.wonders.receive.workflow.process.recv.constants.RecvMainConstants;
import com.wonders.receive.workflow.process.recv.constants.RecvMainMessage;
import com.wonders.receive.workflow.process.recv.model.vo.RecvMainParamVo;
import com.wonders.receive.workflow.process.recv.model.vo.RecvMainVo;
import com.wonders.receive.workflow.process.recv.service.RecvMainCheckService;
import com.wonders.receive.workflow.process.util.SerialUtil;
import com.wonders.receive.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;
/** 
 * @ClassName: RecvMainServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("receive-recvMainCheckService")
@Scope("prototype")
public class RecvMainCheckServiceImpl implements RecvMainCheckService{
	private ResultInfo resultInfo = new ResultInfo();
	private RecvMainParamVo params;
	private RecvMainVo mainVo;
	
	@Override
	public void init(RecvMainParamVo params) {
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
			resultInfo.addErrors(RecvMainMessage.CHECK_WRONG_PROCESS_INFO);
			throw new RuntimeException(RecvMainMessage.CHECK_WRONG_PROCESS_INFO.textCn);
		}
		
		if(cname.equals(pname)&&cincident.equals(pincident)){
	
		}else{

		}
	}
	
	public void checkRegister(){
		CheckTitle();
		CheckFileDate();
		CheckFileUnit();
		CheckFileNum();
		CheckKeyWord();
		CheckRemark();
		CheckSameDoc();
	}

	public void checkGetSerialNo(){
		if(RecvMainConstants.CHOICE_SERIALNO_SUBMIT_SIMULATE.equals(mainVo.getChoice())){
			CheckTitle();
			CheckFileDate();
			CheckFileUnit();
			CheckFileNum();
			CheckKeyWord();
			CheckRemark();
			CheckRecvSerial();
			CheckNum();
			CheckRecvDate();
			CheckSameDoc();
		}else if(RecvMainConstants.CHOICE_SERIALNO_REGISTER_MODIFY.equals(mainVo.getChoice())){
			CheckSuggest();
		}
	}
	
	public void checkRegisterModify(){
		if(RecvMainConstants.CHOICE_MODIFY_SUBMIT_SERIALNO.equals(mainVo.getChoice())){
			CheckTitle();
			CheckFileDate();
			CheckFileUnit();
			CheckFileNum();
			CheckKeyWord();
			CheckRemark();
		}
	}
	
	public void checkRecord(){
		CheckSuggest();
	}
	
	
	
	private void CheckSuggest(){
		mainVo.setSuggest(StringUtil.getNotNullValueString(mainVo.getSuggest()));
		if(mainVo.getSuggest().length()==0){
			resultInfo.addErrors(RecvMainMessage.CHECK_NO_SUGGEST);
			return;
		}else if(StringUtil.getStrLength(mainVo.getSuggest())>400){
			resultInfo.addErrors(RecvMainMessage.CHECK_SUGGES_LIMIT_400);
			return;
		}
	}
	
	
	private void CheckRemark(){
		mainVo.setRemark(StringUtil.getNotNullValueString(mainVo.getRemark()));
		/*长度限制1000*/
		if(StringUtil.getStrLength(mainVo.getRemark())>1000){
			resultInfo.addErrors(RecvMainMessage.CHECK_REMARK_LIMIT_1000);
			return;
		}
	}
	
	private void CheckKeyWord(){
		mainVo.setKeyword(StringUtil.getNotNullValueString(mainVo.getKeyword()));
		/*长度限制50*/
		if(StringUtil.getStrLength(mainVo.getKeyword())>50){
			resultInfo.addErrors(RecvMainMessage.CHECK_KEYWORD_LIMIT_50);
			return;
		}
	}
	
	private void CheckTitle(){
		mainVo.setTitle(StringUtil.getNotNullValueString(mainVo.getTitle()));
		if(mainVo.getTitle().length()==0){
			resultInfo.addErrors(RecvMainMessage.CHECK_NO_RECV_TITLE);
			return;
		}
		/*长度限制200*/
		if(StringUtil.getStrLength(mainVo.getTitle())>100){
			resultInfo.addErrors(RecvMainMessage.CHECK_TITLE_LIMIT_100);
			return;
		}
	}
	
	private void CheckFileDate(){
		mainVo.setFileDate(StringUtil.getNotNullValueString(mainVo.getFileDate()));
		if(mainVo.getFileDate().length()==0){
			resultInfo.addErrors(RecvMainMessage.CHECK_NO_FILE_DATE);
			return;
		}
	}
	
	private void CheckFileUnit(){
		mainVo.setSwUnit(StringUtil.getNotNullValueString(mainVo.getSwUnit()));
		if(mainVo.getSwUnit().length()==0){
			resultInfo.addErrors(RecvMainMessage.CHECK_NO_FILE_UNIT);
			return;
		}
	}
	
	private void CheckFileNum(){
		mainVo.setFilezh(StringUtil.getNotNullValueString(mainVo.getFilezh()));
		if(mainVo.getFilezh().length()==0){
			resultInfo.addErrors(RecvMainMessage.CHECK_NO_FILE_NUM);
			return;
		}		
		if(StringUtil.getStrLength(mainVo.getFilezh()) > 50){
			resultInfo.addErrors(RecvMainMessage.CHECK_FILE_NUM_50);
			return;
		}
	}
	
	private void CheckRecvDate(){
		mainVo.setSwDate(StringUtil.getNotNullValueString(mainVo.getSwDate()));
		if(mainVo.getSwDate().length()==0){
			resultInfo.addErrors(RecvMainMessage.CHECK_NO_RECV_DATE);
			return;
		}
	}
	
	private void CheckRecvSerial(){
		mainVo.setSwSerial(StringUtil.getNotNullValueString(mainVo.getSwSerial()));
		if(mainVo.getSwSerial().length()==0){
			resultInfo.addErrors(RecvMainMessage.CHECK_NO_RECV_SERIAL);
			return;
		}
	}
	
	private void CheckNum(){
		
		mainVo.setNum(StringUtil.getNotNullValueString(mainVo.getNum()));
		if(mainVo.getNum().length()==0 || !(isNum(mainVo.getNum())) ){
			resultInfo.addErrors(RecvMainMessage.CHECK_RECV_NUM_NUMBER);
			return;
		}
	}
	
	private void CheckSameDoc(){
		int count = SerialUtil.hasSameDoc(StringUtil.getNotNullValueString(mainVo.getTitle()).trim(),
				StringUtil.getNotNullValueString(mainVo.getSwUnit()).trim(),
				StringUtil.getNotNullValueString(mainVo.getFilezh()).trim());
		if(count > 1){
			resultInfo.addErrors(RecvMainMessage.CHECK_RECV_SAME_DOC);
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
