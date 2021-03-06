/**
 * 
 */
package com.wonders.receive.workflow.process.instruct.constants;

/** 
 * @ClassName: DeptSubConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午02:03:40 
 *  
 */
public class InstructSubConstants {
	/**/
	public static final String REMARK_BACK_OFFICE = "领导暂时不能处理，退回办公室。";
	
	/*代领导处理*/
	
	public static final String SELF_DEAL = "0";
	
	public static final String INSTEAD_DEAL = "1";

	/*领导秘书*/
	
	/**
	 * 1、已阅
	 */
	public static final String CHOICE_SECRETARY_READ = "1";
	
	
	/**
	 * 2、其他意见
	 */
	public static final String CHOICE_SECRETARY_OTHER_SUGGEST = "2";
	
	/**
	 * 3、领导暂时不能处理 退回办公室
	 */
	public static final String CHOICE_SECRETARY_BACK_OFFICE = "3";
	
		
	
	/*领导处理选项*/
	
	/**
	 * 1、已阅
	 */
	public static final String CHOICE_LEADER_READ = "1";
	

	/**
	 * 2、其他意见
	 */
	public static final String CHOICE_LEADER_OTHER_SUGGEST = "2";
	
	
	/**
	 * 3、退回秘书
	 */
	public static final String CHOICE_LEADER_BACK_SECRETARY = "3";
	
}
