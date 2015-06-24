/** 
 * Copyright (c) 1995-2011 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of WondersGroup.
 * You shall not disclose such Confidential Information and shall use it only in accordance 
 * with the terms of the license agreement you entered into with WondersGroup. 
 *
 */

package com.wonders.contact.deptConfig.model.bo;

/**
 * TDeptContactRelation实体定义
 * 
 * @author zhoushun
 * @version $Revision$
 * @date 2012-5-7
 * @author modify by $Author$
 * @since 1.0
 */
@SuppressWarnings("serial")
public class TDeptContactRelation implements java.io.Serializable {

	private String id; // id

	private String description; // description

	private String ext1; // ext1

	private String ext2; // ext2

	private String ext3; // ext3

	private Long nodeId; // nodeId

	private String removed; // removed

	private Long rootNodeId; // rootNodeId

	private String treeCode; // treeCode

	private Long treeId; // treeId
	
	private Long rootTreeId;

	private String name;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Long getRootTreeId() {
		return rootTreeId;
	}


	public void setRootTreeId(Long rootTreeId) {
		this.rootTreeId = rootTreeId;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getId() {
		return id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	
	public String getExt2() {
		return ext2;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	
	public String getExt3() {
		return ext3;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	
	public Long getNodeId() {
		return nodeId;
	}

	public void setRemoved(String removed) {
		this.removed = removed;
	}

	
	public String getRemoved() {
		return removed;
	}

	public void setRootNodeId(Long rootNodeId) {
		this.rootNodeId = rootNodeId;
	}

	
	public Long getRootNodeId() {
		return rootNodeId;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	
	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}

	
	public Long getTreeId() {
		return treeId;
	}

}
