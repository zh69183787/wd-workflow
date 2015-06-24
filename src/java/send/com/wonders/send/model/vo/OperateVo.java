/*    */ package com.wonders.send.model.vo;
/*    */ 
/*    */ public class OperateVo
/*    */ {
/* 14 */   private String taskId = "";
/* 15 */   private String id = "";
/* 16 */   private String choice = "";
/* 17 */   private String suggest = "";
/* 18 */   private String attachId = "";
/* 19 */   private String steplabel = "";
/* 20 */   private String flowType = "";
/*    */ 
/*    */   public String getAttachId()
/*    */   {
/* 24 */     return this.attachId;
/*    */   }
/*    */   public String getChoice() {
/* 27 */     return this.choice;
/*    */   }
/*    */   public String getId() {
/* 30 */     return this.id;
/*    */   }
/*    */   public String getSteplabel() {
/* 33 */     return this.steplabel;
/*    */   }
/*    */   public String getSuggest() {
/* 36 */     return this.suggest;
/*    */   }
/*    */   public void setAttachId(String attachId) {
/* 39 */     this.attachId = attachId;
/*    */   }
/*    */   public void setChoice(String choice) {
/* 42 */     this.choice = choice;
/*    */   }
/*    */   public void setId(String id) {
/* 45 */     this.id = id;
/*    */   }
/*    */   public void setSteplabel(String steplabel) {
/* 48 */     this.steplabel = steplabel;
/*    */   }
/*    */   public void setSuggest(String suggest) {
/* 51 */     this.suggest = suggest;
/*    */   }
/*    */   public String getFlowType() {
/* 54 */     return this.flowType;
/*    */   }
/*    */   public void setFlowType(String flowType) {
/* 57 */     this.flowType = flowType;
/*    */   }
/*    */   public String getTaskId() {
/* 60 */     return this.taskId;
/*    */   }
/*    */   public void setTaskId(String taskId) {
/* 63 */     this.taskId = taskId;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 68 */     return "id=" + this.id;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.model.vo.OperateVo
 * JD-Core Version:    0.6.0
 */