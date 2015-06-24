package com.wonders.contact.processController.model.bo;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.wonders.contact.common.model.bo.ResultInfo;
import com.wonders.contact.processController.model.result.DeptContactDeptResult;

public class DeptContactResult {
	@Expose
	public boolean flag = false;
	
	@Expose
	public String key;

	//子流程信息
	@Expose
	public DeptContactDeptResult sub = new DeptContactDeptResult();
	
	//下级流程信息
	@Expose
	public DeptContactDeptResult lower = new DeptContactDeptResult();

	public ResultInfo resultInfo = new ResultInfo();
	/**
	 * 部门名称字典项
	 */
	public Map<String, String> deptNameCnMap = new HashMap<String, String>();

	public void filterNull(){
		if(key!=null&&key.length()==0) key=null;
		if(sub.isNull()) sub = null;
		if(lower.isNull()) lower = null;
	}
}
