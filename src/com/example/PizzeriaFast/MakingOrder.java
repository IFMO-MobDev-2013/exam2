package com.example.PizzeriaFast;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MakingOrder extends Activity {

    boolean newOrder;
    boolean editable;
    Order order;
    EditText modelEditText;
    EditText colorEditText;
    EditText phoneEditText;
    EditText signEditText;
    TextView boxTextView;
    Spinner spinnerTime;
    Spinner spinnerLength;
    int uses[];
    int curTime;
    int ourBoxes;
    ArrayList<CharSequence> array;
    ArrayList<CharSequence> arrayLength;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapterLength;

    int lengthOfOrder;


    View.OnClickListener okClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            order.model = modelEditText.getText().toString();
            order.phone = phoneEditText.getText().toString();
            order.time = spinnerTime.getSelectedItemPosition();
            order.length = spinnerLength.getSelectedItemPosition();
            order.box = uses[curTime];
            for (int i = 0; i < order.length; i++)
                uses[curTime + i]++;
            getIntent().putExtra(Order.TIME, order.time);
            getIntent().putExtra(Order.MODEL, order.model);
            getIntent().putExtra(Order.PHONE, order.phone);
            getIntent().putExtra(Order.LENGTH, order.length);
            getIntent().putExtra(Order.BOX, order.box);


            ContentValues contentValues = order.getContentValues();
            MyDataBasePizzasHelper myDataBasePizzasHelper = new MyDataBasePizzasHelper(getApplicationContext());
            SQLiteDatabase sqLiteDatabase = myDataBasePizzasHelper.getWritableDatabase();
            if(newOrder)
                sqLiteDatabase.insert(MyDataBasePizzasHelper.DATABASE_NAME, null, contentValues);
            else
                sqLiteDatabase.update(MyDataBasePizzasHelper.DATABASE_NAME, contentValues, MyDataBasePizzasHelper._ID + "=" +
                        getIntent().getIntExtra(MyDataBasePizzasHelper._ID, 0), null);

            sqLiteDatabase.close();
            myDataBasePizzasHelper.close();

            finish();
        }
    };

    void nextStep() {
        array = new ArrayList<CharSequence>();

            for (int i = 0; i < 30; i++) {
                boolean q = true;
                for (int j = 0; j < lengthOfOrder; j++)
                    if (uses[i] >= ourBoxes)
                        q = false;
                if (q)
                    array.add(Order.makeTime(i));
            }

        adapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, array);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapter);

        if(!newOrder)
            spinnerTime.setSelection(order.time);
        adapter.notifyDataSetChanged();

        Button button = (Button) findViewById(R.id.buttonMakeOrderOk);
        button.setOnClickListener(okClick);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeorder);

        ActionBar actionBar = getActionBar();
        actionBar.hide();
        newOrder = getIntent().getBooleanExtra(Order.NEWORDER, true);
        editable = getIntent().getBooleanExtra(Order.EDITABLE, true);
        ourBoxes = getIntent().getIntExtra(Order.OURBOXES, 3);

        if (newOrder)
            order = new Order();
        else
            order = new Order(getIntent());

        modelEditText = (EditText) findViewById(R.id.editTextMakeOrderModel);
        phoneEditText = (EditText) findViewById(R.id.editTextMakeOrderPhone);
        boxTextView = (TextView) findViewById(R.id.textViewMakeOrderBox);
        spinnerTime = (Spinner) findViewById(R.id.spinnerTime);
        spinnerLength = (Spinner) findViewById(R.id.spinnerLength);
        uses = getIntent().getIntArrayExtra(Order.USESTABLE);

        modelEditText.setText(order.model);
        phoneEditText.setText(order.phone);

        arrayLength = new ArrayList<CharSequence>();
        adapterLength = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item);
        adapterLength.add("0:30");
        adapterLength.add("1:00");
        adapterLength.add("1:30");

        adapterLength.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterLength.notifyDataSetChanged();
        spinnerLength.setAdapter(adapterLength);
        adapterLength.notifyDataSetChanged();


        modelEditText.setEnabled(editable);
        phoneEditText.setEnabled(editable);
        spinnerLength.setEnabled(editable);

        spinnerTime.setEnabled(false);

        spinnerLength.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lengthOfOrder = position + 1;
                spinnerTime.setEnabled(editable);
                nextStep();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerTime.setEnabled(false);
            }
        });

    }

}
