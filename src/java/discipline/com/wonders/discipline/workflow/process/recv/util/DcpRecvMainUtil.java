/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.util;

import com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainStepConstants;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainParamVo;

/** 
 * @ClassName: RecvMainUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:37:52 
 *  
 */
public class DcpRecvMainUtil {
	
	/**是否跳转更新页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeUpdate(DcpRecvMainParamVo params){
		boolean flag = false;
		String steplabel = params.getProcessParamValue("steplabel");
		if(DcpRecvMainStepConstants.STEPNAME_DEPT_LEADER.equals(steplabel)){
			flag = true;
		}
		
		return flag;
	}
	
	/**是否跳转操作页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeModify(DcpRecvMainParamVo params){
		boolean flag = false;
		String steplabel = params.getProcessParamValue("steplabel");
		if(DcpRecvMainStepConstants.STEPNAME_RETURN_MODIFY.equals(steplabel)){
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
	public static String getTypeByInfo(DcpRecvMainParamVo params){
		String type = "detail";
		
		if(isTypeUpdate(params)) type = "update";
		if(isTypeModify(params)) type = "modify";
		
//log.debug("type="+type);
		
		return type;
	}
}
