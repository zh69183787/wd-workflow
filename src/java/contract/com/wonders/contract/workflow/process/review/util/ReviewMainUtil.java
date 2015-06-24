/**
 * 
 */
package com.wonders.contract.workflow.process.review.util;

import com.wonders.contract.workflow.process.review.constants.ReviewMainStepConstants;
import com.wonders.contract.workflow.process.review.model.vo.ReviewMainParamVo;

/** 
 * @ClassName: ReviewMainUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:37:52 
 *  
 */
public class ReviewMainUtil {
	
	/**是否跳转操作页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeModify(ReviewMainParamVo params){
		boolean flag = false;
		String steplabel = params.getProcessParamValue("steplabel");
		if(ReviewMainStepConstants.STEPNAME_MODIFY.equals(steplabel)){
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
	public static String getTypeByInfo(ReviewMainParamVo params){
		String type = "detail";
		if(isTypeModify(params)) type = "modify";
		
//log.debug("type="+type);
		
		return type;
	}
}
