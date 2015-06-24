/*    */ package com.wonders.send.common.exception;
/*    */ 
/*    */ import com.wonders.send.common.util.SimpleLogger;

import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ProcessException extends RuntimeException
/*    */ {
/* 10 */   Logger log = SimpleLogger.getLogger(getClass());
/*    */ 
/* 12 */   public ProcessException(String error) { super(error);
/* 13 */     this.log.warn(error);
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.common.exception.ProcessException
 * JD-Core Version:    0.6.0
 */