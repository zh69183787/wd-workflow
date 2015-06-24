package com.wonders.contact.tListStatus.model.bo;

import java.util.Set;

public class TListStatusType {
	private String code;//分类代码,汉语拼音或英文缩写，建议字母小写VARCHAR2(500)	N
	private String name;//显示的内容VARCHAR2(500)	Y	
	private String uptdate;//to_char(sysdate,'YYYY-MM-DD HH24:mi:ss')		更新的日期VARCHAR2(19)	Y
	private String state;//当前行的状态（0-不可用， 1-可用）CHAR(1)	Y	'1'	
	private String memo;//备注VARCHAR2(500)	Y
	private Set<TListStatus> tListStatuses;
	
	public Set<TListStatus> gettListStatuses() {
		return tListStatuses;
	}
	public void settListStatuses(Set<TListStatus> listStatuses) {
		tListStatuses = listStatuses;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUptdate() {
		return uptdate;
	}
	public void setUptdate(String uptdate) {
		this.uptdate = uptdate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
