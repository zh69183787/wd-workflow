package com.wonders.contact.model.bo;

import java.util.Date;

public class BaseBO {
	private Long operateTime;
	private String operator;
	private int removed;
	
	public long getOperateTime() {
		if(operateTime==null){
			Date d = new Date();
			operateTime = d.getTime();
		}
		return operateTime;
	}
	public void setOperateTime(long operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getRemoved() {
		return removed;
	}
	public void setRemoved(int removed) {
		this.removed = removed;
	}
}
