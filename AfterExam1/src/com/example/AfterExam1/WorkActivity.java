package com.example.AfterExam1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

public class WorkActivity extends Activity {
    private DataBase dataBase;
    private ArrayList<Order> arrayList = new ArrayList<Order>();
    private CustomAdapter customAdapter;
    public static final String KEY_FOR_SOME_TIME = "key_for_some_time";
    public static final String KEY_FOR_SOME_BOX = "key_for_some_box";

    private ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work);
        dataBase = new DataBase(this);
        dataBase.open();

        ((TextView) findViewById(R.id.text_autowashing_name)).setText(dataBase.getAutoWashName());
        loadDatas();
        Button button = (Button) findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


       // listView.setOnClickListener(new AdapterView.OnItemClickListener());
       // listView.setOnClickListener(new AdapterView.OnItemClickListener());

    }

    private void loadDatas() {
        arrayList = new ArrayList<Order>();
        Cursor cursor = dataBase.getCursorStartData();
        while (cursor.moveToNext()) {
            String pizzaName = cursor.getString(cursor.getColumnIndex(DataBase.KEY_PIZZA_NAME));
            String telephoneNumber = cursor.getString(cursor.getColumnIndex(DataBase.KEY_TELEPHONE_NUMBER));
            String timeToClient = cursor.getString(cursor.getColumnIndex(DataBase.KEY_TIME_TO_CLIENT));
            String pizzaPreferTime = cursor.getString(cursor.getColumnIndex(DataBase.KEY_PIZZA_PREFER_TIME));
            String curier = cursor.getString(cursor.getColumnIndex(DataBase.KEY_CURIER));

            Order tmp = new Order(pizzaName, telephoneNumber, timeToClient, pizzaPreferTime, curier);
            arrayList.add(tmp);
        }

        sorting();
        customAdapter = new CustomAdapter(this, arrayList);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(customAdapter);
    }

    private String minus(String timeLeft, String timeRight) {
        int hourLeft = Integer.parseInt(timeLeft.substring(0, timeLeft.indexOf(":")));
        int minuteLeft = Integer.parseInt(timeLeft.substring(timeLeft.indexOf(":") + 1, timeLeft.length()));
        int hourRight = Integer.parseInt(timeRight.substring(0, timeRight.indexOf(":")));
        int minuteRight = Integer.parseInt(timeRight.substring(timeRight.indexOf(":") + 1, timeRight.length()));
        if ( (minuteLeft - minuteRight) < 0) {
            minuteLeft += 60;
            hourLeft--;
        }
        String result = "";
        if (hourLeft - hourRight < 10)
            result = result + (hourLeft - hourRight) + "0";
        else
            result = result + (hourLeft - hourRight);
        result += ":";
        if ( (minuteLeft - minuteRight) < 10)
            result = result + "0" +  (minuteLeft - minuteRight);
        else
            result = result +  (minuteLeft - minuteRight);
        return result;
    }

    // >
    private boolean cmp(String timeLeft, String timeLeft1, String timeRight, String timeRight1) {
        timeLeft  = minus(timeLeft, timeLeft1);
        timeRight = minus(timeRight, timeRight1);
        int hourLeft = Integer.parseInt(timeLeft.substring(0, timeLeft.indexOf(":")));
        int minuteLeft = Integer.parseInt(timeLeft.substring(timeLeft.indexOf(":") + 1, timeLeft.length()));
        int hourRight = Integer.parseInt(timeRight.substring(0, timeRight.indexOf(":")));
        int minuteRight = Integer.parseInt(timeRight.substring(timeRight.indexOf(":") + 1, timeRight.length()));
        if (hourLeft > hourRight) return true;
        if (hourLeft < hourRight) return false;
        if (minuteLeft > minuteRight) return true;
        if (minuteLeft < minuteRight) return false;
        return true;
    }

    private void sorting() {
        for (int i = 0; i < arrayList.size(); ++i)
            for (int j = i + 1; j < arrayList.size(); ++j)
                if (cmp(arrayList.get(i).pizzaPreferTime, arrayList.get(i).timeToClient, arrayList.get(j).pizzaPreferTime, arrayList.get(j).timeToClient)) {
                    Order tmp = arrayList.get(i);
                    arrayList.set(i, arrayList.get(j));
                    arrayList.set(j, tmp);
                }
    }
}
