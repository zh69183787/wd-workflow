/**
 * 
 */
package com.wonders.receive.workflow.process.redv.util;

import com.wonders.receive.workflow.process.redv.constants.RedvMainConstants;
import com.wonders.receive.workflow.process.redv.constants.RedvMainStepConstants;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainParamVo;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainVo;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: RedvMainUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:37:52 
 *  
 */
public class RedvMainUtil {
	public static String getOptionCode(RedvMainVo vo){
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = "";
		
		if(RedvMainStepConstants.STEPNAME_REGISTER.equals(steplabel)){
			
		}else if(RedvMainStepConstants.STEPNAME_LEADER_CHECK.equals(steplabel)){
			if(RedvMainConstants.CHOICE_LEADER_SUBMIT_SIMULATE.equals(choice)){
				optionCode = "DEPT_LEADER_CHECK_PASS";
			}else if(RedvMainConstants.CHOICE_LEADER_REGISTER_MODIFY.equals(choice)){
				optionCode = "DEPT_LEADER_CHECK_SEND_BACK_MODIFY";
			}else if(RedvMainConstants.CHOICE_LEADER_CANCEL_REDV.equals(choice)){
				optionCode = "DEPT_LEADER_CHECK_CANCEL_REPORT";
			}
		}else if(RedvMainStepConstants.STEPNAME_MODIFY.equals(steplabel)){
			if(RedvMainConstants.CHOICE_MODIFY_SUBMIT_LEADER.equals(choice)){

			}else if(RedvMainConstants.CHOICE_MODIFY_CANCEL_REDV.equals(choice)){
				optionCode = "DEPT_LEADER_CHECK_CANCEL_REPORT";
			}
		}else if(RedvMainStepConstants.STEPNAME_RECORD.equals(steplabel)){
			
		}
		
		return optionCode;
	}
	

	
	
	/**是否跳转更新页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeUpdate(RedvMainParamVo params){
		boolean flag = false;
		String steplabel = params.getProcessParamValue("steplabel");
		if(RedvMainStepConstants.STEPNAME_LEADER_CHECK.equals(steplabel)){
			flag = true;
		}
		
		return flag;
	}
	
	/**是否跳转操作页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeModify(RedvMainParamVo params){
		boolean flag = false;
		String steplabel = params.getProcessParamValue("steplabel");
		if(RedvMainStepConstants.STEPNAME_MODIFY.equals(steplabel)){
			flag = true;
		}
		return flag;
	}
	
	/**根据步骤获得类型
	 * add:新增
	 * operate:业务处理
	 * update:修改&业务处理
	 * detail:查看
	 * @return
	 */
	public static String getTypeByInfo(RedvMainParamVo params){
		String type = "detail";
		
		if(isTypeUpdate(params)) type = "update";
		if(isTypeModify(params)) type = "update";
		
//log.debug("type="+type);
		
		return type;
	}
}
