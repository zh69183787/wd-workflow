/**
 * 
 */
package com.wonders.project.workflow.process.dept.service.impl;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.project.workflow.common.model.vo.ResultInfo;
import com.wonders.project.workflow.process.dept.constants.DeptSubConstants;
import com.wonders.project.workflow.process.dept.constants.DeptSubMessage;
import com.wonders.project.workflow.process.dept.model.vo.DeptSubParamVo;
import com.wonders.project.workflow.process.dept.model.vo.DeptSubVo;
import com.wonders.project.workflow.process.dept.service.DeptSubCheckService;
import com.wonders.util.StringUtil;
/** 
 * @ClassName: SimulateSubCheckServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("project-deptSubCheckService")
@Scope("prototype")
public class DeptSubCheckServiceImpl implements DeptSubCheckService{
	private ResultInfo resultInfo = new ResultInfo();
	@SuppressWarnings("unused")
	private DeptSubParamVo params;
	private DeptSubVo subVo;
	
	@Override
	public void init(DeptSubParamVo params) {
		this.params = params;
		if(params!=null){
			this.subVo = params.vo;
			this.resultInfo = params.resultInfo;
		}
		
	}
	

	public void checkDispatcher(){
		if(DeptSubConstants.CHOICE_DISPATCHER_SEND.equals(subVo.getChoice())){
			CheckLeader();
			CheckSuggest(true);
		}
		if(DeptSubConstants.CHOICE_DISPATCHER_DEAL.equals(subVo.getChoice())){
			if(DeptSubConstants.CHOICE_DISPATCHER_DEAL_NO_SUGGEST.equals(subVo.getChoice2())){
				CheckLeader();
				CheckSuggest();
			}else if(DeptSubConstants.CHOICE_DISPATCHER_DEAL_HAS_SUGGEST.equals(subVo.getChoice2())){
				CheckLeader();
				CheckSuggest(true);
			}
		}
	}
	public void checkDeal(){
		CheckSuggest();
	}
	public void checkLeaderDeal(){
		if(DeptSubConstants.CHOICE_LEADER_PASS.equals(subVo.getChoice())){
			CheckSuggest();
		}else if(DeptSubConstants.CHOICE_LEADER_FAILED.equals(subVo.getChoice())){
			CheckSuggest(true);
		}else if(DeptSubConstants.CHOICE_LEADER_CONTINUE.equals(subVo.getChoice())){
			if(DeptSubConstants.CHOICE_LEADER_CONTINUE_DEAL.equals(subVo.getChoice2())){
				CheckSuggest(true);
			}else if(DeptSubConstants.CHOICE_LEADER_CONTINUE_CHOICE_PERSON.equals(subVo.getChoice2())){
				CheckOfficerLeader();
				CheckSuggest(true);
			}
		}
	}

	
	private void CheckSuggest(){
		this.CheckSuggest(false);
	}

	private void CheckSuggest(boolean required){
		subVo.setSuggest(StringUtil.getNotNullValueString(subVo.getSuggest()));
		if(required && subVo.getSuggest().length()==0){
			resultInfo.addErrors(DeptSubMessage.CHECK_NO_SUGGEST);
			return;
		}
		if(StringUtil.getStrLength(subVo.getSuggest())>400){
			resultInfo.addErrors(DeptSubMessage.CHECK_SUGGES_LIMIT_400);
			return;
		}
	}
	private void CheckLeader(){
		subVo.setDealLeaderStr(StringUtil.getNotNullValueString(subVo.getDealLeaderStr()));
		if(subVo.getDealLeaderStr().length()==0){
			resultInfo.addErrors(DeptSubMessage.CHECK_NO_LEADER);
			return;
		}
	}
	private void CheckOfficerLeader(){
		subVo.setDealLeaderStr(StringUtil.getNotNullValueString(subVo.getDealLeaderStr()));
		subVo.setDealPersonStr(StringUtil.getNotNullValueString(subVo.getDealPersonStr()));
		
		if(subVo.getDealLeaderStr().length()==0 && subVo.getDealPersonStr().length()==0){
			resultInfo.addErrors(DeptSubMessage.CHECK_NO_OFFICER_LEADER);
			return;
		}
	}

	
}
