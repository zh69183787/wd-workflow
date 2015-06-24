package com.wonders.discipline.workflow.process.recv.model.bo;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wonders.util.DateUtil;

/**
 * @ClassName: DcpSimulateFutureBo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2013-8-8 下午7:58:51
 * 
 */

@Entity
@Table(name = "T_SIMULATE_FUTURE")
public class DcpSimulateFutureBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 392302411126935986L;
	private String id;
	//基本信息
	private String pname;
	private String pincident;
	private String choice;
	
	// 批示（领导会签 并行）
	private String insSignLdsId ;
	private String insSignLdsName ;
	private String insSignLdsDepId ;
	private String insSignSecsId ;
	private String insSignSecsName ;
	private String insSignSecsDepId ;


	// 协办部门
	private String depsId ;
	private String depsName ;
	private String depRecvsId ;
	private String depLdsId ;

	
	private String updatetime;
	
	private String suggest;
	
	public DcpSimulateFutureBo(){
		this.updatetime = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
	}
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "PNAME", nullable = true, length = 50)
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	
	@Column(name = "PINCIDENT", nullable = true, length = 50)
	public String getPincident() {
		return pincident;
	}
	public void setPincident(String pincident) {
		this.pincident = pincident;
	}
	
	@Column(name = "INSSIGNLDSID", nullable = true, length = 500)
	public String getInsSignLdsId() {
		return insSignLdsId;
	}
	public void setInsSignLdsId(String insSignLdsId) {
		this.insSignLdsId = insSignLdsId;
	}
	
	@Column(name = "INSSIGNLDSNAME", nullable = true, length = 500)
	public String getInsSignLdsName() {
		return insSignLdsName;
	}
	public void setInsSignLdsName(String insSignLdsName) {
		this.insSignLdsName = insSignLdsName;
	}
	
	@Column(name = "INSSIGNLDSDEPID", nullable = true, length = 500)
	public String getInsSignLdsDepId() {
		return insSignLdsDepId;
	}
	public void setInsSignLdsDepId(String insSignLdsDepId) {
		this.insSignLdsDepId = insSignLdsDepId;
	}
	
	@Column(name = "INSSIGNSECSID", nullable = true, length = 500)
	public String getInsSignSecsId() {
		return insSignSecsId;
	}
	public void setInsSignSecsId(String insSignSecsId) {
		this.insSignSecsId = insSignSecsId;
	}
	
	@Column(name = "INSSIGNSECSNAME", nullable = true, length = 500)
	public String getInsSignSecsName() {
		return insSignSecsName;
	}
	public void setInsSignSecsName(String insSignSecsName) {
		this.insSignSecsName = insSignSecsName;
	}
	
	@Column(name = "INSSIGNSECSDEPID", nullable = true, length = 500)
	public String getInsSignSecsDepId() {
		return insSignSecsDepId;
	}
	public void setInsSignSecsDepId(String insSignSecsDepId) {
		this.insSignSecsDepId = insSignSecsDepId;
	}
	
	
	@Column(name = "DEPSID", nullable = true, length = 500)
	public String getDepsId() {
		return depsId;
	}
	public void setDepsId(String depsId) {
		this.depsId = depsId;
	}
	
	@Column(name = "DEPSNAME", nullable = true, length = 500)
	public String getDepsName() {
		return depsName;
	}
	public void setDepsName(String depsName) {
		this.depsName = depsName;
	}
	
	@Column(name = "DEPRECVSID", nullable = true, length = 500)
	public String getDepRecvsId() {
		return depRecvsId;
	}
	public void setDepRecvsId(String depRecvsId) {
		this.depRecvsId = depRecvsId;
	}
	
	@Column(name = "DEPLDSID", nullable = true, length = 500)
	public String getDepLdsId() {
		return depLdsId;
	}
	public void setDepLdsId(String depLdsId) {
		this.depLdsId = depLdsId;
	}
	
	@Column(name = "UPDATETIME", nullable = true, length = 50)
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "CHOICE", nullable = true, length = 50)
	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	@Column(name = "SUGGEST", nullable = true, length = 2000)
	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	
	
	

}
