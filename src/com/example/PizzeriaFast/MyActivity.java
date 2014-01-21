package com.example.PizzeriaFast;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ActionMode;
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
    ArrayList<Integer> arrayID;
    ArrayList<Integer> arrayID_2;
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
        arrayID = new ArrayList<Integer>();
        arrayID_2 = new ArrayList<Integer>();
        useTime = new int[50];
        for (int i = 0; i < useTime.length; i++)
            useTime[i] = 0;

        while (cursor.moveToNext()) {
            array.add(new Order(cursor.getInt(time_column), cursor.getInt(length_column), cursor.getInt(box_column),
                    cursor.getString(model_column), cursor.getString(phone_column)));
            for (int i = 0; i < cursor.getInt(length_column); i++)
                useTime[cursor.getInt(time_column) + i]++;
            arrayID.add(cursor.getInt(cursor.getColumnIndex(MyDataBasePizzasHelper._ID)));
        }
        cursor.close();
        sqLiteDatabase.close();
        myDataBasePizzasHelper.close();

        boolean use_temp[] = new boolean[100];
        for (int i = 0; i < array.size(); i++)
            use_temp[i] = false;

        adapter = new MyAdapter(getApplicationContext(), R.layout.orderitem, new ArrayList<Order>());
        for (int i = 0; i < array.size(); i++) {
            int j0 = -1;
            for (int j = 0; j < array.size(); j++)
                if (!use_temp[j] && (j0 == -1 || array.get(j).time < array.get(j0).time))
                    j0 = j;
            adapter.add(array.get(j0));
            arrayID_2.add(arrayID.get(j0));
            use_temp[j0] = true;
        }

        listView = (ListView) findViewById(R.id.listViewMain);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(goToOrder);
        listView.setOnItemLongClickListener(goToMenu);

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

    public void goToOrderEditable() {
        Intent intent = new Intent();

        Order order = adapter.getItem(currentPosition);
        intent.putExtra(Order.TIME, order.time);
        intent.putExtra(Order.MODEL, order.model);
        intent.putExtra(Order.PHONE, order.phone);
        intent.putExtra(Order.LENGTH, order.length);
        intent.putExtra(Order.BOX, order.box);

        intent.putExtra(MyDataBasePizzasHelper._ID, arrayID_2.get(currentPosition));

        intent.putExtra(Order.NEWORDER, false);
        intent.putExtra(Order.EDITABLE, true);
        intent.putExtra(Order.USESTABLE, useTime);
        intent.setClass(getApplicationContext(), MakingOrder.class);
        startActivity(intent);

    }

    public void deleteOrder() {
        int delID = arrayID_2.get(currentPosition);

        MyDataBasePizzasHelper myDataBasePizzasHelper = new MyDataBasePizzasHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = myDataBasePizzasHelper.getWritableDatabase();
        sqLiteDatabase.delete(MyDataBasePizzasHelper.DATABASE_NAME, MyDataBasePizzasHelper._ID + "=" + delID, null);
        sqLiteDatabase.close();
        myDataBasePizzasHelper.close();
        showOrders();
    }

    ActionMode mActionMode;
    int currentPosition;

    public AdapterView.OnItemLongClickListener goToMenu = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            currentPosition = position;
            DialogFragment newFragment = new MyAlertDialogFragmentAction();
            newFragment.show(getFragmentManager(), "dialogact");
            return true;
        }
    };

    public class MyAlertDialogFragmentDelete extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Dialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.DeleteButton)
                    .setPositiveButton(R.string.YesChange,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    deleteOrder();
                                }
                            }
                    )
                    .setNegativeButton(R.string.NoChange,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }
                    )
                    .setCancelable(true)
                    .create();
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }

    public class MyAlertDialogFragmentAction extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Dialog dialog = new AlertDialog.Builder(getActivity())
                    .setPositiveButton(R.string.EditButton,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    goToOrderEditable();
                                }
                            }
                    )
                    .setNegativeButton(R.string.DeleteButton,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    DialogFragment newFragment = new MyAlertDialogFragmentDelete();
                                    newFragment.show(getFragmentManager(), "dialog");
                                }
                            }
                    )
                    .setCancelable(true)
                    .create();
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }

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
