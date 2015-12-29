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
}
