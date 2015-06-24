package com.wonders.project.workflow.external.service;

import java.util.List;
import java.util.Map;

import com.wonders.project.workflow.organTree.model.bo.GroupBo;
import com.wonders.project.workflow.organTree.model.bo.GroupUserBo;
import com.wonders.project.workflow.organTree.model.bo.OrganDeptBo;
import com.wonders.project.workflow.organTree.model.bo.OrganLeaderBo;
import com.wonders.project.workflow.organTree.model.bo.OrganUserBo;


/**外部接口service
 * @author XFZ
 * @version 1.0 2012-6-19
 */
public interface ExternalService {
	public void setToken(String token);
	
	/**根据部门ID集合获得收发员信息Map
	 * @param deptIds 
	 * @return
	 */
	public Map<String, String> getDeptReceiversMap(String processName,String stepName,String deptIdStr);
	
	public Map<String, String> getDeptReceiversMap(String processName,String stepName,List<String> deptIds);
	
	
	/**根据部门ID集合获得收发员信息
	 * @param deptIds 
	 * @return
	 */
	public List<OrganUserBo> getDeptReceivers(String deptIdStr);
	public List<OrganUserBo> getReceivers(String processName,String stepName,String deptIds);
	public List<OrganUserBo> getProcessReceivers(String processName,String stepName,String deptIds);
	
	
	/**根据部门ID集合获得部门领导层信息
	 * @param deptIds
	 * @return
	 */
	public List<OrganUserBo> getDeptLeaders(String deptIdStr);
	
	
	
	/**根据部门ID集合获得部门最大领导信息Map
	 * @param deptIds 
	 * @return
	 */
	public Map<String,String> getDeptSingleLeaderMap(String deptIdStr);
	/**根据部门ID集合获得部门最大领导信息
	 * @param deptIds
	 * @return
	 */
	public List<OrganUserBo> getDeptSingleLeader(String deptIdStr);
	
	
	/**根据部门ID集合获得部门内部人员信息
	 * @param deptIds
	 * @return
	 */
	public List<OrganUserBo> getDeptUsers(String deptIdStr);
	
	
	/**根据部门ID集合获得部门内部人员信息
	 * @param deptIds
	 * @return
	 */
	public List<OrganUserBo> getDeptUsersOffLeaders(String deptIdStr);
	
	
	
	/**根据子节点获得树完整路径信息
	 * @param deptIdStr
	 * @return
	 */
	public List<OrganDeptBo> getRelatedNodes(String deptIds);
	
	/**获得树节点子节点*/
	public List<OrganDeptBo> getChildNodes(String processName,String deptId);
	
	
	
	
	/**获得树节点信息
	 * @param deptIds
	 * @return
	 */
	public List<OrganDeptBo> getNodesInfo(String deptIds);
	
	public List<OrganDeptBo> getNodesInfo(List<String> deptIds);
	
	/**
	 * 结点完整信息(含接收人,领导)
	 * @param deptIds
	 * @return
	 */
	public List<OrganDeptBo> getNodesFullInfo(String deptIds);
	
	/*
	 *获得集团领导 及 秘书节点信息
	 * @param deptIds
	 * @return
	 */
	public List<OrganLeaderBo> getComLeaderInfo(String deptId);
		
	
	/** 
	* @Title: getGroupsInfo 
	* @Description: 获取群组信息
	* @param @param code
	* @param @return    设定文件 
	* @return List<GroupBo>    返回类型 
	* @throws 
	*/
	public List<GroupBo> getGroupsInfo(String code);
	
	
	/** 
	* @Title: getGroupUsersInfo 
	* @Description: 获取群组用户信息
	* @param @param code
	* @param @return    设定文件 
	* @return List<GroupBo>    返回类型 
	* @throws 
	*/
	public List<GroupUserBo> getGroupUsersInfo(String code);
	
}
