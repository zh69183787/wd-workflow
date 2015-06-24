/**   
* @Title: KpiControlAction.java 
* @Package com.wonders.contract.workflow.kpi 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年8月8日 上午9:44:34 
* @version V1.0   
*/
package com.wonders.contract.workflow.kpi.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wonders.contract.workflow.common.action.AbstractParamAction;
import com.wonders.contract.workflow.kpi.service.KpiControlService;
import com.wonders.contract.workflow.model.vo.ParamVo;

/** 
 * @ClassName: KpiControlAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年8月8日 上午9:44:34 
 *  
 */

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contract-kpi")
@Controller("contract-kpiControlAction")
@Scope("prototype")
public class KpiControlAction extends AbstractParamAction{
	
	private Map<String,String> kpiMap;
	private List<String> kpiKey;
	private String kpiControl;
	private KpiControlService service;
	
	@Action(value="kpiControl",results={
			@Result(name="success",location="/contract/kpi/kpi.jsp")
	})
	public String serialRounds(){
		this.service.contructKPIKey(kpiControl);
		this.service.contructKPIMap();
		kpiKey = this.service.getKpiKey();
		kpiMap = this.service.getKpiMap();
		return "success";
	}
	
	/** 
	* @Title: getParams 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public ParamVo getParams() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getKpiControl() {
		return kpiControl;
	}


	public void setKpiControl(String kpiControl) {
		this.kpiControl = kpiControl;
	}

	public KpiControlService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("contract-kpiControlService")KpiControlService service) {
		this.service = service;
	}

	public Map<String, String> getKpiMap() {
		return kpiMap;
	}

	public void setKpiMap(Map<String, String> kpiMap) {
		this.kpiMap = kpiMap;
	}

	public List<String> getKpiKey() {
		return kpiKey;
	}

	public void setKpiKey(List<String> kpiKey) {
		this.kpiKey = kpiKey;
	}

	
	
}
