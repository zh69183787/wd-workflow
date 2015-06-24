package com.wonders.contact.todo.instance.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.constant.CommonFlowConstants;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.constant.DeptContactFlowConstants;
import com.wonders.contact.processController.constant.ProcessControllerConstants;
import com.wonders.contact.todo.constant.TodoConstants;
import com.wonders.contact.todo.instance.TodoInstance;
import com.wonders.util.StringUtil;

public class DeptContactTodoInstance extends TodoInstance {
	private static Object[] params = { DeptContactConstants.SUBPROCESSNAME, DeptContactConstants.PROCESSNAME, CommonFlowConstants.STATUS_INCIDENTS_ACTIVE,
		CommonFlowConstants.STATUS_TASKS_ACTIVE, String.valueOf(ProcessControllerConstants.STATUS_ACTIVE), DeptContactFlowConstants.STEPNAME_LOWER };
	
	
	
	
	static final String appName = "workflow";
	//static final int type = 1;
	//static final int status = 0;
	
	Gson gson = new Gson();
	
	/**
	 * 待办事项插入sql
	 */
	private static final String sqlInsert = "insert into t_todo_item(app,type,key,occurtime,title,data,userid,loginname,status) values(?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 业务表扫描状态修改
	 */
	private static final String sqlUpdate = "update tasks t set t.scaned = 1 where t.taskid = ?";
	
	
	private JdbcTemplate jt;
	
	static Logger log = SimpleLogger.getLogger(DeptContactTodoInstance.class);
	
	
	@Override
	public void action(JdbcTemplate jt) {
		this.jt = jt;
		
		final List<Map<String,Object>> list = fetchData();
		
		if(list.size()==0){
			log.debug("no data!");
			return;
		}
		
		BatchPreparedStatementSetter pssInsert = new BatchPreparedStatementSetter() {
			@Override
			public int getBatchSize() {
				return list.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Map<String,Object> map = list.get(i);
				
				String taskid = StringUtil.getNotNullValueString(map.get("taskid"));
				String theme = StringUtil.getNotNullValueString(map.get("theme"));
				String loginname = StringUtil.getNotNullValueString(map.get("assignedtouser"));
				String occurTime = StringUtil.getNotNullValueString(map.get("start_time"));
				
				map.put("content", StringUtil.subStr(StringUtil.getNotNullValueString(map.get("content")), 100));
				
				ps.setString(1, appName);
				ps.setInt(2, TodoConstants.TYPE_DEPTCONTACT);
				ps.setString(3, taskid);
				ps.setString(4, occurTime);
				ps.setString(5, theme);
				ps.setString(6, gson.toJson(map));
				
				/*
				 * TODO 查询用户ID
				 */
				ps.setString(7, "");
				ps.setString(8, loginname);
				
				ps.setInt(9, TodoConstants.STATUS_TODO);
			}
		};
		
		jt.batchUpdate(sqlInsert, pssInsert);
		
		BatchPreparedStatementSetter pssUpdate = new BatchPreparedStatementSetter() {
			@Override
			public int getBatchSize() {
				return list.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Map<String,Object> map = list.get(i);
				String taskid = StringUtil.getNotNullValueString(map.get("taskid"));
				ps.setString(1, taskid);
			}
		};
		
		jt.batchUpdate(sqlUpdate, pssUpdate);
		
log.debug(list.size()+" records insert/update!");
	}

	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> fetchData(){
		String sql = " select " +
					" main.id,main.main_unit,main.copy_unit,main.contact_date,main.reply_date,main.theme,main.content," +
					" main.content_attachment_id,main.create_deptname,main.initiator_name,main.start_time" +
					" ,tree.pname,tree.pincident,tree.cname,tree.cincident,t.steplabel,t.assignedtouser,t.taskid from \n"+
					" tasks t,\n"+
					" (select td.pname,td.pincident,td.cname,td.cincident,td.status from t_dept_contact_tree td \n"+
					" where td.cname=? and td.removed!=1\n"+
					" union\n"+
					" select td.cname,td.cincident,td.cname,td.cincident,td.status from t_dept_contact_tree td \n"+
					" where td.cname=? and td.removed!=1\n"+
					" ) tree,\n"+
					" t_dept_contact_main main,incidents i \n"+
					" where 1=1 \n" +
					" and i.processname = tree.pname and i.incident = tree.pincident \n"+
					" and i.status = ? and t.status = ? and tree.status = ?\n"+
					" and t.processname = tree.cname and t.incident = tree.cincident\n"+
					" and tree.pname = main.processname\n"+
					" and tree.pincident = main.incidentno\n"+
					" and t.steplabel != ?" +
					" and t.recipienttype != 0" +
					" and t.scaned = 0"+
					" order by t.starttime desc";
		
		List<Map<String,Object>> list = jt.queryForList(sql,params);
		
		return list;
	}
	
}
