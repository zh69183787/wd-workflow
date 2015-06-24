/**
 * 
 */
package com.wonders.receive.workflow.model.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** 
 * @ClassName: Tasks 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 上午10:56:13 
 *  
 */

@Entity
@Table(name = "TASKS")
public class Tasks implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1043422906962625347L;
	private String taskId;
	private String processname;
	private int incident;
	private String stepLabel;
	private int status;
	private String taskUser;
	private Date startTime;
	private Date endTime;
	/**
	 * @return the taskId
	 */
	@Id
	@Column(name = "TASKID", nullable = true, length = 64)
	public String getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	/**
	 * @return the processname
	 */
	@Column(name = "PROCESSNAME", nullable = true, length = 512)
	public String getProcessname() {
		return processname;
	}
	/**
	 * @param processname the processname to set
	 */
	public void setProcessname(String processname) {
		this.processname = processname;
	}
	/**
	 * @return the incident
	 */
	@Column(name = "INCIDENT")
	public int getIncident() {
		return incident;
	}
	/**
	 * @param incident the incident to set
	 */
	public void setIncident(int incident) {
		this.incident = incident;
	}
	/**
	 * @return the stepLabel
	 */
	@Column(name = "STEPLABEL", nullable = true, length = 256)
	public String getStepLabel() {
		return stepLabel;
	}
	/**
	 * @param stepLabel the stepLabel to set
	 */
	public void setStepLabel(String stepLabel) {
		this.stepLabel = stepLabel;
	}
	/**
	 * @return the status
	 */
	@Column(name = "STATUS")
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the taskUser
	 */
	@Column(name = "TASKUSER", nullable = true, length = 1024)
	public String getTaskUser() {
		return taskUser;
	}
	/**
	 * @param taskUser the taskUser to set
	 */
	public void setTaskUser(String taskUser) {
		this.taskUser = taskUser;
	}
	/**
	 * @return the startTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTTIME")
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDTIME")
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	

}
