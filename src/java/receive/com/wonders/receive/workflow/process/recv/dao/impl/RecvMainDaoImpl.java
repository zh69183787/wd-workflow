/**
 * 
 */
package com.wonders.receive.workflow.process.recv.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.receive.workflow.common.service.CommonService;
import com.wonders.receive.workflow.process.recv.dao.RecvMainDao;
import com.wonders.receive.workflow.process.recv.model.bo.RecvMainBo;

/** 
 * @ClassName: RecvMainDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:00 
 *  
 */
@Repository("receive-recvMainDao")
public class RecvMainDaoImpl implements RecvMainDao{
	
	private CommonService service;
	@Override
	public RecvMainBo findBoByParam(String pname,String pincident){
		RecvMainBo bo = new RecvMainBo();
		String hql = "from RecvMainBo t where t.removed=0 and t.modleId = ? and t.instanceId = ?";
		Object[] params = new Object[]{pname,pincident};
		@SuppressWarnings("unchecked")
		List<RecvMainBo> list = this.service.ListByHql(hql, params);
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
