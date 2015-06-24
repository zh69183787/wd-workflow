/*    */ package com.wonders.send.common.model.vo;
/*    */ 
/*    */ public class UserInfo
/*    */ {
/*  5 */   private String userId = "";
/*  6 */   private String loginName = "";
/*  7 */   private String userName = "";
/*    */ 
/*  9 */   private String deptId = "";
/* 10 */   private String deptName = "";
/*    */ 
/* 12 */   private String token = "";
/*    */ 
/*    */   public String toString()
/*    */   {
/* 16 */     return "userVo: userId:" + this.userId + " loginName:" + this.loginName + " userName" + this.userName + " deptId:" + this.deptId + " deptName:" + this.deptName + " token:" + this.token;
/*    */   }
/*    */ 
/*    */   public String getUserId() {
/* 20 */     return this.userId;
/*    */   }
/*    */ 
/*    */   public void setUserId(String userId) {
/* 24 */     this.userId = userId;
/*    */   }
/*    */ 
/*    */   public String getLoginName() {
/* 28 */     return this.loginName;
/*    */   }
/*    */ 
/*    */   public void setLoginName(String loginName) {
/* 32 */     this.loginName = loginName;
/*    */   }
/*    */ 
/*    */   public String getUserName() {
/* 36 */     return this.userName;
/*    */   }
/*    */ 
/*    */   public void setUserName(String userName) {
/* 40 */     this.userName = userName;
/*    */   }
/*    */ 
/*    */   public String getDeptId() {
/* 44 */     return this.deptId;
/*    */   }
/*    */ 
/*    */   public void setDeptId(String deptId) {
/* 48 */     this.deptId = deptId;
/*    */   }
/*    */ 
/*    */   public String getDeptName() {
/* 52 */     return this.deptName;
/*    */   }
/*    */ 
/*    */   public void setDeptName(String deptName) {
/* 56 */     this.deptName = deptName;
/*    */   }
/*    */ 
/*    */   public String getToken() {
/* 60 */     return this.token;
/*    */   }
/*    */ 
/*    */   public void setToken(String token) {
/* 64 */     this.token = token;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.common.model.vo.UserInfo
 * JD-Core Version:    0.6.0
 */