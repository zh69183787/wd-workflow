/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.discipline.workflow.common.service.CommonService;
import com.wonders.discipline.workflow.process.recv.dao.DcpRecvMainDao;
import com.wonders.discipline.workflow.process.recv.model.bo.DcpRecvMainBo;

/** 
 * @ClassName: DcpRecvMainDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:00 
 *  
 */
@Repository("discipline-dcpRecvMainDao")
public class DcpRecvMainDaoImpl implements DcpRecvMainDao{
	
	private CommonService service;
	@Override
	public DcpRecvMainBo findBoByParam(String pname,String pincident){
		DcpRecvMainBo bo = new DcpRecvMainBo();
		String hql = "from DcpRecvMainBo t where t.removed=0 and t.modleId = ? and t.instanceId = ?";
		Object[] params = new Object[]{pname,pincident};
		@SuppressWarnings("unchecked")
		List<DcpRecvMainBo> list = this.service.ListByHql(hql, params);
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
	public void setService(@Qualifier(value="discipline-commonService")CommonService service) {
		this.service = service;
	}
}
