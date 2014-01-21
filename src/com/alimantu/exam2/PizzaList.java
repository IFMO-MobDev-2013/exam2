package com.alimantu.exam2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 21.01.14
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class PizzaList extends Activity {

    public static final int DELETE = 0;

    private TextView textView;
    private ListView listView;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private PizzaType database;
    private Button button;
    static ArrayList<Pizza> pizzas;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.pizzas_list);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        textView = (TextView) findViewById(R.id.pizzaType);
        textView.setText(name);
        listView = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.addButton);

        database = new PizzaType(this);
        database.open();

        cursor = database.getAllData();
        pizzas = database.getAll();
        startManagingCursor(cursor);

        String[] from = new String[]{
                PizzaType.COLUMN_PIZZA,
                PizzaType.COLUMN_DELIVER,
                PizzaType.COLUMN_TIME
        };

        int[] to = new int[]{
                R.id.pizzaTypeName,
                R.id.pizzaDeliver,
                R.id.pizzaTime
        };

        adapter = new SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(view.getContext(), AddPizzaActivity.class);
                startActivity(nextIntent);
            }
        });

        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = ((TextView) view.findViewById(R.id.pizzaTypeName)).getText().toString();
                PizzaType pizzaType = new PizzaType(view.getContext());
                pizzaType.open();
                Pizza result = pizzaType.selectPizzas(name);
                Intent currentIntent = new Intent(view.getContext(), AboutPizza.class);
                currentIntent.putExtra("result", result);
                startActivity(currentIntent);
            }

        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);    //To change body of overridden methods use File | Settings | File Templates.
        menu.add(0, DELETE, 0, getString(R.string.delete_pizza));
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (DELETE == item.getItemId()) {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            database.deleteChannel((int) adapterContextMenuInfo.id);

            PizzaType database1 = new PizzaType(this);
            database1.open();
            database1.deleteChannel(adapterContextMenuInfo.id);

            cursor.requery();
            pizzas = database1.getAll();

            database1.close();
            return true;
        }

        return super.onContextItemSelected(item);
    }

}
