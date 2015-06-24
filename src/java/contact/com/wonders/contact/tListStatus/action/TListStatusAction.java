package com.wonders.contact.tListStatus.action;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wonders.contact.tListStatus.model.bo.TListStatus;
import com.wonders.contact.tListStatus.model.bo.TListStatusType;
import com.wonders.contact.tListStatus.service.TListStatusService;
import com.wonders.contact.tListStatus.service.TListStatusTypeService;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/")
@Component("contact-tListStatusAction")
@Scope("prototype")
public class TListStatusAction extends ActionSupport{
	private TListStatusTypeService tlstsService;
	private TListStatusService tlssSerivce;
	
	private String splitExp = "@#$";
	
	ActionContext actionContext = ActionContext.getContext();
	HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	
	@SuppressWarnings("unchecked")
	@Action(value="listStatusDetailAction")
	public String listStatusDetail() {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		//传过来的可能是TLISTSTATUS的ID,也有可能是TLISTSTATUS中的INFOTYPE
		String id = request.getParameter("id");
		String counts = "";
		List tlsList = new ArrayList(); 
		//tlssSerivce.findTListStatusBySID(id)
		if(tlstsService.exist(id)){
			
		}else{
			tlsList = tlssSerivce.findTListStatusBySID(id);
		}
		String temps = "";
		if(tlsList.size()>0){
			TListStatus tls = (TListStatus)tlsList.get(0);
			//String sq="select t.s_num as num,t.s_name as nam,(select count(t.parent_id) from t_b_subject t where t.parent_id='"+ts.getId()+"' and t.removed=0) as numbers from t_b_subject t where t.id='"+ts.getParentId()+"' and t.removed=0";
			counts = tlssSerivce.getChildCounts(tls.getSid());
			temps = tls.getInfotype().getName()+splitExp+tls.getContent()+splitExp+tls.getMemo()+splitExp+(tls.getOptorder()==null?"":
					tls.getOptorder())+splitExp+counts+splitExp+""+splitExp+tls.getSid()+splitExp+tlsList.size();
		}else{
			TListStatusType tlst = tlstsService.findTListStatusTypeByCode(id);
			counts = tlstsService.getChildCounts(tlst.getCode());
			temps = tlst.getName() + splitExp + tlst.getName() + splitExp + tlst.getMemo()+splitExp+""+splitExp+counts+splitExp+tlst.getCode()+splitExp+""+splitExp+"0";
		}
		Writer w = null;
		try {
			response.setCharacterEncoding("UTF-8");
			w = response.getWriter();
			w.write(temps);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (w != null)
					w.flush();
				if (w != null)
					w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public TListStatusTypeService getTlstsService() {
		return tlstsService;
	}
	@Autowired(required=false)
	public void setTlstsService(@Qualifier("contact-tListStatusTypeService")TListStatusTypeService tlstsService) {
		this.tlstsService = tlstsService;
	}
	public TListStatusService getTlssSerivce() {
		return tlssSerivce;
	}
	@Autowired(required=false)
	public void setTlssSerivce(@Qualifier("contact-tListStatusService")TListStatusService tlssSerivce) {
		this.tlssSerivce = tlssSerivce;
	}
	
}
