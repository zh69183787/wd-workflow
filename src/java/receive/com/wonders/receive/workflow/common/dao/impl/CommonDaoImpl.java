package com.wonders.receive.workflow.common.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.receive.workflow.common.dao.CommonDao;


@Repository("receive-commonDao")
public class CommonDaoImpl implements CommonDao {

	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("rawtypes")
	public List ListByHql(final String hql){
		List list = hibernateTemplate.find(hql);
		return list;
	}

	@SuppressWarnings("rawtypes")
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


	@SuppressWarnings({ "rawtypes", "unchecked" })
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

	public void saveBatch(List<?> list){
		getHibernateTemplate().saveOrUpdateAll(list);
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

