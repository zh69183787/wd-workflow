/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.discipline.workflow.common.model.vo.UserInfo;
import com.wonders.discipline.workflow.common.service.CommonService;
import com.wonders.discipline.workflow.common.util.LoginUtil;
import com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainConstants;
import com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainStepConstants;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainParamVo;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainVo;
import com.wonders.discipline.workflow.process.recv.service.DcpRecvMainApprovedService;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: DcpRecvMainApprovedServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:53:14 
 *  
 */
@Service("discipline-dcpRecvMainApprovedService")
@Scope("prototype")
public class DcpRecvMainApprovedServiceImpl implements DcpRecvMainApprovedService{
	private CommonService service;
	@Override
	public ApprovedInfoBo saveApprovedInfo(DcpRecvMainParamVo params){
		ApprovedInfoBo tai = new ApprovedInfoBo();
		UserInfo userInfo = params.userInfo;
		DcpRecvMainVo vo = params.vo;
		
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");	
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());	
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		
		tai.setFileGroupId(fileGroupId);
		tai.setDept(userInfo.getDeptName());
		tai.setIncidentno(Long.parseLong(pincident));
		tai.setProcess(pname);
		tai.setRemark(suggest);
		tai.setDeptId(userInfo.getDeptId());
		if(DcpRecvMainStepConstants.STEPNAME_DEAL_FINISH.equals(steplabel)&&
			(DcpRecvMainConstants.CHOICE_DEAL_FINISH_RETURN_SUMMARY_PERSON.equals(choice)||
				DcpRecvMainConstants.CHOICE_DEAL_FINISH_SUMBIT_DEPT.equals(choice))	){
			tai.setStepname(DcpRecvMainStepConstants.STEPNAME_SUMMARY_PERSON+steplabel);
		}else{
			tai.setStepname(steplabel);
		}
		tai.setUsername(taskUserLoginName);
		tai.setUpddate(new Date());
		if(DcpRecvMainStepConstants.STEPNAME_REGISTER.equals(steplabel)||
			DcpRecvMainStepConstants.STEPNAME_RETURN_MODIFY.equals(steplabel)||
			(DcpRecvMainStepConstants.STEPNAME_DEPT_LEADER.equals(steplabel)&&
				DcpRecvMainConstants.CHOICE_DEPT_LEADER_BACK.equals(choice))){
			tai.setStatus(2L);
		}else{
			tai.setStatus(1L);
		}
		
		tai.setUserfullname(userInfo.getUserName());	
		
		service.save(tai);
		
		return tai;
	}
	

	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="discipline-commonService")CommonService service) {
		this.service = service;
	}

}
