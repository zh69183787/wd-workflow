package com.wonders.contact.external.model.vo;

import java.util.HashMap;
import java.util.Map;

import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.bo.TDeptContactTree;
import com.wonders.contact.deptContact.model.vo.DeptContactOperateVo;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessVo;
import com.wonders.contact.todo.model.bo.TodoItem;

public class OperateVo {
	//接口参数
	public String todoId = "";
	public String data = "";//流程处理信息data
	public String userInfo = "";
	
	
	//临时变量
	public String attachId = "";
	public String choice = "";
	public String suggest = "";
	
	public TodoItem todoItem = null;
	
	public Map<String,String> todoDataMap = new HashMap<String,String>();
	
	public TDeptContactMain main = new TDeptContactMain();
	
	public TDeptContactTree tree = new TDeptContactTree();
	
	public DeptContactOperateVo operateVoDC = new DeptContactOperateVo();
	
	public DeptSubProcessVo operateVoDSP = new DeptSubProcessVo();
	
	public UserInfo user = new UserInfo();
	
	public Map<String,String> data_map = new HashMap<String,String>();
}
