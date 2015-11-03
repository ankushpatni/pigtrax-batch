package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class GroupEventInfoMapper extends AbstractMapper{
	
	private String groupId;
	private String companyId;
	private String groupStartDateTime;
	private String phaseOfProductionType;
	private String remarks;
	private String groupCloseDateTime;
	private boolean isActive;
	private String userUpdated;
	private String currentInventory;
	private String previousGroupId;
		
	//derived field
	private Integer derivePhaseOfProductionTypeId;
	private Integer deriveCompanyId;
	private Date deriveGroupStartDateTime;
	private Date deriveGroupcloseDateTime;
	private Integer deriveCurrentInventory;
	private Integer derivePreviousGroupId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPhaseOfProductionType() {
		return phaseOfProductionType;
	}

	public void setPhaseOfProductionType(String phaseOfProductionType) {
		this.phaseOfProductionType = phaseOfProductionType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getDeriveCompanyId() {
		return deriveCompanyId;
	}

	public void setDeriveCompanyId(Integer deriveCompanyId) {
		this.deriveCompanyId = deriveCompanyId;
	}

	public String getGroupStartDateTime() {
		return groupStartDateTime;
	}

	public void setGroupStartDateTime(String groupStartDateTime) {
		this.groupStartDateTime = groupStartDateTime;
	}

	public Date getDeriveGroupStartDateTime() {
		return deriveGroupStartDateTime;
	}

	public void setDeriveGroupStartDateTime(Date deriveGroupStartDateTime) {
		this.deriveGroupStartDateTime = deriveGroupStartDateTime;
	}

	public Integer getDerivePhaseOfProductionTypeId() {
		return derivePhaseOfProductionTypeId;
	}

	public void setDerivePhaseOfProductionTypeId(
			Integer derivePhaseOfProductionTypeId) {
		this.derivePhaseOfProductionTypeId = derivePhaseOfProductionTypeId;
	}

	public String getGroupCloseDateTime() {
		return groupCloseDateTime;
	}

	public void setGroupCloseDateTime(String groupCloseDateTime) {
		this.groupCloseDateTime = groupCloseDateTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}

	public String getCurrentInventory() {
		return currentInventory;
	}

	public void setCurrentInventory(String currentInventory) {
		this.currentInventory = currentInventory;
	}

	public String getPreviousGroupId() {
		return previousGroupId;
	}

	public void setPreviousGroupId(String previousGroupId) {
		this.previousGroupId = previousGroupId;
	}
	
	public Date getDeriveGroupcloseDateTime() {
		return deriveGroupcloseDateTime;
	}

	public void setDeriveGroupcloseDateTime(Date deriveGroupcloseDateTime) {
		this.deriveGroupcloseDateTime = deriveGroupcloseDateTime;
	}

	public Integer getDeriveCurrentInventory() {
		return deriveCurrentInventory;
	}

	public void setDeriveCurrentInventory(Integer deriveCurrentInventory) {
		this.deriveCurrentInventory = deriveCurrentInventory;
	}

	@Override
	public String getId() {
		if (getGroupId() == null) {
			return UUID.randomUUID().toString().replaceAll("-", "");
		} else {
			return getGroupId();
		}
	}

	public Integer getDerivePreviousGroupId() {
		return derivePreviousGroupId;
	}

	public void setDerivePreviousGroupId(Integer derivePreviousGroupId) {
		this.derivePreviousGroupId = derivePreviousGroupId;
	}
}
