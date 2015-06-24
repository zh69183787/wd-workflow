/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.constants;

/** 
 * @ClassName: DcpRecvMainConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:49:34 
 *  
 */
public class DcpRecvMainStepConstants {
	/*
	 * 收文流程步骤名称
	 */
	
	public final static String STEPNAME_REGISTER="Begin";
	
	public final static String STEPNAME_DEPT_LEADER="部门领导";
	
	public final static String STEPNAME_COM_LEADER_1="集团领导（并）";
	
	public final static String STEPNAME_DISCIPLINE_COMMITTEE_1="纪委委员（并）";
	
	public final static String STEPNAME_COM_LEADER_2="集团领导（串）";
	
	public final static String STEPNAME_DISCIPLINE_COMMITTEE_2="纪委委员（串）";
	
	public final static String STEPNAME_RETURN_MODIFY="返回修改";
	
	public final static String STEPNAME_SUMMARY_PERSON="汇总人";
	
	public final static String STEPNAME_DEAL_FINISH="办结";

	
	
	/**
	 * 操作步骤名称组(Begin，文件审核及编号，登记人修改，备案)
	 */
	public final static String[] FLOW_OPERATE_STEP = {
		STEPNAME_REGISTER,
		STEPNAME_DEPT_LEADER,
		STEPNAME_COM_LEADER_1,
		STEPNAME_DISCIPLINE_COMMITTEE_1,
		STEPNAME_COM_LEADER_2,
		STEPNAME_DISCIPLINE_COMMITTEE_2,
		STEPNAME_RETURN_MODIFY,
		STEPNAME_SUMMARY_PERSON,
		STEPNAME_DEAL_FINISH
	};
	
}
