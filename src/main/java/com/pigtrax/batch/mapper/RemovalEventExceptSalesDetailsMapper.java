package com.pigtrax.batch.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class RemovalEventExceptSalesDetailsMapper extends AbstractMapper{
	
	/*"numberOfPigs" smallint NOT NULL,
	"removalDateTime" timestamp NOT NULL,
	"id_PigInfo" integer,
	"id_GroupEvent" integer,
	"weightInKgs" numeric(20,2) NOT NULL,
	"id_RemovalEvent" integer NOT NULL,
	"id_Premise" integer,
	"id_DestPremise" integer,
	"id_TransportJourney" integer,
	"id_MortalityReason" integer,*/
	
	private String numberOfPigs;	
	private String removalDateTime;
	private String pigInfoId;
	private String groupEventId;
	private String weightInKgs;
	private String removalEventTypeId;
	private String premiseId;
	private String destPremiseId;
	private String remarks;
	private String mortalityReasonId;
	private String companyId;
	private String trailerFunction;
	private String journeyStartTime;
	private String journeyEndTime;
	private String transportDestinationId;
	private String transportTruckId;
	private String transportTrailerId;
	
	
/*	private Integer transportJourneyId;
	private TransportJourney transportJourney;
	private Integer companyId;*/
	
	private Integer deriveNumberOfPigs;	
	private Date deriveRemovalDateTime;
	private Integer derivePigInfoId;
	private Integer deriveGroupEventId;
	private Double deriveWeightInKgs;
	private Integer deriveRemovalEventTypeId;
	private Integer derivePremiseId;
	private Integer deriveDestPremiseId;
	private Integer deriveMortalityReasonId;
	private Integer deriveCompanyId;
	private Date deriveJourneyStartTime;
	private Date deriveJourneyEndTime;
	private Integer deriveTransportDestinationId;
	private Integer deriveTransportTruckId;
	private Integer deriveTransportTrailerId;
	
	public String getNumberOfPigs() {
		return numberOfPigs;
	}
	public void setNumberOfPigs(String numberOfPigs) {
		this.numberOfPigs = numberOfPigs;
	}
	public String getRemovalDateTime() {
		return removalDateTime;
	}
	public void setRemovalDateTime(String removalDateTime) {
		this.removalDateTime = removalDateTime;
	}
	public String getPigInfoId() {
		return pigInfoId;
	}
	public void setPigInfoId(String pigInfoId) {
		this.pigInfoId = pigInfoId;
	}
	public String getGroupEventId() {
		return groupEventId;
	}
	public void setGroupEventId(String groupEventId) {
		this.groupEventId = groupEventId;
	}
	public String getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(String weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public String getRemovalEventTypeId() {
		return removalEventTypeId;
	}
	public void setRemovalEventTypeId(String removalEventId) {
		this.removalEventTypeId = removalEventId;
	}
	public String getPremiseId() {
		return premiseId;
	}
	public void setPremiseId(String premiseId) {
		this.premiseId = premiseId;
	}
	public String getDestPremiseId() {
		return destPremiseId;
	}
	public void setDestPremiseId(String destPremiseId) {
		this.destPremiseId = destPremiseId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMortalityReasonId() {
		return mortalityReasonId;
	}
	public void setMortalityReasonId(String mortalityReasonId) {
		this.mortalityReasonId = mortalityReasonId;
	}
	public Integer getDeriveNumberOfPigs() {
		return deriveNumberOfPigs;
	}
	public void setDeriveNumberOfPigs(Integer deriveNumberOfPigs) {
		this.deriveNumberOfPigs = deriveNumberOfPigs;
	}
	public Date getDeriveRemovalDateTime() {
		return deriveRemovalDateTime;
	}
	public void setDeriveRemovalDateTime(Date deriveRemovalDateTime) {
		this.deriveRemovalDateTime = deriveRemovalDateTime;
	}
	public Integer getDerivePigInfoId() {
		return derivePigInfoId;
	}
	public void setDerivePigInfoId(Integer derivePigInfoId) {
		this.derivePigInfoId = derivePigInfoId;
	}
	public Integer getDeriveGroupEventId() {
		return deriveGroupEventId;
	}
	public void setDeriveGroupEventId(Integer deriveGroupEventId) {
		this.deriveGroupEventId = deriveGroupEventId;
	}
	public Double getDeriveWeightInKgs() {
		return deriveWeightInKgs;
	}
	public void setDeriveWeightInKgs(Double deriveWeightInKgs) {
		this.deriveWeightInKgs = deriveWeightInKgs;
	}
	public Integer getDeriveRemovalTypeEventId() {
		return deriveRemovalEventTypeId;
	}
	public void setDeriveRemovalEventTypeId(Integer deriveRemovalEventTypeId) {
		this.deriveRemovalEventTypeId = deriveRemovalEventTypeId;
	}
	public Integer getDerivePremiseId() {
		return derivePremiseId;
	}
	public void setDerivePremiseId(Integer derivePremiseId) {
		this.derivePremiseId = derivePremiseId;
	}
	public Integer getDeriveDestPremiseId() {
		return deriveDestPremiseId;
	}
	public void setDeriveDestPremiseId(Integer deriveDestPremiseId) {
		this.deriveDestPremiseId = deriveDestPremiseId;
	}
	public Integer getDeriveMortalityReasonId() {
		return deriveMortalityReasonId;
	}
	public void setDeriveMortalityReasonId(Integer deriveMortalityReasonId) {
		this.deriveMortalityReasonId = deriveMortalityReasonId;
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
	
	public String getTrailerFunction() {
		return trailerFunction;
	}
	public void setTrailerFunction(String trailerFunction) {
		this.trailerFunction = trailerFunction;
	}
	public String getJourneyStartTime() {
		return journeyStartTime;
	}
	public void setJourneyStartTime(String journeyStartTime) {
		this.journeyStartTime = journeyStartTime;
	}
	public String getJourneyEndTime() {
		return journeyEndTime;
	}
	public void setJourneyEndTime(String journeyEndTime) {
		this.journeyEndTime = journeyEndTime;
	}
	public String getTransportDestinationId() {
		return transportDestinationId;
	}
	public void setTransportDestinationId(String transportDestinationId) {
		this.transportDestinationId = transportDestinationId;
	}
	public String getTransportTruckId() {
		return transportTruckId;
	}
	public void setTransportTruckId(String transportTruckId) {
		this.transportTruckId = transportTruckId;
	}
	public String getTransportTrailerId() {
		return transportTrailerId;
	}
	public void setTransportTrailerId(String transportTrailerId) {
		this.transportTrailerId = transportTrailerId;
	}
	public Date getDeriveJourneyStartTime() {
		return deriveJourneyStartTime;
	}
	public void setDeriveJourneyStartTime(Date deriveJourneyStartTime) {
		this.deriveJourneyStartTime = deriveJourneyStartTime;
	}
	public Date getDeriveJourneyEndTime() {
		return deriveJourneyEndTime;
	}
	public void setDeriveJourneyEndTime(Date deriveJourneyEndTime) {
		this.deriveJourneyEndTime = deriveJourneyEndTime;
	}
	public Integer getDeriveTransportDestinationId() {
		return deriveTransportDestinationId;
	}
	public void setDeriveTransportDestinationId(Integer deriveTransportDestinationId) {
		this.deriveTransportDestinationId = deriveTransportDestinationId;
	}
	public Integer getDeriveTransportTruckId() {
		return deriveTransportTruckId;
	}
	public void setDeriveTransportTruckId(Integer deriveTransportTruckId) {
		this.deriveTransportTruckId = deriveTransportTruckId;
	}
	public Integer getDeriveTransportTrailerId() {
		return deriveTransportTrailerId;
	}
	public void setDeriveTransportTrailerId(Integer deriveTransportTrailerId) {
		this.deriveTransportTrailerId = deriveTransportTrailerId;
	}
	public Integer getDeriveRemovalEventTypeId() {
		return deriveRemovalEventTypeId;
	}
	@Override
	public String getId() {
		if (groupEventId == null) {
			return UUID.randomUUID().toString().replaceAll("-", "");
		} else {
			return groupEventId;
		}
	}
}
