package com.wonders.contact.external.service;

import java.io.BufferedInputStream;

import com.wonders.contact.external.model.data.AttachData;
import com.wonders.contact.external.model.data.AttachDetailData;
import com.wonders.contact.external.model.data.DeptContactData;
import com.wonders.contact.external.model.data.TodoData;
import com.wonders.contact.external.model.vo.AttachVo;
import com.wonders.contact.external.model.vo.OperateVo;
import com.wonders.contact.external.model.vo.TodoVo;

public interface ApiService {

	//获取待办项列表
	public TodoData getTodoData(TodoVo vo);
	
	//获取工作联系单model
	public DeptContactData getDeptContactData(String todoId);
	
	//获取附件组
	public AttachData getAttachList(String groupId);
	
	//获取附件详细
	public String getAttachDetail(String attachId);
	
	//办理
	public void prepareOperateData(OperateVo vo);
	
	//断点续传附件之 获取附件状态
	public AttachDetailData getAttachStatus(AttachVo attachVo);
	
	//断点续传附件之 上传附件
	public void attachUpload(AttachVo attachVo,BufferedInputStream stream);
	
	//断点续传附件之 确认上传
	public AttachDetailData attachCommit(AttachVo attachVo);


}
