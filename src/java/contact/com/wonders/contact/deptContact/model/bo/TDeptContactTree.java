package com.wonders.contact.deptContact.model.bo;

/**
 * TDeptContactTree entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TDeptContactTree implements java.io.Serializable {

	// Fields

	private String id;
	private String PId;
	private String CId;
	private String pname;
	private String pincident;
	private String cname;
	private String cincident;
	private Integer status;
	private Integer type;
	
	private String operateUser;
	private String operateName;
	private String operateDate;
	private Integer removed;
	private String ext1;
	private String ext2;
	private String ext3;

	
	private String mainUnitId;
	private String deptids;
	
	
	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPId() {
		return this.PId;
	}

	public void setPId(String PId) {
		this.PId = PId;
	}

	public String getCId() {
		return this.CId;
	}

	public void setCId(String CId) {
		this.CId = CId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPname() {
		return this.pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPincident() {
		return this.pincident;
	}

	public void setPincident(String pincident) {
		this.pincident = pincident;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCincident() {
		return this.cincident;
	}

	public void setCincident(String cincident) {
		this.cincident = cincident;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMainUnitId() {
		return this.mainUnitId;
	}

	public void setMainUnitId(String mainUnitId) {
		this.mainUnitId = mainUnitId;
	}
	
	public String getDeptids() {
		return this.deptids;
	}

	public void setDeptids(String deptids) {
		this.deptids = deptids;
	}
	/**/
	public String getOperateUser() {
		return this.operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public String getOperateName() {
		return this.operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOperateDate() {
		return this.operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

	public String getExt1() {
		return this.ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return this.ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return this.ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

}