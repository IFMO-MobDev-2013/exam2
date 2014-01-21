package com.example.exam2.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 21.01.14
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class NameDB {
    private static final String DB_NAME = "namebase";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "name_table";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BOX = "box";

    public static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_BOX + " text" +
                    ");";

    private final Context context;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public NameDB(Context context) {
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

    public ArrayList<Name> getAll() {
        Cursor cursor = getAllData();
        ArrayList<Name> result = new ArrayList<Name>();
        while (cursor.moveToNext()) {
            Name current = new Name();
            current.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            current.setNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_BOX)));
            result.add(current);
        }
        return result;
    }


    public void addChannel(String name, String box) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_BOX, box);
        mDB.insert(DB_TABLE, null, cv);
    }

    public void deleteChannel(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }



}
