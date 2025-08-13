package pl.lodz.p.it.functionalfood.investigator.config.database;

public class DatabaseStructures {

    //ABSTRACT ENTITY
    public static final String ID = "ID";
    public static final String VERSION = "VERSION";

    //ACCOUNT TABLE
    public static final String ACCOUNTS_TABLE = "ACCOUNTS";
    public static final String ACCOUNTS_TABLE_LOGIN_COLUMN = "LOGIN";
    public static final String ACCOUNTS_TABLE_PASSWORD_COLUMN = "PASSWORD";
    public static final String ACCOUNTS_TABLE_ACTIVE_COLUMN = "ACTIVE";
    public static final String ACCOUNTS_TABLE_CREATION_DATE = "CREATION_TIME";

    //ACCESS LEVEL TABLE
    public static final String ACCESS_LEVEL_TABLE = "ACCOUNT_ACCESS_LEVELS";
    public static final String ACCESS_LEVEL_TABLE_ACCOUNT_ID_COLUMN = "ACCOUNT_ID";
    public static final String ACCESS_LEVEL_TABLE_LEVEL_COLUMN = "LEVEL";

    //CLIENT DATA TABLE
    public static final String CLIENT_ACCESS_LEVEL_TABLE = "CLIENT_DATA";

    //EXPERT DATA TABLE
    public static final String EXPERT_ACCESS_LEVEL_TABLE = "EXPERT_DATA";

    //ADMIN DATA TABLE
    public static final String ADMIN_ACCESS_LEVEL_TABLE = "ADMIN_DATA";

    //EMPLOYEE DATA TABLE
    public static final String EMPLOYEE_ACCESS_LEVEL_TABLE = "EMPLOYEE_DATA";

    //PERSONAL DATA TABLE
    public static final String PERSONAL_DATA_TABLE = "PERSONAL_DATA";
    public static final String PERSONAL_DATA_FIRST_NAME = "FIRST_NAME";
    public static final String PERSONAL_DATA_LAST_NAME = "LAST_NAME";
    public static final String PERSONAL_DATA_EMAIL = "EMAIL";
    public static final String PERSONAL_DATA_LANGUAGE = "LANGUAGE";

    //VERIFICATION TOKEN TABLE
    public static final String VERIFICATION_TOKEN_TABLE = "VERIFICATION_TOKENS";
    public static final String VERIFICATION_TOKEN_VALUE = "VALUE";
    public static final String VERIFICATION_TOKEN_ACCOUNT_ID = "ACCOUNT_ID";
    public static final String VERIFICATION_TOKEN_EXPIRATION_DATE = "EXPIRATION_DATE";
    public static final String VERIFICATION_TOKEN_IS_ACTIVE = "IS_ACTIVE";
    public static final String VERIFICATION_TOKEN_WAS_RESENT = "WAS_RESENT";
    public static final String VERIFICATION_TOKEN_CREATION_DATE = "CREATION_TIME";

    //AUTHENTICATION TOKEN TABLE
    public static final String AUTHENTICATION_TOKEN_TABLE = "AUTHENTICATION_TOKENS";
    public static final String AUTHENTICATION_TOKEN_VALUE  = "VALUE";
    public static final String AUTHENTICATION_TOKEN_ACCOUNT_ID = "ACCOUNT_ID";
    public static final String AUTHENTICATION_TOKEN_EXPIRATION_DATE = "EXPIRATION_DATE";
    public static final String AUTHENTICATION_TOKEN_IS_ACTIVE = "ACTIVE";

    //PRODUCERS TABLE
    public static final String PRODUCERS_TABLE = "PRODUCER";

    //UNITS TABLE
    public static final String UNITS_TABLE = "UNITS";

    //PACKAGE_TYPES TABLE
    public static final String PACKAGE_TYPES_TABLE = "PACKAGE_TYPE";

    //COMPOSITION TABLE
    public static final String COMPOSITION_TABLE = "COMPOSITION";

    //ADDITION TABLE
    public static final String ADDITION_TABLE = "ADDITION";

    //NUTRITIONAL_INDEXES TABLE
    public static final String NUTRITIONAL_INDEXES_TABLE = "NUTRITIONAL_INDEX";

    //NUTRITIONAL_VALUE_GROUPS TABLE
    public static final String NUTRITIONAL_VALUE_GROUPS_TABLE = "NUTRITIONAL_VALUE_GROUP";

    //NUTRITIONAL_VALUE_NAME TABLE
    public static final String NUTRITIONAL_VALUE_NAME_TABLE = "NUTRITIONAL_VALUE_NAME";


}
