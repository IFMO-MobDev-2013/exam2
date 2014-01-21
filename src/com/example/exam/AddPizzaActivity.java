package com.example.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class AddPizzaActivity extends Activity {
    EditText name, phone, adress;
    Spinner time, speed;
    DBCurs dbb;
    DBAdapter db;
    DBName dbn;
    Time[] times;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pizza);
        dbb = new DBCurs(this);
        db = new DBAdapter(this);
        dbn = new DBName(this);
        name = (EditText) findViewById(R.id.name);
        adress = (EditText) findViewById(R.id.adress);
        phone = (EditText) findViewById(R.id.phone);
        time = (Spinner) findViewById(R.id.time);
        speed = (Spinner) findViewById(R.id.speed);
        times = dbb.getTimes(1, getResources().getStringArray(R.array.times), dbn.getCount());
        speed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                times = dbb.getTimes(position + 1, getResources().getStringArray(R.array.times), dbn.getCount());
                setTimes(times);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setTimes(Time[] times) {
        int size = 0;
        for (int i = 0; i < times.length; i++) {
            if (times[i].cur != 0) size++;
        }
        String[] ttimes = new String[size];
        size = -1;
        for (int  i = 0; i < times.length; i++) {
            if (times[i].cur != 0) {
                size++; ttimes[size] = times[i].time;
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ttimes);
        time.setAdapter(adapter);
    }
    public void addPizza(View v) {
        String nname = name.getText().toString();
        String ttime = time.getSelectedItem().toString();
        String sspeed = speed.getSelectedItem().toString();
        String pp = phone.getText().toString();
        String aadress = adress.getText().toString();
        int ccur = getCur(ttime);
        db.insert(nname, sspeed, ttime, ccur, pp, aadress);
        addOrderToDB(ccur, speed.getSelectedItemPosition(), ttime);
        Intent i = new Intent(this, OrdersActivity.class);
        startActivity(i);
        this.finish();
    }
    private void addOrderToDB(int ccur, int speedPosition, String ttime) {
        String[] allTimes = getResources().getStringArray(R.array.times);
        int timePosition = 0;
        for (int i = 0; i < allTimes.length; i++) {
            if (allTimes[i].equals(ttime)) timePosition = i;
        }
        for (int i = 0; i <= speedPosition; i++) {
            dbb.insert(Integer.toString(ccur), allTimes[timePosition + i]);
        }
    }
    private int getCur(String time) {
        for (int i = 0; i < times.length; i++) {
            if (times[i].time.equals(time)) return times[i].cur;
        }
        return 1;
    }
}
