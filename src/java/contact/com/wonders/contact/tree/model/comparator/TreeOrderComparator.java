package com.wonders.contact.tree.model.comparator;

import java.util.Comparator;

import com.wonders.contact.tree.model.bo.TreeNode;

public class TreeOrderComparator implements Comparator<TreeNode> {
	
	@Override
	public int compare(TreeNode node1, TreeNode node2) {
		
		Integer orders1 = node1.order;
		Integer orders2 = node2.order;
		
		return orders1-orders2;
	}
}
