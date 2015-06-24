package com.wonders.contact.common.service;

import java.util.List;

public interface CommonService {
	@SuppressWarnings("unchecked")
	public List ListByHql(final String hql);
	
	@SuppressWarnings("unchecked")
	public List ListByHql(final String hql,Object[] params);
	
	public void UpdateByHql(final String hql);
	
	public void save(Object obj);
	
	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz);

	public void update(Object obj);

	public void remove(Object obj);
}
