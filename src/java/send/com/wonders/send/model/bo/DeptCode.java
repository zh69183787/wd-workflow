package com.wonders.send.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_DEPT_CODE")
public class DeptCode {
	private String id;
	private String oldDeptId;
	private String newDeptId;
	private String deptCode;
	private String removed;
	private String deptName;
	
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
	
	@Column(name = "OLD_DEPT_ID", nullable = true, length = 50)
	public String getOldDeptId() {
		return oldDeptId;
	}
	public void setOldDeptId(String oldDeptId) {
		this.oldDeptId = oldDeptId;
	}
	
	@Column(name = "NEW_DEPT_ID", nullable = true, length = 50)
	public String getNewDeptId() {
		return newDeptId;
	}
	public void setNewDeptId(String newDeptId) {
		this.newDeptId = newDeptId;
	}
	
	@Column(name = "DEPT_CODE", nullable = true, length = 50)
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	@Column(name = "REMOVED", nullable = true, length = 1)
	public String getRemoved() {
		return removed;
	}
	public void setRemoved(String removed) {
		this.removed = removed;
	}
	
	@Column(name = "DEPT_NAME", nullable = true, length = 50)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
}
