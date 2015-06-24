/*     */ package com.wonders.send.approve.model.bo;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
/*     */ import org.hibernate.annotations.GenericGenerator;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="T_APPROVEDINFO")
/*     */ public class SendApprovedinfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2053160602236876832L;
/*     */   private String guid;
/*     */   private String process;
/*     */   private Long incidentno;
/*     */   private String dept;
/*     */   private String stepname;
/*     */   private String username;
/*     */   private String userfullname;
/*     */   private String usermail;
/*     */   private Long agree;
/*     */   private Long disagree;
/*     */   private Long returned;
/*     */   private String sign;
/*     */   private String signdate;
/*     */   private String remark;
/*     */   private Date upddate;
/*     */   private Long status;
/*     */   private String leaderId;
/*     */   private String fllowFlag;
/*     */   private String readFlag;
/*     */   private String planId;
/*     */   private String optionCode;
/*     */   private String deptId;
/*     */   private String fileGroupId;
/*     */   private Integer rounds;
/*     */ 
/*     */   @Id
/*     */   @GeneratedValue(generator="system-uuid")
/*     */   @GenericGenerator(name="system-uuid", strategy="uuid")
/*     */   @Column(name="GUID")
/*     */   public String getGuid()
/*     */   {
/*  65 */     return this.guid;
/*     */   }
/*     */ 
/*     */   public void setGuid(String guid) {
/*  69 */     this.guid = guid;
/*     */   }
/*     */   @Column(name="PROCESS", nullable=true, length=50)
/*     */   public String getProcess() {
/*  74 */     return this.process;
/*     */   }
/*     */ 
/*     */   public void setProcess(String process) {
/*  78 */     this.process = process;
/*     */   }
/*     */   @Column(name="INCIDENTNO")
/*     */   public Long getIncidentno() {
/*  83 */     return this.incidentno;
/*     */   }
/*     */ 
/*     */   public void setIncidentno(Long incidentno) {
/*  87 */     this.incidentno = incidentno;
/*     */   }
/*     */   @Column(name="DEPT", nullable=true, length=50)
/*     */   public String getDept() {
/*  92 */     return this.dept;
/*     */   }
/*     */ 
/*     */   public void setDept(String dept) {
/*  96 */     this.dept = dept;
/*     */   }
/*     */   @Column(name="STEPNAME", nullable=true, length=50)
/*     */   public String getStepname() {
/* 101 */     return this.stepname;
/*     */   }
/*     */ 
/*     */   public void setStepname(String stepname) {
/* 105 */     this.stepname = stepname;
/*     */   }
/*     */   @Column(name="USERNAME", nullable=true, length=50)
/*     */   public String getUsername() {
/* 110 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username) {
/* 114 */     this.username = username;
/*     */   }
/*     */   @Column(name="USERFULLNAME", nullable=true, length=50)
/*     */   public String getUserfullname() {
/* 119 */     return this.userfullname;
/*     */   }
/*     */ 
/*     */   public void setUserfullname(String userfullname) {
/* 123 */     this.userfullname = userfullname;
/*     */   }
/*     */   @Column(name="USERMAIL", nullable=true, length=50)
/*     */   public String getUsermail() {
/* 128 */     return this.usermail;
/*     */   }
/*     */ 
/*     */   public void setUsermail(String usermail) {
/* 132 */     this.usermail = usermail;
/*     */   }
/*     */   @Column(name="AGREE")
/*     */   public Long getAgree() {
/* 137 */     return this.agree;
/*     */   }
/*     */ 
/*     */   public void setAgree(Long agree) {
/* 141 */     this.agree = agree;
/*     */   }
/*     */   @Column(name="DISAGREE")
/*     */   public Long getDisagree() {
/* 146 */     return this.disagree;
/*     */   }
/*     */ 
/*     */   public void setDisagree(Long disagree) {
/* 150 */     this.disagree = disagree;
/*     */   }
/*     */   @Column(name="RETURNED")
/*     */   public Long getReturned() {
/* 155 */     return this.returned;
/*     */   }
/*     */ 
/*     */   public void setReturned(Long returned) {
/* 159 */     this.returned = returned;
/*     */   }
/*     */   @Column(name="SIGN", nullable=true, length=50)
/*     */   public String getSign() {
/* 164 */     return this.sign;
/*     */   }
/*     */ 
/*     */   public void setSign(String sign) {
/* 168 */     this.sign = sign;
/*     */   }
/*     */   @Column(name="SIGNDATE", nullable=true, length=50)
/*     */   public String getSigndate() {
/* 173 */     return this.signdate;
/*     */   }
/*     */ 
/*     */   public void setSigndate(String signdate) {
/* 177 */     this.signdate = signdate;
/*     */   }
/*     */   @Column(name="REMARK", nullable=true, length=2000)
/*     */   public String getRemark() {
/* 182 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/* 186 */     this.remark = remark;
/*     */   }
/* 192 */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Column(name="UPDDATE")
/*     */   public Date getUpddate() { return this.upddate; }
/*     */ 
/*     */   public void setUpddate(Date upddate)
/*     */   {
/* 196 */     this.upddate = upddate;
/*     */   }
/*     */   @Column(name="STATUS")
/*     */   public Long getStatus() {
/* 201 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Long status) {
/* 205 */     this.status = status;
/*     */   }
/*     */   @Column(name="LEADER_ID", nullable=true, length=50)
/*     */   public String getLeaderId() {
/* 210 */     return this.leaderId;
/*     */   }
/*     */ 
/*     */   public void setLeaderId(String leaderId) {
/* 214 */     this.leaderId = leaderId;
/*     */   }
/*     */   @Column(name="FLLOW_FLAG", nullable=true, length=50)
/*     */   public String getFllowFlag() {
/* 219 */     return this.fllowFlag;
/*     */   }
/*     */ 
/*     */   public void setFllowFlag(String fllowFlag) {
/* 223 */     this.fllowFlag = fllowFlag;
/*     */   }
/*     */   @Column(name="READ_FLAG", nullable=true, length=50)
/*     */   public String getReadFlag() {
/* 228 */     return this.readFlag;
/*     */   }
/*     */ 
/*     */   public void setReadFlag(String readFlag) {
/* 232 */     this.readFlag = readFlag;
/*     */   }
/*     */   @Column(name="PLAN_ID", nullable=true, length=100)
/*     */   public String getPlanId() {
/* 237 */     return this.planId;
/*     */   }
/*     */ 
/*     */   public void setPlanId(String planId) {
/* 241 */     this.planId = planId;
/*     */   }
/*     */   @Column(name="OPTION_CODE", nullable=true, length=200)
/*     */   public String getOptionCode() {
/* 246 */     return this.optionCode;
/*     */   }
/*     */ 
/*     */   public void setOptionCode(String optionCode) {
/* 250 */     this.optionCode = optionCode;
/*     */   }
/*     */   @Column(name="DEPT_ID", nullable=true, length=200)
/*     */   public String getDeptId() {
/* 255 */     return this.deptId;
/*     */   }
/*     */ 
/*     */   public void setDeptId(String deptId) {
/* 259 */     this.deptId = deptId;
/*     */   }
/*     */   @Column(name="FILE_GROUP_ID", nullable=true, length=38)
/*     */   public String getFileGroupId() {
/* 264 */     return this.fileGroupId;
/*     */   }
/*     */ 
/*     */   public void setFileGroupId(String fileGroupId) {
/* 268 */     this.fileGroupId = fileGroupId;
/*     */   }
/*     */   @Column(name="ROUNDS")
/*     */   public Integer getRounds() {
/* 273 */     return this.rounds;
/*     */   }
/*     */ 
/*     */   public void setRounds(Integer rounds) {
/* 277 */     this.rounds = rounds;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.approve.model.bo.TApprovedinfo
 * JD-Core Version:    0.6.0
 */