/**
 * 
 */
package com.wonders.receive.workflow.process.dept.service;

import com.wonders.receive.workflow.process.dept.model.vo.DeptSubParamVo;


/** 
 * @ClassName: RecvMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface DeptSubCheckService {
	public void init(DeptSubParamVo params);
	
	public void checkDispatcher();
	public void checkDeal();
	public void checkLeaderDeal();

	
}
