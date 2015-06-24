/**
 * 
 */
package com.wonders.project.workflow.process.discard.constants;

/** 
 * @ClassName: DiscardMainConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午02:03:40 
 *  
 */
public class DiscardMainConstants {


	/*
	 *	项目销项 code 
	 * 
	 * */
	public static final String PROJECT_DISCARD_PROCESS = "项目销项流程";
	
	
	/*返回修改*/
	/**
	 * 1、提交 部门领导
	 */
	public static final String CHOICE_MODIFY_SUBMIT_DEPT_LEADER = "1";
	
	/**
	 * 2、取消
	 */
	public static final String CHOICE_MODIFY_CANCEL = "2";
	
	
	/**
	 * 1、部门领导 发起部门 
	 */
	public static final String CHOICE_DEPT_LEADER_REGISTER_SUBMIT = "00";
	public static final String CHOICE_DEPT_LEADER_REGISTER_BACK = "01";
	public static final String CHOICE_DEPT_LEADER_REGISTER_CANCEL = "02";
	
	
	/*投资部收发员*/
	/**
	 * 1、提交部门会签
	 */
	public static final String CHOICE_PRETRIAL_SUMBIT_DEPT = "13";
	
	/**
	 * 2、提交经办人
	 */
	public static final String CHOICE_PRETRIAL_SUMBIT_OPERATOR = "10";
	/**
	 * 3、返回修改
	 */
	public static final String CHOICE_PRETRIAL_BACK = "11";	
	
	/*投资部领导办结*/
	/**
	 * 1、直接办结
	 */
	public static final String CHOICE_DEAL_FINISH_OVER = "30";
	
	/**
	 * 2、重新部门会签
	 */
	public static final String CHOICE_DEAL_FINISH_SUMBIT_DEPT = "33";
	
	/**
	 * 3、退回汇总人
	 */
	public static final String CHOICE_DEAL_FINISH_RETURN = "31";
	
	
	
}
