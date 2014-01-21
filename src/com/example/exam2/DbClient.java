package com.example.exam2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: kris13
 * Date: 14.01.14
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public class DbClient  extends SQLiteOpenHelper {

    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "clients";

    public static final String TABLE_NAME = "clients";
    public static final String PIZZA = "pizza";
    public static final String TELEPHONE = "telephone";
    public static final String PLACE = "place";
    public static final String COURIER = "courier";
    public static final String DELIVERY = "dil";
    public static final String TIME = "time";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( _id integer primary key autoincrement, "
            + PIZZA + " TEXT, " + TELEPHONE + " TEXT, " + PLACE + " TEXT, " + DELIVERY + " TEXT, " + TIME + " TEXT, " + COURIER + " TEXT)";

    public DbClient(Context context) {
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