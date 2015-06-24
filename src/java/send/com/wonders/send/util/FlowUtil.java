/*     */ package com.wonders.send.util;
/*     */ 
import com.wonders.send.common.exception.ProcessException;
import com.wonders.send.common.model.vo.UserInfo;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;

import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;

/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("send-flowUtil")
/*     */ public class FlowUtil
/*     */ {
/*     */   private static DbUtil dbUtil;

public static DbUtil getDbUtil() {
	return dbUtil;
}

@Autowired(required=false)
public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
	FlowUtil.dbUtil = dbUtil;
}
/*     */ 
/*     */ 
/*     */   public static String getSummaryByProcessInfo(String processname, String incident)
/*     */   {
/*  42 */     if ((processname.length() == 0) || (incident.length() == 0)) {
/*  43 */       throw new ProcessException("子流程信息错误！");
/*     */     }
/*  45 */     String summary = "";
/*     */ 
/*  47 */     String sql = "select i.summary from incidents i where i.processname=? and i.incident=?";
/*  48 */     Object[] params = { processname, incident };
/*     */ 
/*  50 */     List list = dbUtil.getJdbcTemplate().queryForList(sql, params);
/*     */ 
/*  52 */     if (list.size() > 0) {
/*  53 */       summary = StringUtil.getNotNullValueString(((Map)list.get(0)).get("summary"));
/*  54 */       return summary;
/*     */     }
/*     */ 
/*  57 */     if (summary.length() == 0) {
/*  58 */       throw new ProcessException("summary为空！");
/*     */     }
/*     */ 
/*  62 */     return summary;
/*     */   }
/*     */ 
/*     */   public static List<Map<String, Object>> getActiveProcess(UserInfo userInfo)
/*     */   {
/*  75 */     //System.out.print(userInfo.getLoginName().substring(0,15));
				String sql = " select main.*,deptInfo.deptName from ((  select inci.summary,  '' as memo, inci.priorities,  decode(inci.priorities,'急件','<font color=\"red\"><b>急件</b></font>','')||(case when (to_date(to_char(part.overduetime,'YYYY.MM.DD'),'YYYY.MM.DD')-to_date(to_char(sysdate,'YYYY.MM.DD'),'YYYY.MM.DD'))<0 and part.overduetime is not null then '<font color=\"red\"><b>(超时'||to_char(to_date(to_char(sysdate,'YYYY.MM.DD'),'YYYY.MM.DD')-to_date(to_char(part.overduetime,'YYYY.MM.DD'),'YYYY.MM.DD'))||'天)</b></font>' when (to_date(to_char(sysdate,'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS') - to_date(to_char(part.overduetime, 'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS')) > 0 and to_date(to_char(sysdate,'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS') - to_date(to_char(part.overduetime, 'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS') < 1 and part.overduetime is not null then '<font color=\"red\"><b>(今天超时)</b></font>' end) as priorities_show,  part.*,  '0' as task_type,  '1' as 连接字符串, inci.initiator,  e.name as apply_username,  '" + 
/*  85 */       userInfo.getUserName() + "' as taskuser_name, " + 
/*  86 */       " '" + userInfo.getLoginName() + "' as UserName" + 
/*  88 */       " from incidents inci, " + 
/*  89 */       " cs_user e, " + 
/*  90 */       " ((select t.processname as pname, " + 
/*  91 */       " to_char(t.incident) as pincident, " + 
/*  92 */       " t.processname as processname, " + 
/*  93 */       " to_char(t.incident) as incident, " + 
/*  94 */       " t.steplabel, " + 
/*  95 */       " t.overduetime, " + 
/*  96 */       " to_char(t.endtime, 'yyyy-mm-dd hh24:mi:ss') as endtime, " + 
/*  97 */       " t.taskid, " + 
/*  98 */       " t.assignedtouser " + 
/*  99 */       " from tasks t " + 
/* 100 */       " where exists (select '' " + 
/* 101 */       " from processes a " + 
/* 102 */       " where t.processname = a.processname " + 
/* 103 */       " and a.launchtype = 0 " + 
/* 104 */       " and a.processname <> '拟办子流程' " + 
/* 105 */       " and a.processname <> '办结子流程' ) " + 
/* 106 */       " and (t.processname='新发文流程' or t.processname='新党委发文流程' or t.processname='新纪委发文流程') " + 
/* 107 */       " and t.status = 1 and t.assignedtouser like '%ST/%' " + 
/* 108 */       "  and t.assignedtouser like '%" + userInfo.getLoginName().substring(0,15) + "%' " + 
/* 110 */       " )" + 
/* 111 */       " union " + 
/* 112 */       " (select b.pname as pname, " + 
/* 113 */       "  b.pincident as pincident, " + 
/* 114 */       " b.cname as processname, " + 
/* 115 */       " b.cincident as incident, " + 
/* 116 */       " t.steplabel, " + 
/* 117 */       " t.overduetime, " + 
/* 118 */       " to_char(t.endtime, 'yyyy-mm-dd hh24:mi:ss') as endtime, " + 
/* 119 */       " t.taskid, " + 
/* 120 */       " t.assignedtouser " + 
/* 121 */       " from t_subprocess b, tasks t " + 
/* 122 */       " where b.cname = t.processname " + 
/* 123 */       " and b.cincident = t.incident " + 
/* 124 */       " and not exists " + 
/* 125 */       " (select '' " + 
/* 126 */       " from processes a " + 
/* 127 */       " where t.processname = a.processname " + 
/* 128 */       " and a.launchtype = 0 " + 
/* 129 */       " and a.processname <> '拟办子流程' " + 
/* 130 */       " and a.processname <> '办结子流程' " + 
/* 131 */       " ) " + 
/* 132 */       " and (b.pname='新发文流程' or b.pname='新党委发文流程' or b.pname='新纪委发文流程') " + 
/* 133 */       " and t.status = 1 and t.assignedtouser like '%ST/%' " + 
/* 134 */       " and t.assignedtouser like '%" + userInfo.getLoginName().substring(0,15) + "%'" + 
/* 136 */       " )) part " + 
/* 137 */       " where part.pname = inci.processname " + 
/* 138 */       " and part.pincident = inci.incident " + 
/* 139 */       " and upper(inci.initiator) = 'ST/' || upper(e.login_name)" + 
/* 140 */       " ) main left join  ( select ta.processname, ta.incident," + 
/* 141 */       " (select co.name from cs_organ_node co where replace(substr(ta.helpurl, instr(ta.helpurl, ':') + 1), '<+>', '') = co.id ) as deptName " + 
/* 142 */       "  from tasks ta, (select distinct processname, processversion, steplabel from processsteps where stepid like '%B' ) tb " + 
/* 143 */       "  where ta.processname = tb.processname and ta.processversion = tb.processversion and ta.steplabel = tb.steplabel " + 
/* 144 */       " )deptInfo on(main.pname = deptInfo.processname and main.pincident = to_char(deptInfo.incident))) ";
/*     */ 
/* 148 */     String loginName = userInfo.getLoginName();
/*     */ //System.out.println(sql);
/* 157 */     List list = dbUtil.getJdbcTemplate().queryForList(sql);
/*     */ 
/* 159 */     return list;
/*     */   }

public static List<String> getUltimusListInfo(List<String> src){
	//List<String> src = (List<String>) obj;
	List<String> target = new ArrayList<String>();
	if(src != null && src.size()>0){
		target.addAll(src);
		if(target.size() < 15){
			for(int i = 0 ; i<(15-target.size());i++){
				target.add("");
			}
		}
	}else{
		for(int i = 0; i< 15; i++){
			target.add("");
		}
	}
	return target;
}

public static List<String> getUltimusDeptListInfo(List<String> src){
	//List<String> src = (List<String>) obj;
	List<String> target = new ArrayList<String>();
	if(src != null && src.size()>0){
		target.addAll(src);
	}
	return target;
}

@SuppressWarnings({ "unchecked", "rawtypes" })
public static void putUltimusMap(String code ,List<String> list,Map map){
	List<String> newList = getUltimusListInfo(list);
	map.put(code , newList);
}

@SuppressWarnings({ "unchecked", "rawtypes" })
public static void putUltimusDeptMap(String code ,List<String> list,Map map){
	if(list != null && list.size() > 0){
		List<String> newList = getUltimusDeptListInfo(list);
		map.put(code , newList);
	}else{
		
	}
}


/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.util.FlowUtil
 * JD-Core Version:    0.6.0
 */