package com.alimantu.exam2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 21.01.14
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class AddPizzaActivity extends Activity {

    private EditText editText1, editText2;
    private Pizza pizza = new Pizza();
    private PizzaType database;
    private Spinner spinner, spinner1;
    private Button button;
    private String current_time;
    int firstNotBusyWindows = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pizza_activity);

        editText1 = (EditText) findViewById(R.id.editTextAdd1);
        editText2 = (EditText) findViewById(R.id.editTextAdd2);
        button = (Button) findViewById(R.id.button);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setEnabled(false);
        spinner1 = (Spinner) findViewById(R.id.spinner1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                current_time = String.valueOf(spinner.getSelectedItem());

                spinner.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        List<String> list = new ArrayList<String>();
        for (int i = 8; i < 22; i++) {
            String value = null;
            if (i < 10)
                value = "0" + String.valueOf(i) + ":00";
            else
                value = String.valueOf(i) + ":00";
            String halfValue = null;
            if (i < 10)
                halfValue = "0" + String.valueOf(i) + ":30";
            else
                halfValue = String.valueOf(i) + ":30";
            boolean ok = false;
            for (int j = 1; j <= MyActivity.delivers_count; j++)
                if (free(String.valueOf(j), value))
                    ok = true;
            if (ok)
                list.add(value);

            for (int j = 1; j <= MyActivity.delivers_count; j++)
                if (free(String.valueOf(j), halfValue))
                    ok = true;
            if (ok)
                list.add(halfValue);
        }
        if (list.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Извините, всё время занято!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            button.setEnabled(false);
            toast.show();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String current = String.valueOf(spinner.getSelectedItem());
                for (int j = 1; j <= MyActivity.delivers_count; j++)
                    if (free(String.valueOf(j), current)) {
                        pizza.setDeliver(String.valueOf(j));
                        break;
                    }

                pizza.setTime(String.valueOf(spinner.getSelectedItem()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pizza.setPizzaType(editText1.getText().toString());
                pizza.setTelNumber(editText2.getText().toString());
                if (!pizza.getDeliver().equals("") &&
                        !pizza.getTime().equals("") &&
                        !pizza.getPizzaType().equals("") &&
                        !pizza.getTelNumber().equals("")) {

                    button.setEnabled(true);
                    database = new PizzaType(view.getContext());
                    database.open();
                    database.addChannel(pizza);
                    database.close();
                    Intent intent = new Intent(view.getContext(), PizzaList.class);
                    intent.putExtra("name", MyActivity.name);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean free(String s, String time) {
        for (Pizza pizza : PizzaList.pizzas) {
            if (pizza.getDeliver().equals(s) && pizza.getTime().equals(time))
                return false;
        }
        return true;
    }

}
