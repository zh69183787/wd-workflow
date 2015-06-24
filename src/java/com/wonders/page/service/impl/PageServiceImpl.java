/**
 * 
 */
package com.wonders.page.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.page.dao.PageDao;
import com.wonders.page.service.PageService;

/** 
 * @ClassName: PageServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-21 下午07:20:04 
 *  
 */
@Repository("pageService")
@Scope("prototype")
@Transactional(value="txManager",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class PageServiceImpl implements PageService{
	private PageDao pageDao;

	public PageDao getPageDao() {
		return pageDao;
	}

	@Autowired(required=false)
	public void setPageDao(@Qualifier("pageDao")PageDao pageDao) {
		this.pageDao = pageDao;
	}
	
	public int countBySql(String sql){
		return this.pageDao.countBySql(sql);
	}
	public List<String[]> findPaginationInfo(String sql, int startRow, int pageSize){
		return this.pageDao.findPaginationInfo(sql, startRow, pageSize);
	}
	
	public int countByHql(String hql){
		return this.pageDao.countByHql(hql);
	}

}
