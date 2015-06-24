package com.wonders.contact.deptContact.constant;

import com.wonders.contact.constant.CommonConstants;
import com.wonders.contact.model.bo.MessageBo;

public class DeptContactMessage {
	
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
	public static final MessageBo CHECK_NO_MAINUNIT = new MessageBo("主送部门不能为空！","mainUnit",error2);
	public static final MessageBo CHECK_NO_THEME = new MessageBo("主题不能为空！","theme",error2);
	public static final MessageBo CHECK_NO_CONTENT = new MessageBo("联系内容不能为空！","content",error2);
	
	public static final MessageBo CHECK_CONTENT_LIMIT_1000 = new MessageBo("联系内容应小于1000字！","content",error2);
	
	public static final MessageBo CHECK_NO_CONTACTDATE = new MessageBo("联系日期不能为空！","contactDate",error2);
	public static final MessageBo CHECK_WRONG_CONTACTDATE = new MessageBo("联系日期格式错误！","contactDate",error2);
	
	public static final MessageBo CHECK_NO_REPLYDATE = new MessageBo("要求回复日期不能为空！","replyDate",error2);
	public static final MessageBo CHECK_WRONG_REPLYDATE = new MessageBo("要求回复日期格式错误！","replyDate",error2);
	
	public static final MessageBo CHECK_WRONG_DATE_COMPARE = new MessageBo("要求回复日期应晚于联系日期！","replyDate",error2);
	
	/*
	 * 申请
	 */
	public static final MessageBo APPLY_NO_CHOICE = new MessageBo("请选择申请结果！","choice",error2);
	
	/*
	 * 签发
	 */
	public static final MessageBo SIGN_NO_LEADER = new MessageBo("签发领导不能为空！","SIGN_LEADER",error2);
	
	public static final MessageBo SIGN_NO_CHOICE = new MessageBo("请选择签发结果！","choice",error2);

	


	/*
	public static final String FAIL_TO_LAUNCH_PROCESS = "发起流程失败！";
	
	public static final String FAIL_TO_COMPLETE_STEP = "流程操作失败！";
	
	public static final String FAIL_TO_SAVE_DATA = "数据保存失败！";
	
	
	public static final String CHECK_SUCCESS = "效验成功！";
	
	public static final String CHECK_WRONG_PROCESS_INFO = "流程信息错误！";
	
	
	public static final String CHECK_NO_MAINUNIT = "主送部门不能为空！";
	
	 //public static final String CHECK_NO_COPYUNIT = "抄送部门不能为空！";
	
	public static final String CHECK_NO_THEME = "主题不能为空！";
	
	public static final String CHECK_NO_CONTENT = "联系内容不能为空！";
	
	public static final String CHECK_NO_CONTACTDATE = "联系日期不能为空！";
	public static final String CHECK_WRONG_CONTACTDATE = "联系日期格式错误！";
	
	public static final String CHECK_NO_REPLYDATE = "要求回复日期不能为空！";
	public static final String CHECK_WRONG_REPLYDATE = "要求回复日期格式错误！";
	
	public static final String CHECK_WRONG_DATE_COMPARE = "要求回复日期应晚于联系日期！";
	
	public static final String CHECK_NO_SIGN_LEADER = "签发领导不能为空！";
	
	*/
}
