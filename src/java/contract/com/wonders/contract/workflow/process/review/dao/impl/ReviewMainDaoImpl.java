/**
 * 
 */
package com.wonders.contract.workflow.process.review.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.contract.workflow.common.service.CommonService;
import com.wonders.contract.workflow.process.review.dao.ReviewMainDao;
import com.wonders.contract.workflow.process.review.model.bo.ReviewMainBo;
import com.wonders.contract.workflow.process.review.model.bo.ReviewQuestionBo;

/** 
 * @ClassName: ReviewMainDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:00 
 *  
 */
@Repository("contract-reviewMainDao")
public class ReviewMainDaoImpl implements ReviewMainDao{
	
	private CommonService service;
	@Override
	public ReviewMainBo findBoByParam(String pname,String pincident){
		ReviewMainBo bo = new ReviewMainBo();
		String hql = "from ReviewMainBo t where t.removed=0 and t.process = ? and t.incident = ?";
		Object[] params = new Object[]{pname,pincident};
		@SuppressWarnings("unchecked")
		List<ReviewMainBo> list = this.service.ListByHql(hql, params);
		if(list.size()==0){
			return null;
		}else{
			bo = list.get(0);
		}	
		return bo;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ReviewQuestionBo> findQuestion(String purchaseType){
		String hql = "select distinct t from ReviewQuestionBo t left join fetch t.options where t.purchaseType = ?"
				+ " order by t.orders";
		Object[] params = new Object[]{purchaseType};
		List<ReviewQuestionBo> list = this.service.ListByHql(hql, params);
		return list;
	}
	
	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="contract-commonService")CommonService service) {
		this.service = service;
	}
}
