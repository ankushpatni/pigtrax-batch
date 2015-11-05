package com.pigtrax.batch.beans;

public class BreedingEvent {
	private Integer id;
	private Integer pigInfoId;
	private Integer breedingServiceTypeId;
	private String serviceGroupId;
	private Integer sowCondition;
	private Integer penId;
	private Double weight;
	private String userUpdated;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPigInfoId() {
		return pigInfoId;
	}

	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}

	public Integer getBreedingServiceTypeId() {
		return breedingServiceTypeId;
	}

	public void setBreedingServiceTypeId(Integer breedingServiceTypeId) {
		this.breedingServiceTypeId = breedingServiceTypeId;
	}

	public String getServiceGroupId() {
		return serviceGroupId;
	}

	public void setServiceGroupId(String serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}

	public Integer getSowCondition() {
		return sowCondition;
	}

	public void setSowCondition(Integer sowCondition) {
		this.sowCondition = sowCondition;
	}

	public Integer getPenId() {
		return penId;
	}

	public void setPenId(Integer penId) {
		this.penId = penId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}

}
