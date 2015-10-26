package com.pigtrax.batch.beans;

import java.util.Date;

public class PregnancyInfo {
	
	private Integer id;
	private Integer pigInfoId;
	private Integer breedingEventId;
	private Integer employeeGroupId;
	private Integer pregnancyEventTypeId;
	private Integer PregnancyExamResultTypeId;
	private Date examDate;
	private Date resultDate;
	private Integer sowCondition;
	private String userUpdated;

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getPigInfoId() {
		return pigInfoId;
	}


	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}


	public Integer getBreedingEventId() {
		return breedingEventId;
	}


	public void setBreedingEventId(Integer breedingEventId) {
		this.breedingEventId = breedingEventId;
	}


	public Integer getEmployeeGroupId() {
		return employeeGroupId;
	}


	public void setEmployeeGroupId(Integer employeeGroupId) {
		this.employeeGroupId = employeeGroupId;
	}


	public Integer getPregnancyEventTypeId() {
		return pregnancyEventTypeId;
	}


	public void setPregnancyEventTypeId(Integer pregnancyEventTypeId) {
		this.pregnancyEventTypeId = pregnancyEventTypeId;
	}


	public Integer getPregnancyExamResultTypeId() {
		return PregnancyExamResultTypeId;
	}


	public void setPregnancyExamResultTypeId(Integer pregnancyExamResultTypeId) {
		PregnancyExamResultTypeId = pregnancyExamResultTypeId;
	}


	public Date getExamDate() {
		return examDate;
	}


	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}


	public Date getResultDate() {
		return resultDate;
	}


	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}


	public Integer getSowCondition() {
		return sowCondition;
	}


	public void setSowCondition(Integer sowCondition) {
		this.sowCondition = sowCondition;
	}
	
	
	public String getUserUpdated() {
		return userUpdated;
	}


	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}


	@Override
	public String toString() {
		return "PregnancyInfo [id=" + id + ", pigInfoId=" + pigInfoId
				+ ", breedingEventId=" + breedingEventId + ", employeeGroupId="
				+ employeeGroupId + ", pregnancyEventTypeId="
				+ pregnancyEventTypeId + ", PregnancyExamResultTypeId="
				+ PregnancyExamResultTypeId + ", examDate=" + examDate
				+ ", resultDate=" + resultDate + ", sowCondition="
				+ sowCondition + ", userUpdated=" + userUpdated + "]";
	}



}
