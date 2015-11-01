package com.pigtrax.batch.beans;

import java.util.Date;

public class PigInfo {
	private Integer id;
	private String pigId;
	private String damId;
	private String sireId;
	private Date entryDate;
	private String origin;
	private Integer gline;
	private Integer gcompany;
	private Date birthDate;
	private String tattoo;
	private String alternateTattoo;
	private String remarks;
	private Integer companyId;
	private Integer penId;
	private Integer barnId;
	private Integer sexTypeId;
	private String userUpdated;
	private Date currentFarrowEventDate;
	private Integer farrowId;
	private boolean active = true;
	private Integer parity;
	private Integer gfunctionTypeId;

	public Integer getFarrowId() {
		return farrowId;
	}

	public void setFarrowId(Integer farrowId) {
		this.farrowId = farrowId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPigId() {
		return pigId;
	}

	public void setPigId(String pigId) {
		this.pigId = pigId;
	}

	public String getDamId() {
		return damId;
	}

	public void setDamId(String damId) {
		this.damId = damId;
	}

	public String getSireId() {
		return sireId;
	}

	public void setSireId(String sireId) {
		this.sireId = sireId;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Integer getGline() {
		return gline;
	}

	public void setGline(Integer gline) {
		this.gline = gline;
	}

	public Integer getGcompany() {
		return gcompany;
	}

	public void setGcompany(Integer gcompany) {
		this.gcompany = gcompany;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getTattoo() {
		return tattoo;
	}

	public void setTattoo(String tattoo) {
		this.tattoo = tattoo;
	}

	public String getAlternateTattoo() {
		return alternateTattoo;
	}

	public void setAlternateTattoo(String alternateTattoo) {
		this.alternateTattoo = alternateTattoo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getPenId() {
		return penId;
	}

	public void setPenId(Integer penId) {
		this.penId = penId;
	}

	public Integer getBarnId() {
		return barnId;
	}

	public void setBarnId(Integer barnId) {
		this.barnId = barnId;
	}

	public Integer getSexTypeId() {
		return sexTypeId;
	}

	public void setSexTypeId(Integer sexTypeId) {
		this.sexTypeId = sexTypeId;
	}

	public String getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}

	public Date getCurrentFarrowEventDate() {
		return currentFarrowEventDate;
	}

	public void setCurrentFarrowEventDate(Date currentFarrowEventDate) {
		this.currentFarrowEventDate = currentFarrowEventDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getParity() {
		return parity;
	}

	public void setParity(Integer parity) {
		this.parity = parity;
	}

	public Integer getGfunctionTypeId() {
		return gfunctionTypeId;
	}

	public void setGfunctionTypeId(Integer gfunctionTypeId) {
		this.gfunctionTypeId = gfunctionTypeId;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("PigInfo [");
		buffer.append("id : " + this.id);
		buffer.append(", " + "pigId : " + this.pigId);
		buffer.append(", " + "sireId : " + this.sireId);
		buffer.append(", " + "damId : " + this.damId);
		buffer.append(", " + "entryDate : " + this.entryDate);
		buffer.append(", " + "origin : " + this.origin);
		buffer.append(", " + "gline : " + this.gline);
		buffer.append(", " + "gcompany : " + this.gcompany);
		buffer.append(", " + "birthDate : " + this.birthDate);
		buffer.append(", " + "tattoo : " + this.tattoo);
		buffer.append(", " + "alternateTattoo : " + this.alternateTattoo);
		buffer.append(", " + "remarks : " + this.remarks);
		buffer.append(", " + "companyId : " + this.companyId);
		buffer.append(", " + "penId : " + this.penId);
		buffer.append(", " + "barnId : " + this.barnId);
		buffer.append(", " + "sexTypeId" + this.sexTypeId);
		buffer.append(", " + "currentFarrowEventDate" + this.currentFarrowEventDate);
		buffer.append(", " + "active" + this.active);
		buffer.append(" ] ");
		return buffer.toString();
	}

}
