package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class PigletStatusInfoMapper extends AbstractMapper {
	
	private String pigId;
	private String companyId;
	private String farrowDate;
	private String numberOfPigsWeaned;
	private String weightOfPigsWeaned;
	private String weaningDate;
	private String weanGroupEventId;
	private String numberOfPigsTransferred;
	private String weightOfPigsTransferred;
	private String transferredDate;	
	private String transferredToPig;
	private String numberOfPigsMortality;
	private String weightOfPigsMortality;
	private String mortalityEventDate;
	private String mortalityReason;
	private String weanGroupId;
	private String remarks;
	private String sowCondition;
	
	// Derivable properties
	private Integer deriveCompanyId;
	private Integer derivePigInfoId;	
	private Date deriveFarrowDate;
	private Integer deriveFarrowEventId;
	private Integer deriveWeanPigNum;
	private Double deriveWeanPigWt;
	private Date deriveWeanDate;
	private Integer deriveGroupEventId;
	private Integer deriveTransferPigNum;
	private Double deriveTransferPigWt;
	private Date deriveTransferDate;
	private Integer deriveTransferredPigInfoId;
	private Integer deriveMortalityPigNum;
	private Double deriveMortalityPigWt;
	private Date deriveMortalityEventDate;
	private Integer deriveMortalityReasonId;
	private Integer deriveSowCondition;	
	private Integer deriveFosterFarrowEventId;
	private boolean isWeanType;
	private boolean isTransferType;
	private boolean isDeathType;

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



	public String getNumberOfPigsWeaned() {
		return numberOfPigsWeaned;
	}



	public void setNumberOfPigsWeaned(String numberOfPigsWeaned) {
		this.numberOfPigsWeaned = numberOfPigsWeaned;
	}



	public String getWeightOfPigsWeaned() {
		return weightOfPigsWeaned;
	}



	public void setWeightOfPigsWeaned(String weightOfPigsWeaned) {
		this.weightOfPigsWeaned = weightOfPigsWeaned;
	}



	public String getWeaningDate() {
		return weaningDate;
	}



	public void setWeaningDate(String weaningDate) {
		this.weaningDate = weaningDate;
	}



	public String getWeanGroupEventId() {
		return weanGroupEventId;
	}



	public void setWeanGroupEventId(String weanGroupEventId) {
		this.weanGroupEventId = weanGroupEventId;
	}



	public String getNumberOfPigsTransferred() {
		return numberOfPigsTransferred;
	}



	public void setNumberOfPigsTransferred(String numberOfPigsTransferred) {
		this.numberOfPigsTransferred = numberOfPigsTransferred;
	}



	public String getWeightOfPigsTransferred() {
		return weightOfPigsTransferred;
	}



	public void setWeightOfPigsTransferred(String weightOfPigsTransferred) {
		this.weightOfPigsTransferred = weightOfPigsTransferred;
	}



	public String getTransferredDate() {
		return transferredDate;
	}



	public void setTransferredDate(String transferredDate) {
		this.transferredDate = transferredDate;
	}



	public String getTransferredToPig() {
		return transferredToPig;
	}



	public void setTransferredToPig(String transferredToPig) {
		this.transferredToPig = transferredToPig;
	}



	public String getNumberOfPigsMortality() {
		return numberOfPigsMortality;
	}



	public void setNumberOfPigsMortality(String numberOfPigsMortality) {
		this.numberOfPigsMortality = numberOfPigsMortality;
	}



	public String getWeightOfPigsMortality() {
		return weightOfPigsMortality;
	}



	public void setWeightOfPigsMortality(String weightOfPigsMortality) {
		this.weightOfPigsMortality = weightOfPigsMortality;
	}



	public String getMortalityEventDate() {
		return mortalityEventDate;
	}



	public void setMortalityEventDate(String mortalityEventDate) {
		this.mortalityEventDate = mortalityEventDate;
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



	public Integer getDeriveWeanPigNum() {
		return deriveWeanPigNum;
	}



	public void setDeriveWeanPigNum(Integer deriveWeanPigNum) {
		this.deriveWeanPigNum = deriveWeanPigNum;
	}



	public Double getDeriveWeanPigWt() {
		return deriveWeanPigWt;
	}



	public void setDeriveWeanPigWt(Double deriveWeanPigWt) {
		this.deriveWeanPigWt = deriveWeanPigWt;
	}



	public Date getDeriveWeanDate() {
		return deriveWeanDate;
	}



	public void setDeriveWeanDate(Date deriveWeanDate) {
		this.deriveWeanDate = deriveWeanDate;
	}



	public Integer getDeriveGroupEventId() {
		return deriveGroupEventId;
	}



	public void setDeriveGroupEventId(Integer deriveGroupEventId) {
		this.deriveGroupEventId = deriveGroupEventId;
	}



	public Integer getDeriveTransferPigNum() {
		return deriveTransferPigNum;
	}



	public void setDeriveTransferPigNum(Integer deriveTransferPigNum) {
		this.deriveTransferPigNum = deriveTransferPigNum;
	}



	public Double getDeriveTransferPigWt() {
		return deriveTransferPigWt;
	}



	public void setDeriveTransferPigWt(Double deriveTransferPigWt) {
		this.deriveTransferPigWt = deriveTransferPigWt;
	}



	public Date getDeriveTransferDate() {
		return deriveTransferDate;
	}



	public void setDeriveTransferDate(Date deriveTransferDate) {
		this.deriveTransferDate = deriveTransferDate;
	}



	public Integer getDeriveTransferredPigInfoId() {
		return deriveTransferredPigInfoId;
	}



	public void setDeriveTransferredPigInfoId(Integer deriveTransferredPigInfoId) {
		this.deriveTransferredPigInfoId = deriveTransferredPigInfoId;
	}



	public Integer getDeriveMortalityPigNum() {
		return deriveMortalityPigNum;
	}



	public void setDeriveMortalityPigNum(Integer deriveMortalityPigNum) {
		this.deriveMortalityPigNum = deriveMortalityPigNum;
	}



	public Double getDeriveMortalityPigWt() {
		return deriveMortalityPigWt;
	}



	public void setDeriveMortalityPigWt(Double deriveMortalityPigWt) {
		this.deriveMortalityPigWt = deriveMortalityPigWt;
	}



	public Date getDeriveMortalityEventDate() {
		return deriveMortalityEventDate;
	}



	public void setDeriveMortalityEventDate(Date deriveMortalityEventDate) {
		this.deriveMortalityEventDate = deriveMortalityEventDate;
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



	@Override
	public String getId() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[ Pig Id :"+getPigId()+", Company Id: "+getCompanyId()+", Wean Date : "+getWeaningDate()+", Transferred Date :"+getTransferredDate()+", Mortality Date:"+getMortalityEventDate()+"]");
		return buffer.toString();
	}
}
