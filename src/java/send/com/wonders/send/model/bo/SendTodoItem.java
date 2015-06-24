/**
 * 
 */
package com.wonders.send.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * @ClassName: TodoItem 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 下午03:29:06 
 *  
 */

@Entity
@Table(name = "T_TODO_ITEM")
public class SendTodoItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8471458287730081764L;
	private String id = "";
	private String app;
	private Integer type;
	private String key;
	private String occurTime;
	private String title;
	private String data;
	private String userId;
	private String loginName;
	private String deptId;
	private Integer status;
	private Integer removed;
	private String typename;
	private String applydept;
	private String url;
	private String pname;
	private Integer pincident;
	private String cname;
	private Integer cincident;
	private String stepName;
	private String taskId;
	private String taskType;
	private String dbxType;
	private String initiator;
	private String initdept;
	
	public SendTodoItem(){
		this.removed = 0;
		this.app = "schedule";
	}
	
	/**
	 * @return the initdept
	 */
	@Column(name = "INITDEPT", nullable = true, length = 50)
	public String getInitdept() {
		return initdept;
	}



	/**
	 * @param initdept the initdept to set
	 */
	public void setInitdept(String initdept) {
		this.initdept = initdept;
	}



	/**
	 * @return the initiator
	 */
	@Column(name = "INITIATOR", nullable = true, length = 50)
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
	 * @return the dbxType
	 */
	@Column(name = "DBXTYPE", nullable = true, length = 50)
	public String getDbxType() {
		return dbxType;
	}


	/**
	 * @param dbxType the dbxType to set
	 */
	public void setDbxType(String dbxType) {
		this.dbxType = dbxType;
	}


	/**
	 * @return the taskType
	 */
	@Column(name = "TASKTYPE", nullable = true, length = 50)
	public String getTaskType() {
		return taskType;
	}


	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "APP", nullable = true, length = 50)
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	
	@Column(name = "TYPE")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "KEY", nullable = true, length = 50)
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Column(name = "OCCURTIME", nullable = true, length = 50)
	public String getOccurTime() {
		return occurTime;
	}
	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}
	
	@Column(name = "TITLE", nullable = true, length = 1000)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "DATA", nullable = true, length = 4000)
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Column(name = "USERID", nullable = true, length = 50)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "LOGINNAME", nullable = true, length = 50)
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Column(name = "DEPTID", nullable = true, length = 50)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "REMOVED")
	public Integer getRemoved() {
		return removed;
	}
	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	
	@Column(name = "TYPENAME", nullable = true, length = 50)
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	@Column(name = "APPLYDEPT", nullable = true, length = 50)
	public String getApplydept() {
		return applydept;
	}
	public void setApplydept(String applydept) {
		this.applydept = applydept;
	}
	
	/**
	 * @return the url
	 */
	@Column(name = "URL", nullable = true, length = 4000)
	public String getUrl() {
		return url;
	}
	
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * @return the pname
	 */
	@Column(name = "PNAME", nullable = true, length = 200)
	public String getPname() {
		return pname;
	}
	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}
	/**
	 * @return the pincident
	 */
	@Column(name = "PINCIDENT")
	public Integer getPincident() {
		return pincident;
	}
	/**
	 * @param pincident the pincident to set
	 */
	public void setPincident(Integer pincident) {
		this.pincident = pincident;
	}
	/**
	 * @return the cname
	 */
	@Column(name = "CNAME", nullable = true, length = 200)
	public String getCname() {
		return cname;
	}
	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}
	/**
	 * @return the cincident
	 */
	@Column(name = "CINCIDENT")
	public Integer getCincident() {
		return cincident;
	}
	/**
	 * @param cincident the cincident to set
	 */
	public void setCincident(Integer cincident) {
		this.cincident = cincident;
	}
	/**
	 * @return the stepName
	 */
	@Column(name = "STEPNAME", nullable = true, length = 200)
	public String getStepName() {
		return stepName;
	}
	/**
	 * @param stepName the stepName to set
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	/**
	 * @return the taskId
	 */
	@Column(name = "TASKID", nullable = true, length = 200)
	public String getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
}
	
