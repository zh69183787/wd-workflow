/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.receive.workflow.common.model.vo.UserInfo;
import com.wonders.receive.workflow.common.service.CommonService;
import com.wonders.receive.workflow.common.util.LoginUtil;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubParamVo;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubVo;
import com.wonders.receive.workflow.process.simulate.service.SimulateSubApprovedService;
import com.wonders.receive.workflow.process.simulate.util.SimulateSubUtil;
import com.wonders.util.StringUtil;



/** 
 * @ClassName: SimulateSubApprovedServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:38:13 
 *  
 */
@Service("receive-simulateSubApprovedService")
@Scope("prototype")
public class SimulateSubApprovedServiceImpl implements SimulateSubApprovedService{
	private CommonService service;
	
	@Override
	public ApprovedInfoBo saveApprovedInfo(SimulateSubParamVo params) {
		ApprovedInfoBo tai = new ApprovedInfoBo();
		UserInfo userInfo = params.userInfo;
		SimulateSubVo vo = params.vo;
		
		String optionCode = SimulateSubUtil.getOptionCode(vo);	
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		//String cname = params.getProcessParamValue("cname");
		//String cincident = params.getProcessParamValue("cincident");
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");	
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());
		
		//String choice = StringUtil.getNotNullValueString(vo.getChoice());
		//String type = StringUtil.getNotNullValueString(vo.getType());
		
		tai.setFileGroupId(fileGroupId);
		tai.setDept(userInfo.getDeptName());
		tai.setDeptId(userInfo.getDeptId());
		tai.setStepname(steplabel);
		tai.setUsername(taskUserLoginName);
		tai.setUserfullname(userInfo.getUserName());
		tai.setIncidentno(Long.parseLong(pincident));
		tai.setProcess(pname);
		tai.setRemark(suggest);
		tai.setOptionCode(optionCode);
		tai.setUpddate(new Date());
		tai.setStatus(1L);
		
		
		
		service.save(tai);
		
		return tai;
	}
	

	
	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="receive-commonService")CommonService service) {
		this.service = service;
	}

	
}
