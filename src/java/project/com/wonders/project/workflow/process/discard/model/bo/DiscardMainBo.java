/**
 * 
 */
package com.wonders.project.workflow.process.discard.model.bo;

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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.wonders.util.DateUtil;

/** 
 * @ClassName: DiscardBo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 上午9:38:00 
 *  
 */
@Entity
@Table(name = "PCL_PROJECT_DISCARD")
public class DiscardMainBo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -500946614858553451L;
	private String id;
	private String projectId;	//项目ID
	private String projectNo;	//项目编号
	private String projectName;	//项目名称  
	private String dispatchNo;  //立项批文号
	private String startDate;	//开工日期
	private String finishDate;	//竣工日期
	private String finishPrice;	//竣工决算价
	private String approvalBudget;	//批复概算
	private String moneySource;		//资金来源
	private String costType;		//成本属性
	private String attach;
	private Integer removed;
	private String instanceId;	//实例号
	private String modleId;		//流程名称
	
	private String operator;	//申请人工号
	private String operatorName;	//申请人姓名
	private String operatorMobile;	//申请人电话
	private String operateTime;		//更新时间
	
	private String remark;		//备注
	private String flag;		//申报状态
	private String taskId;
	
	private List<DiscardAssetBo> assets = new ArrayList<DiscardAssetBo>();
	
	public DiscardMainBo() {
  		super();
  		this.removed = 0;
  		this.operateTime = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
  		this.flag = "0";
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
	
	@Column(name = "PROJECT_ID", nullable = true, length = 40)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Column(name = "PROJECT_NO", nullable = true, length = 200)
	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	@Column(name = "PROJECT_NAME", nullable = true, length = 200)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@Column(name = "DISPATCH_NO", nullable = true, length = 200)
	public String getDispatchNo() {
		return dispatchNo;
	}

	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}
	
	@Column(name = "START_DATE", nullable = true, length = 19)
	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Column(name = "FINISH_DATE", nullable = true, length = 19)
	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	@Column(name = "FINISH_PRICE", nullable = true, length = 200)
	public String getFinishPrice() {
		return finishPrice;
	}

	public void setFinishPrice(String finishPrice) {
		this.finishPrice = finishPrice;
	}
	@Column(name = "APPROVAL_BUDGET", nullable = true, length = 200)
	public String getApprovalBudget() {
		return approvalBudget;
	}

	public void setApprovalBudget(String approvalBudget) {
		this.approvalBudget = approvalBudget;
	}
	@Column(name = "MONEY_SOURCE", nullable = true, length = 200)
	public String getMoneySource() {
		return moneySource;
	}

	public void setMoneySource(String moneySource) {
		this.moneySource = moneySource;
	}

	/**
	 * @return the attach
	 */
	@Column(name = "ATTACH", nullable = true, length = 40)
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

	@Column(name = "OPERATOR_NAME", nullable = true, length = 20)
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	@Column(name = "OPERATOR_MOBILE", nullable = true, length = 20)
	public String getOperatorMobile() {
		return operatorMobile;
	}

	public void setOperatorMobile(String operatorMobile) {
		this.operatorMobile = operatorMobile;
	}
	
	@Column(name = "COST_TYPE", nullable = true, length = 20)
	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	@OneToMany(mappedBy="mainId", cascade = {CascadeType.ALL},fetch=FetchType.LAZY)
	@Where(clause="removed = '0'")
	public List<DiscardAssetBo> getAssets() {
		return assets;
	}

	public void setAssets(List<DiscardAssetBo> assets) {
		this.assets = assets;
	}
	
}
