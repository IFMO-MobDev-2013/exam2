package com.example.pizza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Pizza";
    private static final String DATABASE_TABLE_CC = "PC";
    private static final String KEY_CNAME = "cname";
    private static final String KEY_BOXN = "boxn";
    private static final String DATABASE_TABLE = "Orders";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CR = "cr";
    private static final String KEY_BEG = "beg";
    private static final String KEY_LEN = "len";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CreateCarsTable = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " STRING," + KEY_BEG + " INTEGER," + KEY_LEN + " INTEGER," + KEY_CR + " INTEGER," + ")";
        database.execSQL(CreateCarsTable);
        String CreateCCTable = "CREATE TABLE " + DATABASE_TABLE_CC + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CNAME + " STRING," + KEY_BOXN + " INTEGER" + ")";
        database.execSQL(CreateCCTable);
        ContentValues values = new ContentValues();
        values.put(KEY_CNAME, "Default");
        values.put(KEY_BOXN, 0);
        database.insert(DATABASE_TABLE_CC, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CC);
        onCreate(database);
    }

    public String getCName() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE_CC, new String[]{KEY_ID, KEY_CNAME, KEY_BOXN}, KEY_ID + "=?", new String[]{String.valueOf(1)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getString(1);
    }

    public int getBoxNum() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE_CC, new String[]{KEY_ID, KEY_CNAME, KEY_BOXN}, KEY_ID + "=?", new String[]{String.valueOf(1)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getInt(2);
    }

    public int setCC(String name, int box) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CNAME, name);
        values.put(KEY_BOXN, box);
        return database.update(DATABASE_TABLE_CC, values, KEY_ID + " = ?", new String[]{String.valueOf(1)});
    }

    public void addOrder(Order order) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, order.name);
        values.put(KEY_BEG, order.beg);
        values.put(KEY_LEN, order.len);
        values.put(KEY_CR, order.cr);
        database.insert(DATABASE_TABLE, null, values);
        database.close();
    }

    public Order getOrder(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_BEG, KEY_LEN, KEY_CR}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return new Order(cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
    }

    public List<Order> getOrders() {
        List<Order> statsList = new ArrayList<Order>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Order car = new Order(cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
                statsList.add(car);
            } while (cursor.moveToNext());
        }
        return statsList;
    }

    public int updateOrder(int id, Order order) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, order.name);
        values.put(KEY_BEG, order.beg);
        values.put(KEY_LEN, order.len);
        values.put(KEY_CR, order.cr);
        return database.update(DATABASE_TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
