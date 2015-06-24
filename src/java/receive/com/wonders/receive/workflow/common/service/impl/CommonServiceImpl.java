package com.wonders.receive.workflow.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.receive.workflow.common.dao.CommonDao;
import com.wonders.receive.workflow.common.service.CommonService;


@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Repository("receive-commonService")
@Scope("prototype")
public class CommonServiceImpl implements CommonService {

	private CommonDao dao;


	@SuppressWarnings("rawtypes")
	public List ListByHql(String hql) {
		return dao.ListByHql(hql);
	}


	@SuppressWarnings("rawtypes")
	public List ListByHql(final String hql,Object[] params){
		return dao.ListByHql(hql, params);
	}
	
	public void UpdateByHql(String hql) {
		dao.UpdateByHql(hql);
	}


	@SuppressWarnings("rawtypes")
	public Object load(String id, Class clazz) {
		return dao.load(id, clazz);
	}

	public void remove(Object obj) {
		dao.remove(obj);
	}

	public void save(Object obj) {
		dao.save(obj);
	}

	public void update(Object obj) {
		dao.update(obj);
	}
	
	public void saveBatch(List<?> list){
		dao.saveBatch(list);
	}
	
	
	public CommonDao getDao() {
		return dao;
	}
	
	@Autowired(required=false)
	public void setDao(@Qualifier(value="receive-commonDao")CommonDao dao) {
		this.dao = dao;
	}
}
