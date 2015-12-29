package com.pigtrax.batch.beans;

import java.io.Serializable;
import java.util.Date;

public class FeedEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer rationId;
	private Double feedQuantityKGs;
	private Double feedCost;
	private String feedMadication;
	private String ticketNumber;
	private String feedContentId;
	private Date intialFeedEntryDate;
	private Integer transPortJourneyId;
	private String userUpdated;
	private Integer premiseId;
	
	public String getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}

	public Integer getRationId() {
		return rationId;
	}

	public void setRationId(Integer rationId) {
		this.rationId = rationId;
	}

	public Double getFeedQuantityKGs() {
		return feedQuantityKGs;
	}

	public void setFeedQuantityKGs(Double feedQuantityKGs) {
		this.feedQuantityKGs = feedQuantityKGs;
	}

	public Double getFeedCost() {
		return feedCost;
	}

	public void setFeedCost(Double feedCost) {
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

	public Date getIntialFeedEntryDate() {
		return intialFeedEntryDate;
	}

	public void setIntialFeedEntryDate(Date intialFeedEntryDate) {
		this.intialFeedEntryDate = intialFeedEntryDate;
	}

	public Integer getTransPortJourneyId() {
		return transPortJourneyId;
	}

	public void setTransPortJourneyId(Integer transPortJourneyId) {
		this.transPortJourneyId = transPortJourneyId;
	}
		
	public Integer getPremiseId() {
		return premiseId;
	}

	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("FeedEvent  [");
		buffer.append("rationId : " + this.rationId);
		buffer.append(", " + "feedQuantityKGs : " + this.feedQuantityKGs);
		buffer.append(", " + "feedCost : " + this.feedCost);
		buffer.append(", " + "feedMadication : " + this.feedMadication);
		buffer.append(", " + "ticketNumber : " + this.ticketNumber);
		buffer.append(", " + "feedContentId : " + this.feedContentId);
		buffer.append(", " + "intialFeedEntryDate : " + this.intialFeedEntryDate);
		buffer.append(", " + "transPortJourneyId : " + this.transPortJourneyId);
		buffer.append(" ] ");
		return buffer.toString();
	}
}
