package com.example.pizza;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends Activity {

    int time = 0;
    int len = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.len, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                setFreeTime(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setClickable(false);
    }

    private int getCR() {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        boolean[][] t = new boolean[databaseHandler.getBoxNum()][100];
        List<Order> orders = databaseHandler.getOrders();
        List<Integer> tm = new ArrayList<Integer>();
        for (int i = 0; i < orders.size(); i++) {
            for (int j = orders.get(i).beg; j < orders.get(i).beg + orders.get(i).len; j++) {
                t[orders.get(i).cr][j] = true;
            }
        }
        for (int i = 0; i < 26; i++) {
            boolean av = false;
            for (int j = 0; j < databaseHandler.getBoxNum(); j++) {
                boolean m = true;
                for (int k = i; k < i + len; k++) {
                    if (t[j][k]) {
                        m = false;
                    }
                }
                if (m) {
                    return j;
                }
            }
            if (av) {
                tm.add(i);
            }
        }
        return -1;
    }

    private String[] getFreeTime() {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        boolean[][] t = new boolean[databaseHandler.getBoxNum()][100];
        List<Order> orders = databaseHandler.getOrders();
        List<Integer> tm = new ArrayList<Integer>();
        for (int i = 0; i < orders.size(); i++) {
            for (int j = orders.get(i).beg; j < orders.get(i).beg + orders.get(i).len; j++) {
                t[orders.get(i).cr][j] = true;
            }
        }
        for (int i = 0; i < 26; i++) {
            boolean av = false;
            for (int j = 0; j < databaseHandler.getBoxNum(); j++) {
                boolean m = true;
                for (int k = i; k < i + len; k++) {
                    if (t[j][k]) {
                        m = false;
                    }
                }
                if (m) {
                    av = true;
                }
            }
            if (av) {
                tm.add(i);
            }
        }
        String[] arr = new String[tm.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = getResources().getStringArray(R.array.time)[tm.get(i)];
        }
        return arr;
    }

    private void setFreeTime(int len) {
        this.len = len;
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getFreeTime());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                time = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
        spinner.setClickable(true);
    }

    public void onAdd(View view) {
        EditText name = (EditText) findViewById(R.id.editText);
        EditText telephone = (EditText) findViewById(R.id.editText3);
        Order order = new Order(name.getText().toString(), time, len, getCR());
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.addOrder(order);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
