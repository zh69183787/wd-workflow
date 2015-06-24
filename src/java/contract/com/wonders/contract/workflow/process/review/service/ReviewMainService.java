/**
 * 
 */
package com.wonders.contract.workflow.process.review.service;

import com.wonders.contract.workflow.process.review.model.vo.ReviewMainParamVo;
import com.wonders.contract.workflow.process.review.model.vo.ReviewMainVo;


/** 
 * @ClassName: ReviewMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface ReviewMainService {
	public void init(ReviewMainParamVo params);
	public void flowStepRegister(ReviewMainParamVo params);
	public void flowStepModify(ReviewMainParamVo params);
	public void flowStepDeptLeader(ReviewMainParamVo params);
	public void flowStepContractPreTrial(ReviewMainParamVo params);
	public void flowStepContractDealPerson(ReviewMainParamVo params);
	public void flowStepContractSimulate(ReviewMainParamVo params);
	public void flowStepComLeader(ReviewMainParamVo params);
	public void flowStepContractFinish(ReviewMainParamVo params);
	
	
	public ReviewMainVo flowStepForward(ReviewMainParamVo params);	
	public ReviewMainVo viewStepForward(ReviewMainParamVo params);	
	
	public ReviewMainVo print(ReviewMainParamVo params);
}
