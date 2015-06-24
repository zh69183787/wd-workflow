/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.service;

import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubParamVo;

/** 
 * @ClassName: SimulateSubService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 上午11:42:23 
 *  
 */
public interface SimulateSubService {
	public void init(SimulateSubParamVo params);
	public void flowStepSimulate(SimulateSubParamVo params);
	public void flowStepLeaderSimulate(SimulateSubParamVo params);
	public void flowStepSuggest(SimulateSubParamVo params);
}
