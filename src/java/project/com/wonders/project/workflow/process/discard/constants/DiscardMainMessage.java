package com.wonders.project.workflow.process.discard.constants;

import com.wonders.project.workflow.constants.CommonConstants;
import com.wonders.project.workflow.model.bo.MessageBo;


public class DiscardMainMessage {
	
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
	public static final MessageBo CHECK_NO_PROJECT_INFO = new MessageBo("项目信息不能为空！","projectName",error2);
	public static final MessageBo CHECK_DUPLICATE_PROJECT = new MessageBo("该项目已有销项申请记录，不可重复申请！","projectName",error2);
	public static final MessageBo CHECK_NO_MONEY_SOURCE = new MessageBo("资金来源不能为空！","moneySource",error2);
	public static final MessageBo CHECK_NO_START_DATE = new MessageBo("开工日期不能为空！","startDate",error2);
	public static final MessageBo CHECK_NO_FINISH_DATE = new MessageBo("竣工日期不能为空！","finishDate",error2);
	public static final MessageBo CHECK_WRONG_DATE_COMPARE = new MessageBo("开个日期不能晚于竣工日期！","startDate",error2);
	public static final MessageBo CHECK_NO_MOBILE = new MessageBo("申请人联系电话不能为空！","operatorMobile",error2);
	public static final MessageBo CHECK_WRONG_PHONE = new MessageBo("申请人联系电话格式错误！","operatorMobile",error2);
	public static final MessageBo CHECK_NO_ATTACH = new MessageBo("附件不能为空！","attach",error2);
	public static final MessageBo CHECK_NO_SUGGEST = new MessageBo("意见不能为空！","suggest",error2);
	public static final MessageBo CHECK_NO_ASSETS = new MessageBo("请导入待审核资产！","",error2);
	public static final MessageBo CHECK_LIFE_EXTEND_INT = new MessageBo("需延长年限数应为非负整数！","",error2);
	public static final MessageBo CHECK_PRICE_SUM_EQUAL = new MessageBo("竣工决算价 不等于 待审核资产清单中入账原值之和！","",error2);
	public static final MessageBo CHECK_ASSET_CONTENT_LIMIT_500 = new MessageBo("资产履历信息请小于500字！","",error2);
	
	public static final MessageBo CHECK_SUGGES_LIMIT_400 = new MessageBo("意见请小于400字！","suggest",error2);

	public static final MessageBo CHECK_REMARK_LIMIT_1000 = new MessageBo("备注应小于1000字！","remark",error2);
	
	public static final MessageBo CHECK_NO_DEPT = new MessageBo("请选择会签部门！","depsName",error2);
	public static final MessageBo CHECK_NO_CHOOSE_PERSON = new MessageBo("请选择投资部经办人！","dealPersonNames",error2);
	public static final MessageBo CHECK_NO_CHOOSE_LEADER = new MessageBo("请选择投资部领导！","dealLeaderNames",error2);
	
}
