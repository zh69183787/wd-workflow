/**
 * 
 */
package com.wonders.receive.workflow.process.redv.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wonders.util.DateUtil;

/** 
 * @ClassName: RedvBo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 上午9:38:00 
 *  
 */
@Entity
@Table(name = "T_RECEIVE_DIRECTIVE")
public class RedvMainBo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2180186570147977563L;
	/**
	 * 
	 */
	private String id;
	private String submitDept;
	private String submitDate;
	private String deptId;
	private String title;
	private String content;
	private String operator;
	private String deptMaster;
	private String nbOpinion;
	private String deptOpinion;
	private String leaderOpinion;
	private String chairmanOpinion;
	private String handle;
	private String processInstanceid;
	private String activeId;
	private String operateTime;
	private Integer removed;
	private String status;
	private String docLevel;
	private String keyword;
	private String attach;
	private String theme;
	private String zdept;
	private String xdept;
	private String zleader;
	private String fleader;
	private String zbOpinion;
	private String xbOpinion;
	private String pbOpinion;
	private String workitemid;
	private String saved;
	private String receivePerson;
	private String receiveDept;
	private String xDeptAmount;
	private String attachs;
	private String taskid;
	private String personName;
	private String dealFile;
	private String flag;
	private Integer autScanFlag;
	private String chiefPerson;
	private String typeTitle;
	
	public RedvMainBo() {
  		super();
  		this.removed = 0;
  		this.operateTime = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
  		this.flag = "0";
  		this.autScanFlag = 0;
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

	@Column(name = "SUBMITDEPT", nullable = true, length = 1000)
	public String getSubmitDept() {
		return submitDept;
	}

	public void setSubmitDept(String submitDept) {
		this.submitDept = submitDept;
	}

	@Column(name = "SUBMITDATE", nullable = true, length = 19)
	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	@Column(name = "DEPTID", nullable = true, length = 50)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "TITLE", nullable = true, length = 100)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT", nullable = true, length = 4000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "OPERATOR", nullable = true, length = 100)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "DEPTMASTER", nullable = true, length = 100)
	public String getDeptMaster() {
		return deptMaster;
	}

	public void setDeptMaster(String deptMaster) {
		this.deptMaster = deptMaster;
	}

	@Column(name = "NBOPINION", nullable = true, length = 100)
	public String getNbOpinion() {
		return nbOpinion;
	}

	public void setNbOpinion(String nbOpinion) {
		this.nbOpinion = nbOpinion;
	}

	@Column(name = "DEPTOPINION", nullable = true, length = 500)
	public String getDeptOpinion() {
		return deptOpinion;
	}

	public void setDeptOpinion(String deptOpinion) {
		this.deptOpinion = deptOpinion;
	}

	@Column(name = "LEADEROPINION", nullable = true, length = 500)
	public String getLeaderOpinion() {
		return leaderOpinion;
	}

	public void setLeaderOpinion(String leaderOpinion) {
		this.leaderOpinion = leaderOpinion;
	}

	@Column(name = "CHAIRMANOPINION", nullable = true, length = 500)
	public String getChairmanOpinion() {
		return chairmanOpinion;
	}

	public void setChairmanOpinion(String chairmanOpinion) {
		this.chairmanOpinion = chairmanOpinion;
	}

	@Column(name = "HANDLE", nullable = true, length = 500)
	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	@Column(name = "PROCESSINSTANCEID", nullable = true, length = 50)
	public String getProcessInstanceid() {
		return processInstanceid;
	}

	public void setProcessInstanceid(String processInstanceid) {
		this.processInstanceid = processInstanceid;
	}

	@Column(name = "ACTIVEID", nullable = true, length = 50)
	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	@Column(name = "OPERATE_TIME", nullable = true, length = 200)
	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "REMOVED")
	public Integer getRemoved() {
		return removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

	@Column(name = "STATUS", nullable = true, length = 50)
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DOCLEVEL", nullable = true, length = 20)
	public String getDocLevel() {
		return docLevel;
	}

	public void setDocLevel(String docLevel) {
		this.docLevel = docLevel;
	}

	@Column(name = "KEYWORD", nullable = true, length = 200)
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "ATTACH", nullable = true, length = 4000)
	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	@Column(name = "THEME", nullable = true, length = 200)
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "ZDEPT", nullable = true, length = 200)
	public String getZdept() {
		return zdept;
	}

	public void setZdept(String zdept) {
		this.zdept = zdept;
	}

	@Column(name = "XDEPT", nullable = true, length = 200)
	public String getXdept() {
		return xdept;
	}

	public void setXdept(String xdept) {
		this.xdept = xdept;
	}

	@Column(name = "ZLEADER", nullable = true, length = 200)
	public String getZleader() {
		return zleader;
	}

	public void setZleader(String zleader) {
		this.zleader = zleader;
	}

	@Column(name = "FLEADER", nullable = true, length = 200)
	public String getFleader() {
		return fleader;
	}

	public void setFleader(String fleader) {
		this.fleader = fleader;
	}

	
	@Column(name = "ZBOPINION", nullable = true, length = 1000)
	public String getZbOpinion() {
		return zbOpinion;
	}

	public void setZbOpinion(String zbOpinion) {
		this.zbOpinion = zbOpinion;
	}

	@Column(name = "XBOPINION", nullable = true, length = 1000)
	public String getXbOpinion() {
		return xbOpinion;
	}

	public void setXbOpinion(String xbOpinion) {
		this.xbOpinion = xbOpinion;
	}

	@Column(name = "PBOPINION", nullable = true, length = 1000)
	public String getPbOpinion() {
		return pbOpinion;
	}

	public void setPbOpinion(String pbOpinion) {
		this.pbOpinion = pbOpinion;
	}

	@Column(name = "WORKITEMID", nullable = true, length = 100)
	public String getWorkitemid() {
		return workitemid;
	}

	public void setWorkitemid(String workitemid) {
		this.workitemid = workitemid;
	}

	@Column(name = "SAVED", nullable = true, length = 200)
	public String getSaved() {
		return saved;
	}

	public void setSaved(String saved) {
		this.saved = saved;
	}

	@Column(name = "RECEIVE_PERSON", nullable = true, length = 200)
	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	@Column(name = "RECEIVE_DEPT", nullable = true, length = 200)
	public String getReceiveDept() {
		return receiveDept;
	}

	public void setReceiveDept(String receiveDept) {
		this.receiveDept = receiveDept;
	}

	@Column(name = "XDEPTAMOUNT", nullable = true, length = 200)
	
	public String getxDeptAmount() {
		return xDeptAmount;
	}

	public void setxDeptAmount(String xDeptAmount) {
		this.xDeptAmount = xDeptAmount;
	}

	@Column(name = "ATTACHS", nullable = true, length = 4000)
	
	public String getAttachs() {
		return attachs;
	}

	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}

	@Column(name = "TASKID", nullable = true, length = 200)
	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	@Column(name = "PERSONNAME", nullable = true, length = 4000)
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name = "DEALFILE", nullable = true, length = 4000)
	public String getDealFile() {
		return dealFile;
	}

	public void setDealFile(String dealFile) {
		this.dealFile = dealFile;
	}

	@Column(name = "FLAG", nullable = true, length = 10)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "AUT_SCAN_FLAG")
	public Integer getAutScanFlag() {
		return autScanFlag;
	}

	public void setAutScanFlag(Integer autScanFlag) {
		this.autScanFlag = autScanFlag;
	}

	@Column(name = "CHIEF_PERSON", nullable = true, length = 200)
	public String getChiefPerson() {
		return chiefPerson;
	}

	public void setChiefPerson(String chiefPerson) {
		this.chiefPerson = chiefPerson;
	}

	@Column(name = "TYPE_TITLE", nullable = true, length = 200)
	public String getTypeTitle() {
		return typeTitle;
	}

	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}
	
	
	
}
