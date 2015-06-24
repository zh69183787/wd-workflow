package com.wonders.contact.tListStatus.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.contact.tListStatus.dao.TListStatusDao;
import com.wonders.contact.tListStatus.dao.TListStatusTypeDao;
import com.wonders.contact.tListStatus.model.bo.TListStatus;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

@Repository("contact-tListStatusDao")
public class TListStatusDaoImpl implements TListStatusDao{
	
	private HibernateTemplate hibernateTemplate;

	public TListStatus get(long id){
		TListStatus tls = (TListStatus) this.getHibernateTemplate().load(TListStatus.class, id);
		return tls;
	}
	
	public TListStatus get(String id){
		TListStatus tls = (TListStatus) this.getHibernateTemplate().load(TListStatus.class, Long.parseLong(id));
		return tls;
	}
	
	
	@SuppressWarnings("unchecked")
	public TListStatus findTListStatusById(long id) {
		
		String hql = " from TListStatus t where t.state = '1' and t.sid =? ";
		//Object[] params = {id};
		List<TListStatus> list = this.getHibernateTemplate().find(hql,id);
		
		if(list != null && !list.isEmpty())
			return (TListStatus)list.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<TListStatus> findTListStatusByParentCode(String code){
		String hql = " from TListStatus t where t.state = 1 and t.infotype.code = ? and t.infotype.state = '1' and t.refsid is NULL order by t.optorder";
		//Object[] params = {code};
		List<TListStatus> list = this.getHibernateTemplate().find(hql,code);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<TListStatus> findDefaultTListStatus(){
		String hql = " from TListStatus t where t.state = '1' and t.infotype.code = ? and t.infotype.state = '1'";
		//Object[] params = {TListStatusTypeDao.DEFAULTTLISTSTATUSTYPE};
		
		List<TListStatus> list = this.getHibernateTemplate().find(hql,TListStatusTypeDao.DEFAULTTLISTSTATUSTYPE);
		return list;
	}

	public void delRecord(String sid) {
		String hql = " update TListStatus t set t.state='0' where t.state = '1' and t.sid = ?";
		this.getHibernateTemplate().bulkUpdate(hql,sid);

	}

	public void modiRecord(String sid, String name, String memo, String xh) {
		TListStatus tls = this.get(sid);
		
		String dateTime = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
		
		tls.setContent(name);
		tls.setMemo(memo);
		tls.setUptdate(dateTime);
		if(!"".equals(StringUtil.getNotNullValueString(xh))){
			tls.setOptorder(Integer.valueOf(xh));
		}
		this.getHibernateTemplate().update(tls);

	}
	
	@SuppressWarnings("unchecked")
	public List findByHQL(String hql, Object... params) {
		List list = new ArrayList();
		if(params!=null&&params.length>0){
			list = this.getHibernateTemplate().find(hql,params);
		}else{
			list = this.getHibernateTemplate().find(hql);
		}
		return list;
	}
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	
	
	
	
}
