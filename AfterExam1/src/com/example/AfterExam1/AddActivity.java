package com.example.AfterExam1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class AddActivity extends Activity {
    private DataBase dataBase;
    private String[] mainTimes = {"--:--", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
                            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00",
                            "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30", "24:00"};

    private String[] otherTimes = {"--:--", "00:30", "01:00", "01:30"};

    private String timeFromMainSpinner = "";
    private String timeFromOtherSpinner = "";

    private String pizzaName = "";
    private String telephoneNumber = "";
    private String timeToClient = "";
    private String pizzaPreferTime = "";
    private String curier = "";
    private boolean[][] matrix;
    private Spinner mainSpinner;
    private Spinner otherSpinner;

    private ArrayList<String> arrayList = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        dataBase = new DataBase(this);
        dataBase.open();


        mainSpinner = (Spinner) findViewById(R.id.spinner_time);
        otherSpinner = (Spinner) findViewById(R.id.spinner_speed);

        ArrayList<String> otherArrayList = new ArrayList<String>();
        for (int i = 0; i < otherTimes.length; ++i)
            otherArrayList.add(otherTimes[i]);
        ArrayAdapter<String> otherAdapter = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_spinner_item, otherArrayList);
        otherSpinner.setAdapter(otherAdapter);

        arrayList = new ArrayList<String>(); arrayList.add("--:--");

        otherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                timeFromOtherSpinner = otherSpinner.getSelectedItem().toString();
                if (timeFromOtherSpinner.indexOf("-") >= 0) {
                    timeFromOtherSpinner = "";
                    timeFromMainSpinner = "";
                    mainSpinner.setSelection(0);
                    ((TextView) findViewById(R.id.textViewBox)).setText("Курьер №   --");
                    arrayList = new ArrayList<String>(); arrayList.add("--:--");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_spinner_item, arrayList);
                    mainSpinner.setAdapter(adapter);
                    return;
                }

                newMatrix();
                arrayList = new ArrayList<String>(); arrayList.add("--:--");


                for (int i = selectedItemPosition; i < mainTimes.length; ++i) {
                    boolean free = true;
                    for (int j = 0; j < dataBase.getAutoWashBoxNumber(); ++j) {
                        free = true;
                        for (int k = 0; k < getTimeToClient(timeFromOtherSpinner); ++k) ///
                            if (matrix[i - k][j] == true) {
                                free = false;
                            }
                        if (free) break;
                    }
                    if (free == true) {
                        arrayList.add(mainTimes[i]);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_spinner_item, arrayList);
                mainSpinner.setAdapter(adapter);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_spinner_item, arrayList);
        mainSpinner.setAdapter(adapter);

        mainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                int ind = -1;
                if (timeFromOtherSpinner.equals("")) {
             //       Toast toast = Toast.makeText(getApplicationContext(), "Введите предполагаемую скорость доставки!", Toast.LENGTH_SHORT);
             //       toast.setGravity(Gravity.TOP, 0, 0);
             //       toast.show();
                    return;
               }

                timeFromMainSpinner = mainSpinner.getSelectedItem().toString();
                if (timeFromMainSpinner.indexOf("-") >= 0 ) {
                    ((TextView) findViewById(R.id.textViewBox)).setText("Курьер №   --");
                    return;
                }

                for (int i = 0; i < mainTimes.length; ++i)
                    if (timeFromMainSpinner.equals(mainTimes[i])) {
                        ind = i;
                        break;
                    }

                for (int j = 0; j < dataBase.getAutoWashBoxNumber(); ++j) {
                    boolean free = true;
                    for (int i = ind - getTimeToClient(timeFromOtherSpinner); i <= ind; ++i) {
                        if (matrix[i][j] == true) {
                            free = false;
                        }
                    }
                    if (free) {
                        curier = "" + (j + 1);
                        ((TextView) findViewById(R.id.textViewBox)).setText("Курьер №   " + (j + 1));
                        break;
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button button = (Button) findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaName = ((EditText) findViewById(R.id.editText_auto_model)).getText().toString();
                telephoneNumber = ((EditText) findViewById(R.id.editText_telephone_number)).getText().toString();

                if (pizzaName.length() > 0 && telephoneNumber.length() > 0 && timeFromMainSpinner.length() > 0 &&
                        timeFromOtherSpinner.length() > 0 && curier.length() > 0) {
                    dataBase.insertClient(pizzaName,  telephoneNumber, timeFromOtherSpinner, timeFromMainSpinner, curier);
                    Intent intent = new Intent(AddActivity.this, WorkActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(AddActivity.this, "Вы ввели неполные данные!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private void newMatrix() {
        matrix = new boolean[mainTimes.length][dataBase.getAutoWashBoxNumber()];
        Cursor cursor = dataBase.getCursorStartData();
        while (cursor.moveToNext()) {
            int curier = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBase.KEY_CURIER)));
            int preferTime = getPreferTime(cursor.getString(cursor.getColumnIndex(DataBase.KEY_PIZZA_PREFER_TIME)));
            int timeToClient = getTimeToClient(cursor.getString(cursor.getColumnIndex(DataBase.KEY_TIME_TO_CLIENT)));
            if (preferTime != -1 && timeToClient != -1) {
                while (timeToClient > 0) {             //
                    matrix[preferTime - timeToClient + 1][curier - 1] = true;
                    timeToClient--;
                }
            }
        }
    }

    private int getPreferTime(String time) {
        for (int i = 0; i < mainTimes.length; ++i)
            if (mainTimes[i].equals(time))
                return i;
        return -1;
    }

    private int getTimeToClient(String time) {
        for (int i = 0; i < otherTimes.length; ++i)
            if (otherTimes[i].equals(time))
                return i;
        return -1;
    }
}