package com.wonders.project.workflow.external.constant;

/**定义portal外部接口method名称
 * @author XFZ
 * @version 1.0 2012-6-19
 */
public class ExternalConstants {
	/**得到指定节点信息*/
	public static final String METHOD_GET_NODES_INFO = "getNodesInfo";
	
	/**得到指定节点相邻节点信息*/
	public static final String METHOD_GET_RELATED_NODES = "getRelatedNodes";
	
	/**得到指定节点子节点信息*/
	public static final String METHOD_GET_CHILD_NODES = "getChildNodes";
	
	/**得到指定部门领导层信息*/
	public static final String METHOD_GET_DEPT_LEADERS = "getDeptLeaders";
	
	/**得到指定部门大领导信息*/
	public static final String METHOD_GET_DEPT_SINGLE_LEADER = "getDeptSingleLeader";
	
	/**得到指定部门收发员信息*/
	public static final String METHOD_GET_DEPT_RECIEVERS = "getDeptReceivers";
	
	/**得到指定部门人员信息*/
	public static final String METHOD_GET_DEPT_USERS = "getDeptUsers";
	
	/**得到指定部门人员信息(非领导)*/
	public static final String METHOD_GET_DEPT_USERS_OFF_LEADERS = "getDeptUsersOffLeaders";

	/**得到集团领导层及秘书信息*/
	public static final String METHOD_GET_COM_DEPT_LEADERS = "getLeaderSecretary";
	
	/**得到部门收发员信息（流程内，包括取消收发员功能时，返回领导）*/
	public static final String METHOD_GET_RECVS = "getReceivers";
	
	/**得到部门收发员信息（包括配置表）*/
	public static final String METHOD_GET_PROCESS_RECVS = "getProcessReceivers";
	
	/**得到指定群组信息*/
	public static final String METHOD_GET_GROUS_INFO = "getGroupsInfo";
	
	/**得到指定群组对应人员信息*/
	public static final String METHOD_GET_GROUP_USERS_INFO = "getGroupUsersInfo";
	
}
