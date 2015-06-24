package com.wonders.contact.tListStatus.service;

import java.util.List;

import com.wonders.contact.tListStatus.model.bo.TListStatusType;

public interface TListStatusTypeService {
	TListStatusType findTListStatusTypeByCode(String code);
	@SuppressWarnings("unchecked")
	List findAll(String type);
	String getChildCounts(String code);
	Boolean exist(String code);
	Boolean ExistSameFaDict(String code, String name);
	void saveFaDict(Object tlst);
	void delRecord(String code);
	void modiRecord(String code, String name, String memo);
	Boolean ExistSameChildDict(String name);
	
}
