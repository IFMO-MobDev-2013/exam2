package com.example.exam2;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.exam1.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kris13
 * Date: 14.01.14
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
public class AddClient extends Activity {
    protected int curDelivPosition = 0;
    protected int curPosition = 0;
    protected String[] timeDilev = new String[]{"00:30", "01:00", "01:30"};
    protected boolean unlock = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclient);

        final EditText mark = (EditText) findViewById(R.id.pizzaname);
        final EditText color = (EditText) findViewById(R.id.telephone);
        final EditText sign = (EditText) findViewById(R.id.place);
        Spinner delivery = (Spinner) findViewById(R.id.spinner);
        final Spinner time = (Spinner) findViewById(R.id.spinner1);
        Button create = (Button) findViewById(R.id.addclient);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getFreeTime());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(adapter);
        time.setEnabled(false);
        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                curPosition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ArrayAdapter<String> adapterDelivery = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeDilev);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        delivery.setAdapter(adapterDelivery);

        delivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                curDelivPosition = position;
                unlock = true;
                time.setEnabled(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbClient dbClient = new DbClient(AddClient.this);
                SQLiteDatabase db = dbClient.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(DbClient.PIZZA, mark.getText().toString());
                cv.put(DbClient.TELEPHONE, color.getText().toString());
                cv.put(DbClient.PLACE, sign.getText().toString());
                cv.put(DbClient.DELIVERY, timeDilev[curDelivPosition]);
                cv.put(DbClient.TIME, getFreeTime().get(curPosition));
                cv.put(DbClient.COURIER, getFreeBox(getFreeTime().get(curPosition)));
                db.insert(DbClient.TABLE_NAME,null,cv);
                db.close();

                close();
            }
        });
    }
    private void close(){
        MyActivity.refresh();
        MyActivity.myArrayAdapter = new MyArrayAdapter(this, MyActivity.clientItems);
        MyActivity.myArrayAdapter.notifyDataSetChanged();
        MyActivity.listClient.setAdapter(MyActivity.myArrayAdapter);
        super.onBackPressed();
    }

    private String getFreeBox(String s) {
        boolean[] use = new boolean[MyActivity.numberBox];
        DbClient dbClient = new DbClient(AddClient.this);
        SQLiteDatabase db = dbClient.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbClient.TABLE_NAME + " WHERE " + DbClient.TIME + " = '" + s + "'",null);
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            use[Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbClient.COURIER))) - 1] = true;
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        for (int i=0;i<use.length;i++)
            if (!use[i])
                return ((Integer)(i+1)).toString();
        return ((Integer)(0)).toString();
    }

    private ArrayList<String> getFreeTime() {
        String[] times = new String[]{"10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30", "22:00", "22:30", "23:00", "23:30", "24:00"};
        HashMap<String,Integer> hashMap = new HashMap<String, Integer>();
        DbClient dbClient = new DbClient(AddClient.this);
        SQLiteDatabase db = dbClient.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbClient.TABLE_NAME,null);
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            String time = cursor.getString(cursor.getColumnIndex(DbClient.TIME));
            hashMap.put(time,(hashMap.get(time) == null?0:hashMap.get(time)) + 1);
            cursor.moveToNext();
        }
        ArrayList<String> ans = new ArrayList<String>();
        for (String time : times){
            if (hashMap.get(time) == null || hashMap.get(time) < MyActivity.numberBox)
                ans.add(time);
        }
        cursor.close();
        db.close();
        return ans;  //To change body of created methods use File | Settings | File Templates.
    }
}
