package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.pigtrax.batch.beans.RoomPK;
import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class GroupEventInfoMapper extends AbstractMapper{
	
	private String groupId;
	private String companyId;
	private String groupStartDate;
	private String phaseOfProductionType;
	private String remarks;
	private String groupCloseDate;
	private boolean isActive;
	private String userUpdated;
	private String currentInventory;
	private String previousGroupId;
	private String rooms;
	private String farmName;
		
	//derived field
	private Integer derivePremiseId;
	private Integer derivePhaseOfProductionTypeId;
	private Integer deriveCompanyId;
	private Date deriveGroupStartDateTime;
	private Date deriveGroupcloseDateTime;
	private Integer deriveCurrentInventory;
	private Integer derivePreviousGroupId;
	private List<RoomPK> deriveRoomIds;

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
		return groupStartDate;
	}

	public void setGroupStartDate(String groupStartDate) {
		this.groupStartDate = groupStartDate;
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

	public String getGroupCloseDate() {
		return groupCloseDate;
	}

	public void setGroupCloseDate(String groupCloseDate) {
		this.groupCloseDate = groupCloseDate;
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

	public String getRooms() {
		return rooms;
	}

	public void setRooms(String rooms) {
		this.rooms = rooms;
	}

	public List<RoomPK> getDeriveRoomIds() {
		return deriveRoomIds;
	}

	public void setDeriveRoomIds(List<RoomPK> deriveRoomIds) {
		this.deriveRoomIds = deriveRoomIds;
	}

	public String getGroupStartDate() {
		return groupStartDate;
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


	
	public boolean isEmpty()
	{	
		if((this.rooms==null || this.rooms.trim().length() == 0)
				&& (this.groupId==null || this.groupId.trim().length() == 0)
				&& (this.groupStartDate == null || this.groupStartDate.trim().length() == 0)
				&& (this.phaseOfProductionType == null || this.phaseOfProductionType.trim().length() == 0)
				&& (this.groupCloseDate == null || this.groupCloseDate.trim().length() == 0)
				&& (this.remarks == null || this.remarks.trim().length() == 0)
				&& (this.currentInventory == null || this.currentInventory.trim().length() == 0)
				)
			return true;
		else
			return false;
	}	
	
}
