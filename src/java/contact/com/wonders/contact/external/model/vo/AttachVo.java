package com.wonders.contact.external.model.vo;

import java.util.HashMap;
import java.util.Map;

import com.wonders.contact.common.model.vo.UserInfo;

public class AttachVo {
	public String data = "";;
	public String userInfo = "";;
	public Map<String,String> data_map = new HashMap<String,String>();
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Map<String, String> getData_map() {
		return data_map;
	}
	public void setData_map(Map<String, String> data_map) {
		this.data_map = data_map;
	}
	
	public UserInfo user = new UserInfo();
	
	//public String offset; //已上传文件大小
	//public String fileId;//附件ID
	//public String groupId;//附件组ID
	//public String fileUrl;//附件下载url
	//public String fileName;//附件名
	//public String fileSize;//附件总大小
	//public String fileType;//附件类型
	//public String createTime;//创建
	
	
}
