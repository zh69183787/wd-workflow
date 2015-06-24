/**
 * 
 */
package com.wonders.project.workflow.process.dept.constants;

/** 
 * @ClassName: DeptSubConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:49:34 
 *  
 */
public class DeptSubStepConstants {
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
