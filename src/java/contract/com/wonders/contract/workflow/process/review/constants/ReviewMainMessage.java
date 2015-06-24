package com.wonders.contract.workflow.process.review.constants;

import com.wonders.contract.workflow.constants.CommonConstants;
import com.wonders.contract.workflow.model.bo.MessageBo;


public class ReviewMainMessage {
	
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
	public static final MessageBo CHECK_NO_CONTRACT_IDENTIFIER = new MessageBo("合同编号不能为空！","contractIdentifier",error2);
	public static final MessageBo CHECK_NO_CONTRACT_NUM = new MessageBo("自有编号不能为空！","contractSelfNum",error2);
	public static final MessageBo CHECK_DUPLICATE_CONTRACT_NUM = new MessageBo("自有编号不能重复！","contractSelfNum",error2);
	
	public static final MessageBo CHECK_NO_CONTRACT_NAME = new MessageBo("合同名称不能为空！","contractName",error2);
	public static final MessageBo CHECK_NO_CONTRACT_MONEY = new MessageBo("合同金额不能为空！","contractMoney",error2);
	public static final MessageBo CHECK_ERROR_CONTRACT_MONEY = new MessageBo("合同金额请输入数字！","contractMoney",error2);
	public static final MessageBo CHECK_NO_CONTRACT_MONEY_TYPE = new MessageBo("合同金额分类不能为空！","contractMoneyType",error2);
	public static final MessageBo CHECK_NO_CONTRACT_BUDGET = new MessageBo("合同预算不能为空！","contractBudget",error2);
	public static final MessageBo CHECK_ERROR_CONTRACT_BUDGET = new MessageBo("合同预算请输入数字！","contractBudget",error2);
	public static final MessageBo CHECK_NO_PURCHASE_TYPE = new MessageBo("采购方式不能为空！","purchaseType",error2);
	public static final MessageBo CHECK_NO_CONTRACT_TYPE1 = new MessageBo("合同分类不能为空！","contractType1",error2);
	public static final MessageBo CHECK_NO_CONTRACT_TYPE2 = new MessageBo("合同分类不能为空！","contractType2",error2);
	public static final MessageBo CHECK_NO_CONTRACT_GROUP = new MessageBo("合同分组不能为空！","contractGroup",error2);
	
	public static final MessageBo CHECK_NO_PROJECT_CHARGE = new MessageBo("项目公司负责人不能为空！","projectCharge",error2);
	public static final MessageBo CHECK_NO_OPPOSITE_COMPANY = new MessageBo("对方公司不能为空！","oppositeCompany",error2);
	public static final MessageBo CHECK_NO_DEAL_PERSON = new MessageBo("经办人不能为空！","dealPerson",error2);
	public static final MessageBo CHECK_NO_REG_PERSON = new MessageBo("登记人不能为空！","regPerson",error2);
	public static final MessageBo CHECK_NO_PASS_TIME = new MessageBo("审批通过时间不能为空！","passTime",error2);
	public static final MessageBo CHECK_NO_SIGN_TIME = new MessageBo("签约时间不能为空！","signTime",error2);
	public static final MessageBo CHECK_NO_EXEC_START = new MessageBo("履行期限不能为空！","execPeriodStart",error2);
	public static final MessageBo CHECK_NO_EXEC_END = new MessageBo("履行期限不能为空！","execPeriodEnd",error2);
	
	public static final MessageBo CHECK_NO_ATTACH = new MessageBo("相关附件不能为空！","attach",error2);
	public static final MessageBo CHECK_NO_CHOOSE_PERSON = new MessageBo("请选择合约部经办人！","dealPersonNames",error2);
	public static final MessageBo CHECK_NO_CHOOSE_LEADER_DEPT = new MessageBo("请选择会签部门或集团领导，不能同时为空！","depsName",error2);
	public static final MessageBo CHECK_NO_SUGGEST = new MessageBo("意见不能为空！","suggest",error2);
	public static final MessageBo CHECK_SUGGES_LIMIT_400 = new MessageBo("意见请小于400字符！","suggest",error2);
	public static final MessageBo CHECK_STRUCTURED_SUGGEST = new MessageBo("请选择所有结构化意见！","suggest",error2);


}
