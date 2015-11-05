package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class BreedingEventMapper extends AbstractMapper {
	
	private String pigId;
	private String companyId;
	private String serviceType;
	private String serviceGroupId;
	private String sowCondition;
	private String pen;
	private String weightInKgs;
	
	//derived properties
	private Integer derivePigInfoId;
	private Integer deriveCompanyId;
	private Integer deriveServiceTypeId;
	private Integer derivePenId;
	private Integer deriveSowCondition;
	private Double deriveWtInKgs;
	
	
	public String getPigId() {
		return pigId;
	}



	public void setPigId(String pigId) {
		this.pigId = pigId;
	}



	public String getCompanyId() {
		return companyId;
	}



	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	

	public String getServiceType() {
		return serviceType;
	}



	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}



	public String getServiceGroupId() {
		return serviceGroupId;
	}



	public void setServiceGroupId(String serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}



	public String getSowCondition() {
		return sowCondition;
	}



	public void setSowCondition(String sowCondition) {
		this.sowCondition = sowCondition;
	}



	public String getPen() {
		return pen;
	}



	public void setPen(String pen) {
		this.pen = pen;
	}



	public String getWeightInKgs() {
		return weightInKgs;
	}



	public void setWeightInKgs(String weightInKgs) {
		this.weightInKgs = weightInKgs;
	}



	public Integer getDerivePigInfoId() {
		return derivePigInfoId;
	}



	public void setDerivePigInfoId(Integer derivePigInfoId) {
		this.derivePigInfoId = derivePigInfoId;
	}



	public Integer getDeriveCompanyId() {
		return deriveCompanyId;
	}



	public void setDeriveCompanyId(Integer deriveCompanyId) {
		this.deriveCompanyId = deriveCompanyId;
	}



	public Integer getDeriveServiceTypeId() {
		return deriveServiceTypeId;
	}



	public void setDeriveServiceTypeId(Integer deriveServiceTypeId) {
		this.deriveServiceTypeId = deriveServiceTypeId;
	}



	public Integer getDerivePenId() {
		return derivePenId;
	}



	public void setDerivePenId(Integer derivePenId) {
		this.derivePenId = derivePenId;
	}



	public Integer getDeriveSowCondition() {
		return deriveSowCondition;
	}



	public void setDeriveSowCondition(Integer deriveSowCondition) {
		this.deriveSowCondition = deriveSowCondition;
	}



	public Double getDeriveWtInKgs() {
		return deriveWtInKgs;
	}



	public void setDeriveWtInKgs(Double deriveWtInKgs) {
		this.deriveWtInKgs = deriveWtInKgs;
	}



	@Override
	public String getId() {
		if (getPigId() == null) {
			return UUID.randomUUID().toString().replaceAll("-", "");
		} else {
			return getPigId().toString();
		}
	}
}
