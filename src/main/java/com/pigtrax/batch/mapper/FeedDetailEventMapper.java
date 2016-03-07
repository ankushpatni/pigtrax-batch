package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class FeedDetailEventMapper  extends AbstractMapper	{

	@Override
	public String getId() {
		if (getTicketNumber() == null) {
			return UUID.randomUUID().toString().replaceAll("-", "");
		} else {
			return getTicketNumber();
		}
	}
	private String farmName;	
	private String ticketNumber;
	private String companyId;
	private String groupEventId;
	private String feedEventDate;
	private String feedMill;
	private String feedEventType;
	private String weightInKgs;
	private String silo;
	private String remarks;
	private String feedCost;
	
	private String rationId;
	private String feedMadication;
	private String transportTruck;
	private String transportTrailer;
	
	
	private Integer derivePremiseId;
	private Integer deriveCompanyId;
	private Integer deriveGroupEventId;
	private Date deriveFeedEventDate;
	private Integer deriveFeedEventType;
	private Double deriveWeightInKgs;
	private Integer deriveSilo;
	private Integer deriveFeedEventId;
	private Double deriveFeedCost;
	
	private Integer deriveRationId;
	private Integer deriveTransportTruck;
	private Integer deriveTransportTrailer;
	
	private boolean isUpdate;
	
	public String getFarmName() {
		return farmName;
	}
	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getGroupEventId() {
		return groupEventId;
	}
	public void setGroupEventId(String groupEventId) {
		this.groupEventId = groupEventId;
	}
	public String getFeedEventDate() {
		return feedEventDate;
	}
	public void setFeedEventDate(String feedEventDate) {
		this.feedEventDate = feedEventDate;
	}
	public String getFeedMill() {
		return feedMill;
	}
	public void setFeedMill(String feedMill) {
		this.feedMill = feedMill;
	}
	public String getFeedEventType() {
		return feedEventType;
	}
	public void setFeedEventType(String feedEventType) {
		this.feedEventType = feedEventType;
	}
	public String getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(String weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public String getSilo() {
		return silo;
	}
	public void setSilo(String silo) {
		this.silo = silo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getDerivePremiseId() {
		return derivePremiseId;
	}
	public void setDerivePremiseId(Integer derivePremiseId) {
		this.derivePremiseId = derivePremiseId;
	}
	public Integer getDeriveCompanyId() {
		return deriveCompanyId;
	}
	public void setDeriveCompanyId(Integer deriveCompanyId) {
		this.deriveCompanyId = deriveCompanyId;
	}
	public Integer getDeriveGroupEventId() {
		return deriveGroupEventId;
	}
	public void setDeriveGroupEventId(Integer deriveGroupEventId) {
		this.deriveGroupEventId = deriveGroupEventId;
	}
	public Date getDeriveFeedEventDate() {
		return deriveFeedEventDate;
	}
	public void setDeriveFeedEventDate(Date deriveFeedEventDate) {
		this.deriveFeedEventDate = deriveFeedEventDate;
	}
	public Integer getDeriveFeedEventType() {
		return deriveFeedEventType;
	}
	public void setDeriveFeedEventType(Integer deriveFeedEventType) {
		this.deriveFeedEventType = deriveFeedEventType;
	}
	public Double getDeriveWeightInKgs() {
		return deriveWeightInKgs;
	}
	public void setDeriveWeightInKgs(Double deriveWeightInKgs) {
		this.deriveWeightInKgs = deriveWeightInKgs;
	}
	public Integer getDeriveSilo() {
		return deriveSilo;
	}
	public void setDeriveSilo(Integer deriveSilo) {
		this.deriveSilo = deriveSilo;
	}
	public Integer getDeriveFeedEventId() {
		return deriveFeedEventId;
	}
	public void setDeriveFeedEventId(Integer deriveFeedEventId) {
		this.deriveFeedEventId = deriveFeedEventId;
	}
	public String getFeedCost() {
		return feedCost;
	}
	public void setFeedCost(String feedCost) {
		this.feedCost = feedCost;
	}
	public Double getDeriveFeedCost() {
		return deriveFeedCost;
	}
	public void setDeriveFeedCost(Double deriveFeedCost) {
		this.deriveFeedCost = deriveFeedCost;
	}
		
	public String getRationId() {
		return rationId;
	}
	public void setRationId(String rationId) {
		this.rationId = rationId;
	}
	public String getFeedMadication() {
		return feedMadication;
	}
	public void setFeedMadication(String feedMadication) {
		this.feedMadication = feedMadication;
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
	public Integer getDeriveRationId() {
		return deriveRationId;
	}
	public void setDeriveRationId(Integer deriveRationId) {
		this.deriveRationId = deriveRationId;
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
	
	
	
	public boolean isUpdate() {
		return isUpdate;
	}
	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	public boolean isEmpty()
	{	
		if((this.ticketNumber==null || this.ticketNumber.trim().length() == 0)
				&& (this.groupEventId==null || this.groupEventId.trim().length() == 0)
				&& (this.feedEventDate == null || this.feedEventDate.trim().length() == 0)
				&& (this.feedMill == null || this.feedMill.trim().length() == 0)
				&& (this.feedEventType == null || this.feedEventType.trim().length() == 0)
				&& (this.weightInKgs == null || this.weightInKgs.trim().length() == 0)
				&& (this.feedCost == null || this.feedCost.trim().length() == 0)
				&& (this.silo == null || this.silo.trim().length() == 0)
				&& (this.remarks == null || this.remarks.trim().length() == 0)
				)
			return true;
		else
			return false;
	}	
	
}
