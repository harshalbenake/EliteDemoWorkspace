package com.elitedemoworkspace.DBUtil;

public class Contact {

    public int id;
    public String name;
    public String email;
    public String address;
    public String gender;
    public String mobile;

    public static String TABLENAME="Contacts";

    public static String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS "+TABLENAME+" ("
            + "_id INTEGER PRIMARY KEY, " //Don't remove this column.
            + "name VARCHAR default 1, "
            + "email VARCHAR default 1, "
            + "address VARCHAR default 1, "
            + "gender VARCHAR default 1, "
            + "mobile VARCHAR default 1 "
            +")";
}
