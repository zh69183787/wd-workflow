/**
 * 
 */
package com.wonders.project.workflow.process.discard.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.wonders.project.workflow.process.discard.model.bo.DiscardAssetBo;
import com.wonders.receive.workflow.constants.CommonConstants;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: DiscardMainVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:33:43 
 *  
 */
public class DiscardMainVo {
	private String id;
	private String projectId;
	private String projectNo;
	private String projectName;
	private String dispatchNo;
	private String startDate;
	private String finishDate;
	private String finishPrice;
	private String approvalBudget;
	private String moneySource;	
	private String costType;		//成本属性
	private String attach;
	
	private String operator;
	private String operatorName;
	private String operatorMobile;

	private String remark;
	private String flag = "0";
	
	private String status;
	
	private List<DiscardAssetBo> assets = new ArrayList<DiscardAssetBo>();
	/*
	 * 部门领导
	 */
	private String deptLeader;
	
	/*
	 * 操作选项
	 * */
	private String choice = "";
	private String steplabel = "";
	private String suggest = "";
	private String attachId = "";
	
	//会签部门
	private String depsId = "";
	private String depsName = "";
	private String depRecvsId = "";
	private String depLdsId = "";
	private List<String> depsIdList = new ArrayList<String>();
	private List<String> depsNameList = new ArrayList<String>();
	private List<String> depRecvsIdList = new ArrayList<String>();
	private List<String> depLdsIdList = new ArrayList<String>();
	
	//投资部经办人
	private String dealPersonStr = "";
	private List<String> dealPersonList = new ArrayList<String>();
	//投资部领导
	private String dealLeaderStr = "";
	
	/**
	 * 初始化人员list和领导list
	 */
	public void initList(){
		switchList(depsId,depsIdList);
		switchList(depsName,depsNameList);
		switchList(depRecvsId,depRecvsIdList);
		switchList(depLdsId,depLdsIdList);
		
		switchList(dealPersonStr,dealPersonList);
	}
	
	
	public void switchList(String src , List<String> target){
		src = StringUtil.getNotNullValueString(src);

		String[] srcs = src.split(CommonConstants.SPLIT_EXP);
		
		for(int i=0;i<srcs.length;i++){
			String p = StringUtil.getNotNullValueString(srcs[i]);
			if(p.length()>0) target.add(p);
		}
		
		if(srcs.length == 0){
			//target.add("");
		}
		
	}
	
	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectNo() {
		return projectNo;
	}


	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	/**
	 * @return the choice
	 */
	public String getChoice() {
		return choice;
	}
	/**
	 * @param choice the choice to set
	 */
	public void setChoice(String choice) {
		this.choice = choice;
	}

	/**
	 * @return the steplabel
	 */
	public String getSteplabel() {
		return steplabel;
	}
	/**
	 * @param steplabel the steplabel to set
	 */
	public void setSteplabel(String steplabel) {
		this.steplabel = steplabel;
	}
	/**
	 * @return the suggest
	 */
	public String getSuggest() {
		return suggest;
	}
	/**
	 * @param suggest the suggest to set
	 */
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	/**
	 * @return the attach
	 */
	public String getAttach() {
		return attach;
	}
	/**
	 * @param attach the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDepsId() {
		return depsId;
	}

	public void setDepsId(String depsId) {
		this.depsId = depsId;
	}

	public String getDepsName() {
		return depsName;
	}

	public void setDepsName(String depsName) {
		this.depsName = depsName;
	}

	public String getDepRecvsId() {
		return depRecvsId;
	}

	public void setDepRecvsId(String depRecvsId) {
		this.depRecvsId = depRecvsId;
	}

	public String getDepLdsId() {
		return depLdsId;
	}

	public void setDepLdsId(String depLdsId) {
		this.depLdsId = depLdsId;
	}

	public List<String> getDepsIdList() {
		return depsIdList;
	}

	public void setDepsIdList(List<String> depsIdList) {
		this.depsIdList = depsIdList;
	}

	public List<String> getDepsNameList() {
		return depsNameList;
	}

	public void setDepsNameList(List<String> depsNameList) {
		this.depsNameList = depsNameList;
	}

	public List<String> getDepRecvsIdList() {
		return depRecvsIdList;
	}

	public void setDepRecvsIdList(List<String> depRecvsIdList) {
		this.depRecvsIdList = depRecvsIdList;
	}

	public List<String> getDepLdsIdList() {
		return depLdsIdList;
	}

	public void setDepLdsIdList(List<String> depLdsIdList) {
		this.depLdsIdList = depLdsIdList;
	}
	
	public String getDealPersonStr() {
		return dealPersonStr;
	}


	public void setDealPersonStr(String dealPersonStr) {
		this.dealPersonStr = dealPersonStr;
	}


	public String getDealLeaderStr() {
		return dealLeaderStr;
	}


	public void setDealLeaderStr(String dealLeaderStr) {
		this.dealLeaderStr = dealLeaderStr;
	}


	public List<String> getDealPersonList() {
		return dealPersonList;
	}


	public void setDealPersonList(List<String> dealPersonList) {
		this.dealPersonList = dealPersonList;
	}


	public String getDeptLeader() {
		return deptLeader;
	}


	public void setDeptLeader(String deptLeader) {
		this.deptLeader = deptLeader;
	}


	public String getDispatchNo() {
		return dispatchNo;
	}


	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getFinishDate() {
		return finishDate;
	}


	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}


	public String getFinishPrice() {
		return finishPrice;
	}


	public void setFinishPrice(String finishPrice) {
		this.finishPrice = finishPrice;
	}


	public String getApprovalBudget() {
		return approvalBudget;
	}


	public void setApprovalBudget(String approvalBudget) {
		this.approvalBudget = approvalBudget;
	}


	public String getMoneySource() {
		return moneySource;
	}


	public void setMoneySource(String moneySource) {
		this.moneySource = moneySource;
	}


	public String getOperatorMobile() {
		return operatorMobile;
	}


	public void setOperatorMobile(String operatorMobile) {
		this.operatorMobile = operatorMobile;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getOperatorName() {
		return operatorName;
	}


	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}


	public String getAttachId() {
		return attachId;
	}


	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}


	public List<DiscardAssetBo> getAssets() {
		return assets;
	}


	public void setAssets(List<DiscardAssetBo> assets) {
		this.assets = assets;
	}

	public double getAssetCostSum(){
		double oriVal = 0;
		if(assets != null && assets.size() > 0){
			for(DiscardAssetBo asset : assets){
				if("费用性".equals(asset.getCostType())){
					try{
						oriVal += Double.valueOf(asset.getOriginalValue());					
					}catch (NumberFormatException e) {}					
				}
			}
		}
		return oriVal;
	}
	
	public double getAssetCapitalSum(){
		double oriVal = 0;
		if(assets != null && assets.size() > 0){
			for(DiscardAssetBo asset : assets){
				if("资本性".equals(asset.getCostType())){
					try{
						oriVal += Double.valueOf(asset.getOriginalValue());					
					}catch (NumberFormatException e) {}					
				}
			}
		}
		return oriVal;
	}

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}
	
}
