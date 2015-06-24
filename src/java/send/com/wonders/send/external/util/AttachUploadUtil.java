/*    */ package com.wonders.send.external.util;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class AttachUploadUtil
/*    */ {
/*    */   public static void saveFile(String destFilePathStr, String destFileName)
/*    */   {
/*    */     try
/*    */     {
/* 12 */       File destFilePath = new File(destFilePathStr);
/* 13 */       if (!destFilePath.exists()) {
/* 14 */         destFilePath.mkdirs();
/* 15 */         destFilePath = null;
/*    */       }
/* 17 */       File destFile = new File(destFilePathStr + "//" + destFileName);
/* 18 */       if (!destFile.exists())
/* 19 */         destFile.createNewFile();
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 23 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.external.util.AttachUploadUtil
 * JD-Core Version:    0.6.0
 */