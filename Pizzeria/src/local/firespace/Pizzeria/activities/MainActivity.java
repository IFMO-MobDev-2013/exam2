package local.firespace.Pizzeria.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import local.firespace.Pizzeria.Order;
import local.firespace.Pizzeria.R;

public class MainActivity extends Activity {
	private static final String DEFAULT_PIZZERIA_NAME = "Пицца вкусная";
	private static final Integer DEFAULT_COURIERS_NUMBER = 3;

	EditText pizzeriaName, couriersNumber;

	private void initialize() {
		pizzeriaName = (EditText) findViewById(R.id.pizzNameEdit);
		couriersNumber = (EditText) findViewById(R.id.curNumbEdit);
		couriersNumber.setText(DEFAULT_COURIERS_NUMBER.toString());
		pizzeriaName.setText(DEFAULT_PIZZERIA_NAME);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);
		initialize();
	}

	public void enterName(View view) {
		@SuppressWarnings("ConstantConditions") String pizzName = pizzeriaName.getText().toString();
		@SuppressWarnings("ConstantConditions") Integer courNumb = Integer.parseInt(couriersNumber.getText().toString());
		Intent intent = new Intent(MainActivity.this, OrdersActivity.class);
		intent.putExtra(Order.KEY_PIZZERIA_NAME, pizzName);
		intent.putExtra(Order.KEY_COURIERS_NUMBER, courNumb);
		startActivity(intent);
	}
}