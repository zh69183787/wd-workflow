package com.wonders.dataExchange.constants;

import com.wonders.dataExchange.model.common.vo.MessageBo;




public class DataExchangeMessage {
	
	private static final String error1 = CommonConstants.MESSAGE_ERROR_PROCESS;//流程级别错误
	private static final String error2 = CommonConstants.MESSAGE_ERROR_CHECK;//效验级别错误
	private static final String info1 = CommonConstants.MESSAGE_INFO_PROCESS;//流程信息
	
	
	public static final MessageBo FAIL_TO_FIND_FLOW = new MessageBo("该条流程信息不存在或已处理！","",error1);
	public static final MessageBo FAIL_TO_REVERT_BO = new MessageBo("XML转换失败！","",error1);
	public static final MessageBo CHECK_WRONG_REGISTER = new MessageBo("人员信息与当前登录人员不符！","",error1);
	public static final MessageBo FAIL_ERROR = new MessageBo("处理出错！","",error1);
	
	public static final MessageBo CHECK_SUCCESS = new MessageBo("效验成功！","",info1);
	public static final MessageBo CHECK_WRONG_PROCESS_INFO = new MessageBo("流程信息错误！","",error1);
	public static final MessageBo CHECK_IS_FINISH = new MessageBo("操作不存在或操作已完成！","",error1);

	/*
	 * 发起、修改
	 */
	public static final MessageBo CHECK_NO_PROJECT_INFO = new MessageBo("项目信息不能为空！","",error2);

}
