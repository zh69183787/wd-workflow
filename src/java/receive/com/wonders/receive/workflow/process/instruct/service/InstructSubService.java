/**
 * 
 */
package com.wonders.receive.workflow.process.instruct.service;

import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubParamVo;

/** 
 * @ClassName: InstructSubService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-3-18 上午09:31:17 
 *  
 */

public interface InstructSubService {
	public void init(InstructSubParamVo params);
	public void flowStepSecretaryDeal(InstructSubParamVo params);
	public void flowStepLeaderDeal(InstructSubParamVo params);
}
