/**
 * 
 */
package com.wonders.dept.workflow.process.pass.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.wonders.receive.workflow.constants.CommonConstants;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: RecvMainVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:33:43 
 *  
 */
public class PassMainVo {
	private String id;
	private String title;
	private String attach;
	private String remark;
	private String regDeptName;
	private String mainId;
	private String mainTable;
	private String codeId;
	/*
	 * 操作选项
	 * */
	private String choice = "";
	private String attachId = "";
	private String steplabel = "";
	private String suggest = "";
	
	//部门经办人
	
	private String dealPersonStr = "";
	
	private List<String> dealPersonList = new ArrayList<String>();
		
	
	
	
	/**
	 * 初始化人员list和领导list
	 */
	public void initList(){
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

		
		
		
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getMainTable() {
		return mainTable;
	}

	public void setMainTable(String mainTable) {
		this.mainTable = mainTable;
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

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getRegDeptName() {
		return regDeptName;
	}

	public void setRegDeptName(String regDeptName) {
		this.regDeptName = regDeptName;
	}

	
}
