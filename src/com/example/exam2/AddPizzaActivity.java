package com.example.exam2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.exam2.DataBase.NameDB;
import com.example.exam2.DataBase.ListDB;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 21.01.14
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
public class AddPizzaActivity extends Activity implements View.OnClickListener {

    ListDB database;
    EditText editTextName,editTextPhone;
    String  spinner1, delivery;

    Spinner spinner;
    RadioGroup rgGravity;
    String[] data = {"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
                    "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00",
                    "20:30", "21:00", "21:30"};


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpizza);

        Button butinfcar = (Button) findViewById(R.id.butinfcar);
        butinfcar.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        rgGravity = (RadioGroup) findViewById(R.id.rgGravity);
        spinner = (Spinner) findViewById(R.id.spinner);

        database = new ListDB(this);
        database.open();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Title");
        spinner.setSelection(2);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                spinner1 = data[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

        });
    }
            @Override
    public void onClick(View v) {
        //To change body of implemented methods use File | Settings | File Templates.
                switch (rgGravity.getCheckedRadioButtonId()) {
                    // наименование
                    case R.id.rbLeft:
                        delivery = "00:30" ;
                        break;
                    // население
                    case R.id.rbCenter:
                        delivery = "01:00";
                        break;
                    // регион
                    case R.id.rbRight:
                        delivery = "01:30";
                        break;
                }
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        //String time1 = spinner.getText().toString();
        database.addChannel(name, phone, delivery, spinner1);


        Intent intent = new Intent(this, ListOfOrdActivity.class);
        startActivity(intent);
    }
}
