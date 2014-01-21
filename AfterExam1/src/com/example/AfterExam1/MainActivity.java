package com.example.AfterExam1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    private DataBase dataBase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        deleteDatabase(DataBase.DATABASE_NAME);
        dataBase = new DataBase(this);
        dataBase.open();
        if (dataBase.isEmpty() == false)
            solve();

        Button button = (Button) findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String AutoWashingName = ((EditText) findViewById(R.id.editTextName)).getText().toString();
                if (AutoWashingName.isEmpty())
                    AutoWashingName = ((EditText) findViewById(R.id.editTextName)).getHint().toString();

                String AutoWashingBoxNumber = ((EditText) findViewById(R.id.editTextBoxNumber)).getText().toString();
                if (AutoWashingBoxNumber.isEmpty())
                    AutoWashingBoxNumber = ((EditText) findViewById(R.id.editTextBoxNumber)).getHint().toString();

                dataBase.insertAutoWashParameters(AutoWashingName, AutoWashingBoxNumber);
                solve();
            }
        });
    }

    private void solve() {
        Intent intent = new Intent(MainActivity.this, WorkActivity.class);
        startActivity(intent);
    }
}
