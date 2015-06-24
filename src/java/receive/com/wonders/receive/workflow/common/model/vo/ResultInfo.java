package com.wonders.receive.workflow.common.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.wonders.receive.workflow.common.util.SimpleLogger;
import com.wonders.receive.workflow.model.bo.MessageBo;
import com.wonders.receive.workflow.process.recv.constants.RecvMainMessage;

public class ResultInfo {
		
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	/**是否刷新页面*/
	@Expose
	public boolean refresh = false;
	
	/**效验结果*/
	@Expose
	public boolean checkFlag = true;
	
	/**仅进行效验*/
	public boolean checkOnly = false;
	/**
	 * 错误信息
	 */
	@Expose
	private List<MessageBo> errors = new ArrayList<MessageBo>();
	
	@Expose
	public List<String> infos = new ArrayList<String>();
	
	@Expose
	public String url = "";
	
	public void addErrors(MessageBo error){
		setFalse();
		errors.add(error);
		//log.debug(error.textCn);
	}
	
	public void setFalse(){
		checkFlag = false;
	}
	
	public void setTrue(){
		checkFlag = true;
	}
	
	public void ShowResultInfo(){
		
		if(!this.checkFlag){
			/*
			List<MessageBo> list = this.errors;
			for(int i=0;i<list.size();i++){
				log.warn(list.get(i).textCn);
			}*/
			log.warn("errors size:"+errors.size());
		}else{
			log.debug(RecvMainMessage.CHECK_SUCCESS.textCn);
		}
	}
	
	/**合并错误
	 * @param resultAdd
	 */
	public void mergeResultInfo(ResultInfo resultAdd){
		if(resultAdd!=null&&resultAdd.getErrors().size()>0){
			for(int i=0;i<resultAdd.getErrors().size();i++){
				this.addErrors(resultAdd.getErrors().get(i));
			}
		}
	}
	
	/**是否可以进行流程操作
	 * @return
	 */
	public boolean getOperateFlag(){
		return this.checkFlag&&!this.checkOnly;
	}

	public List<MessageBo> getErrors() {
		return errors;
	}
}
