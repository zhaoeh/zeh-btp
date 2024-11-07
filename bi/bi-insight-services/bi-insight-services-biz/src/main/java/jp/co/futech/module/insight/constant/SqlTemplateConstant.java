package jp.co.futech.module.insight.constant;

/**
 * @author tao.ma@futech.co.jp
 * @date 2024/4/10
 */
public class SqlTemplateConstant {


    public static final String TAB = " ";
    public static final String GE = " >= ";
    public static final String LE = " <= ";
    public static final String EQ = " = ";
    public static final String SEMICOLON = ";";
    public static final String UNDER_LINE = "_";
    public static final String UNDER_2LINE = "__";

    public static final String FROM = " from ";
    public static final String DELETE = "delete";

    public static final String INSERT_INTO = "insert into";

    public static final String WHERE = " where 1 = 1 and ";
    public static final String AND = "and";
    public static final String OR = "or";
    public static final String AS = "as";
    public static final String TO_DATE = "";
    public static final String TAB_T_CUSTOMERS_ALL = "t_customers_all";
    public static final String LOGIN_NAME = "login_name";
    public static final String USER_ID = "user_id";
    public static final String DATE_TIME = "date_time";
    public static final String DT = "dt";
    public static final String AIRFLOW_START_TIME = "DATE_ADD('[exec_date]', INTERVAL <minus_dt> DAY)";
    public static final String AIRFLOW_END_TIME = "STR_TO_DATE('[exec_date]', '%Y-%m-%d')";
    public static final String ADS = "ads_";
    public static final String CREATE_TAB = "create table ";

}
