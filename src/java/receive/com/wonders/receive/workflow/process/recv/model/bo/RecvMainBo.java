/**
 * 
 */
package com.wonders.receive.workflow.process.recv.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wonders.util.DateUtil;

/** 
 * @ClassName: RecvBo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 上午9:38:00 
 *  
 */
@Entity
@Table(name = "T_DOC_RECEIVE")
public class RecvMainBo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -500946614858553451L;
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
	private String nbOpinion;
	private String pbOpinion;
	private String blResult;
	private String blMode;
	private String lastDate;
	private Integer removed;
	private String instanceId;
	private String workitemId;
	private String userId;
	private String modleId;
	private String operator;
	private String operateTime;
	private String chiefDep;
	private String ordinaryDep;
	private String chiefPerson;
	private String ordinaryPerson;
	private String receivePerson;
	private String receiveDept;
	private String remark;
	private String flag;
	private String taskId;
	private String personName;
	private String dealFile;
	private Integer autScanFlag;
	private String priorities;
	private String externalLaunch;
	
	public RecvMainBo() {
  		super();
  		this.removed = 0;
  		this.operateTime = DateUtil.getCurrDate("yyyy-MM-dd");
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
	/**
	 * @return the swType
	 */
	@Column(name = "SWTYPE", nullable = true, length = 50)
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
	@Column(name = "SWID", nullable = true, length = 100)
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
	@Column(name = "SWDATE", nullable = true, length = 19)
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
	@Column(name = "SWUNIT", nullable = true, length = 100)
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
	@Column(name = "FILEDATE", nullable = true, length = 19)
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
	@Column(name = "SECRET_CLASS", nullable = true, length = 50)
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
	@Column(name = "NUM", nullable = true, length = 50)
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
	@Column(name = "FILEZH", nullable = true, length = 100)
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
	@Column(name = "TITLE", nullable = true, length = 200)
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
	@Column(name = "KEYWORD", nullable = true, length = 100)
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
	@Column(name = "CONTENT", nullable = true, length = 4000)
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
	@Column(name = "ATTACH", nullable = true, length = 4000)
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
	 * @return the nbOption
	 */
	@Column(name = "NB_OPINION", nullable = true, length = 500)
	public String getNbOpinion() {
		return nbOpinion;
	}
	/**
	 * @param nbOption the nbOption to set
	 */
	public void setNbOpinion(String nbOpinion) {
		this.nbOpinion = nbOpinion;
	}
	/**
	 * @return the pbOption
	 */
	@Column(name = "PB_OPINION", nullable = true, length = 500)
	public String getPbOpinion() {
		return pbOpinion;
	}
	/**
	 * @param pbOption the pbOption to set
	 */
	public void setPbOpinion(String pbOpinion) {
		this.pbOpinion = pbOpinion;
	}
	/**
	 * @return the blResult
	 */
	@Column(name = "BL_RESULT", nullable = true, length = 500)
	public String getBlResult() {
		return blResult;
	}
	/**
	 * @param blResult the blResult to set
	 */
	public void setBlResult(String blResult) {
		this.blResult = blResult;
	}
	/**
	 * @return the blMode
	 */
	@Column(name = "BL_MODE", nullable = true, length = 50)
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
	 * @return the lastDate
	 */
	@Column(name = "LASTDATE", nullable = true, length = 19)
	public String getLastDate() {
		return lastDate;
	}
	/**
	 * @param lastDate the lastDate to set
	 */
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	/**
	 * @return the removed
	 */
	@Column(name = "REMOVED")
	public Integer getRemoved() {
		return removed;
	}
	/**
	 * @param removed the removed to set
	 */
	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	/**
	 * @return the instanceId
	 */
	@Column(name = "INSTANCEID", nullable = true, length = 100)
	public String getInstanceId() {
		return instanceId;
	}
	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	/**
	 * @return the workitemId
	 */
	@Column(name = "WORKITEMID", nullable = true, length = 100)
	public String getWorkitemId() {
		return workitemId;
	}
	/**
	 * @param workitemId the workitemId to set
	 */
	public void setWorkitemId(String workitemId) {
		this.workitemId = workitemId;
	}
	/**
	 * @return the userId
	 */
	@Column(name = "USERID", nullable = true, length = 100)
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the modleId
	 */
	@Column(name = "MODELID", nullable = true, length = 100)
	public String getModleId() {
		return modleId;
	}
	/**
	 * @param modleId the modleId to set
	 */
	public void setModleId(String modleId) {
		this.modleId = modleId;
	}
	/**
	 * @return the operator
	 */
	@Column(name = "OPERATOR", nullable = true, length = 100)
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
	 * @return the operateTime
	 */
	@Column(name = "OPERATE_TIME", nullable = true, length = 19)
	public String getOperateTime() {
		return operateTime;
	}
	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	/**
	 * @return the chiefDep
	 */
	@Column(name = "CHIEF_DEP", nullable = true, length = 200)
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
	 * @return the ordinaryDep
	 */
	@Column(name = "ORDINARY_DEP", nullable = true, length = 200)
	public String getOrdinaryDep() {
		return ordinaryDep;
	}
	/**
	 * @param ordinaryDep the ordinaryDep to set
	 */
	public void setOrdinaryDep(String ordinaryDep) {
		this.ordinaryDep = ordinaryDep;
	}
	/**
	 * @return the chiefPerson
	 */
	@Column(name = "CHIEF_PERSON", nullable = true, length = 200)
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
	@Column(name = "ORDINARY_PERSON", nullable = true, length = 200)
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
	 * @return the receivePerson
	 */
	@Column(name = "RECEIVE_PERSON", nullable = true, length = 200)
	public String getReceivePerson() {
		return receivePerson;
	}
	/**
	 * @param receivePerson the receivePerson to set
	 */
	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}
	/**
	 * @return the receiveDept
	 */
	@Column(name = "RECEIVE_DEPT", nullable = true, length = 200)
	public String getReceiveDept() {
		return receiveDept;
	}
	/**
	 * @param receiveDept the receiveDept to set
	 */
	public void setReceiveDept(String receiveDept) {
		this.receiveDept = receiveDept;
	}
	/**
	 * @return the remark
	 */
	@Column(name = "REMARK", nullable = true, length = 500)
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
	 * @return the flag
	 */
	@Column(name = "FLAG", nullable = true, length = 10)
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * @return the taskId
	 */
	@Column(name = "TASKID", nullable = true, length = 200)
	public String getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	/**
	 * @return the personName
	 */
	@Column(name = "PERSONNAME", nullable = true, length = 4000)
	public String getPersonName() {
		return personName;
	}
	/**
	 * @param personName the personName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	/**
	 * @return the dealFile
	 */
	@Column(name = "DEALFILE", nullable = true, length = 4000)
	public String getDealFile() {
		return dealFile;
	}
	/**
	 * @param dealFile the dealFile to set
	 */
	public void setDealFile(String dealFile) {
		this.dealFile = dealFile;
	}
	/**
	 * @return the autScanFlag
	 */
	@Column(name = "AUT_SCAN_FLAG")
	public Integer getAutScanFlag() {
		return autScanFlag;
	}
	/**
	 * @param autScanFlag the autScanFlag to set
	 */
	public void setAutScanFlag(Integer autScanFlag) {
		this.autScanFlag = autScanFlag;
	}
	/**
	 * @return the priorities
	 */
	@Column(name = "PRIORITIES", nullable = true, length = 200)
	public String getPriorities() {
		return priorities;
	}
	/**
	 * @param priorities the priorities to set
	 */
	public void setPriorities(String priorities) {
		this.priorities = priorities;
	}

	@Column(name = "EXTERNAL_LAUNCH", length = 1)
	public String getExternalLaunch() {
		return externalLaunch;
	}


	public void setExternalLaunch(String externalLaunch) {
		this.externalLaunch = externalLaunch;
	}
	
}
