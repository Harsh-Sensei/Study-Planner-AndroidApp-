package com.example.studyplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, "EventsDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table EventDetails(event TEXT primary key, eventType TEXT, date TEXT, startTime TEXT, endTime TEXT,description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists EventDetails");
    }

    public Boolean insertData(String event, String date, String eventType, String startTime, String endTime, String description){
        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("event", event);
        contentValues.put("eventType", eventType);
        contentValues.put("date", date);
        contentValues.put("startTime", startTime);
        contentValues.put("endTime", endTime);
        contentValues.put("description", description);

        long result = DB.insert("EventDetails", null, contentValues);

        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

//    public Boolean updateData(String event, String date, String time, String description){
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("date", date);
//        contentValues.put("time", time);
//        contentValues.put("description", description);
//
//        Cursor cursor = DB.rawQuery("Select * from EventDetails where event = ?", new String[] {event});
//
//        if(cursor.getCount()==0)
//        {
//            return false;
//        }
//
//        long result = DB.update("EventDetails", contentValues, "event=?", new String[] {event});
//
//        return result != -1;
//    }

    public Boolean deleteData(String event){
        SQLiteDatabase DB = this.getWritableDatabase();


        Cursor cursor = DB.rawQuery("Select * from EventDetails where event = ?", new String[] {event});

        if(cursor.getCount()==0)
        {
            return false;
        }

        long result = DB.delete("EventDetails", "event=?", new String[] {event});

        return result != -1;
    }

    public Cursor getData(String event){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from EventDetails where event=?", new String[] {event});

        return cursor;
    }

    public SQLiteDatabase getDatabase()
    {
        return this.getWritableDatabase();
    }

}
