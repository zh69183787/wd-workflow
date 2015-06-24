/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.constants;

/** 
 * @ClassName: SimulateSubConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午02:03:40 
 *  
 */
public class SimulateSubConstants {

	/*是否提交办公室主任*/
	
	public static final String SELF_DEAL = "";
	
	public static final String SUMBIT_LEADER = "1";
	/*拟办人选项*/
	
	/**
	 * 1、先请领导批示
	 */
	public static final String CHOICE_LEADER_INSTRUCT = "1";
	
	/**
	 * 2、请业务部门提出意见
	 */
	public static final String CHOICE_DEPT_SUGGEST = "2";
	
	/**
	 * 3、直接拟办
	 */
	public static final String CHOICE_SIMULATE_DIRECT = "3";
	
	
	/**
	 * 4、领导部门并行处理
	 */
	public static final String CHOICE_DEPT_LEADER_CONCURRENCE = "4";
	
	
	/**
	 * 5、提交办公室主任审核
	 */
	public static final String CHOICE_OFFICE_LEADER = "5";
	
	
	
	/**
	 * 拟办人 选项组（1、先请领导批示；2、请业务部门提出意见；）
	 */
	public static final String[] OPTIONS_SIMULATE = 
		{CHOICE_LEADER_INSTRUCT,CHOICE_DEPT_SUGGEST,CHOICE_SIMULATE_DIRECT,
		CHOICE_DEPT_LEADER_CONCURRENCE,CHOICE_OFFICE_LEADER};
	
	
}
