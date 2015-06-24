package com.wonders.contact.tree.model.vo;

import java.util.HashMap;
import java.util.Map;

import com.wonders.contact.tree.constant.TreeConstants;
import com.wonders.contact.tree.model.bo.TreeNode;
import com.wonders.contact.tree.util.TreeUtil;

/**树结构模型
 * @author XFZ
 * @version 1.0 2012-6-2
 */
public class TreeModel {
	public TreeModel(){
		super();
		rootNode = new TreeNode(TreeConstants.ROOT_TREE_ID);
		rootNode.level = 0;
		
		TreeUtil.refreshTreeModel(this);
	}
	
	public TreeModel(TreeNode treeNode){
		super();
		
		rootNode = new TreeNode(TreeConstants.ROOT_TREE_ID);
		rootNode.level = 0;
		
		treeNode.pid = rootNode.id;
		treeNode.parentNode = rootNode;
		rootNode.childNodes.add(treeNode);
		
		TreeUtil.refreshTreeModel(this);
		
	}
	
	public TreeNode getTreeNode(String id){
		return treeNodesMap.get(id);
	}
	
	public TreeNode rootNode;
	
	public Map<String,TreeNode> treeNodesMap = new HashMap<String,TreeNode>();
	
	//public Map<String,String> treeNameMap = new HashMap<String,String>();
}
