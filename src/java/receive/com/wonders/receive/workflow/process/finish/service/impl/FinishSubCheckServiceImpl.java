/**
 * 
 */
package com.wonders.receive.workflow.process.finish.service.impl;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.receive.workflow.common.model.vo.ResultInfo;
import com.wonders.receive.workflow.process.finish.constants.FinishSubConstants;
import com.wonders.receive.workflow.process.finish.constants.FinishSubMessage;
import com.wonders.receive.workflow.process.finish.model.vo.FinishSubParamVo;
import com.wonders.receive.workflow.process.finish.model.vo.FinishSubVo;
import com.wonders.receive.workflow.process.finish.service.FinishSubCheckService;
import com.wonders.util.StringUtil;
/** 
 * @ClassName: SimulateSubCheckServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("receive-finishSubCheckService")
@Scope("prototype")
public class FinishSubCheckServiceImpl implements FinishSubCheckService{
	private ResultInfo resultInfo = new ResultInfo();
	@SuppressWarnings("unused")
	private FinishSubParamVo params;
	private FinishSubVo subVo;
	
	@Override
	public void init(FinishSubParamVo params) {
		this.params = params;
		if(params!=null){
			this.subVo = params.vo;
			this.resultInfo = params.resultInfo;
		}
		
	}
	

	/** 
	* @Title: checkFinish 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @throws 
	*/
	@Override
	public void checkFinish() {	
		if(FinishSubConstants.CHOICE_DEPT_LEADER_CONCURRENCE.equals(subVo.getChoice())){
			CheckUrgeSimulate();
		}
		CheckSuggest();
	}


	private void CheckSuggest(){
		subVo.setSuggest(StringUtil.getNotNullValueString(subVo.getSuggest()));
		if(subVo.getSuggest().length() == 0 ) {
			resultInfo.addErrors(FinishSubMessage.CHECK_NO_SUGGEST);
			return;
		}
		if(StringUtil.getStrLength(subVo.getSuggest())>400){
			resultInfo.addErrors(FinishSubMessage.CHECK_SUGGES_LIMIT_400);
			return;
		}
	}

	private void CheckUrgeSimulate(){
		subVo.setDepsId(StringUtil.getNotNullValueString(subVo.getDepsId()));
		subVo.setSignLdsId(StringUtil.getNotNullValueString(subVo.getSignLdsId()));
		if(subVo.getDepsId().length()==0 || subVo.getSignLdsId().length()==0){
			resultInfo.addErrors(FinishSubMessage.CHECK_NO_URGE_SIMULATE);
			return;
		}
	}

	
}
