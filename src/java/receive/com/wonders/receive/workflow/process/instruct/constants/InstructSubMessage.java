package com.wonders.receive.workflow.process.instruct.constants;

import com.wonders.receive.workflow.constants.CommonConstants;
import com.wonders.receive.workflow.model.bo.MessageBo;


public class InstructSubMessage {
	
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
	public static final MessageBo CHECK_NO_RECV_TITLE = new MessageBo("文件标题不为空！","title",error2);
	public static final MessageBo CHECK_NO_FILE_DATE = new MessageBo("文件日期不能为空","fileDate",error2);
	public static final MessageBo CHECK_NO_FILE_UNIT = new MessageBo("来文单位不能为空！","swUnit",error2);
	public static final MessageBo CHECK_NO_FILE_NUM = new MessageBo("文件字号不能为空！","filezh",error2);
	public static final MessageBo CHECK_NO_RECV_SERIAL = new MessageBo("收文编号不能为空！","swSerial",error2);
	public static final MessageBo CHECK_NO_RECV_DATE = new MessageBo("收文日期不能为空！","swDate",error2);
	public static final MessageBo CHECK_NO_SUGGEST = new MessageBo("意见不能为空！","suggest",error2);
	public static final MessageBo CHECK_SUGGES_LIMIT_400 = new MessageBo("意见请小于400字！","suggest",error2);

	public static final MessageBo CHECK_KEYWORD_LIMIT_50 = new MessageBo("关键字应小于50字！","title",error2);
	public static final MessageBo CHECK_TITLE_LIMIT_200 = new MessageBo("文件标题应小于200字！","title",error2);
	public static final MessageBo CHECK_REMARK_LIMIT_1000 = new MessageBo("备注应小于1000字！","remark",error2);
	public static final MessageBo CHECK_RECV_NUM_NUMBER = new MessageBo("份数应为1-100之内数字！","num",error2);
	
}
