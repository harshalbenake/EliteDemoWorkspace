package com.elitedemoworkspace.dbutil;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * This is a manager class used to manage database related information.
 *
 */
public class DBManager {
    Context mContext;

    public DBManager(Context mContext){
        this.mContext=mContext;
    }

    /**
     * This method is used to add data info into DataBase.
     * @param contact
     */
    public void addData(Contact contact){
        try{
            ApplicationClass applicationClass=(ApplicationClass)mContext.getApplicationContext();
            SQLiteDatabase sqLiteDatabase=applicationClass.getWritableDatabase();

            ContentValues contentValues=new ContentValues();
            contentValues.put("name", contact.name);
            contentValues.put("email", contact.email);
            contentValues.put("address", contact.address);
            contentValues.put("gender", contact.gender);
            contentValues.put("mobile", contact.mobile);
            sqLiteDatabase.insert(Contact.TABLENAME, null, contentValues);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get data info into DataBase.
     * @return
     */
    public List<Contact> getData(){
        List<Contact> list = new ArrayList<Contact>();
        String selectQuery = " SELECT * FROM "+Contact.TABLENAME;
        ApplicationClass applicationClass=(ApplicationClass)mContext.getApplicationContext();
        SQLiteDatabase sqLiteDatabase = applicationClass.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact=new Contact();
                contact.name=cursor.getString(cursor.getColumnIndex("name"));
                contact.email=cursor.getString(cursor.getColumnIndex("email"));
                contact.address=cursor.getString(cursor.getColumnIndex("address"));
                contact.gender=cursor.getString(cursor.getColumnIndex("gender"));
                contact.mobile=cursor.getString(cursor.getColumnIndex("mobile"));
                list.add(contact);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        // returning lables
        return list;
    }
}