/**
 * 
 */
package com.wonders.receive.workflow.process.finish.constants;

/** 
 * @ClassName: FinishSubConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午02:03:40 
 *  
 */
public class FinishSubConstants {

	/*办结人选项*/
	
	/**
	 * 1、办结
	 */
	public static final String CHOICE_FINISH = "1";
	
	
	/**
	 * 2、重新拟办
	 */
	public static final String CHOICE_SIMULATE_AGAIN = "2";
	
	
	/**
	 * 3、领导部门并行处理
	 */
	public static final String CHOICE_DEPT_LEADER_CONCURRENCE = "3";
	
	
	
	/**
	 * 办结人 选项组（1、办结；2、重新拟办；）
	 */
	public static final String[] OPTIONS_FINISH = 
		{CHOICE_FINISH,CHOICE_SIMULATE_AGAIN,CHOICE_DEPT_LEADER_CONCURRENCE};
	
	
}
