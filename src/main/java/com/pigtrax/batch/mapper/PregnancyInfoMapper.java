package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class PregnancyInfoMapper extends AbstractMapper {
	
	private String pigId;
	private String serviceDate;
	private String employeeGroup;
	private String pregnancyEventType;
	private String PregnancyExamResultType;
	private String examDate;
	private String resultDate;
	private String sowCondition;
	private String companyId;
	private String farmName;


	// Derivable properties
	private Integer derivePigInfoId;
	private Integer deriveBreedingEventId;
	private Integer deriveEmployeeGroupId;
	private Integer derivePregnancyEventTypeId;
	private Integer derivePregnancyExamResultTypeId;
	private Date deriveExamDate;
	private Date deriveResultDate;
	private Integer deriveSowCondition;
	private Integer deriveCompanyId;
	private Integer derivePremiseId;
	
	
	public String getPigId() {
		return pigId;
	}

	public void setPigId(String pigId) {
		this.pigId = pigId;
	}
	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(String employeeGroup) {
		this.employeeGroup = employeeGroup;
	}

	public String getPregnancyEventType() {
		return pregnancyEventType;
	}

	public void setPregnancyEventType(String pregnancyEventType) {
		this.pregnancyEventType = pregnancyEventType;
	}

	public String getPregnancyExamResultType() {
		return PregnancyExamResultType;
	}

	public void setPregnancyExamResultType(String pregnancyExamResultType) {
		PregnancyExamResultType = pregnancyExamResultType;
	}


	public String getExamDate() {
		return examDate;
	}


	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getResultDate() {
		return resultDate;
	}

	public void setResultDate(String resultDate) {
		this.resultDate = resultDate;
	}

	public String getSowCondition() {
		return sowCondition;
	}

	public void setSowCondition(String sowCondition) {
		this.sowCondition = sowCondition;
	}


	public Integer getDeriveBreedingEventId() {
		return deriveBreedingEventId;
	}

	public void setDeriveBreedingEventId(Integer deriveBreedingEventId) {
		this.deriveBreedingEventId = deriveBreedingEventId;
	}
	

	public Integer getDeriveEmployeeGroupId() {
		return deriveEmployeeGroupId;
	}

	public void setDeriveEmployeeGroupId(Integer deriveEmployeeGroupId) {
		this.deriveEmployeeGroupId = deriveEmployeeGroupId;
	}

	public Integer getDerivePregnancyEventTypeId() {
		return derivePregnancyEventTypeId;
	}

	public void setDerivePregnancyEventTypeId(Integer derivePregnancyEventTypeId) {
		this.derivePregnancyEventTypeId = derivePregnancyEventTypeId;
	}

	public Integer getDerivePregnancyExamResultTypeId() {
		return derivePregnancyExamResultTypeId;
	}

	public void setDerivePregnancyExamResultTypeId(
			Integer derivePregnancyExamResultTypeId) {
		this.derivePregnancyExamResultTypeId = derivePregnancyExamResultTypeId;
	}

	public Date getDeriveExamDate() {
		return deriveExamDate;
	}

	public void setDeriveExamDate(Date deriveExamDate) {
		this.deriveExamDate = deriveExamDate;
	}

	public Date getDeriveResultDate() {
		return deriveResultDate;
	}

	public void setDeriveResultDate(Date deriveResultDate) {
		this.deriveResultDate = deriveResultDate;
	}
	
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getDeriveSowCondition() {
		return deriveSowCondition;
	}

	public void setDeriveSowCondition(Integer deriveSowCondition) {
		this.deriveSowCondition = deriveSowCondition;
	}

	public Integer getDeriveCompanyId() {
		return deriveCompanyId;
	}

	public void setDeriveCompanyId(Integer deriveCompanyId) {
		this.deriveCompanyId = deriveCompanyId;
	}
	

	public Integer getDerivePigInfoId() {
		return derivePigInfoId;
	}

	public void setDerivePigInfoId(Integer derivePigInfoId) {
		this.derivePigInfoId = derivePigInfoId;
	}

	
	
	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public Integer getDerivePremiseId() {
		return derivePremiseId;
	}

	public void setDerivePremiseId(Integer derivePremiseId) {
		this.derivePremiseId = derivePremiseId;
	}

	@Override
	public String getId() {
		if (getPigId() == null) {
			return UUID.randomUUID().toString().replaceAll("-", "");
		} else {
			return "["+getPigId().toString()+", "+getCompanyId()+", "+getResultDate()+"]";
		}
	}
	
	public boolean isEmpty()
	{	
		if((this.pigId==null || this.pigId.trim().length() == 0)
				&& (this.resultDate==null || this.resultDate.trim().length() == 0)
				&& (this.pregnancyEventType == null || this.pregnancyEventType.trim().length() == 0)
				&& (this.PregnancyExamResultType == null || this.PregnancyExamResultType.trim().length() == 0)
				&& (this.employeeGroup == null || this.employeeGroup.trim().length() == 0)
				&& (this.sowCondition == null || this.sowCondition.trim().length() == 0)
				)
			return true;
		else
			return false;
	}
}
