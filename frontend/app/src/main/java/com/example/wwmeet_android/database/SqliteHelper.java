package com.example.wwmeet_android.database;

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

}
