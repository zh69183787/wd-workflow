/**
 * 
 */
package com.wonders.receive.workflow.process.instruct.service.impl;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.receive.workflow.common.model.vo.ResultInfo;
import com.wonders.receive.workflow.process.instruct.constants.InstructSubMessage;
import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubParamVo;
import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubVo;
import com.wonders.receive.workflow.process.instruct.service.InstructSubCheckService;
import com.wonders.util.StringUtil;
/** 
 * @ClassName: InstructSubCheckServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("receive-instructSubCheckService")
@Scope("prototype")
public class InstructSubCheckServiceImpl implements InstructSubCheckService{
	private ResultInfo resultInfo = new ResultInfo();
	@SuppressWarnings("unused")
	private InstructSubParamVo params;
	private InstructSubVo subVo;
	
	@Override
	public void init(InstructSubParamVo params) {
		this.params = params;
		if(params!=null){
			this.subVo = params.vo;
			this.resultInfo = params.resultInfo;
		}
		
	}

	/** 
	* @Title: checkSecretaryDeal 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @throws 
	*/
	@Override
	public void checkSecretaryDeal() {
		// TODO Auto-generated method stub
		CheckSuggest();
	}

	/** 
	* @Title: checkLeaderDeal 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @throws 
	*/
	@Override
	public void checkLeaderDeal() {
		// TODO Auto-generated method stub
		CheckSuggest();
	}
	

	
	private void CheckSuggest(){
		subVo.setSuggest(StringUtil.getNotNullValueString(subVo.getSuggest()));
		if(StringUtil.getStrLength(subVo.getSuggest())>400){
			resultInfo.addErrors(InstructSubMessage.CHECK_SUGGES_LIMIT_400);
			return;
		}
	}


	
}
