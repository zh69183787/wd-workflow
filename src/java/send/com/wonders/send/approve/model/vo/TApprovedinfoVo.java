/*     */ package com.wonders.send.approve.model.vo;
/*     */ 
/*     */ import com.wonders.send.approve.model.bo.SendApprovedinfo;

/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
import org.springframework.beans.BeanUtils;
/*     */ 
/*     */ public class TApprovedinfoVo
/*     */   implements Serializable
/*     */ {
/*     */   private String guid;
/*     */   private String process;
/*     */   private long incidentno;
/*     */   private String dept;
/*     */   private String stepname;
/*     */   private String username;
/*     */   private String userfullname;
/*     */   private String usermail;
/*     */   private long agree;
/*     */   private Long disagree;
/*     */   private long returned;
/*     */   private String sign;
/*     */   private String signdate;
/*     */   private String remark;
/*     */   private Date upddate;
/*     */   private long status;
/*     */   private String leaderId;
/*     */   private String fllowFlag;
/*     */   private String readFlag;
/*     */   private String planId;
/*     */   private String optionCode;
/*     */   private String deptId;
/*     */   private String fileGroupId;
/*     */   private Integer rounds;
/*     */   private String isLeader;
/*     */ 
/*     */   public String getIsLeader()
/*     */   {
/*  47 */     return this.isLeader;
/*     */   }
/*     */ 
/*     */   public void setIsLeader(String isLeader) {
/*  51 */     this.isLeader = isLeader;
/*     */   }
/*     */ 
/*     */   public String getFileGroupId() {
/*  55 */     return this.fileGroupId;
/*     */   }
/*     */ 
/*     */   public void setFileGroupId(String fileGroupId) {
/*  59 */     this.fileGroupId = fileGroupId;
/*     */   }
/*     */ 
/*     */   public String getDeptId() {
/*  63 */     return this.deptId;
/*     */   }
/*     */ 
/*     */   public void setDeptId(String deptId) {
/*  67 */     this.deptId = deptId;
/*     */   }
/*     */ 
/*     */   public TApprovedinfoVo()
/*     */   {
/*  72 */     this.rounds = Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */   public TApprovedinfoVo(String guid)
/*     */   {
/*  77 */     this.guid = guid;
/*     */   }
/*     */ 
/*     */   public TApprovedinfoVo(String guid, String process, long incidentno, String dept, String stepname, String username, String userfullname, String usermail, long agree, Long disagree, long returned, String sign, String signdate, String remark, Date upddate, long status, String leaderId, String fllowFlag, String readFlag) {
/*  81 */     this.guid = guid;
/*  82 */     this.process = process;
/*  83 */     this.incidentno = incidentno;
/*  84 */     this.dept = dept;
/*  85 */     this.stepname = stepname;
/*  86 */     this.username = username;
/*  87 */     this.userfullname = userfullname;
/*  88 */     this.usermail = usermail;
/*  89 */     this.agree = agree;
/*  90 */     this.disagree = disagree;
/*  91 */     this.returned = returned;
/*  92 */     this.sign = sign;
/*  93 */     this.signdate = signdate;
/*  94 */     this.remark = remark;
/*  95 */     this.upddate = upddate;
/*  96 */     this.status = status;
/*  97 */     this.leaderId = leaderId;
/*  98 */     this.fllowFlag = fllowFlag;
/*  99 */     this.readFlag = readFlag;
/* 100 */     this.rounds = Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */   public TApprovedinfoVo(SendApprovedinfo ti, String isLeader)
/*     */   {
/* 105 */     BeanUtils.copyProperties(ti, this);
/* 106 */     this.isLeader = isLeader;
/*     */   }
/*     */ 
/*     */   public String getGuid() {
/* 110 */     return this.guid;
/*     */   }
/*     */ 
/*     */   public void setGuid(String guid) {
/* 114 */     this.guid = guid;
/*     */   }
/*     */   public String getProcess() {
/* 117 */     return this.process;
/*     */   }
/*     */ 
/*     */   public void setProcess(String process) {
/* 121 */     this.process = process;
/*     */   }
/*     */   public long getIncidentno() {
/* 124 */     return this.incidentno;
/*     */   }
/*     */ 
/*     */   public void setIncidentno(long incidentno) {
/* 128 */     this.incidentno = incidentno;
/*     */   }
/*     */   public String getDept() {
/* 131 */     return this.dept;
/*     */   }
/*     */ 
/*     */   public void setDept(String dept) {
/* 135 */     this.dept = dept;
/*     */   }
/*     */   public String getStepname() {
/* 138 */     return this.stepname;
/*     */   }
/*     */ 
/*     */   public void setStepname(String stepname) {
/* 142 */     this.stepname = stepname;
/*     */   }
/*     */   public String getUsername() {
/* 145 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username) {
/* 149 */     this.username = username;
/*     */   }
/*     */   public String getUserfullname() {
/* 152 */     return this.userfullname;
/*     */   }
/*     */ 
/*     */   public void setUserfullname(String userfullname) {
/* 156 */     this.userfullname = userfullname;
/*     */   }
/*     */   public String getUsermail() {
/* 159 */     return this.usermail;
/*     */   }
/*     */ 
/*     */   public void setUsermail(String usermail) {
/* 163 */     this.usermail = usermail;
/*     */   }
/*     */   public long getAgree() {
/* 166 */     return this.agree;
/*     */   }
/*     */ 
/*     */   public void setAgree(long agree) {
/* 170 */     this.agree = agree;
/*     */   }
/*     */   public Long getDisagree() {
/* 173 */     return this.disagree;
/*     */   }
/*     */ 
/*     */   public void setDisagree(Long disagree) {
/* 177 */     this.disagree = disagree;
/*     */   }
/*     */   public long getReturned() {
/* 180 */     return this.returned;
/*     */   }
/*     */ 
/*     */   public void setReturned(long returned) {
/* 184 */     this.returned = returned;
/*     */   }
/*     */   public String getSign() {
/* 187 */     return this.sign;
/*     */   }
/*     */ 
/*     */   public void setSign(String sign) {
/* 191 */     this.sign = sign;
/*     */   }
/*     */   public String getSigndate() {
/* 194 */     return this.signdate;
/*     */   }
/*     */ 
/*     */   public void setSigndate(String signdate) {
/* 198 */     this.signdate = signdate;
/*     */   }
/*     */   public String getRemark() {
/* 201 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/* 205 */     this.remark = remark;
/*     */   }
/*     */   public Date getUpddate() {
/* 208 */     return this.upddate;
/*     */   }
/*     */ 
/*     */   public void setUpddate(Date upddate) {
/* 212 */     this.upddate = upddate;
/*     */   }
/*     */   public long getStatus() {
/* 215 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(long status) {
/* 219 */     this.status = status;
/*     */   }
/*     */   public String getLeaderId() {
/* 222 */     return this.leaderId;
/*     */   }
/*     */ 
/*     */   public void setLeaderId(String leaderId) {
/* 226 */     this.leaderId = leaderId;
/*     */   }
/*     */   public String getFllowFlag() {
/* 229 */     return this.fllowFlag;
/*     */   }
/*     */ 
/*     */   public void setFllowFlag(String fllowFlag) {
/* 233 */     this.fllowFlag = fllowFlag;
/*     */   }
/*     */   public String getReadFlag() {
/* 236 */     return this.readFlag;
/*     */   }
/*     */ 
/*     */   public void setReadFlag(String readFlag) {
/* 240 */     this.readFlag = readFlag;
/*     */   }
/*     */ 
/*     */   public String getPlanId() {
/* 244 */     return this.planId;
/*     */   }
/*     */ 
/*     */   public void setPlanId(String planId) {
/* 248 */     this.planId = planId;
/*     */   }
/*     */ 
/*     */   public String getOptionCode() {
/* 252 */     return this.optionCode;
/*     */   }
/*     */ 
/*     */   public void setOptionCode(String optionCode) {
/* 256 */     this.optionCode = optionCode;
/*     */   }
/*     */ 
/*     */   public Integer getRounds() {
/* 260 */     return this.rounds;
/*     */   }
/*     */ 
/*     */   public void setRounds(Integer rounds) {
/* 264 */     this.rounds = rounds;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.approve.model.vo.TApprovedinfoVo
 * JD-Core Version:    0.6.0
 */