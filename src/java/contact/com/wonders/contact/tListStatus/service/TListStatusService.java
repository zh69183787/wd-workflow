package com.wonders.contact.tListStatus.service;

import java.util.List;

import com.wonders.contact.tListStatus.model.bo.TListStatus;


public interface TListStatusService {
	@SuppressWarnings("unchecked")
	List findTListStatusByCode(String code);
	Boolean findChildRecord(String parentCode);
	@SuppressWarnings("unchecked")
	List findTListStatusByRefId(String refId);
	Boolean Exist(String sid);
	@SuppressWarnings("unchecked")
	List findTListStatusBySID(String sid);
	String getChildCounts(long sid);
	void saveChildListStatus(Object obj);
	void delRecord(String sid);
	void modiRecord(String sid, String name, String memo, String xh);
	@SuppressWarnings("unchecked")
	List findTListStatusForUnitName(String line);
	TListStatus findTListStatusByCodeAndContent(String code, String content);
	Integer getCurrentOptOrder(String infoType);
}
