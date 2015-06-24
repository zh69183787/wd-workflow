/*     */ package com.wonders.send.future.model.bo;
/*     */ 
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ import org.hibernate.annotations.GenericGenerator;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="T_FUTURE", schema="STPT")
/*     */ public class Future
/*     */ {
/*     */   private String id;
/*     */   private String processName;
/*     */   private String incidentNo;
/*     */   private String stepName;
/*     */   private String userId;
/*     */   private String username;
/*     */   private String lastdate;
/*     */   private String deptList;
/*     */   private String leaderList;
/*     */   private String finishList;
/*     */   private String deptIdList;
/*     */   private String finishlistname;
/*     */   private String zbdeptid;
/*     */   private String zbdeptname;
/*     */   private String xbdeptid;
/*     */   private String xbdeptname;
/*     */   private String qfldid;
/*     */   private String qfldname;
/*     */   private String nbsugdept;
/*     */   private String nbsugdeptldid;
/*     */   private String pshqldid;
/*     */   private String pshqldname;
/*     */   private String psshldid;
/*     */   private String psshldname;
/*     */   private String hqldid;
/*     */   private String hqldname;
/*     */   private String shldid;
/*     */   private String shldname;
/*     */   private String zbdeptreid;
/*     */   private String xbdeptreid;
/*     */   private String qfldmsid;
/*     */   private String pshqldmsid;
/*     */   private String psshldmsid;
/*     */   private String hqldmsid;
/*     */   private String shldmsid;
/*     */   private String qfldmsname;
/*     */   private String pshqldmsname;
/*     */   private String psshldmsname;
/*     */   private String hqldmsname;
/*     */   private String shldmsname;
/*     */   private String qflddeptid;
/*     */   private String qfldmsdeptid;
/*     */   private String pshqlddeptid;
/*     */   private String pshqldmsdeptid;
/*     */   private String psshlddeptid;
/*     */   private String psshldmsdeptid;
/*     */   private String hqlddeptid;
/*     */   private String hqldmsdeptid;
/*     */   private String shlddeptid;
/*     */   private String shldmsdeptid;
/*     */   private String finishlistdeptid;
/*     */ 
/*     */   @Column(name="FINISHLISTNAME", length=4000)
/*     */   public String getFinishlistname()
/*     */   {
/*  76 */     return this.finishlistname;
/*     */   }
/*     */ 
/*     */   public void setFinishlistname(String finishlistname) {
/*  80 */     this.finishlistname = finishlistname;
/*     */   }
/*     */   @Column(name="ZBDEPTID", length=4000)
/*     */   public String getZbdeptid() {
/*  85 */     return this.zbdeptid;
/*     */   }
/*     */ 
/*     */   public void setZbdeptid(String zbdeptid) {
/*  89 */     this.zbdeptid = zbdeptid;
/*     */   }
/*     */   @Column(name="ZBDEPTNAME", length=4000)
/*     */   public String getZbdeptname() {
/*  94 */     return this.zbdeptname;
/*     */   }
/*     */ 
/*     */   public void setZbdeptname(String zbdeptname) {
/*  98 */     this.zbdeptname = zbdeptname;
/*     */   }
/*     */   @Column(name="XBDEPTID", length=4000)
/*     */   public String getXbdeptid() {
/* 103 */     return this.xbdeptid;
/*     */   }
/*     */ 
/*     */   public void setXbdeptid(String xbdeptid) {
/* 107 */     this.xbdeptid = xbdeptid;
/*     */   }
/*     */   @Column(name="XBDEPTNAME", length=4000)
/*     */   public String getXbdeptname() {
/* 112 */     return this.xbdeptname;
/*     */   }
/*     */ 
/*     */   public void setXbdeptname(String xbdeptname) {
/* 116 */     this.xbdeptname = xbdeptname;
/*     */   }
/*     */   @Column(name="QFLDID", length=4000)
/*     */   public String getQfldid() {
/* 121 */     return this.qfldid;
/*     */   }
/*     */ 
/*     */   public void setQfldid(String qfldid) {
/* 125 */     this.qfldid = qfldid;
/*     */   }
/*     */   @Column(name="QFLDNAME", length=4000)
/*     */   public String getQfldname() {
/* 130 */     return this.qfldname;
/*     */   }
/*     */ 
/*     */   public void setQfldname(String qfldname) {
/* 134 */     this.qfldname = qfldname;
/*     */   }
/*     */   @Column(name="NBSUGDEPT", length=4000)
/*     */   public String getNbsugdept() {
/* 139 */     return this.nbsugdept;
/*     */   }
/*     */ 
/*     */   public void setNbsugdept(String nbsugdept) {
/* 143 */     this.nbsugdept = nbsugdept;
/*     */   }
/*     */   @Column(name="NBSUGDEPTLDID", length=4000)
/*     */   public String getNbsugdeptldid() {
/* 148 */     return this.nbsugdeptldid;
/*     */   }
/*     */ 
/*     */   public void setNbsugdeptldid(String nbsugdeptldid) {
/* 152 */     this.nbsugdeptldid = nbsugdeptldid;
/*     */   }
/*     */   @Column(name="PSHQLDID", length=4000)
/*     */   public String getPshqldid() {
/* 157 */     return this.pshqldid;
/*     */   }
/*     */ 
/*     */   public void setPshqldid(String pshqldid) {
/* 161 */     this.pshqldid = pshqldid;
/*     */   }
/*     */   @Column(name="PSHQLDNAME", length=4000)
/*     */   public String getPshqldname() {
/* 166 */     return this.pshqldname;
/*     */   }
/*     */ 
/*     */   public void setPshqldname(String pshqldname) {
/* 170 */     this.pshqldname = pshqldname;
/*     */   }
/*     */   @Column(name="PSSHLDID", length=4000)
/*     */   public String getPsshldid() {
/* 175 */     return this.psshldid;
/*     */   }
/*     */ 
/*     */   public void setPsshldid(String psshldid) {
/* 179 */     this.psshldid = psshldid;
/*     */   }
/*     */   @Column(name="PSSHLDNAME", length=4000)
/*     */   public String getPsshldname() {
/* 184 */     return this.psshldname;
/*     */   }
/*     */ 
/*     */   public void setPsshldname(String psshldname) {
/* 188 */     this.psshldname = psshldname;
/*     */   }
/*     */   @Column(name="HQLDID", length=4000)
/*     */   public String getHqldid() {
/* 193 */     return this.hqldid;
/*     */   }
/*     */ 
/*     */   public void setHqldid(String hqldid) {
/* 197 */     this.hqldid = hqldid;
/*     */   }
/*     */   @Column(name="HQLDNAME", length=4000)
/*     */   public String getHqldname() {
/* 202 */     return this.hqldname;
/*     */   }
/*     */ 
/*     */   public void setHqldname(String hqldname) {
/* 206 */     this.hqldname = hqldname;
/*     */   }
/*     */   @Column(name="SHLDID", length=4000)
/*     */   public String getShldid() {
/* 211 */     return this.shldid;
/*     */   }
/*     */ 
/*     */   public void setShldid(String shldid) {
/* 215 */     this.shldid = shldid;
/*     */   }
/*     */   @Column(name="SHLDNAME", length=4000)
/*     */   public String getShldname() {
/* 220 */     return this.shldname;
/*     */   }
/*     */ 
/*     */   public void setShldname(String shldname) {
/* 224 */     this.shldname = shldname;
/*     */   }
/*     */   @Column(name="ZBDEPTREID", length=4000)
/*     */   public String getZbdeptreid() {
/* 229 */     return this.zbdeptreid;
/*     */   }
/*     */ 
/*     */   public void setZbdeptreid(String zbdeptreid) {
/* 233 */     this.zbdeptreid = zbdeptreid;
/*     */   }
/*     */   @Column(name="XBDEPTREID", length=4000)
/*     */   public String getXbdeptreid() {
/* 238 */     return this.xbdeptreid;
/*     */   }
/*     */ 
/*     */   public void setXbdeptreid(String xbdeptreid) {
/* 242 */     this.xbdeptreid = xbdeptreid;
/*     */   }
/*     */   @Column(name="QFLDMSID", length=4000)
/*     */   public String getQfldmsid() {
/* 247 */     return this.qfldmsid;
/*     */   }
/*     */ 
/*     */   public void setQfldmsid(String qfldmsid) {
/* 251 */     this.qfldmsid = qfldmsid;
/*     */   }
/*     */   @Column(name="PSHQLDMSID", length=4000)
/*     */   public String getPshqldmsid() {
/* 256 */     return this.pshqldmsid;
/*     */   }
/*     */ 
/*     */   public void setPshqldmsid(String pshqldmsid) {
/* 260 */     this.pshqldmsid = pshqldmsid;
/*     */   }
/*     */   @Column(name="PSSHLDMSID", length=4000)
/*     */   public String getPsshldmsid() {
/* 265 */     return this.psshldmsid;
/*     */   }
/*     */ 
/*     */   public void setPsshldmsid(String psshldmsid) {
/* 269 */     this.psshldmsid = psshldmsid;
/*     */   }
/*     */   @Column(name="HQLDMSID", length=4000)
/*     */   public String getHqldmsid() {
/* 274 */     return this.hqldmsid;
/*     */   }
/*     */ 
/*     */   public void setHqldmsid(String hqldmsid) {
/* 278 */     this.hqldmsid = hqldmsid;
/*     */   }
/*     */   @Column(name="SHLDMSID", length=4000)
/*     */   public String getShldmsid() {
/* 283 */     return this.shldmsid;
/*     */   }
/*     */ 
/*     */   public void setShldmsid(String shldmsid) {
/* 287 */     this.shldmsid = shldmsid;
/*     */   }
/*     */   @Column(name="QFLDMSNAME", length=4000)
/*     */   public String getQfldmsname() {
/* 292 */     return this.qfldmsname;
/*     */   }
/*     */ 
/*     */   public void setQfldmsname(String qfldmsname) {
/* 296 */     this.qfldmsname = qfldmsname;
/*     */   }
/*     */   @Column(name="PSHQLDMSNAME", length=4000)
/*     */   public String getPshqldmsname() {
/* 301 */     return this.pshqldmsname;
/*     */   }
/*     */ 
/*     */   public void setPshqldmsname(String pshqldmsname) {
/* 305 */     this.pshqldmsname = pshqldmsname;
/*     */   }
/*     */   @Column(name="PSSHLDMSNAME", length=4000)
/*     */   public String getPsshldmsname() {
/* 310 */     return this.psshldmsname;
/*     */   }
/*     */ 
/*     */   public void setPsshldmsname(String psshldmsname) {
/* 314 */     this.psshldmsname = psshldmsname;
/*     */   }
/*     */   @Column(name="HQLDMSNAME", length=4000)
/*     */   public String getHqldmsname() {
/* 319 */     return this.hqldmsname;
/*     */   }
/*     */ 
/*     */   public void setHqldmsname(String hqldmsname) {
/* 323 */     this.hqldmsname = hqldmsname;
/*     */   }
/*     */   @Column(name="SHLDMSNAME", length=4000)
/*     */   public String getShldmsname() {
/* 328 */     return this.shldmsname;
/*     */   }
/*     */ 
/*     */   public void setShldmsname(String shldmsname) {
/* 332 */     this.shldmsname = shldmsname;
/*     */   }
/*     */   @Column(name="DEPT_ID_LIST", length=4000)
/*     */   public String getDeptIdList() {
/* 337 */     return this.deptIdList;
/*     */   }
/*     */ 
/*     */   public void setDeptIdList(String deptIdList) {
/* 341 */     this.deptIdList = deptIdList;
/*     */   }
/*     */   @Column(name="DEPTLIST", length=4000)
/*     */   public String getDeptList() {
/* 346 */     return this.deptList;
/*     */   }
/*     */ 
/*     */   public void setDeptList(String deptList) {
/* 350 */     this.deptList = deptList;
/*     */   }
/*     */   @Column(name="LEADERLIST", length=4000)
/*     */   public String getLeaderList() {
/* 355 */     return this.leaderList;
/*     */   }
/*     */ 
/*     */   public void setLeaderList(String leaderList) {
/* 359 */     this.leaderList = leaderList;
/*     */   }
/*     */   @Column(name="DINISHLIST", length=4000)
/*     */   public String getFinishList() {
/* 364 */     return this.finishList;
/*     */   }
/*     */ 
/*     */   public void setFinishList(String finishList) {
/* 368 */     this.finishList = finishList; } 
/* 377 */   @GenericGenerator(name="generator", strategy="uuid.hex")
/*     */   @Id
/*     */   @GeneratedValue(generator="generator")
/*     */   @Column(name="ID", unique=true, nullable=false, length=50)
/*     */   public String getId() { return this.id; }
/*     */ 
/*     */   public void setId(String id) {
/* 380 */     this.id = id;
/*     */   }
/*     */   @Column(name="PROCESSNAME", length=200)
/*     */   public String getProcessName() {
/* 385 */     return this.processName;
/*     */   }
/*     */   public void setProcessName(String processName) {
/* 388 */     this.processName = processName;
/*     */   }
/*     */   @Column(name="INCIDENT", length=50)
/*     */   public String getIncidentNo() {
/* 393 */     return this.incidentNo;
/*     */   }
/*     */   public void setIncidentNo(String incidentNo) {
/* 396 */     this.incidentNo = incidentNo;
/*     */   }
/*     */   @Column(name="STEPNAME", length=100)
/*     */   public String getStepName() {
/* 401 */     return this.stepName;
/*     */   }
/*     */   public void setStepName(String stepName) {
/* 404 */     this.stepName = stepName;
/*     */   }
/*     */   @Column(name="USERID", length=100)
/*     */   public String getUserId() {
/* 409 */     return this.userId;
/*     */   }
/*     */   public void setUserId(String userId) {
/* 412 */     this.userId = userId;
/*     */   }
/*     */   @Column(name="USERNAME", length=200)
/*     */   public String getUsername() {
/* 417 */     return this.username;
/*     */   }
/*     */   public void setUsername(String username) {
/* 420 */     this.username = username;
/*     */   }
/*     */   @Column(name="LASTDATE", length=20)
/*     */   public String getLastdate() {
/* 425 */     return this.lastdate;
/*     */   }
/*     */   public void setLastdate(String lastdate) {
/* 428 */     this.lastdate = lastdate;
/*     */   }
/*     */   @Column(name="QFLDDEPTID", length=4000)
/*     */   public String getQflddeptid() {
/* 433 */     return this.qflddeptid;
/*     */   }
/*     */ 
/*     */   public void setQflddeptid(String qflddeptid) {
/* 437 */     this.qflddeptid = qflddeptid;
/*     */   }
/*     */   @Column(name="QFLDMSDEPTID", length=4000)
/*     */   public String getQfldmsdeptid() {
/* 442 */     return this.qfldmsdeptid;
/*     */   }
/*     */ 
/*     */   public void setQfldmsdeptid(String qfldmsdeptid) {
/* 446 */     this.qfldmsdeptid = qfldmsdeptid;
/*     */   }
/*     */   @Column(name="PSHQLDDEPTID", length=4000)
/*     */   public String getPshqlddeptid() {
/* 451 */     return this.pshqlddeptid;
/*     */   }
/*     */ 
/*     */   public void setPshqlddeptid(String pshqlddeptid) {
/* 455 */     this.pshqlddeptid = pshqlddeptid;
/*     */   }
/*     */   @Column(name="PSHQLDMSDEPTID", length=4000)
/*     */   public String getPshqldmsdeptid() {
/* 460 */     return this.pshqldmsdeptid;
/*     */   }
/*     */ 
/*     */   public void setPshqldmsdeptid(String pshqldmsdeptid) {
/* 464 */     this.pshqldmsdeptid = pshqldmsdeptid;
/*     */   }
/*     */   @Column(name="PSSHLDDEPTID", length=4000)
/*     */   public String getPsshlddeptid() {
/* 469 */     return this.psshlddeptid;
/*     */   }
/*     */ 
/*     */   public void setPsshlddeptid(String psshlddeptid) {
/* 473 */     this.psshlddeptid = psshlddeptid;
/*     */   }
/*     */   @Column(name="PSSHLDMSDEPTID", length=4000)
/*     */   public String getPsshldmsdeptid() {
/* 478 */     return this.psshldmsdeptid;
/*     */   }
/*     */ 
/*     */   public void setPsshldmsdeptid(String psshldmsdeptid) {
/* 482 */     this.psshldmsdeptid = psshldmsdeptid;
/*     */   }
/*     */   @Column(name="HQLDDEPTID", length=4000)
/*     */   public String getHqlddeptid() {
/* 487 */     return this.hqlddeptid;
/*     */   }
/*     */ 
/*     */   public void setHqlddeptid(String hqlddeptid) {
/* 491 */     this.hqlddeptid = hqlddeptid;
/*     */   }
/*     */   @Column(name="HQLDMSDEPTID", length=4000)
/*     */   public String getHqldmsdeptid() {
/* 496 */     return this.hqldmsdeptid;
/*     */   }
/*     */ 
/*     */   public void setHqldmsdeptid(String hqldmsdeptid) {
/* 500 */     this.hqldmsdeptid = hqldmsdeptid;
/*     */   }
/*     */   @Column(name="SHLDDEPTID", length=4000)
/*     */   public String getShlddeptid() {
/* 505 */     return this.shlddeptid;
/*     */   }
/*     */ 
/*     */   public void setShlddeptid(String shlddeptid) {
/* 509 */     this.shlddeptid = shlddeptid;
/*     */   }
/*     */   @Column(name="SHLDMSDEPTID", length=4000)
/*     */   public String getShldmsdeptid() {
/* 514 */     return this.shldmsdeptid;
/*     */   }
/*     */ 
/*     */   public void setShldmsdeptid(String shldmsdeptid) {
/* 518 */     this.shldmsdeptid = shldmsdeptid;
/*     */   }
/*     */   @Column(name="FINISHLISTDEPTID", length=4000)
/*     */   public String getFinishlistdeptid() {
/* 523 */     return this.finishlistdeptid;
/*     */   }
/*     */ 
/*     */   public void setFinishlistdeptid(String finishlistdeptid) {
/* 527 */     this.finishlistdeptid = finishlistdeptid;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.future.model.bo.Future
 * JD-Core Version:    0.6.0
 */