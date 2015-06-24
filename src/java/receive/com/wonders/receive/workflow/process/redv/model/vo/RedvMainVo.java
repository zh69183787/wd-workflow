/**
 * 
 */
package com.wonders.receive.workflow.process.redv.model.vo;

/** 
 * @ClassName: RedvMainVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:33:43 
 *  
 */
public class RedvMainVo {
	private String id;
	private String submitDept;
	private String submitDate;
	private String title;
	private String content;
	private String operator;
	private String zleader;
	private String nbOpinion;
	private String docLevel;
	private String keyword;
	private String attach;
	private String flag = "0";
	private String chiefPerson;
	private String deptMaster ;
	private String deptId;
	private String zdept;
	private String xdept;
	/*
	 * 操作选项
	 * */
	private String choice = "";
	private String attachId = "";
	private String steplabel = "";
	private String suggest = "";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubmitDept() {
		return submitDept;
	}
	public void setSubmitDept(String submitDept) {
		this.submitDept = submitDept;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getZleader() {
		return zleader;
	}
	public void setZleader(String zleader) {
		this.zleader = zleader;
	}
	public String getNbOpinion() {
		return nbOpinion;
	}
	public void setNbOpinion(String nbOpinion) {
		this.nbOpinion = nbOpinion;
	}
	public String getDocLevel() {
		return docLevel;
	}
	public void setDocLevel(String docLevel) {
		this.docLevel = docLevel;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public String getSteplabel() {
		return steplabel;
	}
	public void setSteplabel(String steplabel) {
		this.steplabel = steplabel;
	}
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	public String getChiefPerson() {
		return chiefPerson;
	}
	public void setChiefPerson(String chiefPerson) {
		this.chiefPerson = chiefPerson;
	}
	public String getDeptMaster() {
		return deptMaster;
	}
	public void setDeptMaster(String deptMaster) {
		this.deptMaster = deptMaster;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getZdept() {
		return zdept;
	}
	public void setZdept(String zdept) {
		this.zdept = zdept;
	}
	public String getXdept() {
		return xdept;
	}
	public void setXdept(String xdept) {
		this.xdept = xdept;
	}
	
	
}
