/*     */ package com.wonders.send.subProcess.issue.model.vo;
/*     */ 
/*     */ public class IssueSubVo
/*     */ {
/*  14 */   private String taskId = "";
/*     */ 
/*  16 */   private String id = "";
/*     */ 
/*  18 */   private String choice = "";
/*     */ 
/*  20 */   private String suggest = "";
/*     */ 
/*  22 */   private String attachId = "";
/*     */ 
/*  24 */   private String remark = "";
/*     */ 
/*  26 */   private String steplabel = "";
/*     */ 
/*  28 */   private String flowType = "";
/*     */ 
/*  30 */   private String type = "";
/*     */ 
/*     */   public String toString()
/*     */   {
/*  35 */     String str = "choice:" + this.choice + " " + 
/*  36 */       "suggest:" + this.suggest + " " + 
/*  37 */       "attachId:" + this.attachId + " " + 
/*  38 */       "remark:" + this.remark + " " + 
/*  39 */       "type:" + this.type + " ";
/*     */ 
/*  41 */     return str;
/*     */   }
/*     */ 
/*     */   public void initList()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  53 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  57 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getChoice() {
/*  61 */     return this.choice;
/*     */   }
/*     */ 
/*     */   public void setChoice(String choice) {
/*  65 */     this.choice = choice;
/*     */   }
/*     */ 
/*     */   public String getSuggest()
/*     */   {
/*  70 */     return this.suggest;
/*     */   }
/*     */ 
/*     */   public void setSuggest(String suggest) {
/*  74 */     this.suggest = suggest;
/*     */   }
/*     */ 
/*     */   public String getAttachId()
/*     */   {
/*  79 */     return this.attachId;
/*     */   }
/*     */ 
/*     */   public void setAttachId(String attachId) {
/*  83 */     this.attachId = attachId;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/*  87 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/*  91 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public String getSteplabel() {
/*  95 */     return this.steplabel;
/*     */   }
/*     */ 
/*     */   public void setSteplabel(String steplabel) {
/*  99 */     this.steplabel = steplabel;
/*     */   }
/*     */ 
/*     */   public String getFlowType() {
/* 103 */     return this.flowType;
/*     */   }
/*     */ 
/*     */   public void setFlowType(String flowType) {
/* 107 */     this.flowType = flowType;
/*     */   }
/*     */ 
/*     */   public String getTaskId() {
/* 111 */     return this.taskId;
/*     */   }
/*     */ 
/*     */   public void setTaskId(String taskId) {
/* 115 */     this.taskId = taskId;
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 119 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/* 123 */     this.type = type;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.issue.model.vo.IssueSubVo
 * JD-Core Version:    0.6.0
 */