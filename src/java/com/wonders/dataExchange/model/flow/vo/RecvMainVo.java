/**
 * 
 */
package com.wonders.dataExchange.model.flow.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 * @ClassName: RecvMainVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:33:43 
 *  
 */

@XmlRootElement(name="RecvMainVo")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecvMainVo {
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
	
	
}
