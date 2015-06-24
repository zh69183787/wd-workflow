/**
 * 
 */
package com.wonders.dept.workflow.process.pass.service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.dept.workflow.process.pass.model.vo.PassMainParamVo;

/** 
 * @ClassName: PassMainApprovedService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:52:52 
 *  
 */
public interface PassMainApprovedService {
	public ApprovedInfoBo saveApprovedInfo(PassMainParamVo params);
}
