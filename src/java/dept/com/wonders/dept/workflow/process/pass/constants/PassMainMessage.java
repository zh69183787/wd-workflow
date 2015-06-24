package com.wonders.dept.workflow.process.pass.constants;

import com.wonders.dept.workflow.constants.CommonConstants;
import com.wonders.dept.workflow.model.bo.MessageBo;


public class PassMainMessage {
	
	private static final String error1 = CommonConstants.MESSAGE_ERROR_PROCESS;//流程级别错误
	private static final String error2 = CommonConstants.MESSAGE_ERROR_CHECK;//效验级别错误
	private static final String info1 = CommonConstants.MESSAGE_INFO_PROCESS;//流程信息
	
	
	public static final MessageBo FAIL_TO_SIGN_ADD = new MessageBo("该文件已被处理，请重新刷新待办项检查！","",error1);
	public static final MessageBo FAIL_TO_WRONG_PERSON = new MessageBo("您登陆的用户不是该文件的签收人，请核查！","",error1);
	
	
	public static final MessageBo FAIL_TO_LAUNCH_PROCESS = new MessageBo("发起流程失败！","",error1);
	public static final MessageBo FAIL_TO_COMPLETE_STEP = new MessageBo("流程操作失败！","",error1);
	public static final MessageBo FAIL_TO_SAVE_DATA = new MessageBo("数据保存失败！","",error1);
	
	
	public static final MessageBo CHECK_SUCCESS = new MessageBo("效验成功！","",info1);
	public static final MessageBo CHECK_WRONG_PROCESS_INFO = new MessageBo("流程信息错误！","",error1);
	public static final MessageBo CHECK_IS_FINISH = new MessageBo("操作不存在或操作已完成！","",error1);

	/*
	 * 发起、修改
	 */
	public static final MessageBo CHECK_NO_TITLE = new MessageBo("传阅标题不能为空！","title",error2);
	public static final MessageBo CHECK_NO_OFFICER = new MessageBo("部门人员不能为空！","dealPersonNames",error2);
	public static final MessageBo CHECK_NO_SUGGEST = new MessageBo("意见不能为空！","suggest",error2);
	public static final MessageBo CHECK_SUGGES_LIMIT_400 = new MessageBo("意见请小于400字符！","suggest",error2);
	//
}
