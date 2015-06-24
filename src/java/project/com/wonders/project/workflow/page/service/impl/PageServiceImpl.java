package com.wonders.project.workflow.page.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.project.workflow.page.dao.PageDao;
import com.wonders.project.workflow.page.service.PageService;
@Repository("project-pageService")
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
	public void setDao(@Qualifier("project-pageDao")PageDao dao) {
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
