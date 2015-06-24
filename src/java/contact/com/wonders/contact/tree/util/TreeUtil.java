package com.wonders.contact.tree.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.contact.common.util.CommonUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.organTree.model.bo.OrganDeptBo;
import com.wonders.contact.tree.model.bo.TreeNode;
import com.wonders.contact.tree.model.comparator.TreeLevelComparator;
import com.wonders.contact.tree.model.comparator.TreeOrderComparator;
import com.wonders.contact.tree.model.vo.TreeModel;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;

/**树常用操作
 * @author XFZ
 * @version 1.0 2012-6-2
 */
@Component("contact-treeUtil")
public class TreeUtil {
	@SuppressWarnings("unused")
	private static SimpleLogger log = new SimpleLogger(TreeUtil.class);
	private static DbUtil dbUtil;
	public static TreeOrderComparator c_order = new TreeOrderComparator();
	public static TreeLevelComparator c_level = new TreeLevelComparator();
	
	
	/**根据node集合生成TreeModel
	 * @param treeNodes 
	 * @return
	 */
	public static TreeModel generateTreeByNodes(List<TreeNode> treeNodes){
		TreeModel treeModel = new TreeModel();
		TreeNode rootNode = treeModel.rootNode;
		
		
		Map<String,TreeNode> treeNodesMap = treeModel.treeNodesMap;
		
		/**
		 * 初始化TreeNode元素MAP;
		 */
		for(int i=0;i<treeNodes.size();i++){
			TreeNode node = treeNodes.get(i);
			
			//第一级节点初始化
			if("".equals(node.pid)){
				node.pid = rootNode.id;
			}
			treeNodesMap.put(node.id, node);
		}
		
		
//log.debug("treeMap.size()="+treeNodesMap.size());
		
		/**
		 * 初始化TreeNode上下级关系;
		 */
		for(int i=0;i<treeNodes.size();i++){
			TreeNode node = treeNodes.get(i);
			TreeNode parentNode = treeNodesMap.get(node.pid);
			
			node.parentNode = parentNode;
			
			parentNode.childNodes.add(node);
		}
		
		/**
		 * 初始化Tree排序
		 */
//log.debug("orginal tree:");
//showTree(treeModel);
		
		sortTree(rootNode,rootNode.level);
		
		return treeModel;
	}
	
	/**根据叶子节点字符串生成TreeModel
	 * @param nodeIds ID字符串(,分隔)
	 * @return
	 */
	public static TreeModel generateTree(List<OrganDeptBo> list){
		List<TreeNode> treeNodes = getTreeNodes(list);
		return generateTreeByNodes(treeNodes);
	}
	
	
	
	/**根据获得的node信息创建树节点
	 * @param nodeInfo
	 * @return
	 */
	public static TreeNode getTreeBo(OrganDeptBo nodeInfo){
		TreeNode tn = new TreeNode();
		
		String pid = StringUtil.getNotNullValueString(nodeInfo.pid);
		String id = StringUtil.getNotNullValueString(nodeInfo.id);
		String name = nodeInfo.name;
		Integer order = nodeInfo.orders;
		Integer level = nodeInfo.levels;
//log.debug("");
		if("2500".equals(id)) return null;
		if("0".equals(pid)) return null;
		if("2500".equals(pid)) pid = "";
		
		tn.id = id;
		tn.pid = pid;
		tn.name = name;
		tn.order = order;
		tn.level = level;
//log.debug(tn);
		return tn;
	}

	/**根据部门ID字符串获得树节点list
	 * @param nodeIdStr
	 * @return
	 */
	public static List<TreeNode> getTreeNodes(List<OrganDeptBo> list){
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		//List<Map<String,Object>> list = getTreeNodeInfos(nodeIdStr);
		
		for(int i =0;i<list.size();i++){
			OrganDeptBo nodeInfo = list.get(i);
			TreeNode tn = getTreeBo(nodeInfo);
			if(tn!=null) treeNodes.add(tn);
		}
		
		return treeNodes;
	}
	
	
	
	/**根据根node获得子树
	 * @return
	 */
	public static TreeModel getSubTreeByRootNode(TreeNode treeNode){
		TreeModel TreeModel = new TreeModel(treeNode);
		return TreeModel;
	}
	
	
	
	/**对TreeModel进行排序
	 * @param treeNode
	 * @param level
	 */
	private static void sortTree(TreeNode treeNode,int level){
		int level_local = level;
		List<TreeNode> treeNodes = treeNode.childNodes;
		treeNode.level = level_local;
		if(treeNodes.size()==0){
			return;
		}
		
		Collections.sort(treeNodes, c_order);
		
		for(int i=0;i<treeNodes.size();i++){
			TreeNode node = treeNodes.get(i);
			sortTree(node,level_local+1);
			node.order=i;
		}
	}
	
	
	
	/**更新treeModel
	 * @param treeModel
	 */
	public static void refreshTreeModel(TreeModel treeModel){
		refreshTreeMaps(treeModel,null);
	}
	
	/**更新tree
	 * @param treeNode
	 * @param treeNodesMap
	 */
	public static void refreshTreeMaps(TreeModel treeModel,TreeNode treeNode){
		if(treeNode==null){
			treeNode = treeModel.rootNode;
		}
		if(treeModel.treeNodesMap==null){
			treeModel.treeNodesMap = new HashMap<String,TreeNode>();
		}
		/*
		if(treeModel.treeNameMap==null){
			treeModel.treeNameMap = new HashMap<String,String>();
		}
		*/
		
		treeModel.treeNodesMap.put(treeNode.id, treeNode);
		//treeModel.treeNameMap.put(treeNode.id, treeNode.name);
		
		List<TreeNode> treeNodes = treeNode.childNodes;
		if(treeNodes.size()==0){
			return;
		}
		
		for(int i=0;i<treeNodes.size();i++){
			TreeNode node = treeNodes.get(i);
			refreshTreeMaps(treeModel,node);
		}
		
		return;
	}
	
	/**获得中文名称字典表
	 * @param treeModel
	 * @return
	 */
	public static Map<String,String> getNameMap(TreeModel treeModel){
		return getNameMap(treeModel.rootNode,null);
	}
	
	/**递归获得名称字典表
	 * @param treeNode
	 * @param map
	 * @return
	 */
	private static Map<String,String> getNameMap(TreeNode treeNode,Map<String,String> map){
		if(map==null) map = new HashMap<String,String>();
		
		map.put(treeNode.id, treeNode.name);
				
		List<TreeNode> treeNodes = treeNode.childNodes;
		if(treeNodes.size()==0){
			return map;
		}
		
		for(int i=0;i<treeNodes.size();i++){
			TreeNode node = treeNodes.get(i);
			getNameMap(node,map);
		}
		
		return map;
	}
	
	
	
	/*
	 * 树展现 
	 */
	
	/**根TreeNode数据展现
	 * @param treeNode
	 */
	public static void showTree(TreeNode treeNode){
		@SuppressWarnings("unused")
		String prefix = CommonUtil.getStrByTimes("  ", treeNode.level);
		
//log.debug(prefix+" "+treeNode);
		//List<TreeNode> treeNodes = treeNode.childNodes;
		
		if(treeNode.childNodes.size()==0){
//log.debug(prefix+" 叶子节点:"+treeNode.id);
			return;
		}
		
		for(int i=0;i<treeNode.childNodes.size();i++){
			TreeNode node = treeNode.childNodes.get(i);
//log.debug("下级节点："+node);
			showTree(node);
		}
	}
	
	/**TreeModel数据展现
	 * @param treeModel
	 */
	public static void showTree(TreeModel treeModel){
		showTree(treeModel.rootNode);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static DbUtil getDbUtil() {
		return TreeUtil.dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		TreeUtil.dbUtil = dbUtil;
	}
}
