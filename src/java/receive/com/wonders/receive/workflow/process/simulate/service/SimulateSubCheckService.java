/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.service;

import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubParamVo;

/** 
 * @ClassName: RecvMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface SimulateSubCheckService {
	public void init(SimulateSubParamVo params);
	
	public void checkSimulate();

	public void checkLeaderSimulate();
	
	
	
}
