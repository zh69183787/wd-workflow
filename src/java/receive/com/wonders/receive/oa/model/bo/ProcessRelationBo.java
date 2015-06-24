/**
 * 
 */
package com.wonders.receive.oa.model.bo;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * @ClassName: ProcessRelation 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-6 上午9:59:39 
 *  
 */
@Entity
@Table(name = "T_FLOWFUNCTION_GUANLIAN")
public class ProcessRelationBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3704805072680022636L;
	private String sid;		//pk
	private String processName;	//流程名称
	private String incidentNo;	//流程实例号

	private String type;		//类型 日程类型schedule,任务类型task,会议类型meeting,流程类型：newflow_sw表示收文流程,newflow_scpj表示收呈批件流程,newflow_fcpj表示发呈批件,newflow_fw表示发文,newflow_xf表示信访流程,备注：与codeinfo表中code一致如需扩展清自行定义code,通知类型notice"
	private String displayName;	//显示名称
	private String yewuId;		//关联ID
	private String url;		//关联URL
	private Integer removed;		//删除标志位

	private String operator;		//操作人

	private String operateTime;	//操作时间
	private String content;		//备注
	
	public ProcessRelationBo(){
		this.removed = 0;
		this.operateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
	
	}
	
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "SID")
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	@Column(name = "PROCESSNAME", nullable = true, length = 200)
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	@Column(name = "INCIDENTNO", nullable = true, length = 10)
	public String getIncidentNo() {
		return incidentNo;
	}
	public void setIncidentNo(String incidentNo) {
		this.incidentNo = incidentNo;
	}
	
	@Column(name = "TYPE", nullable = true, length = 200)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "DISPLAYNAME", nullable = true, length = 2000)
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Column(name = "YEWU_ID", nullable = true, length = 50)
	public String getYewuId() {
		return yewuId;
	}
	public void setYewuId(String yewuId) {
		this.yewuId = yewuId;
	}
	
	@Column(name = "URL", nullable = true, length = 2000)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "REMOVED")
	public Integer getRemoved() {
		return removed;
	}
	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	
	@Column(name = "OPERATOR", nullable = true, length = 200)
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(name = "OPERATE_TIME", nullable = true, length = 200)
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	@Column(name = "CONTENT", nullable = true, length = 2000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
