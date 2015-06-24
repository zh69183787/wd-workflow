/**
 * 
 */
package com.wonders.receive.workflow.process.finish.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.wonders.receive.workflow.constants.CommonConstants;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: FinishSubVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 上午11:12:48 
 *  
 */
public class FinishSubVo {
	private String taskId = "";
	
	private String id = "";
	
	private String choice = "";
	
	private String choice2 = "";
	
	private String suggest = "";
	
	
	
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
	
	//审核 （领导审核 串行）
	private String chkLdsId = "";
	private String chkLdsName = "";
	private String chkLdsDepId = "";
	private String chkSecsId = "";
	private String chkSecsName = "";
	private String chkSecsDepId = "";
	private List<String> chkLdsIdList = new ArrayList<String>();
	private List<String> chkLdsNameList = new ArrayList<String>();
	private List<String> chkLdsDepIdList = new ArrayList<String>();
	private List<String> chkSecsIdList = new ArrayList<String>();
	private List<String> chkSecsNameList = new ArrayList<String>();
	private List<String> chkSecsDepIdList = new ArrayList<String>();
	
	//主办部门
	private String mainDepId = "";
	private String mainDepName = "";
	private String mainDepRecvId = "";
	private String mainDepLdId = "";
	private List<String> mainDepIdList = new ArrayList<String>();
	private List<String> mainDepNameList = new ArrayList<String>();
	private List<String> mainDepRecvIdList = new ArrayList<String>();
	private List<String> mainDepLdIdList = new ArrayList<String>();
	
	//协办部门
	private String depsId = "";
	private String depsName = "";
	private String depRecvsId = "";
	private String depLdsId = "";
	private List<String> depsIdList = new ArrayList<String>();
	private List<String> depsNameList = new ArrayList<String>();
	private List<String> depRecvsIdList = new ArrayList<String>();
	private List<String> depLdsIdList = new ArrayList<String>();
	
	private String attachId = "";
	
	private String remark = "";

	private String steplabel = "";
	private String flowType = "";
	
	
	@Override
	public String toString() {
		String str = "choice:"+choice+" " +
		"choice2:"+choice2+" " +
		"suggest:"+suggest+" " +
		"attachId:"+attachId+" " +
		"remark:"+remark+" " +
				"";
		return str;
	}

	/**
	 * 初始化人员list和领导list
	 */
	public void initList(){
		
		switchList(signLdsId,signLdsIdList);
		switchList(signLdsName,signLdsNameList);
		switchList(signLdsDepId,signLdsDepIdList);
		switchList(signSecsId,signSecsIdList);
		switchList(signSecsName,signSecsNameList);
		switchList(signSecsDepId,signSecsDepIdList);
		
		switchList(chkLdsId,chkLdsIdList);
		switchList(chkLdsName,chkLdsNameList);
		switchList(chkLdsDepId,chkLdsDepIdList);
		switchList(chkSecsId,chkSecsIdList);
		switchList(chkSecsName,chkSecsNameList);
		switchList(chkSecsDepId,chkSecsDepIdList);
		
		switchList(mainDepId,mainDepIdList);
		switchList(mainDepName,mainDepNameList);
		switchList(mainDepRecvId,mainDepRecvIdList);
		switchList(mainDepLdId,mainDepLdIdList);
		
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
			target.add("");
		}
		
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public String getChoice2() {
		return choice2;
	}

	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}


	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSteplabel() {
		return steplabel;
	}

	public void setSteplabel(String steplabel) {
		this.steplabel = steplabel;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	
	
	/**
	 * @return the signLdsId
	 */
	public String getSignLdsId() {
		return signLdsId;
	}

	/**
	 * @param signLdsId the signLdsId to set
	 */
	public void setSignLdsId(String signLdsId) {
		this.signLdsId = signLdsId;
	}

	/**
	 * @return the signLdsName
	 */
	public String getSignLdsName() {
		return signLdsName;
	}

	/**
	 * @param signLdsName the signLdsName to set
	 */
	public void setSignLdsName(String signLdsName) {
		this.signLdsName = signLdsName;
	}

	/**
	 * @return the signLdsDepId
	 */
	public String getSignLdsDepId() {
		return signLdsDepId;
	}

	/**
	 * @param signLdsDepId the signLdsDepId to set
	 */
	public void setSignLdsDepId(String signLdsDepId) {
		this.signLdsDepId = signLdsDepId;
	}

	/**
	 * @return the signSecsId
	 */
	public String getSignSecsId() {
		return signSecsId;
	}

	/**
	 * @param signSecsId the signSecsId to set
	 */
	public void setSignSecsId(String signSecsId) {
		this.signSecsId = signSecsId;
	}

	/**
	 * @return the signSecsName
	 */
	public String getSignSecsName() {
		return signSecsName;
	}

	/**
	 * @param signSecsName the signSecsName to set
	 */
	public void setSignSecsName(String signSecsName) {
		this.signSecsName = signSecsName;
	}

	/**
	 * @return the signSecsDepId
	 */
	public String getSignSecsDepId() {
		return signSecsDepId;
	}

	/**
	 * @param signSecsDepId the signSecsDepId to set
	 */
	public void setSignSecsDepId(String signSecsDepId) {
		this.signSecsDepId = signSecsDepId;
	}

	/**
	 * @return the signLdsIdList
	 */
	public List<String> getSignLdsIdList() {
		return signLdsIdList;
	}

	/**
	 * @param signLdsIdList the signLdsIdList to set
	 */
	public void setSignLdsIdList(List<String> signLdsIdList) {
		this.signLdsIdList = signLdsIdList;
	}

	/**
	 * @return the signLdsNameList
	 */
	public List<String> getSignLdsNameList() {
		return signLdsNameList;
	}

	/**
	 * @param signLdsNameList the signLdsNameList to set
	 */
	public void setSignLdsNameList(List<String> signLdsNameList) {
		this.signLdsNameList = signLdsNameList;
	}

	/**
	 * @return the signLdsDepIdList
	 */
	public List<String> getSignLdsDepIdList() {
		return signLdsDepIdList;
	}

	/**
	 * @param signLdsDepIdList the signLdsDepIdList to set
	 */
	public void setSignLdsDepIdList(List<String> signLdsDepIdList) {
		this.signLdsDepIdList = signLdsDepIdList;
	}

	/**
	 * @return the signSecsIdList
	 */
	public List<String> getSignSecsIdList() {
		return signSecsIdList;
	}

	/**
	 * @param signSecsIdList the signSecsIdList to set
	 */
	public void setSignSecsIdList(List<String> signSecsIdList) {
		this.signSecsIdList = signSecsIdList;
	}

	/**
	 * @return the signSecsNameList
	 */
	public List<String> getSignSecsNameList() {
		return signSecsNameList;
	}

	/**
	 * @param signSecsNameList the signSecsNameList to set
	 */
	public void setSignSecsNameList(List<String> signSecsNameList) {
		this.signSecsNameList = signSecsNameList;
	}

	/**
	 * @return the signSecsDepIdList
	 */
	public List<String> getSignSecsDepIdList() {
		return signSecsDepIdList;
	}

	/**
	 * @param signSecsDepIdList the signSecsDepIdList to set
	 */
	public void setSignSecsDepIdList(List<String> signSecsDepIdList) {
		this.signSecsDepIdList = signSecsDepIdList;
	}

	/**
	 * @return the chkLdsId
	 */
	public String getChkLdsId() {
		return chkLdsId;
	}

	/**
	 * @param chkLdsId the chkLdsId to set
	 */
	public void setChkLdsId(String chkLdsId) {
		this.chkLdsId = chkLdsId;
	}

	/**
	 * @return the chkLdsName
	 */
	public String getChkLdsName() {
		return chkLdsName;
	}

	/**
	 * @param chkLdsName the chkLdsName to set
	 */
	public void setChkLdsName(String chkLdsName) {
		this.chkLdsName = chkLdsName;
	}

	/**
	 * @return the chkLdsDepId
	 */
	public String getChkLdsDepId() {
		return chkLdsDepId;
	}

	/**
	 * @param chkLdsDepId the chkLdsDepId to set
	 */
	public void setChkLdsDepId(String chkLdsDepId) {
		this.chkLdsDepId = chkLdsDepId;
	}

	/**
	 * @return the chkSecsId
	 */
	public String getChkSecsId() {
		return chkSecsId;
	}

	/**
	 * @param chkSecsId the chkSecsId to set
	 */
	public void setChkSecsId(String chkSecsId) {
		this.chkSecsId = chkSecsId;
	}

	/**
	 * @return the chkSecsName
	 */
	public String getChkSecsName() {
		return chkSecsName;
	}

	/**
	 * @param chkSecsName the chkSecsName to set
	 */
	public void setChkSecsName(String chkSecsName) {
		this.chkSecsName = chkSecsName;
	}

	/**
	 * @return the chkSecsDepId
	 */
	public String getChkSecsDepId() {
		return chkSecsDepId;
	}

	/**
	 * @param chkSecsDepId the chkSecsDepId to set
	 */
	public void setChkSecsDepId(String chkSecsDepId) {
		this.chkSecsDepId = chkSecsDepId;
	}

	/**
	 * @return the chkLdsIdList
	 */
	public List<String> getChkLdsIdList() {
		return chkLdsIdList;
	}

	/**
	 * @param chkLdsIdList the chkLdsIdList to set
	 */
	public void setChkLdsIdList(List<String> chkLdsIdList) {
		this.chkLdsIdList = chkLdsIdList;
	}

	/**
	 * @return the chkLdsNameList
	 */
	public List<String> getChkLdsNameList() {
		return chkLdsNameList;
	}

	/**
	 * @param chkLdsNameList the chkLdsNameList to set
	 */
	public void setChkLdsNameList(List<String> chkLdsNameList) {
		this.chkLdsNameList = chkLdsNameList;
	}

	/**
	 * @return the chkLdsDepIdList
	 */
	public List<String> getChkLdsDepIdList() {
		return chkLdsDepIdList;
	}

	/**
	 * @param chkLdsDepIdList the chkLdsDepIdList to set
	 */
	public void setChkLdsDepIdList(List<String> chkLdsDepIdList) {
		this.chkLdsDepIdList = chkLdsDepIdList;
	}

	/**
	 * @return the chkSecsIdList
	 */
	public List<String> getChkSecsIdList() {
		return chkSecsIdList;
	}

	/**
	 * @param chkSecsIdList the chkSecsIdList to set
	 */
	public void setChkSecsIdList(List<String> chkSecsIdList) {
		this.chkSecsIdList = chkSecsIdList;
	}

	/**
	 * @return the chkSecsNameList
	 */
	public List<String> getChkSecsNameList() {
		return chkSecsNameList;
	}

	/**
	 * @param chkSecsNameList the chkSecsNameList to set
	 */
	public void setChkSecsNameList(List<String> chkSecsNameList) {
		this.chkSecsNameList = chkSecsNameList;
	}

	/**
	 * @return the chkSecsDepIdList
	 */
	public List<String> getChkSecsDepIdList() {
		return chkSecsDepIdList;
	}

	/**
	 * @param chkSecsDepIdList the chkSecsDepIdList to set
	 */
	public void setChkSecsDepIdList(List<String> chkSecsDepIdList) {
		this.chkSecsDepIdList = chkSecsDepIdList;
	}

	/**
	 * @return the mainDepId
	 */
	public String getMainDepId() {
		return mainDepId;
	}

	/**
	 * @param mainDepId the mainDepId to set
	 */
	public void setMainDepId(String mainDepId) {
		this.mainDepId = mainDepId;
	}

	/**
	 * @return the mainDepName
	 */
	public String getMainDepName() {
		return mainDepName;
	}

	/**
	 * @param mainDepName the mainDepName to set
	 */
	public void setMainDepName(String mainDepName) {
		this.mainDepName = mainDepName;
	}

	/**
	 * @return the mainDepRecvId
	 */
	public String getMainDepRecvId() {
		return mainDepRecvId;
	}

	/**
	 * @param mainDepRecvId the mainDepRecvId to set
	 */
	public void setMainDepRecvId(String mainDepRecvId) {
		this.mainDepRecvId = mainDepRecvId;
	}

	/**
	 * @return the mainDepLdId
	 */
	public String getMainDepLdId() {
		return mainDepLdId;
	}

	/**
	 * @param mainDepLdId the mainDepLdId to set
	 */
	public void setMainDepLdId(String mainDepLdId) {
		this.mainDepLdId = mainDepLdId;
	}

	/**
	 * @return the mainDepIdList
	 */
	public List<String> getMainDepIdList() {
		return mainDepIdList;
	}

	/**
	 * @param mainDepIdList the mainDepIdList to set
	 */
	public void setMainDepIdList(List<String> mainDepIdList) {
		this.mainDepIdList = mainDepIdList;
	}

	/**
	 * @return the mainDepNameList
	 */
	public List<String> getMainDepNameList() {
		return mainDepNameList;
	}

	/**
	 * @param mainDepNameList the mainDepNameList to set
	 */
	public void setMainDepNameList(List<String> mainDepNameList) {
		this.mainDepNameList = mainDepNameList;
	}

	/**
	 * @return the mainDepRecvIdList
	 */
	public List<String> getMainDepRecvIdList() {
		return mainDepRecvIdList;
	}

	/**
	 * @param mainDepRecvIdList the mainDepRecvIdList to set
	 */
	public void setMainDepRecvIdList(List<String> mainDepRecvIdList) {
		this.mainDepRecvIdList = mainDepRecvIdList;
	}

	/**
	 * @return the mainDepLdIdList
	 */
	public List<String> getMainDepLdIdList() {
		return mainDepLdIdList;
	}

	/**
	 * @param mainDepLdIdList the mainDepLdIdList to set
	 */
	public void setMainDepLdIdList(List<String> mainDepLdIdList) {
		this.mainDepLdIdList = mainDepLdIdList;
	}

	/**
	 * @return the depsId
	 */
	public String getDepsId() {
		return depsId;
	}

	/**
	 * @param depsId the depsId to set
	 */
	public void setDepsId(String depsId) {
		this.depsId = depsId;
	}

	/**
	 * @return the depsName
	 */
	public String getDepsName() {
		return depsName;
	}

	/**
	 * @param depsName the depsName to set
	 */
	public void setDepsName(String depsName) {
		this.depsName = depsName;
	}

	/**
	 * @return the depRecvsId
	 */
	public String getDepRecvsId() {
		return depRecvsId;
	}

	/**
	 * @param depRecvsId the depRecvsId to set
	 */
	public void setDepRecvsId(String depRecvsId) {
		this.depRecvsId = depRecvsId;
	}

	/**
	 * @return the depLdsId
	 */
	public String getDepLdsId() {
		return depLdsId;
	}

	/**
	 * @param depLdsId the depLdsId to set
	 */
	public void setDepLdsId(String depLdsId) {
		this.depLdsId = depLdsId;
	}

	/**
	 * @return the depsIdList
	 */
	public List<String> getDepsIdList() {
		return depsIdList;
	}

	/**
	 * @param depsIdList the depsIdList to set
	 */
	public void setDepsIdList(List<String> depsIdList) {
		this.depsIdList = depsIdList;
	}

	/**
	 * @return the depsNameList
	 */
	public List<String> getDepsNameList() {
		return depsNameList;
	}

	/**
	 * @param depsNameList the depsNameList to set
	 */
	public void setDepsNameList(List<String> depsNameList) {
		this.depsNameList = depsNameList;
	}

	/**
	 * @return the depRecvsIdList
	 */
	public List<String> getDepRecvsIdList() {
		return depRecvsIdList;
	}

	/**
	 * @param depRecvsIdList the depRecvsIdList to set
	 */
	public void setDepRecvsIdList(List<String> depRecvsIdList) {
		this.depRecvsIdList = depRecvsIdList;
	}

	/**
	 * @return the depLdsIdList
	 */
	public List<String> getDepLdsIdList() {
		return depLdsIdList;
	}

	/**
	 * @param depLdsIdList the depLdsIdList to set
	 */
	public void setDepLdsIdList(List<String> depLdsIdList) {
		this.depLdsIdList = depLdsIdList;
	}

	
	
}
