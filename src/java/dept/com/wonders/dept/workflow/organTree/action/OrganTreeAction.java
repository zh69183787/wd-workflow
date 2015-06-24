package com.wonders.dept.workflow.organTree.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wonders.dept.workflow.common.model.vo.UserInfo;
import com.wonders.dept.workflow.common.util.ActionWriter;
import com.wonders.dept.workflow.common.util.LoginUtil;
import com.wonders.dept.workflow.common.util.SimpleLogger;
import com.wonders.dept.workflow.external.service.ExternalService;
import com.wonders.dept.workflow.organTree.model.bo.OrganDeptBo;

/**组织树人员、部门action
 * @author XFZ
 * @version 1.0 2012-6-19
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/dept-organTree")
@Controller("dept-organTreeAction")
@Scope("prototype")
public class OrganTreeAction extends ActionSupport {
	ActionContext actionContext = ActionContext.getContext();
	HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	HttpSession session = request.getSession();
	
	private UserInfo userInfo = new UserInfo();
	private ActionWriter aw = new ActionWriter(response);
	private Logger log = SimpleLogger.getLogger(this.getClass());
	
	private ExternalService externalService;
	
	private String id = "";
	private String processName = "";
	
	public OrganTreeAction(){
		this.setUserInfo(LoginUtil.getUserInfo(session));
		//deptId = StringUtil.getNotNullValueString(userInfo.getDeptId());
//log.debug("OrganTreeAction:"+deptId);
	}
		
	
	/**获得集团领导及秘书
	 * @return
	 */
	@Action(value="getComLeaders")
	public String getComLeaders(){
		aw.writeJson(externalService.getComLeaderInfo(id));
		return null;
	}
	
	/**获得部门领导层
	 * @return
	 */
	@Action(value="getDeptLeaders")
	public String getDeptLeaders(){
//log.debug("getDeptLeaders:"+deptId);
		if(id.length()>0){
			aw.writeJson(externalService.getDeptLeaders(id));
		}
		return null;
	}
	
	/**获得部门领导single
	 * @return
	 */
	@Action(value="getDeptSingleLeader")
	public String getDeptSingleLeader(){
		if(id.length()>0){
			aw.writeJson(externalService.getDeptSingleLeader(id));
		}
		return null;
	}
	
	/**获得部门收发员
	 * @return
	 */
	@Action(value="getDeptReceivers")
	public String getDeptReceivers(){
		if(id.length()>0){
			aw.writeJson(externalService.getDeptReceivers(id));
		}
		return null;
	}
	
	/**获得部门用户
	 * @return
	 */
	@Action(value="getDeptUsers")
	public String getDeptUsers(){
		if(id.length()>0){
			aw.writeJson(externalService.getDeptUsers(id));
		}
		return null;
	}
	
	/**获得部门用户
	 * @return
	 */
	@Action(value="getDeptUsersOffLeaders")
	public String getDeptUsersOffLeaders(){
		if(id.length()>0){
			aw.writeJson(externalService.getDeptUsersOffLeaders(id));
		}
		return null;
	}
	
	
	/**获得树节点子节点*/
	@Action(value="getChildNodes")
	public String getChildNodes(){
//		if(id.length()>0){
//		aw.writeJson(externalService.getChildNodes(processName,id));
//	}
//	return null;
	
	String typeId = "1";
	//if(id.length()>0){
	if("2500".equals(id)){
		List<OrganDeptBo> list = externalService.getChildNodes(processName,id);
		List<OrganDeptBo> list1 = new ArrayList<OrganDeptBo>();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				OrganDeptBo bo = list.get(i);
				if(typeId==null){
					list1.add(bo);
				}else if(typeId.equals("-1")){
					list1.add(bo);
				}else if(typeId.equals(list.get(i).typeId)){
					list1.add(bo);
				}
			}
		}
		
		aw.writeJson(list1);
	}
	
	return null;
	}
	
	/**获得树节点子节点*/
	@Action(value="getNodesInfo")
	public String getNodesInfo(){
		//if(id.length()>0){
		aw.writeJson(externalService.getNodesInfo(id));
		//}
		return null;
	}
	
	/**获得树节点相关节点*/
	@Action(value="getRelatedNodes")
	public String getRelatedNodes(){
		if(id.length()>0){
			aw.writeJson(externalService.getRelatedNodes(id));
		}
		return null;
	}	
	
	
	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired
	public void setExternalService(@Qualifier("dept-externalService")ExternalService externalService) {
		this.externalService = externalService;
		this.externalService.setToken(userInfo.getToken());
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getProcessName() {
		return processName;
	}


	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
}
