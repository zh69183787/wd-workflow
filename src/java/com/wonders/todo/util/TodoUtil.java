/**
 * 
 */
package com.wonders.todo.util;

import com.wonders.page.util.PageUtils;
import com.wonders.todo.model.vo.TodoItemVo;


/** 
 * @ClassName: TodoUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-9-4 下午9:31:44 
 *  
 */
public class TodoUtil {

	public static String generateSql(String loginNames, TodoItemVo vo){
		String sql = " select t.title, "+
	        	" t.pname," +
	        	" t.pincident," +
	        	" t.cname," +
	        	" t.cincident ," +
	        	" t.stepname ," +
	         	" t.occurtime," +
	         	" t.taskid,"+
	        	" t.tasktype, "+
	        	" t.loginname,"+
	        	" t.url"+
	        	" from t_todo_item t" +
	        	" where t.status=0 and t.removed =0 " +
	        	" and t.loginname in ("+loginNames+")";
		if(vo != null){
			String[] queryNameArr = {"pname","title","occurtime"};
			String[] queryTypeArr = {"textType","textType","dateType"};
			String[] queryResultArr = {vo.getTypename(),vo.getTitle(),vo.getOccurtime()};
			sql = PageUtils.generateSQLByType(sql, queryNameArr, queryResultArr, queryTypeArr);
			sql += " order by occurtime desc";
		}
		System.out.println(sql);
		return sql;
	}
}
