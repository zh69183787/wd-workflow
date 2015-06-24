/**
 * 
 */
package com.wonders.receive.workflow.process.redv.service;

import com.wonders.receive.workflow.process.redv.model.vo.RedvMainParamVo;

/** 
 * @ClassName: RedvMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface RedvMainCheckService {
	public void init(RedvMainParamVo params);
	public boolean checkFlowIsInProcess();
	
	public void checkForward();
	
	public void checkRegister();

	public void checkLeaderCheck();
	
	public void checkRegisterModify();
	
	public void checkRecord();
	
	
}
