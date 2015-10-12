package com.pigtrax.batch.mapper;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class PigInfoMapper extends AbstractMapper {
	private String pigId;
	private String sireId;
	private String damId;
	private String entryDate;
	private String origin;
	private String gline;
	private String gcompany;
	private String birthDate;
	private String tattoo;
	private String alternateTattoo;
	private String remarks;
	private String companyId;
	private String penId;
	private String barnId;
	private String sexTypeId;
	private String parity;
	private String gfunctionTypeId;

	public String getPigId() {
		return pigId;
	}

	public void setPigId(String pigId) {
		this.pigId = pigId;
	}

	public String getSireId() {
		return sireId;
	}

	public void setSireId(String sireId) {
		this.sireId = sireId;
	}

	public String getDamId() {
		return damId;
	}

	public void setDamId(String damId) {
		this.damId = damId;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getGline() {
		return gline;
	}

	public void setGline(String gline) {
		this.gline = gline;
	}

	public String getGcompany() {
		return gcompany;
	}

	public void setGcompany(String gcompany) {
		this.gcompany = gcompany;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPenId() {
		return penId;
	}

	public void setPenId(String penId) {
		this.penId = penId;
	}

	public String getBarnId() {
		return barnId;
	}

	public void setBarnId(String barnId) {
		this.barnId = barnId;
	}

	public String getSexTypeId() {
		return sexTypeId;
	}

	public void setSexTypeId(String sexTypeId) {
		this.sexTypeId = sexTypeId;
	}

	public String getParity() {
		return parity;
	}

	public void setParity(String parity) {
		this.parity = parity;
	}

	public String getGfunctionTypeId() {
		return gfunctionTypeId;
	}

	public void setGfunctionTypeId(String gfunctionTypeId) {
		this.gfunctionTypeId = gfunctionTypeId;
	}

	@Override
	public String getId() {
		return getPigId();
	}

}
