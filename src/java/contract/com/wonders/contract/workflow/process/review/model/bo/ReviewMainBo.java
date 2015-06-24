/**
 * 
 */
package com.wonders.contract.workflow.process.review.model.bo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "T_CONTRACT_REVIEW")
public class ReviewMainBo implements Serializable{
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
	private String projectName;
	/** */
	/**
	 * 项目编号.
	 */
	private String projectIdentifier;
	/** */
	/**
	 * 项目批文号.
	 */
	private String projectNum;
	/** */
	/**
	 * 项目公司.
	 */
	private String projectCompany;
	/** */
	
	private String projectChargeDept;
	/**
	 * 项目公司负责人.
	 */
	private String projectCharge;
	/** */
	/**
	 * 合同编号.
	 */
	private String contractIdentifier;
	/** */
	/**
	 * 自有编号.
	 */
	private String contractSelfNum;
	/** */
	/**
	 * 合同名称.
	 */
	private String contractName;
	/** */
	/**
	 * 合同金额.
	 */
	private String contractMoney;
	/** */
	/**
	 * 合同金额分类（下拉框）总价闭口 单价核算 对方支付.
	 */
	private String contractMoneyType;
	/** */
	/**
	 * 合同预算.
	 */
	private String contractBudget;
	/** */
	/**
	 * 采购方式.
	 */
	private String purchaseType;
	/** */
	/**
	 * 合同分类1.
	 */
	private String contractType1;
	/** */
	/**
	 * 合同分类2.
	 */
	private String contractType2;
	/** */
	/**
	 * 合同分类.
	 */
	private String contractType;
	/** */
	/**
	 * 合同分组.
	 */
	private String contractGroup;
	/** */
	/**
	 * 对方公司.
	 */
	private String oppositeCompany;
	/** */
	/**
	 * 经办人.
	 */
	private String dealPerson;
	/** */
	/**
	 * 登记人.
	 */
	private String regPerson;
	/** */
	/**
	 * 登记人账号.
	 */
	private String regLoginName;
	/** */
	/**
	 * 登记时间.
	 */
	private String regTime;
	/** */
	/**
	 * 通过时间.
	 */
	private String passTime;
	/** */
	/**
	 * 签约时间.
	 */
	private String signTime;
	/** */
	/**
	 * 履行期限开始.
	 */
	private String execPeriodStart;
	/** */
	/**
	 * 履行期限结束.
	 */
	private String execPeriodEnd;
	/** */
	/**
	 * 经办部门意见.
	 */
	private String dealDeptSuggest;
	/** */
	/**
	 * 备注.
	 */
	private String remark;
	/** */
	/**
	 * 附件.
	 */
	private String attach;
	/** */
	/**
	 * 实例号.
	 */
	private String incident;
	/** */
	/**
	 * 流程名称.
	 */
	private String process;
	/** */
	/**
	 * 流程类型.
	 */
	private String typeId;
	/** */
	/**
	 * 更新时间.
	 */
	private String operateTime;
	/** */
	/**
	 * 是否归档.
	 */
	private String flag;
	/** */
	/**
	 */
	private Integer autScanFlag;
	/** */
	/**
	 */
	private Integer removed;
	/** */
	/**
	 * 项目表主键ID.
	 */
	private String projectId;
	/** */
	/**
	 */
	private String contractType1Id;
	/** */
	/**
	 */
	private String contractType2Id;
	/** */
	/**
	 */
	private String contractGroupId;
	/** */
	/**
	 */
	private String purchaseTypeId;
	/** */
	/**
	 */
	private String contractMoneyTypeId;

	
	private String moneySource;
	
	private String externalLaunch;
	
	private String kpiControl;
	
	public ReviewMainBo() {
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
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "PROJECT_NAME", length = 200)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "PROJECT_IDENTIFIER", length = 200)
	public String getProjectIdentifier() {
		return this.projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	@Column(name = "PROJECT_NUM", length = 200)
	public String getProjectNum() {
		return this.projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}

	@Column(name = "PROJECT_COMPANY", length = 200)
	public String getProjectCompany() {
		return this.projectCompany;
	}

	public void setProjectCompany(String projectCompany) {
		this.projectCompany = projectCompany;
	}

	@Column(name = "PROJECT_CHARGE", length = 200)
	public String getProjectCharge() {
		return this.projectCharge;
	}

	public void setProjectCharge(String projectCharge) {
		this.projectCharge = projectCharge;
	}
	
	@Column(name = "PROJECT_CHARGE_DEPT", length = 200)
	public String getProjectChargeDept() {
		return this.projectChargeDept;
	}

	public void setProjectChargeDept(String projectChargeDept) {
		this.projectChargeDept = projectChargeDept;
	}

	@Column(name = "CONTRACT_IDENTIFIER", length = 200)
	public String getContractIdentifier() {
		return this.contractIdentifier;
	}

	public void setContractIdentifier(String contractIdentifier) {
		this.contractIdentifier = contractIdentifier;
	}

	@Column(name = "CONTRACT_SELF_NUM", length = 200)
	public String getContractSelfNum() {
		return this.contractSelfNum;
	}

	public void setContractSelfNum(String contractSelfNum) {
		this.contractSelfNum = contractSelfNum;
	}

	@Column(name = "CONTRACT_NAME", length = 200)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "CONTRACT_MONEY", length = 50)
	public String getContractMoney() {
		return this.contractMoney;
	}

	public void setContractMoney(String contractMoney) {
		this.contractMoney = contractMoney;
	}

	@Column(name = "CONTRACT_MONEY_TYPE", length = 50)
	public String getContractMoneyType() {
		return this.contractMoneyType;
	}

	public void setContractMoneyType(String contractMoneyType) {
		this.contractMoneyType = contractMoneyType;
	}

	@Column(name = "CONTRACT_BUDGET", length = 50)
	public String getContractBudget() {
		return this.contractBudget;
	}

	public void setContractBudget(String contractBudget) {
		this.contractBudget = contractBudget;
	}

	@Column(name = "PURCHASE_TYPE", length = 50)
	public String getPurchaseType() {
		return this.purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	@Column(name = "CONTRACT_TYPE1", length = 50)
	public String getContractType1() {
		return this.contractType1;
	}

	public void setContractType1(String contractType1) {
		this.contractType1 = contractType1;
	}

	@Column(name = "CONTRACT_TYPE2", length = 50)
	public String getContractType2() {
		return this.contractType2;
	}

	public void setContractType2(String contractType2) {
		this.contractType2 = contractType2;
	}

	@Column(name = "CONTRACT_TYPE", length = 50)
	public String getContractType() {
		return this.contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	@Column(name = "CONTRACT_GROUP", length = 50)
	public String getContractGroup() {
		return this.contractGroup;
	}

	public void setContractGroup(String contractGroup) {
		this.contractGroup = contractGroup;
	}

	@Column(name = "OPPOSITE_COMPANY", length = 200)
	public String getOppositeCompany() {
		return this.oppositeCompany;
	}

	public void setOppositeCompany(String oppositeCompany) {
		this.oppositeCompany = oppositeCompany;
	}

	@Column(name = "DEAL_PERSON", length = 50)
	public String getDealPerson() {
		return this.dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	@Column(name = "REG_PERSON", length = 50)
	public String getRegPerson() {
		return this.regPerson;
	}

	public void setRegPerson(String regPerson) {
		this.regPerson = regPerson;
	}

	@Column(name = "REG_LOGIN_NAME", length = 50)
	public String getRegLoginName() {
		return this.regLoginName;
	}

	public void setRegLoginName(String regLoginName) {
		this.regLoginName = regLoginName;
	}

	@Column(name = "REG_TIME", length = 50)
	public String getRegTime() {
		return this.regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	@Column(name = "PASS_TIME", length = 50)
	public String getPassTime() {
		return this.passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

	@Column(name = "SIGN_TIME", length = 50)
	public String getSignTime() {
		return this.signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	@Column(name = "EXEC_PERIOD_START", length = 50)
	public String getExecPeriodStart() {
		return this.execPeriodStart;
	}

	public void setExecPeriodStart(String execPeriodStart) {
		this.execPeriodStart = execPeriodStart;
	}

	@Column(name = "EXEC_PERIOD_END", length = 50)
	public String getExecPeriodEnd() {
		return this.execPeriodEnd;
	}

	public void setExecPeriodEnd(String execPeriodEnd) {
		this.execPeriodEnd = execPeriodEnd;
	}

	@Lob
    @Basic(fetch=FetchType.EAGER)
    @Column(name = "DEAL_DEPT_SUGGEST",columnDefinition = "CLOB")
	public String getDealDeptSuggest() {
		return this.dealDeptSuggest;
	}

	public void setDealDeptSuggest(String dealDeptSuggest) {
		this.dealDeptSuggest = dealDeptSuggest;
	}

	@Lob
    @Basic(fetch=FetchType.EAGER)
    @Column(name = "REMARK",columnDefinition = "CLOB")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "ATTACH", length = 100)
	public String getAttach() {
		return this.attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	@Column(name = "INCIDENT", length = 50)
	public String getIncident() {
		return this.incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
	}

	@Column(name = "PROCESS", length = 50)
	public String getProcess() {
		return this.process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	@Column(name = "TYPEID", length = 50)
	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Column(name = "OPERATE_TIME", length = 50)
	public String getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "FLAG", length = 1)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "AUT_SCAN_FLAG")
	public Integer getAutScanFlag() {
		return this.autScanFlag;
	}

	public void setAutScanFlag(Integer autScanFlag) {
		this.autScanFlag = autScanFlag;
	}

	@Column(name = "REMOVED")
	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

	@Column(name = "PROJECT_ID", length = 50)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "CONTRACT_TYPE1_ID", length = 50)
	public String getContractType1Id() {
		return this.contractType1Id;
	}

	public void setContractType1Id(String contractType1Id) {
		this.contractType1Id = contractType1Id;
	}

	@Column(name = "CONTRACT_TYPE2_ID", length = 50)
	public String getContractType2Id() {
		return this.contractType2Id;
	}

	public void setContractType2Id(String contractType2Id) {
		this.contractType2Id = contractType2Id;
	}

	@Column(name = "CONTRACT_GROUP_ID", length = 50)
	public String getContractGroupId() {
		return this.contractGroupId;
	}

	public void setContractGroupId(String contractGroupId) {
		this.contractGroupId = contractGroupId;
	}

	@Column(name = "PURCHASE_TYPE_ID", length = 50)
	public String getPurchaseTypeId() {
		return this.purchaseTypeId;
	}

	public void setPurchaseTypeId(String purchaseTypeId) {
		this.purchaseTypeId = purchaseTypeId;
	}

	@Column(name = "CONTRACT_MONEY_TYPE_ID", length = 50)
	public String getContractMoneyTypeId() {
		return this.contractMoneyTypeId;
	}

	public void setContractMoneyTypeId(String contractMoneyTypeId) {
		this.contractMoneyTypeId = contractMoneyTypeId;
	}


	@Lob
    @Basic(fetch=FetchType.EAGER)
    @Column(name = "MONEY_SOURCE",columnDefinition = "CLOB")
	public String getMoneySource() {
		return moneySource;
	}


	public void setMoneySource(String moneySource) {
		this.moneySource = moneySource;
	}


	@Column(name = "EXTERNAL_LAUNCH", length = 1)
	public String getExternalLaunch() {
		return externalLaunch;
	}


	public void setExternalLaunch(String externalLaunch) {
		this.externalLaunch = externalLaunch;
	}
	
	@Column(name = "KPI_CONTROL", length = 200)
	public String getKpiControl() {
		return kpiControl;
	}


	public void setKpiControl(String kpiControl) {
		this.kpiControl = kpiControl;
	}
}
