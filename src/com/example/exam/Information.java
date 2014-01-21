package com.example.exam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Kirill on 14.01.14.
 */
public class Information extends Activity {
    private final String PID = "pid";
    DBAdapter db;
    String id;
    Pizza pizza;
    TextView name, speed, phone, time, cur, adress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        db = new DBAdapter(this);
        id = getIntent().getStringExtra(PID);
        pizza = db.getPizza(id);
        setInformation();
    }

    private void setInformation() {
        adress = (TextView) findViewById(R.id.adress);
        name = (TextView) findViewById(R.id.name);
        speed = (TextView) findViewById(R.id.speed);
        phone = (TextView) findViewById(R.id.phone);
        cur = (TextView) findViewById(R.id.cur);
        time = (TextView) findViewById(R.id.time);
        adress.setText(adress.getText().toString() + " " + pizza.adress);
        name.setText(name.getText().toString()+ " " + pizza.name);
        speed.setText(speed.getText().toString() + " " + pizza.speed);
        phone.setText(phone.getText().toString()+ " " + pizza.phone);
        time.setText(time.getText().toString()+ " " + pizza.time);
        cur.setText(cur.getText().toString()+ " " + Integer.toString(pizza.cur));
    }
}
