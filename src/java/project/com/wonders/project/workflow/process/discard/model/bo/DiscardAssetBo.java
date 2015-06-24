/**
 * 
 */
package com.wonders.project.workflow.process.discard.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wonders.util.DateUtil;

/** 
 * @ClassName: DiscardBo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 上午9:38:00 
 *  
 */
@Entity
@Table(name = "PCL_PROJECT_DISCARD_ASSET")
public class DiscardAssetBo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -500946614858553451L;
	private String id;
	private String mainId;	//销项主表ID
	private String assetNo;	//资产编号
	private String assetName;	//资产名称  
	private String operateProjectAsset;  //资产标识
	private String assetType;	//资产属性
	private String assetTypeOriginal;	//资产属性
	private String originalValue;	//原值
	private String costType;	//成本属性
	private String netValue;	//净值
	private String maintainCost;		//入账原值\费用
	private String lifeLeft;	//剩余年限
	private String lifeExtend;	//需延长年限
	private String content;	//维修内容

	private String operateTime;		//更新时间
	private String removed;
	
	private String assetRecordId;	//资产履历ID
	
	public DiscardAssetBo() {
  		super();
  		this.operateTime = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
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
	
	@Column(name = "ASSET_RECORD_ID", nullable = true, length = 40)
	public String getAssetRecordId() {
		return assetRecordId;
	}

	public void setAssetRecordId(String assetRecordId) {
		this.assetRecordId = assetRecordId;
	}
	
	@Column(name = "MAIN_ID", nullable = true, length = 40)
	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	@Column(name = "ASSET_NO", nullable = true, length = 200)
	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	@Column(name = "ASSET_NAME", nullable = true, length = 200)
	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	@Column(name = "OPERATE_PROJECT_ASSET", nullable = true, length = 200)
	public String getOperateProjectAsset() {
		return operateProjectAsset;
	}

	public void setOperateProjectAsset(String operateProjectAsset) {
		this.operateProjectAsset = operateProjectAsset;
	}

	@Column(name = "ASSET_TYPE", nullable = true, length = 40)
	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	@Column(name = "ORIGINAL_VALUE", nullable = true, length = 200)
	public String getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}

	@Column(name = "COST_TYPE", nullable = true, length = 40)
	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	@Column(name = "NET_VALUE", nullable = true, length = 40)
	public String getNetValue() {
		return netValue;
	}

	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}

	@Column(name = "MAINTAIN_COST", nullable = true, length = 200)
	public String getMaintainCost() {
		return maintainCost;
	}

	public void setMaintainCost(String maintainCost) {
		this.maintainCost = maintainCost;
	}

	@Column(name = "LIFE_LEFT", nullable = true, length = 20)
	public String getLifeLeft() {
		return lifeLeft;
	}

	public void setLifeLeft(String lifeLeft) {
		this.lifeLeft = lifeLeft;
	}

	@Column(name = "LIFE_EXTEND", nullable = true, length = 20)
	public String getLifeExtend() {
		return lifeExtend;
	}

	public void setLifeExtend(String lifeExtend) {
		this.lifeExtend = lifeExtend;
	}

	@Column(name = "OPERATE_TIME", nullable = true, length = 19)
	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "ASSET_TYPE_ORIGINAL", nullable = true, length = 40)
	public String getAssetTypeOriginal() {
		return assetTypeOriginal;
	}

	public void setAssetTypeOriginal(String assetTypeOriginal) {
		this.assetTypeOriginal = assetTypeOriginal;
	}

	@Column(name = "CONTENT", nullable = true, length = 2000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @return the removed
	 */
	@Column(name = "REMOVED")
	public String getRemoved() {
		return removed;
	}
	/**
	 * @param removed the removed to set
	 */
	public void setRemoved(String removed) {
		this.removed = removed;
	}
	
}
