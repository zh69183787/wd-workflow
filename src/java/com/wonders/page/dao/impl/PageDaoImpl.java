/**
 * 
 */
package com.wonders.page.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.page.dao.PageDao;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: PageDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-21 下午07:19:44 
 *  
 */
@Repository("pageDao")
public class PageDaoImpl implements PageDao{

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countBySql(String sql) {
		List list =this.hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
		return list.size();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countByHql(String hql) {
		List list = this.hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql).list();
		//System.out.println(hql);
		return list.size();
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String[]> findPaginationInfo(String sql, int startRow, int pageSize) {
		List list = new ArrayList();
		List<String[]> finlist = new ArrayList();
		SQLQuery query  = this.hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		list = query.setFirstResult(startRow).setMaxResults(pageSize).list();
		Iterator<String[]> it = list.iterator();
		while(it.hasNext()){
			Object[] objArr = (Object[])it.next();
			String[] ret = new String[objArr.length];
			for(int i=0; i<objArr.length; i++){
				ret[i] = StringUtil.getNotNullValueString(objArr[i]);
				//System.out.println(ret[i]);
			}
			finlist.add(ret);
		}
		return finlist;
	}
	
	
}
