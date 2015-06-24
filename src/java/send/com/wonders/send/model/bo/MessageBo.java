/*    */ package com.wonders.send.model.bo;
/*    */ 
/*    */ import com.google.gson.annotations.Expose;
import com.wonders.util.StringUtil;
/*    */ 
/*    */ public class MessageBo
/*    */ {
/*    */ 
/*    */   @Expose
/* 28 */   public String textCn = "";
/*    */ 
/*    */   @Expose
/* 35 */   public String actionField = "";
/*    */ 
/*    */   @Expose
/* 42 */   public String type = "";
/*    */ 
/*    */   public MessageBo(String textCn, String actionField, String type)
/*    */   {
/*  8 */     this.textCn = StringUtil.getNotNullValueString(textCn);
/*  9 */     this.actionField = StringUtil.getNotNullValueString(actionField);
/* 10 */     this.type = StringUtil.getNotNullValueString(type);
/*    */   }
/*    */ 
/*    */   public MessageBo(String textCn, String actionField) {
/* 14 */     this.textCn = StringUtil.getNotNullValueString(textCn);
/* 15 */     this.actionField = StringUtil.getNotNullValueString(actionField);
/*    */   }
/*    */ 
/*    */   public MessageBo(String textCn)
/*    */   {
/* 20 */     this.textCn = StringUtil.getNotNullValueString(textCn);
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.model.bo.MessageBo
 * JD-Core Version:    0.6.0
 */