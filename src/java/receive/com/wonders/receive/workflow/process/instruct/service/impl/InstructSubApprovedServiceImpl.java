/**
 * 
 */
package com.wonders.receive.workflow.process.instruct.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.receive.workflow.common.model.vo.UserInfo;
import com.wonders.receive.workflow.common.service.CommonService;
import com.wonders.receive.workflow.common.util.LoginUtil;
import com.wonders.receive.workflow.constants.CommonConstants;
import com.wonders.receive.workflow.process.instruct.constants.InstructSubConstants;
import com.wonders.receive.workflow.process.instruct.constants.InstructSubStepConstants;
import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubParamVo;
import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubVo;
import com.wonders.receive.workflow.process.instruct.service.InstructSubApprovedService;
import com.wonders.receive.workflow.process.instruct.util.InstructSubUtil;
import com.wonders.receive.workflow.process.util.ProcessUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: InstructSubApprovedServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-3-18 上午09:32:13 
 *  
 */
@Service("receive-instructSubApprovedService")
@Scope("prototype")
public class InstructSubApprovedServiceImpl implements InstructSubApprovedService{
	private CommonService service;
	
	@Override
	public ApprovedInfoBo saveApprovedInfo(InstructSubParamVo params) {
		ApprovedInfoBo tai = new ApprovedInfoBo();
		UserInfo userInfo = params.userInfo;
		InstructSubVo vo = params.vo;
		
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());
		String type = StringUtil.getNotNullValueString(vo.getType());
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		String optionCode = StringUtil.getNotNullValueString(InstructSubUtil.getOptionCode(vo));
		Long disAgree = InstructSubUtil.getDisAgree(vo);
		String sign = StringUtil.getNotNullValueString(InstructSubUtil.getSign(vo));
		String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		if(InstructSubStepConstants.STEPNAME_SECRETARY_DEAL.equals(steplabel) &&
				InstructSubConstants.SELF_DEAL.equals(type)	&&
				!(InstructSubConstants.CHOICE_SECRETARY_BACK_OFFICE.equals(choice))){
			suggest = StringUtil.getNotNullValueString(suggest);
		}else{
			suggest = StringUtil.getNotNullValueString(InstructSubUtil.getSign(vo)+suggest);
		}
		
		if(InstructSubStepConstants.STEPNAME_SECRETARY_DEAL.equals(steplabel)){
			tai.setFileGroupId(fileGroupId);
			tai.setProcess(cname);
			tai.setIncidentno(Long.parseLong(cincident));
			tai.setDeptId(userInfo.getDeptId());
			tai.setDept(userInfo.getDeptName());
			tai.setStepname(steplabel);
			tai.setUsername(taskUserLoginName);
			tai.setUserfullname(userInfo.getUserName());
			tai.setUpddate(new Date());	
			tai.setAgree(1L);
			tai.setDisagree(disAgree);
			tai.setReturned(2L);
			tai.setStatus(1L);
			tai.setFllowFlag("0");
			tai.setReadFlag("1");
			tai.setSign(sign);
			tai.setRemark(suggest);	
		
			if(InstructSubConstants.CHOICE_SECRETARY_BACK_OFFICE.equals(choice)){
				tai.setOptionCode(optionCode);
				tai.setProcess(pname);
				tai.setIncidentno(Long.parseLong(pincident));
				tai.setRemark(suggest);
				
				String info = ProcessUtil.getLeaderInfo(cname, cincident);
				if(info.length() > 0 && (info.split(",").length>0)){
					taskUserLoginName = CommonConstants.LOGINNAME_PREFIX + info.split(",")[0];
				}	
				String steplabelTemp = ProcessUtil.getStepInfo(taskUserLoginName, cname, cincident);
				
				ApprovedInfoBo tai2 = new ApprovedInfoBo();
				tai2.setFileGroupId(fileGroupId);
				tai2.setProcess(pname);
				tai2.setIncidentno(Long.parseLong(pincident));
				tai2.setDeptId(userInfo.getDeptId());
				tai2.setDept(userInfo.getDeptName());
				tai2.setStepname(steplabelTemp);
				tai2.setUpddate(new Date());	
				tai2.setUsername(taskUserLoginName);
				tai2.setUserfullname(userInfo.getUserName());
				tai2.setAgree(1L);
				tai2.setDisagree(disAgree);
				tai2.setOptionCode(optionCode);
				tai2.setReturned(2L);
				tai2.setStatus(2L);
				tai2.setFllowFlag("0");
				tai2.setReadFlag("1");
				tai2.setSign(sign);
				tai2.setRemark(suggest);	
				service.save(tai);
				service.save(tai2);
			}else if(InstructSubConstants.INSTEAD_DEAL.equals(type)){
				//领导工号
				String leaderId = "";
				String leaderName = "";
				String info = ProcessUtil.getLeaderInfo(cname, cincident);
				if(info.length() > 0 && (info.split(",").length>0)){
					leaderId = info.split(",")[0];
					leaderName = info.split(",")[1];
				}
				steplabel = ProcessUtil.getStepInfo(CommonConstants.LOGINNAME_PREFIX+leaderId, cname, cincident);
				
				tai.setFileGroupId(fileGroupId);
				tai.setProcess(pname);
				tai.setIncidentno(Long.parseLong(pincident));
				tai.setDeptId(userInfo.getDeptId());
				tai.setDept(userInfo.getDeptName());
				tai.setStepname(steplabel);
				tai.setUsername(CommonConstants.LOGINNAME_PREFIX+leaderId);
				tai.setUserfullname(leaderName + "（办公室代为处理）");
				tai.setUpddate(new Date());	
				tai.setAgree(1L);
				tai.setDisagree(disAgree);
				tai.setOptionCode(optionCode);
				tai.setReturned(2L);
				tai.setStatus(1L);
				tai.setFllowFlag("0");
				tai.setReadFlag("1");
				tai.setSign(sign);
				tai.setRemark(suggest);	
				service.save(tai);
			}
		}else if(InstructSubStepConstants.STEPNAME_LEADER_DEAL.equals(steplabel)){
			tai.setFileGroupId(fileGroupId);
			tai.setProcess(pname);
			tai.setIncidentno(Long.parseLong(pincident));
			tai.setDeptId(userInfo.getDeptId());
			tai.setDept(userInfo.getDeptName());
			tai.setStepname(steplabel);
			tai.setUsername(taskUserLoginName);
			tai.setUserfullname(userInfo.getUserName());
			tai.setUpddate(new Date());	
			tai.setAgree(1L);
			tai.setDisagree(disAgree);
			tai.setOptionCode(optionCode);
			tai.setReturned(2L);
			tai.setStatus(1L);
			tai.setFllowFlag("0");
			tai.setReadFlag("1");
			tai.setSign(sign);
			tai.setRemark(suggest);	
			service.save(tai);
		}
		
		
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
