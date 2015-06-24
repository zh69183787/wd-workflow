/**
 * 
 */
package com.wonders.contract.workflow.process.review.model.bo;

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
@Table(name = "T_CONTRACT_REVIEW_SUGGEST")
public class ReviewSuggestBo implements Serializable{
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
	/**
	 * 项目名称.
	 */
	private String userName;
	/** */
	/**
	 * 项目编号.
	 */
	private String loginName;
	/** */
	/**
	 * 项目批文号.
	 */
	private String updateTime;
	/** */
	/**
	 * 项目公司.
	 */
	private String questionId;
	/** */
	
	private String optionId;
	/**
	 * 项目公司负责人.
	 */
	private String reviewId;
	/** */
	
	private String removed;
	
	public ReviewSuggestBo() {
  		super();
  		this.removed = "0";
  		this.updateTime = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
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

	@Column(name = "USER_NAME", length = 200)
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "LOGIN_NAME", length = 200)
	public String getLoginName() {
		return loginName;
	} 


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "UPDATE_TIME", length = 200)
	public String getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "QUESTION_ID", length = 200)
	public String getQuestionId() {
		return questionId;
	}


	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	@Column(name = "OPTION_ID", length = 200)
	public String getOptionId() {
		return optionId;
	}


	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	@Column(name = "REVIEW_ID", length = 200)
	public String getReviewId() {
		return reviewId;
	}


	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	@Column(name = "REMOVED", length = 1)
	public String getRemoved() {
		return removed;
	}


	public void setRemoved(String removed) {
		this.removed = removed;
	}
	
	
}
