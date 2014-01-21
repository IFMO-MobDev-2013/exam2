package ru.marsermd.Exam2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 08.11.13
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns{

    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "Exam 2";

    // Table Names
    private static final String COURIER_NUMBER = "/COURIER_NUMBER";
    private static final String TABLE_COURIER_NNUMBER = "[COURIER" + COURIER_NUMBER + "]";

    //column names
    private static final String KEY_TIME = "time";
    private static final String KEY_COURIER = "courier";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_DELIVERING_TIME = "delivering_time";
    private static final String KEY_MODEL = "model";


    // Table Create Statement
    private static final String CREATE_TABLE_COURIER = "CREATE TABLE " + TABLE_COURIER_NNUMBER
            + "(" + KEY_TIME + " INTEGER," + KEY_COURIER + " INTEGER," +
            KEY_COMMENT + " TEXT," + KEY_PHONE + " TEXT," + KEY_DELIVERING_TIME + " INTEGER," + KEY_MODEL + " TEXT"+ ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onUpgrade(getWritableDatabase(), 1, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        for (int i = 0; i < PizzeriaModel.getCouriersCount(); i++) {
            db.execSQL(CREATE_TABLE_COURIER.replaceAll(COURIER_NUMBER, "" + i));
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        if (oldVersion == newVersion)
            return;
        for (int i = 0; i < PizzeriaModel.getCouriersCount(); i++) {
            db.execSQL(CREATE_TABLE_COURIER.replaceAll(COURIER_NUMBER, "" + i));
        }

        // create new tables
        onCreate(db);
    }

    public long addPizza(PizzaModel pizza) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, pizza.getTime());
        values.put(KEY_COURIER, pizza.getAssignedCourier());
        values.put(KEY_DELIVERING_TIME, pizza.getDeliveryTime());
        values.put(KEY_PHONE, pizza.getPhone());
        values.put(KEY_COMMENT, pizza.getComment());
        values.put(KEY_MODEL, pizza.getModel());

        // insert row
        long categoryId = db.insertWithOnConflict(
                TABLE_COURIER_NNUMBER.replaceAll(COURIER_NUMBER, "" + pizza.getAssignedCourier()), null, values, SQLiteDatabase.CONFLICT_IGNORE);

        return categoryId;
    }

    public PizzaModel getPizza(int time, int courierId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_COURIER_NNUMBER.replaceAll(COURIER_NUMBER, "" + courierId) + " WHERE " +
                KEY_TIME + " = " + time + " AND " +
                KEY_COURIER + " = " +  courierId;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c == null || !c.moveToFirst())
            return null;

        return readFromCursor(c);
    }

    private PizzaModel readFromCursor(Cursor c) {

        PizzaModel pizza = new PizzaModel();
        pizza.setTime(c.getInt(c.getColumnIndex(KEY_TIME)));
        pizza.setAssignedCourier(c.getInt(c.getColumnIndex(KEY_COURIER)));
        pizza.setDeliveryTime((c.getInt(c.getColumnIndex(KEY_DELIVERING_TIME))));
        pizza.setModel(c.getString(c.getColumnIndex(KEY_MODEL)));
        pizza.setComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
        pizza.setPhone(c.getString(c.getColumnIndex(KEY_PHONE)));

        return pizza;
    }

    public List<PizzaModel> getAllPizzas(int courierId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<PizzaModel> pizzaModels = new ArrayList<PizzaModel>();
        String selectQuery = "SELECT * FROM " + TABLE_COURIER_NNUMBER.replaceAll(COURIER_NUMBER, "" + courierId);

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                pizzaModels.add(readFromCursor(c));
            } while (c.moveToNext());
        }

        return pizzaModels;
    }

    public List<PizzaModel> getAllPizzas() {
        List<PizzaModel> tmp = new ArrayList<PizzaModel>();
        for (int i = 0; i < PizzeriaModel.getCouriersCount(); i++) {
            tmp.addAll(getAllPizzas(i));
        }
        Collections.sort(tmp);
        return tmp;
    }

    public void updatePizza(PizzaModel pizza, int newCourier, int newTime) {
        deletePizza(pizza);
        pizza.setTime(newTime);
        pizza.setAssignedCourier(newCourier);
        addPizza(pizza);
    }

    public void deletePizza(PizzaModel pizza) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURIER_NNUMBER.replaceAll(COURIER_NUMBER, "" + pizza.getAssignedCourier()), KEY_TIME + " = ?",
                new String[]{String.valueOf(pizza.getTime())});
    }

    public List<Integer> getFreeTimeForCourier(int courierId, int delivery_time) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Integer> freeTime = new ArrayList<Integer>();
        String selectQuery = "SELECT * FROM " + TABLE_COURIER_NNUMBER.replaceAll(COURIER_NUMBER, "" + courierId);

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        int lastTime = 0;
        int lastDeliveryTime = 0;

        if (c.moveToFirst()) {
            do {
                PizzaModel tmp = readFromCursor(c);
                for (int i = lastTime + lastDeliveryTime; i <= tmp.getTime() - delivery_time; i++) {
                    if (PizzeriaModel.isWorkingTime(i, i + delivery_time))
                        freeTime.add(i);
                }
                lastTime = tmp.getTime();
                lastDeliveryTime = tmp.getDeliveryTime();
            } while (c.moveToNext());
        }
        for (int i = lastTime + lastDeliveryTime; i <= 48; i++) {
            if (PizzeriaModel.isWorkingTime(i, i + delivery_time)) {
                freeTime.add(i);
            }
        }
        return freeTime;
    }

    public Integer[] getFreeTime(int deliveryTime) {
        TreeSet<Integer> freeTime = new TreeSet<Integer>();
        for (int i = 0; i < PizzeriaModel.getCouriersCount(); i++) {
            freeTime.addAll(getFreeTimeForCourier(i, deliveryTime));
            Log.e("focusChange", i + " " + freeTime.size());
        }
        return freeTime.toArray(new Integer[freeTime.size()]);
    }

    public int getCourierForTime(int t, int deliveryTime) {
        for (int i = 0; i < PizzeriaModel.getCouriersCount(); i++) {
            if (getFreeTimeForCourier(i, deliveryTime).contains(t)) return i;
        }
        return -1;
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
