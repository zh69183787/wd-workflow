/*    */ package com.wonders.send.common.action;
/*    */ 
/*    */ import com.opensymphony.xwork2.ActionContext;
/*    */ import com.opensymphony.xwork2.ActionSupport;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public abstract class AbstractParamAction extends ActionSupport
/*    */ {
/* 12 */   public ActionContext actionContext = ActionContext.getContext();
/* 13 */   public HttpServletRequest request = (HttpServletRequest)this.actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/* 14 */   public HttpServletResponse response = (HttpServletResponse)this.actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
/*    */ 
/* 17 */   public HttpSession session = this.request.getSession();
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.common.action.AbstractParamAction
 * JD-Core Version:    0.6.0
 */