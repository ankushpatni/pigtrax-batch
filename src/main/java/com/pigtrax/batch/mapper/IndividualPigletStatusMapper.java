package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class IndividualPigletStatusMapper extends AbstractMapper {
	
	private String pigId;
	private String companyId;
	private String tattooId;
	private String wtAtBirth;
	private String wtAtWeaning;
	private String litterId;
	private String wtAtFirstMonth;
	private String wtAtSecondMonth;
	private String wtAtThirdMonth;
	private String wtAtFourthMonth;
	private String wtAtFifthMonth;
	private String wtAtSixthMonth;
	private String farmName;
	
	private String firstDate;
	private String secondDate;
	private String thirdDate;
	private String fourthDate;
	private String fifthDate;
	private String sixthDate;

	private Integer deriveCompanyId;
	private Integer derivePremiseId;
	private Integer deriveLitterId;
	private Double deriveWtAtBirth;
	private Double deriveWtAtWeaning;
	private Double deriveWtAtFirstMonth;
	private Double deriveWtAtSecondMonth;
	private Double deriveWtAtThirdMonth;
	private Double deriveWtAtFourthMonth;
	private Double deriveWtAtFifthMonth;
	private Double deriveWtAtSixthMonth;
	
	private Date deriveFirstDate;
	private Date deriveSecondDate;
	private Date deriveThirdDate;
	private Date deriveFourthDate;
	private Date deriveFifthDate;
	private Date deriveSixthDate;
	
	
	

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




	public String getLitterId() {
		return litterId;
	}




	public void setLitterId(String litterId) {
		this.litterId = litterId;
	}




	public String getWtAtFirstMonth() {
		return wtAtFirstMonth;
	}




	public void setWtAtFirstMonth(String wtAtFirstMonth) {
		this.wtAtFirstMonth = wtAtFirstMonth;
	}




	public String getWtAtSecondMonth() {
		return wtAtSecondMonth;
	}




	public void setWtAtSecondMonth(String wtAtSecondMonth) {
		this.wtAtSecondMonth = wtAtSecondMonth;
	}




	public String getWtAtThirdMonth() {
		return wtAtThirdMonth;
	}




	public void setWtAtThirdMonth(String wtAtThirdMonth) {
		this.wtAtThirdMonth = wtAtThirdMonth;
	}




	public String getWtAtFourthMonth() {
		return wtAtFourthMonth;
	}




	public void setWtAtFourthMonth(String wtAtFourthMonth) {
		this.wtAtFourthMonth = wtAtFourthMonth;
	}




	public String getWtAtFifthMonth() {
		return wtAtFifthMonth;
	}




	public void setWtAtFifthMonth(String wtAtFifthMonth) {
		this.wtAtFifthMonth = wtAtFifthMonth;
	}




	public String getWtAtSixthMonth() {
		return wtAtSixthMonth;
	}




	public void setWtAtSixthMonth(String wtAtSixthMonth) {
		this.wtAtSixthMonth = wtAtSixthMonth;
	}




	public String getFarmName() {
		return farmName;
	}




	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}




	public Integer getDeriveCompanyId() {
		return deriveCompanyId;
	}




	public void setDeriveCompanyId(Integer deriveCompanyId) {
		this.deriveCompanyId = deriveCompanyId;
	}




	public Integer getDerivePremiseId() {
		return derivePremiseId;
	}




	public void setDerivePremiseId(Integer derivePremiseId) {
		this.derivePremiseId = derivePremiseId;
	}




	public Integer getDeriveLitterId() {
		return deriveLitterId;
	}




	public void setDeriveLitterId(Integer deriveLitterId) {
		this.deriveLitterId = deriveLitterId;
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




	public Double getDeriveWtAtFirstMonth() {
		return deriveWtAtFirstMonth;
	}




	public void setDeriveWtAtFirstMonth(Double deriveWtAtFirstMonth) {
		this.deriveWtAtFirstMonth = deriveWtAtFirstMonth;
	}




	public Double getDeriveWtAtSecondMonth() {
		return deriveWtAtSecondMonth;
	}




	public void setDeriveWtAtSecondMonth(Double deriveWtAtSecondMonth) {
		this.deriveWtAtSecondMonth = deriveWtAtSecondMonth;
	}




	public Double getDeriveWtAtThirdMonth() {
		return deriveWtAtThirdMonth;
	}




	public void setDeriveWtAtThirdMonth(Double deriveWtAtThirdMonth) {
		this.deriveWtAtThirdMonth = deriveWtAtThirdMonth;
	}




	public Double getDeriveWtAtFourthMonth() {
		return deriveWtAtFourthMonth;
	}




	public void setDeriveWtAtFourthMonth(Double deriveWtAtFourthMonth) {
		this.deriveWtAtFourthMonth = deriveWtAtFourthMonth;
	}




	public Double getDeriveWtAtFifthMonth() {
		return deriveWtAtFifthMonth;
	}




	public void setDeriveWtAtFifthMonth(Double deriveWtAtFifthMonth) {
		this.deriveWtAtFifthMonth = deriveWtAtFifthMonth;
	}




	public Double getDeriveWtAtSixthMonth() {
		return deriveWtAtSixthMonth;
	}




	public void setDeriveWtAtSixthMonth(Double deriveWtAtSixthMonth) {
		this.deriveWtAtSixthMonth = deriveWtAtSixthMonth;
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
				&& (this.litterId==null || this.litterId.trim().length() == 0)
				&& (this.tattooId == null || this.tattooId.trim().length() == 0)
				&& (this.wtAtBirth == null || this.wtAtBirth.trim().length() == 0)
				&& (this.wtAtWeaning == null || this.wtAtWeaning.trim().length() == 0)
				&& (this.wtAtFirstMonth == null || this.wtAtFirstMonth.trim().length() == 0)
				&& (this.wtAtSecondMonth == null || this.wtAtSecondMonth.trim().length() == 0)
				&& (this.wtAtThirdMonth == null || this.wtAtThirdMonth.trim().length() == 0)
				&& (this.wtAtFourthMonth == null || this.wtAtFourthMonth.trim().length() == 0)
				&& (this.wtAtFifthMonth == null || this.wtAtFifthMonth.trim().length() == 0)
				&& (this.wtAtSixthMonth == null || this.wtAtSixthMonth.trim().length() == 0)
				)
			return true;
		else
			return false;
	}




	public String getFirstDate() {
		return firstDate;
	}




	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}




	public String getSecondDate() {
		return secondDate;
	}




	public void setSecondDate(String secondDate) {
		this.secondDate = secondDate;
	}




	public String getThirdDate() {
		return thirdDate;
	}




	public void setThirdDate(String thirdDate) {
		this.thirdDate = thirdDate;
	}




	public String getFourthDate() {
		return fourthDate;
	}




	public void setFourthDate(String fourthDate) {
		this.fourthDate = fourthDate;
	}




	public String getFifthDate() {
		return fifthDate;
	}




	public void setFifthDate(String fifthDate) {
		this.fifthDate = fifthDate;
	}




	public String getSixthDate() {
		return sixthDate;
	}




	public void setSixthDate(String sixthDate) {
		this.sixthDate = sixthDate;
	}




	public Date getDeriveFirstDate() {
		return deriveFirstDate;
	}




	public void setDeriveFirstDate(Date deriveFirstDate) {
		this.deriveFirstDate = deriveFirstDate;
	}




	public Date getDeriveSecondDate() {
		return deriveSecondDate;
	}




	public void setDeriveSecondDate(Date deriveSecondDate) {
		this.deriveSecondDate = deriveSecondDate;
	}




	public Date getDeriveThirdDate() {
		return deriveThirdDate;
	}




	public void setDeriveThirdDate(Date deriveThirdDate) {
		this.deriveThirdDate = deriveThirdDate;
	}




	public Date getDeriveFourthDate() {
		return deriveFourthDate;
	}




	public void setDeriveFourthDate(Date deriveFourthDate) {
		this.deriveFourthDate = deriveFourthDate;
	}




	public Date getDeriveFifthDate() {
		return deriveFifthDate;
	}




	public void setDeriveFifthDate(Date deriveFifthDate) {
		this.deriveFifthDate = deriveFifthDate;
	}




	public Date getDeriveSixthDate() {
		return deriveSixthDate;
	}




	public void setDeriveSixthDate(Date deriveSixthDate) {
		this.deriveSixthDate = deriveSixthDate;
	}	
	
	
}
