/**
 * 
 */
package com.wonders.receive.workflow.process.instruct.util;

import com.wonders.receive.workflow.process.instruct.constants.InstructSubConstants;
import com.wonders.receive.workflow.process.instruct.constants.InstructSubStepConstants;
import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubVo;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: InstructSubUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-3-18 下午02:49:55 
 *  
 */
public class InstructSubUtil {
	public static String getSign(InstructSubVo vo){
		String stepLabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String instruct = "";
		if(InstructSubStepConstants.STEPNAME_SECRETARY_DEAL.equals(stepLabel)){
			if(InstructSubConstants.CHOICE_SECRETARY_READ.equals(choice)){
				instruct = "已阅。";
			}else if(InstructSubConstants.CHOICE_SECRETARY_OTHER_SUGGEST.equals(choice)){
				instruct = "其他意见。";
			}else if(InstructSubConstants.CHOICE_SECRETARY_BACK_OFFICE.equals(choice)){
				instruct = "领导暂时不能处理,退回办公室。";
			}
		}else if(InstructSubStepConstants.STEPNAME_LEADER_DEAL.equals(stepLabel)){
			if(InstructSubConstants.CHOICE_LEADER_READ.equals(choice)){
				instruct = "已阅。";
			}else if(InstructSubConstants.CHOICE_LEADER_OTHER_SUGGEST.equals(choice)){
				instruct = "其他意见。";
			}else if(InstructSubConstants.CHOICE_LEADER_BACK_SECRETARY.equals(choice)){
				instruct = "退回秘书。";
			}
		}
		
		return instruct;
	}
	
	public static Long getDisAgree(InstructSubVo vo){
		String stepLabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		Long disagree = 0L;
		if(InstructSubStepConstants.STEPNAME_SECRETARY_DEAL.equals(stepLabel)){
			if(InstructSubConstants.CHOICE_SECRETARY_READ.equals(choice)){
				disagree = 0L;
			}else if(InstructSubConstants.CHOICE_SECRETARY_OTHER_SUGGEST.equals(choice)){
				disagree = 1L;
			}else if(InstructSubConstants.CHOICE_SECRETARY_BACK_OFFICE.equals(choice)){
				disagree = 1L;
			}
		}else if(InstructSubStepConstants.STEPNAME_LEADER_DEAL.equals(stepLabel)){
			if(InstructSubConstants.CHOICE_LEADER_READ.equals(choice)){
				disagree = 0L;
			}else if(InstructSubConstants.CHOICE_LEADER_OTHER_SUGGEST.equals(choice)){
				disagree = 1L;
			}else if(InstructSubConstants.CHOICE_LEADER_BACK_SECRETARY.equals(choice)){
				disagree = 0L;
			}
		}
		
		return disagree;
	}
	
	public static String getOptionCode(InstructSubVo vo){
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String type = StringUtil.getNotNullValueString(vo.getType());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = "";
		if(InstructSubStepConstants.STEPNAME_SECRETARY_DEAL.equals(steplabel)){
			if(InstructSubConstants.INSTEAD_DEAL.equals(type)){
				if(InstructSubConstants.CHOICE_SECRETARY_READ.equals(choice)){
					optionCode = "LEADER_CHECK_READ";
				}else if(InstructSubConstants.CHOICE_SECRETARY_OTHER_SUGGEST.equals(choice)){
					optionCode = "LEADER_CHECK_OTHER_NOTION";
				}
			}else if(InstructSubConstants.SELF_DEAL.equals(type)){
				if(InstructSubConstants.CHOICE_SECRETARY_BACK_OFFICE.equals(choice)){
					optionCode = "LEADER_ASSISTANT_DEAL_LEADER_TMP_CANNT_DEAL";
				}
				
			}
		}else if(InstructSubStepConstants.STEPNAME_LEADER_DEAL.equals(steplabel)){
		
			if(InstructSubConstants.CHOICE_LEADER_READ.equals(choice)){
				optionCode = "LEADER_CHECK_READ";
			}else if(InstructSubConstants.CHOICE_LEADER_OTHER_SUGGEST.equals(choice)){
				optionCode = "LEADER_CHECK_OTHER_NOTION";
			}

		}
		return optionCode;
	}
	
	
}
