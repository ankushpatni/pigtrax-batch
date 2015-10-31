package com.pigtrax.batch.beans;

import java.util.Date;

public class GroupEvent {
	
	private Integer id; 
	private String groupId;
	private Integer companyId;
	private Date groupStartDateTime;
	private Date groupCloseDateTime;
	private boolean isActive;
	private String remarks;
	private Date lastUpdated;
	private String userUpdated;
	private Integer currentInventory;
	private String previousGroupId;
	private Integer phaseOfProductionTypeId;

	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Date getGroupStartDateTime() {
		return groupStartDateTime;
	}
	public void setGroupStartDateTime(Date groupStartDateTime) {
		this.groupStartDateTime = groupStartDateTime;
	}
	public Date getGroupCloseDateTime() {
		return groupCloseDateTime;
	}
	public void setGroupCloseDateTime(Date groupCloseDateTime) {
		this.groupCloseDateTime = groupCloseDateTime;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public Integer getCurrentInventory() {
		return currentInventory;
	}
	public void setCurrentInventory(Integer currentInventory) {
		this.currentInventory = currentInventory;
	}
	public String getPreviousGroupId() {
		return previousGroupId;
	}
	public void setPreviousGroupId(String previousGroupId) {
		this.previousGroupId = previousGroupId;
	}
	public Integer getPhaseOfProductionTypeId() {
		return phaseOfProductionTypeId;
	}
	public void setPhaseOfProductionTypeId(Integer phaseOfProductionTypeId) {
		this.phaseOfProductionTypeId = phaseOfProductionTypeId;
	}
}
