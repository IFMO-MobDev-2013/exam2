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
        //int ind = getIntent().getIntExtra(WorkActivity.KEY_FOR_SOME);
        //TextView
    }
}