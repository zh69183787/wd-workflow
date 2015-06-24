package com.wonders.contact.tListStatus.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.wonders.contact.tListStatus.dao.TListStatusTypeDao;
import com.wonders.contact.tListStatus.model.bo.TListStatusType;
import com.wonders.contact.tListStatus.service.TListStatusTypeService;
import com.wonders.util.DateUtil;

@Repository("contact-tListStatusTypeService")
@Scope("prototype")
public class TListStatusTypeServiceImpl implements TListStatusTypeService{
	private TListStatusTypeDao dao;

	public TListStatusType findTListStatusTypeByCode(String code){
		TListStatusType tListStatusType = dao.findTListStatusTypeByCode(code);

		return tListStatusType;
	}

	@SuppressWarnings("unchecked")
	public List findAll(String type) {
		String hql = "";
		if(type.equals("")){
			hql = "from TListStatusType t where t.state=1 and order by t.uptdate";
			return this.dao.findByHQL(hql);
		}else if(type.equals("JS")){
			hql="from TListStatusType t where t.state=1 and substr(t.code,0,2) = ? order by t.uptdate";
			return this.dao.findByHQL(hql,type);
		}else{
			hql="from TListStatusType t where t.state=1 and t.code = ? order by t.uptdate";
			return this.dao.findByHQL(hql,type);
		}
	}

	public String getChildCounts(String code) {
		return dao.getChildCounts(code);
	}

	public Boolean exist(String code) {
		String hql="from TListStatusType t where t.state=1 and t.code = ?";
		if(this.dao.findByHQL(hql,code).size()>0){
			return true;
		}else{
			return false;
		}
	}

	public Boolean ExistSameFaDict(String code, String name) {
		String hql = "from TListStatusType t where t.state=1 and and (t.code =? or t.name = ?)";
		if(this.dao.findByHQL(hql,code,name).size()>0){
			return true;
		}else{
			return false;
		}
	}

	public void saveFaDict(Object tlst) {
		this.dao.getHibernateTemplate().save(tlst);
	}

	public void delRecord(String code) {
		this.dao.delRecord(code);
	}

	public void modiRecord(String code, String name, String memo) {
		this.dao.modiRecord(code, name, memo);
	}

	public Boolean ExistSameChildDict(String name) {
		String hql = "from TListStatus t where t.state=1 and t.content= ?";
		if(this.dao.findByHQL(hql, name).size()>0){
			return true;
		}else{
			return false;
		}
	}

	
	
	public TListStatusTypeDao getDao() {
		return dao;
	}
	@Autowired(required=false)
	public void setDao(@Qualifier("contact-tListStatusTypeDao")TListStatusTypeDao dao) {
		this.dao = dao;
	}
	
	
	
	public static String getCurrentTime(){
		return DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
		/*
		Date date=null;
        Calendar cl= Calendar.getInstance();
        cl.setTime(new java.util.Date());
        date=cl.getTime();
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = time.format(date);//系统日期
        return dateTime;
		*/
	}
}
