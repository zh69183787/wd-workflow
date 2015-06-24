package com.wonders.dept.workflow.model.bo;

import com.google.gson.annotations.Expose;
import com.wonders.util.StringUtil;

public class MessageBo {
	public MessageBo(String textCn,String actionField,String type){
		this.textCn = StringUtil.getNotNullValueString(textCn);
		this.actionField = StringUtil.getNotNullValueString(actionField);
		this.type = StringUtil.getNotNullValueString(type);
	}

	public MessageBo(String textCn,String actionField){
		this.textCn = StringUtil.getNotNullValueString(textCn);
		this.actionField = StringUtil.getNotNullValueString(actionField);
	}
	
	
	public MessageBo(String textCn){
		this.textCn = StringUtil.getNotNullValueString(textCn);
	}

	
	/**
	 * 提示信息
	 */
	@Expose
	public String textCn = "";
	
	
	/**
	 * 对应action vo属性
	 */
	@Expose
	public String actionField = "";
	
	
	/**
	 * 提示类型
	 */
	@Expose
	public String type = "";
}
