package com.example.exam2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: kris13
 * Date: 14.01.14
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public class DbWash extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "pizza";

    public static final String TABLE_NAME = "washing";
    public static final String NAMEWASH = "name";
    public static final String NUMBERBOX = "box";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( _id integer primary key autoincrement, "
            + NAMEWASH + " TEXT, " + NUMBERBOX + " INTEGER)";

    public DbWash(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(dropDataBase());
            onCreate(db);
        }
    }

    String dropDataBase() {
        return "DROP TABLE IF EXISTS " + DB_NAME;
    }
}
