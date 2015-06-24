/**
 * 
 */
package com.wonders.contract.workflow.process.review.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.contract.workflow.common.model.vo.UserInfo;
import com.wonders.contract.workflow.common.service.CommonService;
import com.wonders.contract.workflow.common.util.LoginUtil;
import com.wonders.contract.workflow.process.review.constants.ReviewMainStepConstants;
import com.wonders.contract.workflow.process.review.model.bo.ReviewSuggestBo;
import com.wonders.contract.workflow.process.review.model.vo.ReviewMainParamVo;
import com.wonders.contract.workflow.process.review.model.vo.ReviewMainVo;
import com.wonders.contract.workflow.process.review.service.ReviewMainApprovedService;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: ReviewMainApprovedServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:53:14 
 *  
 */
@Service("contract-reviewMainApprovedService")
@Scope("prototype")
public class ReviewMainApprovedServiceImpl implements ReviewMainApprovedService{
	private CommonService service;
	@Override
	public ApprovedInfoBo saveApprovedInfo(ReviewMainParamVo params){
		
		
		ApprovedInfoBo tai = new ApprovedInfoBo();
		UserInfo userInfo = params.userInfo;
		ReviewMainVo vo = params.vo;
		
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");	
		String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
		String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
		String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());	
		//String choice = StringUtil.getNotNullValueString(vo.getChoice());
	
		
		if(ReviewMainStepConstants.STEPNAME_REGISTER.equals(steplabel)){
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
		}else{
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
		
		if(ReviewMainStepConstants.STEPNAME_CONTRACT_DEAL.equals(steplabel)){
			//结构化意见
			List<ReviewSuggestBo> suggestResult = new ArrayList<ReviewSuggestBo>();
			String structuredSuggest = StringUtil.getNotNullValueString(vo.getStructuredSuggest());
			Type listType = new TypeToken<ArrayList<Map<String,String>>>(){int a = 1;}.getType();
			Gson gson = new Gson();
			List<Map<String,String>> list = gson.fromJson(structuredSuggest, listType);
			if(list != null && list.size() > 0){
				for(Map<String,String> map : list){
					ReviewSuggestBo suggestBo = new ReviewSuggestBo();	
					suggestBo.setLoginName(taskUserLoginName);
					suggestBo.setUserName(userInfo.getUserName());
					suggestBo.setReviewId(vo.getId());
					suggestBo.setQuestionId(StringUtil.getNotNullValueString(map.get("q")));
					suggestBo.setOptionId(StringUtil.getNotNullValueString(map.get("o")));
					suggestResult.add(suggestBo);
				}
			}
			service.saveBatch(suggestResult);
		}
		
		
		service.save(tai);
		
		return tai;
	}
	

	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="contract-commonService")CommonService service) {
		this.service = service;
	}

}
