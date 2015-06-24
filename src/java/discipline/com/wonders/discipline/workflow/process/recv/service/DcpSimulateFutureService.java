/**   
* @Title: DcpSimulateFutureService.java 
* @Package com.wonders.discipline.workflow.process.recv.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月28日 下午3:42:29 
* @version V1.0   
*/
package com.wonders.discipline.workflow.process.recv.service;

import com.wonders.discipline.workflow.process.recv.model.bo.DcpSimulateFutureBo;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainParamVo;

/** 
 * @ClassName: DcpSimulateFutureService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月28日 下午3:42:29 
 *  
 */
public interface DcpSimulateFutureService {
	public DcpSimulateFutureBo saveFutureInfo(DcpRecvMainParamVo params);
}
