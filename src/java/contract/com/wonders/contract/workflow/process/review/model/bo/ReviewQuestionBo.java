/**
 * 
 */
package com.wonders.contract.workflow.process.review.model.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/** 
 * @ClassName: RecvBo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 上午9:38:00 
 *  
 */
@Entity
@Table(name = "T_CONTRACT_REVIEW_QUESTION")
public class ReviewQuestionBo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -500946614858553451L;

	
	/** */
	
	private String id;
	/** */
	
	private String name;
	/** */

	private String type;
	/** */
	
	private String removed;
	
	private String purchaseType;
	
	private List<ReviewOptionBo> options = new ArrayList<ReviewOptionBo>();
	
	private Integer orders;
	
	public ReviewQuestionBo() {
  		super();
  		this.removed = "0";
  	}
	
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "ID")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Column(name = "NAME", length = 200)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Column(name = "TYPE", length = 200)
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	@Column(name = "REMOVED", length = 1)
	public String getRemoved() {
		return removed;
	}


	public void setRemoved(String removed) {
		this.removed = removed;
	}


	@OneToMany(mappedBy="reviewQuestionBo", cascade = {CascadeType.ALL},fetch=FetchType.LAZY)
	@Where(clause="removed = 0")
	@OrderBy("orders asc")
	public List<ReviewOptionBo> getOptions() {
		return options;
	}


	public void setOptions(List<ReviewOptionBo> options) {
		this.options = options;
	}


	@Column(name = "PURCHASE_TYPE", length = 200)
	public String getPurchaseType() {
		return purchaseType;
	}


	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}


	@Column(name = "ORDERS", columnDefinition="NUMBER")
	public Integer getOrders() {
		return orders;
	}


	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	
}
