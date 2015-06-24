package com.wonders.contact.deptContact.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.contact.common.util.CommonUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.service.DeptContactTreeService;
import com.wonders.contact.tree.constant.TreeConstants;
import com.wonders.contact.tree.model.bo.TreeNode;
import com.wonders.contact.tree.model.vo.TreeModel;
import com.wonders.contact.tree.util.TreeUtil;
import com.wonders.util.StringUtil;

@Service("contact-deptContactTreeService")
@Scope("prototype")
public class DeptContactTreeServiceImpl implements DeptContactTreeService {
	Logger log = SimpleLogger.getLogger(this.getClass());
	
	private String mainUnitId="";
	private String rootId = "";
	private int level = 0;
	private TreeNode sourceNode;
	private List<String> markNodeId;
	private TreeModel treeModel;
	private List<String> stopNodes;
	
	@Override
	public void setMainUnitId(String mainUnitId){
		if(mainUnitId!=null) this.mainUnitId = mainUnitId;
	}
	
	@Override
	public void setRootId(String rootId){
		if(rootId!=null) this.rootId = rootId;
	}
	
	@Override
	public void setLevel(int level){
		if(level>0) this.level = level;
	}
	
	@Override
	public void setMarkNodeId(List<String> markNodeId){
		this.markNodeId = markNodeId;
	}
	
	@Override
	public void setTreeModel(TreeModel treeModel){
		this.treeModel = treeModel;
	}
	
	@Override
	public void setStopNodes(List<String> stopNodes) {
		this.stopNodes = stopNodes;
	}
	
	@Override
	public boolean markNodeOnTreeModel() {
		if(markNodeId==null||markNodeId.size()==0){
			log.warn("没有需要mark的节点！");
			return false;
		}
		
		boolean flag = false;
		for(int i=0;i<markNodeId.size();i++){
			if(markOnTreeNode(markNodeId.get(i))){
				flag = true;
			}
		}
		return flag;
	}
	
	
	@Override
	public boolean markOnTreeNode(String markNodeId){
		boolean flag = false;
		Map<String,TreeNode> treeNodeMap = treeModel.treeNodesMap;
		String id = StringUtil.getNotNullValueString(markNodeId);
		if(treeNodeMap.containsKey(id)){
			//标注节点
			//treeNodeMap.get(id).setParamByName(DeptcontactConstants.TREE_PARAMS_IS_LOWER_DEPT, true);
			treeNodeMap.get(id).mark(true);
			flag = true;
//log.debug("treeModel中标注ID:"+id+"");
		}else{
//log.warn("treeModel中找不到ID:"+id+"的节点！");
		}	
		return flag;
	}
	
	public boolean processOnTreeNode(TreeNode node){
		
//log.debug("--processOnTreeNode: id="+node.id+" pid="+node.pid);
		//已是顶层虚拟节点，直接退出
		if(TreeConstants.ROOT_TREE_ID.equals(node.id)){
//log.debug("return for root!");
			return true;
		}
		
		if(stopNodes!=null&&stopNodes.contains(node.id)){
//log.debug("stop node:node id="+node.id);
			return true;
		}
		
		//上级节点满足所需根节点
		if(!"".equals(rootId)&&rootId.equals(node.id)){
//log.debug("return for rootId:"+rootId+"!");
			//sourceNode.setParamByName(DeptContactConstants.TREE_PARAMS_TOP_NODE_ID, node.id);
			return true;
		}
		
		//当前节点是否配置下级流程
		Boolean flag = node.marked;
		if(flag!=null&&flag){
			sourceNode.setParamByName(DeptContactConstants.TREE_PARAMS_TOP_NODE_ID, node.id);
		}
		
		//当层级满足最高层级时
		if(level>0&&level==node.level){
//log.debug("return for level:"+level+"!");
			//sourceNode.setParamByName(DeptContactConstants.TREE_PARAMS_TOP_NODE_ID, node.id);
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	@Override
	public List<TreeNode> getResultNodes(){
		List<TreeNode> resultList = new ArrayList<TreeNode>();
//log.debug("mainUnitId:"+mainUnitId);
		List<String> deptId = CommonUtil.stringsToList(mainUnitId);
		for(int i=0;i<deptId.size();i++){
			TreeNode leaf = treeModel.getTreeNode(deptId.get(i));
//log.debug(leaf);
			this.sourceNode = leaf;
			//leaf.setParamByName(DeptContactConstants.TREE_PARAMS_TOP_NODE_ID, leaf.id);
			findTreeRootNode(leaf);
			
//log.debug(""+leaf.id+" belongs to "+leaf.getParamByName(DeptContactConstants.TREE_PARAMS_TOP_NODE_ID));
		}
//log.debug("--result tree--");
//TreeUtil.showTree(treeModel);
//log.debug("--result tree--");
		
		for(int i=0;i<deptId.size();i++){
			TreeNode leaf = treeModel.getTreeNode(deptId.get(i));
			
			//关联的顶层节点ID
			String id = leaf.getParamByName(DeptContactConstants.TREE_PARAMS_TOP_NODE_ID);
//log.debug(leaf.id+"----"+id);
			if(id!=null){
				TreeNode top_node = treeModel.getTreeNode(id);
//log.debug("leaf_id="+leaf.id+" top_id="+top_node.id);
				List<TreeNode> includeNodes = top_node.getParamByName(DeptContactConstants.TREE_PARAMS_INCLUDE_NODE);
				
				if(includeNodes==null){
					includeNodes = new ArrayList<TreeNode>();
					top_node.setParamByName(DeptContactConstants.TREE_PARAMS_INCLUDE_NODE, includeNodes);
				}
				if(!includeNodes.contains(leaf)&&!leaf.equals(top_node)){
//log.debug("includeNodes add:"+leaf.id);
					includeNodes.add(leaf);
				}
				if(!resultList.contains(top_node)){
//log.debug("resultList add:"+top_node.id);
					resultList.add(top_node);
				}
			}else{
				resultList.add(leaf);
			}
		}
		Collections.sort(resultList,TreeUtil.c_level);
		for(int i=0;i<resultList.size();i++){
			TreeNode node = resultList.get(i);
//log.debug(i+":"+node);
			List<TreeNode> includeNodes = node.getParamByName(DeptContactConstants.TREE_PARAMS_INCLUDE_NODE);
			if(includeNodes!=null){
				Collections.sort(includeNodes,TreeUtil.c_level);
				//for(int j=0;j<includeNodes.size();j++){
//log.debug(i+"-"+j+":"+includeNodes.get(j));
				//}
			}
		}
		
		return resultList;
	}
	
	/**递归遍历节点
	 * @param node
	 */
	private void findTreeRootNode(TreeNode node){
		if(processOnTreeNode(node)){
			return;
		}
		
		findTreeRootNode(node.parentNode);
		
		return;
	}
}
