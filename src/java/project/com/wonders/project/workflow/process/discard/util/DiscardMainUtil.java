/**
 * 
 */
package com.wonders.project.workflow.process.discard.util;

import com.wonders.project.workflow.process.discard.constants.DiscardMainStepConstants;
import com.wonders.project.workflow.process.discard.model.vo.DiscardMainParamVo;

/** 
 * @ClassName: DiscardMainUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:37:52 
 *  
 */
public class DiscardMainUtil {
	
	/**是否跳转更新页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeUpdate(DiscardMainParamVo params){
		boolean flag = false;
		String steplabel = params.getProcessParamValue("steplabel");
/*		if(DiscardMainStepConstants.STEPNAME_DEPT_LEADER.equals(steplabel)){
			flag = true;
		}*/
		
		return flag;
	}
	
	/**是否跳转操作页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeModify(DiscardMainParamVo params){
		boolean flag = false;
		String steplabel = params.getProcessParamValue("steplabel");
		if(DiscardMainStepConstants.STEPNAME_RETURN_MODIFY.equals(steplabel)){
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
	public static String getTypeByInfo(DiscardMainParamVo params){
		String type = "detail";
		
		if(isTypeModify(params)) type = "modify";
		
//log.debug("type="+type);
		
		return type;
	}
}
