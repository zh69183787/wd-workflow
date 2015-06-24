/*     */ package com.wonders.send.organTree.action;
/*     */ 
/*     */ import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wonders.send.common.model.vo.UserInfo;
import com.wonders.send.common.util.ActionWriter;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.common.util.SimpleLogger;
import com.wonders.send.external.service.ExternalService;
import com.wonders.send.organTree.model.bo.OrganDeptBo;
import com.wonders.send.organTree.model.bo.OrganLeaderBo;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ @ParentPackage("struts-default")
/*     */ @Namespace("/send-organTree")
/*     */ @Controller("send-organTreeAction")
/*     */ @Scope("prototype")
/*     */ public class OrganTreeAction extends ActionSupport
/*     */ {
/*  40 */   ActionContext actionContext = ActionContext.getContext();
/*  41 */   HttpServletRequest request = (HttpServletRequest)this.actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/*  42 */   HttpServletResponse response = (HttpServletResponse)this.actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
/*  43 */   HttpSession session = this.request.getSession();
/*     */ 
/*  45 */   private UserInfo userInfo = new UserInfo();
/*  46 */   private ActionWriter aw = new ActionWriter(this.response);
/*  47 */   private Logger log = SimpleLogger.getLogger(getClass());
/*     */   private ExternalService externalService;
/*  51 */   private String id = "";
/*     */ 
/*     */   public OrganTreeAction()
/*     */   {
/*  55 */     setUserInfo(LoginUtil.getUserInfo(this.session));
/*     */   }
/*     */ 
/*     */   @Action("/getDeptLeaders")
/*     */   public String getDeptLeaders()
/*     */   {
/*  67 */     if (this.id.length() > 0) {
/*  68 */       this.aw.writeJson(this.externalService.getDeptLeaders(this.id));
/*     */     }
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   @Action("/getDeptSingleLeader")
/*     */   public String getDeptSingleLeader()
/*     */   {
/*  78 */     if (this.id.length() > 0) {
/*  79 */       this.aw.writeJson(this.externalService.getDeptSingleLeader(this.id));
/*     */     }
/*  81 */     return null;
/*     */   }
/*     */ 
///*     */   @Action("/getDeptReceivers")
///*     */   public String getDeptReceivers()
///*     */   {
///*  89 */     if (this.id.length() > 0) {
///*  90 */       this.aw.writeJson(this.externalService.getDeptReceivers(this.id));
///*     */     }
///*  92 */     return null;
///*     */   }
/*     */ 
/*     */   @Action("/getDeptUsers")
/*     */   public String getDeptUsers()
/*     */   {
/* 100 */     if (this.id.length() > 0) {
/* 101 */       this.aw.writeJson(this.externalService.getDeptUsers(this.id));
/*     */     }
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   @Action("/getDeptUsersOffLeaders")
/*     */   public String getDeptUsersOffLeaders()
/*     */   {
/* 111 */     if (this.id.length() > 0) {
/* 112 */       this.aw.writeJson(this.externalService.getDeptUsersOffLeaders(this.id));
/*     */     }
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */   @Action("/getChildNodes")
/*     */   public String getChildNodes()
/*     */   {
/* 121 */     String typeId = this.request.getParameter("typeId");
/*     */ 
/* 123 */     if ("2500".equals(this.id)) {
/* 124 */       List<OrganDeptBo> list = this.externalService.getChildNodes(this.id);
/* 125 */       List<OrganDeptBo> list1 = new ArrayList<OrganDeptBo>();
/* 126 */       if ((list != null) && (list.size() > 0)) {
/* 127 */         for (int i = 0; i < list.size(); i++) {
/* 128 */           OrganDeptBo bo = (OrganDeptBo)list.get(i);
					bo.isParent = false;
/* 129 */           if (typeId == null)
/* 130 */             list1.add(bo);
/* 131 */           else if (typeId.equals("-1")&&"1".equals(((OrganDeptBo)list.get(i)).typeId))
/* 132 */             list1.add(bo);
/* 133 */           else if (typeId.equals(((OrganDeptBo)list.get(i)).typeId)) {
/* 134 */             list1.add(bo);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 139 */       this.aw.writeJson(list1);
/*     */     }
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */   @Action("/getNodesInfo")
/*     */   public String getNodesInfo()
/*     */   {
/* 148 */     this.aw.writeJson(this.externalService.getNodesInfo(this.id));
/*     */ 
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   @Action("/getRelatedNodes")
/*     */   public String getRelatedNodes() {
/* 156 */     if (this.id.length() > 0) {
/* 157 */       this.aw.writeJson(this.externalService.getRelatedNodes(this.id));
/*     */     }
/* 159 */     return null;
/*     */   }
/*     */ 
/*     */   public ExternalService getExternalService()
/*     */   {
/* 164 */     return this.externalService;
/*     */   }
/*     */   @Autowired
/*     */   public void setExternalService(@Qualifier("send-externalService") ExternalService externalService) {
/* 169 */     this.externalService = externalService;
/* 170 */     this.externalService.setToken(this.userInfo.getToken());
/*     */   }
/*     */ 
/*     */   public UserInfo getUserInfo() {
/* 174 */     return this.userInfo;
/*     */   }
/*     */ 
/*     */   public void setUserInfo(UserInfo userInfo) {
/* 178 */     this.userInfo = userInfo;
/*     */   }
/*     */ 
/*     */   public Logger getLog() {
/* 182 */     return this.log;
/*     */   }
/*     */ 
/*     */   public void setLog(Logger log) {
/* 186 */     this.log = log;
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 190 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/* 194 */     this.id = id;
/*     */   }
/*     */ 
/*     */   @Action("/getShlds")
/*     */   public String getShlds()
/*     */   {
				String type = this.request.getParameter("type");
    String processName = this.request.getParameter("processName");
/* 202 */     List<OrganLeaderBo> boList = this.externalService.getShlds(processName,type);
/* 203 */     if ((boList != null) && (boList.size() > 0)) {
/* 204 */       for (int i = 0; i < boList.size(); i++) {
/* 205 */         if ("2500".equals(((OrganLeaderBo)boList.get(i)).leaderId))
/* 206 */           ((OrganLeaderBo)boList.get(i)).id = "25000";
/*     */         else {
/* 208 */           ((OrganLeaderBo)boList.get(i)).id = ((OrganLeaderBo)boList.get(i)).leaderId;
/*     */         }
/* 210 */         ((OrganLeaderBo)boList.get(i)).name = ((OrganLeaderBo)boList.get(i)).leaderName;
/*     */       }
/*     */     }
/* 213 */     this.aw.writeJson(boList);
/* 214 */     return null;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.organTree.action.OrganTreeAction
 * JD-Core Version:    0.6.0
 */