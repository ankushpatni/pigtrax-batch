package com.pigtrax.batch.mapper;

import java.math.BigDecimal;
import java.util.Date;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class SalesEventDetailsMapper extends AbstractMapper  {
	
	private String invoiceId;
	private String ticketNumber;
	private String numberOfPigs;//required//done
	private String revenueUsd;
	private String weightInKgs;//required//done
	private String salesDateTime;//done
	private String pigInfoId;//done
	private String groupEventId;//done
	private String soldTo;
	private String removalEventId;//done
	private String companyId;//done
	private String remarks;
	
	/*"invoiceId" varchar(20),
	"ticketNumber" varchar(20),
	"numberOfPigs" smallint NOT NULL,
	"revenueUsd" numeric(20,2),
	"weightInKgs" numeric(20,2) NOT NULL,
	"salesDateTime" timestamp NOT NULL,
	"id_PigInfo" integer,
	"id_GroupEvent" integer,
	"soldTo" varchar(75),
	"id_RemovalEvent" integer,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_TransportJourney" integer,
	remarks varchar(255),
	*/
	/////////////
	
	private Integer deriveNumberOfPigs;	
	private BigDecimal deriveRevenueUsd;
	private Date deriveSalesDateTime;
	private Integer derivePigInfoId;
	private Integer deriveGroupEventId;
	private Double deriveWeightInKgs;
	private Integer deriveRemovalEventTypeId;
	private Integer deriveCompanyId;
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getNumberOfPigs() {
		return numberOfPigs;
	}
	public void setNumberOfPigs(String numberOfPigs) {
		this.numberOfPigs = numberOfPigs;
	}
	public String getRevenueUsd() {
		return revenueUsd;
	}
	public void setRevenueUsd(String revenueUsd) {
		this.revenueUsd = revenueUsd;
	}
	public String getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(String weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public String getSalesDateTime() {
		return salesDateTime;
	}
	public void setSalesDateTime(String salesDateTime) {
		this.salesDateTime = salesDateTime;
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
	public String getSoldTo() {
		return soldTo;
	}
	public void setSoldTo(String soldTo) {
		this.soldTo = soldTo;
	}
	public String getRemovalEventId() {
		return removalEventId;
	}
	public void setRemovalEventId(String removalEventId) {
		this.removalEventId = removalEventId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getDeriveNumberOfPigs() {
		return deriveNumberOfPigs;
	}
	public void setDeriveNumberOfPigs(Integer deriveNumberOfPigs) {
		this.deriveNumberOfPigs = deriveNumberOfPigs;
	}
	public BigDecimal getDeriveRevenueUsd() {
		return deriveRevenueUsd;
	}
	public void setDeriveRevenueUsd(BigDecimal deriveRevenueUsd) {
		this.deriveRevenueUsd = deriveRevenueUsd;
	}
	public Date getDeriveSalesDateTime() {
		return deriveSalesDateTime;
	}
	public void setDeriveSalesDateTime(Date deriveSalesDateTime) {
		this.deriveSalesDateTime = deriveSalesDateTime;
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
	public Integer getDeriveRemovalEventTypeId() {
		return deriveRemovalEventTypeId;
	}
	public void setDeriveRemovalEventTypeId(Integer deriveRemovalEventTypeId) {
		this.deriveRemovalEventTypeId = deriveRemovalEventTypeId;
	}
	public Integer getDeriveCompanyId() {
		return deriveCompanyId;
	}
	public void setDeriveCompanyId(Integer deriveCompanyId) {
		this.deriveCompanyId = deriveCompanyId;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
