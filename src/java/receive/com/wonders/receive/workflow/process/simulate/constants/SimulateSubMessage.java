package com.wonders.receive.workflow.process.simulate.constants;

import com.wonders.receive.workflow.constants.CommonConstants;
import com.wonders.receive.workflow.model.bo.MessageBo;


public class SimulateSubMessage {
	
	private static final String error1 = CommonConstants.MESSAGE_ERROR_PROCESS;//流程级别错误
	private static final String error2 = CommonConstants.MESSAGE_ERROR_CHECK;//效验级别错误
	private static final String info1 = CommonConstants.MESSAGE_INFO_PROCESS;//流程信息
	
	
	public static final MessageBo FAIL_TO_LAUNCH_PROCESS = new MessageBo("发起流程失败！","",error1);
	public static final MessageBo FAIL_TO_COMPLETE_STEP = new MessageBo("流程操作失败！","",error1);
	public static final MessageBo FAIL_TO_SAVE_DATA = new MessageBo("数据保存失败！","",error1);
	
	
	public static final MessageBo CHECK_SUCCESS = new MessageBo("效验成功！","",info1);
	public static final MessageBo CHECK_WRONG_PROCESS_INFO = new MessageBo("流程信息错误！","",error1);
	public static final MessageBo CHECK_IS_FINISH = new MessageBo("操作不存在或操作已完成！","",error1);

	/*
	 * 发起、修改
	 */
	public static final MessageBo CHECK_NO_INSTRUCT_LEADER = new MessageBo("批示领导不能为空！","insSignLdsName",error2);
	public static final MessageBo CHECK_NO_SUGGEST_DEPT = new MessageBo("拟办建议部门不能为空！","sugDepsName",error2);
	public static final MessageBo CHECK_NO_URGE_SIMULATE = new MessageBo("请同时选择部门和会签领导,如果只选其中之一请选择直接拟办！","depsName",error2);
	public static final MessageBo CHECK_NO_SUGGEST = new MessageBo("意见不能为空！","suggest",error2);
	public static final MessageBo CHECK_SUGGES_LIMIT_400 = new MessageBo("意见请小于400字！","suggest",error2);
	

}
