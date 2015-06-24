package com.wonders.contact.deptContact.constant;

/**多级工作联系单 常量
 * @author XFZ
 *
 */
public class DeptContactConstants {
	/**流程名称*/
	public static final String PROCESSNAME = "多级工作联系单";
	
	/**子流程名称*/
	public static final String SUBPROCESSNAME = "部门内部子流程";
	
	//TODO 测试用
	/**
	 * 应用IP筛选前缀
	 */
	public static final String IP_PREFIX = "10.1";
	/**
	 * 端口
	 */
	public static final String IP_PORT = "IP_PORT";
	/**
	 * 上下文
	 */
	public static final String IP_CONTEXT_PATH = "IP_CONTEXT_PATH";
	
	/**部门分隔符*/
	public static final String DEPT_SPLIT_STR = ",";
	
	
	/**Params map key:flowType*/
	public static final String PARAMS_FLOW_TYPE = "FLOW_TYPE";
	
	/**Params map key:引用联系单ID*/
	public static final String PARAMS_KEY_REF_ID = "ref_id_zone";
	
	/**Params map key:签发领导*/
	public static final String PARAMS_KEY_SIGN_LEADER = "SIGN_LEADER";
	
	
	
	
	/**TreeModel params key：是否配置下级流程*/
	public static final String TREE_PARAMS_IS_LOWER_DEPT = "PARAM_KEY$IS_LOWER_DEPT";
	
	/**TreeModel params key：所关联的顶层节点ID*/
	public static final String TREE_PARAMS_TOP_NODE_ID = "PARAM_KEY$TOP_NODE_ID";
	
	/**TreeModel params key：所关联的顶层节点ID*/
	public static final String TREE_PARAMS_INCLUDE_NODE = "PARAM_KEY$INLUCE_NODE";
	
	
	
	
	//流程stauts
	/**主流程status值*/
	public static final int STATUS_MAIN = 0;
	
	/**主流程status值(String)*/
	public static final String STATUS_MAIN_STR = String.valueOf(STATUS_MAIN);
	
	/**子流程status值*/
	public static final int STATUS_SUB = 1;
	
	/**子流程status值(String)*/
	public static final String STATUS_SUB_STR = String.valueOf(STATUS_SUB);
	
	/**下级流程status值*/
	public static final int STATUS_LOWER = 2;
	
	/**下级流程status值(String)*/
	public static final String STATUS_LOWER_STR = String.valueOf(STATUS_LOWER);
	
	/**主流程(返回发起部门子流程)status值*/
	public static final int STATUS_MAIN_BACKSUB = 3;
	
	/**主流程(返回发起部门子流程)status值(String)*/
	public static final String STATUS_MAIN_BACKSUB_STR = String.valueOf(STATUS_MAIN_BACKSUB);
	
	
	
	
	//申请节点选项
	/**1、部门领导签发*/
	public static final String CHOICE_APPLY_TO_SIGN = "1";
	
	/**2、取消多级工作联系单*/
	public static final String CHOICE_APPLY_TO_CANCEL = "2";
	
	/**申请节点选项组（主流程）（1、部门领导签发，2、取消多级工作联系单）*/
	public static final String[] OPTIONS_APPLY_MAIN = {CHOICE_APPLY_TO_SIGN,CHOICE_APPLY_TO_CANCEL};
	
	/**申请节点选项组（下级流程）（1、部门领导签发）*/
	public static final String[] OPTIONS_APPLY_LOWER = {CHOICE_APPLY_TO_SIGN};
	
	
	//签发节点选项
	/**1、同意签发*/
	public static final String CHOICE_SIGN_AGREE = "1";
	
	/**2、返回修改*/
	public static final String CHOICE_SIGN_TO_APPLY = "2";
	
	/**3、处理完毕*/
	public static final String CHOICE_SIGN_FINISH = "3";
	
	/**签发节点选项组（主流程）（1、同意签发，2、返回修改）*/
	public static final String[] OPTIONS_SIGN_MAIN = {CHOICE_SIGN_AGREE,CHOICE_SIGN_TO_APPLY};
	
	/**签发节点选项组（下级流程）（1、同意签发，3、处理完毕）*/
	public static final String[] OPTIONS_SIGN_LOWER = {CHOICE_SIGN_AGREE,CHOICE_SIGN_TO_APPLY,CHOICE_SIGN_FINISH};
	
	
	
	/**下级流程节点optionCode*/
	public static final String OPTIONCODE_LOWER = "LOWER_RESULT";
}
