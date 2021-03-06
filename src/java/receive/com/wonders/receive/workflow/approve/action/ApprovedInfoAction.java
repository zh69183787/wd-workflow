/**
 * 
 */
package com.wonders.receive.workflow.approve.action;

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

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.receive.workflow.approve.service.ApprovedInfoService;
import com.wonders.receive.workflow.common.action.AbstractParamAction;
import com.wonders.receive.workflow.model.vo.ParamVo;

/** 
 * @ClassName: ApprovedInfoAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-1 上午9:21:38 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/receive-approve")
@Controller("receive-approvedInfoAction")
@Scope("prototype")
public class ApprovedInfoAction extends AbstractParamAction{
	private ApprovedInfoService service;
	private Map<Integer,List<ApprovedInfoBo>> appRounds;
	private Map<Integer,Map<String,List<ApprovedInfoBo>>> appDeptRounds;
	
	private Map<Integer,List<String>> deptRounds;
	private Map<String,String> deptMap;
	private String pname;
	private String pincident;
	private String[] stepname;
	

	@Action(value="serial",results={
			@Result(name="success",location="/receive/approve/serial.jsp")
	})
	public String serialRounds(){
		this.service.queryByRounds(pname, pincident, stepname);
		appRounds = this.service.getAppRounds();
		return "success";
	}
	
	@Action(value="simulate",results={
			@Result(name="success",location="/receive/approve/simulate.jsp")
	})
	public String simulate(){
		this.service.queryByRounds(pname, pincident, stepname);
		appRounds = this.service.getAppRounds();
		return "success";
	}
	
	
	@Action(value="simulateRounds",results={
			@Result(name="success",location="/receive/approve/simulateRounds.jsp")
	})
	public String simulateRounds(){
		this.service.queryByRounds(pname, pincident, stepname);
		appRounds = this.service.getAppRounds();
		return "success";
	}
	
	@Action(value="leaderRounds",results={
			@Result(name="success",location="/receive/approve/leaderRounds.jsp")
	})
	public String leaderRounds(){
		this.service.queryByRounds(pname, pincident, stepname);
		appRounds = this.service.getAppRounds();
		return "success";
	}
	
	@Action(value="leader",results={
			@Result(name="success",location="/receive/approve/leader.jsp")
	})
	public String leader(){
		this.service.queryByRounds(pname, pincident, stepname);
		appRounds = this.service.getAppRounds();
		return "success";
	}
	
	@Action(value="deptRounds",results={
			@Result(name="success",location="/receive/approve/deptRounds.jsp")
	})
	public String deptRounds(){
		this.service.queryByDeptRounds(pname, pincident, stepname);
		appDeptRounds = this.service.getAppDeptRounds();
		deptRounds = this.service.getDeptRounds();
		deptMap = this.service.getDeptMap();
		return "success";
	}
	
	@Action(value="dept",results={
			@Result(name="success",location="/receive/approve/dept.jsp")
	})
	public String dept(){
		this.service.queryByDeptRounds(pname, pincident, stepname);
		appDeptRounds = this.service.getAppDeptRounds();
		deptRounds = this.service.getDeptRounds();
		deptMap = this.service.getDeptMap();
		return "success";
	}
	
	@Action(value="finish",results={
			@Result(name="success",location="/receive/approve/finish.jsp")
	})
	public String finishRounds(){
		this.service.queryByRounds(pname, pincident, stepname);
		appRounds = this.service.getAppRounds();
		return "success";
	}
	
	@Action(value="record",results={
			@Result(name="success",location="/receive/approve/record.jsp")
	})
	public String recordRounds(){
		this.service.queryByRounds(pname, pincident, stepname);
		appRounds = this.service.getAppRounds();
		return "success";
	}
	
	@Action(value="secretary",results={
			@Result(name="success",location="/receive/approve/secretary.jsp")
	})
	public String secretaryRounds(){
		this.service.queryByRounds(pname, pincident, stepname);
		appRounds = this.service.getAppRounds();
		return "success";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getPincident() {
		return pincident;
	}
	public void setPincident(String pincident) {
		this.pincident = pincident;
	}
	public String[] getStepname() {
		return stepname;
	}

	public void setStepname(String[] stepname) {
		this.stepname = stepname;
	}
	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}
	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}
/**
	 * @return the service
	 */
	public ApprovedInfoService getService() {
		return service;
	}
	/**
	 * @param service the service to set
	 */
	@Autowired(required=false)
	public void setService(@Qualifier("receive-approvedInfoService")ApprovedInfoService service) {
		this.service = service;
	}
	/**
	 * @return the appRounds
	 */
	public Map<Integer, List<ApprovedInfoBo>> getAppRounds() {
		return appRounds;
	}
	/**
	 * @param appRounds the appRounds to set
	 */
	public void setAppRounds(Map<Integer, List<ApprovedInfoBo>> appRounds) {
		this.appRounds = appRounds;
	}
	/**
	 * @return the appDeptRounds
	 */
	public Map<Integer, Map<String, List<ApprovedInfoBo>>> getAppDeptRounds() {
		return appDeptRounds;
	}
	/**
	 * @param appDeptRounds the appDeptRounds to set
	 */
	public void setAppDeptRounds(
			Map<Integer, Map<String, List<ApprovedInfoBo>>> appDeptRounds) {
		this.appDeptRounds = appDeptRounds;
	}

	/**
	 * @return the deptRounds
	 */
	public Map<Integer, List<String>> getDeptRounds() {
		return deptRounds;
	}

	/**
	 * @param deptRounds the deptRounds to set
	 */
	public void setDeptRounds(Map<Integer, List<String>> deptRounds) {
		this.deptRounds = deptRounds;
	}
/**
	 * @return the deptMap
	 */
	public Map<String, String> getDeptMap() {
		return deptMap;
	}
	/**
	 * @param deptMap the deptMap to set
	 */
	public void setDeptMap(Map<String, String> deptMap) {
		this.deptMap = deptMap;
	}

	@Override
	public ParamVo getParams() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
