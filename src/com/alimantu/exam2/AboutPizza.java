package com.alimantu.exam2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 21.01.14
 * Time: 15:55
 * To change this template use File | Settings | File Templates.
 */
public class AboutPizza extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.about_pizza);
        Intent intent = getIntent();
        Pizza pizza = (Pizza) intent.getSerializableExtra("result");
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);

        textView.setText(pizza.getPizzaType());
        textView1.setText(pizza.getTelNumber());
        textView2.setText(pizza.getDeliver());
        textView3.setText(pizza.getTime());
    }
}
