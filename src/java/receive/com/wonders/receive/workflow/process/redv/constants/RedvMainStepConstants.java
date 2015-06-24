/**
 * 
 */
package com.wonders.receive.workflow.process.redv.constants;

/** 
 * @ClassName: RedvMainConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:49:34 
 *  
 */
public class RedvMainStepConstants {
	/*
	 * 收文流程步骤名称
	 */
	
	public final static String STEPNAME_REGISTER="Begin";
	
	public final static String STEPNAME_LEADER_CHECK="呈报部门领导审核";
	
	public final static String STEPNAME_SIMULATE="拟办人";
	
	public final static String STEPNAME_MODIFY="呈报人修改";
	
	public final static String STEPNAME_RECORD="备案";
	
	
	/**
	 * 操作步骤名称组(Begin，文件审核及编号，登记人修改，备案)
	 */
	public final static String[] FLOW_OPERATE_STEP = {
		STEPNAME_REGISTER,
		STEPNAME_LEADER_CHECK,
		STEPNAME_SIMULATE,
		STEPNAME_MODIFY,
		STEPNAME_RECORD
	};
	
}
