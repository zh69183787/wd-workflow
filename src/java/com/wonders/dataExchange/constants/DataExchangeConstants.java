/**
 * 
 */
package com.wonders.dataExchange.constants;

/** 
 * @ClassName: DataExchangeConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月12日 下午1:50:37 
 *  
 */
public class DataExchangeConstants {
	public static final String WORKFLOW_CONTRACT_REVIEW = "合同后审流程";
	public static final String WORKFLOW_RECEIVE_RECV = "新收文流程";
	public static final String WORKFLOW_RECEIVE_REDV = "新收呈批件";
	
	public static String getType(String in){
		if(WORKFLOW_CONTRACT_REVIEW.equals(in)){
			return "contractReview";
		}else if(WORKFLOW_RECEIVE_RECV.equals(in)){
			return "docReceive";
		}else if(WORKFLOW_RECEIVE_REDV.equals(in)){
			return "docDirective";
		}
		
		return "";
	}
}
