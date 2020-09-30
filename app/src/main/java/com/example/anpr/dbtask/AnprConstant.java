package com.example.anpr.dbtask;

public class AnprConstant
{
    public static final String DB_NAME = "anprdb3";


    public static final String TABLE_NAME = "PoliceDetail";
    public static final String USER_NAME = "UserId";

    public static final String PASS = "Password";
    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String STREET="Street";
    public static final String CITY="City";
    public static final String STATE="State";
    public static final String PHONE = "Phone";
    public static final String AGE="Age";
    public static final String GENDER="Gender";


    public static final int DB_VERSION=1;
    public static final String POLICE_DETAIL_QUERY = "create table PoliceDetail(UserId text primary key not null,Password text not null,Name text not null,Email text not null,Street text not null ,City text not null,State text not null,Phone text not null,Age integer not null,Gender text not null)";

    public static final String TABLE_NAME2 = "Login";
    public static final String LOGIN_QUERY = "create table Login(UserId text primary key not null,Password text not null)";

    public static final String TABLE_NAME3 = "NumberTable";
    public static final String LICENSE_NO = "LicenseNo";
    public static final String DATE_TIME = "DateTime";


    public static final String STATUS = "Status";
    public static final String NUMBER_QUERY = "create table NumberTable(LicenseNo text primary key not null,Status text not null, DateTime text not null)";

}
