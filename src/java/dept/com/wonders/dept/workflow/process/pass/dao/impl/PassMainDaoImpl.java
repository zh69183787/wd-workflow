/**
 * 
 */
package com.wonders.dept.workflow.process.pass.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.dept.workflow.common.service.CommonService;
import com.wonders.dept.workflow.process.pass.dao.PassMainDao;
import com.wonders.dept.workflow.process.pass.model.bo.PassMainBo;

/** 
 * @ClassName: PassMainDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:00 
 *  
 */
@Repository("dept-passMainDao")
public class PassMainDaoImpl implements PassMainDao{
	
	private CommonService service;
	@Override
	public PassMainBo findBoByParam(String pname,String pincident){
		PassMainBo bo = new PassMainBo();
		String hql = "from PassMainBo t where t.removed=0 and t.process = ? and t.incident = ?";
		Object[] params = new Object[]{pname,pincident};
		@SuppressWarnings("unchecked")
		List<PassMainBo> list = this.service.ListByHql(hql, params);
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
	public void setService(@Qualifier(value="dept-commonService")CommonService service) {
		this.service = service;
	}
}
