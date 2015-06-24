/**
 * 
 */
package com.wonders.discipline.workflow.process.dept.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.discipline.workflow.common.model.vo.UserInfo;
import com.wonders.discipline.workflow.common.service.CommonService;
import com.wonders.discipline.workflow.common.util.LoginUtil;
import com.wonders.discipline.workflow.process.dept.constants.DeptSubConstants;
import com.wonders.discipline.workflow.process.dept.constants.DeptSubStepConstants;
import com.wonders.discipline.workflow.process.dept.model.vo.DeptSubParamVo;
import com.wonders.discipline.workflow.process.dept.model.vo.DeptSubVo;
import com.wonders.discipline.workflow.process.dept.service.DeptSubApprovedService;
import com.wonders.discipline.workflow.process.dept.util.DeptSubUtil;
import com.wonders.util.StringUtil;



/** 
 * @ClassName: DeptSubApprovedServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:38:13 
 *  
 */
@Service("discipline-deptSubApprovedService")
@Scope("prototype")
public class DeptSubApprovedServiceImpl implements DeptSubApprovedService{
	private CommonService service;
	
	@Override
	public ApprovedInfoBo saveApprovedInfo(DeptSubParamVo params) {
		ApprovedInfoBo tai = new ApprovedInfoBo();
		UserInfo userInfo = params.userInfo;
		DeptSubVo vo = params.vo;
		
		String optionCode = DeptSubUtil.getOptionCode(vo);	
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		//String cname = params.getProcessParamValue("cname");
		//String cincident = params.getProcessParamValue("cincident");
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");	
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());
		
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		//String choice2 = StringUtil.getNotNullValueString(vo.getChoice2());
		
		if(DeptSubStepConstants.STEPNAME_DISPATCHER.equals(steplabel)){
			if(DeptSubConstants.CHOICE_DISPATCHER_DEAL.equals(choice)){
				tai.setProcess(pname);
				tai.setIncidentno(Long.valueOf(pincident));
				tai.setDept(userInfo.getDeptName());
				tai.setDeptId(userInfo.getDeptId());
				tai.setStepname(steplabel);
				tai.setUsername(taskUserLoginName);
				tai.setUserfullname(userInfo.getUserName());
				tai.setUpddate(new Date());
				tai.setAgree(1L);
				tai.setDisagree(0L);
				tai.setReturned(2L);
				tai.setStatus(1L);
				tai.setFllowFlag("0");
				tai.setReadFlag("1");
				tai.setRemark(suggest);
				tai.setOptionCode(optionCode);
				tai.setFileGroupId(fileGroupId);
			}else if(DeptSubConstants.CHOICE_DISPATCHER_SEND.equals(choice)){
				tai.setProcess(pname);
				tai.setIncidentno(Long.valueOf(pincident));
				tai.setDept(userInfo.getDeptName());
				tai.setDeptId(userInfo.getDeptId());
				tai.setStepname(steplabel);
				tai.setUsername(taskUserLoginName);
				tai.setUserfullname(userInfo.getUserName());
				tai.setUpddate(new Date());
				tai.setAgree(1L);
				tai.setDisagree(0L);
				tai.setReturned(2L);
				tai.setStatus(1L);
				tai.setFllowFlag("0");
				tai.setReadFlag("1");
				tai.setRemark(suggest);//录入意见或者备注			
				tai.setOptionCode(optionCode);
			}
		}else if(DeptSubStepConstants.STEPNAME_DEAL.endsWith(steplabel)){
			if(DeptSubConstants.CHOICE_DEAL_HAS_SUGGEST.equals(choice)){
				tai.setFileGroupId(fileGroupId);
				tai.setProcess(pname);
				tai.setIncidentno(Long.valueOf(pincident));
				tai.setDept(userInfo.getDeptName());
				tai.setDeptId(userInfo.getDeptId());
				tai.setStepname(steplabel);
				tai.setUsername(taskUserLoginName);
				tai.setUserfullname(userInfo.getUserName());
				tai.setUpddate(new Date());
				tai.setAgree(1L);
				tai.setDisagree(0L);
				tai.setReturned(2L);
				tai.setStatus(1L);
				tai.setFllowFlag("0");
				tai.setReadFlag("1");
				tai.setRemark(suggest);
				tai.setOptionCode(optionCode);
			}else if(DeptSubConstants.CHOICE_DEAL_NO_SUGGEST.equals(choice)){
				tai.setFileGroupId(fileGroupId);
				tai.setProcess(pname);
				tai.setIncidentno(Long.valueOf(pincident));
				tai.setDept(userInfo.getDeptName());
				tai.setDeptId(userInfo.getDeptId());
				tai.setStepname(steplabel);
				tai.setUsername(taskUserLoginName);
				tai.setUserfullname(userInfo.getUserName());
				tai.setUpddate(new Date());
				tai.setAgree(1L);
				tai.setDisagree(0L);
				tai.setReturned(2L);
				tai.setStatus(1L);
				tai.setFllowFlag("0");
				tai.setReadFlag("1");
				tai.setRemark(suggest);
				tai.setOptionCode(optionCode);
			}else if(DeptSubConstants.CHOICE_DEAL_NOT_MY_BUSINESS.equals(choice)){
				tai.setFileGroupId(fileGroupId);
				tai.setProcess(pname);
				tai.setIncidentno(Long.valueOf(pincident));
				tai.setDept(userInfo.getDeptName());
				tai.setDeptId(userInfo.getDeptId());
				tai.setStepname(steplabel);
				tai.setUsername(taskUserLoginName);
				tai.setUserfullname(userInfo.getUserName());
				tai.setUpddate(new Date());
				tai.setAgree(1L);
				tai.setDisagree(0L);
				tai.setReturned(2L);
				tai.setStatus(1L);
				tai.setFllowFlag("0");
				tai.setReadFlag("1");
				tai.setRemark(suggest);
				tai.setOptionCode(optionCode);
			}
		}else if(DeptSubStepConstants.STEPNAME_LEADER.equals(steplabel)){
			if(DeptSubConstants.CHOICE_LEADER_FAILED.equals(choice)){
				tai.setFileGroupId(fileGroupId);
				tai.setProcess(pname);
				tai.setIncidentno(Long.valueOf(pincident));
				tai.setDept(userInfo.getDeptName());
				tai.setDeptId(userInfo.getDeptId());
				tai.setStepname(steplabel);
				tai.setUsername(taskUserLoginName);
				tai.setUserfullname(userInfo.getUserName());
				tai.setUpddate(new Date());
				tai.setAgree(1L);
				tai.setDisagree(0L);
				tai.setReturned(2L);
				tai.setStatus(1L);
				tai.setFllowFlag("0");
				tai.setReadFlag("1");
				tai.setRemark(suggest);
				tai.setOptionCode(optionCode);
			}else if(DeptSubConstants.CHOICE_LEADER_PASS.equals(choice)){
				tai.setFileGroupId(fileGroupId);
				tai.setProcess(pname);
				tai.setIncidentno(Long.valueOf(pincident));
				tai.setDept(userInfo.getDeptName());
				tai.setDeptId(userInfo.getDeptId());
				tai.setStepname(steplabel);
				tai.setUsername(taskUserLoginName);
				tai.setUserfullname(userInfo.getUserName());
				tai.setUpddate(new Date());
				tai.setAgree(1L);
				tai.setDisagree(0L);
				tai.setReturned(2L);
				tai.setStatus(1L);
				tai.setFllowFlag("0");
				tai.setReadFlag("1");
				tai.setRemark(suggest);
				tai.setOptionCode(optionCode);
			}else if(DeptSubConstants.CHOICE_LEADER_CONTINUE.equals(choice)){
				tai.setFileGroupId(fileGroupId);
				tai.setProcess(pname);
				tai.setIncidentno(Long.valueOf(pincident));
				tai.setDept(userInfo.getDeptName());
				tai.setDeptId(userInfo.getDeptId());
				tai.setStepname(steplabel);
				tai.setUsername(taskUserLoginName);
				tai.setUserfullname(userInfo.getUserName());
				tai.setUpddate(new Date());
				tai.setAgree(1L);
				tai.setDisagree(0L);
				tai.setReturned(2L);
				tai.setStatus(1L);
				tai.setFllowFlag("0");
				tai.setReadFlag("1");
				tai.setRemark(suggest);
				tai.setOptionCode(optionCode);
			}
		}
		
		
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
