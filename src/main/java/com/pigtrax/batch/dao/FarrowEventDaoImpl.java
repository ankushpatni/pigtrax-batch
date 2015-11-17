package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.FarrowEvent;
import com.pigtrax.batch.dao.interfaces.FarrowEventDao;

@Repository
@Transactional
public class FarrowEventDaoImpl implements FarrowEventDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = Logger.getLogger(FarrowEventDaoImpl.class);

	@Override
	public Integer getFarrowEventId(Integer pigInfoId, Date farrowEventDate) {
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"FarrowEvent\" where \"id_PigInfo\" = ? and \"farrowDateTime\"::date = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigInfoId);
				ps.setDate(2, new java.sql.Date(farrowEventDate.getTime()));
			}
		}, new ResultSetExtractor<Long>() {
			public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				if (resultSet.next()) {
					return resultSet.getLong(1);
				}
				return null;
			}
		});
		logger.debug("farrowEvent Id retVal is :" + retValList1);
		if (retValList1 != null) {
			return Integer.decode(retValList1.toString());
		}

		return null;
	}

	@Override
	public Integer getFarrowEventId(Integer pigInfoId) {
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"FarrowEvent\" where \"id_PigInfo\" = ? order by \"id\" desc ");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigInfoId);
			}
		}, new ResultSetExtractor<Long>() {
			public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				if (resultSet.next()) {
					return resultSet.getLong(1);
				}
				return null;
			}
		});
		logger.debug("farrowEvent Id retVal is :" + retValList1);
		if (retValList1 != null) {
			return Integer.decode(retValList1.toString());
		}

		return null;
	}

	@Override
	public int insertFarrowEventformation(final FarrowEvent farrowEvent) throws SQLException, DuplicateKeyException {

		final String Qry = "insert into pigtrax.\"FarrowEvent\"(\"farrowDateTime\", \"id_Pen\", \"liveBorns\", "
				+ "\"stillBorns\", \"mummies\", \"maleBorns\", \"femaleBorns\", \"weightInKgs\", \"inducedBirth\", "
				+ "\"assistedBirth\", \"remarks\", \"sowCondition\", \"lastUpdated\", \"userUpdated\", \"id_EmployeeGroup\", "
				+ "\"id_PigInfo\", \"id_PregnancyEvent\",\"teats\" ) values(?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry, new String[] { "id" });
				if (farrowEvent.getFarrowDateTime() != null) {
					ps.setDate(1, new java.sql.Date(farrowEvent.getFarrowDateTime().getTime()));
				} else {
					ps.setNull(1, java.sql.Types.DATE);
				}
				if (farrowEvent.getPenId() != null) {
					ps.setInt(2, farrowEvent.getPenId());
				} else {
					ps.setNull(2, java.sql.Types.INTEGER);
				}
				ps.setObject(3, farrowEvent.getLiveBorns(), java.sql.Types.INTEGER);
				ps.setObject(4, farrowEvent.getStillBorns(), java.sql.Types.INTEGER);
				ps.setObject(5, farrowEvent.getMummies(), java.sql.Types.INTEGER);
				ps.setObject(6, farrowEvent.getMaleBorns(), java.sql.Types.INTEGER);
				ps.setObject(7, farrowEvent.getFemaleBorns(), java.sql.Types.INTEGER);
				ps.setObject(8, farrowEvent.getWeightInKGs(), java.sql.Types.DOUBLE);
				ps.setBoolean(9, farrowEvent.getInducedBirth());
				ps.setBoolean(10, farrowEvent.getAssistedBirth());
				ps.setString(11, farrowEvent.getRemarks());
				if (farrowEvent.getSowCondition() != null) {
					ps.setInt(12, farrowEvent.getSowCondition());
				} else {
					ps.setNull(12, java.sql.Types.INTEGER);
				}
				ps.setString(13, farrowEvent.getUserUpdated());
				if (farrowEvent.getEmployeeGrpId() != null && farrowEvent.getEmployeeGrpId() != 0) {
					ps.setInt(14, farrowEvent.getEmployeeGrpId());
				} else {
					ps.setNull(14, java.sql.Types.INTEGER);
				}

				if (farrowEvent.getPigInfoId() != null) {
					ps.setInt(15, farrowEvent.getPigInfoId());
				} else {
					ps.setNull(15, java.sql.Types.INTEGER);
				}
				if (farrowEvent.getPragnancyEventId() != null) {
					ps.setInt(16, farrowEvent.getPragnancyEventId());
				} else {
					ps.setNull(16, java.sql.Types.INTEGER);
				}
				ps.setInt(17, farrowEvent.getTeasts());
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;

	}
	
	
	 @Override
		public boolean checkFarrowEventByBreedingEvent(final Integer breedingEventId) { 
			String sql = "select count(FE.\"id\") from pigtrax.\"FarrowEvent\" FE where \"id_PregnancyEvent\" "
					+ "in (select PE.\"id\" from pigtrax.\"PregnancyEvent\" PE where \"id_BreedingEvent\" = ?) ";
			@SuppressWarnings("unchecked")
			Integer cnt  = (Integer)jdbcTemplate.query(sql,new PreparedStatementSetter() {
				@Override
					public void setValues(PreparedStatement ps) throws SQLException {					
						ps.setInt(1, breedingEventId);
					}
				},
			        new ResultSetExtractor() {
			          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return null;
			          }
			        });
			
			return cnt > 0? true : false;
		}
	 
	 @Override
	 public boolean checkIfFarrowExists(final Integer pregnancyEventId) {
		 String sql = "select count(FE.\"id\") from pigtrax.\"FarrowEvent\" FE where \"id_PregnancyEvent\" = ? ";
			@SuppressWarnings("unchecked")
			Integer cnt  = (Integer)jdbcTemplate.query(sql,new PreparedStatementSetter() {
				@Override
					public void setValues(PreparedStatement ps) throws SQLException {					
						ps.setInt(1, pregnancyEventId);
					}
				},
			        new ResultSetExtractor() {
			          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return null;
			          }
			        });
			
			return cnt > 0? true : false;
	 }
}
