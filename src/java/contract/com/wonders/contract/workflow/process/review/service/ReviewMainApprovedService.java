/**
 * 
 */
package com.wonders.contract.workflow.process.review.service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.contract.workflow.process.review.model.vo.ReviewMainParamVo;

/** 
 * @ClassName: ReviewMainApprovedService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:52:52 
 *  
 */
public interface ReviewMainApprovedService {
	public ApprovedInfoBo saveApprovedInfo(ReviewMainParamVo params);
}
