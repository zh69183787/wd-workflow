/**
 * 
 */
package com.wonders.receive.workflow.process.finish.service;

import com.wonders.receive.workflow.process.finish.model.vo.FinishSubParamVo;

/** 
 * @ClassName: FinishSubService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 上午11:42:23 
 *  
 */
public interface FinishSubService {
	public void init(FinishSubParamVo params);
	public void flowStepFinish(FinishSubParamVo params);
}
