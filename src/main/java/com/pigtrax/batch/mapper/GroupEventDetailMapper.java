package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class GroupEventDetailMapper extends AbstractMapper{
	
	private String barnId;
	private String dateOfEntry;
	private String numberOfPigs;
	private String weightInKgs;
	private String inventoryAdjustment;
	private String remarks;
	private Date lastUpdated;
	private String userUpdated;
	private String roomId;
	private String employeeGroupId;
	private String groupId;
	private String transportDestination;
	private String companyId;
	private String farmName;
	private String sowSource;
	
	//deriavalbe property
	private Integer deriveBarnId;
	private Date deriveDateOfEntry;
	private Integer deriveNumberOfPigs;
	private Double deriveWeightInKgs;
	private Integer deriveInventoryAdjustment;
	private Integer deriveRoomId;
	private Integer deriveEmployeeGroupId;
	private int deriveGroupId;
	private Integer deriveTransportDestination;
	private Integer derivecompanyId;
	private Integer derivePremiseId;
	private Integer deriveSowSourceId;
	
	
	public String getBarnId() {
		return barnId;
	}
	public void setBarnId(String barnId) {
		this.barnId = barnId;
	}
	public String getDateOfEntry() {
		return dateOfEntry;
	}
	public void setDateOfEntry(String dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}
	public String getNumberOfPigs() {
		return numberOfPigs;
	}
	public void setNumberOfPigs(String numberOfPigs) {
		this.numberOfPigs = numberOfPigs;
	}
	public String getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(String weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public String getInventoryAdjustment() {
		return inventoryAdjustment;
	}
	public void setInventoryAdjustment(String inventoryAdjustment) {
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
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getEmployeeGroupId() {
		return employeeGroupId;
	}
	public void setEmployeeGroupId(String employeeGroupId) {
		this.employeeGroupId = employeeGroupId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTransportDestination() {
		return transportDestination;
	}
	public void setTransportDestination(String transportDestination) {
		this.transportDestination = transportDestination;
	}
	public Integer getDeriveBarnId() {
		return deriveBarnId;
	}
	public void setDeriveBarnId(Integer deriveBarnId) {
		this.deriveBarnId = deriveBarnId;
	}
	public Date getDeriveDateOfEntry() {
		return deriveDateOfEntry;
	}
	public void setDeriveDateOfEntry(Date deriveDateOfEntry) {
		this.deriveDateOfEntry = deriveDateOfEntry;
	}
	public Integer getDeriveNumberOfPigs() {
		return deriveNumberOfPigs;
	}
	public void setDeriveNumberOfPigs(Integer deriveNumberOfPigs) {
		this.deriveNumberOfPigs = deriveNumberOfPigs;
	}
	public Double getDeriveWeightInKgs() {
		return deriveWeightInKgs;
	}
	public void setDeriveWeightInKgs(Double deriveWeightInKgs) {
		this.deriveWeightInKgs = deriveWeightInKgs;
	}
	public Integer getDeriveInventoryAdjustment() {
		return deriveInventoryAdjustment;
	}
	public void setDeriveInventoryAdjustment(Integer deriveInventoryAdjustment) {
		this.deriveInventoryAdjustment = deriveInventoryAdjustment;
	}
	public Integer getDeriveRoomId() {
		return deriveRoomId;
	}
	public void setDeriveRoomId(Integer deriveRoomId) {
		this.deriveRoomId = deriveRoomId;
	}
	public Integer getDeriveEmployeeGroupId() {
		return deriveEmployeeGroupId;
	}
	public void setDeriveEmployeeGroupId(Integer deriveEmployeeGroupId) {
		this.deriveEmployeeGroupId = deriveEmployeeGroupId;
	}
	public int getDeriveGroupId() {
		return deriveGroupId;
	}
	public void setDeriveGroupId(int deriveGroupId) {
		this.deriveGroupId = deriveGroupId;
	}
	public Integer getDeriveTransportDestination() {
		return deriveTransportDestination;
	}
	public void setDeriveTransportDestination(Integer deriveTransportDestination) {
		this.deriveTransportDestination = deriveTransportDestination;
	}	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Integer getDerivecompanyId() {
		return derivecompanyId;
	}
	public void setDerivecompanyId(Integer derivecompanyId) {
		this.derivecompanyId = derivecompanyId;
	}	
	public String getFarmName() {
		return farmName;
	}
	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}
	public String getSowSource() {
		return sowSource;
	}
	public void setSowSource(String sowSource) {
		this.sowSource = sowSource;
	}
	public Integer getDerivePremiseId() {
		return derivePremiseId;
	}
	public void setDerivePremiseId(Integer derivePremiseId) {
		this.derivePremiseId = derivePremiseId;
	}
	public Integer getDeriveSowSourceId() {
		return deriveSowSourceId;
	}
	public void setDeriveSowSourceId(Integer deriveSowSourceId) {
		this.deriveSowSourceId = deriveSowSourceId;
	}
	@Override
	public String getId() {
		if (getGroupId() == null) {
			return UUID.randomUUID().toString().replaceAll("-", "");
		} else {
			return getGroupId();
		}
	}
	
}
