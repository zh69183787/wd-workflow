/**
 * 
 */
package com.wonders.receive.workflow.process.sign.service;

import com.wonders.receive.workflow.process.sign.model.vo.SignSubParamVo;

/** 
 * @ClassName: SignSubService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-3-18 上午09:31:17 
 *  
 */

public interface SignSubService {
	public void init(SignSubParamVo params);
	public void flowStepSecretaryDeal(SignSubParamVo params);
	public void flowStepLeaderDeal(SignSubParamVo params);
}
