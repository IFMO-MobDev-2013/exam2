package com.example.exam2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.exam2.DataBase.NameDB;
import com.example.exam2.DataBase.Name;

import java.util.ArrayList;

public class MyActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */



    EditText text1, text2;
    Button finalbutton;
    Cursor cursor;
    NameDB database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        text1 = (EditText) findViewById(R.id.editText1);
        text2 = (EditText) findViewById(R.id.editText2);

        finalbutton = (Button) findViewById(R.id.finalbutton);
        finalbutton.setOnClickListener(this);

        database = new NameDB(this);
        database.open();

        ArrayList<Name> carWashs = database.getAll();

        if (!carWashs.isEmpty()) {
            Intent intent = new Intent(this, ListOfOrdActivity.class);
            startActivity(intent);
            finish();
        }



    }

    @Override
    public void onClick(View v) {
        //To change body of implemented methods use File | Settings | File Templates.
        String name = text1.getText().toString();
        String box = text2.getText().toString();
        database.addChannel(name, box);

        Intent intent = new Intent(this, ListOfOrdActivity.class);
        startActivity(intent);
    }
}
