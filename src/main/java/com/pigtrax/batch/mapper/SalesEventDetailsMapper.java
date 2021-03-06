package com.pigtrax.batch.mapper;

import java.math.BigDecimal;
import java.util.Date;

import com.pigtrax.batch.mapper.interfaces.AbstractMapper;

public class SalesEventDetailsMapper extends AbstractMapper  {
	
	private String invoiceNumber;
	private String ticketNumber;
	private String numberOfPigs;//required//done
	private String revenueUsd;
	private String weightInKgs;//required//done
	private String salesDateTime;//done
	private String pigInfoId;//done
	private String groupEventId;//done
	private String soldTo;
	private String companyId;//done
	private String remarks;
	private String salesType;	
	private String removalType;
	private String salesReason;
	private String transportTruck;
	private String transportTrailer;
	
	
	private Integer deriveNumberOfPigs;	
	private BigDecimal deriveRevenueUsd;
	private Date deriveSalesDateTime;
	private Integer derivePigInfoId;
	private Integer deriveGroupEventId;
	private Double deriveWeightInKgs;
	private Integer deriveCompanyId;
	private String deriveSalesTypes;
	private Integer deriveRemovalType;
	private Integer deriveSoldTo;
	private String deriveSalesReason;
	private Integer deriveTransportTruck;
	private Integer deriveTransportTrailer;
	
	
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

	public Integer getDeriveCompanyId() {
		return deriveCompanyId;
	}
	public void setDeriveCompanyId(Integer deriveCompanyId) {
		this.deriveCompanyId = deriveCompanyId;
	}
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getSalesType() {
		return salesType;
	}
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	public String getDeriveSalesTypes() {
		return deriveSalesTypes;
	}
	public void setDeriveSalesTypes(String deriveSalesTypes) {
		this.deriveSalesTypes = deriveSalesTypes;
	}
	@Override
	public String getId() {
		return "["+getPigInfoId()+","+getGroupEventId()+", "+getCompanyId()+"]";
	}
	public String getRemovalType() {
		return removalType;
	}
	public void setRemovalType(String removalType) {
		this.removalType = removalType;
	}
	public Integer getDeriveRemovalType() {
		return deriveRemovalType;
	}
	public void setDeriveRemovalType(Integer deriveRemovalType) {
		this.deriveRemovalType = deriveRemovalType;
	}
	public Integer getDeriveSoldTo() {
		return deriveSoldTo;
	}
	public void setDeriveSoldTo(Integer deriveSoldTo) {
		this.deriveSoldTo = deriveSoldTo;
	}
	public String getSalesReason() {
		return salesReason;
	}
	public void setSalesReason(String salesReason) {
		this.salesReason = salesReason;
	}
	public String getDeriveSalesReason() {
		return deriveSalesReason;
	}
	public void setDeriveSalesReason(String deriveSalesReason) {
		this.deriveSalesReason = deriveSalesReason;
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
	
}
