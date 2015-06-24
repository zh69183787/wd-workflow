package com.wonders.contact.external.model.data;

import com.google.gson.annotations.Expose;
import com.wonders.contact.approvedInfo.model.vo.DeptContactApprovedInfoVo;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;

public class DeptContactData {
	@Expose
	public String type;
	@Expose
	public TDeptContactMain main = new TDeptContactMain();
	@Expose
	public DeptContactApprovedInfoVo comment = new DeptContactApprovedInfoVo();
}
