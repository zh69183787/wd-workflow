package com.wonders.contact.deptSubProcess.constant;

public class DeptSubProcessFlowConstants {
	/*
	 * 部门内部子流程步骤名称
	 */
	
	public final static String STEPNAME_DISPATCHER="部门接受人工作分发";
	
	public final static String STEPNAME_DEAL="部门业务人员处理";
	
	public final static String STEPNAME_LEADER="部门领导审核";
	
	
	/**
	 * 操作步骤名称组(部门接受人工作分发、部门业务人员处理、部门领导审核)
	 */
	public final static String[] FLOW_OPERATE_STEP = {
		STEPNAME_DISPATCHER,
		STEPNAME_DEAL,
		STEPNAME_LEADER
	};
	
}
