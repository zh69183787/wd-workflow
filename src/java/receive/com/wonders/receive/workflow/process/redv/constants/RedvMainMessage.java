package com.wonders.receive.workflow.process.redv.constants;

import com.wonders.receive.workflow.constants.CommonConstants;
import com.wonders.receive.workflow.model.bo.MessageBo;


public class RedvMainMessage {
	
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
	public static final MessageBo CHECK_NO_REDV_TITLE = new MessageBo("文件标题不为空！","title",error2);
	public static final MessageBo CHECK_NO_REDV_DATE = new MessageBo("呈报日期不能为空","submitDate",error2);
	public static final MessageBo CHECK_NO_DEPT_LEADER = new MessageBo("部门领导不能为空！","nbOpinion",error2);
	public static final MessageBo CHECK_NO_SUGGEST = new MessageBo("意见不能为空！","suggest",error2);
	public static final MessageBo CHECK_SUGGES_LIMIT_400 = new MessageBo("意见请小于400字！","suggest",error2);
	public static final MessageBo CHECK_NO_DEPT_SERIAL = new MessageBo("你所在的部门的呈批件前缀在系统中尚未配置，请联系管理员添加相关编号前缀。！","zleader",error2);
	
	
	
	public static final MessageBo CHECK_KEYWORD_LIMIT_200 = new MessageBo("关键字应小于200字！","keyword",error2);
	public static final MessageBo CHECK_TITLE_LIMIT_200 = new MessageBo("文件标题应小于200字！","title",error2);
	public static final MessageBo CHECK_CONTENT_LIMIT_1000 = new MessageBo("主要内容应小于1000字！","content",error2);
	
}
