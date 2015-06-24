/**
 * 
 */
package com.wonders.receive.workflow.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.wonders.receive.workflow.model.pk.IncidentsPK;

/** 
 * @ClassName: Incidents 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 上午10:56:08 
 *  
 */
@Entity
@IdClass(value=IncidentsPK.class) 
@Table(name = "INCIDENTS")
public class Incidents implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4967884353991433783L;
	private String processname;
	private Integer incident;
	private Integer status;
	private String summary;
	private String initiator;
	private String priorities;
	private Integer processVersion;
	/**
	 * @return the processname
	 */
	@Id
	//@Column(name = "PROCESSNAME", nullable = true, length = 512)
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
	@Id
	//@Column(name = "INCIDENT")
	public Integer getIncident() {
		return incident;
	}
	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Integer incident) {
		this.incident = incident;
	}
	/**
	 * @return the status
	 */
	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the summary
	 */
	@Column(name = "SUMMARY", nullable = true, length = 1600)
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return the initiator
	 */
	@Column(name = "INITIATOR", nullable = true, length = 1024)
	public String getInitiator() {
		return initiator;
	}
	/**
	 * @param initiator the initiator to set
	 */
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	/**
	 * @return the priorities
	 */
	@Column(name = "PRIORITIES", nullable = true, length = 200)
	public String getPriorities() {
		return priorities;
	}
	/**
	 * @param priorities the priorities to set
	 */
	public void setPriorities(String priorities) {
		this.priorities = priorities;
	}
	/**
	 * @return the processVersion
	 */
	@Column(name = "PROCESSVERSION")
	public Integer getProcessVersion() {
		return processVersion;
	}
	/**
	 * @param processVersion the processVersion to set
	 */
	public void setProcessVersion(Integer processVersion) {
		this.processVersion = processVersion;
	}

	
}
