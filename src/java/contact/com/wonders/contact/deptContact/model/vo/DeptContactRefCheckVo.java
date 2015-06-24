package com.wonders.contact.deptContact.model.vo;


public class DeptContactRefCheckVo {
	private final String split = "#";
	
	public String main_unit = "";
	public String copy_unit = "";
	
	public String contact_date_start = "";
	public String contact_date_end = "";
	//private String contact_date = "";
	
	public String reply_date_start = "";
	public String reply_date_end = "";
	//private String reply_date = "";
	
	public String theme = "";
	public String status = "";
	
	public String getContact_date() {
		return contact_date_start+split+contact_date_end;
	}
	public String getReply_date() {
		return reply_date_start+split+reply_date_end;
	}
	
	public String getMain_unit() {
		return main_unit;
	}
	public void setMain_unit(String main_unit) {
		this.main_unit = main_unit;
	}
	public String getCopy_unit() {
		return copy_unit;
	}
	public void setCopy_unit(String copy_unit) {
		this.copy_unit = copy_unit;
	}
	public String getContact_date_start() {
		return contact_date_start;
	}
	public void setContact_date_start(String contact_date_start) {
		this.contact_date_start = contact_date_start;
	}
	public String getContact_date_end() {
		return contact_date_end;
	}
	public void setContact_date_end(String contact_date_end) {
		this.contact_date_end = contact_date_end;
	}
	
	public String getReply_date_start() {
		return reply_date_start;
	}
	public void setReply_date_start(String reply_date_start) {
		this.reply_date_start = reply_date_start;
	}
	public String getReply_date_end() {
		return reply_date_end;
	}
	public void setReply_date_end(String reply_date_end) {
		this.reply_date_end = reply_date_end;
	}
	
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
