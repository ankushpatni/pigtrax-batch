package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class PigInfoMapper extends AbstractMapper {
	private String pigId;
	private String sireId;
	private String damId;
	private String entryDate;
	private String geneticOrigin;
	private String geneticLine;
	private String geneticCompany;
	private String birthDate;
	private String tattoo;
	private String alternateTattoo;
	private String remarks;
	private String companyId;
	private String roomId;
	private String barnId;
	private String sexTypeId;
	private String parity;
	private String geneticFunction;
	private String farmName;

	// Derivable properties
	private Integer deriveFfunctionTypeId;
	private Integer deriveSexId;
	private Integer deriveRoomId;
	private Integer deriveBarnId;
	private Integer deriveParity;
	private Integer deriveCompanyId;
	private Date deriveBirthDate;
	private Date deriveEntryDate;
	private Date deriveFarrowEventDate;
	private Integer deriveGline;
	private Integer deriveGCompany;
	private Integer deriveOriginId;
	private Integer derivePremiseId;
	
	public Integer getDeriveGline() {
		return deriveGline;
	}

	public void setDeriveGline(Integer deriveGline) {
		this.deriveGline = deriveGline;
	}

	public Integer getDeriveGCompany() {
		return deriveGCompany;
	}

	public void setDeriveGCompany(Integer deriveGCompany) {
		this.deriveGCompany = deriveGCompany;
	}

	public Integer getDeriveFfunctionTypeId() {
		return deriveFfunctionTypeId;
	}

	public void setDeriveFfunctionTypeId(Integer deriveFfunctionTypeId) {
		this.deriveFfunctionTypeId = deriveFfunctionTypeId;
	}

	public Integer getDeriveSexId() {
		return deriveSexId;
	}

	public void setDeriveSexId(Integer deriveSexId) {
		this.deriveSexId = deriveSexId;
	}
	public Integer getDeriveBarnId() {
		return deriveBarnId;
	}

	public void setDeriveBarnId(Integer deriveBarnId) {
		this.deriveBarnId = deriveBarnId;
	}

	public Integer getDeriveParity() {
		return deriveParity;
	}

	public void setDeriveParity(Integer deriveParity) {
		this.deriveParity = deriveParity;
	}

	public Integer getDeriveCompanyId() {
		return deriveCompanyId;
	}

	public void setDeriveCompanyId(Integer deriveCompanyId) {
		this.deriveCompanyId = deriveCompanyId;
	}

	public Date getDeriveBirthDate() {
		return deriveBirthDate;
	}

	public void setDeriveBirthDate(Date deriveBirthDate) {
		this.deriveBirthDate = deriveBirthDate;
	}

	public Date getDeriveEntryDate() {
		return deriveEntryDate;
	}

	public void setDeriveEntryDate(Date deriveEntryDate) {
		this.deriveEntryDate = deriveEntryDate;
	}

	public Date getDeriveFarrowEventDate() {
		return deriveFarrowEventDate;
	}

	public void setDeriveFarrowEventDate(Date deriveFarrowEventDate) {
		this.deriveFarrowEventDate = deriveFarrowEventDate;
	}

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

	
	
	public String getGeneticOrigin() {
		return geneticOrigin;
	}

	public void setGeneticOrigin(String geneticOrigin) {
		this.geneticOrigin = geneticOrigin;
	}

	public String getGeneticLine() {
		return geneticLine;
	}

	public void setGeneticLine(String geneticLine) {
		this.geneticLine = geneticLine;
	}

	public String getGeneticCompany() {
		return geneticCompany;
	}

	public void setGeneticCompany(String geneticCompany) {
		this.geneticCompany = geneticCompany;
	}

	public String getGeneticFunction() {
		return geneticFunction;
	}

	public void setGeneticFunction(String geneticFunction) {
		this.geneticFunction = geneticFunction;
	}

	@Override
	public String getId() {
		if (getPigId() == null) {
			return UUID.randomUUID().toString().replaceAll("-", "");
		} else {
			return getPigId();
		}
	}

	public Integer getDeriveOriginId() {
		return deriveOriginId;
	}

	public void setDeriveOriginId(Integer deriveOriginId) {
		this.deriveOriginId = deriveOriginId;
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

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Integer getDeriveRoomId() {
		return deriveRoomId;
	}

	public void setDeriveRoomId(Integer deriveRoomId) {
		this.deriveRoomId = deriveRoomId;
	}

	public boolean isEmpty()
	{	
		if((this.pigId==null || this.pigId.trim().length() == 0)
				&& (this.roomId==null || this.roomId.trim().length() == 0)
				&& (this.tattoo == null || this.tattoo.trim().length() == 0)
				&& (this.alternateTattoo == null || this.alternateTattoo.trim().length() == 0)
				&& (this.entryDate == null || this.entryDate.trim().length() == 0)
				&& (this.sexTypeId == null || this.sexTypeId.trim().length() == 0)
				&& (this.geneticOrigin == null || this.geneticOrigin.trim().length() == 0)
				&& (this.geneticCompany == null || this.geneticCompany.trim().length() == 0)
				&& (this.geneticLine == null || this.geneticLine.trim().length() == 0)
				&& (this.geneticFunction == null || this.geneticFunction.trim().length() == 0)
				&& (this.birthDate == null || this.birthDate.trim().length() == 0)
				&& (this.sireId == null || this.sireId.trim().length() == 0)
				&& (this.damId == null || this.damId.trim().length() == 0)
				&& (this.remarks == null || this.remarks.trim().length() == 0)
				)
			return true;
		else
			return false;
	}
	
}
