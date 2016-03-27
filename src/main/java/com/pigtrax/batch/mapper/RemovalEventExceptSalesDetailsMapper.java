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
	private String revenue;
	private String roomId;
	private String transportTruck;
	private String transportTrailer;
	private String toGroupEventId;
	
	
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
	private Double  deriveRevenue;
	private Integer deriveRoomId;
	private Integer deriveTransportTruck;
	private Integer deriveTransportTrailer;
	private Integer deriveToGroupEventId;
	
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
	public Integer getDeriveCompanyId() {
		return deriveCompanyId;
	}
	public void setDeriveCompanyId(Integer deriveCompanyId) {
		this.deriveCompanyId = deriveCompanyId;
	}	
	
	public Integer getDeriveRemovalEventTypeId() {
		return deriveRemovalEventTypeId;
	}
	@Override
	public String getId() {
		return "["+getPigInfoId()+","+getGroupEventId()+"]";
	}
	public String getRevenue() {
		return revenue;
	}
	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}
	public Double getDeriveRevenue() {
		return deriveRevenue;
	}
	public void setDeriveRevenue(Double deriveRevenue) {
		this.deriveRevenue = deriveRevenue;
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
	public String getTransportTruck() {
		return transportTruck;
	}
	public void setTransportTruck(String transportTruck) {
		this.transportTruck = transportTruck;
	}
	public String getTransportTrailer() {
		return transportTrailer;
	}
	public void setTransportTrailer(String transportTrailer) {
		this.transportTrailer = transportTrailer;
	}
	public Integer getDeriveTransportTruck() {
		return deriveTransportTruck;
	}
	public void setDeriveTransportTruck(Integer deriveTransportTruck) {
		this.deriveTransportTruck = deriveTransportTruck;
	}
	public Integer getDeriveTransportTrailer() {
		return deriveTransportTrailer;
	}
	public void setDeriveTransportTrailer(Integer deriveTransportTrailer) {
		this.deriveTransportTrailer = deriveTransportTrailer;
	}
	public String getToGroupEventId() {
		return toGroupEventId;
	}
	public void setToGroupEventId(String toGroupEventId) {
		this.toGroupEventId = toGroupEventId;
	}
	public Integer getDeriveToGroupEventId() {
		return deriveToGroupEventId;
	}
	public void setDeriveToGroupEventId(Integer deriveToGroupEventId) {
		this.deriveToGroupEventId = deriveToGroupEventId;
	}
	
	
	
}
