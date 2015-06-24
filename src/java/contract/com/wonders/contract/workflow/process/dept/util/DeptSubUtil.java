/**
 * 
 */
package com.wonders.contract.workflow.process.dept.util;

import com.wonders.contract.workflow.process.dept.constants.DeptSubConstants;
import com.wonders.contract.workflow.process.dept.constants.DeptSubStepConstants;
import com.wonders.contract.workflow.process.dept.model.vo.DeptSubVo;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: DeptSubUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-20 上午8:39:49 
 *  
 */
public class DeptSubUtil {

	public static String getOptionCode(DeptSubVo vo){
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String choice2 = StringUtil.getNotNullValueString(vo.getChoice2());
		
		String optionCode = "";
		if(DeptSubStepConstants.STEPNAME_DISPATCHER.equals(steplabel)){
			if(DeptSubConstants.CHOICE_DISPATCHER_SEND.equals(choice)){
				optionCode = "DEPT_OPERATOR_DISPATCH_BUSS";
			}	
			if(DeptSubConstants.CHOICE_DISPATCHER_DEAL.equals(choice)){
				if(DeptSubConstants.CHOICE_DISPATCHER_DEAL_NO_SUGGEST.equals(choice2)){
					optionCode = "DEPT_OPERATOR_DEAL_BUSS_NO_NOTION";
				}
				if(DeptSubConstants.CHOICE_DISPATCHER_DEAL_HAS_SUGGEST.equals(choice2)){
					optionCode = "DEPT_OPERATOR_DEAL_BUSS_NEW_NOTION";
				}
			}
		}else if(DeptSubStepConstants.STEPNAME_DEAL.equals(steplabel)){
			if(DeptSubConstants.CHOICE_DEAL_NO_SUGGEST.equals(choice)){
				optionCode = "BUSS_OPERATOR_NO_NOTION";
			}	
			if(DeptSubConstants.CHOICE_DEAL_HAS_SUGGEST.equals(choice)){
				optionCode = "BUSS_OPERATOR_NEW_NOTION";
			}
			if(DeptSubConstants.CHOICE_DEAL_NOT_MY_BUSINESS.equals(choice)){
				optionCode = "BUSS_OPERATOR_NOT_MY_BUSS";
			}
		}else if(DeptSubStepConstants.STEPNAME_LEADER.equals(steplabel)){
			if(DeptSubConstants.CHOICE_LEADER_PASS.equals(choice)){
				optionCode = "DEPT_LEADER_CHECK_PASS";
			}	
			if(DeptSubConstants.CHOICE_LEADER_FAILED.equals(choice)){
				optionCode = "DEPT_LEADER_CHECK_FAIL_NEW_NOTION";
			}
			if(DeptSubConstants.CHOICE_LEADER_CONTINUE.equals(choice)){
				if(DeptSubConstants.CHOICE_LEADER_CONTINUE_DEAL.equals(choice2)){
					optionCode = "DEPT_LEADER_DEPT_BUSS_OPERATOR_GO_ON";
				}
				if(DeptSubConstants.CHOICE_LEADER_CONTINUE_CHOICE_PERSON.equals(choice2)){
					optionCode = "DEPT_LEADER_DEPT_BUSS_OPERATOR_GO_ON";
				}
			}
		}
		return optionCode;
	}
	
	

}
