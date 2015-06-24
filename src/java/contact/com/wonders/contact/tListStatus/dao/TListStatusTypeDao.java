package com.wonders.contact.tListStatus.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.wonders.contact.tListStatus.model.bo.TListStatusType;

public interface TListStatusTypeDao{
	public static final String DEFAULTTLISTSTATUSTYPE = "qita_att_dic";
	TListStatusType findTListStatusTypeByCode(String code);
	TListStatusType findDefaultTListStatusType();
	String getChildCounts(String code);
	void delRecord(String code);
	void modiRecord(String code, String name, String memo);
	
	@SuppressWarnings("unchecked")
	List findByHQL(String hql,Object... params);
	
	HibernateTemplate getHibernateTemplate();
}
