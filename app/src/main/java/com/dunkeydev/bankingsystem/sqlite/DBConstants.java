package com.dunkeydev.bankingsystem.sqlite;

import android.provider.BaseColumns;

public class DBConstants implements BaseColumns {

    public static final String TABLE_NAME_BANK = "basicbank_customers";
    public static final String TABLE_NAME_TRANSCTIONS = "basicbank_transctions";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_BALANCE = "balance";


    public static final String COLUMN_ID_T = "id";
    public static final String COLUMN_SENDER = "sender";
    public static final String COLUMN_BALANCE_T = "balance";
    public static final String COLUMN_RECEIVER = "receiver";


    public static final String SQL_CREATE_BANK_ENTRIES =
            "CREATE TABLE " + TABLE_NAME_BANK +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_BALANCE + " INTEGER" + ");";

    public static final String SQL_DELETE_BANK_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME_BANK;

    public static final String SQL_CREATE_TRANSCTIONS_ENTRIES =
            "CREATE TABLE " + TABLE_NAME_TRANSCTIONS +
                    " (" + COLUMN_ID_T + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_SENDER + " TEXT, " +
                    COLUMN_RECEIVER + " TEXT, " +
                    COLUMN_BALANCE_T + " INTEGER" + ");";

    public static final String SQL_DELETE_TRANSCTIONS_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME_TRANSCTIONS;


}
