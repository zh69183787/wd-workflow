/**
 * 
 */
package com.wonders.receive.workflow.process.instruct.model.vo;

/** 
 * @ClassName: SignSubVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-3-18 上午09:21:29 
 *  
 */
public class InstructSubVo {
	private String taskId = "";
	
	private String id = "";
	
	private String choice = "";
	
	private String suggest = "";
	
	private String attachId = "";
	
	private String remark = "";

	private String steplabel = "";
	
	private String flowType = "";
	
	private String type = "";
	
	
	@Override
	public String toString() {
		String str = "choice:"+choice+" " +
		"suggest:"+suggest+" " +
		"attachId:"+attachId+" " +
		"remark:"+remark+" " +
		"type:"+type+" " +
				"";
		return str;
	}

	/**
	 * 初始化人员list和领导list
	 */
	public void initList(){
		
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}


	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}


	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSteplabel() {
		return steplabel;
	}

	public void setSteplabel(String steplabel) {
		this.steplabel = steplabel;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
