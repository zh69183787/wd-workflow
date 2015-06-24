/**
 * 
 */
package com.wonders.dataExchange.model.common.bo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * @ClassName: DwWorkflowInterface 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月12日 下午12:00:34 
 *  
 */


@Entity
@Table(name = "DW_WORKFLOW_INTERFACE")
public class DwWorkflowInterface implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2216453640282363323L;
	private String id;
	private String type;
	private String source;
	private String title;
	private String loginName;
	private String userName;
	private	String deptId;
	private String deptName;
	private String createTime;
	private String operateTime;
	private String status;
	private String content;
	private String removed;
	private	String autoLaunch;
	private String logInfo;
	
	
	public DwWorkflowInterface(){
	
	}

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "TYPE", length = 200,nullable = true)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "SOURCE", length = 500,nullable = true)
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	@Column(name = "TITLE", length = 500,nullable = true)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "LOGIN_NAME", length = 50,nullable = true)
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Column(name = "USER_NAME", length = 50,nullable = true)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "DEPT_ID", length = 50,nullable = true)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Column(name = "DEPT_NAME", length = 50,nullable = true)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	@Column(name = "CREATE_TIME", length = 50,nullable = true)
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "OPERATE_TIME", length = 50,nullable = true)
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	@Column(name = "STATUS", length = 50,nullable = true)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Lob
    @Basic(fetch=FetchType.EAGER)
    @Column(name = "CONTENT",columnDefinition = "CLOB")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "REMOVED", length = 50,nullable = true)
	public String getRemoved() {
		return removed;
	}
	public void setRemoved(String removed) {
		this.removed = removed;
	}

	@Column(name = "AUTO_LAUNCH", length = 1,nullable = true)
	public String getAutoLaunch() {
		return autoLaunch;
	}

	public void setAutoLaunch(String autoLaunch) {
		this.autoLaunch = autoLaunch;
	}
	
	@Lob
    @Basic(fetch=FetchType.EAGER)
    @Column(name = "LOG_INFO",columnDefinition = "CLOB")
	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	
}
