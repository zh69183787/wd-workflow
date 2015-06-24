package com.wonders.project.workflow.page.dao;

import java.util.List;

public interface PageDao {
	int countBySql(String sql);
	int countByHql(String hql);
	public List<String[]> findPaginationInfo(String sql, int startRow, int pageSize);
}
