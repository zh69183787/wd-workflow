/**
 * 
 */
package com.wonders.project.workflow.model.vo;

/** 
 * @ClassName: OperateVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-16 上午10:17:30 
 *  
 */
public class OperateVo {
	private String taskId = "";
	private String id = "";
	private String choice = "";
	private String suggest = "";
	private String attachId = "";
	private String steplabel = "";
	private String flowType = "";
	
	
	public String getAttachId() {
		return attachId;
	}
	public String getChoice() {
		return choice;
	}
	public String getId() {
		return id;
	}
	public String getSteplabel() {
		return steplabel;
	}
	public String getSuggest() {
		return suggest;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setSteplabel(String steplabel) {
		this.steplabel = steplabel;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
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
	
	@Override
	public String toString(){
		return "id="+id;
	}
}
