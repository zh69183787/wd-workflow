/**
 * 
 */
package com.wonders.receive.workflow.process.instruct.constants;

/** 
 * @ClassName: DeptSubConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:49:34 
 *  
 */
public class InstructSubStepConstants {
	/*
	 * 部门内部子流程步骤名称
	 */
	
	public final static String STEPNAME_SECRETARY_DEAL="批示秘书";
	
	public final static String STEPNAME_LEADER_DEAL="批示领导";
	
	
	
	/**
	 * 操作步骤名称组(批示秘书，批示领导)
	 */
	public final static String[] FLOW_OPERATE_STEP = {
		STEPNAME_SECRETARY_DEAL,
		STEPNAME_LEADER_DEAL
	};
	
}
