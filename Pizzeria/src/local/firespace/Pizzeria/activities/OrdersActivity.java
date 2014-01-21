package local.firespace.Pizzeria.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import local.firespace.Pizzeria.Order;
import local.firespace.Pizzeria.R;
import local.firespace.Pizzeria.databases.OrdersDatabase;

import java.util.*;

public class OrdersActivity extends Activity {

	String pizzeriaName;
	Integer couriersCount;
	ListView orders;
	OrdersDatabase database;
	ArrayList<Order> ordersList;

	private void initialize() {
		orders = (ListView) findViewById(R.id.orders);
		pizzeriaName = getIntent().getStringExtra(Order.KEY_PIZZERIA_NAME);
		couriersCount = getIntent().getIntExtra(Order.KEY_COURIERS_NUMBER, 0);
		orders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(OrdersActivity.this, ShowOrderActivity.class);
				intent.putExtra(Order.KEY_PIZZERIA_NAME, pizzeriaName);
				intent.putExtra(OrdersDatabase.KEY_ID, ordersList.get(position).getID());
				startActivity(intent);
			}
		});
		database = new OrdersDatabase(this);
		ordersList = database.getOrders(pizzeriaName);
		Collections.sort(ordersList, new customComparator());
		ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> temp;
		for (Order anOrdersList : ordersList) {
			temp = new HashMap<String, String>();
			temp.put(OrdersDatabase.KEY_PIZZA_NAME, anOrdersList.getPizzaName());
			temp.put(OrdersDatabase.KEY_COURIER_NUMBER, anOrdersList.getCourierNumb().toString());
			temp.put(OrdersDatabase.KEY_ORDER_TIME, anOrdersList.getOrderTime());
			data.add(temp);
		}
		String[] from = {OrdersDatabase.KEY_PIZZA_NAME, OrdersDatabase.KEY_COURIER_NUMBER, OrdersDatabase.KEY_ORDER_TIME};
		int[] to = {R.id.sort, R.id.courNumb, R.id.time};
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.listitem, from, to);
		orders.setAdapter(adapter);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orders_layout);
		initialize();
	}

	public void addOrder(View view) {
		Intent intent = new Intent(OrdersActivity.this, AddOrderActivity.class);
		startActivity(intent);
	}

	public class customComparator implements Comparator<Order> {

		@Override
		public int compare(Order lhs, Order rhs) {
			return lhs.getOrderTime().compareTo(rhs.getOrderTime());
		}
	}
}