/*     */ package com.wonders.send.future.service.impl;
/*     */ 
/*     */ import com.wonders.send.future.dao.FutureDao;
import com.wonders.send.future.model.bo.Future;
/*     */ import com.wonders.send.future.service.FutureService;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Repository;
/*     */ import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
/*     */ @Repository("send-futureService")
/*     */ @Scope("prototype")
/*     */ public class FutureServiceImpl
/*     */   implements FutureService
/*     */ {
/*     */   private FutureDao dao;
/*     */ 
/*     */   @Autowired(required=false)
/*     */   public void setFutureDao(@Qualifier("send-futureDao") FutureDao dao)
/*     */   {
/*  23 */     this.dao = dao;
/*     */   }
/*     */ 
/*     */   public String findDeptName(String processName, String incident) {
/*  27 */     Future future = this.dao.findFuturesByPName(processName, incident);
/*  28 */     if (future != null)
/*     */     {
/*  30 */       return this.dao.toOrdersByDept(future.getDeptIdList());
/*     */     }
/*  32 */     return null;
/*     */   }
/*     */ 
/*     */   public String findDeptNameByOrders(String processName, String incident)
/*     */   {
/*  37 */     Future future = this.dao.findFuturesByPName(processName, incident);
/*  38 */     if (future != null)
/*     */     {
/*  40 */       return this.dao.getDeptsOrders(future.getDeptIdList());
/*     */     }
/*  42 */     return null;
/*     */   }
/*     */ 
/*     */   public String findDeptId(String processName, String incident)
/*     */   {
/*  48 */     Future future = this.dao.findFuturesByPName(processName, incident);
/*  49 */     if (future != null) {
/*  50 */       return future.getDeptIdList();
/*     */     }
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */   public String getDeptsOrders(String noOrderString)
/*     */   {
/*  57 */     return this.dao.getDeptsOrders(noOrderString);
/*     */   }
/*     */ 
/*     */   public String findXBDeptName(String processName, String incident)
/*     */   {
/*  62 */     Future future = this.dao.findFuturesByPName(processName, incident);
/*  63 */     if (future != null) {
/*  64 */       return this.dao.getDeptsOrders(future.getXbdeptid());
/*     */     }
/*  66 */     return null;
/*     */   }
/*     */ 
/*     */   public Future findFuturesByPName(String processName, String incident)
/*     */   {
/*  72 */     Future future = this.dao.findFuturesByPName(processName, incident);
/*  73 */     return future;
/*     */   }
/*     */ 
/*     */   public String findLDName(String processName, String incident)
/*     */   {
/*  78 */     Future future = this.dao.findFuturesByPName(processName, incident);
/*  79 */     if (future != null)
/*     */     {
/*  81 */       return this.dao.toOrdersByQFLeader(future.getLeaderList());
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */   public String findQFLeadName(String processName, String incident)
/*     */   {
/*  89 */     Future future = this.dao.findFuturesByPName(processName, incident);
/*  90 */     if (future != null)
/*     */     {
/*  92 */       return this.dao.toOrdersByQFLeader(future.getShldid());
/*     */     }
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   public String findHQLeadName(String processName, String incident)
/*     */   {
/* 100 */     Future future = this.dao.findFuturesByPName(processName, incident);
/* 101 */     if (future != null)
/*     */     {
/* 103 */       return this.dao.toOrdersByQFLeader(future.getHqldid());
/*     */     }
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */   public String findByType(String processName, String incident, String type)
/*     */   {
/* 111 */     if ("领导".equals(type)) {
/* 112 */       return findLDName(processName, incident);
/*     */     }
/* 114 */     if ("部门".equals(type)) {
/* 115 */       return findDeptName(processName, incident);
/*     */     }
/* 117 */     if ("办结".equals(type)) {
/* 118 */       return findBJName(processName, incident);
/*     */     }
/* 120 */     if ("协办部门".equals(type)) {
/* 121 */       return findXBDeptName(processName, incident);
/*     */     }
/* 123 */     if ("BM".equals(type)) {
/* 124 */       return findDeptNameByOrders(processName, incident);
/*     */     }
/*     */ 
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */   public String findBJName(String processName, String incident) {
/* 131 */     Future future = this.dao.findFuturesByPName(processName, incident);
/* 132 */     if (future != null) {
/* 133 */       return future.getFinishList();
/*     */     }
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */   public String findStep(String processName, String incident, String stepName)
/*     */   {
/* 140 */     Future future = this.dao.findFuturesByPName(processName, incident);
/* 141 */     if (future != null) {
/* 142 */       String temp = future.getStepName();
/* 143 */       if (temp != null) {
/* 144 */         String[] temps = temp.split(",");
/*     */ 
/* 146 */         String[] userName = future.getUsername().split(",");
/* 147 */         for (int i = 0; i < temps.length; i++) {
/* 148 */           if (temps[i].equals(stepName)) {
/* 149 */             return userName[i];
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 155 */         return null;
/*     */       }
/*     */     } else {
/* 158 */       return null;
/*     */     }
/* 160 */     return null;
/*     */   }
/*     */ 
/*     */   public void addFutureFairs(Future future) {
/* 164 */     this.dao.save(future);
/*     */   }
/*     */ 
/*     */   public void saveOrUpdateFutures(String processName, String incidentNo, Future future) {
/* 168 */     Future oldFuture = this.dao.findFuturesByPName(processName, incidentNo);
/* 169 */     if (oldFuture != null)
/* 170 */       this.dao.updateFutures(future, oldFuture.getId());
/*     */     else
/* 172 */       this.dao.save(future);
/*     */   }
/*     */ 
/*     */   public void updateFutures(Future future, String id)
/*     */   {
/* 179 */     this.dao.updateFutures(future, id);
/*     */   }
/*     */ 
/*     */   public void justSaveOrUpdateFuture(Future future)
/*     */   {
/* 184 */     this.dao.justSaveOrUpdateFuture(future);
/*     */   }
/*     */ 
/*     */   public String toOrdersByQFLeader(String noOrderString) {
/* 188 */     return this.dao.toOrdersByQFLeader(noOrderString);
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.future.service.impl.FutureServiceImpl
 * JD-Core Version:    0.6.0
 */