package com.pigtrax.batch.beans;

import java.util.Date;

public class PigletStatusInfo {
    private Integer id;
    private Integer pigInfoId;
    private Integer fosterFrom;
    private Integer fosterTo;
    private Integer pigletStatusEventType;
    private Date eventDateTime;
    private Integer numberOfPigs;
    private Double weightInKgs;
    private String eventReason;
    private String remarks;
    private Integer sowCondition;
    private String weanGroupId;
    private Date lastUpdated;
    private String userUpdated;
    private Integer farrowEventId;
    private Integer fosterFarrowEventId;
    private Integer groupEventId;
    private Integer mortalityReasonTypeId;
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
	public Integer getFosterFrom() {
		return fosterFrom;
	}
	public void setFosterFrom(Integer fosterFrom) {
		this.fosterFrom = fosterFrom;
	}
	public Integer getFosterTo() {
		return fosterTo;
	}
	public void setFosterTo(Integer fosterTo) {
		this.fosterTo = fosterTo;
	}
	public Integer getPigletStatusEventType() {
		return pigletStatusEventType;
	}
	public void setPigletStatusEventType(Integer pigletStatusEventType) {
		this.pigletStatusEventType = pigletStatusEventType;
	}
	public Date getEventDateTime() {
		return eventDateTime;
	}
	public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}
	public Integer getNumberOfPigs() {
		return numberOfPigs;
	}
	public void setNumberOfPigs(Integer numberOfPigs) {
		this.numberOfPigs = numberOfPigs;
	}
	public Double getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(Double weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public String getEventReason() {
		return eventReason;
	}
	public void setEventReason(String eventReason) {
		this.eventReason = eventReason;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getSowCondition() {
		return sowCondition;
	}
	public void setSowCondition(Integer sowCondition) {
		this.sowCondition = sowCondition;
	}
	public String getWeanGroupId() {
		return weanGroupId;
	}
	public void setWeanGroupId(String weanGroupId) {
		this.weanGroupId = weanGroupId;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getUserUpdated() {
		return userUpdated;
	}
	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}
	public Integer getFarrowEventId() {
		return farrowEventId;
	}
	public void setFarrowEventId(Integer farrowEventId) {
		this.farrowEventId = farrowEventId;
	}
	public Integer getFosterFarrowEventId() {
		return fosterFarrowEventId;
	}
	public void setFosterFarrowEventId(Integer fosterFarrowEventId) {
		this.fosterFarrowEventId = fosterFarrowEventId;
	}
	public Integer getGroupEventId() {
		return groupEventId;
	}
	public void setGroupEventId(Integer groupEventId) {
		this.groupEventId = groupEventId;
	}
	public Integer getMortalityReasonTypeId() {
		return mortalityReasonTypeId;
	}
	public void setMortalityReasonTypeId(Integer mortalityReasonTypeId) {
		this.mortalityReasonTypeId = mortalityReasonTypeId;
	}
    
    
}
