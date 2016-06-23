package com.pigtrax.batch.exception;

import java.util.List;
import java.util.Map;

import com.pigtrax.batch.mapper.interfaces.Mapper;

public class ErrorBeanCollection {
	
    private Map<Mapper, List<ErrorBean>> errorBeanList;
    
    private String eventType;
    
    private Integer companyId;
    
    private String userId;

	public Map<Mapper, List<ErrorBean>> getErrorBeanList() {
		return errorBeanList;
	}

	public void setErrorBeanList(Map<Mapper, List<ErrorBean>> errorBeanList) {
		this.errorBeanList = errorBeanList;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
	
    
}
