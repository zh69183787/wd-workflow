package com.wonders.contact.deptContact.model.vo;

import com.wonders.contact.common.model.vo.ParamVo;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.bo.TDeptContactTree;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
public class DeptContactParamVo extends ParamVo{
	public DeptContactMainVo mainVo;
	public DeptContactOperateVo operateVo;
	
	public TDeptContactTree treeBo;
	public TDeptContactMain mainBo;
	
	@Override
	public String toString(){
		if(mainVo!=null){
			return mainVo.toString();
		}
		if(operateVo!=null){
			return operateVo.toString();
		}
		
		return "vo is null";
	}
}
