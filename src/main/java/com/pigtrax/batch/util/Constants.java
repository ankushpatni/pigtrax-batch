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

	public static final String ERR_PREGNANCY_EVENT_DUPLICATE_MSG = "An entry already exist with the given combination of (Pig Id, Company Id, service Date)";

	public static final String ERR_PREGNANCY_EVENT_DATE_CODE = "702";

	public static final String ERR_PREGNANCY_EVENT_DATE_MSG = "Pregnancy exam and result dates should be between 18 to 60 days from service date";

	public static final String ERR_ABORTION_DATE_CODE = "703";

	public static final String ERR_ABORTION_DATE_MSG = "Abortion date should be between 18 to 110 days from service date";

	public static final String ERR_NOT_IN_PIG_DATE_CODE = "704";

	public static final String ERR_NOT_IN_PIG_DATE_MSG = "Not in pig date should be between 105 to 125 days from service date";

	public static final String ERR_PREG_EVENT_SERVICE_NOT_FOUND_CODE = "705";

	public static final String ERR_PREG_EVENT_SERVICE_NOT_FOUND_MSG = "Matching Service record not found for the pig";

	public static final String ERR_FOSTER_PIG_NOT_FOUND_CODE = "801";

	public static final String ERR_FOSTER_PIG_NOT_FOUND_MSG = "Transferred to Pig is not found";

	public static final String ERR_FOSTER_FARROW_NOT_FOUND_CODE = "802";

	public static final String ERR_FOSTER_FARROW_NOT_FOUND_MSG = "No valid farrow event associated with transferred to Pig";

	public static final String ERR_WEAN_GROUPID_NOT_FOUND_CODE = "803";

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

	public static final String FRW_EVNT_ERR_PRG_TEST = "606";

	public static final String FRW_EVNT_ERR_PRG_TEST_MSG = "No positive pregnancy records found for the given result date";

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
	
	public static final String FRW_EVNT_INVALID_LITTER_WEIGHT = "613";

	public static final String FRW_EVNT_INVALID_LITTER_WEIGHT_MSG = "Invalid Litter Weight specified";


	public static final String FRW_EVNT_ERR_BIRTH_TYPE = "614";

	public static final String FRW_EVNT_ERR_BIRTH_TYPE_MSG = "Type Of Birth is missing";
	
	public static final String FRW_EVNT_INVALID_BIRTH_TYPE = "614";

	public static final String FRW_EVNT_INVALID_BIRTH_TYPE_MSG = "Invalid birth type specified. Please give Assisted/Induced";

	public static final String FRW_EVNT_ERR_EMP_GRP = "615";

	public static final String FRW_EVNT_ERR_EMP_GRP_MSG = "Invalid employee group specified";

	public static final String FRW_EVNT_ERR_TEATS = "616";

	public static final String FRW_EVNT_ERR_EMP_TEATS_MSG = "Teats is missing";

	public static final String FRW_EVNT_ERR_TEATS_VAL = "617";

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
	
    public static final String BREEDING_EVNT_ERR_INCOMPLETE_CYCLE_CODE = "808";
    
    public static final String BREEDING_EVNT_ERR_INCOMPLETE_CYCLE_MSG = "Incomplete service cycle identified for the pig Id.";
	
    public static final String BREEDING_EVNT_ERR_INVALID_SERVICETYPE_CODE = "809";
    
    public static final String BREEDING_EVNT_ERR_INVALID_SERVICETYPE_MSG = "Invalid service type given";
    
    public static final String BREEDING_EVNT_ERR_INVALID_PEN_CODE = "810";
    
    public static final String BREEDING_EVNT_ERR_INVALID_PEN_MSG = "Invalid pen given";
    
    public static final String BREED_NG_EVNT_INVALID_PIGID_CODE = "810";
    
    public static final String BREED_NG_EVNT_INVALID_PIGID_MSG = "Invalid Pig Id for the company";
    
    public static final String BREED_NG_EVNT_INVALID_COMPANY_CODE = "811";
    
    public static final String BREED_NG_EVNT_INVALID_COMPANY_MSG = "Invalid company id";
    
    public static final String MATING_ERR_INVALID_MATEQUALITY_CODE = "812";
    
    public static final String MATING_ERR_INVALID_MATEQUALITY_MSG = "Invalid mate quality given";
    
    public static final String MATING_ERR_DUPLICATE_MATINGDATE_CODE = "813";
    
    public static final String MATING_ERR_DUPLICATE_MATINGDATE_MSG = "Mating date given is same as that of current service date";
	
    public static final String MATING_ERR_EARLY_DATE_CODE = "814";
    
    public static final String MATING_ERR_EARLY_DATE_MSG = "Mating date given is earlier than service date. Pregnancy record already added for the service event";
    
    public static final String MATING_ERR_NEXT_SERVICE_CODE = "815";
    
    public static final String MATING_ERR_NEXT_SERVICE_MSG = "Duration is more than 130 days. It should be added as a new service record";

    public static final String MATING_ERR_PREG_RECORD_CODE = "816";
    
    public static final String MATING_ERR_PREG_RECORD_MSG = "Pregnancy record already added for the service event";

    public static final String MATING_ERR_INVALID_WINDOW_CODE = "817";
    
    public static final String MATING_ERR_INVALID_WINDOW_MSG = "Mating record added for invalid window of 19 - 60 days";
    
    public static final String MATING_ERR_NO_SERVICE_RECORD_CODE = "818";
    
    public static final String MATING_ERR_NO_SERVICE_RECORD_MSG = "No active breeding event record available for the given pig Id";
    
    public static final String BREED_EVNT_INVALID_SOW_CONDITION_CODE="819";
    
    public static final String BREED_EVNT_INVALID_SOW_CONDITION_MSG = "Invalid sow condition given. Value should be between 1-5";
    
    public static final String BREED_EVNT_PIG_NOTA_SOW_CODE="820";
    
    public static final String BREED_EVNT_PIG_NOTA_SOW_MSG = "The given pig  is a boar";
    
    public static final String MATING_ERR_DATE_EARLIER_BIRTHDATE_CODE="821";
    
    public static final String MATING_ERR_DATE_EARLIER_BIRTHDATE_MSG = "Mating date given is earlier than birth date";
    
    public static final String MATING_ERR_DATE_EARLIER_ENTRYDATE_CODE="822";
    
    public static final String MATING_ERR_DATE_EARLIER_ENTRYDATE_MSG = "Mating date given is earlier than entry date";    
    
	public static final String REM_GROUP_PIG_ERR_BOTH_PRESENT_CODE = "1001";
	
	public static final String REM_GROUP_PIG_ERR_BOTH_PRESENT_MSG = "Removal can have either GroupID or PigInfoId Not Both Of them";
	
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

	public static final String ENTRY_EVENT_INVALID_ORIGIN_CODE = "610";
	
	public static final String ENTRY_EVENT_INVALID_ORIGIN_MSG = "Invalid origin specified";
	
	public static final String ENTRY_EVENT_DUPLICATE_PIGID_CODE = "611";
	
	public static final String ENTRY_EVENT_DUPLICATE_PIGID_MSG = "Pig Id given already exists in the company";
	
	public static final String ENTRY_EVENT_INVALID_COMPANYID_CODE ="612";

	public static final String ENTRY_EVENT_INVALID_COMPANYID_MSG ="Invalid company id specified";
	
	public static final String FRW_EVNT_INVALID_FARROW_NUM = "613";
	
	public static final String FRW_EVNT_INVALID_FARROW_NUM_MSG = "Invalid count value";

	public static final String ERR_FARROW_DUPLICATE_CODE = "614";
	
	public static final String ERR_FARROW_DUPLICATE_CODE_MSG = "Farrow entry already exists for the given service record";
	
	public static final String PIGLETSTATUS_INVALID_FARROWDATE_CODE = "615";
	
	public static final String PIGLETSTATUS_INVALID_FARROWDATE_MSG = "Invalid farrow date specified";

	public static final String PIGLETSTATUS_INVALID_TRANSFERDATE_CODE = "616";
	
	public static final String PIGLETSTATUS_INVALID_TRANSFERDATE_MSG = "Invalid transfer date specified. The date should be between 0-50 days of farrow event";
	
	public static final String PIGLETSTATUS_INVALID_WEAN_DATE_CODE = "617";
	
	public static final String PIGLETSTATUS_INVALID_WEAN_DATE_MSG = "Invalid wean date specified. The date should be between 0-60 days of farrow event";

	public static final String PIGLETSTATUS_INVALID_MORTALITY_CODE = "618";
	
	public static final String PIGLETSTATUS_INVALID_MORTALITY_MSG = "Invalid mortality reason specified";
	
	public static final String FEED_EVNT_ERR_FEEDCOST = "619";
	
	public static final String FEED_EVNT_ERR_FEEDCOST_MSG = "Invalid feed cost";
	
	public static final String FEED_EVNT_ERR_FEEDQTY = "620";
	
	public static final String FEED_EVNT_ERR_FEEDQTY_MSG = "Invalid feed quantity";
	
	public static final String ERR_FEED_DUPLICATE_TKTNUM = "621";
	
	public static final String ERR_FEED_DUPLICATE_TKTNUM_MSG = "Duplicate ticket number";
	
	public static final String ERR_PREGNANCY_EVENTRESULT_DATE_MSG = "Pregnancy result date is missing";
	
	public static final String ERR_PREGNANCY_EVENTRESULT_DATE = "622";
	
	public static final String ERR_PREGNANCY_NO_BREEDINGRECORDS = "623";
	
	public static final String ERR_PREGNANCY_NO_BREEDINGRECORDS_MSG = "No breeding records available for the pig ";
}

