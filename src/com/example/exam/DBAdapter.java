package com.example.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    public static final String TABLE_ID = "_id";
    private static final String DATABASE_NAME = "pizza";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "pizza_table";
    public static final String NAME = "name";
    public static final String SPEED = "speed";
    public static final String TIME = "time";
    public static final String CUR = "cur";
    public static final String PHONE = "phone";
    public static final String PID = "pid";
    public static final String ADRESS = "adress";

    private static final String SQL_CREATE_ENTRIES = "create table "
            + TABLE_NAME + " ("
            + TABLE_ID + " integer primary key autoincrement, "
            + NAME + " text not null, "
            + SPEED + " text not null, "
            + TIME + " text not null, "
            + CUR + " integer not null, "
            + PID + " text not null, "
            + ADRESS + " text not null, "
            + PHONE + " text not null ); ";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    private final Context mcontext;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public DBAdapter(Context context) {
        this.mcontext = context;
        DBHelper = new DatabaseHelper(mcontext);
        db = DBHelper.getWritableDatabase();
    }


    public void insert(String name, String speed, String time, int cur, String phone, String adress) {
        ContentValues cv =  new ContentValues();
        cv.put(NAME, name); cv.put(SPEED, speed);
        cv.put(TIME, time); cv.put(CUR, cur); cv.put(ADRESS, adress);
        cv.put(PHONE, phone); cv.put(PID, Long.toString(System.currentTimeMillis()));
        db.insert(TABLE_NAME, null, cv);
    }

    public Cursor getAllData() {
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
    public Pizza[] getAllPizza() {
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        int size = -1;
        Pizza[] res = null;
        while (cursor.moveToNext()) {
            if (size == -1) res = new Pizza[cursor.getCount()];
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String time = cursor.getString(cursor.getColumnIndex(TIME));
            String speed = cursor.getString(cursor.getColumnIndex(SPEED));
            String phone = cursor.getString(cursor.getColumnIndex(PHONE));
            String pid = cursor.getString(cursor.getColumnIndex(PID));
            String adress = cursor.getString(cursor.getColumnIndex(ADRESS));
            int cur = cursor.getInt(cursor.getColumnIndex(CUR));
            size++;
            res[size] = new Pizza(name, phone, speed, time, cur, pid, adress);
        }
        return res;
    }
    public void deletePizza(String pid) {
        db.delete(TABLE_NAME, PID + " = ?", new String[]{pid});
    }

    public void create() {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public Pizza getPizza(String pid) {
        Cursor cursor = db.query(TABLE_NAME, null, PID + " = ?", new String[]{pid}, null, null, null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex(NAME));
        String time = cursor.getString(cursor.getColumnIndex(TIME));
        String speed = cursor.getString(cursor.getColumnIndex(SPEED));
        String phone = cursor.getString(cursor.getColumnIndex(PHONE));
        String adress = cursor.getString(cursor.getColumnIndex(ADRESS));
        int cur = cursor.getInt(cursor.getColumnIndex(CUR));
        String id = cursor.getString(cursor.getColumnIndex(PID));
        return new Pizza(name, phone, speed, time, cur, id, adress);
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
