package com.wonders.contact.deptContact.model.bo;

/**
 * TDeptContactReference entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TDeptContactReference implements java.io.Serializable {

	// Fields

	private String id;
	private String mainId;
	private String refId;
	private Long orders;
	private String operateUser;
	private String operateName;
	private String operateDate;
	private Integer removed;
	private String ext1;
	private String ext2;
	private String ext3;

	
	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainId() {
		return this.mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getRefId() {
		return this.refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public Long getOrders() {
		return this.orders;
	}

	public void setOrders(Long orders) {
		this.orders = orders;
	}

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