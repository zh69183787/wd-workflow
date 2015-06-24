/**
 * 
 */
package com.wonders.project.workflow.process.discard.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.project.workflow.common.model.vo.UserInfo;
import com.wonders.project.workflow.common.service.CommonService;
import com.wonders.project.workflow.common.util.LoginUtil;
import com.wonders.project.workflow.process.discard.constants.DiscardMainConstants;
import com.wonders.project.workflow.process.discard.constants.DiscardMainStepConstants;
import com.wonders.project.workflow.process.discard.model.vo.DiscardMainParamVo;
import com.wonders.project.workflow.process.discard.model.vo.DiscardMainVo;
import com.wonders.project.workflow.process.discard.service.DiscardMainApprovedService;
import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: DiscardMainApprovedServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:53:14 
 *  
 */
@Service("project-discardMainApprovedService")
@Scope("prototype")
public class DiscardMainApprovedServiceImpl implements DiscardMainApprovedService{
	private CommonService service;
	@Override
	public ApprovedInfoBo saveApprovedInfo(DiscardMainParamVo params){
		ApprovedInfoBo tai = new ApprovedInfoBo();
		UserInfo userInfo = params.userInfo;
		DiscardMainVo vo = params.vo;
		
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
		tai.setDept(userInfo.getDeptName());
		tai.setStepname(steplabel);
		tai.setUsername(taskUserLoginName);
		tai.setUserfullname(userInfo.getUserName());	
		tai.setUpddate(new Date());
		
		if(DiscardMainStepConstants.STEPNAME_REGISTER.equals(steplabel)){
			tai.setStatus(2L);
		}else{
			tai.setStatus(1L);
		}
		
		service.save(tai);
		
		return tai;
	}
	

	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="project-commonService")CommonService service) {
		this.service = service;
	}

}
