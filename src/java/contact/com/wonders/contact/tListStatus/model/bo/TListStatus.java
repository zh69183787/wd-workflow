package com.wonders.contact.tListStatus.model.bo;

import java.util.Set;

public class TListStatus {
	private long sid;		//内部流水号NUMBER		
	private String content;	//显示的内容VARCHAR2(500)		
	private Integer optorder;	//显示的顺序NUMBER	
	private TListStatusType infotype;	//显示信息的类型，其他涉及到该表的类别信息以此字段进行判别VARCHAR2(500)		
	private String uptdate;	//更新的日期VARCHAR2(19)	to_char(sysdate,'YYYY-MM-DD HH24:mi:ss')
	private String state;	//当前行的状态（0-不可用， 1-可用）CHAR(1)		'1'	
	private String keyvalue;	//一些可能用到的关键字段VARCHAR2(500)
	private String memo;	//备注VARCHAR2(2000)	
	
	private TListStatus refsid;
	private Set<TListStatus> childTListStatus;
	
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getOptorder() {
		return optorder;
	}
	public void setOptorder(Integer optorder) {
		this.optorder = optorder;
	}
	public TListStatusType getInfotype() {
		return infotype;
	}
	public void setInfotype(TListStatusType infotype) {
		this.infotype = infotype;
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
	public TListStatus getRefsid() {
		return refsid;
	}
	public void setRefsid(TListStatus refsid) {
		this.refsid = refsid;
	}
	public String getKeyvalue() {
		return keyvalue;
	}
	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Set<TListStatus> getChildTListStatus() {
		return childTListStatus;
	}
	public void setChildTListStatus(Set<TListStatus> childTListStatus) {
		this.childTListStatus = childTListStatus;
	}

}
