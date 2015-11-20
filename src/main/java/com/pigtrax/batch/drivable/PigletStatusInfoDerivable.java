package com.pigtrax.batch.drivable;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.beans.FarrowEvent;
import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.FarrowEventDao;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
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
	

	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				PigletStatusInfoMapper pigletStatusInfoMapper = (PigletStatusInfoMapper) mapper;
				setCompanyId(pigletStatusInfoMapper); 
				setPigInfoId(pigletStatusInfoMapper);
				setSowCondition(pigletStatusInfoMapper);
				setDeriveWeanPigNum(pigletStatusInfoMapper);
				setDeriveWeanPigWt(pigletStatusInfoMapper);
				setDeriveWeanDate(pigletStatusInfoMapper);
				setDeriveGroupEventId(pigletStatusInfoMapper);
				setDeriveTransferPigNum(pigletStatusInfoMapper);
				setDeriveTransferPigWt(pigletStatusInfoMapper);
				setDeriveTransferDate(pigletStatusInfoMapper);
				setDeriveTransferToPigInfoId(pigletStatusInfoMapper);
				setDeriveMortalityPigNum(pigletStatusInfoMapper);
				setDeriveMortalityPigWt(pigletStatusInfoMapper);
				setDeriveMortalityDate(pigletStatusInfoMapper);
				setDeriveMortalityReasonId(pigletStatusInfoMapper);
				setDeriveFosterFarrowEventId(pigletStatusInfoMapper);
				//setDerivePigletStatusType(pigletStatusInfoMapper);
				setDeriveFarrowEventId(pigletStatusInfoMapper);
				
			}
		}
	}

	
	private void setDerivePigletStatusType(final PigletStatusInfoMapper pigletStatusInfoMapper) {
		if(pigletStatusInfoMapper.getDeriveWeanPigNum() != null && pigletStatusInfoMapper.getDeriveWeanPigNum() > 0)
			pigletStatusInfoMapper.setWeanType(true);
		if(pigletStatusInfoMapper.getDeriveTransferPigNum() != null && pigletStatusInfoMapper.getDeriveTransferPigNum() > 0)
			pigletStatusInfoMapper.setTransferType(true);
		if(pigletStatusInfoMapper.getDeriveMortalityPigNum() != null && pigletStatusInfoMapper.getDeriveMortalityPigNum() > 0)
			pigletStatusInfoMapper.setDeathType(true);
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
			pigletStatusInfoMapper.setDerivePigInfoId(pigInfoDao.getPigInfoId(pigletStatusInfoMapper.getPigId(), pigletStatusInfoMapper.getDeriveCompanyId()));  
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
				 if(pigletStatusInfoMapper.getDeriveWeanPigNum() != null && pigletStatusInfoMapper.getDeriveWeanPigNum() > 0)
				 {
					 DateTime weanDate = new DateTime(pigletStatusInfoMapper.getDeriveWeanDate());
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
				 if(pigletStatusInfoMapper.getDeriveTransferPigNum() != null && pigletStatusInfoMapper.getDeriveTransferPigNum() > 0)
				 {
					 DateTime transferDate = new DateTime(pigletStatusInfoMapper.getDeriveTransferDate());
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
				 if(pigletStatusInfoMapper.getDeriveMortalityPigNum() != null && pigletStatusInfoMapper.getDeriveMortalityPigNum() > 0)
				 {
					 DateTime deathDate = new DateTime(pigletStatusInfoMapper.getDeriveMortalityEventDate());
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
	
	private void setDeriveWeanPigNum(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try{
			pigletStatusInfoMapper.setDeriveWeanPigNum(Integer.parseInt(pigletStatusInfoMapper.getNumberOfPigsWeaned()));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void setDeriveWeanPigWt(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try{
			pigletStatusInfoMapper.setDeriveWeanPigWt(Double.parseDouble(pigletStatusInfoMapper.getWeightOfPigsWeaned()));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private void setDeriveWeanDate(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try {
			pigletStatusInfoMapper.setDeriveWeanDate(DateUtil.getDateFromString(pigletStatusInfoMapper.getWeaningDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDeriveGroupEventId(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try { 
			if(pigletStatusInfoMapper.getWeanGroupId() != null)
				pigletStatusInfoMapper.setDeriveGroupEventId(groupEventDao.getGroupEventId(pigletStatusInfoMapper.getWeanGroupId(), pigletStatusInfoMapper.getDeriveCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setDeriveTransferPigNum(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try{
			pigletStatusInfoMapper.setDeriveTransferPigNum(Integer.parseInt(pigletStatusInfoMapper.getNumberOfPigsTransferred()));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void setDeriveTransferPigWt(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try{
			pigletStatusInfoMapper.setDeriveTransferPigWt(Double.parseDouble(pigletStatusInfoMapper.getWeightOfPigsTransferred()));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void setDeriveTransferDate(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try {
			pigletStatusInfoMapper.setDeriveTransferDate(DateUtil.getDateFromString(pigletStatusInfoMapper.getTransferredDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDeriveTransferToPigInfoId(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try {
			pigletStatusInfoMapper.setDeriveTransferredPigInfoId(pigInfoDao.getPigInfoId(pigletStatusInfoMapper.getTransferredToPig(), pigletStatusInfoMapper.getDeriveCompanyId()));  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDeriveMortalityPigNum(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try{
			pigletStatusInfoMapper.setDeriveMortalityPigNum(Integer.parseInt(pigletStatusInfoMapper.getNumberOfPigsMortality()));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void setDeriveMortalityPigWt(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try{
			pigletStatusInfoMapper.setDeriveMortalityPigWt(Double.parseDouble(pigletStatusInfoMapper.getWeightOfPigsMortality()));
		}catch(Exception e)
		{
			
			e.printStackTrace();
		}
	}
	
	private void setDeriveMortalityDate(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try {
			pigletStatusInfoMapper.setDeriveMortalityEventDate(DateUtil.getDateFromString(pigletStatusInfoMapper.getMortalityEventDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDeriveMortalityReasonId(final PigletStatusInfoMapper pigletStatusInfoMapper)
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
	
	
	private void setDeriveFosterFarrowEventId(final PigletStatusInfoMapper pigletStatusInfoMapper)
	{
		try {
			pigletStatusInfoMapper.setDeriveFosterFarrowEventId(farrowEventDao.getFarrowEventId(pigletStatusInfoMapper.getDeriveTransferredPigInfoId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
