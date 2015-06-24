/**
 * 
 */
package com.wonders.contract.workflow.process.review.service;

import com.wonders.contract.workflow.process.review.model.vo.ReviewMainParamVo;


/** 
 * @ClassName: ReviewMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface ReviewMainCheckService {
	public void init(ReviewMainParamVo params);
	public boolean checkFlowIsInProcess();
	
	public void checkForward();
	
	public void checkRegister();

	public void checkModify();
	
	public void checkDeptLeader();
	
	public void checkContractPreTrial();
	
	public void checkContractDealPerson();
	
	public void checkContractSimulate();
	
	public void checkComLeader();
	
	public void checkContractFinish();
	
}
