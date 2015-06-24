package com.wonders.contact.deptContact.service;

import java.util.List;

import com.wonders.contact.tree.model.bo.TreeNode;
import com.wonders.contact.tree.model.vo.TreeModel;

/**多级工作联系单部门树操作service
 * @author XFZ
 * @version 1.0 2012-6-2
 */
public interface DeptContactTreeService {
	public void setMainUnitId(String mainUnitId);
	public void setRootId(String rootId);
	public void setLevel(int level);
	public void setMarkNodeId(List<String> markNodeId);
	public void setTreeModel(TreeModel treeModel);
	public void setStopNodes(List<String> stopNodes);
	
	/**树节点标记逻辑
	 * @param markNodeId
	 * @return
	 */
	public boolean markOnTreeNode(String markNodeId);
	
	/**树节点业务逻辑
	 * @param node
	 * @return
	 */
	public boolean processOnTreeNode(TreeNode node);
	
	/**标注treeModel上的下级流程部门节点
	 * @param treeModel 需要标注的treeModel
	 * @param markNodes 需要标注的部门节点ID集合
	 */
	public boolean markNodeOnTreeModel();
	
	/**寻找所选主送部门的顶层节点
	 * @param treeModel
	 * @param mainUnitId
	 * @return
	 */
	public List<TreeNode> getResultNodes();
	
}
