/*    */ package com.wonders.send.common.action;
/*    */ 
/*    */ import com.opensymphony.xwork2.ActionContext;
/*    */ import com.opensymphony.xwork2.ActionSupport;
import com.wonders.send.external.service.ExternalService;
import com.wonders.util.StringUtil;
/*    */ import com.wonders.send.common.model.vo.UserInfo;
/*    */ import com.wonders.send.common.util.ActionWriter;
/*    */ import com.wonders.send.common.util.LoginUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.struts2.convention.annotation.Action;
/*    */ import org.apache.struts2.convention.annotation.Namespace;
/*    */ import org.apache.struts2.convention.annotation.ParentPackage;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Qualifier;
/*    */ import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/*    */ 
/*    */ @ParentPackage("struts-default")
/*    */ @Namespace("/send-deptContact")
/*    */ @Controller("send-deptContactAjaxAction")
/*    */ @Scope("prototype")
/*    */ public class DeptContactAjaxAction extends ActionSupport
/*    */ {
/* 39 */   ActionContext actionContext = ActionContext.getContext();
/* 40 */   HttpServletRequest request = (HttpServletRequest)this.actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/* 41 */   HttpServletResponse response = (HttpServletResponse)this.actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
/* 42 */   HttpSession session = this.request.getSession();
/* 43 */   private UserInfo userInfo = new UserInfo();
/*    */ 
/* 45 */   private ActionWriter aw = new ActionWriter(this.response);
/*    */   private ExternalService externalService;
/*    */ 
/*    */   public DeptContactAjaxAction()
/*    */   {
/* 50 */     setUserInfo(LoginUtil.getUserInfo(this.session));
/*    */   }
/*    */   @Action("/signLeader")
/*    */   public String signLeader() {
/* 55 */     String deptId = StringUtil.getNotNullValueString(this.session.getAttribute("dept_id"));
/*    */ 
/* 57 */     this.aw.writeJson(this.externalService.getDeptLeaders(deptId));
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   public ExternalService getExternalService()
/*    */   {
/* 63 */     return this.externalService;
/*    */   }
/*    */   @Autowired
/*    */   public void setExternalService(@Qualifier("send-externalService") ExternalService externalService) {
/* 68 */     this.externalService = externalService;
/* 69 */     this.externalService.setToken(this.userInfo.getToken());
/*    */   }
/*    */ 
/*    */   public ActionWriter getAw()
/*    */   {
/* 74 */     return this.aw;
/*    */   }
/*    */ 
/*    */   public void setAw(ActionWriter aw) {
/* 78 */     this.aw = aw;
/*    */   }
/*    */ 
/*    */   public UserInfo getUserInfo() {
/* 82 */     return this.userInfo;
/*    */   }
/*    */ 
/*    */   public void setUserInfo(UserInfo userInfo) {
/* 86 */     this.userInfo = userInfo;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.common.action.DeptContactAjaxAction
 * JD-Core Version:    0.6.0
 */