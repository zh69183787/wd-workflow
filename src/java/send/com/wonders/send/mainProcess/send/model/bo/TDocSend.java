/*     */package com.wonders.send.mainProcess.send.model.bo;

/*     */
/*     */import java.io.Serializable;
/*     */
import javax.persistence.Column;
/*     */
import javax.persistence.Entity;
/*     */
import javax.persistence.GeneratedValue;
/*     */
import javax.persistence.Id;
/*     */
import javax.persistence.Table;
/*     */
import org.hibernate.annotations.GenericGenerator;

/*     */
/*     */@Entity
/*     */@Table(name = "T_DOC_SEND", schema = "STPT")
/*     */public class TDocSend
/*     */implements Serializable
/*     */{
	/*     */private String id;
	/*     */private String attFlag;
	/*     */private String contentAtt;
	/*     */private String docClass;
	/*     */private String docCount;
	/*     */private String docKeyword;
	/*     */private String docState;
	/*     */private String docTitle;
	/*     */private String modelid;
	/*     */private String operator;
	/*     */private String pinstanceid;
	/*     */private String secretClass;
	/*     */private String snedCopy;
	/*     */private String sendDate;
	/*     */private String sendId;
	/*     */private String sendId1;
	/*     */private String sendId2;
	/*     */private String sendInside;
	/*     */private String sendMain;
	/*     */private String sendUser;
	/*     */private String sendUserdept;
	/*     */private String signDept;
	/*     */private String signLead;
	/*     */private String userid;
	/*     */private String viseLead;
	/*     */private String workitemid;
	/*     */private String operateTime;
	/*     */private Long removed;
	/*     */private String code1;
	/*     */private String code2;
	/*     */private String code3;
	/*     */private String hj;
	/*     */private String taskid;
	/*     */private String fileType;
	/*     */private String secretLimit;
	/*     */private String attachRemark;
	/*     */private String sendReport;
	/*     */private String lineType;
	/*     */private String taskuser;
	/*     */private String sendMainId;
	/*     */private String sendMainW;
	/*     */private String sendInsideId;
	/*     */private String sendReportId;
	/*     */private String sendReportW;
	/*     */private String sendUserLeader;
	/*     */private String contentAttMain;
	/*     */private String personname;
	/*     */private String flag;
	/*     */private Long autScanFlag;
	/*     */private String contentAttTyping;
	/*     */private String sendType;
	/*     */private String sendTitleType;
	/*     */private String typeTitle;
    private String sendFileType;
	private String normative;

	/*     */
	/*     */public TDocSend()
	/*     */{
		/*     */}

	/*     */
	/*     */public TDocSend(String attFlag, String contentAtt, String docClass,
			String docCount, String docKeyword, String docState,
			String docTitle, String modelid, String operator,
			String pinstanceid, String secretClass, String snedCopy,
			String sendDate, String sendId, String sendId1, String sendId2,
			String sendInside, String sendMain, String sendUser,
			String sendUserdept, String signDept, String signLead,
			String userid, String viseLead, String workitemid,
			String operateTime, Long removed, String code1, String code2,
			String code3, String hj, String taskid, String fileType,
			String secretLimit, String attachRemark, String sendReport,
			String lineType, String taskuser, String sendMainId,
			String sendMainW, String sendInsideId, String sendReportId,
			String sendReportW, String sendUserLeader, String contentAttMain,
			String personname, String flag, Long autScanFlag,
			String contentAttTyping, String sendType, String sendTitleType,
			String typeTitle)
	/*     */{
		/* 90 */this.attFlag = attFlag;
		/* 91 */this.contentAtt = contentAtt;
		/* 92 */this.docClass = docClass;
		/* 93 */this.docCount = docCount;
		/* 94 */this.docKeyword = docKeyword;
		/* 95 */this.docState = docState;
		/* 96 */this.docTitle = docTitle;
		/* 97 */this.modelid = modelid;
		/* 98 */this.operator = operator;
		/* 99 */this.pinstanceid = pinstanceid;
		/* 100 */this.secretClass = secretClass;
		/* 101 */this.snedCopy = snedCopy;
		/* 102 */this.sendDate = sendDate;
		/* 103 */this.sendId = sendId;
		/* 104 */this.sendId1 = sendId1;
		/* 105 */this.sendId2 = sendId2;
		/* 106 */this.sendInside = sendInside;
		/* 107 */this.sendMain = sendMain;
		/* 108 */this.sendUser = sendUser;
		/* 109 */this.sendUserdept = sendUserdept;
		/* 110 */this.signDept = signDept;
		/* 111 */this.signLead = signLead;
		/* 112 */this.userid = userid;
		/* 113 */this.viseLead = viseLead;
		/* 114 */this.workitemid = workitemid;
		/* 115 */this.operateTime = operateTime;
		/* 116 */this.removed = removed;
		/* 117 */this.code1 = code1;
		/* 118 */this.code2 = code2;
		/* 119 */this.code3 = code3;
		/* 120 */this.hj = hj;
		/* 121 */this.taskid = taskid;
		/* 122 */this.fileType = fileType;
		/* 123 */this.secretLimit = secretLimit;
		/* 124 */this.attachRemark = attachRemark;
		/* 125 */this.sendReport = sendReport;
		/* 126 */this.lineType = lineType;
		/* 127 */this.taskuser = taskuser;
		/* 128 */this.sendMainId = sendMainId;
		/* 129 */this.sendMainW = sendMainW;
		/* 130 */this.sendInsideId = sendInsideId;
		/* 131 */this.sendReportId = sendReportId;
		/* 132 */this.sendReportW = sendReportW;
		/* 133 */this.sendUserLeader = sendUserLeader;
		/* 134 */this.contentAttMain = contentAttMain;
		/* 135 */this.personname = personname;
		/* 136 */this.flag = flag;
		/* 137 */this.autScanFlag = autScanFlag;
		/* 138 */this.contentAttTyping = contentAttTyping;
		/* 139 */this.sendType = sendType;
		/* 140 */this.sendTitleType = sendTitleType;
		/* 141 */this.typeTitle = typeTitle;
		/*     */}

	/* 151 */@GenericGenerator(name = "generator", strategy = "uuid.hex")
	/*     */@Id
	/*     */@GeneratedValue(generator = "generator")
	/*     */@Column(name = "ID", unique = true, nullable = false, length = 50)
	/*     */public String getId() {
		return this.id;
	}

	/*     */
	/*     */public void setId(String id)
	/*     */{
		/* 155 */this.id = id;
		/*     */}

	/*     */
	/*     */@Column(name = "ATT_FLAG", length = 2)
	/*     */public String getAttFlag() {
		/* 161 */return this.attFlag;
		/*     */}

	/*     */
	/*     */public void setAttFlag(String attFlag) {
		/* 165 */this.attFlag = attFlag;
		/*     */}

	/*     */
	/*     */@Column(name = "CONTENT_ATT", length = 200)
	/*     */public String getContentAtt() {
		/* 171 */return this.contentAtt;
		/*     */}

	/*     */
	/*     */public void setContentAtt(String contentAtt) {
		/* 175 */this.contentAtt = contentAtt;
		/*     */}

	/*     */
	/*     */@Column(name = "DOC_CLASS", length = 20)
	/*     */public String getDocClass() {
		/* 181 */return this.docClass;
		/*     */}

	/*     */
	/*     */public void setDocClass(String docClass) {
		/* 185 */this.docClass = docClass;
		/*     */}

	/*     */
	/*     */@Column(name = "DOC_COUNT", length = 400)
	/*     */public String getDocCount() {
		/* 191 */return this.docCount;
		/*     */}

	/*     */
	/*     */public void setDocCount(String docCount) {
		/* 195 */this.docCount = docCount;
		/*     */}

	/*     */
	/*     */@Column(name = "DOC_KEYWORD", length = 400)
	/*     */public String getDocKeyword() {
		/* 201 */return this.docKeyword;
		/*     */}

	/*     */
	/*     */public void setDocKeyword(String docKeyword) {
		/* 205 */this.docKeyword = docKeyword;
		/*     */}

	/*     */
	/*     */@Column(name = "DOC_STATE", length = 200)
	/*     */public String getDocState() {
		/* 211 */return this.docState;
		/*     */}

	/*     */
	/*     */public void setDocState(String docState) {
		/* 215 */this.docState = docState;
		/*     */}

	/*     */
	/*     */@Column(name = "DOC_TITLE", length = 400)
	/*     */public String getDocTitle() {
		/* 221 */return this.docTitle;
		/*     */}

	/*     */
	/*     */public void setDocTitle(String docTitle) {
		/* 225 */this.docTitle = docTitle;
		/*     */}

	/*     */
	/*     */@Column(name = "MODELID", length = 200)
	/*     */public String getModelid() {
		/* 231 */return this.modelid;
		/*     */}

	/*     */
	/*     */public void setModelid(String modelid) {
		/* 235 */this.modelid = modelid;
		/*     */}

	/*     */
	/*     */@Column(name = "OPERATOR", length = 200)
	/*     */public String getOperator() {
		/* 241 */return this.operator;
		/*     */}

	/*     */
	/*     */public void setOperator(String operator) {
		/* 245 */this.operator = operator;
		/*     */}

	/*     */
	/*     */@Column(name = "PINSTANCEID", length = 200)
	/*     */public String getPinstanceid() {
		/* 251 */return this.pinstanceid;
		/*     */}

	/*     */
	/*     */public void setPinstanceid(String pinstanceid) {
		/* 255 */this.pinstanceid = pinstanceid;
		/*     */}

	/*     */
	/*     */@Column(name = "SECRET_CLASS", length = 200)
	/*     */public String getSecretClass() {
		/* 261 */return this.secretClass;
		/*     */}

	/*     */
	/*     */public void setSecretClass(String secretClass) {
		/* 265 */this.secretClass = secretClass;
		/*     */}

	/*     */
	/*     */@Column(name = "SNED_COPY", length = 4000)
	/*     */public String getSnedCopy() {
		/* 271 */return this.snedCopy;
		/*     */}

	/*     */
	/*     */public void setSnedCopy(String snedCopy) {
		/* 275 */this.snedCopy = snedCopy;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_DATE", length = 20)
	/*     */public String getSendDate() {
		/* 281 */return this.sendDate;
		/*     */}

	/*     */
	/*     */public void setSendDate(String sendDate) {
		/* 285 */this.sendDate = sendDate;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_ID", length = 400)
	/*     */public String getSendId() {
		/* 291 */return this.sendId;
		/*     */}

	/*     */
	/*     */public void setSendId(String sendId) {
		/* 295 */this.sendId = sendId;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_ID_1", length = 4000)
	/*     */public String getSendId1() {
		/* 301 */return this.sendId1;
		/*     */}

	/*     */
	/*     */public void setSendId1(String sendId1) {
		/* 305 */this.sendId1 = sendId1;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_ID_2", length = 4000)
	/*     */public String getSendId2() {
		/* 311 */return this.sendId2;
		/*     */}

	/*     */
	/*     */public void setSendId2(String sendId2) {
		/* 315 */this.sendId2 = sendId2;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_INSIDE", length = 4000)
	/*     */public String getSendInside() {
		/* 321 */return this.sendInside;
		/*     */}

	/*     */
	/*     */public void setSendInside(String sendInside) {
		/* 325 */this.sendInside = sendInside;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_MAIN", length = 4000)
	/*     */public String getSendMain() {
		/* 331 */return this.sendMain;
		/*     */}

	/*     */
	/*     */public void setSendMain(String sendMain) {
		/* 335 */this.sendMain = sendMain;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_USER", length = 200)
	/*     */public String getSendUser() {
		/* 341 */return this.sendUser;
		/*     */}

	/*     */
	/*     */public void setSendUser(String sendUser) {
		/* 345 */this.sendUser = sendUser;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_USERDEPT", length = 200)
	/*     */public String getSendUserdept() {
		/* 351 */return this.sendUserdept;
		/*     */}

	/*     */
	/*     */public void setSendUserdept(String sendUserdept) {
		/* 355 */this.sendUserdept = sendUserdept;
		/*     */}

	/*     */
	/*     */@Column(name = "SIGN_DEPT", length = 4000)
	/*     */public String getSignDept() {
		/* 361 */return this.signDept;
		/*     */}

	/*     */
	/*     */public void setSignDept(String signDept) {
		/* 365 */this.signDept = signDept;
		/*     */}

	/*     */
	/*     */@Column(name = "SIGN_LEAD", length = 4000)
	/*     */public String getSignLead() {
		/* 371 */return this.signLead;
		/*     */}

	/*     */
	/*     */public void setSignLead(String signLead) {
		/* 375 */this.signLead = signLead;
		/*     */}

	/*     */
	/*     */@Column(name = "USERID", length = 200)
	/*     */public String getUserid() {
		/* 381 */return this.userid;
		/*     */}

	/*     */
	/*     */public void setUserid(String userid) {
		/* 385 */this.userid = userid;
		/*     */}

	/*     */
	/*     */@Column(name = "VISE_LEAD", length = 200)
	/*     */public String getViseLead() {
		/* 391 */return this.viseLead;
		/*     */}

	/*     */
	/*     */public void setViseLead(String viseLead) {
		/* 395 */this.viseLead = viseLead;
		/*     */}

	/*     */
	/*     */@Column(name = "WORKITEMID", length = 200)
	/*     */public String getWorkitemid() {
		/* 401 */return this.workitemid;
		/*     */}

	/*     */
	/*     */public void setWorkitemid(String workitemid) {
		/* 405 */this.workitemid = workitemid;
		/*     */}

	/*     */
	/*     */@Column(name = "OPERATE_TIME", length = 400)
	/*     */public String getOperateTime() {
		/* 411 */return this.operateTime;
		/*     */}

	/*     */
	/*     */public void setOperateTime(String operateTime) {
		/* 415 */this.operateTime = operateTime;
		/*     */}

	/*     */
	/*     */@Column(name = "REMOVED", precision = 10, scale = 0)
	/*     */public Long getRemoved() {
		/* 421 */return this.removed;
		/*     */}

	/*     */
	/*     */public void setRemoved(Long removed) {
		/* 425 */this.removed = removed;
		/*     */}

	/*     */
	/*     */@Column(name = "CODE1", length = 20)
	/*     */public String getCode1() {
		/* 431 */return this.code1;
		/*     */}

	/*     */
	/*     */public void setCode1(String code1) {
		/* 435 */this.code1 = code1;
		/*     */}

	/*     */
	/*     */@Column(name = "CODE2", length = 20)
	/*     */public String getCode2() {
		/* 441 */return this.code2;
		/*     */}

	/*     */
	/*     */public void setCode2(String code2) {
		/* 445 */this.code2 = code2;
		/*     */}

	/*     */
	/*     */@Column(name = "CODE3", length = 20)
	/*     */public String getCode3() {
		/* 451 */return this.code3;
		/*     */}

	/*     */
	/*     */public void setCode3(String code3) {
		/* 455 */this.code3 = code3;
		/*     */}

	/*     */
	/*     */@Column(name = "HJ", length = 60)
	/*     */public String getHj() {
		/* 461 */return this.hj;
		/*     */}

	/*     */
	/*     */public void setHj(String hj) {
		/* 465 */this.hj = hj;
		/*     */}

	/*     */
	/*     */@Column(name = "TASKID", length = 200)
	/*     */public String getTaskid() {
		/* 471 */return this.taskid;
		/*     */}

	/*     */
	/*     */public void setTaskid(String taskid) {
		/* 475 */this.taskid = taskid;
		/*     */}

	/*     */
	/*     */@Column(name = "FILE_TYPE", length = 200)
	/*     */public String getFileType() {
		/* 481 */return this.fileType;
		/*     */}

	/*     */
	/*     */public void setFileType(String fileType) {
		/* 485 */this.fileType = fileType;
		/*     */}

	/*     */
	/*     */@Column(name = "SECRET_LIMIT", length = 200)
	/*     */public String getSecretLimit() {
		/* 491 */return this.secretLimit;
		/*     */}

	/*     */
	/*     */public void setSecretLimit(String secretLimit) {
		/* 495 */this.secretLimit = secretLimit;
		/*     */}

	/*     */
	/*     */@Column(name = "ATTACH_REMARK", length = 4000)
	/*     */public String getAttachRemark() {
		/* 501 */return this.attachRemark;
		/*     */}

	/*     */
	/*     */public void setAttachRemark(String attachRemark) {
		/* 505 */this.attachRemark = attachRemark;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_REPORT", length = 4000)
	/*     */public String getSendReport() {
		/* 511 */return this.sendReport;
		/*     */}

	/*     */
	/*     */public void setSendReport(String sendReport) {
		/* 515 */this.sendReport = sendReport;
		/*     */}

	/*     */
	/*     */@Column(name = "LINE_TYPE", length = 200)
	/*     */public String getLineType() {
		/* 521 */return this.lineType;
		/*     */}

	/*     */
	/*     */public void setLineType(String lineType) {
		/* 525 */this.lineType = lineType;
		/*     */}

	/*     */
	/*     */@Column(name = "TASKUSER", length = 400)
	/*     */public String getTaskuser() {
		/* 531 */return this.taskuser;
		/*     */}

	/*     */
	/*     */public void setTaskuser(String taskuser) {
		/* 535 */this.taskuser = taskuser;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_MAIN_ID", length = 4000)
	/*     */public String getSendMainId() {
		/* 541 */return this.sendMainId;
		/*     */}

	/*     */
	/*     */public void setSendMainId(String sendMainId) {
		/* 545 */this.sendMainId = sendMainId;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_MAIN_W", length = 4000)
	/*     */public String getSendMainW() {
		/* 551 */return this.sendMainW;
		/*     */}

	/*     */
	/*     */public void setSendMainW(String sendMainW) {
		/* 555 */this.sendMainW = sendMainW;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_INSIDE_ID", length = 4000)
	/*     */public String getSendInsideId() {
		/* 561 */return this.sendInsideId;
		/*     */}

	/*     */
	/*     */public void setSendInsideId(String sendInsideId) {
		/* 565 */this.sendInsideId = sendInsideId;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_REPORT_ID", length = 4000)
	/*     */public String getSendReportId() {
		/* 571 */return this.sendReportId;
		/*     */}

	/*     */
	/*     */public void setSendReportId(String sendReportId) {
		/* 575 */this.sendReportId = sendReportId;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_REPORT_W", length = 4000)
	/*     */public String getSendReportW() {
		/* 581 */return this.sendReportW;
		/*     */}

	/*     */
	/*     */public void setSendReportW(String sendReportW) {
		/* 585 */this.sendReportW = sendReportW;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_USER_LEADER", length = 200)
	/*     */public String getSendUserLeader() {
		/* 591 */return this.sendUserLeader;
		/*     */}

	/*     */
	/*     */public void setSendUserLeader(String sendUserLeader) {
		/* 595 */this.sendUserLeader = sendUserLeader;
		/*     */}

	/*     */
	/*     */@Column(name = "CONTENT_ATT_MAIN", length = 200)
	/*     */public String getContentAttMain() {
		/* 601 */return this.contentAttMain;
		/*     */}

	/*     */
	/*     */public void setContentAttMain(String contentAttMain) {
		/* 605 */this.contentAttMain = contentAttMain;
		/*     */}

	/*     */
	/*     */@Column(name = "PERSONNAME", length = 2000)
	/*     */public String getPersonname() {
		/* 611 */return this.personname;
		/*     */}

	/*     */
	/*     */public void setPersonname(String personname) {
		/* 615 */this.personname = personname;
		/*     */}

	/*     */
	/*     */@Column(name = "FLAG", length = 10)
	/*     */public String getFlag() {
		/* 621 */return this.flag;
		/*     */}

	/*     */
	/*     */public void setFlag(String flag) {
		/* 625 */this.flag = flag;
		/*     */}

	/*     */
	/*     */@Column(name = "AUT_SCAN_FLAG")
	/*     */public Long getAutScanFlag() {
		/* 631 */return this.autScanFlag;
		/*     */}

	/*     */
	/*     */public void setAutScanFlag(Long autScanFlag) {
		/* 635 */this.autScanFlag = autScanFlag;
		/*     */}

	/*     */
	/*     */@Column(name = "CONTENT_ATT_TYPING", length = 200)
	/*     */public String getContentAttTyping() {
		/* 641 */return this.contentAttTyping;
		/*     */}

	/*     */
	/*     */public void setContentAttTyping(String contentAttTyping) {
		/* 645 */this.contentAttTyping = contentAttTyping;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_TYPE", length = 200)
	/*     */public String getSendType() {
		/* 651 */return this.sendType;
		/*     */}

	/*     */
	/*     */public void setSendType(String sendType) {
		/* 655 */this.sendType = sendType;
		/*     */}

	/*     */
	/*     */@Column(name = "SEND_TITLE_TYPE", length = 200)
	/*     */public String getSendTitleType() {
		/* 661 */return this.sendTitleType;
		/*     */}

	/*     */
	/*     */public void setSendTitleType(String sendTitleType) {
		/* 665 */this.sendTitleType = sendTitleType;
		/*     */}

	/*     */
	/*     */@Column(name = "TYPE_TITLE", length = 200)
	/*     */public String getTypeTitle() {
		/* 671 */return this.typeTitle;
		/*     */}

	/*     */
	/*     */public void setTypeTitle(String typeTitle) {
		/* 675 */this.typeTitle = typeTitle;
		/*     */}

	@Column(name = "NORMATIVE", length = 1)
	public String getNormative() {
		return normative;
	}

	public void setNormative(String normative) {
		this.normative = normative;
	}

    @Column(name = "SEND_FILE_TYPE", length = 10)
    public String getSendFileType() {
        return sendFileType;
    }

    public void setSendFileType(String sendFileType) {
        this.sendFileType = sendFileType;
    }

    /*     */
}

/*
 * Location: \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\ Qualified
 * Name: com.wonders.workflow.mainProcess.send.model.bo.TDocSend JD-Core
 * Version: 0.6.0
 */