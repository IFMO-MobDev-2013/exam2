package com.alimantu.exam2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 21.01.14
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class PizzaDataBase {

    private static final String DB_NAME = "pizza_delivery";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "information";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "name";
    public static final String COLUMN_NUMBER = "number";

    public static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_TITLE + " text, " +
                    COLUMN_NUMBER + " text" +
                    ");";

    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase mDB;

    public PizzaDataBase(Context context) {
        this.context = context;
    }

    public void open() {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        mDB = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (dbHelper != null) dbHelper.close();
    }


    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public ArrayList<PizzaDelivery> getAll() {
        Cursor cursor = getAllData();
        ArrayList<PizzaDelivery> result = new ArrayList<PizzaDelivery>();
        while (cursor.moveToNext()) {
            PizzaDelivery current = new PizzaDelivery();
            current.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            current.setNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER)));
            result.add(current);
        }
        return result;
    }

    public void addChannel(String name, String number) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, name);
        cv.put(COLUMN_NUMBER, number);
        mDB.insert(DB_TABLE, null, cv);
    }

    public void deleteChannel(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

}
