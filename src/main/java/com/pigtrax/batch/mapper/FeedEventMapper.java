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
	private String rationId;
	private String feedQuantityKGs;
	private String feedCost;
	private String feedMadication;
	private String ticketNumber;
	private String feedContentId;
	private String feedEntryDate;
	private String transPortJourneyId;
	
	private Double deriveFeedQuantityKGs;
	private Double deriveFeedCost;
	private Date deriveIntialFeedEntryDate;
	private Integer deriveTransPortJourneyId;
	private Integer deriveRationId;
	
	
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
	public Integer getDeriveTransPortJourneyId() {
		return deriveTransPortJourneyId;
	}
	public void setDeriveTransPortJourneyId(Integer deriveTransPortJourneyId) {
		this.deriveTransPortJourneyId = deriveTransPortJourneyId;
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
	public String getTransPortJourneyId() {
		return transPortJourneyId;
	}
	public void setTransPortJourneyId(String transPortJourneyId) {
		this.transPortJourneyId = transPortJourneyId;
	}
	public String getFeedEntryDate() {
		return feedEntryDate;
	}
	public void setFeedEntryDate(String feedEntryDate) {
		this.feedEntryDate = feedEntryDate;
	}
	
	

}
