package com.wonders.contact.processController.model.result;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class DeptContactDeptResult {
	@Expose
	public int count = 0;
	
	@Expose
	public List<String> deptId = new ArrayList<String>();
	
	public List<String> deptName = new ArrayList<String>();
	
	@Expose
	public List<String> deptUser = new ArrayList<String>();
	
	public boolean isNull(){
		boolean flag = true;
		if(deptId!=null&&deptId.size()>0){
			flag = false;
		}else{
			deptId = null;
		}
		if(deptUser!=null&&deptUser.size()>0){
			flag = false;
		}else{
			deptUser = null;
		}
		if(deptName.size()>0) flag = false;
		if(count>=0) flag = false;
		return flag;
	}
}
