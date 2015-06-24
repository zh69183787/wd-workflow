/**
 * 
 */
package com.wonders.dept.workflow.process.pass.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.dept.workflow.common.model.vo.UserInfo;
import com.wonders.dept.workflow.common.service.CommonService;
import com.wonders.dept.workflow.common.util.LoginUtil;
import com.wonders.dept.workflow.process.pass.model.vo.PassMainParamVo;
import com.wonders.dept.workflow.process.pass.model.vo.PassMainVo;
import com.wonders.dept.workflow.process.pass.service.PassMainApprovedService;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: PassMainApprovedServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:53:14 
 *  
 */
@Service("dept-passMainApprovedService")
@Scope("prototype")
public class PassMainApprovedServiceImpl implements PassMainApprovedService{
	private CommonService service;
	@Override
	public ApprovedInfoBo saveApprovedInfo(PassMainParamVo params){
		ApprovedInfoBo tai = new ApprovedInfoBo();
		UserInfo userInfo = params.userInfo;
		PassMainVo vo = params.vo;
		
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");	
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());	
		//String choice = StringUtil.getNotNullValueString(vo.getChoice());
		
		tai.setFileGroupId(fileGroupId);
		tai.setDept(userInfo.getDeptName());
		tai.setIncidentno(Long.parseLong(pincident));
		tai.setProcess(pname);
		tai.setRemark(suggest);
		tai.setDeptId(userInfo.getDeptId());
		tai.setStepname(steplabel);
		tai.setUsername(taskUserLoginName);
		tai.setUpddate(new Date());
		tai.setStatus(1L);
		tai.setUserfullname(userInfo.getUserName());	
		
		service.save(tai);
		
		return tai;
	}
	

	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="dept-commonService")CommonService service) {
		this.service = service;
	}

}
