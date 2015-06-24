/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.constants;

/** 
 * @ClassName: SimulateSubConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:49:34 
 *  
 */
public class SimulateSubStepConstants {
	/*
	 * 拟办子流程步骤名称
	 */
	
	public final static String STEPNAME_SIMULATE="拟办人";
	
	public final static String STEPNAME_SIMULATE_SUGGEST_COLLECT="拟办意见汇总";
	
	public final static String STEPNAME_SIMULATE_LEADER="拟办领导批示";
	
	public final static String STEPNAME_SIMULATE_SUGGEST="拟办建议";
	
	public final static String STEPNAME_SIMULATE_FINSIH="拟办办结";
	
	
	/**
	 * 操作步骤名称组(拟办人、拟办意见汇总、拟办领导批示、拟办建议)
	 */
	public final static String[] FLOW_OPERATE_STEP = {
		STEPNAME_SIMULATE,
		STEPNAME_SIMULATE_SUGGEST_COLLECT,
		STEPNAME_SIMULATE_LEADER,
		STEPNAME_SIMULATE_SUGGEST
	};
	
}
