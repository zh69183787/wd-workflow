/**
 * 
 */
package com.wonders.receive.workflow.process.redv.constants;

/** 
 * @ClassName: RedvMainConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午02:03:40 
 *  
 */
public class RedvMainConstants {


	/*
	 *	收呈批件 code 
	 * 
	 * */
	
	public static final String REDV_DICTIONARY = "chengpijian";
	
	public static final String REDV_PRIORITIES = "shouwen_priorities";
	
	public static final String REDV_PROCESS = "新收呈批件";
	
	public static final String REDV_DOC_LEVEL_CODE = "02";
	
	/*呈报部门领导审核*/
	
	/**
	 * 1、提交领导
	 */
	public static final String CHOICE_LEADER_SUBMIT_SIMULATE = "1";
	
	/**
	 * 2、返回修改
	 */
	public static final String CHOICE_LEADER_REGISTER_MODIFY = "2";
	
	/**
	 * 3、取消申报
	 */
	public static final String CHOICE_LEADER_CANCEL_REDV = "3";
	
	
	/*登记人修改*/
	/**
	 * 1、提交部门领导
	 */
	public static final String CHOICE_MODIFY_SUBMIT_LEADER = "1";
	
	/**
	 * 2、取消呈批件
	 */
	public static final String CHOICE_MODIFY_CANCEL_REDV = "2";
	
}
