package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class PigletStatusInfoMapper extends AbstractMapper {
	
	private String pigId;
	private String companyId;
	private String farmName;
	private String eventType;
	private String farrowDate;
	private String numberOfPigs;
	private String weightOfPigs;
	private String eventDate;
	private String weanGroupEventId;
	private String transferredToPig;
	private String mortalityReason;
	private String weanGroupId;
	private String pen;
	private String remarks;
	private String sowCondition;
	
	// Derivable properties
	private Integer deriveCompanyId;
	private Integer derivePremiseId;
	private Integer derivePigInfoId;	
	private Date deriveFarrowDate;
	private Integer deriveFarrowEventId;
	private Integer derivePigNum;
	private Double derivePigWt;
	private Date deriveEventDate;
	private Integer derivePigletStatusEventTypeId;
	private Integer deriveGroupEventId;
	private Integer deriveTransferredPigInfoId;
	private Integer deriveMortalityReasonId;
	private Integer deriveSowCondition;	
	private Integer deriveFosterFarrowEventId;
	private boolean isWeanType;
	private boolean isTransferType;
	private boolean isDeathType;
	private Integer derivePenId;

	public String getPigId() {
		return pigId;
	}



	public void setPigId(String pigId) {
		this.pigId = pigId;
	}



	public String getFarrowDate() {
		return farrowDate;
	}



	public void setFarrowDate(String farrowDate) {
		this.farrowDate = farrowDate;
	}




	public String getWeanGroupEventId() {
		return weanGroupEventId;
	}



	public void setWeanGroupEventId(String weanGroupEventId) {
		this.weanGroupEventId = weanGroupEventId;
	}	

	public String getTransferredToPig() {
		return transferredToPig;
	}



	public void setTransferredToPig(String transferredToPig) {
		this.transferredToPig = transferredToPig;
	}



	

	public String getMortalityReason() {
		return mortalityReason;
	}



	public void setMortalityReason(String mortalityReason) {
		this.mortalityReason = mortalityReason;
	}



	public String getWeanGroupId() {
		return weanGroupId;
	}



	public void setWeanGroupId(String weanGroupId) {
		this.weanGroupId = weanGroupId;
	}



	public String getRemarks() {
		return remarks;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	public String getSowCondition() {
		return sowCondition;
	}



	public void setSowCondition(String sowCondition) {
		this.sowCondition = sowCondition;
	}

	

	public Integer getDerivePigInfoId() {
		return derivePigInfoId;
	}



	public void setDerivePigInfoId(Integer derivePigInfoId) {
		this.derivePigInfoId = derivePigInfoId;
	}



	public Date getDeriveFarrowDate() {
		return deriveFarrowDate;
	}



	public void setDeriveFarrowDate(Date deriveFarrowDate) {
		this.deriveFarrowDate = deriveFarrowDate;
	}



	public Integer getDeriveFarrowEventId() {
		return deriveFarrowEventId;
	}



	public void setDeriveFarrowEventId(Integer deriveFarrowEventId) {
		this.deriveFarrowEventId = deriveFarrowEventId;
	}



	

	public Integer getDeriveTransferredPigInfoId() {
		return deriveTransferredPigInfoId;
	}



	public void setDeriveTransferredPigInfoId(Integer deriveTransferredPigInfoId) {
		this.deriveTransferredPigInfoId = deriveTransferredPigInfoId;
	}


	public Integer getDeriveMortalityReasonId() {
		return deriveMortalityReasonId;
	}



	public void setDeriveMortalityReasonId(Integer deriveMortalityReasonId) {
		this.deriveMortalityReasonId = deriveMortalityReasonId;
	}



	public Integer getDeriveSowCondition() {
		return deriveSowCondition;
	}



	public void setDeriveSowCondition(Integer deriveSowCondition) {
		this.deriveSowCondition = deriveSowCondition;
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

	
	

	public Integer getDeriveFosterFarrowEventId() {
		return deriveFosterFarrowEventId;
	}



	public void setDeriveFosterFarrowEventId(Integer deriveFosterFarrowEventId) {
		this.deriveFosterFarrowEventId = deriveFosterFarrowEventId;
	}

	


	public boolean isWeanType() {
		return isWeanType;
	}



	public void setWeanType(boolean isWeanType) {
		this.isWeanType = isWeanType;
	}



	public boolean isTransferType() {
		return isTransferType;
	}



	public void setTransferType(boolean isTransferType) {
		this.isTransferType = isTransferType;
	}



	public boolean isDeathType() {
		return isDeathType;
	}



	public void setDeathType(boolean isDeathType) {
		this.isDeathType = isDeathType;
	}



	public String getFarmName() {
		return farmName;
	}



	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}



	public String getEventType() {
		return eventType;
	}



	public void setEventType(String eventType) {
		this.eventType = eventType;
	}



	public String getNumberOfPigs() {
		return numberOfPigs;
	}



	public void setNumberOfPigs(String numberOfPigs) {
		this.numberOfPigs = numberOfPigs;
	}



	public String getWeightOfPigs() {
		return weightOfPigs;
	}



	public void setWeightOfPigs(String weightOfPigs) {
		this.weightOfPigs = weightOfPigs;
	}



	public String getEventDate() {
		return eventDate;
	}



	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}



	public String getPen() {
		return pen;
	}



	public void setPen(String pen) {
		this.pen = pen;
	}



	public Integer getDerivePremiseId() {
		return derivePremiseId;
	}



	public void setDerivePremiseId(Integer derivePremiseId) {
		this.derivePremiseId = derivePremiseId;
	}



	public Integer getDerivePigNum() {
		return derivePigNum;
	}



	public void setDerivePigNum(Integer derivePigNum) {
		this.derivePigNum = derivePigNum;
	}



	public Double getDerivePigWt() {
		return derivePigWt;
	}



	public void setDerivePigWt(Double derivePigWt) {
		this.derivePigWt = derivePigWt;
	}



	public Date getDeriveEventDate() {
		return deriveEventDate;
	}



	public void setDeriveEventDate(Date deriveEventDate) {
		this.deriveEventDate = deriveEventDate;
	}



	public Integer getDerivePigletStatusEventTypeId() {
		return derivePigletStatusEventTypeId;
	}



	public void setDerivePigletStatusEventTypeId(
			Integer derivePigletStatusEventTypeId) {
		this.derivePigletStatusEventTypeId = derivePigletStatusEventTypeId;
	}



	public Integer getDeriveGroupEventId() {
		return deriveGroupEventId;
	}



	public void setDeriveGroupEventId(Integer deriveGroupEventId) {
		this.deriveGroupEventId = deriveGroupEventId;
	}



	public Integer getDerivePenId() {
		return derivePenId;
	}



	public void setDerivePenId(Integer derivePenId) {
		this.derivePenId = derivePenId;
	}



	@Override
	public String getId() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[ Pig Id :"+getPigId()+", Company Id: "+getCompanyId()+", Wean Date : "+getDeriveEventDate()+", Event Type"+getEventType()+"]");
		return buffer.toString();
	}
}
