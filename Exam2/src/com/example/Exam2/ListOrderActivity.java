package com.example.Exam2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by asus on 21.01.14.
 */
public class ListOrderActivity extends Activity {
    HashMap<String,String> map;
    ArrayList<HashMap<String,String>> hashMaps = new ArrayList<HashMap<String, String>>();
    ArrayAdapter<String> adapter;
    ArrayList<String>   test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_order);
        test = new ArrayList<String>();
        test.add("lol");
        adapter  = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test);
        ((ListView)findViewById(R.id.listView)).setOnCreateContextMenuListener(this);
       /* SQLiteDatabase database ;
        OrderDataBaseSQLOpenHelper dataBaseSQLOpenHelper = new OrderDataBaseSQLOpenHelper(this);
        database = dataBaseSQLOpenHelper.getWritableDatabase();
       // ContentValues contentValues = new ContentValues();
        Cursor cursor = database.query(OrderDataBaseSQLOpenHelper.TABLE_NAME,null,null,null,null,null,null);

        while (cursor.moveToNext()) {
            String name_pizza = cursor.getString(cursor.getColumnIndex(OrderDataBaseSQLOpenHelper.NAME_PITZZA));
            int number_empoyee = cursor.getInt(cursor.getColumnIndex(OrderDataBaseSQLOpenHelper.NUMBER_EMPLOYEE));
            String time_start  = cursor.getString(cursor.getColumnIndex(OrderDataBaseSQLOpenHelper.START_TIME_DELIVERY));
            map = new HashMap<String, String>();
            map.put("name_pizza",name_pizza);
            map.put("number_empoyee", String.valueOf(number_empoyee)) ;
            map.put("time_start",time_start) ;
            hashMaps.add(map);
        }
        cursor.close();
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                hashMaps,
                R.layout.item,new String[] {
                    "name_pizza",
                    "number_empoyee",
                    "time_start"
                },
                new int[] {
                        R.id.item_name_pizza,
                        R.id.item_number_employee
                        ,R.id.item_start_time
                }
        );*/
        ((ListView) findViewById(R.id.listView)).setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item:
                startActivity(new Intent(this,AddActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(ListOrderActivity.this,EditTimeActivity.class));
                return true;
            }
        });;
        menu.add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                test.remove(acmi.position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });;
    }
}
