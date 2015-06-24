package com.wonders.project.workflow.common.dao;

import java.util.List;

public interface CommonDao {
	

	@SuppressWarnings("rawtypes")
	public List ListByHql(final String hql);
	

	@SuppressWarnings("rawtypes")
	public List ListByHql(final String hql,Object[] params);
	
	public void UpdateByHql(final String hql);
	
	public void save(Object obj);
	

	@SuppressWarnings("rawtypes")
	public Object load(String id,Class clazz);

	public void update(Object obj);

	public void remove(Object obj);
	
	public void saveBatch(List<?> list);
}