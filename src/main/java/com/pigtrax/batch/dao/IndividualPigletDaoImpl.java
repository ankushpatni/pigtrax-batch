package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.IndividualPigletStatus;
import com.pigtrax.batch.dao.interfaces.IndividualPigletDao;

@Repository
@Transactional
public class IndividualPigletDaoImpl implements IndividualPigletDao {
	private static final Logger logger = Logger.getLogger(IndividualPigletDaoImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { 
		this.jdbcTemplate = jdbcTemplate;
	} 
	
	@Override
	public boolean checkIfExists(final String tattooId, final Integer premiseId) {
		String sql = "select \"id\" from pigtrax.\"IndividualPigletStatus\" where lower(\"tattooId\") = ? "
				+ "and \"id_Premise\" = ? ";
		Long retValList1 = null;
		retValList1 = jdbcTemplate.query(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tattooId.trim().toLowerCase());
				ps.setInt(2, premiseId);
			}
		}, new ResultSetExtractor<Long>() {
			public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				if (resultSet.next()) {
					return resultSet.getLong(1);
				}
				return null;
			}
		});
		if (retValList1 != null) {
			return true;
		} else
			return false;
	}
	
	
	@Override
	public Integer insertIndividualPigletStatus(
			final IndividualPigletStatus individualPigletStatus) throws Exception {
		final String Qry = "insert into pigtrax.\"IndividualPigletStatus\"(\"tattooId\", \"weightAtBirth\", \"weightAtWeaning\", \"lastUpdated\","
				+ " \"userUpdated\", \"pigId\",\"litterId\",\"id_Premise\",\"weight1\",\"weight2\", \"weight3\", \"weight4\", \"weight5\",\"weight6\") "
				+ "values(?,?,?,current_timestamp,?,?,?,?,?,?,?,?,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setString(1, individualPigletStatus.getTattooId());
	    	            ps.setObject(2, individualPigletStatus.getWtAtBirth(), java.sql.Types.DOUBLE);
	    				ps.setObject(3, individualPigletStatus.getWtAtWeaning(), java.sql.Types.DOUBLE);
	    				ps.setString(4, individualPigletStatus.getUserUpdated());
	    				ps.setObject(5, individualPigletStatus.getPigId());
	    				ps.setObject(6, individualPigletStatus.getLitterId(), java.sql.Types.INTEGER);
	    				ps.setObject(7, individualPigletStatus.getPremiseId(), java.sql.Types.INTEGER);
	    				ps.setObject(8, individualPigletStatus.getWtAtFirstMonth(), java.sql.Types.DOUBLE);
	    				ps.setObject(9, individualPigletStatus.getWtAtSecondMonth(), java.sql.Types.DOUBLE);
	    				ps.setObject(10, individualPigletStatus.getWtAtThirdMonth(), java.sql.Types.DOUBLE);
	    				ps.setObject(11, individualPigletStatus.getWtAtFourthMonth(), java.sql.Types.DOUBLE);
	    				ps.setObject(12, individualPigletStatus.getWtAtFifthMonth(), java.sql.Types.DOUBLE);
	    				ps.setObject(13, individualPigletStatus.getWtAtSixthMonth(), java.sql.Types.DOUBLE);
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
		
	}
	
	@Override
	public boolean canAddPigletStatus(final Integer farrowEventId) {
		String sql = "select CASE WHEN FE.\"liveBorns\"> COUNT(IND.\"id\") THEN 'true' ELSE 'false' END "
				+ "from pigtrax.\"FarrowEvent\" FE left join pigtrax.\"IndividualPigletStatus\" IND ON IND.\"id_FarrowEvent\" = FE.\"id\" "
				+ "where FE.\"id\" = ? group by FE.\"id\"";
		String flag = null;
		flag = jdbcTemplate.query(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, farrowEventId);
			}
		}, new ResultSetExtractor<String>() {
			public String extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				if (resultSet.next()) {
					return resultSet.getString(1);
				}
				return null;
			}
		});
		if (flag != null && "true".equals(flag)) {
			return true;
		} else
			return false;
	}
	
}
