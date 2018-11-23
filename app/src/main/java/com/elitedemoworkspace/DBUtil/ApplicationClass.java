package com.elitedemoworkspace.DBUtil;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;


/**
 * This is application class is used to declare global variable at application level.
 *
 */
public class ApplicationClass extends Application{

    private SQLiteDatabase mSqLiteDatabase;

    @Override
    public void onCreate() {
        DBHelper dbHelper=new DBHelper(getApplicationContext());
        mSqLiteDatabase=dbHelper.getWritableDatabase();
        super.onCreate();
    }


    /**
     * This method is used to get database object.
     * @return SQLiteDatabase
     */
    public SQLiteDatabase getReadableDatabase() {
        if(mSqLiteDatabase==null || mSqLiteDatabase.isOpen()==false){
            DBHelper dbHelper=new DBHelper(getApplicationContext());
            mSqLiteDatabase=dbHelper.getWritableDatabase();
        }

        return mSqLiteDatabase;
    }

    /**
     * This method is used to get database object.
     * @return SQLiteDatabase
     */
    public SQLiteDatabase getWritableDatabase() {
        if(mSqLiteDatabase==null|| mSqLiteDatabase.isOpen()==false){
            DBHelper dbHelper=new DBHelper(getApplicationContext());
            mSqLiteDatabase=dbHelper.getWritableDatabase();
        }
        return mSqLiteDatabase;
    }
}