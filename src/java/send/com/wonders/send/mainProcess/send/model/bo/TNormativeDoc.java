package com.wonders.send.mainProcess.send.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.wonders.util.DateUtil;


@Entity
@Table(name = "T_NORMATIVE_DOC", schema = "STPT")
public class TNormativeDoc implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5594969807766980507L;
	private String id;
	private String mainId;
	private String title;
	private String code;
	private String remark;
	private String attach;
	private String status;
	private String operateTime;
	private String operatePerson;
	private String removed;
	
	public TNormativeDoc(){
		this.operateTime = DateUtil.getCurrDate(DateUtil.TIME_FORMAT);
		this.remark = "0";
	}
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "MAIN_ID", length = 50)
	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	
	@Column(name = "TITLE", length = 200)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "CODE", length = 50)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "REMARK", length = 4000)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "ATTACH", length = 50)
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "OPERATE_TIME", length = 50)
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	@Column(name = "OPERATE_PERSON", length = 50)
	public String getOperatePerson() {
		return operatePerson;
	}
	public void setOperatePerson(String operatePerson) {
		this.operatePerson = operatePerson;
	}
	
	@Column(name = "REMOVED", length = 1)
	public String getRemoved() {
		return removed;
	}
	public void setRemoved(String removed) {
		this.removed = removed;
	}

	@Transient
	public String getStatusCn(){
		if("2".equals(this.status)){
			return "部分有效";
		}else if("3".equals(this.status)){
			return "失效";
		}else if("4".equals(this.status)){
			return "废止";
		}
		return "";
	}
}
