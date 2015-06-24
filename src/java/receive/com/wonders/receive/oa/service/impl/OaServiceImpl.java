/**
 * 
 */
package com.wonders.receive.oa.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.receive.oa.model.bo.DocsFileBo;
import com.wonders.receive.oa.model.bo.MsgBo;
import com.wonders.receive.oa.model.bo.ProcessRelationBo;
import com.wonders.receive.oa.service.OaService;
import com.wonders.receive.oa.util.OaUtil;
import com.wonders.receive.workflow.common.model.vo.UserInfo;
import com.wonders.receive.workflow.common.service.CommonService;
import com.wonders.receive.workflow.model.vo.ParamVo;
import com.wonders.receive.workflow.process.util.TextUtil;
import com.wonders.receive.workflow.util.FlowUtil;
import com.wonders.util.PropertyUtil;

/** 
 * @ClassName: OaServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-6 上午10:42:38 
 *  
 */

@Service("oaService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class OaServiceImpl implements OaService{
	private CommonService commonService;
	private UserInfo userInfo;
	
	@Override
	public void init(ParamVo params) {
		this.userInfo = params.userInfo;
	}
	
	public void addProcessMsg(ParamVo params){		
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String title = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		String config_ip = PropertyUtil.getValueByKey("config.properties", "contextIpPath");
		
		List<Long> userList = OaUtil.getOaMsgUsers(pname, pincident, steplabel);
		Long sendUser = OaUtil.getOaUserId(userInfo.getLogin_Name());
		String content = "系统中已有一条业务流程完成流转 "+
				" <a target='_blank' href='"+config_ip+
				TextUtil.generateMainUrl(params) +
				"' ><font color='red'>请点击此处查看详细内容。</font></a>";
		String display = "系统中已有一条业务流程完成流转，摘要："+title+"。请关注。";
		if(userList != null && userList.size() > 0){
			for(Long userId : userList){
				MsgBo bo = new MsgBo();
				bo.setTitle(display);
				bo.setContent(content);
				//id
				bo.setEmpidrec(userId);
				bo.setEmpidsend(sendUser);
				this.commonService.save(bo);
				if(bo!=null){
					ProcessRelationBo pbo = new ProcessRelationBo();
					pbo.setProcessName(pname);
					pbo.setIncidentNo(pincident);
					pbo.setDisplayName(display);
					pbo.setYewuId(""+bo.getSid());
					if(pname.endsWith("收文流程")){
						pbo.setType("DocRe_notice");
					}else if(pname.endsWith("收呈批件")){
						pbo.setType("ReDirective_notice");
					}
					pbo.setOperator(userInfo.getLogin_Name());
					this.commonService.save(pbo);
				}
			}
		}
	}
	
	public void addDocsFile(ParamVo params){
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		List<String> deptList = OaUtil.getDocFileDept(pname, pincident, steplabel,userInfo.getLogin_Name());
		Map<String,String> deptMap = OaUtil.getCatalog(deptList);
		List<DocsFileBo> result = new ArrayList<DocsFileBo>();
		String title = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		String config_ip = PropertyUtil.getValueByKey("config.properties", "contextIpPath");
		if(deptMap != null && deptMap.size() > 0){
			for(Map.Entry<String, String> dept : deptMap.entrySet()){
				DocsFileBo docsFile=new DocsFileBo();
				docsFile.setFile_name(title);
				docsFile.setFile_path(config_ip+
						TextUtil.generateMainUrl(params));
				docsFile.setCreate_user(userInfo.getLogin_Name());
				docsFile.setUpdate_user(userInfo.getLogin_Name());
				docsFile.setFile_assessor(userInfo.getLogin_Name());
				docsFile.setLink_flag("2");
				docsFile.setFile_check_flag("1");
				docsFile.setParent_sid(dept.getValue());
				result.add(docsFile);
			}
			this.commonService.saveBatch(result);
		}
		
		
	}
	
	public String queryProcessMsg(ParamVo params){
		String result = "";
		
		return result;
	}
	
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired(required=false)
	public void setCommonService(@Qualifier(value="receive-commonService")CommonService commonService) {
		this.commonService = commonService;
	}
	
}
