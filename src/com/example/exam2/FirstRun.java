package com.example.exam2;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.exam1.R;

/**
 * Created with IntelliJ IDEA.
 * User: kris13
 * Date: 14.01.14
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class FirstRun extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstrun);

        final TextView name = (TextView) findViewById(R.id.marketname);
        MyActivity.namewash.setText(name.getText());
        final TextView box = (TextView) findViewById(R.id.numbercoutier);
        Button ok = (Button) findViewById(R.id.createmarket);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbWash dbWash = new DbWash(FirstRun.this);
                SQLiteDatabase db = dbWash.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(DbWash.NAMEWASH,name.getText().toString());
                cv.put(DbWash.NUMBERBOX,box.getText().toString());
                db.insert(DbWash.TABLE_NAME,null,cv);
                db.close();
                close();
            }
        });
    }
    private void close(){
        super.onBackPressed();
    }
}
