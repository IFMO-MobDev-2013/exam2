package com.example.PizzeriaFast;

import android.content.ContentValues;
import android.content.Intent;

public class Order {

    public static final String MODEL = "model";
    public static final String TIME = "time";
    public static final String BOX = "box";
    public static final String PHONE = "phone";
    public static final String LENGTH = "length";
    public static final String NEWORDER = "new_order";
    public static final String EDITABLE = "editable";
    public static final String USESTABLE = "usestable";
    public static final String OURBOXES = "ourboxes";
    public static final String HAVENEWORDER = "haveneworder";

    public String model;
    public int time;
    public int box;
    public String phone;
    public int length;

    Order() {
        time = 0;
        box = 0;
        model = "";
        phone = "";
        length = 0;
    }

    Order(int _time, int _length, int _box, String _model, String _phone) {
        time = _time;
        box = _box;
        model = _model;
        phone = _phone;
        length = _length;
    }

    static String makeTime(int x) {
        String hours = "" + (x / 2 + 8);
        if (hours.length() < 2)
            hours = "0" + hours;
        return hours + (x % 2 == 1 ? ":30" : ":00");
    }

    static int makeIntFromTime(String s) {
        return (Integer.parseInt(s.substring(0, 2)) - 8) * 2 + (s.charAt(3) == '3' ? 1 : 0);
    }

    static String makeTime(String s) {
        return makeTime(Integer.parseInt(s));
    }

    Order(ContentValues c) {
        time = c.getAsInteger(TIME);
        box = c.getAsInteger(BOX);
        model = c.getAsString(MODEL);
        length = c.getAsInteger(LENGTH);
        phone = c.getAsString(PHONE);
    }

    ContentValues getContentValues() {
        ContentValues c = new ContentValues();
        c.put(TIME, time);
        c.put(BOX, box);
        c.put(MODEL, model);
        c.put(LENGTH, length);
        c.put(PHONE, phone);

        return c;
    }

    Order(Intent intent) {
        time = intent.getIntExtra(TIME, 0);
        box = intent.getIntExtra(BOX, 0);
        model = intent.getStringExtra(MODEL);
        phone = intent.getStringExtra(PHONE);
        length = intent.getIntExtra(LENGTH, 0);
    }
}
