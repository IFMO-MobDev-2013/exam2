package com.example.AfterExam1;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    LayoutInflater lInflater;
    ArrayList<Order> objects;

    CustomAdapter(Context _context, ArrayList<Order> products) {
        context = _context;
        objects = products;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private String minus(String timeLeft, String timeRight) {
        int hourLeft = Integer.parseInt(timeLeft.substring(0, timeLeft.indexOf(":")));
        int minuteLeft = Integer.parseInt(timeLeft.substring(timeLeft.indexOf(":") + 1, timeLeft.length()));
        int hourRight = Integer.parseInt(timeRight.substring(0, timeRight.indexOf(":")));
        int minuteRight = Integer.parseInt(timeRight.substring(timeRight.indexOf(":") + 1, timeRight.length()));
        if ( (minuteLeft - minuteRight) < 0) {
            minuteLeft += 60;
            hourLeft--;
        }
        String result = "";
        if (hourLeft - hourRight < 10)
            result = result + (hourLeft - hourRight) + "0";
        else
            result = result + (hourLeft - hourRight);
        result += ":";
        if ( (minuteLeft - minuteRight) < 10)
            result = result + "0" +  (minuteLeft - minuteRight);
        else
            result = result +  (minuteLeft - minuteRight);
        return result;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Order p = getOrder(position);

        ((TextView) view.findViewById(R.id.text_pizza_name)).setText(p.pizzaName);
        ((TextView) view.findViewById(R.id.text_telephone_number)).setText(p.curier);
        ((TextView) view.findViewById(R.id.text_curier)).setText(minus(p.pizzaPreferTime, p.timeToClient));
        return view;
    }

    Order getOrder(int position) {
        return ((Order) getItem(position));
    }

    DialogInterface.OnClickListener myclick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            int asd = which;
            int asda = which;
        }
    };


}