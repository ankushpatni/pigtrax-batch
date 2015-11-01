package com.pigtrax.batch.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.PigTraxEventMaster;
import com.pigtrax.batch.dao.interfaces.PigTraxEventMasterDao;

@Repository
@Transactional
public class PigTraxEventMasterDaoImpl implements PigTraxEventMasterDao {
    
	private static final Logger logger = Logger.getLogger(PigTraxEventMasterDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * Insert the entry event details of a pig info
	 * @param master
	 * @return
	 * @throws SQLException
	 */
	public int insertEventMaster(final PigTraxEventMaster master) throws SQLException {
		logger.info("Entering insertEventMaster");
		String Qry = "insert into pigtrax.\"PigTraxEventMaster\"( \"eventTime\", \"id_PigInfo\", \"lastUpdated\", \"userUpdated\", \"id_GroupEvent\", \"id_BreedingEvent\", \"id_PregnancyEvent\", \"id_FarrowEvent\", \"id_PigletStatus\", \"id_FeedEvent\", \"id_RemovalEvent\") "
				+ "values(?,?,current_timestamp,?,?,?,?,?,?,?,?)";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
		
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setDate(1, new java.sql.Date(master.getEventTime().getTime()));
				if(null != master.getPigInfoId())
					ps.setInt(2, master.getPigInfoId());
				else
					ps.setNull(2, java.sql.Types.INTEGER);
				ps.setString(3, master.getUserUpdated());
				ps.setObject(4, (master.getGroupEventId() != null)?master.getGroupEventId():null);
				ps.setObject(5, (master.getBreedingEventId() != null)?master.getBreedingEventId():null);
				ps.setObject(6, (master.getPregnancyEventId() != null)?master.getPregnancyEventId():null);
				ps.setObject(7, (master.getFarrowEventId() != null)?master.getFarrowEventId():null);
				ps.setObject(8, (master.getPigletStatusId() != null)?master.getPigletStatusId():null);
				ps.setObject(9, (master.getFeedEventId() != null)?master.getFeedEventId():null);
				ps.setObject(10, (master.getRemovalEventId() != null)?master.getRemovalEventId():null);
			}
		});
		
	}
	
	/**
	 * Delete the piglet status event entries for a given farrow event id
	 * @param farrowEventId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int deletePigletStatusEvents(final Integer farrowEventId)
			throws SQLException {
		final String qry = "delete from pigtrax.\"PigTraxEventMaster\" where \"id_PigletStatus\" IN "
				+ "(select \"id\" from pigtrax.\"PigletStatus\" where \"id_FarrowEvent\" = ? or \"id_fosterFarrowEvent\" = ?)";
				
		int rowsDeleted = this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, farrowEventId);
				ps.setInt(2, farrowEventId);
			}
		});
		return rowsDeleted;
	}
	
}
