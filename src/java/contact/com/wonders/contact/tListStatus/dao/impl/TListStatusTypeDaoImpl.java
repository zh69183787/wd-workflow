package com.wonders.contact.tListStatus.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.contact.tListStatus.dao.TListStatusTypeDao;
import com.wonders.contact.tListStatus.model.bo.TListStatusType;
import com.wonders.util.DateUtil;
import com.wonders.util.DbUtil;

@Repository("contact-tListStatusTypeDao")
public class TListStatusTypeDaoImpl implements TListStatusTypeDao{

	private HibernateTemplate hibernateTemplate;
	private DbUtil dbUtil;
	
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
	
	@SuppressWarnings("unchecked")
	public TListStatusType findTListStatusTypeByCode(String code) {
		String hql = " from TListStatusType t where t.state = '1' and t.code = ?";
		List<TListStatusType> list = this.getHibernateTemplate().find(hql, code);
		if(list != null && !list.isEmpty())
			return list.get(0);
		return null;
	}
	@SuppressWarnings("unchecked")
	public TListStatusType findDefaultTListStatusType(){
		String hql = " from TListStatusType t where t.state = '1' and t.code = ?";
		List<TListStatusType> list = this.getHibernateTemplate().find(hql, TListStatusTypeDao.DEFAULTTLISTSTATUSTYPE);
		if(list != null && !list.isEmpty())
			return list.get(0);
		return null;
	}

	public String getChildCounts(String code) {
		String sql = "select count(*) as numbers from t_list_status t where t.INFOTYPE = ? and t.state=1";
		
		int i = dbUtil.getJdbcTemplate().queryForInt(sql, new Object[]{code});
		
		return String.valueOf(i);
	}

	public void delRecord(String code) {
		String hql = " update TListStatusType t set t.state='0' where t.state = '1' and t.code = ?";
		this.getHibernateTemplate().bulkUpdate(hql,code);
		
		/*
		String hql = " from TListStatusType t where t.state = '1' and t.code = ?";
		TListStatusType tlst = (TListStatusType)this.getHibernateTemplate().find(hql, code).get(0);
		tlst.setState("0");
		this.getHibernateTemplate().save(tlst);
		*/
	}

	public void modiRecord(String code, String name, String memo) {
		String hql = " update TListStatusType t set t.name=?,t.memo=?,t.uptdate=? where t.state = '1' and t.code = ?";
		String dateTime = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
		//String hql = " update TListStatusType t set t.state='0' where t.state = '1' and t.code = ?";
		this.getHibernateTemplate().bulkUpdate(hql,new Object[]{name,memo,dateTime,code});
		
		
		/*
		TListStatusType tlst = (TListStatusType)this.getHibernateTemplate().find(hql, code).get(0);

		tlst.setName(name);
		tlst.setMemo(memo);
		tlst.setUptdate(dateTime);
		this.getHibernateTemplate().save(tlst);
		*/
	}

	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		this.dbUtil = dbUtil;
	}
}
