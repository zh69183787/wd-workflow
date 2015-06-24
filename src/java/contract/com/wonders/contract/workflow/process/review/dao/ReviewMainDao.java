/**
 * 
 */
package com.wonders.contract.workflow.process.review.dao;

import java.util.List;

import com.wonders.contract.workflow.process.review.model.bo.ReviewMainBo;
import com.wonders.contract.workflow.process.review.model.bo.ReviewQuestionBo;

/** 
 * @ClassName: ReviewMainDao 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:14 
 *  
 */
public interface ReviewMainDao {
	public List<ReviewQuestionBo> findQuestion(String purchaseType);
	public ReviewMainBo findBoByParam(String pname,String pincident);
}
