package local.firespace.Pizzeria.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import local.firespace.Pizzeria.R;
import local.firespace.Pizzeria.databases.OrdersDatabase;

public class AddOrderActivity extends Activity {

	Spinner timeSpinner;
	Spinner speedSpinner;
	EditText editTextPizzaSort;
	EditText editTextTelephoneNumber;
	OrdersDatabase database;

	private void initialize() {
		timeSpinner = (Spinner) findViewById(R.id.time);
		speedSpinner = (Spinner) findViewById(R.id.speed);
		editTextTelephoneNumber = (EditText) findViewById(R.id.phone);
		editTextPizzaSort = (EditText) findViewById(R.id.sort);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_order_layout);
		initialize();
	}

	public void addOrder(View view) {
	}
}