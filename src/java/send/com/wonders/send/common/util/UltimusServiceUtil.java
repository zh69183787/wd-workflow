/*     */ package com.wonders.send.common.util;
/*     */ 
/*     */ import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;

/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("ultimusServiceUtil")
/*     */ public class UltimusServiceUtil
/*     */ {
			private static DbUtil dbUtil;
/*     */   public static Map<String, Integer> isPersonOverInSubProcessByOrders(String pname, String pincident, String userLoginName)
/*     */   {
/*  17 */     Map map = null;
/*  18 */     if ((StringUtil.isNull(pname)) || (StringUtil.isNull(pincident)) || (StringUtil.isNull(userLoginName))) {
/*  19 */       return null;
/*     */     }
/*  21 */     String sql = " select  rtrim(ta1.assignedtouser) loginname,  ta1.status,  vu1.name,  rtrim(ta1.steplabel) stepname  from tasks ta1,cs_user vu1  where 'ST/'||vu1.login_name = RTRIM(ta1.assignedtouser)  and rtrim(ta1.steplabel) <> 'Begin'  and exists  (  select  ''  from t_subprocess ts  where ts.pname = '" + 
/*  34 */       pname + "' " + 
/*  35 */       " and ts.pincident = '" + pincident + "' " + 
/*  36 */       " and rtrim(ta1.processname) = ts.cname " + 
/*  37 */       " and rtrim(ta1.incident) = ts.cincident " + 
/*  38 */       " ) ";
/*  39 */     String getUserSql = "select t.orders,'#'||t.id||'#'||t.sup_name as sup_name, t.sup_login_name from v_superinfo t where 1 = 1 ";
/*  40 */     if (StringUtil.isNull(userLoginName)) {
/*  41 */       return null;
/*     */     }
/*  43 */     String[] userLoginNames = userLoginName.split(",");
/*     */ 
/*  45 */     if ((userLoginNames != null) && (userLoginNames.length > 0)) {
/*  46 */       map = new TreeMap();
/*     */ 
/*  48 */       String tempSql = "";
/*  49 */       String userTempSql = "";
/*  50 */       for (int i = 0; i < userLoginNames.length; i++) {
/*  51 */         if (i == userLoginNames.length - 1) {
/*  52 */           tempSql = tempSql + " rtrim(ta1.assignedtouser) = '" + userLoginNames[i] + "'";
/*  53 */           userTempSql = userTempSql + " t.sup_login_name = '" + userLoginNames[i] + "'";
/*     */         } else {
/*  55 */           tempSql = tempSql + " rtrim(ta1.assignedtouser) = '" + userLoginNames[i] + "' or";
/*  56 */           userTempSql = userTempSql + " t.sup_login_name = '" + userLoginNames[i] + "' or";
/*     */         }
/*     */       }
/*  59 */       if (StringUtil.isNull(tempSql)) return null;
/*     */ 
/*  61 */       sql = sql + " and ( " + tempSql + ")";
/*  62 */       getUserSql = getUserSql + " and ( " + userTempSql + ") order by t.orders";
/*     */ 
/*  64 */       String nameStrin = "";
/*  65 */       String userResults = "";
/*  66 */       String userResults2 = "";
/*  67 */       int count = 0;
/*  68 */       sql = "select v.id,a.* from ( " + sql + ") a,v_superinfo v where v.sup_login_name in a.loginname order by v.orders";
/*  69 */       List list = dbUtil.getJdbcTemplate().queryForList(getUserSql);
/*  70 */       if ((list != null) && (list.size() > 0)) {
/*  71 */         for (int i = 0; i < list.size(); i++) {
/*  72 */           int orders = Integer.valueOf(String.valueOf(((Map)list.get(0)).get("orders"))).intValue();
/*  73 */           String name = getOrdersNum(orders) + String.valueOf(((Map)list.get(0)).get("sup_name"));
/*  74 */           map.put(name, Integer.valueOf(3));
/*  75 */           nameStrin = nameStrin + name + ",";
/*     */         }
/*  77 */         String countSql = "select count(*) as count " + sql.substring(sql.indexOf("from"), sql.lastIndexOf(" order "));
/*  78 */         List list2 = dbUtil.getJdbcTemplate().queryForList(countSql);
/*  79 */         if ((list2 != null) && (list2.size() > 0)) {
/*  80 */           for (int i = 0; i < list2.size(); i++) {
/*  81 */             count = Integer.valueOf(String.valueOf(((Map)list.get(0)).get("count"))).intValue();
/*     */           }
/*     */         }
/*  84 */         String[] users = nameStrin.split(",");
/*  85 */         if (count > 0) {
/*  86 */           List list3 = dbUtil.getJdbcTemplate().queryForList(sql);
/*  87 */           if ((list3 != null) && (list3.size() > 0)) {
/*  88 */             for (int j = 0; j < list3.size(); j++) {
/*  89 */               int userId = Integer.valueOf(String.valueOf(((Map)list.get(0)).get("id"))).intValue();
/*  90 */               String loginname = String.valueOf(((Map)list.get(0)).get("loginname"));
/*  91 */               int status = Integer.valueOf(String.valueOf(((Map)list.get(0)).get("status"))).intValue();
/*  92 */               userResults = userResults + userId + ",";
/*  93 */               for (int i = 0; i < users.length; i++)
/*     */               {
/*     */                 
/*     */                 String[] userArrays;
/*     */                 String[] userArrays2;
/*     */                 List list1;
/*     */               
/*  94 */                 if ((users[i] != null) && (users[i].length() > 0)) {
/*  95 */                   String[] tempUsers = users[i].split("#");
/*  96 */                   if ((tempUsers != null) && (tempUsers.length > 0)) {
/*  97 */                     String orders = tempUsers[0];
/*  98 */                     String userid = tempUsers[1];
/*  99 */                     String loginName = tempUsers[2];
/* 100 */                     if ((userId != Integer.parseInt(userid)) || 
/* 101 */                       (1 != status)) continue;
/* 102 */                     map.put(users[i], Integer.valueOf(1));
/* 103 */                     break;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 111 */           for (int i = 0; i < users.length; i++) {
/* 112 */             String[] tempUsers = users[i].split("#");
/* 113 */             if ((tempUsers != null) && (tempUsers.length > 0)) {
/* 114 */               String userid = tempUsers[1];
/* 115 */               userResults2 = userResults2 + userid + ",";
/*     */             }
/*     */           }
/* 118 */           String[] userArrays = userResults.split(",");
/* 119 */           String[] userArrays2 = userResults2.split(",");
/*     */ 
/* 121 */           List list1 = Arrays.asList(userArrays);
/* 122 */           for (int i = 0; i < userArrays2.length; i++)
/*     */           {
/* 124 */             if ((userArrays2[i] == null) || (userArrays2.length <= 0) || 
/* 125 */               (list1.contains(userArrays2[i])))
/*     */               continue;
/* 127 */             map.put(users[i], Integer.valueOf(0));
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 132 */           for (int i = 0; i < users.length; i++) {
/* 133 */             if ((users[i] != null) && (users[i].length() > 0)) {
/* 134 */               map.put(users[i], Integer.valueOf(0));
/*     */             }
/*     */           }
/* 137 */           return map;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 143 */     return map;
/*     */   }
/*     */ 
/*     */   public static String getOrdersNum(int i) {
/* 147 */     String nextNum = "00";
/* 148 */     if (i < 10)
/* 149 */       nextNum = nextNum + i;
/* 150 */     else if ((i >= 10) && (i < 100))
/* 151 */       nextNum = "0" + i;
/*     */     else {
/* 153 */       nextNum = i+"";
/*     */     }
/* 155 */     return nextNum;
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
 * Qualified Name:     com.wonders.workflow.common.util.UltimusServiceUtil
 * JD-Core Version:    0.6.0
 */