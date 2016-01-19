package com.pigtrax.batch.drivable;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.beans.FarrowEvent;
import com.pigtrax.batch.config.PigletStatusEventType;
import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.FarrowEventDao;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.PenDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.PremisesDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.PigletStatusInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;

@Component
public class PigletStatusInfoDerivable implements Derivable {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private PigInfoDao pigInfoDao;
	
	@Autowired
	FarrowEventDao farrowEventDao;
	
	@Autowired
	GroupEventDao groupEventDao;
	
	@Autowired
	PremisesDao premisesDao;
	
	@Autowired
	PenDao penDao;
	

	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				PigletStatusInfoMapper pigletStatusInfoMapper = (PigletStatusInfoMapper) mapper;
				setCompanyId(pigletStatusInfoMapper); 
				setPremiseId(pigletStatusInfoMapper);
				setPigInfoId(pigletStatusInfoMapper);
				setDerivePigletStatusType(pigletStatusInfoMapper);
				setSowCondition(pigletStatusInfoMapper);
				setDerivePigNum(pigletStatusInfoMapper);
				setDerivePigWt(pigletStatusInfoMapper);
				setDeriveEventDate(pigletStatusInfoMapper);
				setDeriveGroupEventId(pigletStatusInfoMapper);				
				setDeriveTransferToPigInfoId(pigletStatusInfoMapper);				
				setDeriveMortalityReasonId(pigletStatusInfoMapper);
				setDeriveFosterFarrowEventId(pigletStatusInfoMapper);				
				setDeriveFarrowEventId(pigletStatusInfoMapper);
				setDerivePenId(pigletStatusInfoMapper);
			}
		}
	}

	
	private void setPremiseId(final PigletStatusInfoMapper pigletStatusInfoMapper) {
		try {
			pigletStatusInfoMapper.setDerivePremiseId(premisesDao.getPremisesPK(pigletStatusInfoMapper.getFarmName(), pigletStatusInfoMapper.getDeriveCompanyId())); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void setDerivePigletStatusType(final PigletStatusInfoMapper pigletStatusInfoMapper) {
		if(pigletStatusInfoMapper.getEventType() != null)
		{
		  if("Wean".equalsIgnoreCase(pigletStatusInfoMapper.getEventType().trim()))
		  	pigletStatusInfoMapper.setDerivePigletStatusEventTypeId(PigletStatusEventType.Wean.getTypeCode());
		  else if("Transfer".equalsIgnoreCase(pigletStatusInfoMapper.getEventType().trim()))
			  	pigletStatusInfoMapper.setDerivePigletStatusEventTypeId(PigletStatusEventType.FosterOut.getTypeCode());
		  else if("Piglet Mortality".equalsIgnoreCase(pigletStatusInfoMapper.getEventType().trim()))
			  	pigletStatusInfoMapper.setDerivePigletStatusEventTypeId(PigletStatusEventType.Death.getTypeCode());
		}		
	}
	
	
	private void setCompanyId(final PigletStatusInfoMapper pigletStatusInfoMapper) {
		try {
			pigletStatusInfoMapper.setDeriveCompanyId(companyDao.getCompanyId(pigletStatusInfoMapper.getCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setPigInfoId(final PigletStatusInfoMapper pigletStatusInfoMapper) {
		try {
			pigletStatusInfoMapper.setDerivePigInfoId(pigInfoDao.getPigInfoId(pigletStatusInfoMapper.getPigId(), pigletStatusInfoMapper.getDeriveCompanyId(), pigletStatusInfoMapper.getDerivePremiseId()));  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void setSowCondition(final PigletStatusInfoMapper pigletStatusInfoMapper) {
		try {
			pigletStatusInfoMapper.setDeriveSowCondition(Integer.parseInt(pigletStatusInfoMapper.getSowCondition()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDeriveFarrowDate(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try {
			pigletStatusInfoMapper.setDeriveFarrowDate(DateUtil.getDateFromString(pigletStatusInfoMapper.getFarrowDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDeriveFarrowEventId(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		if(pigletStatusInfoMapper.getDerivePigInfoId() != null)
		{
		 List<FarrowEvent> farrowEvents = farrowEventDao.getFarrowEvents(pigletStatusInfoMapper.getDerivePigInfoId());	
		 if(farrowEvents != null && 0 < farrowEvents.size())
		 {
			 for(FarrowEvent farrowEvent :  farrowEvents)
			 {
				 DateTime farrowDate = new DateTime(farrowEvent.getFarrowDateTime());
				 if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.Wean.getTypeCode() && 
						 pigletStatusInfoMapper.getDerivePigNum() != null && pigletStatusInfoMapper.getDerivePigNum() > 0)
				 {
					 DateTime weanDate = new DateTime(pigletStatusInfoMapper.getDeriveEventDate());
					 int duration = Days.daysBetween(farrowDate, weanDate).getDays();
					 if(duration >= 0 && duration <= 60)
					 {
						 pigletStatusInfoMapper.setDeriveFarrowEventId(farrowEvent.getId());
						 pigletStatusInfoMapper.setWeanType(true);
					 }
					 else
					 {
						 pigletStatusInfoMapper.setWeanType(false);
					 }
				 }
				 if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.FosterOut.getTypeCode() && 
						 pigletStatusInfoMapper.getDerivePigNum() != null && pigletStatusInfoMapper.getDerivePigNum() > 0)
				 {
					 DateTime transferDate = new DateTime(pigletStatusInfoMapper.getDeriveEventDate());
					 int duration = Days.daysBetween(farrowDate, transferDate).getDays();
					 if(duration >= 0 && duration <= 50 )
					 {
						 pigletStatusInfoMapper.setDeriveFarrowEventId(farrowEvent.getId());
						 pigletStatusInfoMapper.setTransferType(true);
					 }
					 else
					 {
						 pigletStatusInfoMapper.setTransferType(false);
					 }
				 }
				 if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.Death.getTypeCode() && 
						 pigletStatusInfoMapper.getDerivePigNum() != null && pigletStatusInfoMapper.getDerivePigNum() > 0)
				 {
					 DateTime deathDate = new DateTime(pigletStatusInfoMapper.getDeriveEventDate());
					 int duration = Days.daysBetween(farrowDate, deathDate).getDays();
					 if(duration >= 0 && duration <= 50 )
					 {
						 pigletStatusInfoMapper.setDeriveFarrowEventId(farrowEvent.getId());
						 pigletStatusInfoMapper.setDeathType(true);
					 }
					 else
					 {
						 pigletStatusInfoMapper.setDeathType(false);
					 }
				 }
			 }
		 }
		}
	}
	
	private void setDerivePigNum(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{		
		try{
			pigletStatusInfoMapper.setDerivePigNum(Integer.parseInt(pigletStatusInfoMapper.getNumberOfPigs()));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void setDerivePigWt(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try{
			pigletStatusInfoMapper.setDerivePigWt(Double.parseDouble(pigletStatusInfoMapper.getWeightOfPigs()));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private void setDeriveEventDate(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try {
			pigletStatusInfoMapper.setDeriveEventDate(DateUtil.getDateFromString(pigletStatusInfoMapper.getEventDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDeriveGroupEventId(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.Wean.getTypeCode())
		{		
			try { 
				if(pigletStatusInfoMapper.getWeanGroupId() != null)
					pigletStatusInfoMapper.setDeriveGroupEventId(groupEventDao.getGroupEventId(pigletStatusInfoMapper.getWeanGroupId(), pigletStatusInfoMapper.getDeriveCompanyId(), pigletStatusInfoMapper.getDerivePremiseId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	
	private void setDeriveTransferToPigInfoId(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.FosterOut.getTypeCode())
		{
			try {
				pigletStatusInfoMapper.setDeriveTransferredPigInfoId(pigInfoDao.getPigInfoId(pigletStatusInfoMapper.getTransferredToPig(), pigletStatusInfoMapper.getDeriveCompanyId()));  
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	private void setDeriveMortalityReasonId(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.Death.getTypeCode())
		{
		
			try {
				Integer mortalityReasonTypeId = RefData.MORTALITYREASONTYPE.getId(pigletStatusInfoMapper.getMortalityReason());
				if (mortalityReasonTypeId > -1) {
					pigletStatusInfoMapper.setDeriveMortalityReasonId(mortalityReasonTypeId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void setDeriveFosterFarrowEventId(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.FosterOut.getTypeCode())
		{
		
			try {
				pigletStatusInfoMapper.setDeriveFosterFarrowEventId(farrowEventDao.getFarrowEventId(pigletStatusInfoMapper.getDeriveTransferredPigInfoId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void setDerivePenId(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		if(pigletStatusInfoMapper.getPen() != null && pigletStatusInfoMapper.getPen().length() > 0 && pigletStatusInfoMapper.getDerivePremiseId() != null)
		{
		
			try {
				pigletStatusInfoMapper.setDerivePenId(penDao.getPenPKId(pigletStatusInfoMapper.getPen(), pigletStatusInfoMapper.getDeriveCompanyId(), pigletStatusInfoMapper.getDerivePremiseId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
