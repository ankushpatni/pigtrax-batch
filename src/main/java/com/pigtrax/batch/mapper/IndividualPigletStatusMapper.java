package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class IndividualPigletStatusMapper extends AbstractMapper {
	
	private String pigId;
	private String companyId;
	private String farrowDate;
	private String tattooId;
	private String wtAtBirth;
	private String wtAtWeaning;

	// Derivable properties
	private Integer derivePigInfoId;
	private Integer deriveCompanyId;
	private Date farrowEventDate;
	private Integer deriveFarrowEventId;
	private Double deriveWtAtBirth;
	private Double deriveWtAtWeaning;
	
	
	
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



	public String getFarrowDate() {
		return farrowDate;
	}



	public void setFarrowDate(String farrowDate) {
		this.farrowDate = farrowDate;
	}



	public String getTattooId() {
		return tattooId;
	}



	public void setTattooId(String tattooId) {
		this.tattooId = tattooId;
	}



	public String getWtAtBirth() {
		return wtAtBirth;
	}



	public void setWtAtBirth(String wtAtBirth) {
		this.wtAtBirth = wtAtBirth;
	}



	public String getWtAtWeaning() {
		return wtAtWeaning;
	}



	public void setWtAtWeaning(String wtAtWeaning) {
		this.wtAtWeaning = wtAtWeaning;
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



	public Integer getDeriveFarrowEventId() {
		return deriveFarrowEventId;
	}



	public void setDeriveFarrowEventId(Integer deriveFarrowEventId) {
		this.deriveFarrowEventId = deriveFarrowEventId;
	}



	public Double getDeriveWtAtBirth() {
		return deriveWtAtBirth;
	}



	public void setDeriveWtAtBirth(Double deriveWtAtBirth) {
		this.deriveWtAtBirth = deriveWtAtBirth;
	}



	public Double getDeriveWtAtWeaning() {
		return deriveWtAtWeaning;
	}



	public void setDeriveWtAtWeaning(Double deriveWtAtWeaning) {
		this.deriveWtAtWeaning = deriveWtAtWeaning;
	}

    

	public Date getFarrowEventDate() {
		return farrowEventDate;
	}



	public void setFarrowEventDate(Date farrowEventDate) {
		this.farrowEventDate = farrowEventDate;
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
