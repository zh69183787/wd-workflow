/**
 * 
 */
package com.wonders.contract.workflow.process.review.model.bo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/** 
 * @ClassName: RecvBo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 上午9:38:00 
 *  
 */
@Entity
@Table(name = "T_CONTRACT_REVIEW_OPTION")
public class ReviewOptionBo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -500946614858553451L;

	
	/** */
	/**
	 * 主键.
	 */
	private String id;
	/** */
	
	private String name;
	/** */
	
	private String removed;
	
	private String question_id;
	
	private ReviewQuestionBo reviewQuestionBo;
	
	private Integer orders;
	
	/** */
	
	public ReviewOptionBo() {
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


	@Column(name = "REMOVED", length = 1)
	public String getRemoved() {
		return removed;
	}


	public void setRemoved(String removed) {
		this.removed = removed;
	}


	@Column(name = "QUESTION_ID", length = 50)
	public String getQuestion_id() {
		return question_id;
	}


	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}


	@ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name = "QUESTION_ID", nullable = true, insertable = false, updatable = false)	
	public ReviewQuestionBo getReviewQuestionBo() {
		return reviewQuestionBo;
	}


	public void setReviewQuestionBo(ReviewQuestionBo reviewQuestionBo) {
		this.reviewQuestionBo = reviewQuestionBo;
	}

	@Column(name = "ORDERS", columnDefinition="NUMBER")
	public Integer getOrders() {
		return orders;
	}


	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
}
