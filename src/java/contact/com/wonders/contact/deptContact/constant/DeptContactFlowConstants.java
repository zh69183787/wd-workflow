package com.wonders.contact.deptContact.constant;

import com.wonders.contact.deptSubProcess.constant.DeptSubProcessFlowConstants;

public class DeptContactFlowConstants {
	/**基本信息*/
	public static final String NEED_LINK =  "多级_是否关联";
	
	public static final String LINK_PROCESS_TYPE =  "多级_关联流程类型";
	public static final String LINK_PROCESS_KEY =  "多级_流程关联_KEY";
	
	
	public static final String TARGET =  "多级_目标节点";

	
	public static final String SERVERIP =  "多级_服务器地址";
	
	public static final String DEPTID =  "多级_发起部门ID";
	
	
	/**数量信息*/
	
	public static final String COUNT_SUBPROCESS =  "多级_数量_子流程";
	public static final String COUNT_LOWERPROCESS =  "多级_数量_下级流程";
	//private static final String COUNT_TOTAL =  "多级_数量_总流程";
	

	/**人员信息*/
	
	public static final String USER_BEGIN = "多级_处理人员_BEGIN";
	public static final String USER_APPLY = "多级_处理人员_申请";
	public static final String USER_SIGN = "多级_处理人员_签发";
	
	
	/**步骤信息 目标节点CODE*/
	
	public static final String STEP_BEGIN = "BEGIN";
	public static final String STEP_APPLY = "APPLY";
	public static final String STEP_SIGN = "SIGN";
	public static final String STEP_LOWER_SUB = "LOWER_SUB_PROCESS";
	public static final String STEP_RETURN_APPLYDEPT = "RETURN_APPLYDEPT";
	public static final String STEP_END = "END";
	
	/**步骤信息*/
	
	public static final String STEPNAME_BEGIN = "Begin";
	public static final String STEPNAME_APPLY = "申请";
	public static final String STEPNAME_SIGN = "部门内部签发";
	public static final String STEPNAME_LOWER = "下级流程";
	public static final String STEPNAME_END = "END";
	
	
	/**节点操作类型*/
	
	/**操作节点组(部门内部子流程节点)*/
	public final static String[] TYPE_OPERATE_STEP = DeptSubProcessFlowConstants.FLOW_OPERATE_STEP;
	
	/**操作&更新节点组（申请、签发）*/
	public final static String[] TYPE_OPERATE_UPDATE_STEP = {
		STEPNAME_APPLY,
		STEPNAME_SIGN
	};
}

