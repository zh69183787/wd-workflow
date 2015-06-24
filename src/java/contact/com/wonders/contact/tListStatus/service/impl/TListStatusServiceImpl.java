package com.wonders.contact.tListStatus.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.wonders.contact.tListStatus.dao.TListStatusDao;
import com.wonders.contact.tListStatus.model.bo.TListStatus;
import com.wonders.contact.tListStatus.service.TListStatusService;

@Repository("contact-tListStatusService")
@Scope("prototype")
public class TListStatusServiceImpl implements TListStatusService{
	
	private TListStatusDao dao;
	
	@SuppressWarnings("unchecked")
	public List findTListStatusByCode(String code){
		return dao.findTListStatusByParentCode(code);
	}

	public Boolean findChildRecord(String parentCode) {
		//String hql = " from TListStatus t where t.state = '1' and t.infotype.code = ? and t.infotype.state = '1'";
		String hql="from TListStatus t where t.state=1 and 1=1 and t.infotype.code=? and t.infotype.state = '1' order by t.optorder";
		if(this.dao.findByHQL(hql, parentCode).size()>0){
			return true;
		}else{
			String hql1 = "from TListStatusType t where t.state=1 and t.code=?";
			if(this.dao.findByHQL(hql1, parentCode).size()>0){
				return true;
			}else{
				return false;
			}
		}
	}

	
	@SuppressWarnings("unchecked")
	public List findTListStatusByRefId(String refId) {
		String hql = "from TListStatus t where t.state=1 and t.refsid.sid = ? order by t.optorder";
		return this.dao.findByHQL(hql, Long.parseLong(refId));
	}
	
	@SuppressWarnings("unchecked")
	public List findTListStatusBySID(String sid){
		String hql = "from TListStatus t where t.state=1 and t.sid = ?";
		return this.dao.findByHQL(hql, Long.valueOf(sid));
	}
	public Boolean Exist(String sid) {
		String hql="from TListStatusType t where t.state=1 and t.code = ?";
		if(this.dao.findByHQL(hql,sid).size()>0){
			return false;
		}
		String hql1="from TListStatus t where t.state=1 and t.sid = ?";
		if(this.dao.findByHQL(hql1, Long.valueOf(sid)).size()>0){
			return true;
		}else{
			return false;
		}
	}

	public String getChildCounts(long sid) {
		String hql = "select count(t.sid) from TListStatus t where t.state=1 and t.refsid.sid = ?";
		
		return this.dao.findByHQL(hql,Long.valueOf(sid)).get(0).toString();
	}

	public void saveChildListStatus(Object obj) {
		this.dao.getHibernateTemplate().save(obj);
	}

	public void delRecord(String sid) {
		this.dao.delRecord(sid);
	}

	public void modiRecord(String sid, String name, String memo, String xh){
		this.dao.modiRecord(sid, name, memo, xh);
	}

	@SuppressWarnings("unchecked")
	public List findTListStatusForUnitName(String line) {
		String hql1 = "from TListStatus t where t.content = ?";
		TListStatus obj = (TListStatus)this.dao.findByHQL(hql1,line).get(0);
		String hql = "from TListStatus t where t.state = 1 and 1=1 and t.refsid = ?";
		return this.dao.findByHQL(hql,obj.getSid());
	}

	public TListStatus findTListStatusByCodeAndContent(String code, String content) {
		String hql = "from TListStatus t where t.content = ? and t.infotype.code = ?";
		TListStatus obj = (TListStatus)this.dao.findByHQL(hql,content,code).get(0);
		return obj;
	}

	public Integer getCurrentOptOrder(String infoType) {
		String hql = "select max(t.optorder) from TListStatus t where t.infotype.code = ?";
		Integer i = new Integer(0);
		if(this.dao.findByHQL(hql, new Integer(1)).size()>0){
			i = (Integer)this.dao.findByHQL(hql, infoType).get(0)+1;
		}else{
			i = 1;
		}
		return i;
	}


	public TListStatusDao getDao() {
		return dao;
	}
	
	@Autowired(required=false)
	public void setDao(@Qualifier("contact-tListStatusDao")TListStatusDao dao) {
		this.dao = dao;
	}

}
