package com.wonders.contact.page.service;

import java.util.List;

/**
 * 分页Service
 * @author xjb
 *
 */
public interface PageService {
	/**
	 * 根据Sql语句获得总记录数
	 * @return
	 */
	int countBySql(String sql);
	/**
	 * 根据Hql语句获得总记录数
	 * @return
	 */
	int countByHql(String hql);
	/**
	 * 分页查询
	 */
	List<String[]> findPaginationInfo(String sql, int startRow, int pageSize);
}
