/*     */ package com.wonders.send.subProcess.dept.model.vo;
/*     */ 
/*     */ import com.wonders.util.StringUtil;

/*     */ import java.util.ArrayList;
import java.util.List;
/*     */ 
/*     */ public class DeptSubVo
/*     */ {
/*  20 */   private String taskId = "";
/*     */ 
/*  22 */   private String id = "";
/*     */ 
/*  24 */   private String choice = "";
/*     */ 
/*  26 */   private String choice2 = "";
/*     */ 
/*  28 */   private String suggest = "";
/*     */ 
/*  32 */   private String dealPersonStr = "";
/*     */ 
/*  34 */   private List<String> dealPersonList = new ArrayList();
/*     */ 
/*  36 */   private String dealLeaderStr = "";
/*     */ 
/*  38 */   private List<String> dealLeaderList = new ArrayList();
/*     */ 
/*  42 */   private String attachId = "";
/*     */ 
/*  44 */   private String remark = "";
/*     */ 
/*  46 */   private String steplabel = "";
/*  47 */   private String flowType = "";
/*     */ 
/*     */   public String toString()
/*     */   {
/*  53 */     String str = "choice:" + this.choice + " " + 
/*  54 */       "choice2:" + this.choice2 + " " + 
/*  55 */       "suggest:" + this.suggest + " " + 
/*  56 */       "dealPersonStr:" + this.dealPersonStr + " " + 
/*  57 */       "dealLeaderStr:" + this.dealLeaderStr + " " + 
/*  58 */       "attachId:" + this.attachId + " " + 
/*  59 */       "remark:" + this.remark + " ";
/*     */ 
/*  61 */     return str;
/*     */   }
/*     */ 
/*     */   public void initList()
/*     */   {
/*  68 */     String person = StringUtil.getNotNullValueString(this.dealPersonStr);
/*  69 */     String leader = StringUtil.getNotNullValueString(this.dealLeaderStr);
/*     */ 
/*  71 */     String[] persons = person.split(",");
/*  72 */     String[] leaders = leader.split(",");
/*     */ 
/*  74 */     for (int i = 0; i < persons.length; i++) {
/*  75 */       String p = StringUtil.getNotNullValueString(persons[i]);
/*  76 */       if (p.length() <= 0) continue; this.dealPersonList.add(p);
/*     */     }
/*     */ 
/*  79 */     for (int i = 0; i < leaders.length; i++) {
/*  80 */       String l = StringUtil.getNotNullValueString(leaders[i]);
/*  81 */       if (l.length() <= 0) continue; this.dealLeaderList.add(l);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  88 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  92 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getChoice() {
/*  96 */     return this.choice;
/*     */   }
/*     */ 
/*     */   public void setChoice(String choice) {
/* 100 */     this.choice = choice;
/*     */   }
/*     */ 
/*     */   public String getChoice2() {
/* 104 */     return this.choice2;
/*     */   }
/*     */ 
/*     */   public void setChoice2(String choice2) {
/* 108 */     this.choice2 = choice2;
/*     */   }
/*     */ 
/*     */   public String getSuggest() {
/* 112 */     return this.suggest;
/*     */   }
/*     */ 
/*     */   public void setSuggest(String suggest) {
/* 116 */     this.suggest = suggest;
/*     */   }
/*     */ 
/*     */   public String getDealPersonStr() {
/* 120 */     return this.dealPersonStr;
/*     */   }
/*     */ 
/*     */   public void setDealPersonStr(String dealPersonStr) {
/* 124 */     this.dealPersonStr = dealPersonStr;
/*     */   }
/*     */ 
/*     */   public List<String> getDealPersonList() {
/* 128 */     return this.dealPersonList;
/*     */   }
/*     */ 
/*     */   public void setDealPersonList(List<String> dealPersonList) {
/* 132 */     this.dealPersonList = dealPersonList;
/*     */   }
/*     */ 
/*     */   public String getDealLeaderStr() {
/* 136 */     return this.dealLeaderStr;
/*     */   }
/*     */ 
/*     */   public void setDealLeaderStr(String dealLeaderStr) {
/* 140 */     this.dealLeaderStr = dealLeaderStr;
/*     */   }
/*     */ 
/*     */   public List<String> getDealLeaderList() {
/* 144 */     return this.dealLeaderList;
/*     */   }
/*     */ 
/*     */   public void setDealLeaderList(List<String> dealLeaderList) {
/* 148 */     this.dealLeaderList = dealLeaderList;
/*     */   }
/*     */ 
/*     */   public String getAttachId() {
/* 152 */     return this.attachId;
/*     */   }
/*     */ 
/*     */   public void setAttachId(String attachId) {
/* 156 */     this.attachId = attachId;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/* 160 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/* 164 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public String getSteplabel() {
/* 168 */     return this.steplabel;
/*     */   }
/*     */ 
/*     */   public void setSteplabel(String steplabel) {
/* 172 */     this.steplabel = steplabel;
/*     */   }
/*     */ 
/*     */   public String getFlowType() {
/* 176 */     return this.flowType;
/*     */   }
/*     */ 
/*     */   public void setFlowType(String flowType) {
/* 180 */     this.flowType = flowType;
/*     */   }
/*     */ 
/*     */   public String getTaskId() {
/* 184 */     return this.taskId;
/*     */   }
/*     */ 
/*     */   public void setTaskId(String taskId) {
/* 188 */     this.taskId = taskId;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.dept.model.vo.DeptSubVo
 * JD-Core Version:    0.6.0
 */