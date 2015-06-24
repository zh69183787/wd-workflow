/*    */ package com.wonders.send.util;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class AuthorityFunction
/*    */ {
/*    */   public static String toSQL(String authorityOperation, Map value)
/*    */   {
/*  7 */     String sql = "";
/*  8 */     if ((authorityOperation != null) && (authorityOperation.length() > 0)) {
/*  9 */       String[] temp = authorityOperation.split("@");
/* 10 */       if (temp.length > 0) {
/* 11 */         for (int i = 0; i < temp.length; i++) {
/* 12 */           if (i % 2 != 0)
/* 13 */             sql = sql + value.get(temp[i]);
/*    */           else {
/* 15 */             sql = sql + temp[i];
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 20 */     return sql;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.util.AuthorityFunction
 * JD-Core Version:    0.6.0
 */