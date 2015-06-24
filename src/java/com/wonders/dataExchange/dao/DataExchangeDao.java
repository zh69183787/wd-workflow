/**
 * 
 */
package com.wonders.dataExchange.dao;

import java.util.Map;

/** 
 * @ClassName: DataExchangeDao 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月12日 下午1:27:24 
 *  
 */
public interface DataExchangeDao {
	public Object load(String id,Class<?> clazz);
	
	public void update(Object obj) ;
	
	public Object findBoCount(String hql,Map<String,Object> filter);
}
