package com.wonders.contact.tListStatus.action;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/")
@Component("contact-tListStatusTypeAction")
@Scope("prototype")
public class TListStatusTypeAction extends ActionSupport{

	private TListStatusTypeService tlstsService;
	private TListStatusService tlssSerivce;
	
	private String splitExp = "@#$";
	
	ActionContext actionContext = ActionContext.getContext();
	HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	
	
	@SuppressWarnings("unchecked")
	@Action(value="listStatusTypeAction")
	public String listStatusType() {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		String parentCode = StringUtil.getNotNullValueString(request.getParameter("parentid"));
		String type = StringUtil.getNotNullValueString(request.getParameter("type"));
		List list = new ArrayList();
		//判断此处是找TListStatusType的第一层节点，还是去找TListStatus的子节点
		Boolean firstLevel = tlssSerivce.findChildRecord(parentCode);
		Boolean otherLevel = tlssSerivce.Exist(parentCode);
		if(!firstLevel){
			if(!otherLevel){
				list = tlstsService.findAll(type);
			}else{
				list = tlssSerivce.findTListStatusByRefId(parentCode);
			}
			
		}else{
			list = tlssSerivce.findTListStatusByCode(parentCode);
		}
		
		Iterator iter = list.iterator();
		TListStatusType tempListStatusType = null;
		TListStatus tempListStatus = null;
		String temps = "";

		while (iter.hasNext()) {
			if(!firstLevel){
				if(!otherLevel){
					tempListStatusType = (TListStatusType) iter.next();

					String tmpVal = tempListStatusType.getCode() + splitExp + tempListStatusType.getName() + splitExp + tempListStatusType.getMemo() + splitExp + "" + splitExp + firstLevel.toString() + splitExp + otherLevel.toString() + splitExp + "root" + "@|@";
					temps += tmpVal ;
				}else{
					tempListStatus = (TListStatus) iter.next();
					String tmpVal = tempListStatus.getSid() + splitExp + tempListStatus.getContent() + splitExp + tempListStatus.getMemo() + splitExp + tempListStatus.getOptorder() + splitExp + firstLevel.toString() + splitExp + otherLevel.toString() + splitExp + "child" + "@|@";
					temps += tmpVal;
				}
			}else{
				tempListStatus = (TListStatus) iter.next();
				String tmpVal = tempListStatus.getSid() + splitExp + tempListStatus.getContent() + splitExp + tempListStatus.getMemo() + splitExp + tempListStatus.getOptorder() + splitExp + firstLevel.toString() + splitExp + otherLevel.toString()+ splitExp + "child1" + "@|@";
				temps += tmpVal;
			}
		}
		
		//String last_xml = xml;
		//System.out.println("==========" +temps + "============");
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
	
	@Action(value="saveDict")
	public String saveDict(){
		String code = StringUtil.getNotNullValueString(request.getParameter("code"));
		String name = StringUtil.getNotNullValueString(request.getParameter("name"));
		String memo = StringUtil.getNotNullValueString(request.getParameter("memo"));
		String flag = "false";
		if(tlstsService.ExistSameFaDict(code,name)){
			Writer w = null;
			try {
				flag = "true";
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				w = response.getWriter();
				w.write(flag);
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
		}else{
			
			//保存一级科目信息
			TListStatusType tlst = new TListStatusType();
			//Date date=null;
	        Calendar cl= Calendar.getInstance();
	        cl.setTime(new java.util.Date());
	        //date=cl.getTime();
	        //SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        //String dateTime = time.format(date);//系统日期
			tlst.setCode(code);
			tlst.setName(name);
			tlst.setMemo(memo);
			tlst.setState("1");
			tlst.setUptdate(getCurrentTime());
			tlstsService.saveFaDict(tlst);
		}
		return null;
	}
	
	@Action(value="saveChildDict")
	public String saveChildDict(){
		String xh = StringUtil.getNotNullValueString(request.getParameter("xh"));
		String name = StringUtil.getNotNullValueString(request.getParameter("name"));
		String memo = StringUtil.getNotNullValueString(request.getParameter("memo"));
		String sid = StringUtil.getNotNullValueString(request.getParameter("sid"));
		String belongKindCode = request.getParameter("belongKindCode");
		Date date=null;
        Calendar cl= Calendar.getInstance();
        cl.setTime(new java.util.Date());
        date=cl.getTime();
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = time.format(date);//系统日期
		//保存子科目
        if(tlstsService.ExistSameChildDict(name)){
        	TListStatus tls = new TListStatus();
    		tls.setContent(name);
    		if(!xh.equals("")){
    			tls.setOptorder(Integer.parseInt(xh));
    		}else{
    		}
    		tls.setMemo(memo);
    		tls.setState("1");
    		tls.setUptdate(dateTime);
    		tls.setInfotype(tlstsService.findTListStatusTypeByCode(belongKindCode));
    		if(!sid.equals("")){
    			tls.setRefsid((TListStatus)tlssSerivce.findTListStatusBySID(sid).get(0));
    		}
    		tlssSerivce.saveChildListStatus(tls);
        }else{
        	TListStatus tls = new TListStatus();
    		tls.setContent(name);
    		if(!xh.equals("")){
    			tls.setOptorder(Integer.parseInt(xh));
    		}else{
    		}
    		tls.setMemo(memo);
    		tls.setState("1");
    		tls.setUptdate(dateTime);
    		tls.setInfotype(tlstsService.findTListStatusTypeByCode(belongKindCode));
    		if(!sid.equals("")){
    			tls.setRefsid((TListStatus)tlssSerivce.findTListStatusBySID(sid).get(0));
    		}
    		tlssSerivce.saveChildListStatus(tls);
    		//同样在T_LIST_TYPE中也要保存字典项,如果belongKindCode为JS_UnitType
    		if(belongKindCode.equals("JS_UnitType")){
    			TListStatusType tlst = new TListStatusType();
    			tlst.setCode("JS_"+name);
    			tlst.setName(name+"字典项");
    			tlst.setUptdate(getCurrentTime());
    			tlst.setState("1");
    			tlst.setMemo(memo);
    			tlstsService.saveFaDict(tlst);
    		}
        }
		
		return null;
	}
	
	@Action(value="delDict")
	public String delDict(){
		String sid = request.getParameter("sid")==null?"":request.getParameter("sid");
		String code = request.getParameter("code")==null?"":request.getParameter("code");
		if(sid.equals("")){
			if(tlstsService.exist(code)){
				tlstsService.delRecord(code);
			}
		}else{
			if(tlstsService.exist(code)&&tlssSerivce.Exist(sid)){
				tlssSerivce.delRecord(sid);
			}
		}
		return null;
	}
	
	@Action(value="modiDict")
	public String modiDict(){
		String sid = request.getParameter("sid")==null?"":request.getParameter("sid");
		String code = request.getParameter("code")==null?"":request.getParameter("code");
		String memo = request.getParameter("memo")==null?"":request.getParameter("memo");
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String xh = request.getParameter("xh")==null?"":request.getParameter("xh");
		if(sid.equals("")&&!code.equals("")){
				tlstsService.modiRecord(code,name,memo);
		}else if(!sid.equals("")&&!code.equals("")){
				tlssSerivce.modiRecord(sid,name,memo,xh);
		}
		return null;
	}

	public static String getCurrentTime(){
		return DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
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
