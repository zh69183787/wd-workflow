package com.wonders.contact.common.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.contact.common.dao.CommonDao;

@Repository("contact-commonDao")
public class CommonDaoImpl implements CommonDao {

	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	public List ListByHql(final String hql){
		List list = hibernateTemplate.find(hql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List ListByHql(final String hql,Object[] params){
		List list = hibernateTemplate.find(hql, params);
		return list;
	}
	
	
	public void UpdateByHql(final String hql){
		hibernateTemplate.bulkUpdate(hql);
	}
	
	public void save(Object obj) {
		getHibernateTemplate().save(obj);
	}

	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz) {
		Object instance = getHibernateTemplate().get(clazz, id);
		return instance;
	}
	
	public void update(Object obj) {
		getHibernateTemplate().update(obj);
	}

	public void remove(Object obj) {
		getHibernateTemplate().delete(obj);
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

