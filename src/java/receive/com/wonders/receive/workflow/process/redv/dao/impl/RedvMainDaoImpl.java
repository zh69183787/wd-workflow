/**
 * 
 */
package com.wonders.receive.workflow.process.redv.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.receive.workflow.common.service.CommonService;
import com.wonders.receive.workflow.process.redv.dao.RedvMainDao;
import com.wonders.receive.workflow.process.redv.model.bo.RedvMainBo;

/** 
 * @ClassName: RedvMainDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:00 
 *  
 */
@Repository("receive-redvMainDao")
public class RedvMainDaoImpl implements RedvMainDao{
	
	private CommonService service;
	@Override
	public RedvMainBo findBoByParam(String pname,String pincident){
		RedvMainBo bo = new RedvMainBo();
		String hql = "from RedvMainBo t where t.removed=0 and t.activeId = ? and t.processInstanceid = ?";
		Object[] params = new Object[]{pname,pincident};
		@SuppressWarnings("unchecked")
		List<RedvMainBo> list = this.service.ListByHql(hql, params);
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
	public void setService(@Qualifier(value="receive-commonService")CommonService service) {
		this.service = service;
	}
}
