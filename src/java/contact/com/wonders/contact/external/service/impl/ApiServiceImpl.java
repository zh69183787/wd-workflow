package com.wonders.contact.external.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.model.vo.UploadFile;
import com.wonders.attach.service.FjshService;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

import com.wonders.contact.approvedInfo.service.ApprovedInfoDeptContactService;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.service.CommonService;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.dao.DeptContactDao;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.bo.TDeptContactTree;
import com.wonders.contact.deptContact.model.vo.DeptContactOperateVo;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessVo;
import com.wonders.contact.external.model.data.AttachData;
import com.wonders.contact.external.model.data.AttachDetailData;
import com.wonders.contact.external.model.data.DeptContactData;
import com.wonders.contact.external.model.data.TodoData;
import com.wonders.contact.external.model.vo.AttachVo;
import com.wonders.contact.external.model.vo.OperateVo;
import com.wonders.contact.external.model.vo.TodoVo;
import com.wonders.contact.external.service.ApiService;
import com.wonders.contact.external.util.AttachUploadUtil;
import com.wonders.contact.todo.model.bo.TodoItem;


@Service("contact-apiService")
@Scope("prototype")
public class ApiServiceImpl implements ApiService{
	private DeptContactDao deptContactDao;
	private CommonService commonService;
	private FjshService fjshService;
	private ApprovedInfoDeptContactService approvedInfoDeptContactService;
	private Gson gson = new Gson();
	
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	@Override
	public String getAttachDetail(String attachId) {
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public AttachData getAttachList(String groupId) {
		List<AttachFile> list = this.fjshService.loadFilesByGroupId(groupId);
		
//log.debug("groupId="+groupId+" attach list size="+list.size());
		
		AttachData data = new AttachData();
		data.list = list;
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DeptContactData getDeptContactData(String todoId) {
		try{
		
			TodoItem bo = (TodoItem) this.commonService.load(todoId, TodoItem.class);
			//Map<String,String> map = new HashMap<String,String>();
			//map = gson.fromJson(bo.getData(),map.getClass());
			//String mainId = map.get("TASKID");
			String mainId=bo.getTaskId();
			TDeptContactMain main = (TDeptContactMain) this.commonService.load(mainId, TDeptContactMain.class);
			DeptContactData data = new DeptContactData();
			data.main = main;
			data.comment = this.approvedInfoDeptContactService.getApprovedInfoVo(mainId, "");
			data.type = bo.getType()+"";
			return data;
		
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepareOperateData(OperateVo vo) {
		TodoItem todoItem = (TodoItem) this.commonService.load(vo.todoId, TodoItem.class);
		vo.todoItem=todoItem;
		/*
		//待办项原始信息map
		Map<String,String> todoMap = gson.fromJson(todoItem.getData(),vo.todoDataMap.getClass());
		
		if(todoMap!=null){
			vo.todoDataMap = todoMap; 
		}else{
			return;
		}
		
//log.debug("todoMap size="+todoMap.size());
		
		String mainId = todoMap.get("ID");*/
		String mainId=todoItem.getTaskId();
		TDeptContactMain mainBo = (TDeptContactMain) this.commonService.load(mainId, TDeptContactMain.class);
		TDeptContactTree treeBo = this.deptContactDao.getTreeBoByMainBoId(mainId);
		
		vo.main = mainBo;
		vo.tree = treeBo;

		String type = String.valueOf(treeBo.getType());
		
		//String steplabel = StringUtil.getNotNullValueString(todoMap.get("STEPLABEL"));
		String steplabel = StringUtil.getNotNullValueString(todoItem.getStepName());
//log.debug("steplabel="+steplabel);
		DeptContactOperateVo operateVoDC = vo.operateVoDC;
		operateVoDC.setTaskId(todoItem.getId());
		operateVoDC.setId(mainId);
		operateVoDC.setSteplabel(steplabel);
		operateVoDC.setFlowType(type);
		operateVoDC.setChoice(vo.data_map.get("choice"));
		operateVoDC.setSuggest(vo.data_map.get("suggest"));
		operateVoDC.setAttachId(vo.data_map.get("attachId"));
		
		DeptSubProcessVo operateVoDSP = vo.operateVoDSP;
		operateVoDSP.setTaskId(todoItem.getId());
		operateVoDSP.setId(mainId);
		operateVoDSP.setSteplabel(steplabel);
		operateVoDSP.setFlowType(type);
		operateVoDSP.setChoice(vo.data_map.get("choice"));
		operateVoDSP.setSuggest(vo.data_map.get("suggest"));
		operateVoDSP.setAttachId(vo.data_map.get("attachId"));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public TodoData getTodoData(TodoVo vo) {
		String hql = "from TodoItem t where t.loginName = ? and t.occurTime > ?  and t.removed!=1 and t.status=0 order by t.occurTime desc";
		List<TodoItem> list = this.commonService.ListByHql(hql,new Object[] {vo.loginName,vo.occurTime});
//System.out.println("todo list size="+list.size());
		
		TodoData data = new TodoData();
		data.list = list;
		return data;
	}
	
	
	public AttachDetailData getAttachStatus(AttachVo attachVo){
		AttachDetailData data = new AttachDetailData();
		Map<String,String> map = new HashMap<String,String>();
		map = attachVo.data_map;
		UserInfo user = attachVo.user;
		if(map!=null&&map.size()>0){
			System.out.println(map.get("fileId"));
			String fileId = map.get("fileId");
			String fileName = map.get("fileName");
			String fileType = map.get("fileType");
			String fileSize = map.get("fileSize");
			if(fileId!=null&&!"".equals(fileId)){
				UploadFile uo = this.fjshService.loadFileById(fileId);
				AttachFile ao = null;
				if(uo==null){
					//第一次上传
					ao = this.fjshService.beginUpload(fileName, fileType, fileSize, 
							user.getUserName(), user.getLoginName(), DateUtil.getNowTime(), null, "音频");
					data.offset =  ao.getFileSize()+"";
					data.fileId = ao.getId()+ "";
					data.groupId = ao.getGroupId();
					data.fileUrl = "xxxxx";
					data.fileName = ao.getFileName();
					data.fileSize = map.get("fileSize");
					data.fileType = ao.getFileType();
					data.createTime = ao.getUploadDate();
				}else{
					ao = uo.getAttachFile();
					//断点续传
					data.offset =  ao.getFileSize()+"";
					data.fileId = ao.getId()+ "";
					data.groupId = ao.getGroupId();
					data.fileUrl = "xxxxx";
					data.fileName = ao.getFileName();
					data.fileSize = map.get("fileSize");
					data.fileType = ao.getFileType();
					data.createTime = ao.getUploadDate();
				}
			}
		}
		return data;
	}
	
	
	//断点续传附件之 上传附件
	public void attachUpload(AttachVo attachVo,BufferedInputStream stream){
		Map<String,String> map = new HashMap<String,String>();
		map = attachVo.data_map;
		UserInfo user = attachVo.user;
		if(map!=null&&map.size()>0){
			String fileId = map.get("fileId");
			String offset = "";
			if(fileId!=null&&!"".equals(fileId)){
				UploadFile uo = this.fjshService.loadFileById(fileId);
				if(uo!=null){
					offset = uo.getAttachFile().getFileSize()+"";
					String destFileName = uo.getAttachFile().getPath()+uo.getAttachFile().getSaveFileName();
					try {
						long l = AttachUploadUtil.uploadFile(offset, destFileName, stream);
						if(l!=0){
							this.fjshService.updateAttach(user.getUserName(), user.getLoginName(),uo.getAttachFile(), l);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public AttachDetailData attachCommit(AttachVo attachVo){
		AttachDetailData data = new AttachDetailData();
		Map<String,String> map = new HashMap<String,String>();
		map = attachVo.data_map;
		UserInfo user = attachVo.user;
		if(map!=null&&map.size()>0){
			String fileId = map.get("fileId");
			//String fileName = map.get("fileName");
			//String fileType = map.get("fileType");
			String fileSize = map.get("fileSize");
			if(fileId!=null&&!"".equals(fileId)){
				UploadFile uo = this.fjshService.loadFileById(fileId);
				AttachFile ao = null;
				if(uo!=null){
					ao = uo.getAttachFile();
					data.offset = ao.getFileSize()+"";
					data.fileId = ao.getId()+ "";
					data.groupId = ao.getGroupId();
					data.fileUrl = "xxxxx";
					data.fileName = ao.getFileName()+"."+ao.getFileExtName();
					data.fileSize = ao.getFileSize()+"";
					data.fileType = ao.getFileType();
					data.createTime = ao.getUploadDate();
					if(fileSize!=null&&fileSize.equals(ao.getFileSize()+"")){
						this.fjshService.commitAttach(user.getUserName(), user.getLoginName(),ao);
						data.status = "success";
					}else{
						data.status = "fail";
					}
				}
			}
		}
		return data;
	}
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired
	public void setCommonService(@Qualifier(value="contact-commonService")CommonService commonService) {
		this.commonService = commonService;
	}
	
	public FjshService getFjshService() {
		return fjshService;
	}
	
	@Autowired
	public void setFjshService(@Qualifier("fjshService")FjshService fjshService) {
		this.fjshService = fjshService;
	}
	
	public ApprovedInfoDeptContactService getApprovedInfoDeptContactService() {
		return approvedInfoDeptContactService;
	}

	@Autowired
	public void setApprovedInfoDeptContactService(@Qualifier("contact-approvedInfoDeptContactService")ApprovedInfoDeptContactService approvedInfoDeptContactService) {
		this.approvedInfoDeptContactService = approvedInfoDeptContactService;
	}
	
	public DeptContactDao getDeptContactDao() {
		return deptContactDao;
	}
	
	@Autowired
	public void setDeptContactDao(@Qualifier(value="contact-deptContactDao")DeptContactDao deptContactDao) {
		this.deptContactDao = deptContactDao;
	}
}
