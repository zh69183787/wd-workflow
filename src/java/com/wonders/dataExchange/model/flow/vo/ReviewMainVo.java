/**
 * 
 */
package com.wonders.dataExchange.model.flow.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.wonders.dataExchange.constants.CommonConstants;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: ReviewMainVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:33:43 
 *  
 */

@XmlRootElement(name="ReviewMainVo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReviewMainVo {
	private String id;
	private String projectName;
	private String projectIdentifier;
	private String projectNum;
	private String projectCompany;
	private String projectCharge;
	private String projectChargeDept;
	private String contractIdentifier;
	private String contractSelfNum;
	private String contractName;
	private String contractMoney;
	private String contractMoneyType;
	private String contractBudget;
	private String purchaseType;
	private String contractType1;
	private String contractType2;
	private String contractType;
	private String contractGroup;
	private String oppositeCompany;
	private String dealPerson;
	private String passTime;
	private String signTime;
	private String execPeriodStart;
	private String execPeriodEnd;
	private String dealDeptSuggest;
	private String remark;
	private String attach;
	private String projectId;
	private String contractType1Id;
	private String contractType2Id;
	private String contractGroupId;
	private String purchaseTypeId;
	private String contractMoneyTypeId;
	private String regPerson;
	private String moneySource;
	
	private String deptLeader;
	/*
	 * 操作选项
	 * */
	private String choice = "";
	private String attachId = "";
	private String steplabel = "";
	private String suggest = "";
	
	//合约部经办人
	
	private String dealPersonStr = "";
	
	private List<String> dealPersonList = new ArrayList<String>();
		
	
	//协办部门
	private String depsId = "";
	private String depsName = "";
	private String depRecvsId = "";
	private String depLdsId = "";
	private List<String> depsIdList = new ArrayList<String>();
	private List<String> depsNameList = new ArrayList<String>();
	private List<String> depRecvsIdList = new ArrayList<String>();
	private List<String> depLdsIdList = new ArrayList<String>();
	
	//会签（领导会签  并行）
	private String signLdsId = "";
	private String signLdsName = "";
	private String signLdsDepId = "";
	private String signSecsId = "";
	private String signSecsName = "";
	private String signSecsDepId = "";
	private List<String> signLdsIdList = new ArrayList<String>();
	private List<String> signLdsNameList = new ArrayList<String>();
	private List<String> signLdsDepIdList = new ArrayList<String>();
	private List<String> signSecsIdList = new ArrayList<String>();
	private List<String> signSecsNameList = new ArrayList<String>();
	private List<String> signSecsDepIdList = new ArrayList<String>();
	
	
	/**
	 * 初始化人员list和领导list
	 */
	public void initList(){
		switchList(dealPersonStr,dealPersonList);
		
		switchList(signLdsId,signLdsIdList);
		switchList(signLdsName,signLdsNameList);
		switchList(signLdsDepId,signLdsDepIdList);
		switchList(signSecsId,signSecsIdList);
		switchList(signSecsName,signSecsNameList);
		switchList(signSecsDepId,signSecsDepIdList);
		
		switchList(depsId,depsIdList);
		switchList(depsName,depsNameList);
		switchList(depRecvsId,depRecvsIdList);
		switchList(depLdsId,depLdsIdList);
		
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
	 * @return the attachId
	 */
	public String getAttachId() {
		return attachId;
	}
	/**
	 * @param attachId the attachId to set
	 */
	public void setAttachId(String attachId) {
		this.attachId = attachId;
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
	
	public String getDeptLeader() {
		return deptLeader;
	}
	public void setDeptLeader(String deptLeader) {
		this.deptLeader = deptLeader;
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

	public String getSignLdsId() {
		return signLdsId;
	}

	public void setSignLdsId(String signLdsId) {
		this.signLdsId = signLdsId;
	}

	public String getSignLdsName() {
		return signLdsName;
	}

	public void setSignLdsName(String signLdsName) {
		this.signLdsName = signLdsName;
	}

	public String getSignLdsDepId() {
		return signLdsDepId;
	}

	public void setSignLdsDepId(String signLdsDepId) {
		this.signLdsDepId = signLdsDepId;
	}

	public String getSignSecsId() {
		return signSecsId;
	}

	public void setSignSecsId(String signSecsId) {
		this.signSecsId = signSecsId;
	}

	public String getSignSecsName() {
		return signSecsName;
	}

	public void setSignSecsName(String signSecsName) {
		this.signSecsName = signSecsName;
	}

	public String getSignSecsDepId() {
		return signSecsDepId;
	}

	public void setSignSecsDepId(String signSecsDepId) {
		this.signSecsDepId = signSecsDepId;
	}

	public List<String> getSignLdsIdList() {
		return signLdsIdList;
	}

	public void setSignLdsIdList(List<String> signLdsIdList) {
		this.signLdsIdList = signLdsIdList;
	}

	public List<String> getSignLdsNameList() {
		return signLdsNameList;
	}

	public void setSignLdsNameList(List<String> signLdsNameList) {
		this.signLdsNameList = signLdsNameList;
	}

	public List<String> getSignLdsDepIdList() {
		return signLdsDepIdList;
	}

	public void setSignLdsDepIdList(List<String> signLdsDepIdList) {
		this.signLdsDepIdList = signLdsDepIdList;
	}

	public List<String> getSignSecsIdList() {
		return signSecsIdList;
	}

	public void setSignSecsIdList(List<String> signSecsIdList) {
		this.signSecsIdList = signSecsIdList;
	}

	public List<String> getSignSecsNameList() {
		return signSecsNameList;
	}

	public void setSignSecsNameList(List<String> signSecsNameList) {
		this.signSecsNameList = signSecsNameList;
	}

	public List<String> getSignSecsDepIdList() {
		return signSecsDepIdList;
	}

	public void setSignSecsDepIdList(List<String> signSecsDepIdList) {
		this.signSecsDepIdList = signSecsDepIdList;
	}

	public String getDealPersonStr() {
		return dealPersonStr;
	}

	public void setDealPersonStr(String dealPersonStr) {
		this.dealPersonStr = dealPersonStr;
	}

	public List<String> getDealPersonList() {
		return dealPersonList;
	}

	public void setDealPersonList(List<String> dealPersonList) {
		this.dealPersonList = dealPersonList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public String getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}

	public String getProjectCompany() {
		return projectCompany;
	}

	public void setProjectCompany(String projectCompany) {
		this.projectCompany = projectCompany;
	}

	public String getProjectCharge() {
		return projectCharge;
	}

	public void setProjectCharge(String projectCharge) {
		this.projectCharge = projectCharge;
	}

	public String getContractIdentifier() {
		return contractIdentifier;
	}

	public void setContractIdentifier(String contractIdentifier) {
		this.contractIdentifier = contractIdentifier;
	}

	public String getContractSelfNum() {
		return contractSelfNum;
	}

	public void setContractSelfNum(String contractSelfNum) {
		this.contractSelfNum = contractSelfNum;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(String contractMoney) {
		this.contractMoney = contractMoney;
	}

	public String getContractMoneyType() {
		return contractMoneyType;
	}

	public void setContractMoneyType(String contractMoneyType) {
		this.contractMoneyType = contractMoneyType;
	}

	public String getContractBudget() {
		return contractBudget;
	}

	public void setContractBudget(String contractBudget) {
		this.contractBudget = contractBudget;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getContractType1() {
		return contractType1;
	}

	public void setContractType1(String contractType1) {
		this.contractType1 = contractType1;
	}

	public String getContractType2() {
		return contractType2;
	}

	public void setContractType2(String contractType2) {
		this.contractType2 = contractType2;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractGroup() {
		return contractGroup;
	}

	public void setContractGroup(String contractGroup) {
		this.contractGroup = contractGroup;
	}

	public String getOppositeCompany() {
		return oppositeCompany;
	}

	public void setOppositeCompany(String oppositeCompany) {
		this.oppositeCompany = oppositeCompany;
	}

	public String getDealPerson() {
		return dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	public String getPassTime() {
		return passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getExecPeriodStart() {
		return execPeriodStart;
	}

	public void setExecPeriodStart(String execPeriodStart) {
		this.execPeriodStart = execPeriodStart;
	}

	public String getExecPeriodEnd() {
		return execPeriodEnd;
	}

	public void setExecPeriodEnd(String execPeriodEnd) {
		this.execPeriodEnd = execPeriodEnd;
	}

	public String getDealDeptSuggest() {
		return dealDeptSuggest;
	}

	public void setDealDeptSuggest(String dealDeptSuggest) {
		this.dealDeptSuggest = dealDeptSuggest;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getContractType1Id() {
		return contractType1Id;
	}

	public void setContractType1Id(String contractType1Id) {
		this.contractType1Id = contractType1Id;
	}

	public String getContractType2Id() {
		return contractType2Id;
	}

	public void setContractType2Id(String contractType2Id) {
		this.contractType2Id = contractType2Id;
	}

	public String getContractGroupId() {
		return contractGroupId;
	}

	public void setContractGroupId(String contractGroupId) {
		this.contractGroupId = contractGroupId;
	}

	public String getPurchaseTypeId() {
		return purchaseTypeId;
	}

	public void setPurchaseTypeId(String purchaseTypeId) {
		this.purchaseTypeId = purchaseTypeId;
	}

	public String getContractMoneyTypeId() {
		return contractMoneyTypeId;
	}

	public void setContractMoneyTypeId(String contractMoneyTypeId) {
		this.contractMoneyTypeId = contractMoneyTypeId;
	}

	public String getRegPerson() {
		return regPerson;
	}

	public void setRegPerson(String regPerson) {
		this.regPerson = regPerson;
	}

	public String getProjectChargeDept() {
		return projectChargeDept;
	}

	public void setProjectChargeDept(String projectChargeDept) {
		this.projectChargeDept = projectChargeDept;
	}

	public String getMoneySource() {
		return moneySource;
	}

	public void setMoneySource(String moneySource) {
		this.moneySource = moneySource;
	}
	
	
}
