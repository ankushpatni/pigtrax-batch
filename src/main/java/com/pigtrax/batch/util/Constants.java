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

	public static final String BLANK_STRING = "";

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

	public static final String ERR_NOT_IN_PIG_DATE_CODE = "704";

	public static final String ERR_NOT_IN_PIG_DATE_MSG = "Not in pig date should be between 105 to 125 days from service date";

	public static final String ERR_PREG_EVENT_SERVICE_NOT_FOUND_CODE = "705";

	public static final String ERR_PREG_EVENT_SERVICE_NOT_FOUND_MSG = "Service record not found for the given service date";

	public static final String ERR_FOSTER_PIG_NOT_FOUND_CODE = "801";

	public static final String ERR_FOSTER_PIG_NOT_FOUND_MSG = "Transferred to Pig is not found";

	public static final String ERR_FOSTER_FARROW_NOT_FOUND_CODE = "802";

	public static final String ERR_FOSTER_FARROW_NOT_FOUND_MSG = "No valid farrow event associated with transferred to Pig";

	public static final String ERR_WEAN_GROUPID_NOT_FOUND_CODE = "803";

	public static final String ERR_WEAN_GROUPID_NOT_FOUND_MSG = "No valid group event found for the wean record";

	public static final String FRW_EVNT_ERR_PIG_ID = "601";

	public static final String FRW_EVNT_ERR_PIG_ID_MSG = "Pig ID  is missing";

	public static final String FRW_EVNT_ERR_CMPNY_ID = "602";

	public static final String FRW_EVNT_ERR_CMPNY_ID_MSG = "Company Id is missing";

	public static final String FRW_EVNT_ERR_PIG_PK_ID = "603";

	public static final String FRW_EVNT_ERR_PIG_PK_ID_MSG = "Either Pig ID or Company Id is missing";

	public static final String FRW_EVNT_ERR_SRV_DATE = "604";

	public static final String FRW_EVNT_ERR_SRV_DATE_MSG = "Service Date  is missing";

	public static final String FRW_EVNT_ERR_BRD_DATE = "605";

	public static final String FRW_EVNT_ERR_BRD_DATE_MSG = "Breeding Date  is missing";

	public static final String FRW_EVNT_ERR_PRG_TEST = "606";

	public static final String FRW_EVNT_ERR_PRG_TEST_MSG = "Pragnancy Test Failed.";

	public static final String FRW_EVNT_ERR_PEN_ID = "607";

	public static final String FRW_EVNT_ERR_PEN_ID_MSG = "Pen Id id missing in CSV file";

	public static final String FRW_EVNT_ERR_PEN_ID_PK = "608";

	public static final String FRW_EVNT_ERR_PEN_ID_PK_MSG = "Pen Id id missing in Data Base";

	public static final String FRW_EVNT_ERR_FRW_DATE = "609";

	public static final String FRW_EVNT_ERR_FRW_DATE_MSG = "Farrow Date is missing in CSV file";

	public static final String FRW_EVNT_ERR_FRW_DATE_1 = "610";

	public static final String FRW_EVNT_ERR_FRW_DATE_1_MSG = "Farrow Date is missing.";

	public static final String FRW_EVNT_ERR_FRW_DATE_2 = "611";

	public static final String FRW_EVNT_ERR_FRW_DATE_2_MSG = "Farrow Date lessthan by 105 days OR greaterthan by 130 days to Service  Date ";

	public static final String FRW_EVNT_ERR_BORNS_MUM_VAL = "612";

	public static final String FRW_EVNT_ERR_BORNS_MUM_VAL_MSG = "Atleast 1 Borns or Mummies required.";

	public static final String FRW_EVNT_ERR_LITTER_WEIGHT = "613";

	public static final String FRW_EVNT_ERR_LITTER_WEIGHT_MSG = "Litter Weight in KGs is missing";

	public static final String FRW_EVNT_ERR_BIRTH_TYPE = "614";

	public static final String FRW_EVNT_ERR_BIRTH_TYPE_MSG = "Type Of Birth is missing";

	public static final String FRW_EVNT_ERR_EMP_GRP = "615";

	public static final String FRW_EVNT_ERR_EMP_GRP_MSG = "Employee Group is missing";

	public static final String FRW_EVNT_ERR_TEATS = "616";

	public static final String FRW_EVNT_ERR_EMP_TEATS_MSG = "Teats is missing";

	public static final String FRW_EVNT_ERR_TEATS_VAL = "617";

	public static final String FRW_EVNT_ERR_TEATS_VAL_MSG = "Teats should be between 1-5";

	public static final String FRW_EVNT_ERR_SOW_CNDTN = "618";

	public static final String FRW_EVNT_ERR_SOW_CNDTN_MSG = "Sow Condition is missing";

	public static final String FRW_EVNT_ERR_SOW_CNDTN_VAL = "615";

	public static final String FRW_EVNT_ERR_SOW_CNDTN_VAL_MSG = "Sow Condition should be between 1-30";

}
