/**
 * 
 */
package com.wonders.receive.workflow.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * @ClassName: Subprocess 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 上午11:06:00 
 *  
 */
@Entity
@Table(name = "T_SUBPROCESS")
public class Subprocess implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 855182949916452534L;
	private String guid;
	private String pname;
	private String pincident;
	private String cname;
	private String cincident;
	/**
	 * @return the guid
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "GUID")
	public String getGuid() {
		return guid;
	}
	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
	/**
	 * @return the pname
	 */
	@Column(name = "PNAME", nullable = true, length = 50)
	public String getPname() {
		return pname;
	}
	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}
	/**
	 * @return the pincident
	 */
	@Column(name = "PINCIDENT", nullable = true, length = 50)
	public String getPincident() {
		return pincident;
	}
	/**
	 * @param pincident the pincident to set
	 */
	public void setPincident(String pincident) {
		this.pincident = pincident;
	}
	/**
	 * @return the cname
	 */
	@Column(name = "CNAME", nullable = true, length = 50)
	public String getCname() {
		return cname;
	}
	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}
	/**
	 * @return the cincident
	 */
	@Column(name = "CINCIDENT", nullable = true, length = 50)
	public String getCincident() {
		return cincident;
	}
	/**
	 * @param cincident the cincident to set
	 */
	public void setCincident(String cincident) {
		this.cincident = cincident;
	}

	
	
	
	

}
