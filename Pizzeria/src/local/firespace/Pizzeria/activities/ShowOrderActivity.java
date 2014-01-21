package local.firespace.Pizzeria.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import local.firespace.Pizzeria.Order;
import local.firespace.Pizzeria.R;
import local.firespace.Pizzeria.databases.OrdersDatabase;

public class ShowOrderActivity extends Activity {

	TextView sort;
	TextView cour_numb;
	TextView time;
	TextView phone;
	TextView speed;
	OrdersDatabase database;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_order_layout);
		sort = (TextView) findViewById(R.id.sort);
		cour_numb = (TextView) findViewById(R.id.cour_numb);
		time = (TextView) findViewById(R.id.time);
		phone = (TextView) findViewById(R.id.phone);
		speed = (TextView) findViewById(R.id.speed);
		String pizzeria = getIntent().getStringExtra(Order.KEY_PIZZERIA_NAME);
		Integer ID = getIntent().getIntExtra(OrdersDatabase.KEY_ID, 0);
		database = new OrdersDatabase(this);
		database.open();
		Order order = database.getOrderByID(pizzeria, ID);
		sort.setText(order.getPizzaName());
		cour_numb.setText(order.getCourierNumb().toString());
		time.setText(order.getOrderTime());
		phone.setText(order.getTelephoneNumber());
		speed.setText(order.getOrderSpeed());
	}
}