/**
 * 
 */
package com.wonders.project.workflow.process.discard.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.project.workflow.common.service.CommonService;
import com.wonders.project.workflow.process.discard.dao.DiscardMainDao;
import com.wonders.project.workflow.process.discard.model.bo.DiscardMainBo;

/** 
 * @ClassName: DiscardMainDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:00 
 *  
 */
@Repository("project-discardMainDao")
public class DiscardMainDaoImpl implements DiscardMainDao{
	
	private CommonService service;
	@Override
	public DiscardMainBo findBoByParam(String pname,String pincident){
		DiscardMainBo bo = new DiscardMainBo();
		String hql = "from DiscardMainBo t left join fetch t.assets where t.removed=0 and t.modleId = ? and t.instanceId = ?";
		Object[] params = new Object[]{pname,pincident};
		@SuppressWarnings("unchecked")
		List<DiscardMainBo> list = this.service.ListByHql(hql, params);
		if(list.size()==0){
			return null;
		}else{
			bo = list.get(0);
		}	
		return bo;
		
	}
	
	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="project-commonService")CommonService service) {
		this.service = service;
	}
}
