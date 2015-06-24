package com.wonders.contact.page.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.contact.page.service.PageService;
import com.wonders.util.StringUtil;

@Repository("contact-queryUtil")
public class QueryUtil {

	private PageService pageService;
	// private List<ID> IDs = null;
	private Page page = null;
	private List<String[]> ps = null;

	// private List commonList = null;
	// private List commonList1 = null;
	// 带查询条件
	public void commonQueryModule(String sql, String[] queryNameArr, String[] queryResultArr, HttpServletRequest request, String attListName, String idsName,
			String pageName) {
		// 生成SQL语句(包含带查询条件)
		sql = generateSQL(sql, queryNameArr, queryResultArr);

		// commonList = pageResult(sql,request);
		// request.setAttribute(idsName, IDs);
		// request.setAttribute(pageName, page);
		// request.setAttribute(attListName, commonList);

	}

	// 带查询条件区别类型
	public void commonQueryModule(String sql, String[] queryNameArr, String[] queryResultArr, String[] queryTypeArr, HttpServletRequest request,
			String attListName, String idsName, String pageName) {
		// 生成SQL语句
		sql = generateSQLByType(sql, queryNameArr, queryResultArr, queryTypeArr);
		/*
		 * commonList = pageResult(sql,request); request.setAttribute(idsName,
		 * IDs); request.setAttribute(pageName, page);
		 * request.setAttribute(attListName, commonList);
		 */
	}

	public void commonQueryModule(String sql, HttpServletRequest request, String attListName, String idsName, String pageName) {
		/*
		 * commonList1 = pageResult(sql,request); request.setAttribute(idsName,
		 * IDs); request.setAttribute(pageName, page);
		 * request.setAttribute(attListName, commonList1);
		 */
	}

	public static String generateSQLByType(String sql, String[] queryNameArr, String[] queryResultArr, String[] queryTypeArr) {
		StringBuffer sb = new StringBuffer("select * from (" + sql + ")");
		//int count = 0;

		sb.append(" where 1=1");

		for (int i = 0; i < queryNameArr.length; i++) {
			String type = StringUtil.getNotNullValueString(queryTypeArr[i]);
			String name = StringUtil.getNotNullValueString(queryNameArr[i]);
			String value = StringUtil.getNotNullValueString(queryResultArr[i]);
			
//System.out.println(type+" "+name+" "+value);
			
			if (queryResultArr == null || value.equals("") || value.equals("#")) {

			} else {
				//"selectType","selectType","selectType","selectType","dateType","dateType","dateType"
				if (type.equals("textType")) {
					sb.append(" and " + name + " LIKE '%" + queryResultArr[i] + "%'");
				} else if (type.equals("dateType")) {
					// 查询条件为日期的有可能只选一个开始时间或一个结束时间					String start = "";
					String end = "";
					try {
						start = StringUtil.getNotNullValueString(value.split("#")[0]);
						end = StringUtil.getNotNullValueString(value.split("#")[1]);
					} catch (Exception e) {}

					if (start.length() > 0) {
						sb.append(" and " + name + "" + " >= '" + start + "'");
					}
					if (end.length() > 0) {
						sb.append(" and " + name + "" + " <= '" + end + "'");
					}

				} else if (type.equals("selectType")) {
					sb.append(" and " + name + " = '" + value + "' ");
				}
			}
		}
		return sb.toString();
	}

	public static String generateSQL(String sql, String[] queryNameArr, String[] queryResultArr) {
		StringBuffer sb = new StringBuffer(sql);
		int count = 0;
		for (int i = 0; i < queryNameArr.length; i++) {
			if (queryResultArr[i].equals("")) {

			} else {
				if (count == 0) {
					sb.append(" where ").append(queryNameArr[i]).append(" LIKE '%").append(queryResultArr[i]).append("%'");
				} else {
					sb.append(" and ").append(queryNameArr[i]).append(" LIKE '%").append(queryResultArr[i]).append("%'");
				}
				count++;
			}
		}
		return sb.toString();
	}

	public List<String[]> pageResult(String sql, HttpServletRequest request) {
		/* 记录总数 */
		// PageService pageService = (PageService)SpringBeanUtil.getBean("pageService");
		int totalRows = (int) pageService.countBySql(sql);
		page = PageHelp.getPager(request, totalRows);
		//int totalPages = page.getTotalPages();
		/*
		 * IDs = new ArrayList<ID>(); for(int i=0;i<totalPages;i++){ ID id = new
		 * ID(); id.setID(i+1); IDs.add(id); }
		 */
		ps = pageService.findPaginationInfo(sql, page.getStartRow(), page.getPageSize());
		return ps;
	}

	public static String generateResultColSQL(List<String> cols, String sql, String condition) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select ");
		for (int i = 0; i < cols.size(); i++) {
			sb.append("t." + cols.get(i));
			if (i < cols.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append(" from(");
		sb.append(sql);
		sb.append(") t");
		sb.append(" where 1=1 " + condition);
		return sb.toString();
	}

	public static String generateResultColSQL(String[] strs, String sql, String condition) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select ");
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i]);
			if (i == strs.length - 1) {
				sb.append(" from(" + sql + ")");
			} else {
				sb.append(", ");
			}
		}
// System.out.println(sb.toString());
		return sb.toString();
	}

	
	
	public PageService getPageService() {
		return pageService;
	}

	@Autowired(required = false)
	public void setPageService(@Qualifier("contact-pageService") PageService pageService) {
		this.pageService = pageService;
	}
}
