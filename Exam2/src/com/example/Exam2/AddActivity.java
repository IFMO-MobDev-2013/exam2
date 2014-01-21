package com.example.Exam2;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

/**
 * Created by asus on 21.01.14.
 */
public class AddActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order);
    }

    public void onClickAdd(View v) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase database;
        OrderDataBaseSQLOpenHelper openHelper = new OrderDataBaseSQLOpenHelper(this);
        database = openHelper.getWritableDatabase();
        EditText name_pizza = (EditText)findViewById(R.id.pizzaEditText);
        EditText telephone = (EditText)findViewById(R.id.teleEditText);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        contentValues.put(OrderDataBaseSQLOpenHelper.NAME_PITZZA,name_pizza.getText().toString());
        contentValues.put(OrderDataBaseSQLOpenHelper.TELEPHONE,telephone.getText().toString());
        contentValues.put(OrderDataBaseSQLOpenHelper.SPEED_DELIVERY,spinner.getSelectedItem().toString().substring(0,2));
        contentValues.put(OrderDataBaseSQLOpenHelper.START_TIME_DELIVERY,timePicker.getCurrentHour().toString() +timePicker.getCurrentMinute().toString() );

        database.insert(OrderDataBaseSQLOpenHelper.TABLE_NAME,null,contentValues);
    }

}
