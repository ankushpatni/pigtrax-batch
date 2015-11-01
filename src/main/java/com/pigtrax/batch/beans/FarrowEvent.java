package com.pigtrax.batch.beans;

import java.io.Serializable;
import java.util.Date;

public class FarrowEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date farrowDateTime;
	private Integer penId;
	private Integer liveBorns;
	private Integer stillBorns;
	private Integer maleBorns;
	private Integer femaleBorns;
	private Integer mummies;
	private Integer weightInKGs;
	private Boolean inducedBirth;
	private Boolean assistedBirth;
	private String remarks;
	private Integer sowCondition;
	private Integer employeeGrpId;
	private Integer pigInfoId;
	private Integer pragnancyEventId;
	private Integer teasts;
	private Integer litterId;
	private Integer companyId;
	private String userUpdated;

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFarrowDateTime() {
		return farrowDateTime;
	}

	public void setFarrowDateTime(Date farrowDateTime) {
		this.farrowDateTime = farrowDateTime;
	}

	public Integer getPenId() {
		return penId;
	}

	public void setPenId(Integer penId) {
		this.penId = penId;
	}

	public Integer getLiveBorns() {
		return liveBorns;
	}

	public void setLiveBorns(Integer liveBorns) {
		this.liveBorns = liveBorns;
	}

	public Integer getStillBorns() {
		return stillBorns;
	}

	public void setStillBorns(Integer stillBorns) {
		this.stillBorns = stillBorns;
	}

	public Integer getMaleBorns() {
		return maleBorns;
	}

	public void setMaleBorns(Integer maleBorns) {
		this.maleBorns = maleBorns;
	}

	public Integer getFemaleBorns() {
		return femaleBorns;
	}

	public void setFemaleBorns(Integer femaleBorns) {
		this.femaleBorns = femaleBorns;
	}

	public Integer getMummies() {
		return mummies;
	}

	public void setMummies(Integer mummies) {
		this.mummies = mummies;
	}

	public Integer getWeightInKGs() {
		return weightInKGs;
	}

	public void setWeightInKGs(Integer weightInKGs) {
		this.weightInKGs = weightInKGs;
	}

	public Boolean getInducedBirth() {
		return inducedBirth;
	}

	public void setInducedBirth(Boolean inducedBirth) {
		this.inducedBirth = inducedBirth;
	}

	public Boolean getAssistedBirth() {
		return assistedBirth;
	}

	public void setAssistedBirth(Boolean assistedBirth) {
		this.assistedBirth = assistedBirth;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getSowCondition() {
		return sowCondition;
	}

	public void setSowCondition(Integer sowCondition) {
		this.sowCondition = sowCondition;
	}

	public Integer getEmployeeGrpId() {
		return employeeGrpId;
	}

	public void setEmployeeGrpId(Integer employeeGrpId) {
		this.employeeGrpId = employeeGrpId;
	}

	public Integer getPigInfoId() {
		return pigInfoId;
	}

	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}

	public Integer getPragnancyEventId() {
		return pragnancyEventId;
	}

	public void setPragnancyEventId(Integer pragnancyEventId) {
		this.pragnancyEventId = pragnancyEventId;
	}

	public Integer getTeasts() {
		return teasts;
	}

	public void setTeasts(Integer teasts) {
		this.teasts = teasts;
	}

	public Integer getLitterId() {
		return litterId;
	}

	public void setLitterId(Integer litterId) {
		this.litterId = litterId;
	}

	public String getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("FarrowEvent  [");
		buffer.append("id : " + this.id);
		buffer.append(", " + "farrowDateTime : " + this.farrowDateTime);
		buffer.append(", " + "penId : " + this.penId);
		buffer.append(", " + "liveBorns : " + this.liveBorns);
		buffer.append(", " + "stillBorns : " + this.stillBorns);
		buffer.append(", " + "maleBorns : " + this.maleBorns);
		buffer.append(", " + "femaleBorns : " + this.femaleBorns);
		buffer.append(", " + "mummies : " + this.mummies);
		buffer.append(", " + "weightInKGs : " + this.weightInKGs);
		buffer.append(", " + "inducedBirth : " + this.inducedBirth);
		buffer.append(", " + "assistedBirth : " + this.assistedBirth);
		buffer.append(", " + "remarks : " + this.remarks);
		buffer.append(", " + "sowCondition : " + this.sowCondition);
		buffer.append(", " + "employeeGrpId : " + this.employeeGrpId);
		buffer.append(", " + "pigInfoId : " + this.pigInfoId);
		buffer.append(", " + "pragnancyEventId" + this.pragnancyEventId);
		buffer.append(", " + "teasts" + this.teasts);
		buffer.append(", " + "litterId" + this.litterId);
		buffer.append(" ] ");
		return buffer.toString();
	}

}
