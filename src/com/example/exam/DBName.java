package com.example.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBName {

    public static final String TABLE_ID = "_id";
    private static final String DATABASE_NAME = "pizzeria";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "pizzeria_table";
    public static final String PIZZA = "pizza";
    public static final String CURCOUNT = "curcount";


    private static final String SQL_CREATE_ENTRIES = "create table "
            + TABLE_NAME + " ("
            + TABLE_ID + " integer primary key autoincrement, "
            + PIZZA + " text not null, "
            + CURCOUNT + " integer not null ); ";


    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    private final Context mcontext;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public DBName(Context context) {
        this.mcontext = context;
        DBHelper = new DatabaseHelper(mcontext);
        db = DBHelper.getWritableDatabase();
    }



    public Cursor getAllData() {
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
    public void insertName(String name, String curcount) {
        int bc = Integer.parseInt(curcount);
        ContentValues cv = new ContentValues();
        cv.put(PIZZA, name); cv.put(CURCOUNT, bc);
        db.insert(TABLE_NAME, null, cv);
    }
    public String getName() {
        Cursor cursor = getAllData();
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(PIZZA));
    }
    public int getCount() {
        Cursor cursor = getAllData();
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(CURCOUNT));
    }
    public boolean checkName() {
        Cursor cursor = getAllData();
        if (cursor.getCount() > 0) return true;
        return false;
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
