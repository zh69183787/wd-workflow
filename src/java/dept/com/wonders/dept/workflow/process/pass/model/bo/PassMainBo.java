/**
 * 
 */
package com.wonders.dept.workflow.process.pass.model.bo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wonders.util.DateUtil;

/** 
 * @ClassName: PassMainBo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 上午9:38:00 
 *  
 */
@Entity
@Table(name = "T_DEPT_PASS")
public class PassMainBo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -500946614858553451L;
	private String id;
	private String title;
	private String attach;
	private String remark;
	private String process;
	private String incident;
	private String regTime;
	private String regDeptName;
	private String regDeptId;
	private String regName;
	private String regLoginName;
	private String operateTime;
	private String removed;
	private String flag;
	private String mainId;
	private String mainTable;
	private String codeId;

	public PassMainBo() {
  		super();
  		this.removed = "0";
  		this.operateTime = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
  		this.flag = "0";
  	}
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "TITLE", length = 2000)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "ATTACH", length = 100)
	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	@Lob
    @Basic(fetch=FetchType.EAGER)
    @Column(name = "REMARK",columnDefinition = "CLOB")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "PROCESS", length = 50)
	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	@Column(name = "INCIDENT", length = 50)
	public String getIncident() {
		return incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
	}

	@Column(name = "REG_DEPT_ID", length = 50)
	public String getRegDeptId() {
		return regDeptId;
	}

	public void setRegDeptId(String regDeptId) {
		this.regDeptId = regDeptId;
	}

	@Column(name = "REG_NAME", length = 100)
	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	@Column(name = "REG_LOGIN_NAME", length = 100)
	public String getRegLoginName() {
		return regLoginName;
	}

	public void setRegLoginName(String regLoginName) {
		this.regLoginName = regLoginName;
	}

	@Column(name = "OPERATE_TIME", length = 50)
	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "REMOVED", length = 1)
	public String getRemoved() {
		return removed;
	}

	public void setRemoved(String removed) {
		this.removed = removed;
	}

	@Column(name = "FLAG", length = 10)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "MAIN_ID", length = 50)
	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	@Column(name = "MAIN_TABLE", length = 50)
	public String getMainTable() {
		return mainTable;
	}

	public void setMainTable(String mainTable) {
		this.mainTable = mainTable;
	}

	@Column(name = "CODE_ID", length = 50)
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	@Column(name = "REG_TIME", length = 50)
	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	@Column(name = "REG_DEPT_NAME", length = 50)
	public String getRegDeptName() {
		return regDeptName;
	}

	public void setRegDeptName(String regDeptName) {
		this.regDeptName = regDeptName;
	}
	
	
}
