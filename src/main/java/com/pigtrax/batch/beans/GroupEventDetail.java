package com.pigtrax.batch.beans;

import java.util.Date;

public class GroupEventDetail {
	
	private Integer barnId;
	private Date dateOfEntry;
	private Integer numberOfPigs;
	private Double weightInKgs;
	private Integer inventoryAdjustment;
	private String remarks;
	private Date lastUpdated;
	private String userUpdated;
	private Integer roomId;
	private Integer employeeGroupId;
	private Integer groupId;
	private Integer companyId;
	private Integer premiseId;
	private Integer sowSourceId;
	
	public Integer getBarnId() {
		return barnId;
	}
	public void setBarnId(Integer barnId) {
		this.barnId = barnId;
	}
	public Date getDateOfEntry() {
		return dateOfEntry;
	}
	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
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
	public Integer getInventoryAdjustment() {
		return inventoryAdjustment;
	}
	public void setInventoryAdjustment(Integer inventoryAdjustment) {
		this.inventoryAdjustment = inventoryAdjustment;
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
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	public Integer getEmployeeGroupId() {
		return employeeGroupId;
	}
	public void setEmployeeGroupId(Integer employeeGroupId) {
		this.employeeGroupId = employeeGroupId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getPremiseId() {
		return premiseId;
	}
	public void setPremiseId(int premiseId) {
		this.premiseId = premiseId;
	}
	public Integer getSowSourceId() {
		return sowSourceId;
	}
	public void setSowSourceId(Integer sowSourceId) {
		this.sowSourceId = sowSourceId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}
	
}
