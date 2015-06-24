package com.wonders.dept.workflow.constants;

import com.wonders.util.PWSProperties;


public class CommonConstants {
	
	/**
	 * 工号前缀
	 */
	public static final String LOGINNAME_PREFIX = PWSProperties.getValueByKey("domain_name")+"/";;
	
	/**
	 * 工号、姓名 分隔符
	 */
	public static final String SPLIT_EXP = ",";
	
	

	/**
	 * 流程提示信息
	 */
	public static final String MESSAGE_INFO_PROCESS = "00";
	
	
	/**
	 * 流程错误信息
	 */
	public static final String MESSAGE_ERROR_PROCESS = "10";
	
	/**
	 * 流程效验信息
	 */
	public static final String MESSAGE_ERROR_CHECK = "11";
	
	/**
	 * 其他信息
	 */
	public static final String MESSAGE_ERROR_OTHER = "12";
	
	
}
