/**
 * 
 */
package com.wonders.receive.workflow.process.redv.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.receive.workflow.common.model.vo.UserInfo;
import com.wonders.receive.workflow.common.service.CommonService;
import com.wonders.receive.workflow.common.util.LoginUtil;
import com.wonders.receive.workflow.process.redv.constants.RedvMainStepConstants;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainParamVo;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainVo;
import com.wonders.receive.workflow.process.redv.service.RedvMainApprovedService;
import com.wonders.receive.workflow.process.redv.util.RedvMainUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: RedvMainApprovedServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:53:14 
 *  
 */
@Service("receive-redvMainApprovedService")
@Scope("prototype")
public class RedvMainApprovedServiceImpl implements RedvMainApprovedService{
	private CommonService service;
	@Override
	public ApprovedInfoBo saveApprovedInfo(RedvMainParamVo params){
		ApprovedInfoBo tai = new ApprovedInfoBo();
		UserInfo userInfo = params.userInfo;
		RedvMainVo vo = params.vo;
		
		String optionCode = RedvMainUtil.getOptionCode(vo);	
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");	
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());	
		//String choice = StringUtil.getNotNullValueString(vo.getChoice());
		
		if(RedvMainStepConstants.STEPNAME_REGISTER.equals(steplabel)){
			tai.setDept(userInfo.getDeptName());
			tai.setDeptId(userInfo.getDeptId());
			tai.setStepname(steplabel);
			tai.setUsername(taskUserLoginName);
			tai.setUserfullname(userInfo.getUserName());	
			tai.setIncidentno(Long.parseLong(pincident));
			tai.setProcess(pname);
			tai.setUpddate(new Date());
			tai.setRemark("");
			tai.setStatus(2L);
		}else if(RedvMainStepConstants.STEPNAME_LEADER_CHECK.equals(steplabel)){
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
			tai.setOptionCode(optionCode);
			tai.setUserfullname(userInfo.getUserName());	
		}else if(RedvMainStepConstants.STEPNAME_MODIFY.equals(steplabel)){
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
			tai.setOptionCode(optionCode);
			tai.setUserfullname(userInfo.getUserName());	
		}else if(RedvMainStepConstants.STEPNAME_RECORD.equals(steplabel)){
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
		}
		
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
