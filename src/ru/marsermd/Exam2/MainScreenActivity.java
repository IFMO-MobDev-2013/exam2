package ru.marsermd.Exam2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 14.01.14
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */
public class MainScreenActivity extends Activity{

    TextView name;
    public static DatabaseHelper db;

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        name = (TextView)findViewById(R.id.name);
        name.setText(PizzeriaModel.getName());

        initDatabase();
        addButton = (Button)findViewById(R.id.adding_button);
        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        refresh();
    }

    private void refresh() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddCarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(intent);
            }
        });
        ListView lv = (ListView)findViewById(R.id.car_schedule);
        lv.setAdapter(new CarAdapter(this));
    }

    private void initDatabase() {
        db = new DatabaseHelper(getBaseContext());
    }


}
