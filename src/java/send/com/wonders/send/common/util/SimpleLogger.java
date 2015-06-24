/*    */ package com.wonders.send.common.util;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SimpleLogger
/*    */ {
/*    */   private Logger log;
/*    */ 
/*    */   public static Logger getLogger(String name)
/*    */   {
/*  7 */     return Logger.getLogger(name);
/*    */   }
/*    */ 
/*    */   public static Logger getLogger(Class c) {
/* 11 */     return Logger.getLogger(c.getName());
/*    */   }
/*    */ 
/*    */   private void init(String name) {
/* 15 */     if (this.log == null) this.log = getLogger(name);
/*    */   }
/*    */ 
/*    */   private void init(Class c)
/*    */   {
/* 20 */     if (this.log == null) this.log = getLogger(c); 
/*    */   }
/*    */ 
/*    */   public SimpleLogger(String name)
/*    */   {
/* 24 */     init(name);
/*    */   }
/*    */ 
/*    */   public SimpleLogger(Class c)
/*    */   {
/* 29 */     init(c);
/*    */   }
/*    */ 
/*    */   public void debug(Object obj)
/*    */   {
/* 35 */     if (this.log.isDebugEnabled())
/* 36 */       this.log.debug(obj.toString());
/*    */   }
/*    */ 
/*    */   public void debug(String message)
/*    */   {
/* 41 */     if (this.log.isDebugEnabled())
/* 42 */       this.log.debug(message);
/*    */   }
/*    */ 
/*    */   public void info(Object obj)
/*    */   {
/* 47 */     if (this.log.isDebugEnabled())
/* 48 */       this.log.info(obj.toString());
/*    */   }
/*    */ 
/*    */   public void info(String message)
/*    */   {
/* 53 */     if (this.log.isInfoEnabled())
/* 54 */       this.log.info(message);
/*    */   }
/*    */ 
/*    */   public void warn(Object obj)
/*    */   {
/* 59 */     this.log.warn(obj.toString());
/*    */   }
/*    */ 
/*    */   public void warn(String message) {
/* 63 */     this.log.warn(message);
/*    */   }
/*    */ 
/*    */   public void error(String message)
/*    */   {
/* 68 */     this.log.error(message);
/*    */   }
/*    */ 
/*    */   public void fatal(String message) {
/* 72 */     this.log.fatal(message);
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.common.util.SimpleLogger
 * JD-Core Version:    0.6.0
 */