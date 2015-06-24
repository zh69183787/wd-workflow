package com.wonders.deptDoc.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.deptDoc.dao.DeptDocDao;
import com.wonders.deptDoc.model.bo.ZDocsFile;

@Repository("deptDocDao")
public class DeptDocDaoImpl<T> implements DeptDocDao<T>{
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	public T getBo(String sql,Object[] o){
		List<T> result =  this.hibernateTemplate.find(sql, o);
		if(result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
	public void saveAll(List<ZDocsFile> list){
		this.hibernateTemplate.saveOrUpdateAll(list);
	}
	
	public void save(Object o){
		this.hibernateTemplate.save(o);
	}
}
