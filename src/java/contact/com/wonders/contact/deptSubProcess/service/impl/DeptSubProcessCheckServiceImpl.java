package com.wonders.contact.deptSubProcess.service.impl;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.contact.common.model.bo.ResultInfo;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.util.CommonUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.util.FlowUtil;
import com.wonders.contact.deptSubProcess.constant.DeptSubProcessConstants;
import com.wonders.contact.deptSubProcess.constant.DeptSubProcessMessage;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessParamVo;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessVo;
import com.wonders.contact.deptSubProcess.service.DeptSubProcessCheckService;
import com.wonders.util.StringUtil;

@Service("contact-deptSubProcessCheckService")
@Scope("prototype")
public class DeptSubProcessCheckServiceImpl implements DeptSubProcessCheckService {
	private ResultInfo resultInfo = new ResultInfo();
	private DeptSubProcessVo vo;
	
	private DeptSubProcessParamVo params;
	
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	@Override
	public void init(DeptSubProcessParamVo params) {
		this.params = params;
		if(params!=null){
			this.vo = params.vo;
			this.resultInfo = params.resultInfo;
		}
	}

	
	/*流程提交信息检查*/
	
	@Override
	public void checkDispatcher() {
//log.debug("检查 （部门接收人分发）提交信息");
		if(!checkFlowIsInProcess()){
			return;
		}
		
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
//log.debug("choice="+choice);

		if(!CommonUtil.targetIsInArray(choice, DeptSubProcessConstants.OPTIONS_DISPATCHER)){
			resultInfo.addErrors(DeptSubProcessMessage.DISPATCHER_NO_CHOICE);
			return;
		}
		
		vo.initList();
		
		if(DeptSubProcessConstants.CHOICE_DISPATCHER_SEND.equals(choice)){
			checkLeader();
			checkSuggest();
		}
		
		if(DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL.equals(choice)){
			checkLeader();
			
			String choice2 = StringUtil.getNotNullValueString(vo.getChoice2());
//log.debug("choice2="+choice2);
			
			if(!CommonUtil.targetIsInArray(choice2, DeptSubProcessConstants.OPTIONS_DISPATCHER_DEAL)){
				resultInfo.addErrors(DeptSubProcessMessage.DISPATCHER_NO_CHOICE2);
				return;
			}
			
			if(DeptSubProcessConstants.CHOICE_DEAL_HAS_SUGGEST.equals(choice2)){
				checkSuggest();
			}
		}
		
		//resultInfo.ShowResultInfo();
	}
	
	@Override
	public void checkDeal(){
		if(!checkFlowIsInProcess()){
			return;
		}
//log.debug("检查 （部门人员处理）提交信息");
		
		String choice = StringUtil.getNotNullValueString(vo.getChoice());

		if(!CommonUtil.targetIsInArray(choice, DeptSubProcessConstants.OPTIONS_DEAL)){
			resultInfo.addErrors(DeptSubProcessMessage.DEAL_NO_CHOICE);
			return;
		}
		
		if(DeptSubProcessConstants.CHOICE_DEAL_HAS_SUGGEST.equals(choice)){
			checkSuggest();
		}
		
		//resultInfo.ShowResultInfo();
	}
	
	@Override
	public void checkLeaderDeal(){
		if(!checkFlowIsInProcess()){
			return;
		}
		
//log.debug("检查 （部门领导审核）提交信息");
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		
		if(!CommonUtil.targetIsInArray(choice, DeptSubProcessConstants.OPTIONS_LEADER)){
			resultInfo.addErrors(DeptSubProcessMessage.LEADER_NO_CHOICE);
			return;
		}
		
		vo.initList();
			
		if(DeptSubProcessConstants.CHOICE_LEADER_FAILED.equals(choice)){
			checkSuggest();
		}
		
		if(DeptSubProcessConstants.CHOICE_LEADER_CONTINUE.equals(choice)){
			String choice2 = StringUtil.getNotNullValueString(vo.getChoice2());
//log.debug("choice2="+choice2);
			
			if(!CommonUtil.targetIsInArray(choice2, DeptSubProcessConstants.OPTIONS_LEADER_CONTINUE)){
				resultInfo.addErrors(DeptSubProcessMessage.LEADER_NO_CHOICE2);
				return;
			}
			
			checkSuggest();
			
			if(DeptSubProcessConstants.CHOICE_LEADER_CONTINUE_CHOICE_PERSON.equals(choice2)){
				if(vo.getDealLeaderList().size()==0&&vo.getDealPersonList().size()==0){
					resultInfo.addErrors(DeptSubProcessMessage.LEADER_NO_PERSON_LEADER);
					return;
				}
			}
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
			boolean flag = FlowUtil.checkActiveProcess(processname, incident, steplabel, userInfo);
			
			if(!flag){
				//找不到待办事项信息，操作已完成！
				//params.addProcessParam("operateType", "detail");
				resultInfo.addErrors(DeptSubProcessMessage.CHECK_IS_FINISH);
				resultInfo.refresh = true;
			}
		
//System.out.println("checkFlowIsInProcess:"+flag);
		return flag;
		}
		
//System.out.println("checkFlowIsInProcess:"+flag);
		resultInfo.addErrors(DeptSubProcessMessage.CHECK_IS_FINISH);
		resultInfo.refresh = true;
		return false;
	}
	
	
	
	private void checkLeader(){
		if(vo.getDealLeaderList().size()==0){
			resultInfo.addErrors(DeptSubProcessMessage.COMMON_NO_LEADER);
			return;
		}
	}
	
	private void checkSuggest(){
		if(vo.getSuggest().length()==0){
			resultInfo.addErrors(DeptSubProcessMessage.COMMON_NO_SUGGEST);
			return;
		}
	}
	
}
