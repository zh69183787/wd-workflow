/**
 * 
 */
package com.wonders.receive.workflow.process.sign.service.impl;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.receive.workflow.common.model.vo.ResultInfo;
import com.wonders.receive.workflow.process.sign.constants.SignSubMessage;
import com.wonders.receive.workflow.process.sign.model.vo.SignSubParamVo;
import com.wonders.receive.workflow.process.sign.model.vo.SignSubVo;
import com.wonders.receive.workflow.process.sign.service.SignSubCheckService;
import com.wonders.util.StringUtil;
/** 
 * @ClassName: SignSubCheckServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("receive-signSubCheckService")
@Scope("prototype")
public class SignSubCheckServiceImpl implements SignSubCheckService{
	private ResultInfo resultInfo = new ResultInfo();
	@SuppressWarnings("unused")
	private SignSubParamVo params;
	private SignSubVo subVo;
	
	@Override
	public void init(SignSubParamVo params) {
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
			resultInfo.addErrors(SignSubMessage.CHECK_SUGGES_LIMIT_400);
			return;
		}
	}


	
}
