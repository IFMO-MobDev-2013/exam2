package com.mikhov.pizza;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Act2 extends ListActivity {
    Database database;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor;
    TextView tv;
    String myOrders;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act2);

        database = new Database(this);
        database.open();
        database.drop();

        /*database.addOrder("+79522418693", "Peperony", "30 min", "10:00", 3);
        database.addOrder("+79522418693", "Peperony", "30 min", "23:00", 3);
        database.addOrder("+79522418693", "Peperony", "30 min", "11:30", 3);
        database.addOrder("+79522418693", "Peperony", "30 min", "11:03", 3);
        database.addOrder("+79522418693", "Peperony", "30 min", "11:00", 3); */

        tv = (TextView) findViewById(R.id.empty);
        if (database.isNotEmpty()) {
           //myOrders = database.getOrdersInfo();
           tv.setText("");
        } else {
            tv.setText("Base is empty now. You may add your first order...");
        }

        fillData();
    }

    private void addOrder() {
        Intent add = new Intent(this, OrderAdd.class);
        startActivityForResult(add, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                addOrder();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                addOrder();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }

    private void fillData() {
        cursor = database.getAllData();
        startManagingCursor(cursor);
        String[] from = new String[] { Database.COL_PIZZA, Database.COL_COURIER, Database.COL_TIME };
        int[] to = new int[] { R.id.pizza, R.id.courier, R.id.time };
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        setListAdapter(simpleCursorAdapter);
    }
}