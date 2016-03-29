package com.pigtrax.batch.util;

public interface Constants {

	public static final String HEADER = "header";

	public static final String DATA = "data";

	public static final String EVENT_TYPE = "eventType";

	public static final String SEPERATOR = "seperator";

	public static final String FILE_TYPE = "fileType";

	public static final String USER_NAME = "userName";

	public static final char DEFAULT_SEPRATOR = ',';
	
	public static final String COMPANY_ID = "companyId";
	
	public static final String PREMISE_ID = "premiseId";

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

	public static final String REF_DATA_NOT_FOUND_MSG = "Invalid data found";
	
	public static final String ENTRY_EVENT_INVALID_SEX_CODE = "EE-205";

	public static final String ENTRY_EVENT_INVALID_SEX_MSG = "Invalid sex specified";

	public static final String CUSTOM_VAL_ERR = "CUSTOM_VALIDATION_ERROR";

	public static final String ERR_DATA_TYPE_MIS_MATCH = "GEN-103";

	public static final String ERR_DATA_TYPE_MIS_MATCH_MSG = "Invalid type of data specified";
	
	public static final String INVALID_PIGID_CODE = "GEN-104";
	
	public static final String INVALID_PIGID_MSG = "Invalid Pig Id specified";
	
	public static final String ERR_PIGONFO_ENTRY_EVENT_ENTRY_DATE = "EE-204";

	public static final String ERR_PIGONFO_ENTRY_EVENT_ENTRY_DATE_MSG = "The difference between Entry Date and Birth Date should fall between 100-300 days.";

	public static final String ERR_PREGNANCY_EVENT_DUPLICATE_CODE = "PE-205";

	public static final String ERR_PREGNANCY_EVENT_DUPLICATE_MSG = "An entry already exist with the given combination of (Pig Id, Company Id, service Date)";

	public static final String ERR_PREGNANCY_EVENT_DATE_CODE = "702";

	public static final String ERR_PREGNANCY_EVENT_DATE_MSG = "Pregnancy exam and result dates should be between 18 to 60 days from service date";

	public static final String ERR_ABORTION_DATE_CODE = "703";

	public static final String ERR_ABORTION_DATE_MSG = "Abortion date should be between 18 to 110 days from service date";

	public static final String ERR_NOT_IN_PIG_DATE_CODE = "704";

	public static final String ERR_NOT_IN_PIG_DATE_MSG = "Not in pig date should be between 105 to 125 days from service date";

	public static final String ERR_PREG_EVENT_SERVICE_NOT_FOUND_CODE = "PE-202";

	public static final String ERR_PREG_EVENT_SERVICE_NOT_FOUND_MSG = "Matching Service record not found for the pig for the given date";
	
	public static final String ERR_FRW_EVENT_SERVICE_NOT_FOUND_CODE = "PSE-204";

	public static final String ERR_FRW_EVENT_SERVICE_NOT_FOUND_MSG = "Matching farrow event record not found for the pig";

	public static final String ERR_FOSTER_PIG_NOT_FOUND_CODE = "PSE-205";

	public static final String ERR_FOSTER_PIG_NOT_FOUND_MSG = "Transferred to Pig is not found";

	public static final String ERR_FOSTER_FARROW_NOT_FOUND_CODE = "PSE-206";

	public static final String ERR_FOSTER_FARROW_NOT_FOUND_MSG = "No valid farrow event associated with transferred to Pig";

	public static final String ERR_WEAN_GROUPID_NOT_FOUND_CODE = "PSE-207";

	public static final String ERR_WEAN_GROUPID_NOT_FOUND_MSG = "No valid group event found for the wean record";
	
	public static final String REF_DATA_PHASEOFPRODUCTIONTYPE = "PHASEOFPRODUCTIONTYPE"; 

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

	public static final String FRW_EVNT_ERR_SERVICE_RECORD_CODE = "FE-201";

	public static final String FRW_EVNT_ERR_SERVICE_RECORD_MSG = "No matching service records found for the given result date";

	public static final String FRW_EVNT_ERR_PEN_ID = "FE-202";

	public static final String FRW_EVNT_ERR_PEN_ID_MSG = "Pen Id id missing";

	public static final String FRW_EVNT_ERR_PEN_ID_PK = "FE-203";

	public static final String FRW_EVNT_ERR_PEN_ID_PK_MSG = "Invalid pen id specified";

	public static final String FRW_EVNT_ERR_FRW_DATE = "FE-204";

	public static final String FRW_EVNT_ERR_FRW_DATE_MSG = "Farrow Date is missing";

	public static final String FRW_EVNT_ERR_FRW_DATE_1 = "FE-205";

	public static final String FRW_EVNT_ERR_FRW_DATE_1_MSG = "Farrow Date is missing.";

	public static final String FRW_EVNT_ERR_FRW_DATE_2 = "611";

	public static final String FRW_EVNT_ERR_FRW_DATE_2_MSG = "Farrow Date lessthan by 105 days OR greaterthan by 130 days to Service  Date ";

	public static final String FRW_EVNT_ERR_BORNS_MUM_VAL = "612";

	public static final String FRW_EVNT_ERR_BORNS_MUM_VAL_MSG = "Atleast 1 Borns or Mummies required.";

	public static final String FRW_EVNT_ERR_LITTER_WEIGHT = "FE-207";

	public static final String FRW_EVNT_ERR_LITTER_WEIGHT_MSG = "Litter Weight in KGs is missing";
	
	public static final String FRW_EVNT_INVALID_LITTER_WEIGHT = "FE-208";

	public static final String FRW_EVNT_INVALID_LITTER_WEIGHT_MSG = "Invalid Litter Weight specified";


	public static final String FRW_EVNT_ERR_BIRTH_TYPE = "614";

	public static final String FRW_EVNT_ERR_BIRTH_TYPE_MSG = "Type Of Birth is missing";
	
	public static final String FRW_EVNT_INVALID_BIRTH_TYPE = "FE-209";

	public static final String FRW_EVNT_INVALID_BIRTH_TYPE_MSG = "Invalid birth type specified. Applicable values - Assisted/Induced";

	public static final String INVALID_EMP_GRP_CODE = "GEN-107";

	public static final String INVALID_EMP_GRP_MSG = "Invalid employee group specified";

	public static final String FRW_EVNT_ERR_TEATS = "FE-210";

	public static final String FRW_EVNT_ERR_EMP_TEATS_MSG = "Invalid Teats value";

	public static final String FRW_EVNT_ERR_TEATS_VAL = "FE-211";

	public static final String FRW_EVNT_ERR_TEATS_VAL_MSG = "Teats should be between 1-30";

	public static final String FRW_EVNT_ERR_SOW_CNDTN = "618";

	public static final String FRW_EVNT_ERR_SOW_CNDTN_MSG = "Invalid sow condition";

	public static final String FRW_EVNT_ERR_SOW_CNDTN_VAL = "615";

	public static final String FRW_EVNT_ERR_SOW_CNDTN_VAL_MSG = "Sow Condition should be between 1-5";
	
	public static final String FEED_EVNT_ERR_RATIONID = "701";

	public static final String FEED_EVNT_ERR_RATIONID_MSG = "Ration Id is missing";
	
	public static final String FEED_EVNT_ERR_TICKETID = "702";

	public static final String FEED_EVNT_ERR_TICKETID_MSG = "Ticket id is missing";
	
	public static final String FEED_EVNT_ERR_INI_ENTRY_DATE = "703";

	public static final String FEED_EVNT_ERR_INI_ENTRY_DATE_MSG = "Initial Feed Entry Date is missing";
	
	public static final String FEED_EVNT_ERR_TRNS_JRNY_ID = "704";

	public static final String FEED_EVNT_ERR_TRNS_JRNY_ID_MSG = "Transport Journey Id is missing";
	
	public static final String IND_PIGLET_ERR_DUPLICATE_TATTOO_CODE = "801";
	
	public static final String IND_PIGLET_ERR_DUPLICATE_TATTOO_MSG = "Individual piglet duplicate tattoo Id";
	
	public static final String IND_PIGLET_ERR_WT_WEANING_CODE= "802";
	
	public static final String IND_PIGLET_ERR_WT_WEANING_MSG= "Individual piglet weight at weaning is missing";
	
	public static final String IND_PIGLET_ERR_WT_BIRTH_CODE = "803";
	
	public static final String IND_PIGLET_ERR_WT_BIRTH_MSG = "Individual piglet weight at birth is missing";
	
	public static final String IND_PIGLET_ERR_TATTOO_CODE = "804";
	
	public static final String IND_PIGLET_ERR_TATTOO_MSG = "Individual piglet tattoo is missing";
	
	public static final String IND_PIGLET_ERR_FARROW_DATE_CODE = "805";
	
	public static final String IND_PIGLET_ERR_FARROW_DATE_MSG = "Individual piglet farrow date is missing";
	
	public static final String IND_PIGLET_ERR_FARROW_EVENT_CODE = "806";
	
	public static final String IND_PIGLET_ERR_FARROW_EVENT_MSG = "Invalid farrow date specified.";
	
	public static final String IND_PIGLET_ERR_PIGLET_CNT_CODE = "807";
	
	public static final String IND_PIGLET_ERR_PIGLET_CNT_MSG = "Individual piglet count can not be more than live borns captured in farrow event";

    public static final String REF_DATA_BREEDINGSERIVCETYPE = "BREEDINGSERVICETYPE";	
    
    public static final String BREEDING_EVNT_DUPLICATE_SERVICEDT_CODE = "BE-204";
    
    public static final String BREEDING_EVNT_DUPLICATE_SERVICEDT_MSG = "An existing service record found with the same service date";
	
    public static final String BREEDING_EVNT_ERR_INCOMPLETE_CYCLE_CODE = "BE-203";
    
    public static final String BREEDING_EVNT_ERR_INCOMPLETE_CYCLE_MSG = "Incomplete service cycle identified for the pig Id.";
	
    public static final String BREEDING_EVNT_ERR_INVALID_SERVICETYPE_CODE = "BE-202";
    
    public static final String BREEDING_EVNT_ERR_INVALID_SERVICETYPE_MSG = "Invalid service type given";
    
    public static final String BREEDING_EVNT_ERR_INVALID_PEN_CODE = "810";
    
    public static final String BREEDING_EVNT_ERR_INVALID_PEN_MSG = "Invalid pen given";
    
    public static final String BREED_NG_EVNT_INVALID_PIGID_CODE = "810";
    
    public static final String BREED_NG_EVNT_INVALID_PIGID_MSG = "Invalid Pig Id for the company";
    
    public static final String MATING_ERR_INVALID_MATEQUALITY_CODE = "MD-201";
    
    public static final String MATING_ERR_INVALID_MATEQUALITY_MSG = "Invalid mating quality specified. Applicable values are - Good, OK and Poor";
    
    public static final String MATING_ERR_DUPLICATE_MATINGDATE_CODE = "MD-205";
    
    public static final String MATING_ERR_DUPLICATE_MATINGDATE_MSG = "Mating date given is same as that of current service date";
	
    public static final String MATING_ERR_EARLY_DATE_CODE = "MD-206";
    
    public static final String MATING_ERR_EARLY_DATE_MSG = "Mating date given is earlier than service date. Pregnancy record already added for the service event";
    
    public static final String MATING_ERR_NEXT_SERVICE_CODE = "MD-207";
    
    public static final String MATING_ERR_NEXT_SERVICE_MSG = "The duration of the mating date and the service start date is more than 130 days. This should be added as a new service record.";

    public static final String MATING_ERR_PREG_RECORD_CODE = "MD-208";
    
    public static final String MATING_ERR_PREG_RECORD_MSG = "Mating date is between 18-130 days. Pregnancy record already added for the service event";

    public static final String MATING_ERR_INVALID_WINDOW_CODE = "MD-209";
    
    public static final String MATING_ERR_INVALID_WINDOW_MSG = "Mating record added for invalid window of 6 - 18 days";
    
    public static final String MATING_ERR_INVALID_SERVICE_CODE = "MD-210";
    
    public static final String MATING_ERR_INVALID_SERVICE_MSG = "No matching service records found for the mating details";
    
    public static final String MATING_ERR_NO_SERVICE_RECORD_CODE = "MD-202";
    
    public static final String MATING_ERR_NO_SERVICE_RECORD_MSG = "No active breeding event record available for the given pig Id";
    
    public static final String INVALID_SOW_CONDITION_CODE="GEN-106";
    
    public static final String INVALID_SOW_CONDITION_MSG = "Invalid sow condition given. Value should be between 1-5";
    
    public static final String PIG_NOTA_SOW_CODE="BE-201";
    
    public static final String PIG_NOTA_SOW_MSG = "The given pig  is a boar";
    
    public static final String MATING_ERR_DATE_EARLIER_BIRTHDATE_CODE="MD-203";
    
    public static final String MATING_ERR_DATE_EARLIER_BIRTHDATE_MSG = "Mating date given is earlier than the birth date of the Pig";
    
    public static final String MATING_ERR_DATE_EARLIER_ENTRYDATE_CODE="MD-204";
    
    public static final String MATING_ERR_DATE_EARLIER_ENTRYDATE_MSG = "Mating date given is earlier than the entry date of the Pig";    
    
	public static final String REM_GROUP_PIG_ERR_BOTH_PRESENT_CODE = "1001";
	
	public static final String REM_GROUP_PIG_ERR_BOTH_PRESENT_MSG = "Removal/Transfer/Sales can have either GroupID or PigInfoId, not both Of them";
	
	public static final String REM_REM_EVENT_TYPE_PRESENT_CODE = "1002";
	
	public static final String REM_REM_EVENT_TYPE_PRESENT_MSG = "Removal Event Type missing";
	
	public static final String REM_COMPANY_PRESENT_CODE = "1003";
	
	public static final String REM_COMPANY_PRESENT_MSG = "Company missing";
	
	public static final String REM_NUMBER_OF_PIGS_PRESENT_CODE = "1004";
	
	public static final String REM_NUMBER_OF_PIGS_PRESENT_MSG = "Number Of Pigs are missing";
	
	public static final String REM_REMOVAL_DATE_PRESENT_CODE = "1005";
	
	public static final String REM_REMOVAL_DATE_PRESENT_MSG = "Removal Date is missing";
	
	public static final String REM_WEIGHT_IN_KG_PRESENT_PRESENT_CODE = "1006";
	
	public static final String REM_WEIGHT_IN_KG_PRESENT_PRESENT_MSG = "Weight in KG missing";
	
	public static final String REM_NUMBER_OF_PIGS_PRESENT_MORE_CODE = "1007";
	
	public static final String REM_NUMBER_OF_PIGS_PRESENT_MORE_MSG = "Number of Pigs can not be more then Group  in KG missing";
	
	public static final String REF_DATA_REMOVALEVENTTYPE = "REF_DATA_REMOVALEVENTTYPE";
	
	public static final String REF_DATA_GCOMPANY = "REF_DATA_GCOMPANY";
	
	public static final String REF_DATA_GLINE = "REF_DATA_GLINE";
	
	public static final String REF_DATA_SALETYPE = "REF_DATA_SALETYPE";
	
	public static final String REF_DATA_SALEREASON = "REF_DATA_SALEREASON";

	public static final String ENTRY_EVENT_INVALID_ORIGIN_CODE = "EE-206";
	
	public static final String ENTRY_EVENT_INVALID_ORIGIN_MSG = "Invalid genetic origin specified";
	
	public static final String ENTRY_EVENT_INVALID_GFUNC_CODE = "EE-207";
	
	public static final String ENTRY_EVENT_INVALID_GFUNC_MSG = "Invalid genetic function specified";
	
	public static final String ENTRY_EVENT_INVALID_GCOMP_CODE = "EE-208";
	
	public static final String ENTRY_EVENT_INVALID_GCOMP_MSG = "Invalid genetic company specified";
	
	public static final String ENTRY_EVENT_INVALID_GLINE_CODE = "EE-209";
	
	public static final String ENTRY_EVENT_INVALID_GLINE_MSG = "Invalid genetic line specified";
	
	public static final String ENTRY_EVENT_DUPLICATE_PIGID_CODE = "EE-202";
	
	public static final String ENTRY_EVENT_DUPLICATE_PIGID_MSG = "Pig already exists with the given pig id in the premise";
	
	public static final String INVALID_COMPANYID_CODE ="GEN-101";
	
	public static final String INVALID_PREMISEID_CODE ="GEN-102";

	public static final String INVALID_COMPANYID_MSG ="Invalid Company Id specified";
	
	public static final String INVALID_PREMISEID_MSG ="Invalid premise specified";
	
	public static final String ENTRY_EVENT_INVALID_SOWSOURCE_CODE ="612";
	
	public static final String ENTRY_EVENT_INVALID_SOWSOURCE_MSG ="Invalid sow source specified";
	
	public static final String FRW_EVNT_INVALID_FARROW_NUM = "FE-206";
	
	public static final String FRW_EVNT_INVALID_FARROW_NUM_MSG = "Invalid farrow count value";

	public static final String ERR_FARROW_DUPLICATE_CODE = "FE-212";
	
	public static final String ERR_FARROW_DUPLICATE_CODE_MSG = "Farrow entry already exists for the given service record";
	
	public static final String PIGLETSTATUS_INVALID_FARROWDATE_CODE = "615";
	
	public static final String PIGLETSTATUS_INVALID_FARROWDATE_MSG = "Invalid farrow date specified";

	public static final String PIGLETSTATUS_INVALID_TRANSFERDATE_CODE = "PSE-203";
	
	public static final String PIGLETSTATUS_INVALID_TRANSFERDATE_MSG = "Invalid transfer date specified. The date should be between 0-50 days of farrow event";
	
	public static final String PIGLETSTATUS_INVALID_WEAN_DATE_CODE = "PSE-201";
	
	public static final String PIGLETSTATUS_INVALID_WEAN_DATE_MSG = "Invalid wean date specified. The date should be between 0-60 days of farrow event";
	
	public static final String PIGLETSTATUS_INVALID_MORTALITY_DATE_CODE = "PSE-202";
	
	public static final String PIGLETSTATUS_INVALID_MORTALITY_DATE_MSG = "Invalid pre mortality date specified. The date should be between 0-50 days of farrow event";


	public static final String PIGLETSTATUS_INVALID_MORTALITY_CODE = "PSE-208";
	
	public static final String PIGLETSTATUS_INVALID_MORTALITY_MSG = "Invalid mortality reason specified";
	
	public static final String FEED_EVNT_ERR_FEEDCOST = "619";
	
	public static final String FEED_EVNT_ERR_FEEDCOST_MSG = "Invalid feed cost";
	
	public static final String FEED_EVNT_ERR_FEEDQTY = "620";
	
	public static final String FEED_EVNT_ERR_FEEDQTY_MSG = "Invalid feed quantity";
	
	public static final String ERR_FEED_DUPLICATE_TKTNUM = "621";
	
	public static final String ERR_FEED_DUPLICATE_TKTNUM_MSG = "Duplicate ticket number";
	
	public static final String ERR_PREGNANCY_EVENTRESULT_DATE_MSG = "Pregnancy result date is invalid";
	
	public static final String ERR_PREGNANCY_EVENTRESULT_DATE = "PE-203";
	
	public static final String ERR_PREGNANCY_NO_BREEDINGRECORDS = "623";
	
	public static final String ERR_PREGNANCY_NO_BREEDINGRECORDS_MSG = "No breeding records available for the pig ";
	
	public static final String REM_REMOVAL_REVENUE_CODE = "624";
	
	public static final String REM_REMOVAL_REVENUE_MSG = "Invalid revenue value specified";
	
	public static final String ERR_REMOVAL_MISSING_GRP_PIGID_CODE = "625";
	
	public static final String ERR_REMOVAL_MISSING_GRP_PIGID_MSG = "Both group event id and pig id are missing. Please specify any one of the value";
	
	public static final String REMOVAL_INVALID_GROUPID_CODE = "626";
		
	public static final String REMOVAL_INVALID_GROUPID_MSG = "Invalid group event id specified";
	
	public static final String REMOVAL_INVALID_PIGID_CODE = "627";
	
	public static final String REMOVAL_INVALID_PIGID_MSG = "Invalid pig id specified. Please specify an active pig id";
	
	public static final String REMOVAL_INVALID_MORTALITY_CODE = "628";
	
	public static final String REMOVAL_INVALID_MORTALITY_MSG = "Invalid mortality reason specified";	
	
	public static final String GROUP_EVENT_DUPLICATE_GRP_ID_CODE = "629";
	
	public static final String GROUP_EVENT_DUPLICATE_GRP_ID_MSG = "Group Id already present for the company";
	
	
	public static final String GROUP_EVENT_INVALID_ROOM_CODE ="630";
	
	public static final String GROUP_EVENT_INVALID_ROOM_MSG ="Invalid room specified";
	
	public static final String REF_DATA_FEEDEVENTTYPE = "REF_DATA_FEEDEVENTTYPE";
	
	public static final String FEED_EVENT_DETAIL_INVALID_ID_CODE = "631";
	
	public static final String FEED_EVENT_DETAIL_INVALID_ID_MSG = "No feed event record identified for the given ticket number";
	
	public static final String FEED_EVENT_DETAIL_INVALID_GRPID_CODE = "632";
	
	public static final String FEED_EVENT_DETAIL_INVALID_GRPID_MSG = "Invalid group event id specified";
	
	public static final String FEED_EVENT_DETAIL_INVALID_DATE_CODE = "633";
	
	public static final String FEED_EVENT_DETAIL_INVALID_DATE_MSG = "Invalid feed event date specified";
	
	public static final String FEED_EVENT_DETAIL_INVALID_EVT_TYPE_CODE = "634";
	
	public static final String FEED_EVENT_DETAIL_INVALID_EVT_TYPE_MSG = "Invalid feed event type specified";
	
	public static final String FEED_EVENT_DETAIL_INVALID_SILO_CODE = "635";
	
	public static final String FEED_EVENT_DETAIL_INVALID_SILO_MSG = "Invalid silo specified";

	public static final String IND_PIGLET_ERR_LITTERID_CODE = "636";
	
	public static final String IND_PIGLET_ERR_LITTERID_MSG = "Invalid litterId specified";
	
	public static final String ENTRY_EVENT_INVALID_ROOMIDS_CODE = "637";
	
	public static final String ENTRY_EVENT_INVALID_ROOMIDS_MSG = "Invalid room Ids specified";
	
	public static final String ENTRY_EVENT_INVALID_ROOMID_CODE = "EE-201";
	
	public static final String ENTRY_EVENT_INVALID_ROOMID_MSG = "Invalid room specified for the premise";
	
	public static final String ENTRY_EVENT_DUPLICATE_TATTOO_CODE = "EE-203";
	
	public static final String ENTRY_EVENT_DUPLICATE_TATTOO_MSG = "Pig already exists in the premise with the given tattoo";
	
	public static final String REM_REMOVAL_TRANSFER_DEST_PREMISES_NOT_PRESENT_CODE = "640";
	
	public static final String REM_REMOVAL_TRANSFER_DEST_PREMISES_NOT_PRESENT_MSG = "For Given Removal Event Transfer Destination Premises is required";
	
	public static final String REM_REMOVAL_TRANSFER_DEST_ROOM_NOT_PRESENT_CODE = "641";
	
	public static final String REM_REMOVAL_TRANSFER_DEST_ROOM_NOT_PRESENT_MSG = "For Given Removal Event Transfer For Group Transfer Room is required";
	
	public static final String REM_REMOVAL_TRANSFER_CURRENT_PREM_DEST_PREM_SAME_CODE = "642";
	
	public static final String REM_REMOVAL_TRANSFER_CURRENT_PREM_DEST_PREM_SAME_MSG = "For Given Removal Event Transfer Current and Destination Premises is same";
	
	public static final String REM_REMOVAL_TICKET_NUMBER_CODE = "643";
	
	public static final String REM_REMOVAL_TICKET_NUMBER_MSG = "Ticket Number is missing";
	
	public static final String GROUP_EVENT_INVALID_GROUPID_CODE = "644";
	
	public static final String GROUP_EVENT_INVALID_GROUPID_MSG = "Invalid Group Id specified";
	
	public static final String PREG_EVENT_INVALID_EVENT_TYPE_CODE = "PE-201";
	
	public static final String PREG_EVENT_INVALID_EVENT_TYPE_MSG = "Invalid pregnancy event type specified";
	
	public static final String PREG_EVENT_INVALID_RESULT_TYPE_CODE = "PE-204";
	
	public static final String PREG_EVENT_INVALID_RESULT_TYPE_MSG = "Invalid pregnancy exam result type specified";
	
	public static final String SALE_EVENT_TYPE_REVENUE_CODE = "1101";
	
	public static final String SALE_EVENT_TYPE_REVENUE_MSG = "Invalid Revenue Not able to parse.";
	
	public static final String REM_REMOVAL_TRANSFER_TO_GROUP_NOT_PRESENT_CODE = "1102";
	
	public static final String REM_REMOVAL_TRANSFER_TO_GROUP_NOT_PRESENT_MSG = "Given To Group not present";
	
	public static final String REM_REMOVAL_GROUP_PREMISES_TRANSFER_NOT_ALLOWED_CODE = "1103";
	
	public static final String REM_REMOVAL_GROUP_PREMISES_TRANSFER_NOT_ALLOWED_MSG = "Group premises transfer not allowed in mass upload";
	
	public static final String REM_REMOVAL_DATE_BEFORE_GROUP_START_CODE = "1103";
	
	public static final String REM_REMOVAL_DATE_BEFORE_GROUP_START_MSG = "Date specified is earlier than group start date";
	
	public static final String REM_REMOVAL_DATE_BEFORE_PIG_START_CODE = "1103";
	
	public static final String REM_REMOVAL_DATE_BEFORE_PIG_START_MSG = "Date specified is earlier than pig entry date";

	
}


