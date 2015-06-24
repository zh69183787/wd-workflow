/**
 * 
 */
package com.wonders.receive.workflow.process.finish.util;

import com.wonders.receive.workflow.process.finish.constants.FinishSubConstants;
import com.wonders.receive.workflow.process.finish.model.vo.FinishSubVo;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: FinishSubUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-20 上午8:39:49 
 *  
 */
public class FinishSubUtil {

	public static String getOptionCode(FinishSubVo vo){
		//String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = "";
		
		if(FinishSubConstants.CHOICE_FINISH.equals(choice)){
			optionCode = "END_DEAL_FINISH_FILE_FLOW";
		}else if(FinishSubConstants.CHOICE_SIMULATE_AGAIN.equals(choice)){
			optionCode = "END_DEAL_RE_PROCESS";
		}else if(FinishSubConstants.CHOICE_DEPT_LEADER_CONCURRENCE.equals(choice)){
			optionCode = "DEPT_LEADER_COMMON_DEAL";
		}
		
		return optionCode;
	}
	
	

}
