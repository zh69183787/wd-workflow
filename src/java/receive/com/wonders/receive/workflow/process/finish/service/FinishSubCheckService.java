/**
 * 
 */
package com.wonders.receive.workflow.process.finish.service;

import com.wonders.receive.workflow.process.finish.model.vo.FinishSubParamVo;

/** 
 * @ClassName: RecvMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface FinishSubCheckService {
	public void init(FinishSubParamVo params);
	
	public void checkFinish();

	
}
