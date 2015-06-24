/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.util;



import com.wonders.receive.workflow.process.simulate.constants.SimulateSubConstants;
import com.wonders.receive.workflow.process.simulate.constants.SimulateSubStepConstants;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubVo;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: SimulateSubUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-20 上午8:39:49 
 *  
 */
public class SimulateSubUtil {

	public static String getOptionCode(SimulateSubVo vo){
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String type = StringUtil.getNotNullValueString(vo.getType());
		
		String optionCode = "";
		if(SimulateSubStepConstants.STEPNAME_SIMULATE.equals(steplabel) 
				|| SimulateSubStepConstants.STEPNAME_SIMULATE_SUGGEST_COLLECT.endsWith(steplabel)){
			if(SimulateSubConstants.SELF_DEAL.equals(type)){
				if(SimulateSubConstants.CHOICE_LEADER_INSTRUCT.equals(choice)){
					optionCode = "PLAN_PROC_CHECK_ASK_LEADER";
				}else if(SimulateSubConstants.CHOICE_SIMULATE_DIRECT.equals(choice)){
					optionCode = "PLAN_PROC_CHECK_DOIT";
				}else if(SimulateSubConstants.CHOICE_DEPT_LEADER_CONCURRENCE.equals(choice)){
					optionCode = "DEPT_LEADER_COMMON_DEAL_LEADER";
				}else if(SimulateSubConstants.CHOICE_DEPT_SUGGEST.equals(choice)){
					optionCode = "PLAN_PROC_ASK_BUSS_DEPT";
				}
			}else if(SimulateSubConstants.SUMBIT_LEADER.equals(type)){
				if(SimulateSubConstants.CHOICE_LEADER_INSTRUCT.equals(choice)){
					optionCode = "PLAN_PROC_ASK_LEADER";
				}else if(SimulateSubConstants.CHOICE_SIMULATE_DIRECT.equals(choice)){
					optionCode = "PLAN_PROC_DOIT";
				}else if(SimulateSubConstants.CHOICE_DEPT_LEADER_CONCURRENCE.equals(choice)){
					optionCode = "DEPT_LEADER_COMMON_DEAL";
				}else if(SimulateSubConstants.CHOICE_DEPT_SUGGEST.equals(choice)){
					optionCode = "PLAN_PROC_ASK_BUSS_DEPT";
				}
			}
		}else if(SimulateSubStepConstants.STEPNAME_SIMULATE_LEADER.equals(steplabel)){
			if(SimulateSubConstants.CHOICE_LEADER_INSTRUCT.equals(choice)){
				optionCode = "PLAN_PROC_CHECK_ASK_LEADER";
			}else if(SimulateSubConstants.CHOICE_SIMULATE_DIRECT.equals(choice)){
				optionCode = "PLAN_PROC_CHECK_DOIT";
			}else if(SimulateSubConstants.CHOICE_DEPT_LEADER_CONCURRENCE.equals(choice)){
				optionCode = "DEPT_LEADER_COMMON_DEAL_LEADER";
			}else if(SimulateSubConstants.CHOICE_DEPT_SUGGEST.equals(choice)){
				optionCode = "PLAN_PROC_CHECK_ASK_BUSS_DEPT";
			}
		}
		return optionCode;
	}
	
	

}
