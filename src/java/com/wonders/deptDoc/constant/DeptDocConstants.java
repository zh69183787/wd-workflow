package com.wonders.deptDoc.constant;

import com.wonders.constants.WorkflowConstants;

public class DeptDocConstants {
	public static final String CATELOG_DOC_PROCESS = "000";
	
	public static final String FILE_DOC_RECEIVE = "001";
	public static final String FILE_RECEIVE_DIRECTIVE = "002";
	public static final String FILE_DOC_SEND = "003";
	public static final String FILE_PARTY_SEND = "004";
	public static final String FILE_DISCIPLINE_SEND = "005";
	public static final String FILE_PASS_SEND = "006";
	
	public static final String FILE_RIGHT_TYPE = "1";
	
	public static final String LINK_FLAG = "3";
	
	public static String getConstants(String pname){
		if(WorkflowConstants.NEW_DOC_SEND.equals(pname)){
			return FILE_DOC_SEND;
		}else if(WorkflowConstants.NEW_PARTY_SEND.equals(pname)){
			return FILE_PARTY_SEND;
		}else if(WorkflowConstants.NEW_DCP_SEND.equals(pname)){
			return FILE_DISCIPLINE_SEND;
		}
		return "";
	}
	
}