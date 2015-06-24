/*    */ package com.wonders.send.external.model;
/*    */ 
/*    */ public class ExternalResultSingle extends AbstractExternalResult
/*    */ {
/* 23 */   public ExternalParam params = new ExternalParam();
/*    */ 
/*    */   public String getString()
/*    */   {
/* 27 */     return this.params.getString();
/*    */   }
/*    */ 
/*    */   public class ExternalParam
/*    */   {
/* 10 */     public Object param = new Object();
/*    */ 
/*    */     public ExternalParam() {  }
/*    */ 
/* 13 */     public String getString() { String ret = this.param.toString();
/* 14 */       if ((ret != null) && (ret.length() > 0) && 
/* 15 */         (ret.startsWith("{")) && (ret.endsWith("}"))) {
/* 16 */         return "[" + ret + "]";
/*    */       }
/*    */ 
/* 19 */       return "";
/*    */     }
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.external.model.ExternalResultSingle
 * JD-Core Version:    0.6.0
 */