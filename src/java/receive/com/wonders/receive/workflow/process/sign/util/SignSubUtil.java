/**
 * 
 */
package com.wonders.receive.workflow.process.sign.util;

import com.wonders.receive.workflow.process.sign.constants.SignSubConstants;
import com.wonders.receive.workflow.process.sign.constants.SignSubStepConstants;
import com.wonders.receive.workflow.process.sign.model.vo.SignSubVo;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: SignSubUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-3-18 下午02:49:55 
 *  
 */
public class SignSubUtil {
	public static String getSign(SignSubVo vo){
		String stepLabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String sign = "";
		if(SignSubStepConstants.STEPNAME_SECRETARY_DEAL.equals(stepLabel)){
			if(SignSubConstants.CHOICE_SECRETARY_READ.equals(choice)){
				sign = "已阅。";
			}else if(SignSubConstants.CHOICE_SECRETARY_AGREE.equals(choice)){
				sign = "同意。";
			}else if(SignSubConstants.CHOICE_SECRETARY_OTHER_SUGGEST.equals(choice)){
				sign = "其他意见。";
			}else if(SignSubConstants.CHOICE_SECRETARY_BACK_OFFICE.equals(choice)){
				sign = "领导暂时不能处理,退回办公室。";
			}
		}else if(SignSubStepConstants.STEPNAME_LEADER_DEAL.equals(stepLabel)){
			if(SignSubConstants.CHOICE_LEADER_READ.equals(choice)){
				sign = "已阅。";
			}else if(SignSubConstants.CHOICE_LEADER_AGREE.equals(choice)){
				sign = "同意。";
			}else if(SignSubConstants.CHOICE_LEADER_BACK_SECRETARY.equals(choice)){
				sign = "退回秘书。";
			}else if(SignSubConstants.CHOICE_LEADER_OTHER_SUGGEST.equals(choice)){
				sign = "其他意见。";
			}
		}
		
		return sign;
	}
	
	public static Long getDisAgree(SignSubVo vo){
		String stepLabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		Long disagree = 0L;
		if(SignSubStepConstants.STEPNAME_SECRETARY_DEAL.equals(stepLabel)){
			if(SignSubConstants.CHOICE_SECRETARY_READ.equals(choice)){
				disagree = 0L;
			}else if(SignSubConstants.CHOICE_SECRETARY_AGREE.equals(choice)){
				disagree = 0L;
			}else if(SignSubConstants.CHOICE_SECRETARY_OTHER_SUGGEST.equals(choice)){
				disagree = 1L;
			}else if(SignSubConstants.CHOICE_SECRETARY_BACK_OFFICE.equals(choice)){
				disagree = 1L;
			}
		}else if(SignSubStepConstants.STEPNAME_LEADER_DEAL.equals(stepLabel)){
			if(SignSubConstants.CHOICE_LEADER_READ.equals(choice)){
				disagree = 0L;
			}else if(SignSubConstants.CHOICE_LEADER_AGREE.equals(choice)){
				disagree = 0L;
			}else if(SignSubConstants.CHOICE_LEADER_BACK_SECRETARY.equals(choice)){
				disagree = 0L;
			}else if(SignSubConstants.CHOICE_LEADER_OTHER_SUGGEST.equals(choice)){
				disagree = 1L;
			}
		}
		
		return disagree;
	}
	
	public static String getOptionCode(SignSubVo vo){
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String type = StringUtil.getNotNullValueString(vo.getType());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = "";
		if(SignSubStepConstants.STEPNAME_SECRETARY_DEAL.equals(steplabel)){
			if(SignSubConstants.INSTEAD_DEAL.equals(type)){
				if(SignSubConstants.CHOICE_SECRETARY_READ.equals(choice)){
					optionCode = "LEADER_TREAT_READ";
				}else if(SignSubConstants.CHOICE_SECRETARY_AGREE.equals(choice)){
					optionCode = "LEADER_TREAT_AGREE";
				}else if(SignSubConstants.CHOICE_SECRETARY_OTHER_SUGGEST.equals(choice)){
					optionCode = "LEADER_TREAT_OTHER";
				}
			}else if(SignSubConstants.SELF_DEAL.equals(type)){
				if(SignSubConstants.CHOICE_SECRETARY_BACK_OFFICE.equals(choice)){
					optionCode = "LEADER_ASSISTANT_TREAT_LEADER_TMP_CANNT_DEAL";
				}
				
			}
		}else if(SignSubStepConstants.STEPNAME_LEADER_DEAL.equals(steplabel)){
		
			if(SignSubConstants.CHOICE_LEADER_READ.equals(choice)){
				optionCode = "LEADER_TREAT_READ";
			}else if(SignSubConstants.CHOICE_LEADER_AGREE.equals(choice)){
				optionCode = "LEADER_TREAT_AGREE";
			}else if(SignSubConstants.CHOICE_LEADER_OTHER_SUGGEST.equals(choice)){
				optionCode = "LEADER_TREAT_OTHER";
			}

		}
		return optionCode;
	}
	
	
}
