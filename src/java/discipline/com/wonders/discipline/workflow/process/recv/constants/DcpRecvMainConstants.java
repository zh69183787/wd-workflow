/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.constants;

/** 
 * @ClassName: DcpRecvMainConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午02:03:40 
 *  
 */
public class DcpRecvMainConstants {


	/*
	 *	收文流程 code 
	 * 
	 * */
	
	
	public static final String DISCIPLINE_RECV_PRIORITIES = "shouwen_priorities";
	
	public static final String DISCIPLINE_RECV_PROCESS = "纪委收文流程";
	
	/*部门领导*/
	
	/**
	 * 1、集团领导 委员并行
	 */
	public static final String CHOICE_DEPT_LEADER_SUBMIT_ONE = "1";
	
	/**
	 * 2、集团领导 委员串行
	 */
	public static final String CHOICE_DEPT_LEADER_SUBMIT_TWO = "2";
	
	/**
	 * 3、返回修改
	 */
	public static final String CHOICE_DEPT_LEADER_BACK = "3";
	
	
	
	/*返回修改*/
	/**
	 * 1、提交 部门领导
	 */
	public static final String CHOICE_MODIFY_SUBMIT_DEPT_LEADER = "1";
	
	/**
	 * 2、取消
	 */
	public static final String CHOICE_MODIFY_CANCEL_RECV = "4";
	
	
	/*集团领导  纪委委员*/
	/**
	 * 1、已阅
	 */
	public static final String CHOICE_DEAL_PERSON_READ = "1";
	
	
	/*汇总人*/
	/**
	 * 1、部门处理
	 */
	public static final String CHOICE_SUMMARY_PERSON_SUMBIT_DEPT = "1";
	
	/**
	 * 2、提交办结人
	 */
	public static final String CHOICE_SUMMARY_PERSON_SUMBIT_FINISH = "2";
	
	
	/*办结*/
	/**
	 * 1、直接办结
	 */
	public static final String CHOICE_DEAL_FINISH_OVER = "1";
	
	
	/**
	 * 2、部门处理
	 */
	public static final String CHOICE_DEAL_FINISH_SUMBIT_DEPT = "2";
	
	/**
	 * 3、退回汇总人
	 */
	public static final String CHOICE_DEAL_FINISH_RETURN_SUMMARY_PERSON = "3";
	
	
	
}
