package com.mikhov.pizza;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class OrderAdd extends Activity {
    private Long mId;
    private EditText mPhone;
    private EditText mPizza;
    private Spinner mTime;
    private Spinner mTimeArr;
    private Database mDbHelper;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.order_add);
        mDbHelper = new Database(this);
        mDbHelper.open();
        mTime = (Spinner) findViewById(R.id.time);
        mTimeArr = (Spinner) findViewById(R.id.time_arr);
        mPhone = (EditText) findViewById(R.id.order_edit_number);
        mPizza = (EditText) findViewById(R.id.order_edit_pizza);

        String[] unusedTime = mDbHelper.getUnusedTime().split("#");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unusedTime);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTime.setAdapter(adapter);

        Button confirmButton = (Button) findViewById(R.id.order_add_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveState();
                setResult(RESULT_OK);
                finish();
            }

        });
    }

    private void saveState() {
        String time = (String) mTime.getSelectedItem();
        String timeArr = (String) mTimeArr.getSelectedItem();
        String phone = mPhone.getText().toString();
        String pizza = mPizza.getText().toString();

        long id = mDbHelper.addOrder(phone, pizza, timeArr, time, 3);
    }
}
