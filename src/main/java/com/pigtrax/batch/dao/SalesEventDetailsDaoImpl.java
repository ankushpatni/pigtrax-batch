package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.SalesEventDetails;
import com.pigtrax.batch.dao.interfaces.SalesEventDetailsDao;

@Repository
@Transactional
public class SalesEventDetailsDaoImpl implements SalesEventDetailsDao
{

	private static final Logger logger = Logger.getLogger(SalesEventDetailsDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	
	@Override
	public int addSalesEventDetails( final SalesEventDetails salesEventDetails)	throws SQLException 
	{
		/*final String Qry = "insert into pigtrax.\"SalesEventDetails\"(\"invoiceId\", \"ticketNumber\", \"numberOfPigs\", \"revenueUsd\","+ 
			       "\"weightInKgs\", \"salesDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
			       "\"soldTo\", \"id_RemovalEvent\", \"lastUpdated\", \"userUpdated\",\"id_TransportJourney\",\"remarks\") " + 
			       "values(?,?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?)";		
*/
		final String Qry = "insert into pigtrax.\"SalesEventDetails\"(\"invoiceId\", \"ticketNumber\", \"numberOfPigs\", \"revenueUsd\","+ 
			       "\"weightInKgs\", \"salesDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
			       "\"soldTo\", \"id_RemovalEvent\", \"lastUpdated\", \"userUpdated\",\"remarks\",\"salesTypes\",\"salesReasons\",\"id_TransportJourney\") " + 
			       "values(?,?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?)";		

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				ps.setString(1, salesEventDetails.getInvoiceId());
				ps.setString(2, salesEventDetails.getTicketNumber());
				
				if(salesEventDetails.getNumberOfPigs() != null )
				{
					ps.setInt(3, salesEventDetails.getNumberOfPigs());
				}
				else
				{
					ps.setNull(3, java.sql.Types.INTEGER);
				}
				
				if(salesEventDetails.getRevenueUsd() != null )
				{
					ps.setBigDecimal(4, salesEventDetails.getRevenueUsd());
				}
				else
				{
					ps.setNull(4, java.sql.Types.INTEGER);
				}
				
				if(salesEventDetails.getWeightInKgs() != null )
				{
					ps.setBigDecimal(5, salesEventDetails.getWeightInKgs());
				}
				else
				{
					ps.setNull(5, java.sql.Types.INTEGER);
				}
				
				
				if(null != salesEventDetails.getSalesDateTime())
				{
				ps.setDate(6, new java.sql.Date(salesEventDetails
						.getSalesDateTime().getTime()));
				}
				else
				{
					ps.setNull(6, java.sql.Types.DATE);
				}
				
				if(salesEventDetails.getPigInfoId() != null && salesEventDetails.getPigInfoId() !=0)
				{
					ps.setInt(7, salesEventDetails.getPigInfoId());
				}
				else
				{
					ps.setNull(7, java.sql.Types.INTEGER);
				}
				
				if(salesEventDetails.getGroupEventId() != null && salesEventDetails.getGroupEventId() !=0 )
				{
					ps.setInt(8, salesEventDetails.getGroupEventId());
				}
				else
				{
					ps.setNull(8, java.sql.Types.INTEGER);
				}
				
				ps.setString(9, salesEventDetails.getSoldTo());
				
				if(salesEventDetails.getRemovalEventId() != null && salesEventDetails.getRemovalEventId() !=0)
				{
					ps.setInt(10, salesEventDetails.getRemovalEventId());
				}
				else
				{
					ps.setNull(10, java.sql.Types.INTEGER);
				}
				
				
				ps.setString(11, salesEventDetails.getUserUpdated());
				
				/*if(salesEventDetails.getTransportJourneyId() != null )
				{
					ps.setInt(12, salesEventDetails.getTransportJourneyId());
				}
				else
				{
					ps.setNull(12, java.sql.Types.INTEGER);
				}*/
				
				ps.setString(12, salesEventDetails.getRemarks());
				
				if((salesEventDetails.getSalesTypesAsString()) != null )
				{
					ps.setString(13, salesEventDetails.getSalesTypesAsString());
				}
				
				if((salesEventDetails.getSalesReasonAsString()) != null )
				{
					ps.setString(14, salesEventDetails.getSalesReasonAsString());
				}
				
				if((salesEventDetails.getTransPortJourneyId()) != null )
				{
					ps.setInt(15, salesEventDetails.getTransPortJourneyId());
				}else
				{
					ps.setNull(15, java.sql.Types.INTEGER);
				}
				
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;
	}
	
}
