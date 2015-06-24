/**
 * 
 */
package com.wonders.project.workflow.process.discard.constants;

/** 
 * @ClassName: DiscardMainConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:49:34 
 *  
 */
public class DiscardMainStepConstants {
	/*
	 * 收文流程步骤名称
	 */
	
	public final static String STEPNAME_REGISTER="Begin";
	
	public final static String STEPNAME_DEPT_LEADER="发起部门领导";
	
	public final static String STEPNAME_TH_RECV="投资部收发员";
	
	public final static String STEPNAME_TH_OPERATOR="投资部经办人";
	
	public final static String STEPNAME_COM_LEADER_1="集团领导（并）";
	
	public final static String STEPNAME_COM_LEADER_2="集团领导（串）";
	
	public final static String STEPNAME_RETURN_MODIFY="返回修改";
	
	public final static String STEPNAME_SUMMARY_PERSON="汇总人";
	
	public final static String STEPNAME_DEAL_FINISH="投资部领导";

	
	
	/**
	 * 操作步骤名称组(Begin，文件审核及编号，登记人修改，备案)
	 */
	public final static String[] FLOW_OPERATE_STEP = {
		STEPNAME_REGISTER,
		STEPNAME_DEPT_LEADER,
		STEPNAME_COM_LEADER_1,
		STEPNAME_COM_LEADER_2,
		STEPNAME_RETURN_MODIFY,
		STEPNAME_SUMMARY_PERSON,
		STEPNAME_DEAL_FINISH
	};
	
}
