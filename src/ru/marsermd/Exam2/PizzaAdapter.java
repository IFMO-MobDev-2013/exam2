package ru.marsermd.Exam2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 14.01.14
 * Time: 18:18
 * To change this template use File | Settings | File Templates.
 */
public class PizzaAdapter extends BaseAdapter{
    private List<PizzaModel> list;
    private int page = 0;

    public void init() {
        list.clear();
        page = 0;
        count = 0;
    }

    private Activity context;
    private int count;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public PizzaAdapter(Activity context) {
        super();
        this.context = context;
        list = MainScreenActivity.db.getAllPizzas();
    }

    private class ViewHolder {
        TextView modelView;
        TextView timeView;
        TextView courierView;
        PizzaModel model;

        public ViewHolder(TextView modelView, TextView timeView, TextView courierView) {
            this.modelView = modelView;
            this.timeView = timeView;
            this.courierView = courierView;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.pizza_row, null);
            TextView model = (TextView) convertView.findViewById(R.id.pizza_model);
            TextView time = (TextView) convertView.findViewById(R.id.pizza_time);
            TextView courier = (TextView) convertView.findViewById(R.id.courier_id);
            ViewHolder viewHolder = new ViewHolder(model, time, courier);
            convertView.setTag(viewHolder);
            holder = viewHolder;
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (holder.model != null) {
            //holder.model.cancel();
        }
        holder.model = list.get(position);
        holder.model.setModelV(holder.modelView);
        holder.model.setCourierV(holder.courierView);
        holder.model.setTimeV(holder.timeView);
        return convertView;
    }
}
