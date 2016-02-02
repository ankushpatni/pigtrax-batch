package com.pigtrax.batch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pigtrax.batch.beans.RefDataTranslation;
import com.pigtrax.batch.dao.interfaces.RefDataDao;

@Repository
public class RefDataDaoImpl implements RefDataDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<RefDataTranslation> getSexData() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_SexType\" FROM pigtraxrefdata.\"SexTypeTranslation\" order by \"fieldLanguage\", \"id_SexType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}

	@Override
	public List<RefDataTranslation> getGfunctionType() { 
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_GfunctionType\" FROM pigtraxrefdata.\"GfunctionTypeTranslation\" order by \"fieldLanguage\", \"id_GfunctionType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}

	@Override
	public List<RefDataTranslation> getPregnancyEventType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_PregnancyEventType\" FROM pigtraxrefdata.\"PregnancyEventTypeTranslation\" order by \"fieldLanguage\", \"id_PregnancyEventType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslation> getPregnancyExamResultType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_PregnancyExamResultType\" FROM pigtraxrefdata.\"PregnancyExamResultTypeTranslation\" order by \"fieldLanguage\", \"id_PregnancyExamResultType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslation> getMortalityReasonType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_MortalityReasonType\" FROM pigtraxrefdata.\"MortalityReasonTypeTranslation\" order by \"fieldLanguage\", \"id_MortalityReasonType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	public List<RefDataTranslation> getPigletStatusEventType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_PigletStatusEventType\" FROM pigtraxrefdata.\"PigletStatusEventTypeTranslation\" order by \"fieldLanguage\", \"id_PigletStatusEventType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslation> getPhaseOfProductionType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_PhaseOfProductionType\" FROM pigtraxrefdata.\"PhaseOfProductionTypeTranslation\" order by \"fieldLanguage\", \"id_PhaseOfProductionType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslation> getBreedingServiceType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_BreedingServiceType\" FROM pigtraxrefdata.\"BreedingServiceTypeTranslation\" order by \"fieldLanguage\", \"id_BreedingServiceType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslation> getRemovalEventType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_RemovalType\" FROM pigtraxrefdata.\"RemovalEventTypeTranslation\" order by \"fieldLanguage\", \"id_RemovalType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	private final static class CacheRefDataRowMaper implements RowMapper<RefDataTranslation> {
		@Override
		public RefDataTranslation mapRow(ResultSet rs, int rowNum) throws SQLException {
			String fieldValue = rs.getString(1);
			String fieldLanguage = rs.getString(2);
			Integer fieldCode = rs.getInt(3);
			return new RefDataTranslation(fieldLanguage, fieldValue, fieldCode);
		}
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<RefDataTranslation> getGcompanyType() { 
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_GcompanyType\" FROM pigtraxrefdata.\"GcompanyTypeTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	@Override
	public List<RefDataTranslation> getGlineTypes() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_GlineType\" FROM pigtraxrefdata.\"GlineTypeTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslation> getSaleTypes() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_SalesType\" FROM pigtraxrefdata.\"SalesTypeTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslation> getSaleResons() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_SalesReason\" FROM pigtraxrefdata.\"SalesReasonsTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslation> getFeedEventTypes() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_FeedEventType\" FROM pigtraxrefdata.\"FeedEventTypeTranslation\" order by \"fieldLanguage\", \"id_FeedEventType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}

}