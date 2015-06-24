/*    */ package com.wonders.send.util;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.sun.jersey.api.client.Client;
/*    */ import com.sun.jersey.api.client.WebResource;
/*    */ import com.sun.jersey.api.client.WebResource.Builder;
/*    */ import com.sun.jersey.core.util.MultivaluedMapImpl;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Map;
/*    */ import javax.ws.rs.core.MultivaluedMap;
/*    */ 
/*    */ public class UltimusFunc
/*    */ {
/*    */   //private static final String ultimusFuncLaunchPath = "http://10.1.48.60/workflowController/service/workflow/launchProcess";
/*    */   //private static final String ultimusFuncDealPath = "http://10.1.48.60/workflowController/service/workflow/saveProcessStep";
/*    */ 
/*    */   public static int launchProcess(Map map)
/*    */   {
/* 21 */     int result = -1;
/* 22 */     Client c = Client.create();
/* 23 */     WebResource r = c.resource("http://10.1.48.60111/workflowController/service/workflow/launchProcess");
/* 24 */     Gson gson = new Gson();
/* 25 */     MultivaluedMap formData = new MultivaluedMapImpl();
/* 26 */     formData.add("data", gson.toJson(map));
/* 27 */     String response = 
/* 28 */       (String)r.type("application/x-www-form-urlencoded")
/* 28 */       .post(String.class, formData);
/* 29 */     System.out.println(response.toString());
/* 30 */     Map resultMap = (Map)gson.fromJson(response.toString(), Map.class);
/* 31 */     if (resultMap != null) {
/* 32 */       if ("true".equals(resultMap.get("is_success")))
/* 33 */         result = Integer.valueOf((String)resultMap.get("incident")).intValue();
/*    */       else {
/* 35 */         System.out.println("launchProcessError!");
/*    */       }
/*    */     }
/* 38 */     return result;
/*    */   }
/*    */ 
/*    */   public static boolean dealProcess(Map map) {
/* 42 */     Client c = Client.create();
/* 43 */     WebResource r = c.resource("http://10.1.48.60111/workflowController/service/workflow/saveProcessStep");
/* 44 */     Gson gson = new Gson();
/* 45 */     MultivaluedMap formData = new MultivaluedMapImpl();
/* 46 */     formData.add("data", gson.toJson(map));
/* 47 */     String response = 
/* 48 */       (String)r.type("application/x-www-form-urlencoded")
/* 48 */       .post(String.class, formData);
/* 49 */     System.out.println(response.toString());
/* 50 */     Map resultMap = (Map)gson.fromJson(response.toString(), Map.class);
/* 51 */     if (resultMap != null) {
/* 52 */       if ("true".equals(resultMap.get("is_success"))) {
/* 53 */         System.out.println("launchProcessSuccess!");
/* 54 */         return true;
/*    */       }
/* 56 */       System.out.println("launchProcessError!");
/* 57 */       return false;
/*    */     }
/*    */ 
/* 60 */     return false;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.util.UltimusFunc
 * JD-Core Version:    0.6.0
 */