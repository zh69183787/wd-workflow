package com.wonders.contact.deptSubProcess.constant;

public class DeptSubProcessConstants {

	
	/*部门接收人工作分发选项*/
	
	/**
	 * 1、业务转发（不进行具体业务处理）
	 */
	public static final String CHOICE_DISPATCHER_SEND = "1";
	
	/**
	 * 2、业务处理
	 */
	public static final String CHOICE_DISPATCHER_DEAL = "2";
	
	/**
	 * 2、业务处理_选项1、本人没有意见
	 */
	public static final String CHOICE_DISPATCHER_DEAL_NO_SUGGEST = "1";
	
	/**
	 * 2、业务处理_选项2、本人有新意见
	 */
	public static final String CHOICE_DISPATCHER_DEAL_HAS_SUGGEST = "2";
	
	
	/**
	 * 部门接收人工作分发 选项组（1、业务转发，2、业务处理）
	 */
	public static final String[] OPTIONS_DISPATCHER = {CHOICE_DISPATCHER_SEND,CHOICE_DISPATCHER_DEAL};
	
	/**
	 * 部门接收人工作分发 3、业务处理 选项组（1、本人没有意见，2、本人有新意见）
	 */
	public static final String[] OPTIONS_DISPATCHER_DEAL = {CHOICE_DISPATCHER_DEAL_NO_SUGGEST,CHOICE_DISPATCHER_DEAL_HAS_SUGGEST};
	
	
	/*部门人员处理选项*/
	
	/**
	 * 1、本人没有意见
	 */
	public static final String CHOICE_DEAL_NO_SUGGEST = "1";
	
	/**
	 * 2、本人有意见
	 */
	public static final String CHOICE_DEAL_HAS_SUGGEST = "2";
	
	/**
	 * 3、非本人处理业务
	 */
	public static final String CHOICE_DEAL_NOT_MY_BUSINESS = "3";
	
	/**
	 * 部门人员处理 选项组（1、本人没有意见，2、本人有意见，3、非本人处理业务）
	 */
	public static final String[] OPTIONS_DEAL = {CHOICE_DEAL_NO_SUGGEST,CHOICE_DEAL_HAS_SUGGEST,CHOICE_DEAL_NOT_MY_BUSINESS};
	
	
	/*部门领导审核选项*/
	
	/**
	 * 1、审核通过(形成部门意见)
	 */
	public static final String CHOICE_LEADER_PASS = "1";
	
	/**
	 * 2、审核不通过,有新意见(形成部门意见)
	 */
	public static final String CHOICE_LEADER_FAILED = "2";
	
	/**
	 * 3、还需要本部门业务人员继续处理
	 */
	public static final String CHOICE_LEADER_CONTINUE = "3";
	
	
	/**
	 * 3、还需要本部门业务人员继续处理_选项1、须本部门其他人员继续处理,转业务经办人 
	 */
	public static final String CHOICE_LEADER_CONTINUE_DEAL = "1";
	
	/**
	 * 3、还需要本部门业务人员继续处理_选项2、直接选择处理人员
	 */
	public static final String CHOICE_LEADER_CONTINUE_CHOICE_PERSON = "2";
	
	
	
	
	/**
	 * 部门领导审核 选项组（1、审核通过，2、审核不通过,有新意见，3、还需要本部门业务人员继续处理）
	 */
	public static final String[] OPTIONS_LEADER = {CHOICE_LEADER_PASS,CHOICE_LEADER_FAILED,CHOICE_LEADER_CONTINUE};
	
	/**
	 * 部门领导审核 3、还需要本部门业务人员继续处理 选项组（1、须本部门其他人员继续处理,转业务经办人，2、直接选择处理人员）
	 */
	public static final String[] OPTIONS_LEADER_CONTINUE = {CHOICE_LEADER_CONTINUE_DEAL,CHOICE_LEADER_CONTINUE_CHOICE_PERSON};
	
}
