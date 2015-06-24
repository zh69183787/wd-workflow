/**
 * 
 */
package com.wonders.receive.workflow.process.finish.service;

import com.wonders.receive.workflow.process.finish.model.bo.FinishSubFutureBo;
import com.wonders.receive.workflow.process.finish.model.vo.FinishSubParamVo;
/** 
 * @ClassName: SimulateSubFutureService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-8 下午8:26:32 
 *  
 */
public interface FinishSubFutureService {
	public FinishSubFutureBo saveFutureInfo(FinishSubParamVo params);
}
