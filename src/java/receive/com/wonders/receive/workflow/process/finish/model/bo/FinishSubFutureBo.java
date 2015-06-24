/**
 * 
 */
package com.wonders.receive.workflow.process.finish.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wonders.util.DateUtil;

/**
 * @ClassName: RecvMainFutureBo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2013-8-8 下午7:58:51
 * 
 */

@Entity
@Table(name = "T_SIMULATE_FUTURE")
public class FinishSubFutureBo implements Serializable{

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

	// 批示（领导批示 串行）
	private String insInsLdsId ;
	private String insInsLdsName ;
	private String insInsLdsDepId ;
	private String insInsSecsId ;
	private String insInsSecsName ;
	private String insInsSecsDepId ;

	// 拟办建议
	private String sugDepsName;
	private String sugDepLdsId ;
	private String sugDepLdsDepId ;

	// 会签（领导会签 并行）
	private String signLdsId ;
	private String signLdsName ;
	private String signLdsDepId ;
	private String signSecsId ;
	private String signSecsName ;
	private String signSecsDepId ;

	// 审核 （领导审核 串行）
	private String chkLdsId ;
	private String chkLdsName ;
	private String chkLdsDepId ;
	private String chkSecsId ;
	private String chkSecsName ;
	private String chkSecsDepId ;

	// 主办部门
	private String mainDepId ;
	private String mainDepName ;
	private String mainDepRecvId ;
	private String mainDepLdId ;

	// 协办部门
	private String depsId ;
	private String depsName ;
	private String depRecvsId ;
	private String depLdsId ;

	// 半截人
	private String finPersonName ;
	private String finPersonId ;
	private String finPersonDepId ;
	
	private String updatetime;
	
	public FinishSubFutureBo(){
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
	
	@Column(name = "INSSIGNLDSID", nullable = true, length = 200)
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
	
	@Column(name = "INSSIGNLDSDEPID", nullable = true, length = 200)
	public String getInsSignLdsDepId() {
		return insSignLdsDepId;
	}
	public void setInsSignLdsDepId(String insSignLdsDepId) {
		this.insSignLdsDepId = insSignLdsDepId;
	}
	
	@Column(name = "INSSIGNSECSID", nullable = true, length = 200)
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
	
	@Column(name = "INSSIGNSECSDEPID", nullable = true, length = 200)
	public String getInsSignSecsDepId() {
		return insSignSecsDepId;
	}
	public void setInsSignSecsDepId(String insSignSecsDepId) {
		this.insSignSecsDepId = insSignSecsDepId;
	}
	
	@Column(name = "INSINSLDSID", nullable = true, length = 200)
	public String getInsInsLdsId() {
		return insInsLdsId;
	}
	public void setInsInsLdsId(String insInsLdsId) {
		this.insInsLdsId = insInsLdsId;
	}
	
	@Column(name = "INSINSLDSNAME", nullable = true, length = 500)
	public String getInsInsLdsName() {
		return insInsLdsName;
	}
	public void setInsInsLdsName(String insInsLdsName) {
		this.insInsLdsName = insInsLdsName;
	}
	
	@Column(name = "INSINSLDSDEPID", nullable = true, length = 200)
	public String getInsInsLdsDepId() {
		return insInsLdsDepId;
	}
	public void setInsInsLdsDepId(String insInsLdsDepId) {
		this.insInsLdsDepId = insInsLdsDepId;
	}
	
	@Column(name = "INSINSSECSID", nullable = true, length = 200)
	public String getInsInsSecsId() {
		return insInsSecsId;
	}
	public void setInsInsSecsId(String insInsSecsId) {
		this.insInsSecsId = insInsSecsId;
	}
	
	@Column(name = "INSINSSECSNAME", nullable = true, length = 500)
	public String getInsInsSecsName() {
		return insInsSecsName;
	}
	public void setInsInsSecsName(String insInsSecsName) {
		this.insInsSecsName = insInsSecsName;
	}
	
	@Column(name = "INSINSSECSDEPID", nullable = true, length = 200)
	public String getInsInsSecsDepId() {
		return insInsSecsDepId;
	}
	public void setInsInsSecsDepId(String insInsSecsDepId) {
		this.insInsSecsDepId = insInsSecsDepId;
	}
	
	@Column(name = "SUGDEPLDSID", nullable = true, length = 200)
	public String getSugDepLdsId() {
		return sugDepLdsId;
	}
	public void setSugDepLdsId(String sugDepLdsId) {
		this.sugDepLdsId = sugDepLdsId;
	}
	
	@Column(name = "SUGDEPLDSDEPID", nullable = true, length = 200)
	public String getSugDepLdsDepId() {
		return sugDepLdsDepId;
	}
	public void setSugDepLdsDepId(String sugDepLdsDepId) {
		this.sugDepLdsDepId = sugDepLdsDepId;
	}
	
	@Column(name = "SIGNLDSID", nullable = true, length = 200)
	public String getSignLdsId() {
		return signLdsId;
	}
	public void setSignLdsId(String signLdsId) {
		this.signLdsId = signLdsId;
	}
	
	@Column(name = "SIGNLDSNAME", nullable = true, length = 500)
	public String getSignLdsName() {
		return signLdsName;
	}
	public void setSignLdsName(String signLdsName) {
		this.signLdsName = signLdsName;
	}
	
	@Column(name = "SIGNLDSDEPID", nullable = true, length = 200)
	public String getSignLdsDepId() {
		return signLdsDepId;
	}
	public void setSignLdsDepId(String signLdsDepId) {
		this.signLdsDepId = signLdsDepId;
	}
	
	@Column(name = "SIGNSECSID", nullable = true, length = 200)
	public String getSignSecsId() {
		return signSecsId;
	}
	public void setSignSecsId(String signSecsId) {
		this.signSecsId = signSecsId;
	}
	
	@Column(name = "SIGNSECSNAME", nullable = true, length = 500)
	public String getSignSecsName() {
		return signSecsName;
	}
	public void setSignSecsName(String signSecsName) {
		this.signSecsName = signSecsName;
	}
	
	@Column(name = "SIGNSECSDEPID", nullable = true, length = 200)
	public String getSignSecsDepId() {
		return signSecsDepId;
	}
	public void setSignSecsDepId(String signSecsDepId) {
		this.signSecsDepId = signSecsDepId;
	}
	
	@Column(name = "CHKLDSID", nullable = true, length = 200)
	public String getChkLdsId() {
		return chkLdsId;
	}
	public void setChkLdsId(String chkLdsId) {
		this.chkLdsId = chkLdsId;
	}
	
	@Column(name = "CHKLDSNAME", nullable = true, length = 500)
	public String getChkLdsName() {
		return chkLdsName;
	}
	public void setChkLdsName(String chkLdsName) {
		this.chkLdsName = chkLdsName;
	}
	
	@Column(name = "CHKLDSDEPID", nullable = true, length = 200)
	public String getChkLdsDepId() {
		return chkLdsDepId;
	}
	public void setChkLdsDepId(String chkLdsDepId) {
		this.chkLdsDepId = chkLdsDepId;
	}
	
	@Column(name = "CHKSECSID", nullable = true, length = 200)
	public String getChkSecsId() {
		return chkSecsId;
	}
	public void setChkSecsId(String chkSecsId) {
		this.chkSecsId = chkSecsId;
	}
	
	@Column(name = "CHKSECSNAME", nullable = true, length = 500)
	public String getChkSecsName() {
		return chkSecsName;
	}
	public void setChkSecsName(String chkSecsName) {
		this.chkSecsName = chkSecsName;
	}
	
	@Column(name = "CHKSECSDEPID", nullable = true, length = 200)
	public String getChkSecsDepId() {
		return chkSecsDepId;
	}
	public void setChkSecsDepId(String chkSecsDepId) {
		this.chkSecsDepId = chkSecsDepId;
	}
	
	@Column(name = "MAINDEPID", nullable = true, length = 200)
	public String getMainDepId() {
		return mainDepId;
	}
	public void setMainDepId(String mainDepId) {
		this.mainDepId = mainDepId;
	}
	
	@Column(name = "MAINDEPNAME", nullable = true, length = 500)
	public String getMainDepName() {
		return mainDepName;
	}
	public void setMainDepName(String mainDepName) {
		this.mainDepName = mainDepName;
	}
	
	@Column(name = "MAINDEPRECVID", nullable = true, length = 200)
	public String getMainDepRecvId() {
		return mainDepRecvId;
	}
	public void setMainDepRecvId(String mainDepRecvId) {
		this.mainDepRecvId = mainDepRecvId;
	}
	
	@Column(name = "MAINDEPLDID", nullable = true, length = 200)
	public String getMainDepLdId() {
		return mainDepLdId;
	}
	public void setMainDepLdId(String mainDepLdId) {
		this.mainDepLdId = mainDepLdId;
	}
	
	@Column(name = "DEPSID", nullable = true, length = 200)
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
	
	@Column(name = "DEPRECVSID", nullable = true, length = 200)
	public String getDepRecvsId() {
		return depRecvsId;
	}
	public void setDepRecvsId(String depRecvsId) {
		this.depRecvsId = depRecvsId;
	}
	
	@Column(name = "DEPLDSID", nullable = true, length = 200)
	public String getDepLdsId() {
		return depLdsId;
	}
	public void setDepLdsId(String depLdsId) {
		this.depLdsId = depLdsId;
	}
	
	@Column(name = "FINPERSONID", nullable = true, length = 200)
	public String getFinPersonId() {
		return finPersonId;
	}
	public void setFinPersonId(String finPersonId) {
		this.finPersonId = finPersonId;
	}
	
	@Column(name = "FINPERSONDEPID", nullable = true, length = 200)
	public String getFinPersonDepId() {
		return finPersonDepId;
	}
	public void setFinPersonDepId(String finPersonDepId) {
		this.finPersonDepId = finPersonDepId;
	}
	
	@Column(name = "SUGDEPSNAME", nullable = true, length = 500)
	public String getSugDepsName() {
		return sugDepsName;
	}
	public void setSugDepsName(String sugDepsName) {
		this.sugDepsName = sugDepsName;
	}
	
	@Column(name = "FINPERSONNAME", nullable = true, length = 500)
	public String getFinPersonName() {
		return finPersonName;
	}
	public void setFinPersonName(String finPersonName) {
		this.finPersonName = finPersonName;
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
	
	
	

}
