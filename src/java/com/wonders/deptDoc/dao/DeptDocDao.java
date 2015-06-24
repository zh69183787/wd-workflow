package com.wonders.deptDoc.dao;

import java.util.List;

import com.wonders.deptDoc.model.bo.ZDocsFile;


public interface DeptDocDao<T> {
	public T getBo(String sql,Object[] o);
	public void saveAll(List<ZDocsFile> list);
	public void save(Object o);
}
