/**   
* @Title: KpiControlService.java 
* @Package com.wonders.contract.workflow.kpi.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年8月8日 上午9:48:22 
* @version V1.0   
*/
package com.wonders.contract.workflow.kpi.service;

import java.util.List;
import java.util.Map;

/** 
 * @ClassName: KpiControlService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年8月8日 上午9:48:22 
 *  
 */
public interface KpiControlService {
	public void contructKPIKey(String kpiControl);
	
	public void contructKPIMap();
	
	public Map<String, String> getKpiMap();
	
	public List<String> getKpiKey();
}
