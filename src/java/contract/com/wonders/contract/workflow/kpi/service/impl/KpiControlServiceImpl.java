/**   
* @Title: KpiControlServiceImpl.java 
* @Package com.wonders.contract.workflow.kpi.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年8月8日 上午9:47:59 
* @version V1.0   
*/
package com.wonders.contract.workflow.kpi.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.contract.workflow.kpi.service.KpiControlService;
import com.wonders.contract.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: KpiControlServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年8月8日 上午9:47:59 
 *  
 */

@Scope("prototype")
@Service("contract-kpiControlService")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class KpiControlServiceImpl implements KpiControlService{
	private Map<String,String> kpiMap = new HashMap<String,String>();
	private List<String> kpiKey = new ArrayList<String>();
	
	public void contructKPIKey(String kpiControl){
		String[] temp = StringUtil.getNotNullValueString(kpiControl).split(",");
		kpiKey = Arrays.asList(temp);	
	}
	
	public void contructKPIMap(){
		List<Map<String,Object>> list = FlowUtil.getKPIInfo();
		for(Map<String,Object> map : list){
			kpiMap.put(StringUtil.getNotNullValueString(map.get("ID")),StringUtil.getNotNullValueString(map.get("NAME")));
		}
	}

	
	
	public Map<String, String> getKpiMap() {
		return kpiMap;
	}

	public List<String> getKpiKey() {
		return kpiKey;
	}

	
}
