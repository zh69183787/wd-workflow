/*     */ package com.wonders.send.subProcess.sign.action;
/*     */ 
/*     */ import com.opensymphony.xwork2.ModelDriven;
import com.wonders.send.common.action.AbstractParamAction;
import com.wonders.send.common.util.ActionWriter;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.subProcess.sign.model.vo.SignSubParamVo;
import com.wonders.send.subProcess.sign.model.vo.SignSubVo;
import com.wonders.send.subProcess.sign.service.SignSubService;
import com.wonders.send.subProcess.util.ProcessUtil;

/*     */ import org.apache.struts2.convention.annotation.Action;
/*     */ import org.apache.struts2.convention.annotation.Namespace;
/*     */ import org.apache.struts2.convention.annotation.ParentPackage;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/*     */ 
/*     */ @ParentPackage("struts-default")
/*     */ @Namespace("/send-sign")
/*     */ @Controller("send-signSubAction")
/*     */ @Scope("prototype")
/*     */ public class SignSubAction extends AbstractParamAction
/*     */   implements ModelDriven<SignSubVo>
/*     */ {
/*     */   private SignSubService service;
/*  41 */   public SignSubVo vo = new SignSubVo();
/*     */   public SignSubParamVo params;
/*     */ 
/*     */   public SignSubAction()
/*     */   {
/*  45 */     if (this.params == null) this.params = new SignSubParamVo();
/*  46 */     this.params.vo = this.vo;
/*  47 */     this.params.userInfo = LoginUtil.getUserInfo(this.session);
/*     */   }
/*     */ 
/*     */   @Action("secretaryDeal")
/*     */   public String stepSecretaryDeal()
/*     */   {
/*  57 */     ActionWriter aw = new ActionWriter(this.response);
/*  58 */     ProcessUtil.prepareFlowInfo(this.request, this.params);
this.params.userInfo = LoginUtil.getUserInfo(this.session,this.request.getParameter("assignedtouser"));
/*     */     try {
/*  60 */       this.service.flowStepSecretaryDeal(this.params);
/*  61 */       aw.writeAjax("{\"if_success\":\"yes\"}");
/*     */     } catch (Throwable e) {
/*  63 */       e.printStackTrace();
/*  64 */       aw.writeAjax("{\"if_success\":\"no\"}");
/*     */     }
/*     */ 
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */   @Action("leaderDeal")
/*     */   public String stepLeaderDeal()
/*     */   {
/*  77 */     ActionWriter aw = new ActionWriter(this.response);
/*  78 */     ProcessUtil.prepareFlowInfo(this.request, this.params);
this.params.userInfo = LoginUtil.getUserInfo(this.session,this.request.getParameter("assignedtouser"));
/*     */     try {
/*  80 */       this.service.flowStepLeaderDeal(this.params);
/*  81 */       aw.writeAjax("{\"if_success\":\"yes\"}");
/*     */     } catch (Throwable e) {
/*  83 */       e.printStackTrace();
/*  84 */       aw.writeAjax("{\"if_success\":\"no\"}");
/*     */     }
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */   public SignSubService getService()
/*     */   {
/*  91 */     return this.service;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setService(@Qualifier("send-signSubService") SignSubService service) {
/*  96 */     this.service = service;
/*     */   }
/*     */ 
/*     */   public SignSubVo getModel()
/*     */   {
/* 109 */     return this.vo;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.sign.action.SignSubAction
 * JD-Core Version:    0.6.0
 */