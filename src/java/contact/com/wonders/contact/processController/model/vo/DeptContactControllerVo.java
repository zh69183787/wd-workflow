package com.wonders.contact.processController.model.vo;

import org.springframework.stereotype.Component;

@Component("deptContactControllerVo")
public class DeptContactControllerVo extends ControllerVo{
	private String deptId = "";
	private String subType = "";

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}
	
	@Override
	public String toString(){
		return super.toString()+" deptId:"+deptId+" subType:"+subType;
	}
}
