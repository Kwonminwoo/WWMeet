package com.example.wwmeet_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.lights.Light;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.wwmeet_android.domain.MyAppointment;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {
    private final String TABLE_NAME = "my_appointment";
    public SqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("create", "asdas");
        String initSql = "create table if not exists " + TABLE_NAME + "("+
                "_id integer primary key autoincrement, " +
                "appointmentId integer, " +
                "name text)";

        sqLiteDatabase.execSQL(initSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table if exists " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void saveMyAppointment(MyAppointment myAppointment){
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("appointmentId", myAppointment.getAppointmentId());
        cv.put("name", myAppointment.getName());
        Log.e(myAppointment.getName(), myAppointment.getAppointmentId() + "");

        writableDatabase.insert(TABLE_NAME, null, cv);
    }

    public List<MyAppointment> findAllMyAppointment(){
        List<MyAppointment> resultList = new ArrayList<>();
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            MyAppointment myAppointment = new MyAppointment(cursor.getLong(1),  cursor.getString(2));
            resultList.add(myAppointment);
        }
        return resultList;
    }

    public void clearAllData(){
        this.getWritableDatabase().execSQL("delete from " + TABLE_NAME);
    }
}
