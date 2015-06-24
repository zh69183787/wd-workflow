package com.wonders.contact.deptContact.model.bo;

import com.google.gson.annotations.Expose;

/**
 * TDeptContactMain entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TDeptContactMain implements java.io.Serializable {

	// Fields

	@Expose
	private String id;
	@Expose
	private String mainUnitId;
	@Expose
	private String mainUnit;
	@Expose
	private String copyUnitId;
	@Expose
	private String copyUnit;
	@Expose
	private String contactDate;
	@Expose
	private String replyDate;
	@Expose
	private String timeDiff;
	@Expose
	private String theme;
	@Expose
	private String content;
	@Expose
	private String contentAttachmentId;
	@Expose
	private String processname;
	@Expose
	private String incidentno;
	@Expose
	private Integer serial;
	@Expose
	private String createDeptid;
	@Expose
	private String createDeptname;
	@Expose
	private String initiator;
	@Expose
	private String initiatorName;
	@Expose
	private String startTime;
	@Expose
	private String updateTime;
	@Expose
	private String operateUser;
	@Expose
	private String operateName;
	@Expose
	private String operateDate;
	@Expose
	private Integer removed;
	


	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainUnitId() {
		return this.mainUnitId;
	}

	public void setMainUnitId(String mainUnitId) {
		this.mainUnitId = mainUnitId;
	}

	public String getMainUnit() {
		return this.mainUnit;
	}

	public void setMainUnit(String mainUnit) {
		this.mainUnit = mainUnit;
	}

	public String getCopyUnitId() {
		return this.copyUnitId;
	}

	public void setCopyUnitId(String copyUnitId) {
		this.copyUnitId = copyUnitId;
	}

	public String getCopyUnit() {
		return this.copyUnit;
	}

	public void setCopyUnit(String copyUnit) {
		this.copyUnit = copyUnit;
	}

	public String getContactDate() {
		return this.contactDate;
	}

	public void setContactDate(String contactDate) {
		this.contactDate = contactDate;
	}

	public String getReplyDate() {
		return this.replyDate;
	}

	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}

	public String getTimeDiff() {
		return this.timeDiff;
	}

	public void setTimeDiff(String timeDiff) {
		this.timeDiff = timeDiff;
	}

	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentAttachmentId() {
		return this.contentAttachmentId;
	}

	public void setContentAttachmentId(String contentAttachmentId) {
		this.contentAttachmentId = contentAttachmentId;
	}

	public String getProcessname() {
		return this.processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public String getIncidentno() {
		return this.incidentno;
	}

	public void setIncidentno(String incidentno) {
		this.incidentno = incidentno;
	}

	public String getCreateDeptid() {
		return this.createDeptid;
	}

	public void setCreateDeptid(String createDeptid) {
		this.createDeptid = createDeptid;
	}

	public String getCreateDeptname() {
		return this.createDeptname;
	}

	public void setCreateDeptname(String createDeptname) {
		this.createDeptname = createDeptname;
	}

	public String getInitiator() {
		return this.initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getInitiatorName() {
		return this.initiatorName;
	}

	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getOperateUser() {
		return this.operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public String getOperateName() {
		return this.operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOperateDate() {
		return this.operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

}