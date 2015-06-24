/**
 * 
 */
package com.wonders.dataExchange.dao.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.dataExchange.dao.DataExchangeDao;

/** 
 * @ClassName: DataExchangeDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月12日 下午1:26:51 
 *  
 */

@Repository("dataExchangeDao")
public class DataExchangeDaoImpl implements DataExchangeDao{
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object load(String id,Class clazz) {
		Object instance = getHibernateTemplate().get(clazz, id);
		return instance;
	}
	
	public void update(Object obj) {
		getHibernateTemplate().update(obj);
	}
	
	public Object findBoCount(String hql,Map<String,Object> filter){
		for(Map.Entry<String, Object> entry : filter.entrySet()){
			hql += " and " +entry.getKey() +" =:" + entry.getKey();
		}
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		for(Map.Entry<String, Object> entry : filter.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.uniqueResult();
	}
}
