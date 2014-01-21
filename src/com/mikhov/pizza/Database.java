package com.mikhov.pizza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Database {
    private static final String DATABASE_TABLE = "orders";

    public static final String COL_ID = "_id";
    static final String COL_PHONE = "phone";
    static final String COL_PIZZA = "pizza";
    static final String COL_SPEED = "speed";
    static final String COL_TIME = "time";
    static final String COL_COURIER = "courier";

    private Context context;
    private DbHelper databaseHelper;
    private SQLiteDatabase database;


    public Database(Context ctx) {
        this.context = ctx;
    }

    public Database open() throws SQLException {
        databaseHelper = new DbHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public boolean isNotEmpty() {
        Cursor cursor = getAllData();
        return cursor.moveToNext();
    }

    public void drop() {
        database.execSQL("DROP TABLE IF EXISTS orders");
        database.execSQL("create table orders (_id integer primary key autoincrement, phone text, pizza text, speed text, time text, courier integer);");
    }

    public void dropAll() {
        database.execSQL("DROP TABLE IF EXISTS orders");
    }

    public Cursor getAllData() {
        return database.query(DATABASE_TABLE, new String[] {
                COL_ID,
                COL_PHONE,
                COL_PIZZA,
                COL_SPEED,
                COL_TIME,
                COL_COURIER
        }, null, null, null, null, COL_TIME);
    }

    public long addOrder(String phone, String pizza, String speed, String time, int courier) {
        ContentValues cv = new ContentValues();
        cv.put(COL_PHONE, phone);
        cv.put(COL_PIZZA, pizza);
        cv.put(COL_SPEED, speed);
        cv.put(COL_TIME, time);
        cv.put(COL_COURIER, courier);
        return database.insert(DATABASE_TABLE, null, cv);
    }

    public Cursor fetchOrder(long id) throws SQLException {
        Cursor cursor = database.query(true, DATABASE_TABLE, new String[] {
                COL_ID,
                COL_PHONE,
                COL_PIZZA,
                COL_SPEED,
                COL_TIME,
                COL_COURIER },
                COL_ID + "=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public void updateOrder(long id, String phone, String pizza, String speed, String time, int courier) {
        ContentValues cv = new ContentValues();
        cv.put(COL_PHONE, phone);
        cv.put(COL_PIZZA, pizza);
        cv.put(COL_SPEED, speed);
        cv.put(COL_TIME, time);
        cv.put(COL_COURIER, courier);
        database.update(DATABASE_TABLE, cv, COL_ID + "=" + id, null);
    }

    public String getOrdersInfo() {
        String allInfo = "Info:\n";
        Cursor cursor = getAllData();
        int i = -1;
        while (cursor.moveToNext()) {
            i++;
            allInfo += (i + 1) + ") " +
                    cursor.getString(cursor.getColumnIndex(COL_PHONE)) + "; " +
                    cursor.getString(cursor.getColumnIndex(COL_PIZZA)) + "; " +
                    cursor.getString(cursor.getColumnIndex(COL_SPEED)) + "; " +
                    cursor.getString(cursor.getColumnIndex(COL_TIME)) + "; " +
                    cursor.getString(cursor.getColumnIndex(COL_COURIER)) + "\n\n";
        }
        return allInfo;
    }

    public String getUnusedTime() {
        boolean[] c1 = new boolean[29];
        boolean[] c2 = new boolean[29];
        boolean[] c3 = new boolean[29];
        String[] times;
        String time = "";
        int j = 10;
        for (int i = 0; i < 14; i++) {
            time += j + ":00#";
            time += j + ":30#";
            j++;
        }
       /* times = time.substring(0, time.length() - 1).split("#");

        Cursor cursor = getAllData();
        while (cursor.moveToNext()) {
                for (int i = 0; i < 29; i++) {
                    if (cursor.getString(cursor.getColumnIndex(COL_TIME)).equals(times[i])) {
                        if (cursor.getString(cursor.getColumnIndex(COL_SPEED)).equals("30 min")) {
                            if (cursor.getInt(cursor.getColumnIndex(COL_COURIER)) == 1)  {
                                c1[i] = true;
                            } else if (cursor.getInt(cursor.getColumnIndex(COL_COURIER)) == 2)  {
                                c1[i] = true;
                            } else if (cursor.getInt(cursor.getColumnIndex(COL_COURIER)) == 3)  {
                                c1[i] = true;
                            }
                        } else if (cursor.getString(cursor.getColumnIndex(COL_SPEED)).equals("1 hour")) {

                        } else if (cursor.getString(cursor.getColumnIndex(COL_SPEED)).equals("1 hour 30 min")) {

                        }
                    }
                }
        } */
        return time;
    }

    public void delOrder(long id) {
        database.delete(DATABASE_TABLE, COL_ID + " = " + id, null);
    }
}
