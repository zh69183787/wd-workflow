package com.wonders.contact.deptContact.model.vo;

import com.wonders.util.StringUtil;

public class DeptContactMainVo {
	private String id;
	private String mainUnitId;
	private String mainUnit;
	private String copyUnitId;
	private String copyUnit;
	private String contactDate;
	private String replyDate;
	private int timeDiff;
	private String theme;
	private String content;
	private String contentAttachmentId;
	
	//private String createDeptname;
	//private String initiatorName;
	
	;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMainUnitId() {
		return mainUnitId;
	}
	public void setMainUnitId(String mainUnitId) {
		this.mainUnitId = mainUnitId;
	}
	public String getMainUnit() {
		return mainUnit;
	}
	public void setMainUnit(String mainUnit) {
		this.mainUnit = mainUnit;
	}
	public String getCopyUnitId() {
		return copyUnitId;
	}
	public void setCopyUnitId(String copyUnitId) {
		this.copyUnitId = copyUnitId;
	}
	public String getCopyUnit() {
		return copyUnit;
	}
	public void setCopyUnit(String copyUnit) {
		this.copyUnit = copyUnit;
	}
	public String getContactDate() {
		return contactDate;
	}
	public void setContactDate(String contactDate) {
		this.contactDate = contactDate;
	}
	public String getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}
	public int getTimeDiff() {
		return timeDiff;
	}
	public void setTimeDiff(int timeDiff) {
		this.timeDiff = timeDiff;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentAttachmentId() {
		return contentAttachmentId;
	}
	public void setContentAttachmentId(String contentAttachmentId) {
		this.contentAttachmentId = contentAttachmentId;
	}
	
	@Override
	public String toString(){
		String ret = "DeptContactMainVo: id:"+id+" mainUnitId:"
		+mainUnitId+" mainUnit"+mainUnit
		+" copyUnitId:"+copyUnitId+" copyUnit:"+copyUnit+"\n"
		+" contactDate:"+contactDate+" replyDate"+replyDate+" timeDiff:"+timeDiff+"\n"
		+" theme:"+theme+" content:"+StringUtil.subStr(content, 20)+"..."+" contentAttachmentId:"+contentAttachmentId;
		
		return ret;
		
	}
	/*
	public String getCreateDeptname() {
		return createDeptname;
	}
	public void setCreateDeptname(String createDeptname) {
		this.createDeptname = createDeptname;
	}
	public String getInitiatorName() {
		return initiatorName;
	}
	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
	*/
}
