package com.wonders.contact.tListStatus.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.wonders.contact.tListStatus.model.bo.TListStatus;

public interface TListStatusDao{
	TListStatus findTListStatusById(long id);
	List<TListStatus> findTListStatusByParentCode(String code);
	List<TListStatus> findDefaultTListStatus();
	void delRecord(String sid);
	void modiRecord(String sid, String name, String memo, String xh);
	
	@SuppressWarnings("unchecked")
	List findByHQL(String hql,Object... params);
	
	HibernateTemplate getHibernateTemplate();
}
