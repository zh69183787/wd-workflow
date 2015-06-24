/**
 * 
 */
package com.wonders.receive.workflow.process.recv.util;

import com.wonders.receive.workflow.process.recv.constants.RecvMainConstants;
import com.wonders.receive.workflow.process.recv.constants.RecvMainStepConstants;
import com.wonders.receive.workflow.process.recv.model.vo.RecvMainParamVo;
import com.wonders.receive.workflow.process.recv.model.vo.RecvMainVo;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: RecvMainUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:37:52 
 *  
 */
public class RecvMainUtil {
	public static String getOptionCode(RecvMainVo vo){
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = "";
		
		if(RecvMainStepConstants.STEPNAME_REGISTER.equals(steplabel)){
			
		}else if(RecvMainStepConstants.STEPNAME_GET_SERIALNO.equals(steplabel)){
			if(RecvMainConstants.CHOICE_SERIALNO_SUBMIT_SIMULATE.equals(choice)){
				optionCode = "FILE_CHECKER_CHECK_PASS";
			}else if(RecvMainConstants.CHOICE_SERIALNO_REGISTER_MODIFY.equals(choice)){
				optionCode = "FILE_CHECKER_CHECK_SEND_BACK_MODIFY";
			}
		}else if(RecvMainStepConstants.STEPNAME_MODIFY.equals(steplabel)){
			if(RecvMainConstants.CHOICE_MODIFY_SUBMIT_SERIALNO.equals(choice)){
				optionCode = "FILE_CHECKER_CHECK_PASS";
			}else if(RecvMainConstants.CHOICE_MODIFY_CANCEL_RECV.equals(choice)){
				optionCode = "FILE_CHECKER_CHECK_CANCEL_REPORT";
			}
		}else if(RecvMainStepConstants.STEPNAME_RECORD.equals(steplabel)){
			
		}
		
		return optionCode;
	}
	

	
	
	/**是否跳转更新页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeUpdate(RecvMainParamVo params){
		boolean flag = false;
		String steplabel = params.getProcessParamValue("steplabel");
		if(RecvMainStepConstants.STEPNAME_GET_SERIALNO.equals(steplabel)){
			flag = true;
		}
		
		return flag;
	}
	
	/**是否跳转操作页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeModify(RecvMainParamVo params){
		boolean flag = false;
		String steplabel = params.getProcessParamValue("steplabel");
		if(RecvMainStepConstants.STEPNAME_MODIFY.equals(steplabel)){
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
	public static String getTypeByInfo(RecvMainParamVo params){
		String type = "detail";
		
		if(isTypeUpdate(params)) type = "update";
		if(isTypeModify(params)) type = "modify";
		
//log.debug("type="+type);
		
		return type;
	}
}
