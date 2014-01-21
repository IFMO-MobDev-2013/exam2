package com.example.exam2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.exam1.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kris13
 * Date: 14.01.14
 * Time: 16:29
 * To change this template use File | Settings | File Templates.
 */
public class MyArrayAdapter extends ArrayAdapter<ClientItem> {
    private final Context context;
    private final ArrayList<ClientItem> values;

    public MyArrayAdapter(Context context, ArrayList<ClientItem> values) {
        super(context, R.layout.list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView car = (TextView) rowView.findViewById(R.id.itemcar);
        car.setText(values.get(position).getCar());
        TextView time = (TextView) rowView.findViewById(R.id.itemtime);
        time.setText(values.get(position).getTime());
        TextView box = (TextView) rowView.findViewById(R.id.itembox);
        box.setText(values.get(position).getBox());
        return rowView;
    }
}