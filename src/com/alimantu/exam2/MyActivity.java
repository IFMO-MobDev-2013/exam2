package com.alimantu.exam2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private PizzaDataBase database;
    public static int delivers_count;
    public static String name;
    private Button button;
    private EditText editText1, editText2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        database = new PizzaDataBase(this);
        database.open();

        ArrayList<PizzaDelivery> pizzaDeliveries = database.getAll();
        if(!pizzaDeliveries.isEmpty())
        {
            Intent intent = new Intent(this, PizzaList.class);
            intent.putExtra("name",pizzaDeliveries.get(0).getName());
            delivers_count = pizzaDeliveries.get(0).getNumber();
            name = pizzaDeliveries.get(0).getName();
            startActivity(intent);
            finish();
        }

        button = (Button) findViewById(R.id.OK);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText1.getText().toString();
                String number = editText2.getText().toString();
                database.addChannel(name, number);
                Intent intent = new Intent(view.getContext(), PizzaList.class);
                intent.putExtra("name", name);
                name = name;
                delivers_count = Integer.parseInt(number);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
