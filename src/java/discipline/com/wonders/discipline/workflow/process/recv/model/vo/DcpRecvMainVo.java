/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.wonders.receive.workflow.constants.CommonConstants;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: DcpRecvMainVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:33:43 
 *  
 */
public class DcpRecvMainVo {
	private String id;
	private String swType;
	private String swId;
	private String swDate;
	private String swUnit;
	private String fileDate;
	private String secretClass;
	private String num;
	private String filezh;
	private String title;
	private String keyword;
	private String content;
	private String attach;
	private String blMode;
	private String operator;
	private String chiefDep;
	//private String ordinaryDep;//发起人部门
	private String chiefPerson;//typeid
	private String ordinaryPerson;
	private String remark;
	private String swSerial;
	private String priorities;
	private String flag = "0";
	//private String flag; 0
	
	/*
	 * 操作选项
	 * */
	private String choice = "";
	private String attachId = "";
	private String steplabel = "";
	private String suggest = "";
	
	
	//协办部门
	private String depsId = "";
	private String depsName = "";
	private String depRecvsId = "";
	private String depLdsId = "";
	private List<String> depsIdList = new ArrayList<String>();
	private List<String> depsNameList = new ArrayList<String>();
	private List<String> depRecvsIdList = new ArrayList<String>();
	private List<String> depLdsIdList = new ArrayList<String>();
	
	
	//集团领导
	private String comLeaderLoginName = "";
	private String comLeaderName = "";
	private String comLeaderDeptId = "";
	private List<String> comLeaderLoginNameList = new ArrayList<String>();
	private List<String> comLeaderNameList = new ArrayList<String>();
	private List<String> comLeaderDeptIdList = new ArrayList<String>();
	
	//纪委委员
	private String dcpLoginName = "";
	private String dcpName = "";
	private String dcpDeptId = "";
	private List<String> dcpLoginNameList = new ArrayList<String>();
	private List<String> dcpNameList = new ArrayList<String>();
	private List<String> dcpDeptIdList = new ArrayList<String>();
	
	private String simulateLoginName = "";
	private String simulateName = "";
	private String simulateDeptId = "";
	
	private String finishLoginName = "";
	private String finishName = "";
	private String finishDeptId = "";
	
	/**
	 * 初始化人员list和领导list
	 */
	public void initList(){
		switchList(depsId,depsIdList);
		switchList(depsName,depsNameList);
		switchList(depRecvsId,depRecvsIdList);
		switchList(depLdsId,depLdsIdList);
		switchList(comLeaderLoginName,comLeaderLoginNameList);
		switchList(comLeaderName,comLeaderNameList);
		switchList(comLeaderDeptId,comLeaderDeptIdList);
		switchList(dcpLoginName,dcpLoginNameList);
		switchList(dcpName,dcpNameList);
		switchList(dcpDeptId,dcpDeptIdList);
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
	/**
	 * @return the swType
	 */
	public String getSwType() {
		return swType;
	}
	/**
	 * @param swType the swType to set
	 */
	public void setSwType(String swType) {
		this.swType = swType;
	}
	/**
	 * @return the swId
	 */
	public String getSwId() {
		return swId;
	}
	/**
	 * @param swId the swId to set
	 */
	public void setSwId(String swId) {
		this.swId = swId;
	}
	/**
	 * @return the swDate
	 */
	public String getSwDate() {
		return swDate;
	}
	/**
	 * @param swDate the swDate to set
	 */
	public void setSwDate(String swDate) {
		this.swDate = swDate;
	}
	/**
	 * @return the swUnit
	 */
	public String getSwUnit() {
		return swUnit;
	}
	/**
	 * @param swUnit the swUnit to set
	 */
	public void setSwUnit(String swUnit) {
		this.swUnit = swUnit;
	}
	/**
	 * @return the fileDate
	 */
	public String getFileDate() {
		return fileDate;
	}
	/**
	 * @param fileDate the fileDate to set
	 */
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	/**
	 * @return the secretClass
	 */
	public String getSecretClass() {
		return secretClass;
	}
	/**
	 * @param secretClass the secretClass to set
	 */
	public void setSecretClass(String secretClass) {
		this.secretClass = secretClass;
	}
	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}
	/**
	 * @return the filezh
	 */
	public String getFilezh() {
		return filezh;
	}
	/**
	 * @param filezh the filezh to set
	 */
	public void setFilezh(String filezh) {
		this.filezh = filezh;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the blMode
	 */
	public String getBlMode() {
		return blMode;
	}
	/**
	 * @param blMode the blMode to set
	 */
	public void setBlMode(String blMode) {
		this.blMode = blMode;
	}
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * @return the chiefDep
	 */
	public String getChiefDep() {
		return chiefDep;
	}
	/**
	 * @param chiefDep the chiefDep to set
	 */
	public void setChiefDep(String chiefDep) {
		this.chiefDep = chiefDep;
	}
	/**
	 * @return the chiefPerson
	 */
	public String getChiefPerson() {
		return chiefPerson;
	}
	/**
	 * @param chiefPerson the chiefPerson to set
	 */
	public void setChiefPerson(String chiefPerson) {
		this.chiefPerson = chiefPerson;
	}
	/**
	 * @return the ordinaryPerson
	 */
	public String getOrdinaryPerson() {
		return ordinaryPerson;
	}
	/**
	 * @param ordinaryPerson the ordinaryPerson to set
	 */
	public void setOrdinaryPerson(String ordinaryPerson) {
		this.ordinaryPerson = ordinaryPerson;
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
	 * @return the serial
	 */
	public String getSwSerial() {
		return swSerial;
	}
	/**
	 * @param serial the serial to set
	 */
	public void setSwSerial(String swSerial) {
		this.swSerial = swSerial;
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
	/**
	 * @return the priorities
	 */
	public String getPriorities() {
		return priorities;
	}
	/**
	 * @param priorities the priorities to set
	 */
	public void setPriorities(String priorities) {
		this.priorities = priorities;
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

	
	public String getComLeaderLoginName() {
		return comLeaderLoginName;
	}

	public void setComLeaderLoginName(String comLeaderLoginName) {
		this.comLeaderLoginName = comLeaderLoginName;
	}

	public String getComLeaderName() {
		return comLeaderName;
	}

	public void setComLeaderName(String comLeaderName) {
		this.comLeaderName = comLeaderName;
	}

	public String getComLeaderDeptId() {
		return comLeaderDeptId;
	}

	public void setComLeaderDeptId(String comLeaderDeptId) {
		this.comLeaderDeptId = comLeaderDeptId;
	}

	
	public String getDcpLoginName() {
		return dcpLoginName;
	}

	public void setDcpLoginName(String dcpLoginName) {
		this.dcpLoginName = dcpLoginName;
	}

	public String getDcpName() {
		return dcpName;
	}

	public void setDcpName(String dcpName) {
		this.dcpName = dcpName;
	}

	public String getDcpDeptId() {
		return dcpDeptId;
	}

	public void setDcpDeptId(String dcpDeptId) {
		this.dcpDeptId = dcpDeptId;
	}


	public List<String> getComLeaderLoginNameList() {
		return comLeaderLoginNameList;
	}


	public void setComLeaderLoginNameList(List<String> comLeaderLoginNameList) {
		this.comLeaderLoginNameList = comLeaderLoginNameList;
	}


	public List<String> getComLeaderNameList() {
		return comLeaderNameList;
	}


	public void setComLeaderNameList(List<String> comLeaderNameList) {
		this.comLeaderNameList = comLeaderNameList;
	}


	public List<String> getComLeaderDeptIdList() {
		return comLeaderDeptIdList;
	}


	public void setComLeaderDeptIdList(List<String> comLeaderDeptIdList) {
		this.comLeaderDeptIdList = comLeaderDeptIdList;
	}


	public List<String> getDcpLoginNameList() {
		return dcpLoginNameList;
	}


	public void setDcpLoginNameList(List<String> dcpLoginNameList) {
		this.dcpLoginNameList = dcpLoginNameList;
	}


	public List<String> getDcpNameList() {
		return dcpNameList;
	}


	public void setDcpNameList(List<String> dcpNameList) {
		this.dcpNameList = dcpNameList;
	}


	public List<String> getDcpDeptIdList() {
		return dcpDeptIdList;
	}


	public void setDcpDeptIdList(List<String> dcpDeptIdList) {
		this.dcpDeptIdList = dcpDeptIdList;
	}


	public String getSimulateLoginName() {
		return simulateLoginName;
	}


	public void setSimulateLoginName(String simulateLoginName) {
		this.simulateLoginName = simulateLoginName;
	}


	public String getSimulateName() {
		return simulateName;
	}


	public void setSimulateName(String simulateName) {
		this.simulateName = simulateName;
	}


	public String getSimulateDeptId() {
		return simulateDeptId;
	}


	public void setSimulateDeptId(String simulateDeptId) {
		this.simulateDeptId = simulateDeptId;
	}


	public String getFinishLoginName() {
		return finishLoginName;
	}


	public void setFinishLoginName(String finishLoginName) {
		this.finishLoginName = finishLoginName;
	}


	public String getFinishName() {
		return finishName;
	}


	public void setFinishName(String finishName) {
		this.finishName = finishName;
	}


	public String getFinishDeptId() {
		return finishDeptId;
	}


	public void setFinishDeptId(String finishDeptId) {
		this.finishDeptId = finishDeptId;
	}

	
	
	
}
