package com.example.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OrdersActivity extends Activity {
    private String TIME, NAME, CUR;
    private final String PID = "pid";
    DBAdapter db;
    DBName dbn;
    TextView pizzaname;
    ListView listView;
    Pizza[] pizzas;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);
        TIME = getResources().getString(R.string.time);
        NAME = getResources().getString(R.string.name);
        CUR = getResources().getString(R.string.cur);
        pizzaname = (TextView) findViewById(R.id.textView);
        listView = (ListView) findViewById(R.id.listView);
        db = new DBAdapter(this);
        dbn = new DBName(this);
        pizzaname.setText(dbn.getName());
        pizzas = db.getAllPizza();
        if (pizzas != null) makeList();
    }


    private void makeList() {
        sortPizzas();
        listView = (ListView) findViewById(R.id.listView);
        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>(
                pizzas.length);
        Map<String, String> m;
        for (int i = 0; i < pizzas.length; i++) {
            m = new HashMap<String, String>();
            m.put(NAME, pizzas[i].name);
            m.put(TIME, pizzas[i].time);
            m.put(CUR, Integer.toString(pizzas[i].cur));
            data.add(m);
        }
        String[] from = {NAME, TIME, CUR};
        int[] to = {R.id.name, R.id.time, R.id.cur};
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.listitem, from, to);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> l, View view,
                                    int position, long id) {
                Intent intent = new Intent(OrdersActivity.this, Information.class);
                intent.putExtra(PID, pizzas[position].id);
                startActivity(intent);
            }
        });
    }
    public void addPizza(View v) {
        Intent i = new Intent(this, AddPizzaActivity.class);
        startActivity(i);
        this.finish();
    }
    private void sortPizzas() {
        Pizza temp;
        for (int i = 0; i < pizzas.length; i++) {
            for (int j = i + 1; j < pizzas.length; j++) {
                if (time(pizzas[j].time) < time(pizzas[i].time)) {
                    temp = pizzas[i];
                    pizzas[i] = pizzas[j];
                    pizzas[j] = temp;
                }
            }
        }
    }
    private int time(String tt) {
        for (int i = 0; i < tt.length(); i++) {
            if (tt.charAt(i) == ':') tt = tt.substring(0, i) + tt.substring(i + 1, tt.length());
        }
        return Integer.parseInt(tt);
    }
}
