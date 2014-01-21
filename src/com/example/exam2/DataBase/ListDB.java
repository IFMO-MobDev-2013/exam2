package com.example.exam2.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 21.01.14
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class ListDB {


    private static final String DB_NAME = "carsbase";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "cars_table";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_TIMETODELIVERY = "time";
    public static final String COLUMN_STARTTIME = "starttime";

    public static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text, " +  COLUMN_PHONE + " text, " + COLUMN_TIMETODELIVERY + " text, "
                    + COLUMN_STARTTIME + " text" + ");";

    private final Context context;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public ListDB(Context context) {
        this.context = context;
    }

    public void open() {
        mDBHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }


    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public Cursor getChannelData(long channel) {
        return mDB.query(DB_TABLE, null, COLUMN_NAME + " = " + channel, null, null, null, COLUMN_STARTTIME + " DESC");
    }

    public void addChannel(String name, String phone, String timetodelivery, String starttime) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_TIMETODELIVERY, timetodelivery);
        cv.put(COLUMN_STARTTIME, starttime);


        mDB.insert(DB_TABLE, null, cv);
    }

    public void deleteChannel(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

}