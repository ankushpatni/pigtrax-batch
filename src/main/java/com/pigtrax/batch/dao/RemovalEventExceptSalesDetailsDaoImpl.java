package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pigtrax.batch.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.batch.dao.interfaces.RemovalEventExceptSalesDetailsDao;

@Repository
public class RemovalEventExceptSalesDetailsDaoImpl implements RemovalEventExceptSalesDetailsDao
{
	
private static final Logger logger = Logger.getLogger(RemovalEventExceptSalesDetailsDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	

	@Override
	public int addRemovalEventExceptSalesDetails(
			final RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
			throws SQLException {
		final String Qry = "insert into pigtrax.\"RemovalEventExceptSalesDetails\"(\"numberOfPigs\", \"removalDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
			    "\"weightInKgs\", \"id_RemovalEvent\", \"id_Premise\", \"lastUpdated\", \"userUpdated\",\"id_TransportJourney\",\"id_DestPremise\",\"remarks\",\"id_MortalityReason\", \"revenueUsd\") " + 
			       "values(?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?,?)";	
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				
				if(removalEventExceptSalesDetails.getNumberOfPigs() != null )
				{
					ps.setInt(1, removalEventExceptSalesDetails.getNumberOfPigs());
				}
				else
				{
					ps.setNull(1, java.sql.Types.INTEGER);
				}
				if(null != removalEventExceptSalesDetails.getRemovalDateTime())
				{
				ps.setDate(2, new java.sql.Date(removalEventExceptSalesDetails
						.getRemovalDateTime().getTime()));
				}
				else
				{
					ps.setNull(2, java.sql.Types.DATE);
				}
				
				if(removalEventExceptSalesDetails.getPigInfoId() != null && removalEventExceptSalesDetails.getPigInfoId() !=0 )
				{
					ps.setInt(3, removalEventExceptSalesDetails.getPigInfoId());
				}
				else
				{
					ps.setNull(3, java.sql.Types.INTEGER);
				}
				
				if(removalEventExceptSalesDetails.getGroupEventId() != null && removalEventExceptSalesDetails.getGroupEventId() !=0)
				{
					ps.setInt(4, removalEventExceptSalesDetails.getGroupEventId());
				}
				else
				{
					ps.setNull(4, java.sql.Types.INTEGER);
				}
				
				if(removalEventExceptSalesDetails.getWeightInKgs() != null )
				{
					ps.setBigDecimal(5, removalEventExceptSalesDetails.getWeightInKgs());
				}
				else
				{
					ps.setNull(5, java.sql.Types.INTEGER);
				}
				
				if(removalEventExceptSalesDetails.getRemovalEventId() != null && removalEventExceptSalesDetails.getRemovalEventId() !=0)
				{
					ps.setInt(6, removalEventExceptSalesDetails.getRemovalEventId());
				}
				else
				{
					ps.setNull(6, java.sql.Types.INTEGER);
				}	
				
				if(removalEventExceptSalesDetails.getPremiseId() != null && removalEventExceptSalesDetails.getPremiseId() !=0)
				{
					ps.setInt(7, removalEventExceptSalesDetails.getPremiseId());
				}
				else
				{
					ps.setNull(7, java.sql.Types.INTEGER);
				}				
				
				ps.setString(8, removalEventExceptSalesDetails.getUserUpdated());	
				

				if(removalEventExceptSalesDetails.getTransportJourneyId() != null && removalEventExceptSalesDetails.getTransportJourneyId()!=0)
				{
					ps.setInt(9, removalEventExceptSalesDetails.getTransportJourneyId());
				}
				else
				{
					ps.setNull(9, java.sql.Types.INTEGER);
				}
				if(removalEventExceptSalesDetails.getDestPremiseId() != null )
				{
					ps.setInt(10, removalEventExceptSalesDetails.getDestPremiseId());
				}
				else
				{
					ps.setNull(10, java.sql.Types.INTEGER);
				}
				ps.setString(11, removalEventExceptSalesDetails.getRemarks());
				
				if(removalEventExceptSalesDetails.getMortalityReasonId() != null && removalEventExceptSalesDetails.getMortalityReasonId() !=0)
				{
					ps.setInt(12, removalEventExceptSalesDetails.getMortalityReasonId());
				}
				else
				{
					ps.setNull(12, java.sql.Types.INTEGER);
				}
				
				if(removalEventExceptSalesDetails.getRevenue() != null)
				{
					ps.setDouble(13, removalEventExceptSalesDetails.getRevenue());
				}
				else
				{
					ps.setNull(13, java.sql.Types.DOUBLE);
				}
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;

	}
}
