package com.alimantu.exam2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 21.01.14
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context, String dbName, Object o, int dbVersion) {
        super(context, dbName, (SQLiteDatabase.CursorFactory) o, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PizzaDataBase.DB_CREATE);
        sqLiteDatabase.execSQL(PizzaType.DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

}
