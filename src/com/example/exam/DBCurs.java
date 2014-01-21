package com.example.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;

public class DBCurs {

    public static final String TABLE_ID = "_id";
    private static final String DATABASE_NAME = "cur";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "curs_table";
    public static final String CUR = "cur";
    public static final String TIME = "time";

    private static final String SQL_CREATE_ENTRIES = "create table "
            + TABLE_NAME + " ("
            + TABLE_ID + " integer primary key autoincrement, "
            + CUR + " text not null, "
            + TIME + " text not null ); ";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    private final Context mcontext;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public DBCurs(Context context) {
        this.mcontext = context;
        DBHelper = new DatabaseHelper(mcontext);
        db = DBHelper.getWritableDatabase();
    }


    public void insert(String cur, String time) {
        ContentValues cv;
            cv = new ContentValues();
            cv.put(CUR, cur); cv.put(TIME, time);
            db.insert(TABLE_NAME, null, cv);
    }

    public Cursor getAllData() {
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public Time[] getTimes(int llong, String[] times, int curCount) {
        Time[] res = new Time[28];
        for (int i = 0; i < res.length; i++) { res[i] = new Time("0", 0);}
        Cursor cursor;
        for (int i = 0; i < times.length; ++i) {
            res[i].time = times[i];
            if (i + llong > times.length) break;
            for (int j = 1; j <= curCount; j++) {
                for (int q = 0; q < llong; q++) {
                    cursor = db.query(TABLE_NAME, null, TIME + " = ? and " + CUR + " = ?", new String[]{times[i + q], Integer.toString(j)}, null, null, null);
                    cursor.moveToFirst();
                    if (cursor.getCount() > 0) break;
                    if (q == llong - 1) {
                        res[i].cur = j;
                    }
                }
            }
        }
        return res;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
