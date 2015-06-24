/**
 * 
 */
package com.wonders.receive.workflow.approve.model.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @ClassName: TApprovedinfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2013-2-17 上午10:06:54
 * 
 */

@Entity
@Table(name = "T_APPROVEDINFO")
public class ApprovedInfoBo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2053160602236876832L;
	private String guid;
	private String process;
	private Long incidentno;
	private String dept;
	private String stepname;
	private String username;
	private String userfullname;
	private String usermail;
	private Long agree;
	private Long disagree;
	private Long returned;
	private String sign;
	private String signdate;
	private String remark;
	private Date upddate;
	private Long status;
	private String leaderId;
	private String fllowFlag;
	private String readFlag;
	private String planId;
	private String optionCode;
	private String deptId;
	private String fileGroupId;
	private Integer rounds;
	
	 public ApprovedInfoBo() {
	    	this.rounds = 0;
	    	this.agree = 0L;
//	    	this.disagree = 0L;
	    	this.returned = 0L;
//	    	this.status=1L;
//	    	this.fllowFlag="0";
//	    	this.readFlag="1";
	    }


	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "GUID")
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(name = "PROCESS", nullable = true, length = 50)
	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	@Column(name = "INCIDENTNO")
	public Long getIncidentno() {
		return incidentno;
	}

	public void setIncidentno(Long incidentno) {
		this.incidentno = incidentno;
	}

	@Column(name = "DEPT", nullable = true, length = 50)
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name = "STEPNAME", nullable = true, length = 50)
	public String getStepname() {
		return stepname;
	}

	public void setStepname(String stepname) {
		this.stepname = stepname;
	}

	@Column(name = "USERNAME", nullable = true, length = 50)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "USERFULLNAME", nullable = true, length = 50)
	public String getUserfullname() {
		return userfullname;
	}

	public void setUserfullname(String userfullname) {
		this.userfullname = userfullname;
	}

	@Column(name = "USERMAIL", nullable = true, length = 50)
	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	@Column(name = "AGREE")
	public Long getAgree() {
		return agree;
	}

	public void setAgree(Long agree) {
		this.agree = agree;
	}

	@Column(name = "DISAGREE")
	public Long getDisagree() {
		return disagree;
	}

	public void setDisagree(Long disagree) {
		this.disagree = disagree;
	}

	@Column(name = "RETURNED")
	public Long getReturned() {
		return returned;
	}

	public void setReturned(Long returned) {
		this.returned = returned;
	}

	@Column(name = "SIGN", nullable = true, length = 50)
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Column(name = "SIGNDATE", nullable = true, length = 50)
	public String getSigndate() {
		return signdate;
	}

	public void setSigndate(String signdate) {
		this.signdate = signdate;
	}

	@Column(name = "REMARK", nullable = true, length = 2000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDDATE")
	public Date getUpddate() {
		return upddate;
	}

	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}

	@Column(name = "STATUS")
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "LEADER_ID", nullable = true, length = 50)
	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	@Column(name = "FLLOW_FLAG", nullable = true, length = 50)
	public String getFllowFlag() {
		return fllowFlag;
	}

	public void setFllowFlag(String fllowFlag) {
		this.fllowFlag = fllowFlag;
	}

	@Column(name = "READ_FLAG", nullable = true, length = 50)
	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	@Column(name = "PLAN_ID", nullable = true, length = 100)
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	@Column(name = "OPTION_CODE", nullable = true, length = 200)
	public String getOptionCode() {
		return optionCode;
	}

	public void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}

	@Column(name = "DEPT_ID", nullable = true, length = 200)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "FILE_GROUP_ID", nullable = true, length = 38)
	public String getFileGroupId() {
		return fileGroupId;
	}

	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}

	@Column(name = "ROUNDS")
	public Integer getRounds() {
		return rounds;
	}

	public void setRounds(Integer rounds) {
		this.rounds = rounds;
	}

}
