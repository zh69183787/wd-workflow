/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.service.impl;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.receive.workflow.common.model.vo.ResultInfo;
import com.wonders.receive.workflow.process.simulate.constants.SimulateSubConstants;
import com.wonders.receive.workflow.process.simulate.constants.SimulateSubMessage;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubParamVo;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubVo;
import com.wonders.receive.workflow.process.simulate.service.SimulateSubCheckService;
import com.wonders.util.StringUtil;
/** 
 * @ClassName: SimulateSubCheckServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("receive-simulateSubCheckService")
@Scope("prototype")
public class SimulateSubCheckServiceImpl implements SimulateSubCheckService{
	private ResultInfo resultInfo = new ResultInfo();
	@SuppressWarnings("unused")
	private SimulateSubParamVo params;
	private SimulateSubVo subVo;
	
	@Override
	public void init(SimulateSubParamVo params) {
		this.params = params;
		if(params!=null){
			this.subVo = params.vo;
			this.resultInfo = params.resultInfo;
		}
		
	}
	

	/** 
	* @Title: checkSimulate 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @throws 
	*/
	@Override
	public void checkSimulate() {
		if(SimulateSubConstants.CHOICE_LEADER_INSTRUCT.equals(subVo.getChoice())){
			CheckInstructLeader();
		}else if(SimulateSubConstants.CHOICE_DEPT_SUGGEST.equals(subVo.getChoice())){
			CheckSuggestDept();
		}else if(SimulateSubConstants.CHOICE_DEPT_LEADER_CONCURRENCE.equals(subVo.getChoice())){
			CheckUrgeSimulate();
		}
			
		CheckSuggest();
	}



	/** 
	* @Title: checkLeaderSimulate 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @throws 
	*/
	@Override
	public void checkLeaderSimulate() {
		if(SimulateSubConstants.CHOICE_LEADER_INSTRUCT.equals(subVo.getChoice())){
			CheckInstructLeader();
		}else if(SimulateSubConstants.CHOICE_DEPT_SUGGEST.equals(subVo.getChoice())){
			CheckSuggestDept();
		}else if(SimulateSubConstants.CHOICE_DEPT_LEADER_CONCURRENCE.equals(subVo.getChoice())){
			CheckUrgeSimulate();
		}
		CheckSuggest();
	}



	
	
	
	private void CheckInstructLeader(){
		subVo.setInsInsLdsId(StringUtil.getNotNullValueString(subVo.getInsInsLdsId()));
		subVo.setInsSignLdsId(StringUtil.getNotNullValueString(subVo.getInsSignLdsId()));
		if(subVo.getInsInsLdsId().length()==0 && subVo.getInsSignLdsId().length()==0){
			resultInfo.addErrors(SimulateSubMessage.CHECK_NO_INSTRUCT_LEADER);
			return;
		}
	}
	
	private void CheckSuggestDept(){
		subVo.setSugDepLdsId(StringUtil.getNotNullValueString(subVo.getSugDepLdsId()));
		if(subVo.getSugDepLdsId().length()==0){
			resultInfo.addErrors(SimulateSubMessage.CHECK_NO_SUGGEST_DEPT);
			return;
		}
	}
	
	private void CheckUrgeSimulate(){
		subVo.setDepsId(StringUtil.getNotNullValueString(subVo.getDepsId()));
		subVo.setSignLdsId(StringUtil.getNotNullValueString(subVo.getSignLdsId()));
		if(subVo.getDepsId().length()==0 || subVo.getSignLdsId().length()==0){
			resultInfo.addErrors(SimulateSubMessage.CHECK_NO_URGE_SIMULATE);
			return;
		}
	}
	
	private void CheckSuggest(){
		subVo.setSuggest(StringUtil.getNotNullValueString(subVo.getSuggest()));
		if(StringUtil.getStrLength(subVo.getSuggest())>400){
			resultInfo.addErrors(SimulateSubMessage.CHECK_SUGGES_LIMIT_400);
			return;
		}
	}



	
}
