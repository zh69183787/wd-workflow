/*    */ package com.wonders.send.external.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ExternalResultMulti extends AbstractExternalResult
/*    */ {
/* 13 */   public List params = new ArrayList();
/*    */ 
/*    */   public String getString()
/*    */   {
/* 17 */     return this.params.toString();
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.external.model.ExternalResultMulti
 * JD-Core Version:    0.6.0
 */