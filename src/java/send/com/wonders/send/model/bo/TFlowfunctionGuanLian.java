package com.wonders.send.model.bo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TFlowfunctionGuanLian {
	String sid;		//pk
	String processName;	//流程名称
	String incidentNo;	//流程实例号
	String type;		//类型 日程类型schedule,任务类型task,会议类型meeting,流程类型：newflow_sw表示收文流程,newflow_scpj表示收呈批件流程,newflow_fcpj表示发呈批件,newflow_fw表示发文,newflow_xf表示信访流程,备注：与codeinfo表中code一致如需扩展清自行定义code,通知类型notice"
	String displayName;	//显示名称
	String yewuId;		//关联ID
	String url;		//关联URL
	int    removed;		//删除标志位
	String operator;		//操作人
	String operateTime;	//操作时间
	String content;		//备注
	
	
	public  TFlowfunctionGuanLian(){
		this.removed = 0;
		this.operateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(System.currentTimeMillis()));
	}
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getIncidentNo() {
		return incidentNo;
	}
	public void setIncidentNo(String incidentNo) {
		this.incidentNo = incidentNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getYewuId() {
		return yewuId;
	}
	public void setYewuId(String yewuId) {
		this.yewuId = yewuId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getRemoved() {
		return removed;
	}
	public void setRemoved(int removed) {
		this.removed = removed;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
