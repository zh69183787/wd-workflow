/**
 * 
 */
package com.wonders.receive.workflow.process.sign.constants;

/** 
 * @ClassName: DeptSubConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:49:34 
 *  
 */
public class SignSubStepConstants {
	/*
	 * 部门内部子流程步骤名称
	 */
	
	public final static String STEPNAME_SECRETARY_DEAL="秘书";
	
	public final static String STEPNAME_LEADER_DEAL="领导";
	
	
	
	/**
	 * 操作步骤名称组(秘书，领导)
	 */
	public final static String[] FLOW_OPERATE_STEP = {
		STEPNAME_SECRETARY_DEAL,
		STEPNAME_LEADER_DEAL
	};
	
}
