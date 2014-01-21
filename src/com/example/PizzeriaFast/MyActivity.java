package com.example.PizzeriaFast;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyActivity extends Activity {

    public static final int MAXBOXES = 20;

    String ourName = "";

    MyAdapter adapter;
    ArrayList<Order> array;
    ListView listView;
    int useTime[];

    class MyAdapter extends ArrayAdapter<Order> {
        private Context context;

        public MyAdapter(Context context, int textViewResourceId, ArrayList<Order> items) {
            super(context, textViewResourceId, items);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.orderitem, null);
            TextView textViewOrderTime = (TextView) rowView.findViewById(R.id.textViewOrderTime);
            TextView textViewOrderBox = (TextView) rowView.findViewById(R.id.textViewOrderBox);
            TextView textViewOrderModel = (TextView) rowView.findViewById(R.id.textViewOrderModel);

            Order item = getItem(position);

            if (item == null) {
                return rowView;
            }
            textViewOrderTime.setText(Order.makeTime(item.time) + "");
            textViewOrderBox.setText((item.box + 1) + "");
            textViewOrderModel.setText(item.model);

            return rowView;
        }
    }

    void checkFirstTime() {
        MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(myDataBaseHelper.DATABASE_NAME, null, null, null, null, null, null);
        ourName = "";
        int ourBoxes = 0;
        while (cursor.moveToNext()) {
            ourName = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.NAME));
            try {
                ourBoxes = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COURIERS)));
            } catch (Exception e) {
                ourBoxes = 0;
                System.out.println("OOOOOu  bad E");
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        myDataBaseHelper.close();
        if ("".equals(ourName) || ourBoxes <= 0) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), FirstTimeActivity.class);
            startActivity(intent);
        }
    }

    void showOrders() {
        MyDataBasePizzasHelper myDataBasePizzasHelper = new MyDataBasePizzasHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = myDataBasePizzasHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(myDataBasePizzasHelper.DATABASE_NAME, null, null, null, null, null, null);

        int time_column = cursor.getColumnIndex(MyDataBasePizzasHelper.TIME);
        int box_column = cursor.getColumnIndex(MyDataBasePizzasHelper.BOX);
        int model_column = cursor.getColumnIndex(MyDataBasePizzasHelper.MODEL);
        int length_column = cursor.getColumnIndex(MyDataBasePizzasHelper.LENGTH);
        int phone_column = cursor.getColumnIndex(MyDataBasePizzasHelper.PHONE);

        array = new ArrayList<Order>();
        useTime = new int[50];
        for (int i = 0; i < useTime.length; i++)
            useTime[i] = 0;

        while (cursor.moveToNext()) {
            array.add(new Order(cursor.getInt(time_column), cursor.getInt(length_column), cursor.getInt(box_column),
                    cursor.getString(model_column), cursor.getString(phone_column)));
            for (int i = 0; i < cursor.getInt(length_column); i++)
                useTime[cursor.getInt(time_column) + i]++;
        }
        cursor.close();
        sqLiteDatabase.close();
        myDataBasePizzasHelper.close();

        adapter = new MyAdapter(getApplicationContext(), R.layout.orderitem, array);

        listView = (ListView) findViewById(R.id.listViewMain);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(goToOrder);

        adapter.notifyDataSetChanged();
    }

    public AdapterView.OnItemClickListener goToOrder = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {

            Intent intent = new Intent();

            Order order = array.get(position);
            intent.putExtra(Order.TIME, order.time);
            intent.putExtra(Order.MODEL, order.model);
            intent.putExtra(Order.PHONE, order.phone);
            intent.putExtra(Order.LENGTH, order.length);
            intent.putExtra(Order.BOX, order.box);

            intent.putExtra(Order.NEWORDER, false);
            intent.putExtra(Order.EDITABLE, false);
            intent.putExtra(Order.USESTABLE, useTime);
            intent.setClass(getApplicationContext(), MakingOrder.class);
            startActivity(intent);
        }
    };

    public void newOrder(View view) {
        Intent intent = new Intent();

        intent.putExtra(Order.NEWORDER, true);
        intent.putExtra(Order.EDITABLE, true);
        intent.putExtra(Order.USESTABLE, useTime);
        intent.setClass(getApplicationContext(), MakingOrder.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        showOrders();
    }

    @Override
    public void onResume() {
        super.onResume();
        showOrders();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        checkFirstTime();
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(ourName);
        showOrders();
    }
}
