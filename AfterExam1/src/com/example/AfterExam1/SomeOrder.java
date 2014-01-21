package com.example.AfterExam1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SomeOrder extends Activity {
    private DataBase dataBase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.some);
        dataBase = new DataBase(this);
        dataBase.open();
        int ind = WorkActivity.mainIndex;
        TextView text = (TextView) findViewById(R.id.textViewSome);
        text.setText("Название пиццы : " + WorkActivity.arrayList.get(ind).pizzaName + "\n\n\n" +
                "Телефонный номер : " + WorkActivity.arrayList.get(ind).telephoneNumber + "\n\n\n" +
                "Время доставки заказа : " + WorkActivity.arrayList.get(ind).pizzaPreferTime);
        //int ind = getIntent().getIntExtra(WorkActivity.KEY_FOR_SOME);
        //TextView
    }
}