/*    */ package com.wonders.send.common.util;
/*    */ 
/*    */ import java.util.Map;

import com.wonders.send.common.model.vo.UserInfo;
import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.util.StringUtil;
/*    */ import com.wonders.constants.CommonConstants;
import com.wonders.constants.LoginConstants;

import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class LoginUtil
/*    */ {
/*    */   public static UserInfo getUserInfo(HttpSession session)
/*    */   {
/* 12 */     UserInfo u = new UserInfo();
/* 13 */     SimpleLogger log = new SimpleLogger(LoginUtil.class);
/*    */     try {
/* 15 */       u.setUserId(StringUtil.getNotNullValueString(session.getAttribute("user_id")));

/* 16 */       u.setLoginName(StringUtil.getNotNullValueString(session.getAttribute("login_name")));
/*    */ 
/* 18 */       u.setLoginName(getUserLoginName(u.getLoginName()));
/*    */ 
/* 20 */       u.setUserName(StringUtil.getNotNullValueString(session.getAttribute("user_name")));
/*    */ 
/* 22 */       u.setDeptId(StringUtil.getNotNullValueString(session.getAttribute("dept_id")));
/* 23 */       u.setDeptName(StringUtil.getNotNullValueString(session.getAttribute("dept_name")));
/* 24 */       u.setToken(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.TOKEN)));
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 28 */       log.warn(e.getMessage());
/*    */     }
/*    */ 
/* 31 */     if (u.getUserId().length() == 0) log.warn("session user init failed!");
/* 32 */     return u;
/*    */   }
/*    */ 
/*    */   public static String getUserLoginName(UserInfo u) {
/* 36 */     String prefix = CommonConstants.LOGINNAME_PREFIX;
/* 37 */     String loginName = u.getLoginName();
/*    */ 
/* 39 */     if (!loginName.startsWith(prefix)) {
/* 40 */       loginName = prefix + loginName;
/*    */     }
/*    */ 
/* 43 */     return loginName;
/*    */   }
/*    */ 
/*    */   public static String getUserLoginName(String loginName) {
/* 47 */     if (loginName.length() == 0) return "";
/* 48 */     String prefix = CommonConstants.LOGINNAME_PREFIX;
/*    */ 
/* 50 */     if (!loginName.startsWith(prefix)) {
/* 51 */       loginName = prefix + loginName;
/*    */     }
/*    */ 
/* 54 */     return loginName;
/*    */   }

			public static UserInfo getUserInfo(HttpSession session,String assignedtouser)
/*    */   {
/* 12 */     UserInfo u = new UserInfo();
/* 13 */     SimpleLogger log = new SimpleLogger(LoginUtil.class);
/*    */     try {
/* 15 */       u.setUserId(StringUtil.getNotNullValueString(session.getAttribute("user_id")));

				Map<String,TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
				if(userMap!=null){
					TaskUserVo taskUserVo = userMap.get(assignedtouser);
					if(taskUserVo!=null){
						u.setLoginName(getUserLoginName(StringUtil.getNotNullValueString( taskUserVo.getLoginName())));
						u.setUserName(StringUtil.getNotNullValueString(taskUserVo.getUserName()));
						u.setDeptId(StringUtil.getNotNullValueString(taskUserVo.getDeptId()));
						u.setDeptName(StringUtil.getNotNullValueString(taskUserVo.getDeptName()));
					}else{
						 u.setLoginName(getUserLoginName(StringUtil.getNotNullValueString(session.getAttribute("login_name"))));
						 u.setUserName(StringUtil.getNotNullValueString(session.getAttribute("user_name")));
						 u.setDeptId(StringUtil.getNotNullValueString(session.getAttribute("dept_id")));
						 u.setDeptName(StringUtil.getNotNullValueString(session.getAttribute("dept_name")));
					}
				}
/* 16 */       
/* 24 */       u.setToken(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.TOKEN)));
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 28 */       log.warn(e.getMessage());
/*    */     }
/*    */ 
/* 31 */     if (u.getUserId().length() == 0) log.warn("session user init failed!");
/* 32 */     return u;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.common.util.LoginUtil
 * JD-Core Version:    0.6.0
 */