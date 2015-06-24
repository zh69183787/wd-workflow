package com.wonders.contact.tree.model.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

/**组织树节点
 * @author XFZ
 * @version 1.0 2012-6-2
 */
public class TreeNode {
	public TreeNode(){
		super();
	}
	
	public TreeNode(String id){
		super();
		this.id = id;
	}
	
	/**
	 * 父节点
	 */
	public TreeNode parentNode;
	
	/**
	 * ID
	 */
	@Expose
	public String id;
	
	/**
	 * 父节点ID
	 */
	@Expose
	public String pid;
	
	/**
	 * 名称
	 */
	public String name;
	
	/**
	 * 携带参数map
	 */
	public Map<String,Object> params = new HashMap<String,Object>();
	
	/**
	 * 下级节点
	 */
	public List<TreeNode> childNodes = new ArrayList<TreeNode>();
	
	/**
	 * 序号
	 */
	@Expose
	public Integer order = 0;
	
	/**
	 * 层级
	 */
	@Expose
	public Integer level = 0;
	
	/**
	 * 是否标记
	 */
	public boolean marked = false;
	
	/**添加param参数
	 * @param <T> 
	 * @param paramName 参数名称
	 * @param obj 参数值
	 */
	public <T> void setParamByName(String paramName,T obj){
		params.put(paramName, obj);
	}
	
	/**获得param参数值
	 * @param <T>
	 * @param paramName 参数名称
	 * @return 参数值
	 */
	@SuppressWarnings("unchecked")
	public <T> T getParamByName(String paramName){
		try{
			T param = (T) params.get(paramName);
			
			if(param!=null){
				return param;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**是否叶子节点
	 * @return
	 */
	public boolean isLeaf(){
		return childNodes.size()==0?true:false;
	}
	
	public void mark(boolean flag){
		this.marked = flag;
	}
	
	@Override
	public String toString(){
		return "l="+level+" o="+order+" id:"+id+" name:"+name+" pid:"+pid+" params:"+params.size()+" "+(isLeaf()?"leaf":"")+" "+(marked?"marked":"");
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TreeNode){
			TreeNode node = (TreeNode)obj;
			if(this.id.equals(node.id)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
