package com.example.Exam2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asus on 21.01.14.
 */
public class OrderDataBaseSQLOpenHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "table";
    public static final String DATABASE_NAME = "database.db";
    public static final String TELEPHONE = "telephone";
    public static final String NAME_PITZZA = "name_pitzza";
    public static final String SPEED_DELIVERY = "speed_delivery";
    public static final String FINISH_TIME_DELIVERY = "finish_time";
    public static final String START_TIME_DELIVERY = "finish_time";
    public static final String NUMBER_EMPLOYEE = "number_employee";

    public OrderDataBaseSQLOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_PITZZA + " TEXT, " +
                NUMBER_EMPLOYEE + " TEXT, " +
                TELEPHONE + " TEXT, " +
                SPEED_DELIVERY + " TEXT, " +
                START_TIME_DELIVERY + " TEXT, " +
                FINISH_TIME_DELIVERY + " TEXT" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
