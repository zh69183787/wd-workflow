/*     */ package com.wonders.send.subProcess.dept.action;
/*     */ 
/*     */ import com.opensymphony.xwork2.ModelDriven;
import com.wonders.send.common.action.AbstractParamAction;
import com.wonders.send.common.util.ActionWriter;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.subProcess.util.ProcessUtil;
/*     */ import com.wonders.send.subProcess.dept.model.vo.DeptSubParamVo;
/*     */ import com.wonders.send.subProcess.dept.model.vo.DeptSubVo;
/*     */ import com.wonders.send.subProcess.dept.service.DeptSubService;
/*     */ import org.apache.struts2.convention.annotation.Action;
/*     */ import org.apache.struts2.convention.annotation.Namespace;
/*     */ import org.apache.struts2.convention.annotation.ParentPackage;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/*     */ 
/*     */ @ParentPackage("struts-default")
/*     */ @Namespace("/send-deptSub")
/*     */ @Controller("send-deptSubAction")
/*     */ @Scope("prototype")
/*     */ public class DeptSubAction extends AbstractParamAction
/*     */   implements ModelDriven<DeptSubVo>
/*     */ {
/*     */   private DeptSubService service;
/*  39 */   public DeptSubVo vo = new DeptSubVo();
/*     */   public DeptSubParamVo params;
/*     */ 
/*     */   public DeptSubAction()
/*     */   {
/*  43 */     if (this.params == null) this.params = new DeptSubParamVo();
/*  44 */     this.params.vo = this.vo;
/*  45 */     this.params.userInfo = LoginUtil.getUserInfo(this.session);
/*     */   }
/*     */ 
/*     */   @Action("dispatcher")
/*     */   public String stepDispatcher()
/*     */   {
/*  54 */     ActionWriter aw = new ActionWriter(this.response);
/*  55 */     ProcessUtil.prepareFlowInfo(this.request, this.params);
				this.params.userInfo = LoginUtil.getUserInfo(this.session,this.request.getParameter("assignedtouser"));
/*     */     try {
/*  57 */       this.service.flowStepDispatcher(this.params);
/*  58 */       aw.writeAjax("{\"if_success\":\"yes\"}");
/*     */     } catch (Throwable e) {
/*  60 */       e.printStackTrace();
/*  61 */       aw.writeAjax("{\"if_success\":\"no\"}");
/*     */     }
/*     */ 
/*  64 */     return null;
/*     */   }
/*     */ 
/*     */   @Action("deal")
/*     */   public String stepDeal()
/*     */   {
/*  72 */     ActionWriter aw = new ActionWriter(this.response);
/*  73 */     ProcessUtil.prepareFlowInfo(this.request, this.params);
this.params.userInfo = LoginUtil.getUserInfo(this.session,this.request.getParameter("assignedtouser"));
/*     */     try {
/*  75 */       this.service.flowStepDeal(this.params);
/*  76 */       aw.writeAjax("{\"if_success\":\"yes\"}");
/*     */     } catch (Throwable e) {
/*  78 */       e.printStackTrace();
/*  79 */       aw.writeAjax("{\"if_success\":\"no\"}");
/*     */     }
/*     */ 
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   @Action("leaderDeal")
/*     */   public String stepLeaderDeal()
/*     */   {
/*  90 */     ActionWriter aw = new ActionWriter(this.response);
/*  91 */     ProcessUtil.prepareFlowInfo(this.request, this.params);
this.params.userInfo = LoginUtil.getUserInfo(this.session,this.request.getParameter("assignedtouser"));
/*     */     try {
/*  93 */       this.service.flowStepLeaderDeal(this.params);
/*  94 */       aw.writeAjax("{\"if_success\":\"yes\"}");
/*     */     } catch (Throwable e) {
/*  96 */       e.printStackTrace();
/*  97 */       aw.writeAjax("{\"if_success\":\"no\"}");
/*     */     }
/*     */ 
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   public DeptSubVo getModel()
/*     */   {
/* 112 */     return this.vo;
/*     */   }
/*     */ 
/*     */   public DeptSubService getService() {
/* 116 */     return this.service;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setService(@Qualifier("send-deptSubService") DeptSubService service) {
/* 121 */     this.service = service;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.dept.action.DeptSubAction
 * JD-Core Version:    0.6.0
 */