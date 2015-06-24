/**
 * 
 */
package com.wonders.project.workflow.process.discard.service;

import com.wonders.project.workflow.process.discard.model.vo.DiscardMainParamVo;

/** 
 * @ClassName: DiscardMainCheckService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface DiscardMainCheckService {
	public void init(DiscardMainParamVo params);
	public boolean checkFlowIsInProcess();
	
	public void checkForward();
	
	public void checkRegister();

	public void checkDeptLeader();
	
	public void checkRegisterModify();
	
	public void checkPersonDeal();
	
	public void checkPreTrial();
	
	public void checkDealFinish();
	
	
}
