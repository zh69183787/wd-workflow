/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.service;

import com.wonders.receive.workflow.process.simulate.model.bo.SimulateSubFutureBo;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubParamVo;

/** 
 * @ClassName: SimulateSubFutureService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-8 下午8:26:32 
 *  
 */
public interface SimulateSubFutureService {
	public SimulateSubFutureBo saveFutureInfo(SimulateSubParamVo params);
}
