package com.wonders.contact.page.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.contact.page.dao.PageDao;
import com.wonders.contact.page.service.PageService;
@Repository("contact-pageService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED)
public class PageServiceImpl implements PageService{

	private PageDao dao;
	@Override
	public int countBySql(String sql) {
		return dao.countBySql(sql);
	}
	public PageDao getDao() {
		return dao;
	}
	@Autowired(required=false)
	public void setDao(@Qualifier("contact-pageDao")PageDao dao) {
		this.dao = dao;
	}
	@Override
	public int countByHql(String hql) {
		return dao.countByHql(hql);
	}
	@Override
	public List<String[]> findPaginationInfo(String sql, int startRow, int pageSize) {
		return dao.findPaginationInfo(sql, startRow, pageSize) ;
	}
	
	
}
