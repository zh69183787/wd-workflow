/*    */ package com.wonders.send.common.action;
/*    */ 
/*    */ import com.opensymphony.xwork2.ActionContext;
/*    */ import com.opensymphony.xwork2.ActionSupport;
import com.wonders.send.common.util.UltimusServiceUtil;

/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts2.convention.annotation.Action;
/*    */ import org.apache.struts2.convention.annotation.Namespace;
/*    */ import org.apache.struts2.convention.annotation.ParentPackage;
/*    */ import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/*    */ 
/*    */ @ParentPackage("struts-default")
/*    */ @Namespace("/send-ultimus")
/*    */ @Controller("send-ultimusAction")
/*    */ @Scope("prototype")
/*    */ public class UltimusAction extends ActionSupport
/*    */ {
/* 27 */   ActionContext actionContext = ActionContext.getContext();
/* 28 */   HttpServletRequest request = (HttpServletRequest)this.actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/* 29 */   HttpServletResponse response = (HttpServletResponse)this.actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
/*    */ 
/* 33 */   @Action("returnPersonStepByOrders")
/*    */   public String returnPersonStepByOrders() { String processname = this.request.getParameter("processname");
/* 34 */     String incidentNo = this.request.getParameter("incidentNo");
/* 35 */     String loginname = this.request.getParameter("loginname");
/* 36 */     String cProcessname = this.request.getParameter("cProcessname");
/* 37 */     Map map = UltimusServiceUtil.isPersonOverInSubProcessByOrders(processname, incidentNo, loginname);
/*    */ 
/* 40 */     String str = "";
/* 41 */     if ((map != null) && (map.size() > 0)) {
/* 42 */       Object[] keys = map.keySet().toArray();
/* 43 */       for (int i = 0; i < keys.length; i++) {
/* 44 */         String key = (String)keys[i];
/* 45 */         Integer value = (Integer)map.get(key);
/* 46 */         if (key.lastIndexOf("#") >= 0) {
/* 47 */           key = key.substring(key.lastIndexOf("#") + 1, key.length());
/*    */         }
/*    */ 
/* 50 */         if (3 != value.intValue()) {
/* 51 */           if (value.intValue() == 0) {
/* 52 */             str = str + "<b>" + key + "：尚未开始处理</b><BR>";
/*    */           }
/* 54 */           if (1 == value.intValue()) {
/* 55 */             str = str + "<b>" + key + "：正在处理</b><BR>";
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 60 */     Writer w = null;
/*    */     try {
/* 62 */       w = this.response.getWriter();
/* 63 */       w.write(str);
/*    */     } catch (Exception e) {
/* 65 */       e.printStackTrace();
/*    */ 
/* 67 */       if (w != null)
/*    */         try {
/* 69 */           w.flush();
/* 70 */           w.close();
/*    */         }
/*    */         catch (IOException ee) {
/* 73 */           ee.printStackTrace();
/*    */         }
/*    */     }
/*    */     finally
/*    */     {
/* 67 */       if (w != null) {
/*    */         try {
/* 69 */           w.flush();
/* 70 */           w.close();
/*    */         }
/*    */         catch (IOException e) {
/* 73 */           e.printStackTrace();
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 78 */     return null;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.common.action.UltimusAction
 * JD-Core Version:    0.6.0
 */