package com.example.wwmeet_android.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wwmeet_android.database.SqliteHelper;
import com.example.wwmeet_android.domain.MyAppointment;

import java.util.ArrayList;
import java.util.List;

public class LocalDatabaseUtil {
    private final SqliteHelper database;

    private final String DATABASE_NAME = "appointmentDB";
    private final String TABLE_NAME = "my_appointment";
    private final int version = 1;
    public LocalDatabaseUtil(Context context) {
        database = new SqliteHelper(context, DATABASE_NAME, null, version);
    }

    public List<MyAppointment> findAllMyAppointment(){
        List<MyAppointment> resultList = new ArrayList<>();
        SQLiteDatabase readableDatabase = database.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            MyAppointment myAppointment = new MyAppointment(cursor.getLong(1),  cursor.getString(2));
            resultList.add(myAppointment);
        }
        return resultList;
    }

    public void saveMyAppointment(MyAppointment myAppointment){
        SQLiteDatabase writableDatabase = database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("appointmentId", myAppointment.getAppointmentId());
        cv.put("name", myAppointment.getName());
        Log.e(myAppointment.getName(), myAppointment.getAppointmentId() + "");

        writableDatabase.insert(TABLE_NAME, null, cv);
    }
}
