package com.wonders.contact.deptSubProcess.constant;

import com.wonders.contact.constant.CommonConstants;
import com.wonders.contact.model.bo.MessageBo;

/**部门内部子流程 提示信息
 * @author XFZ
 *
 */
public class DeptSubProcessMessage {
	
	private static final String error1 = CommonConstants.MESSAGE_ERROR_PROCESS;//流程级别错误
	private static final String error2 = CommonConstants.MESSAGE_ERROR_CHECK;//效验级别错误
	//private static final String info1 = CommonConstants.MESSAGE_INFO_PROCESS;//流程信息
	
	public static final MessageBo FAIL_TO_COMPLETE_STEP = new MessageBo("流程操作失败！","",error1);
	
	public static final MessageBo CHECK_IS_FINISH = new MessageBo("操作不存在或操作已完成！","",error1);
	
	
	/*通用*/
	
	/**
	 * 没有选择领导
	 */
	public static final MessageBo COMMON_NO_LEADER = new MessageBo("请选择领导！","dealLeaderNames",error2);
	
	/**
	 * 没有输入意见
	 */
	public static final MessageBo COMMON_NO_SUGGEST = new MessageBo("请输入意见！","suggest",error2);
	
	
	
	/*部门接收人工作分发*/
	
	/**
	 * 没有选择申请结果
	 */
	public static final MessageBo DISPATCHER_NO_CHOICE = new MessageBo("请选择申请结果！","choice",error2);
	
	/**
	 * 没有选择（业务处理）结果
	 */
	public static final MessageBo DISPATCHER_NO_CHOICE2 = new MessageBo("请选择（业务处理）结果！","choice2",error2);

	
	
	/*部门人员处理*/
	
	/**
	 * 没有选择处理结果
	 */
	public static final MessageBo DEAL_NO_CHOICE = new MessageBo("请选择处理结果！","choice",error2);
	
	
	
	/*部门领导审核*/
	
	/**
	 * 没有选择审核结果
	 */
	public static final MessageBo LEADER_NO_CHOICE = new MessageBo("请选择审核结果！","choice",error2);
	
	/**
	 * 没有选择（本部门业务人员继续处理 ）结果
	 */
	public static final MessageBo LEADER_NO_CHOICE2 = new MessageBo("请选择（本部门业务人员继续处理 ）结果！","choice2",error2);
	
	/**
	 * 处理人员和领导同时为空
	 */
	public static final MessageBo LEADER_NO_PERSON_LEADER = new MessageBo("领导和处理人员不能同时为空！","",error2);
	
	
	
	
}
