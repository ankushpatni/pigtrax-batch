package com.pigtrax.batch.util;

public interface Constants {

	public static final String HEADER = "header";

	public static final String DATA = "data";

	public static final String EVENT_TYPE = "eventType";

	public static final String SEPERATOR = "seperator";

	public static final String FILE_TYPE = "fileType";

	public static final String USER_NAME = "userName";

	public static final char DEFAULT_SEPRATOR = ',';

	public static final String REF_DATA_SEX = "SEX";

	public static final String REF_DATA_GFUNCTION = "GFUNCTION";
	
	public static final String REF_DATA_PREGNANCYEVENTTYPE = "PREGNANCYEVENTTYPE";
	
	public static final String REF_DATA_PREGNANCYEXAMRESULTTYPE = "PREGNANCYEXAMRESULTTYPE";
	
	public static final String REF_DATA_MORTALITYREASONTYPE = "MORTALITYREASONTYPE";
	
	public static final String REF_DATA_PIGLETSTATUSEVENTTYPE = "PIGLETSTATUSEVENTTYPE";	

	public static final String ERR_SYS_CODE = "100";

	public static final String ERR_SYS_MESSASGE = "Unexpected error :";

	public static final String ERR_EMPTY_CODE = "01";

	public static final String ERR_EMPTY_MESSAGE = "Field can not be empty";

	public static final String NO_ID_FOUND = "NO_ID_FOUND";

	public static final String DB_ERR = "DB_ERROR";

	public static final String REF_DATA_ERR = "REF_DATA_ERROR";

	public static final String REF_DATA_NOT_FOUND_CODE = "200";

	public static final String REF_DATA_NOT_FOUND_MSG = "REF_DATA_NOT_FOUND";

	public static final String CUSTOM_VAL_ERR = "CUSTOM_VALIDATION_ERROR";

	public static final String ERR_DATA_TYPE_MIS_MATCH = "501";

	public static final String ERR_DATA_TYPE_MIS_MATCH_MSG = "Data type is wrong";

	public static final String ERR_PIGONFO_ENTRY_EVENT_ENTRY_DATE = "601";

	public static final String ERR_PIGONFO_ENTRY_EVENT_ENTRY_DATE_MSG = "Entry date should be greater that birthdate by 90 days";
	
	public static final String ERR_PREGNANCY_EVENT_DUPLICATE_CODE = "701";
	
	public static final String ERR_PREGNANCY_EVENT_DUPLICATE_MSG = "An entry already exist with the given combination of (Pig Id, Company Id, service Date, Event Type, Result Type)";
	
	public static final String ERR_PREGNANCY_EVENT_DATE_CODE = "702";
	
	public static final String ERR_PREGNANCY_EVENT_DATE_MSG = "Pregnancy exam and result dates should be between 18 to 60 days from service date";
	
	public static final String ERR_ABORTION_DATE_CODE = "703";
	
	public static final String ERR_ABORTION_DATE_MSG = "Abortion date should be between 18 to 110 days from service date";
	
	public static final String ERR_NOT_IN_PIG_DATE_CODE ="704";
	
	public static final String ERR_NOT_IN_PIG_DATE_MSG = "Not in pig date should be between 105 to 125 days from service date";
	
	public static final String ERR_PREG_EVENT_SERVICE_NOT_FOUND_CODE = "705";
	
	public static final String ERR_PREG_EVENT_SERVICE_NOT_FOUND_MSG = "Service record not found for the given service date";	

	public static final String ERR_FOSTER_PIG_NOT_FOUND_CODE = "801";
	
	public static final String ERR_FOSTER_PIG_NOT_FOUND_MSG = "Transferred to Pig is not found";
	
	public static final String ERR_FOSTER_FARROW_NOT_FOUND_CODE = "802";
	
	public static final String ERR_FOSTER_FARROW_NOT_FOUND_MSG = "No valid farrow event associated with transferred to Pig";
	
	public static final String ERR_WEAN_GROUPID_NOT_FOUND_CODE = "803";
	
	public static final String ERR_WEAN_GROUPID_NOT_FOUND_MSG = "No valid group event found for the wean record";
}
