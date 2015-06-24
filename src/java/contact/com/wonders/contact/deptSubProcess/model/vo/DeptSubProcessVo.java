package com.wonders.contact.deptSubProcess.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.wonders.contact.constant.CommonConstants;
import com.wonders.util.StringUtil;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
public class DeptSubProcessVo {
	private String taskId = "";
	
	private String id = "";
	
	private String choice = "";
	
	private String choice2 = "";
	
	private String suggest = "";
	
	
	
	private String dealPersonStr = "";
	
	private List<String> dealPersonList = new ArrayList<String>();
	
	private String dealLeaderStr = "";
	
	private List<String> dealLeaderList = new ArrayList<String>();
	
	
	
	private String attachId = "";
	
	private String remark = "";

	private String steplabel = "";
	private String flowType = "";
	
	
	
	@Override
	public String toString() {
		String str = "choice:"+choice+" " +
		"choice2:"+choice2+" " +
		"suggest:"+suggest+" " +
		"dealPersonStr:"+dealPersonStr+" " +
		"dealLeaderStr:"+dealLeaderStr+" " +
		"attachId:"+attachId+" " +
		"remark:"+remark+" " +
				"";
		return str;
	}

	/**
	 * 初始化人员list和领导list
	 */
	public void initList(){
		String person = StringUtil.getNotNullValueString(dealPersonStr);
		String leader = StringUtil.getNotNullValueString(dealLeaderStr);
		
		String[] persons = person.split(CommonConstants.SPLIT_EXP);
		String[] leaders = leader.split(CommonConstants.SPLIT_EXP);
		
		for(int i=0;i<persons.length;i++){
			String p = StringUtil.getNotNullValueString(persons[i]);
			if(p.length()>0) dealPersonList.add(p);
		}
		
		for(int i=0;i<leaders.length;i++){
			String l = StringUtil.getNotNullValueString(leaders[i]);
			if(l.length()>0) dealLeaderList.add(l);
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

	public String getDealLeaderStr() {
		return dealLeaderStr;
	}

	public void setDealLeaderStr(String dealLeaderStr) {
		this.dealLeaderStr = dealLeaderStr;
	}

	public List<String> getDealLeaderList() {
		return dealLeaderList;
	}

	public void setDealLeaderList(List<String> dealLeaderList) {
		this.dealLeaderList = dealLeaderList;
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
	
	
}
