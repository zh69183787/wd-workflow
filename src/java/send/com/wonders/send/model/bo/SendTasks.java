/*    */ package com.wonders.send.model.bo;
/*    */ 
/*    */ import java.util.Date;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.GeneratedValue;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.Table;
/*    */ import javax.persistence.Temporal;
/*    */ import javax.persistence.TemporalType;
/*    */ import org.hibernate.annotations.GenericGenerator;
/*    */ 
/*    */ @Entity
/*    */ @Table(name="TASKS")
/*    */ public class SendTasks
/*    */ {
/*    */   private String taskId;
/*    */   private String processName;
/*    */   private int incident;
/*    */   private String stepLabel;
/*    */   private int status;
/*    */   private String taskUser;
/*    */   private Date startTime;
/*    */   private Date endTime;
/*    */ 
/*    */   @Column(name="TASKUSER", length=1024)
/*    */   public String getTaskUser()
/*    */   {
/* 28 */     return this.taskUser;
/*    */   }
/*    */   public void setTaskUser(String taskUser) {
/* 31 */     this.taskUser = taskUser;
/*    */   }
/* 37 */   @Temporal(TemporalType.DATE)
/*    */   @Column(name="STARTTIME", length=7)
/*    */   public Date getStartTime() { return this.startTime; }
/*    */ 
/*    */   public void setStartTime(Date startTime) {
/* 40 */     this.startTime = startTime; } 
/* 46 */   @GenericGenerator(name="generator", strategy="uuid.hex")
/*    */   @Id
/*    */   @GeneratedValue(generator="generator")
/*    */   @Column(name="TASKID", unique=true, nullable=false, length=64)
/*    */   public String getTaskId() { return this.taskId; }
/*    */ 
/*    */   public void setTaskId(String taskId) {
/* 49 */     this.taskId = taskId;
/*    */   }
/*    */   @Column(name="PROCESSNAME", length=512)
/*    */   public String getProcessName() {
/* 54 */     return this.processName;
/*    */   }
/*    */   public void setProcessName(String processName) {
/* 57 */     this.processName = processName;
/*    */   }
/*    */   @Column(name="INCIDENT")
/*    */   public int getIncident() {
/* 62 */     return this.incident;
/*    */   }
/*    */   public void setIncident(int incident) {
/* 65 */     this.incident = incident;
/*    */   }
/*    */   @Column(name="STEPLABEL", length=256)
/*    */   public String getStepLabel() {
/* 70 */     return this.stepLabel;
/*    */   }
/*    */   public void setStepLabel(String stepLable) {
/* 73 */     this.stepLabel = stepLable;
/*    */   }
/*    */   @Column(name="STATUS")
/*    */   public int getStatus() {
/* 78 */     return this.status;
/*    */   }
/*    */   public void setStatus(int status) {
/* 81 */     this.status = status;
/*    */   }
/* 87 */   @Temporal(TemporalType.DATE)
/*    */   @Column(name="ENDTIME", length=7)
/*    */   public Date getEndTime() { return this.endTime; }
/*    */ 
/*    */   public void setEndTime(Date endTime) {
/* 90 */     this.endTime = endTime;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.model.bo.Tasks
 * JD-Core Version:    0.6.0
 */