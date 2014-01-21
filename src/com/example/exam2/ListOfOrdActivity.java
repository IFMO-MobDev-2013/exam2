package com.example.exam2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.exam2.DataBase.NameDB;
import com.example.exam2.DataBase.ListDB;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 21.01.14
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class ListOfOrdActivity extends Activity implements View.OnClickListener {


    private long carIndex;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listoford);


        Intent intent = getIntent();
        carIndex = intent.getLongExtra("id", 0);

        ImageButton addorder = (ImageButton) findViewById(R.id.addorder);
        addorder.setOnClickListener(this);

        TextView companyname = (TextView) findViewById(R.id.companyname);
        NameDB database = new NameDB(this);
        database.open();

        Cursor c = database.getAllData();
        c.moveToFirst();
        int compname = c.getColumnIndex("name");
        companyname.setText(c.getString(compname));



        ListView listView = (ListView) findViewById(R.id.listView);
        String[] from = new String[] {
                ListDB.COLUMN_NAME,
                ListDB.COLUMN_STARTTIME,
        };
        int[] to = new int[] {
                R.id.pizzasname,
                R.id.pizzastime,
        };
        ListDB db = new ListDB(this);
        db.open();
        Cursor cursor = db.getAllData();
        startManagingCursor(cursor);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to);
        listView.setAdapter(adapter);
        //-----
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pizzasname = ((TextView)view.findViewById(R.id.pizzasname)).getText().toString();
                String numberofcourier = ((TextView)findViewById(R.id.numberofcourier)).getText().toString();
                String pizzastime = ((TextView)view.findViewById(R.id.pizzastime)).getText().toString();

                Intent intent = new Intent(view.getContext(), MoreInformActivity.class);
                intent.putExtra("name", pizzasname);
                //место для номера курьера
                intent.putExtra("starttime", pizzastime);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addorder:
                Intent intent = new Intent(this, AddPizzaActivity.class);
                startActivity(intent);
                break;
        }



    }
}
