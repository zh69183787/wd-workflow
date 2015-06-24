/**
 * 
 */
package com.wonders.dataExchange.service;

import com.wonders.dataExchange.model.common.vo.MainParamVo;
/** 
 * @ClassName: DataExchangeService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月12日 下午1:28:15 
 *  
 */
public interface DataExchangeService {
	public void init(MainParamVo params);
	public void flowStepWorkflow(MainParamVo params);
	public void updateStatus(String id);
}
