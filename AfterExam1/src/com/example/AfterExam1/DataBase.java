package com.example.AfterExam1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase {
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private DatabaseHelper databaseHelper;
    private Cursor cursor;

    public static final String DATABASE_NAME = "exam.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "tasty_pizza";
    public static final String KEY_ID = "_id";

    public static final String KEY_PIZZA_NAME = "pizza_name";
    public static final String KEY_TELEPHONE_NUMBER = "telephone_number";
    public static final String KEY_TIME_TO_CLIENT = "time_to_client";
    public static final String KEY_PIZZA_PREFER_TIME = "prefer_time";
    public static final String KEY_CURIER = "curier_number";

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
            + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_PIZZA_NAME + " TEXT NOT NULL, "
            + KEY_TELEPHONE_NUMBER+ " TEXT NOT NULL, "
            + KEY_TIME_TO_CLIENT + " TEXT NOT NULL, "
            + KEY_PIZZA_PREFER_TIME + " TEXT NOT NULL, "
            + KEY_CURIER + " TEXT NOT NULL);";

    public DataBase(Context context) {
        this.context = context;
    }

    public DataBase open() throws SQLiteException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public long insertAutoWashParameters(String name, String curierNumber) {
        ContentValues values = new ContentValues();
        values.put(KEY_PIZZA_NAME, name);
        values.put(KEY_TELEPHONE_NUMBER, "null");
        values.put(KEY_TIME_TO_CLIENT, "null");
        values.put(KEY_PIZZA_PREFER_TIME, "null");
        values.put(KEY_CURIER, curierNumber);
        return sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    public long insertClient(String pizzaName, String telephoneNumber, String timeToClient, String pizzaPreferTime, String curier) {
        ContentValues values = new ContentValues();
        values.put(KEY_PIZZA_NAME, pizzaName);
        values.put(KEY_TELEPHONE_NUMBER, telephoneNumber);
        values.put(KEY_TIME_TO_CLIENT, timeToClient);
        values.put(KEY_PIZZA_PREFER_TIME, pizzaPreferTime);
        values.put(KEY_CURIER, curier);
        return sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    public String getAutoWashName() {
        newCursor();
        cursor.moveToNext();
        return cursor.getString(cursor.getColumnIndex(KEY_PIZZA_NAME));
    }

    public int getAutoWashBoxNumber() {
        newCursor();
        cursor.moveToNext();
        return Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_CURIER)));
    }


    public Cursor getCursorStartData() {
        newCursor();
        cursor.moveToNext();
        return cursor;
    }

    public boolean isEmpty() {
        newCursor();
        if (cursor.moveToNext())
            return false;
        else
            return true;
    }

    public void newCursor() {
        cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{
            KEY_ID, KEY_PIZZA_NAME, KEY_TELEPHONE_NUMBER, KEY_TIME_TO_CLIENT, KEY_PIZZA_PREFER_TIME, KEY_CURIER},
            null, // The columns for the WHERE clause
            null, // The values for the WHERE clause
            null, // don't group the rows
            null, // don't filter by row groups
            null // The sort order
        );
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("TABLE ARTICLE CREATE", DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
            onCreate(db);
        }
    }
}
