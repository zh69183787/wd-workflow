package com.wonders.contact.deptSubProcess.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.contact.approvedInfo.model.bo.TApprovedinfo;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.service.CommonService;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptSubProcess.constant.DeptSubProcessConstants;
import com.wonders.contact.deptSubProcess.constant.DeptSubProcessFlowConstants;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessParamVo;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessVo;
import com.wonders.contact.deptSubProcess.service.DeptSubProcessCommonService;
import com.wonders.util.StringUtil;

/**
 * @author XFZ
 * @version 1.0 2012-6-11
 */
@Service("contact-deptSubProcessCommonService")
@Scope("prototype")
public class DeptSubProcessCommonServiceImpl implements	DeptSubProcessCommonService {
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	private CommonService service;
	
	@Override
	public TApprovedinfo saveApprovedInfo(DeptSubProcessParamVo params) {
		TApprovedinfo tai = new TApprovedinfo();
		UserInfo userInfo = params.userInfo;
		DeptSubProcessVo vo = params.vo;
		
		String optionCode = "";
		
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String processName = params.getProcessParamValue("cname");
		String incidentNo = params.getProcessParamValue("cincident");
		
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());
		
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String choice2 = StringUtil.getNotNullValueString(vo.getChoice2());
		
		if(DeptSubProcessFlowConstants.STEPNAME_DISPATCHER.equals(steplabel)){
			if(DeptSubProcessConstants.CHOICE_DISPATCHER_SEND.equals(choice)){
				optionCode = "DISPATCHER_SEND";
			}	
			if(DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL.equals(choice)){
				if(DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL_NO_SUGGEST.equals(choice2)){
					optionCode = "DISPATCHER_DEAL_NO_SUGGEST";
				}
				if(DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL_HAS_SUGGEST.equals(choice2)){
					optionCode = "DISPATCHER_DEAL_HAS_SUGGEST";
				}
			}
		}
		
		if(DeptSubProcessFlowConstants.STEPNAME_DEAL.equals(steplabel)){
			if(DeptSubProcessConstants.CHOICE_DEAL_NO_SUGGEST.equals(choice)){
				optionCode = "DEAL_NO_SUGGEST";
			}	
			if(DeptSubProcessConstants.CHOICE_DEAL_HAS_SUGGEST.equals(choice)){
				optionCode = "DEAL_HAS_SUGGEST";
			}
			if(DeptSubProcessConstants.CHOICE_DEAL_NOT_MY_BUSINESS.equals(choice)){
				optionCode = "DEAL_NOT_MY_BUSINESS";
			}
		}
		
		if(DeptSubProcessFlowConstants.STEPNAME_LEADER.equals(steplabel)){
			if(DeptSubProcessConstants.CHOICE_LEADER_PASS.equals(choice)){
				optionCode = "LEADER_PASS";
				tai.setAgree(1);
			}	
			if(DeptSubProcessConstants.CHOICE_LEADER_FAILED.equals(choice)){
				optionCode = "LEADER_FAILED";
				tai.setDisagree(1);
			}
			if(DeptSubProcessConstants.CHOICE_LEADER_CONTINUE.equals(choice)){
				if(DeptSubProcessConstants.CHOICE_LEADER_CONTINUE_DEAL.equals(choice2)){
					optionCode = "LEADER_CONTINUE_DEAL";
					tai.setReturned(1);
				}
				if(DeptSubProcessConstants.CHOICE_LEADER_CONTINUE_CHOICE_PERSON.equals(choice2)){
					optionCode = "LEADER_CONTINUE_CHOICE_PERSON";
					tai.setReturned(1);
				}
			}
		}
		
		tai.setDeptId(userInfo.getDeptId());
		tai.setDept(userInfo.getDeptName());
		tai.setUsername(taskUserLoginName);
		tai.setUserfullname(userInfo.getUserName());
		
		tai.setUpddate(new Date());
		
		tai.setProcess(processName);
		tai.setIncidentno(Long.parseLong(incidentNo));
		
		tai.setStepname(steplabel);
		
		tai.setFileGroupId(fileGroupId);
		
		tai.setRemark(suggest);
		tai.setStatus(1);
		
		String flowTypeStr = vo.getFlowType().length()>0?":"+vo.getFlowType():"";
		
		tai.setOptionCode(optionCode+flowTypeStr);
		
		service.save(tai);
		
//log.debug("添加意见:id="+tai.getGuid());
		
		return tai;
	}
	
	
	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="contact-commonService")CommonService service) {
		this.service = service;
	}
}
