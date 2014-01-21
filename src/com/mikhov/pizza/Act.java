package com.mikhov.pizza;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Act extends Activity implements View.OnClickListener {

    public static enum TransitionType {
        SlideLeft
    }
    public static TransitionType transitionType;

    private Button btnSlideLeftTransition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnSlideLeftTransition = (Button)findViewById(R.id.btnSlideLeftTransition);
        btnSlideLeftTransition.setOnClickListener(this);
    }

    public void onClick(View v) {
        this.finish();

        Intent intent = new Intent(this, Act2.class);
        startActivity(intent);

        switch (v.getId()) {
            case R.id.btnSlideLeftTransition:
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
                break;
        }
    }
}