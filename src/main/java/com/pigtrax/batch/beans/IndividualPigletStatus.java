package com.pigtrax.batch.beans;

public class IndividualPigletStatus {
	
	private Integer pigInfoId;
	private Integer farrowEventId;
	private String tattooId;
	private Double wtAtBirth;
	private Double wtAtWeaning;
	private String userUpdated;
	
	public Integer getPigInfoId() {
		return pigInfoId;
	}
	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}
	public Integer getFarrowEventId() {
		return farrowEventId;
	}
	public void setFarrowEventId(Integer farrowEventId) {
		this.farrowEventId = farrowEventId;
	}
	public Double getWtAtBirth() {
		return wtAtBirth;
	}
	public void setWtAtBirth(Double wtAtBirth) {
		this.wtAtBirth = wtAtBirth;
	}
	public Double getWtAtWeaning() {
		return wtAtWeaning;
	}
	public void setWtAtWeaning(Double wtAtWeaning) {
		this.wtAtWeaning = wtAtWeaning;
	}
	public String getUserUpdated() {
		return userUpdated;
	}
	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}
	public String getTattooId() {
		return tattooId;
	}
	public void setTattooId(String tattooId) {
		this.tattooId = tattooId;
	}
	
	
}
