/**
 * 
 */
package com.wonders.dept.workflow.process.pass.util;

import com.wonders.dept.workflow.process.pass.model.vo.PassMainParamVo;

/** 
 * @ClassName: PassMainUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:37:52 
 *  
 */
public class PassMainUtil {
	
	/**是否跳转操作页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeModify(PassMainParamVo params){
		boolean flag = false;
		//String steplabel = params.getProcessParamValue("steplabel");
		//if(PassMainStepConstants.STEPNAME_DEAL_PERSON.equals(steplabel)){
			//flag = true;
		//}
		return flag;
	}
	
	/**根据步骤获得类型
	 * add:新增
	 * operate:业务处理
	 * update:修改&业务处理
	 * detail:查看
	 * @return
	 */
	public static String getTypeByInfo(PassMainParamVo params){
		String type = "detail";
		if(isTypeModify(params)) type = "modify";
		
//log.debug("type="+type);
		
		return type;
	}
}
