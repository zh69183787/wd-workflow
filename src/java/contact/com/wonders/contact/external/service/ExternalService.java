package com.wonders.contact.external.service;

import java.util.List;
import java.util.Map;

import com.wonders.contact.organTree.model.bo.OrganDeptBo;
import com.wonders.contact.organTree.model.bo.OrganUserBo;

/**外部接口service
 * @author XFZ
 * @version 1.0 2012-6-19
 */
public interface ExternalService {
	public void setToken(String token);
	
	public List<OrganUserBo> getProcessReceivers(String processName,String stepName,String deptIds);
	
	/**根据部门ID集合获得收发员信息Map
	 * @param deptIds 
	 * @return
	 */
	public Map<String, String> getDeptReceiversMap(String deptIdStr);
	
	public Map<String, String> getDeptReceiversMap(List<String> deptIds);
	
	public Map<String, String> getDeptReceiversMap(String processName,String stepName,List<String> deptIds);
	
	public Map<String,String> getDeptReceiversMap(String processName,String stepName,String deptIdStr);
	
	/**根据部门ID集合获得收发员信息
	 * @param deptIds 
	 * @return
	 */
	public List<OrganUserBo> getDeptReceivers(String deptIdStr);
	
	
	/**根据部门ID集合获得部门领导层信息
	 * @param deptIds
	 * @return
	 */
	public List<OrganUserBo> getDeptLeaders(String deptIdStr);
	
	
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
	public List<OrganDeptBo> getChildNodes(String deptId);
	
	
	
	
	/**获得树节点信息
	 * @param deptIds
	 * @return
	 */
	public List<OrganDeptBo> getNodesInfo(String deptIds);
	
	public List<OrganDeptBo> getNodesInfo(List<String> deptIds);
	
	
	
	
	
	
}
