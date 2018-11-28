package com.elitedemoworkspace.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME="EliteTechnologies.db";
    static int DARABASE_VERSION=1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DARABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, DATABASE_NAME, factory, DARABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contact.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public void restoreDatabase() {
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " +Contact.TABLENAME);
        database.execSQL(Contact.CREATE_TABLE_QUERY);
        database.close();
    }
}
