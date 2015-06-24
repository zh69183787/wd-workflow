package com.wonders.contact.deptContact.util;

import com.wonders.contact.common.exception.ProcessException;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.bo.TDeptContactTree;
import com.wonders.contact.processController.constant.ProcessControllerConstants;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

public class DeptContactUtil {
	
	public static TDeptContactTree generateTreeBoByParentMainBo(TDeptContactMain mainBo){
		TDeptContactTree tree = generateTreeBo();
		
		tree.setPname(mainBo.getProcessname());
		tree.setPincident(mainBo.getIncidentno());
		tree.setPId(mainBo.getId());
			
		return tree;
	}
	
	public static TDeptContactTree generateTreeBoByParentTreeBo(TDeptContactTree parentTreeBo){
		TDeptContactTree tree = generateTreeBo();
		
		tree.setPname(parentTreeBo.getCname());
		tree.setPincident(parentTreeBo.getCincident());
		tree.setPId(parentTreeBo.getCId());
			
		return tree;
	}
	
	public static TDeptContactTree generateTreeBo(){
		TDeptContactTree tree = new TDeptContactTree();
		
		tree.setCname(DeptContactConstants.PROCESSNAME);
		tree.setStatus(ProcessControllerConstants.STATUS_ACTIVE);
		
		return tree;
	}
	
	public static void setOperateInfo(TDeptContactMain mainBo,String loginName,String name,String time){
		loginName = StringUtil.getNotNullValueString(loginName);
		name = StringUtil.getNotNullValueString(name);
		time = StringUtil.getNotNullValueString(time);
		
		if(loginName.length()>0) mainBo.setOperateUser(loginName);
		if(name.length()>0) mainBo.setOperateName(name);
		if(time.length()>0){
			mainBo.setOperateDate(time);
		}else{
			mainBo.setOperateDate(DateUtil.getNowTime());
		}
		
	}
	
	public static void setOperateInfo(TDeptContactTree treeBo,String loginName,String name,String time){
		loginName = StringUtil.getNotNullValueString(loginName);
		name = StringUtil.getNotNullValueString(name);
		time = StringUtil.getNotNullValueString(time);
		
		if(loginName.length()>0) treeBo.setOperateUser(loginName);
		if(name.length()>0) treeBo.setOperateName(name);
		if(time.length()>0){
			treeBo.setOperateUser(time);
		}else{
			treeBo.setOperateUser(DateUtil.getNowTime());
		}
		
	}
	
	public static void copyOperateInfo(TDeptContactTree treeBo,TDeptContactMain mainBo){
		treeBo.setOperateDate(mainBo.getOperateDate());
		treeBo.setOperateUser(mainBo.getOperateUser());
		treeBo.setOperateName(mainBo.getOperateName());
	}
	
	public static void checkMainBo(TDeptContactMain mainBo){
		if(mainBo==null){
			throw new ProcessException("找不到符合的mainBo信息！");
		}
	}
	
	public static void checkTreeBo(TDeptContactTree treeBo){
		if(treeBo==null){
			throw new ProcessException("找不到符合的mainBo信息！");
		}
	}
	
	public static void checkKey(String key){
		if(StringUtil.getNotNullValueString(key).length()==0){
			throw new ProcessException("key值为空！");
		}
	}
	
}
