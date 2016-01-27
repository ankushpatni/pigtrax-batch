package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class MatingDetailsMapper extends AbstractMapper {
	
	private String pigId;
	private String companyId;
	private String matingDate;
	private String employeeGroup;
	private String semenId;
	private String mateQuality;	
	private String farmName;
	private String semenDate;
	
	//Derived properties
	private Integer deriveCompanyId;
	private Integer derivePigInfoId;
	private Date deriveMatingDate;
	private Integer deriveEmployeeGroupId;
	private Integer deriveMateQuality;
	private Integer deriveBreedingEventId;
	private boolean updateServiceStartDate;
	private Integer derivePremiseId;
	private Date deriveSemenDate;
	
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



	public String getMatingDate() {
		return matingDate;
	}



	public void setMatingDate(String matingDate) {
		this.matingDate = matingDate;
	}



	public String getEmployeeGroup() {
		return employeeGroup;
	}



	public void setEmployeeGroup(String employeeGroup) {
		this.employeeGroup = employeeGroup;
	}



	public String getSemenId() {
		return semenId;
	}



	public void setSemenId(String semenId) {
		this.semenId = semenId;
	}



	public String getMateQuality() {
		return mateQuality;
	}



	public void setMateQuality(String mateQuality) {
		this.mateQuality = mateQuality;
	}


	

	public Integer getDeriveCompanyId() {
		return deriveCompanyId;
	}



	public void setDeriveCompanyId(Integer deriveCompanyId) {
		this.deriveCompanyId = deriveCompanyId;
	}



	public Integer getDerivePigInfoId() {
		return derivePigInfoId;
	}



	public void setDerivePigInfoId(Integer derivePigInfoId) {
		this.derivePigInfoId = derivePigInfoId;
	}



	public Date getDeriveMatingDate() {
		return deriveMatingDate;
	}



	public void setDeriveMatingDate(Date deriveMatingDate) {
		this.deriveMatingDate = deriveMatingDate;
	}



	public Integer getDeriveEmployeeGroupId() {
		return deriveEmployeeGroupId;
	}



	public void setDeriveEmployeeGroupId(Integer deriveEmployeeGroupId) {
		this.deriveEmployeeGroupId = deriveEmployeeGroupId;
	}



	public Integer getDeriveMateQuality() {
		return deriveMateQuality;
	}



	public void setDeriveMateQuality(Integer deriveMateQuality) {
		this.deriveMateQuality = deriveMateQuality;
	}

	


	public Integer getDeriveBreedingEventId() {
		return deriveBreedingEventId;
	}



	public void setDeriveBreedingEventId(Integer deriveBreedingEventId) {
		this.deriveBreedingEventId = deriveBreedingEventId;
	}

	

	public boolean isUpdateServiceStartDate() {
		return updateServiceStartDate;
	}



	public void setUpdateServiceStartDate(boolean updateServiceStartDate) {
		this.updateServiceStartDate = updateServiceStartDate;
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

	
	
	public String getSemenDate() {
		return semenDate;
	}



	public void setSemenDate(String semenDate) {
		this.semenDate = semenDate;
	}



	public Date getDeriveSemenDate() {
		return deriveSemenDate;
	}



	public void setDeriveSemenDate(Date deriveSemenDate) {
		this.deriveSemenDate = deriveSemenDate;
	}



	@Override
	public String getId() {
		if (getPigId() == null) {
			return UUID.randomUUID().toString().replaceAll("-", "");
		} else {
			return getPigId().toString();
		}
	}
	
	public boolean isEmpty()
	{	
		if((this.pigId==null || this.pigId.trim().length() == 0)
				&& (this.matingDate==null || this.matingDate.trim().length() == 0)
				&& (this.employeeGroup == null || this.employeeGroup.trim().length() == 0)
				&& (this.semenId == null || this.semenId.trim().length() == 0)
				&& (this.mateQuality == null || this.mateQuality.trim().length() == 0)
				&& (this.semenDate == null || this.semenDate.trim().length() == 0))
			return true;
		else
			return false;
	}
}
