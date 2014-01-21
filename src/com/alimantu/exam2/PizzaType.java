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
 * Time: 15:05
 * To change this template use File | Settings | File Templates.
 */
public class PizzaType {

    private static final String DB_NAME = "pizzadelivery1";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "schedule1";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PIZZA = "pizzatype";
    public static final String COLUMN_TEL_NUMB = "telnumb";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DELIVER = "deliver";

    public static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_PIZZA + " text, " +
                    COLUMN_TEL_NUMB + " text, " +
                    COLUMN_TIME + " text, " +
                    COLUMN_DELIVER + " text" +
                    ");";

    private final Context context;

    private DBHelper dbHelper;
    private SQLiteDatabase mDB;

    public PizzaType(Context context) {
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
        return mDB.query(DB_TABLE, null, null, null, null, null, COLUMN_TIME);
    }

    public void addChannel(Pizza pizza) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PIZZA, pizza.getPizzaType());
        cv.put(COLUMN_TIME, pizza.getTime());
        cv.put(COLUMN_TEL_NUMB, pizza.getTelNumber());
        cv.put(COLUMN_DELIVER, pizza.getDeliver());
        mDB.insert(DB_TABLE, null, cv);
    }

    public void deleteChannel(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public ArrayList<Pizza> getAll() {
        Cursor cursor = getAllData();
        ArrayList<Pizza> result = new ArrayList<Pizza>();
        while (cursor.moveToNext()) {
            Pizza current = new Pizza();
            current.setPizzaType(cursor.getString(cursor.getColumnIndex(COLUMN_PIZZA)));
            current.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
            current.setTelNumber(cursor.getString(cursor.getColumnIndex(COLUMN_TEL_NUMB)));
            current.setDeliver(cursor.getString(cursor.getColumnIndex(COLUMN_DELIVER)));
            result.add(current);
        }
        return result;
    }

    public Pizza selectPizzas(String name) {
        ArrayList<Pizza> pizzas = getAll();
        for (int i = 0; i < pizzas.size(); i++)
            if (pizzas.get(i).getPizzaType().equals(name))
                return pizzas.get(i);
        return null;
    }

}
