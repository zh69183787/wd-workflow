
package com.wonders.dept.workflow.process.pass.constants;

/** 
 * @ClassName: PassMainConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午02:03:40 
 *  
 */
public class PassMainConstants {


	/*
	 *	合同后审流程 code 
	 * 
	 * */
	
	
	public static final String PASS_PROCESS = "部门内部审阅";
	
	/*发起人*/
	
	/**
	 * 1、结束传阅
	 */
	public static final String CHOICE_FINISH_FLOW = "1";
	
	/**
	 * 2、选择业务人员传阅，并直接结束
	 */
	public static final String CHOICE_SELECT_DEPT_PERSON_FINISH = "2";
	
	
	/**3
	 * 选择业务人员传阅 后 本人审核
	 * 
	 */
	public static final String CHOICE_SELECT_DEPT_PERSON_SELF_CHECK = "3";
}
