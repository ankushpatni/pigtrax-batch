package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class FarrowEventMapper extends AbstractMapper {

	private String pigId;
	private String serviceDate;
	private String penId;
	private String farrowDate;
	private String liveBorns;
	private String stillBorns;
	private String maleBorns;
	private String femaleBorns;
	private String mummies;
	private String weightInKGs;
	private String typeOfBirth;
	private String employeeGrpId;
	private String sowCondition;
	private String teats;
	private String companyId;
	private String farmName;
	private String weakBorns;
	
	private Integer derivePigInfoId;
	private Date deriveServiceDate;
	private Integer derivePenId;
	private Date deriveFarrowDate;
	private Integer deriveLiveBorns;
	private Integer deriveStillBorns;
	private Integer deriveMaleBorns;
	private Integer deriveFemaleBorns;
	private Integer deriveMummies;
	private Double deriveWeightInKGs;
	private Integer deriveEmployeeGrpId;
	private Integer deriveSowCondition;
	private Integer deriveTeasts;
	private Integer deriveCompanyId;
	private Integer pragnancyEventId;
	private Integer derivePremiseId;
	private Integer deriveWeakBorns;
	private Integer breedingEventId;

	public Integer getPragnancyEventId() {
		return pragnancyEventId;
	}

	public void setPragnancyEventId(Integer pragnancyEventId) {
		this.pragnancyEventId = pragnancyEventId;
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

	public String getPigId() {
		return pigId;
	}

	public void setPigId(String pigId) {
		this.pigId = pigId;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getPenId() {
		return penId;
	}

	public void setPenId(String penId) {
		this.penId = penId;
	}

	public String getFarrowDate() {
		return farrowDate;
	}

	public void setFarrowDate(String farrowDate) {
		this.farrowDate = farrowDate;
	}

	public String getLiveBorns() {
		return liveBorns;
	}

	public void setLiveBorns(String liveBorns) {
		this.liveBorns = liveBorns;
	}

	public String getStillBorns() {
		return stillBorns;
	}

	public void setStillBorns(String stillBorns) {
		this.stillBorns = stillBorns;
	}

	public String getMaleBorns() {
		return maleBorns;
	}

	public void setMaleBorns(String maleBorns) {
		this.maleBorns = maleBorns;
	}

	public String getFemaleBorns() {
		return femaleBorns;
	}

	public void setFemaleBorns(String femaleBorns) {
		this.femaleBorns = femaleBorns;
	}

	public String getMummies() {
		return mummies;
	}

	public void setMummies(String mummies) {
		this.mummies = mummies;
	}

	public String getWeightInKGs() {
		return weightInKGs;
	}

	public void setWeightInKGs(String weightInKGs) {
		this.weightInKGs = weightInKGs;
	}

	public String getTypeOfBirth() {
		return typeOfBirth;
	}

	public void setTypeOfBirth(String typeOfBirth) {
		this.typeOfBirth = typeOfBirth;
	}

	public String getEmployeeGrpId() {
		return employeeGrpId;
	}

	public void setEmployeeGrpId(String employeeGrpId) {
		this.employeeGrpId = employeeGrpId;
	}

	public String getSowCondition() {
		return sowCondition;
	}

	public void setSowCondition(String sowCondition) {
		this.sowCondition = sowCondition;
	}

	public String getTeats() {
		return teats;
	}

	public void setTeats(String teats) {
		this.teats = teats;
	}

	public Integer getDerivePigInfoId() {
		return derivePigInfoId;
	}

	public void setDerivePigInfoId(Integer derivePigInfoId) {
		this.derivePigInfoId = derivePigInfoId;
	}

	public Date getDeriveServiceDate() {
		return deriveServiceDate;
	}

	public void setDeriveServiceDate(Date deriveServiceDate) {
		this.deriveServiceDate = deriveServiceDate;
	}

	public Integer getDerivePenId() {
		return derivePenId;
	}

	public void setDerivePenId(Integer derivePenId) {
		this.derivePenId = derivePenId;
	}

	public Date getDeriveFarrowDate() {
		return deriveFarrowDate;
	}

	public void setDeriveFarrowDate(Date deriveFarrowDate) {
		this.deriveFarrowDate = deriveFarrowDate;
	}

	public Integer getDeriveLiveBorns() {
		return deriveLiveBorns;
	}

	public void setDeriveLiveBorns(Integer deriveLiveBorns) {
		this.deriveLiveBorns = deriveLiveBorns;
	}

	public Integer getDeriveStillBorns() {
		return deriveStillBorns;
	}

	public void setDeriveStillBorns(Integer deriveStillBorns) {
		this.deriveStillBorns = deriveStillBorns;
	}

	public Integer getDeriveMaleBorns() {
		return deriveMaleBorns;
	}

	public void setDeriveMaleBorns(Integer deriveMaleBorns) {
		this.deriveMaleBorns = deriveMaleBorns;
	}

	public Integer getDeriveFemaleBorns() {
		return deriveFemaleBorns;
	}

	public void setDeriveFemaleBorns(Integer deriveFemaleBorns) {
		this.deriveFemaleBorns = deriveFemaleBorns;
	}

	public Integer getDeriveMummies() {
		return deriveMummies;
	}

	public void setDeriveMummies(Integer deriveMummies) {
		this.deriveMummies = deriveMummies;
	}

	public Double getDeriveWeightInKGs() {
		return deriveWeightInKGs;
	}

	public void setDeriveWeightInKGs(Double deriveWeightInKGs) {
		this.deriveWeightInKGs = deriveWeightInKGs;
	}

	public Integer getDeriveEmployeeGrpId() {
		return deriveEmployeeGrpId;
	}

	public void setDeriveEmployeeGrpId(Integer deriveEmployeeGrpId) {
		this.deriveEmployeeGrpId = deriveEmployeeGrpId;
	}

	public Integer getDeriveSowCondition() {
		return deriveSowCondition;
	}

	public void setDeriveSowCondition(Integer deriveSowCondition) {
		this.deriveSowCondition = deriveSowCondition;
	}

	public Integer getDeriveTeasts() {
		return deriveTeasts;
	}

	public void setDeriveTeasts(Integer deriveTeasts) {
		this.deriveTeasts = deriveTeasts;
	}

	@Override
	public String getId() {		
		return "["+getPigId()+", "+getCompanyId()+", "+getFarrowDate()+"]";
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

	public String getWeakBorns() {
		return weakBorns;
	}

	public void setWeakBorns(String weakBorns) {
		this.weakBorns = weakBorns;
	}

	public Integer getDeriveWeakBorns() {
		return deriveWeakBorns;
	}

	public void setDeriveWeakBorns(Integer deriveWeakBorns) {
		this.deriveWeakBorns = deriveWeakBorns;
	}

	public Integer getBreedingEventId() {
		return breedingEventId;
	}

	public void setBreedingEventId(Integer breedingEventId) {
		this.breedingEventId = breedingEventId;
	}
	
	public boolean isEmpty()
	{	
		if((this.penId==null || this.penId.trim().length() == 0)
				&& (this.pigId==null || this.pigId.trim().length() == 0)
				&& (this.farrowDate == null || this.farrowDate.trim().length() == 0)
				&& (this.liveBorns == null || this.liveBorns.trim().length() == 0)
				&& (this.stillBorns == null || this.stillBorns.trim().length() == 0)
				&& (this.mummies == null || this.mummies.trim().length() == 0)
				&& (this.weakBorns == null || this.weakBorns.trim().length() == 0)
				&& (this.maleBorns == null || this.maleBorns.trim().length() == 0)
				&& (this.femaleBorns == null || this.femaleBorns.trim().length() == 0)
				&& (this.weightInKGs == null || this.weightInKGs.trim().length() == 0)
				&& (this.typeOfBirth == null || this.typeOfBirth.trim().length() == 0)
				&& (this.employeeGrpId == null || this.employeeGrpId.trim().length() == 0)
				&& (this.sowCondition == null || this.sowCondition.trim().length() == 0)
				&& (this.teats == null || this.teats.trim().length() == 0)
				)
			return true;
		else
			return false;
	}	

}
