package com.pigtrax.batch.mapper;

import java.util.Date;
import java.util.UUID;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class FeedEventMapper  extends AbstractMapper	{

	@Override
	public String getId() {
		if (getTicketNumber() == null) {
			return UUID.randomUUID().toString().replaceAll("-", "");
		} else {
			return getTicketNumber();
		}
	}
	private String farmName;	
	private String rationId;
	private String feedQuantityKGs;
	private String feedCost;
	private String feedMadication;
	private String ticketNumber; 
	private String feedContentId;
	private String feedEntryDate;
	private String transportTruck;
	private String transportTrailer;
	private String companyId;
	
	private Double deriveFeedQuantityKGs;
	private Double deriveFeedCost;
	private Date deriveIntialFeedEntryDate;
	private Integer derivePremiseId;
	private Integer deriveRationId;
	private Integer deriveTransportTruck;
	private Integer deriveTransportTrailer;
	private Integer deriveCompanyId;
	
	public Integer getDeriveRationId() {
		return deriveRationId;
	}
	public void setDeriveRationId(Integer deriveRationId) {
		this.deriveRationId = deriveRationId;
	}
	public Double getDeriveFeedQuantityKGs() {
		return deriveFeedQuantityKGs;
	}
	public void setDeriveFeedQuantityKGs(Double deriveFeedQuantityKGs) {
		this.deriveFeedQuantityKGs = deriveFeedQuantityKGs;
	}
	public Double getDeriveFeedCost() {
		return deriveFeedCost;
	}
	public void setDeriveFeedCost(Double deriveFeedCost) {
		this.deriveFeedCost = deriveFeedCost;
	}
	public Date getDeriveIntialFeedEntryDate() {
		return deriveIntialFeedEntryDate;
	}
	public void setDeriveIntialFeedEntryDate(Date deriveIntialFeedEntryDate) {
		this.deriveIntialFeedEntryDate = deriveIntialFeedEntryDate;
	}
	
	public String getRationId() {
		return rationId;
	}
	public void setRationId(String rationId) {
		this.rationId = rationId;
	}
	public String getFeedQuantityKGs() {
		return feedQuantityKGs;
	}
	public void setFeedQuantityKGs(String feedQuantityKGs) {
		this.feedQuantityKGs = feedQuantityKGs;
	}
	public String getFeedCost() {
		return feedCost;
	}
	public void setFeedCost(String feedCost) {
		this.feedCost = feedCost;
	}
	public String getFeedMadication() {
		return feedMadication;
	}
	public void setFeedMadication(String feedMadication) {
		this.feedMadication = feedMadication;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getFeedContentId() {
		return feedContentId;
	}
	public void setFeedContentId(String feedContentId) {
		this.feedContentId = feedContentId;
	}
	
	public String getFeedEntryDate() {
		return feedEntryDate;
	}
	public void setFeedEntryDate(String feedEntryDate) {
		this.feedEntryDate = feedEntryDate;
	}
	public String getFarmName() {
		return farmName;
	}
	public void setFarmName(String farmName) {
		this.farmName = farmName;
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
	public Integer getDerivePremiseId() {
		return derivePremiseId;
	}
	public void setDerivePremiseId(Integer derivePremiseId) {
		this.derivePremiseId = derivePremiseId;
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
	
	public boolean isEmpty()
	{	
		if((this.ticketNumber==null || this.ticketNumber.trim().length() == 0)
				&& (this.rationId==null || this.rationId.trim().length() == 0)
				&& (this.feedMadication == null || this.feedMadication.trim().length() == 0)
				&& (this.transportTruck == null || this.transportTruck.trim().length() == 0)
				&& (this.transportTrailer == null || this.transportTrailer.trim().length() == 0)
				)
			return true;
		else
			return false;
	}	

}
