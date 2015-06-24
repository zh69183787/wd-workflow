/*    */ package com.wonders.send.util;
/*    */ 
/*    */ import com.wonders.send.common.exception.ProcessException;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;

/*    */ import java.util.ArrayList;
import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("publicFunction")
/*    */ public class PublicFunction
/*    */ {
			private static DbUtil dbUtil;
/*    */   public static List<String> getUserInWorkFLow(String processName, String incidentNo, String StepName, String typeId)
/*    */   {
/* 23 */     List<String> relist = new ArrayList<String>();
/* 24 */     String sql = "select username,userfullname,parent_dept from t_flowsetp_config where 1=1 ";
/* 25 */     if ((processName == null) || (processName.length() <= 0) || (typeId == null) || (typeId.length() <= 0)) {
/* 26 */       sql = sql + " and 1<>1";
/*    */     } else {
/* 28 */       sql = sql + " and PROCESSNAME = '" + processName + "' ";
/* 29 */       sql = sql + " and typeid = '" + typeId + "' ";
/*    */     }
/* 31 */     if ((StepName != null) && (StepName.length() > 0)) {
/* 32 */       sql = sql + " and STEPNAME = '" + StepName + "' ";
/*    */     }
/* 34 */     if ((incidentNo != null) && (incidentNo.length() > 0)) {
/* 35 */       sql = sql + " and INSTANCEID = '" + incidentNo + "' ";
/*    */     }
/* 37 */     List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
/* 38 */     if (list.size() > 0) {
/* 39 */       relist.add(StringUtil.getNotNullValueString(((Map<String,Object>)list.get(0)).get("username")) + 
/* 40 */         ":" + StringUtil.getNotNullValueString(((Map<String,Object>)list.get(0)).get("userfullname")) + 
/* 41 */         ":" + StringUtil.getNotNullValueString(((Map<String,Object>)list.get(0)).get("parent_dept")));
/*    */     }
/*    */ 
/* 44 */     if (relist.size() == 0) {
/* 45 */       throw new ProcessException("relist为空！");
/*    */     }
/* 47 */     return relist;
/*    */   }
/*    */ 
/*    */   public static String getBoByFlow(String tableName, String modelCol, String modelId, String incidentCol, String incidentNo, String primaryKey)
/*    */   {
/* 65 */     String id = "";
/* 66 */     String sql = "";
/* 67 */     if ((tableName != null) && (tableName.length() > 0)) {
/* 68 */       sql = "select * from " + tableName + " t where 1=1 and removed = 0";
/*    */     }
/* 70 */     if ((modelCol != null) && (modelCol.length() > 0) && (modelId != null) && (modelId.length() > 0))
/* 71 */       sql = sql + " and t." + modelCol + " = '" + modelId + "' ";
/*    */     else {
/* 73 */       sql = sql + " and 2=1 ";
/*    */     }
/* 75 */     if ((incidentCol != null) && (incidentCol.length() > 0) && (incidentNo != null) && (incidentNo.length() > 0))
/* 76 */       sql = sql + " and t." + incidentCol + " = '" + incidentNo + "' ";
/*    */     else {
/* 78 */       sql = sql + " and 2=1 ";
/*    */     }
/* 80 */     List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
/* 81 */     if (list.size() > 0) {
/* 82 */       id = StringUtil.getNotNullValueString(((Map<String,Object>)list.get(0)).get(primaryKey));
/*    */     }
/* 84 */     if (id.length() == 0) {
/* 85 */       throw new ProcessException("id为空！");
/*    */     }
/* 87 */     return id;
/*    */   }


			/**
			 * 按给定条件在流程步骤配置表中查找部门ids信息
			 * @author liu_feng
			 * @param processName - 流程名称
			 * @param stepName - 步骤名称
			 * @param typeId - 业务分类ID
			 * @param loginName - 登录名
			
			 * @return 部门ids - deptId1,deptId2,...deptIdn
			 * @throws HibernateException
			 */
			public static String findDeptIdsFromFC(String processName, String stepName, 
					String typeId, String loginName )  {
				String sql = "select distinct fc.dept" +
					" from t_flowsetp_config fc" +
					" where fc.processname = '" + processName + "'" +
					"   and fc.stepname = '" + stepName + "'" +			
					"	and fc.typeid = '" + typeId + "'";
				//loginName判空
				if(!loginName.equals(null) && !loginName.equals("") &&!loginName.equalsIgnoreCase("null")) { //loginName不为空
			
					sql += 	"   and fc.username = '" + loginName + "'";	
				}
				
				//按给定条件在流程步骤配置表中查找部门id
				List<Map<String,Object>> deptList = dbUtil.getJdbcTemplate().queryForList(sql);
				
				if(deptList.size() == 0 || deptList.get(0) == null) { //流程步骤配置表中没有查找到-无记录或有记录但dept为空
					return null;
				}else {  //流程步骤配置表中查找到相关部门信息
			
					return deptList.get(0).toString();						
				}	
			}
			
			//判断部门为 单位 或者 部门
			public static boolean judgeDeptInfo(String deptId){
				//在流程步骤配置表中查找部门ids
				//String coms = PublicFunction.findDeptIdsFromFC("processes", "company", "0", "ADMIN");
				String deps = findDeptIdsFromFC("new_processes", "new_dept", "0", "ADMIN");
				if(deptId!=null&&deps.indexOf(deptId)>-1){
					return true;
				}
				return false;
			}
			
			
			public static boolean judgeDeptInfoForNotice(String deptId){
				//在流程步骤配置表中查找部门ids
				String deps = findDeptIdsFromFC("dept_code", "dept", "0", "ADMIN");
				if(deptId!=null&&deps.indexOf(deptId)>-1){
					return true;
				}
				return false;
			}
			
			public static boolean judgeSendPass(String deptId){
				//在流程步骤配置表中查找部门ids
				String deps = findDeptIdsFromFC("send_notice", "dept", "0", "ADMIN");
				if(deptId!=null&&deps.indexOf(deptId)>-1){
					return true;
				}
				return false;
			}
			
			//根据工号（12位）得到id
			public String getIdByLoginName(String loginName){
				String result = "";
				String sql = "select id from cs_user where login_name = '"+loginName+"'";
				List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
				if(list!=null&&list.size()>0){
					result = String.valueOf(list.get(0).get("id"));
				}
				return result;
			}
			
			//根据姓名得到id
			public String getIdByName(String name){
				String result = "";
				String sql = "select id from cs_user where name = '"+name+"' and id>0 and ext1 = 'true'";
				List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
				if(list!=null&&list.size()>0){
					result = String.valueOf(list.get(0).get("id"));
				}
				return result;
			}
			
			//从t_todo_item表判断当前是否是代办项
			public boolean ifDbx(String cname,String cincident,String loginName,String stepName){
				boolean result = false;
				String sql = "select * from t_todo_item t where t.cname = '"+cname+"' and t.cincident = "+cincident+" and t.loginname = 'ST/"+loginName+"' and t.stepname = '"+stepName+"' and t.status = 0 and t.removed = 0";
				//System.out.println("ifDbx_sql===="+sql);
				List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
				if(list!=null&&list.size()>0){
					result = true;
				}
				return result;
			}
			
			//获得发起人账号
			public static String getOaInitator(String pname,String pincident){
				String sql = "select distinct substr(a.username,4) as initiator " +
						" from t_approvedinfo a where a.process = ? " +
						" and a.incidentno = ? and a.stepname='Begin'" +
						" and a.status=2 and a.username is not null";
				
				Object[] params = new Object[]{pname,pincident};
				String initiator = dbUtil.getJdbcTemplate().queryForObject(sql, String.class, params);
				return initiator;
			}
			
			//获得用户部门
			public static List<String> getUserDeptId(String user){
				String sql = "select distinct v.parent_node_id||'' from v_userdep v " +
						" where v.LOGIN_NAME in ( "+user+" ) and v.REMOVED=0";
				List<String> list = dbUtil.getJdbcTemplate().queryForList(sql, String.class);
				return list;
			}
			
			//获取文件柜ID
			public static Map<String,String> getCatalog(List<String> src){
				String depts = "";
				if(src != null && src.size() > 0){
					for(String d : src){
						depts += "'" + d + "',";
					}
					if(depts.length() > 0){
						depts = depts.substring(0,depts.length()-1);
					}
				}
				Map<String,String> map = new HashMap<String,String>();
				String sql = "select distinct t.dept_id,tc.sid from t_docs_dept_cfg t,t_docs_catalog tc " +
						" where t.dept_id in ("+ depts +") and t.cate_sid=tc.parent_sid" +
						" and tc.state=1 and t.state = 1";
				List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
				if(list != null && list.size() > 0){
					for(Map<String,Object> m : list){
						map.put(StringUtil.getNotNullValueString(m.get("dept_id")), StringUtil.getNotNullValueString(m.get("sid")));
					}
				}
				return map;
			}
			
			//取会签领导名单
			public String getHqld(){
				String result = "";
				String sql = "select t.leader from t_flowsetp_config t where t.stepname = 'hqld' and t.processname = '发文流程' and t.typeid = '120403'";
				List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
				if(list!=null&&list.size()>0){
					result = String.valueOf(list.get(0).get("leader"));
				}
				return result;
			}
			
			//根据老部门id得到id
			public String getReceiverId(String dept_id){
				String result = "";
				String sql = "select id from v_dept_receivers where dept_id = "+dept_id;
				List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
				if(list!=null&&list.size()>0){
					result = String.valueOf(list.get(0).get("id"));
				}
				return result;
			}
			
			public DbUtil getDbUtil() {
				return dbUtil;
			}
			
			@Autowired(required=false)
			public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
				this.dbUtil = dbUtil;
			}
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.util.PublicFunction
 * JD-Core Version:    0.6.0
 */