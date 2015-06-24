/*     */ package com.wonders.send.subProcess.issue.action;
/*     */ 
/*     */ import com.opensymphony.xwork2.ModelDriven;
import com.wonders.send.common.action.AbstractParamAction;
import com.wonders.send.common.util.ActionWriter;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.subProcess.util.ProcessUtil;
/*     */ import com.wonders.send.subProcess.issue.model.vo.IssueSubParamVo;
/*     */ import com.wonders.send.subProcess.issue.model.vo.IssueSubVo;
/*     */ import com.wonders.send.subProcess.issue.service.IssueSubService;
/*     */ import org.apache.struts2.convention.annotation.Action;
/*     */ import org.apache.struts2.convention.annotation.Namespace;
/*     */ import org.apache.struts2.convention.annotation.ParentPackage;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/*     */ 
/*     */ @ParentPackage("struts-default")
/*     */ @Namespace("/send-issue")
/*     */ @Controller("send-issueSubAction")
/*     */ @Scope("prototype")
/*     */ public class IssueSubAction extends AbstractParamAction
/*     */   implements ModelDriven<IssueSubVo>
/*     */ {
/*     */   private IssueSubService service;
/*  40 */   public IssueSubVo vo = new IssueSubVo();
/*     */   public IssueSubParamVo params;
/*     */ 
/*     */   public IssueSubAction()
/*     */   {
/*  44 */     if (this.params == null) this.params = new IssueSubParamVo();
/*  45 */     this.params.vo = this.vo;
/*  46 */     this.params.userInfo = LoginUtil.getUserInfo(this.session);
/*     */   }
/*     */ 
/*     */   @Action("secretaryDeal")
/*     */   public String stepSecretaryDeal()
/*     */   {
	          ActionWriter aw = new ActionWriter(this.response);
/*  56 */     ProcessUtil.prepareFlowInfo(this.request, this.params);
this.params.userInfo = LoginUtil.getUserInfo(this.session,this.request.getParameter("assignedtouser"));
/*     */     try {
/*  58 */       this.service.flowStepSecretaryDeal(this.params);
				aw.writeAjax("{\"if_success\":\"yes\"}");
/*     */     } catch (Throwable e) {
/*  60 */       e.printStackTrace();
				aw.writeAjax("{\"if_success\":\"no\"}");
/*     */     }
/*     */ 
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */   @Action("leaderDeal")
/*     */   public String stepLeaderDeal()
/*     */   {
/*  73 */     ActionWriter aw = new ActionWriter(this.response);
/*  74 */     ProcessUtil.prepareFlowInfo(this.request, this.params);
this.params.userInfo = LoginUtil.getUserInfo(this.session,this.request.getParameter("assignedtouser"));
/*     */     try
/*     */     {
/*  77 */       this.service.flowStepLeaderDeal(this.params);
/*  78 */       aw.writeAjax("{\"if_success\":\"yes\"}");
/*     */     } catch (Throwable e) {
/*  80 */       e.printStackTrace();
/*  81 */       aw.writeAjax("{\"if_success\":\"no\"}");
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */   public IssueSubService getService()
/*     */   {
/*  88 */     return this.service;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setService(@Qualifier("send-issueSubService") IssueSubService service) {
/*  93 */     this.service = service;
/*     */   }
/*     */ 
/*     */   public IssueSubVo getModel()
/*     */   {
/* 106 */     return this.vo;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.issue.action.IssueSubAction
 * JD-Core Version:    0.6.0
 */