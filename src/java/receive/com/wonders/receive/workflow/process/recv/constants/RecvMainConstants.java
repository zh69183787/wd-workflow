/**
 * 
 */
package com.wonders.receive.workflow.process.recv.constants;

/** 
 * @ClassName: RecvMainConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午02:03:40 
 *  
 */
public class RecvMainConstants {


	/*
	 *	收文流程 code 
	 * 
	 * */
	
	
	public static final String RECV_PRIORITIES = "shouwen_priorities";
	
	public static final String RECV_PROCESS = "新收文流程";
	
	/*文件审核及编号*/
	
	/**
	 * 1、提交拟办
	 */
	public static final String CHOICE_SERIALNO_SUBMIT_SIMULATE = "1";
	
	/**
	 * 2、返回修改
	 */
	public static final String CHOICE_SERIALNO_REGISTER_MODIFY = "2";
	
	
	/*登记人修改*/
	/**
	 * 1、提交 文件审核及编号
	 */
	public static final String CHOICE_MODIFY_SUBMIT_SERIALNO = "1";
	
	/**
	 * 2、取消收文
	 */
	public static final String CHOICE_MODIFY_CANCEL_RECV = "2";
	
}
