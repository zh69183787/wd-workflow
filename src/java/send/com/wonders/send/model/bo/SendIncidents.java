/*     */ package com.wonders.send.model.bo;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import javax.persistence.AttributeOverrides;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.EmbeddedId;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="INCIDENTS", schema="STPT")
/*     */ public class SendIncidents
/*     */   implements Serializable
/*     */ {
/*     */   private SendIncidentsId id;
/*     */   private BigDecimal processversion;
/*     */   private BigDecimal priority;
/*     */   private String summary;
/*     */   private Date starttime;
/*     */   private Date endtime;
/*     */   private BigDecimal status;
/*     */   private String initiator;
/*     */   private String parenttaskid;
/*     */   private Date timelimit;
/*     */   private String helpurl;
/*     */   private String mainss;
/*     */   private String priorities;
/*     */ 
/*     */   public SendIncidents()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SendIncidents(SendIncidentsId id)
/*     */   {
/*  52 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public SendIncidents(SendIncidentsId id, BigDecimal processversion, BigDecimal priority, String summary, Date starttime, Date endtime, BigDecimal status, String initiator, String parenttaskid, Date timelimit, String helpurl, String mainss, String priorities)
/*     */   {
/*  57 */     this.id = id;
/*  58 */     this.processversion = processversion;
/*  59 */     this.priority = priority;
/*  60 */     this.summary = summary;
/*  61 */     this.starttime = starttime;
/*  62 */     this.endtime = endtime;
/*  63 */     this.status = status;
/*  64 */     this.initiator = initiator;
/*  65 */     this.parenttaskid = parenttaskid;
/*  66 */     this.timelimit = timelimit;
/*  67 */     this.helpurl = helpurl;
/*  68 */     this.mainss = mainss;
/*  69 */     this.priorities = priorities;
/*     */   }
/*     */ 
/*     */   @EmbeddedId
/*     */   @AttributeOverrides({@javax.persistence.AttributeOverride(name="processname", column=@Column(name="PROCESSNAME", nullable=false, length=512)), @javax.persistence.AttributeOverride(name="incident", column=@Column(name="INCIDENT", nullable=false, precision=20, scale=0))})
/*     */   public SendIncidentsId getId()
/*     */   {
/*  81 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(SendIncidentsId id) {
/*  85 */     this.id = id;
/*     */   }
/*     */ 
/*     */   @Column(name="PROCESSVERSION", precision=20, scale=0)
/*     */   public BigDecimal getProcessversion() {
/*  91 */     return this.processversion;
/*     */   }
/*     */ 
/*     */   public void setProcessversion(BigDecimal processversion) {
/*  95 */     this.processversion = processversion;
/*     */   }
/*     */ 
/*     */   @Column(name="PRIORITY", precision=20, scale=0)
/*     */   public BigDecimal getPriority() {
/* 101 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(BigDecimal priority) {
/* 105 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   @Column(name="SUMMARY", length=1600)
/*     */   public String getSummary() {
/* 111 */     return this.summary;
/*     */   }
/*     */ 
/*     */   public void setSummary(String summary) {
/* 115 */     this.summary = summary;
/*     */   }
/* 121 */   @Temporal(TemporalType.DATE)
/*     */   @Column(name="STARTTIME", length=7)
/*     */   public Date getStarttime() { return this.starttime; }
/*     */ 
/*     */   public void setStarttime(Date starttime)
/*     */   {
/* 125 */     this.starttime = starttime;
/*     */   }
/* 131 */   @Temporal(TemporalType.DATE)
/*     */   @Column(name="ENDTIME", length=7)
/*     */   public Date getEndtime() { return this.endtime; }
/*     */ 
/*     */   public void setEndtime(Date endtime)
/*     */   {
/* 135 */     this.endtime = endtime;
/*     */   }
/*     */ 
/*     */   @Column(name="STATUS", precision=20, scale=0)
/*     */   public BigDecimal getStatus() {
/* 141 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(BigDecimal status) {
/* 145 */     this.status = status;
/*     */   }
/*     */ 
/*     */   @Column(name="INITIATOR", length=1024)
/*     */   public String getInitiator() {
/* 151 */     return this.initiator;
/*     */   }
/*     */ 
/*     */   public void setInitiator(String initiator) {
/* 155 */     this.initiator = initiator;
/*     */   }
/*     */ 
/*     */   @Column(name="PARENTTASKID", length=64)
/*     */   public String getParenttaskid() {
/* 161 */     return this.parenttaskid;
/*     */   }
/*     */ 
/*     */   public void setParenttaskid(String parenttaskid) {
/* 165 */     this.parenttaskid = parenttaskid;
/*     */   }
/* 171 */   @Temporal(TemporalType.DATE)
/*     */   @Column(name="TIMELIMIT", length=7)
/*     */   public Date getTimelimit() { return this.timelimit; }
/*     */ 
/*     */   public void setTimelimit(Date timelimit)
/*     */   {
/* 175 */     this.timelimit = timelimit;
/*     */   }
/*     */ 
/*     */   @Column(name="HELPURL", length=1024)
/*     */   public String getHelpurl() {
/* 181 */     return this.helpurl;
/*     */   }
/*     */ 
/*     */   public void setHelpurl(String helpurl) {
/* 185 */     this.helpurl = helpurl;
/*     */   }
/*     */ 
/*     */   @Column(name="MAINSS")
/*     */   public String getMainss() {
/* 191 */     return this.mainss;
/*     */   }
/*     */ 
/*     */   public void setMainss(String mainss) {
/* 195 */     this.mainss = mainss;
/*     */   }
/*     */ 
/*     */   @Column(name="PRIORITIES", length=200)
/*     */   public String getPriorities() {
/* 201 */     return this.priorities;
/*     */   }
/*     */ 
/*     */   public void setPriorities(String priorities) {
/* 205 */     this.priorities = priorities;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.model.bo.Incidents
 * JD-Core Version:    0.6.0
 */