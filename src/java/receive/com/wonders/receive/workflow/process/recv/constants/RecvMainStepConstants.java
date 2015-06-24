/**
 * 
 */
package com.wonders.receive.workflow.process.recv.constants;

/** 
 * @ClassName: RecvMainConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:49:34 
 *  
 */
public class RecvMainStepConstants {
	/*
	 * 收文流程步骤名称
	 */
	
	public final static String STEPNAME_REGISTER="Begin";
	
	public final static String STEPNAME_GET_SERIALNO="文件审核及编号";
	
	public final static String STEPNAME_MODIFY="登记人修改";
	
	public final static String STEPNAME_RECORD="备案";
	
	
	/**
	 * 操作步骤名称组(Begin，文件审核及编号，登记人修改，备案)
	 */
	public final static String[] FLOW_OPERATE_STEP = {
		STEPNAME_REGISTER,
		STEPNAME_GET_SERIALNO,
		STEPNAME_MODIFY,
		STEPNAME_RECORD
	};
	
}
