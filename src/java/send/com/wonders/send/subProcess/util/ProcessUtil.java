/*     */ package com.wonders.send.subProcess.util;
/*     */ 
/*     */ import com.wonders.send.model.vo.ParamVo;
import com.wonders.util.DbUtil;
import com.wonders.util.PWSUtil;
import com.wonders.util.StringUtil;
import com.wonders.send.common.exception.ProcessException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/*     */ @Component("processUtil")
/*     */ public class ProcessUtil
/*     */ {
/*     */   public static final String domainName = "ST/";
/*     */   private static DbUtil dbUtil;
/*     */   public static void prepareFlowInfo(HttpServletRequest request, ParamVo params)
/*     */   {
/*  28 */     String pname = StringUtil.getNotNullValueString(request.getParameter("pname"));
/*  29 */     String pincident = StringUtil.getNotNullValueString(request.getParameter("pincident"));
/*  30 */     String cname = StringUtil.getNotNullValueString(request.getParameter("cname"));
/*  31 */     String cincident = StringUtil.getNotNullValueString(request.getParameter("cincident"));
/*     */ 
/*  33 */     String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
/*     */ 
/*  35 */     params.addProcessParam("pname", pname);
/*  36 */     params.addProcessParam("pincident", pincident);
/*  37 */     params.addProcessParam("cname", cname);
/*  38 */     params.addProcessParam("cincident", cincident);
/*  39 */     params.addProcessParam("steplabel", steplabel);
/*     */   }
/*     */ 
/*     */   public static boolean launchProcessStep(String processName, String taskUserLoginName, int incidentno, String steplabel, String summary, String memo, Map<String, Object> map)
/*     */   {
/*  46 */     boolean flag = PWSUtil.completeStepTest(processName, taskUserLoginName, incidentno, steplabel, summary, memo, map);
/*  47 */     return flag;
/*     */   }
/*     */ 
/*     */   public static String getLeaderInfo(String processName, String incident)
/*     */   {
/*  57 */     if ((processName.length() == 0) || (incident.length() == 0)) {
/*  58 */       throw new ProcessException("流程信息错误！");
/*     */     }
/*  60 */     String val = "";
/*  61 */     String sql = "select c.login_name ,c.name from cs_user c where 'ST/' ||  c.login_name = (select o.taskuser from tasks o where o.processname = ? and o.incident = ?  and o.steplabel like '%领导%'and o.recipient <> 'WONDERS-TEST_WF' ) and c.removed = 0";
/*     */ 
/*  65 */     Object[] params = { processName, Long.valueOf(incident) };
/*     */ 
/*  67 */     List list = dbUtil.getJdbcTemplate().queryForList(sql, params);
/*  68 */     if (list.size() > 0) {
/*  69 */       val = StringUtil.getNotNullValueString(((Map)list.get(0)).get("login_name")) + "," + 
/*  70 */         StringUtil.getNotNullValueString(((Map)list.get(0)).get("name"));
/*  71 */       return val;
/*     */     }
/*  73 */     if (val.length() == 0) {
/*  74 */       throw new ProcessException("领导信息为空！");
/*     */     }
/*     */ 
/*  77 */     return val;
/*     */   }
/*     */ 
/*     */   public static String getStepInfo(String loginName, String processName, String incident)
/*     */   {
/*  87 */     if ((processName.length() == 0) || (incident.length() == 0)) {
/*  88 */       throw new ProcessException("流程信息错误！");
/*     */     }
/*  90 */     String val = "";
/*  91 */     String sql = "select t.steplabel as stepName from tasks t where t.taskuser = ?  and t.processname = ? and t.incident = ?";
/*     */ 
/*  95 */     Object[] params = { loginName, processName, Long.valueOf(incident) };
/*     */ 
/*  97 */     List list = dbUtil.getJdbcTemplate().queryForList(sql, params);
/*  98 */     if (list.size() > 0) {
/*  99 */       val = StringUtil.getNotNullValueString(((Map)list.get(0)).get("steplabel"));
/* 100 */       return val;
/*     */     }
/* 102 */     if (val.length() == 0) {
/* 103 */       throw new ProcessException("节点信息为空！");
/*     */     }
/*     */ 
/* 106 */     return val;
/*     */   }

public DbUtil getDbUtil() {
	return dbUtil;
}

@Autowired(required=false)
public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
	this.dbUtil = dbUtil;
}
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.util.ProcessUtil
 * JD-Core Version:    0.6.0
 */