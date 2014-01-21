package ru.marsermd.Exam2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 14.01.14
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
public class AddPizzaActivity extends Activity{

    Spinner washTime;
    EditText model, comment, phoneNumber;
    TextView deliveryTimeViewer;
    SeekBar deliveryTime;
    Button carAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pizza);

        washTime = (Spinner) findViewById(R.id.start_time_edit);

        model = (EditText)findViewById(R.id.pizza_model_edit);
        deliveryTime = (SeekBar)findViewById(R.id.pizza_delivery_time_edit);
        comment = (EditText)findViewById(R.id.pizza_comment_edit);
        phoneNumber = (EditText)findViewById(R.id.phone_number_edit);
        deliveryTimeViewer = (TextView) findViewById(R.id.delivery_time_viewer);

        deliveryTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                refreshDeliveryTime();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        carAdd = (Button)findViewById(R.id.add_car_edit);
        carAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PizzaModel pizza = new PizzaModel();
                pizza.setModel(model.getText().toString());
                int deliveryTimeValue = deliveryTime.getProgress() + 1;
                pizza.setDeliveryTime(deliveryTimeValue);
                pizza.setComment(comment.getText().toString());
                pizza.setPhone(phoneNumber.getText().toString());
                int time = PizzeriaModel.deformatTime(washTime.getSelectedItem().toString());
                pizza.setTime(time);
                pizza.setAssignedCourier(MainScreenActivity.db.getCourierForTime(pizza.getTime(), pizza.getDeliveryTime()));
                MainScreenActivity.db.addPizza(pizza);
                finish();
            }
        });

    }

    private void refreshDeliveryTime() {
        int deliveryTimeValue = deliveryTime.getProgress() + 1;
        Integer[] tmpTime = MainScreenActivity.db.getFreeTime(deliveryTimeValue);
        String[] freeTime = new String[tmpTime.length];

        for (int i = 0; i < freeTime.length; i++) {
            freeTime[i] = PizzeriaModel.getFormatedTime(tmpTime[i]);
        }
        washTime.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, freeTime));

        deliveryTimeViewer.setText(PizzeriaModel.getFormatedTimeMinutes(deliveryTimeValue));
    }
}
