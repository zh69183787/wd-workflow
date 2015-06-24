/*    */ package com.wonders.send.model.bo;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ public class BaseBO
/*    */ {
/*    */   private Long operateTime;
/*    */   private String operator;
/*    */   private int removed;
/*    */ 
/*    */   public long getOperateTime()
/*    */   {
/* 11 */     if (this.operateTime == null) {
/* 12 */       Date d = new Date();
/* 13 */       this.operateTime = Long.valueOf(d.getTime());
/*    */     }
/* 15 */     return this.operateTime.longValue();
/*    */   }
/*    */   public void setOperateTime(long operateTime) {
/* 18 */     this.operateTime = Long.valueOf(operateTime);
/*    */   }
/*    */   public String getOperator() {
/* 21 */     return this.operator;
/*    */   }
/*    */   public void setOperator(String operator) {
/* 24 */     this.operator = operator;
/*    */   }
/*    */   public int getRemoved() {
/* 27 */     return this.removed;
/*    */   }
/*    */   public void setRemoved(int removed) {
/* 30 */     this.removed = removed;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.model.bo.BaseBO
 * JD-Core Version:    0.6.0
 */