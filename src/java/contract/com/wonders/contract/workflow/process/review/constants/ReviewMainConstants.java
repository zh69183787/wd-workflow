
package com.wonders.contract.workflow.process.review.constants;

/** 
 * @ClassName: ReviewMainConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午02:03:40 
 *  
 */
public class ReviewMainConstants {


	/*
	 *	合同后审流程 code 
	 * 
	 * */
	
	
	public static final String REVIEW_PROCESS = "合同后审流程";
	
	/*申报部门领导*/
	
	/**
	 * 1、提交合约部初审
	 */
	public static final String CHOICE_DEPT_LEADER_SUBMIT_CONTRACT = "1";
	
	/**
	 * 2、返回修改
	 */
	public static final String CHOICE_DEPT_LEADER_REGISTER_MODIFY = "2";
	
	
	/*返回修改*/
	/**
	 * 1、提交 申报部门领导
	 */
	public static final String CHOICE_MODIFY_SUBMIT_DEPT_LEADER = "1";
	
	/**
	 * 2、取消合同
	 */
	public static final String CHOICE_MODIFY_CANCEL_REVIEW = "2";
	
	/*合约部初审*/
	/**
	 * 1 选择合约部人员
	 */
	public static final String CHOICE_PRE_TRIAL_CHOOSE_PERSON = "1";
	/**
	 * 2  直接提交合约部拟办
	 */
	public static final String CHOICE_PRE_TRIAL_SUBMIT_SIMULATE = "2";
	/**
	 * 3  流程结束，直接入库
	 */
	public static final String CHOICE_END_PROCESS = "3";
	
	/*
	 * 4 返回修改
	 */
	
	public static final String CHOICE_PRE_TRIAL_BACK_MODIFY = "4";
	
	
	/*合约部经办人*/
	/*
	 * 提出意见
	 */
	
	/*合约部拟办*/
	/*
	 * 1 选择 领导 部门
	 */
	public static final String CHOICE_SIMULATE_CHOOSE_LEADER_DEPT = "1";
	
	/*
	 * 2 直接完成
	 */
	public static final String CHOICE_SIMULATE_END_PROCESS = "2";
	
	/*
	 * 3 返回修改
	 */
	
	public static final String CHOICE_SIMULATE_BACK_MODIFY = "3";
	
	
	
	/*会签部门*/
	
	
	/*集团领导*/
	
	/*合约部办结*/
	/*
	 * 1  完成办结
	 */
	public static final String CHOICE_FINISH_END_PROCESS = "1";
	
	/*
	 * 2 重新拟办选人
	 */
	public static final String CHOICE_FINISH_RE_SIMULATE = "2";
	
	
}
