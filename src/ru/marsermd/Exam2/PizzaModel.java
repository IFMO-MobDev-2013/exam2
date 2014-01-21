package ru.marsermd.Exam2;

import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 14.01.14
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */
public class PizzaModel implements Comparable<PizzaModel>{

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        if (PizzeriaModel.isWorkingTime(time))
            this.time = time;
    }

    public int getAssignedCourier() {
        return assignedCourier;
    }

    public void setAssignedCourier(int assignedCourier) {
        if(assignedCourier > 0 && assignedCourier < PizzeriaModel.getCouriersCount())
            this.assignedCourier = assignedCourier;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    private String model = "";
    private int deliveryTime = 0;
    private String comment = "";
    private String phone = "";
    private int time = 0;
    private int assignedCourier = 0;

    public void setModelV(TextView modelV) {
        this.modelV = modelV;
        if (modelV != null)
        modelV.setText("" + model);
    }

    public void setTimeV(TextView timeV) {
        this.timeV = timeV;
        if (timeV != null)
        timeV.setText(PizzeriaModel.getFormatedTime(time));
    }

    public void setCourierV(TextView courierV) {
        this.courierV = courierV;
        if (courierV != null)
        courierV.setText("" + (assignedCourier + 1));
    }

    private TextView modelV, timeV, courierV;


    @Override
    public int compareTo(PizzaModel pizzaModel) {
        if (pizzaModel.getTime() > getTime())
            return -1;
        if (pizzaModel.getTime() < getTime())
            return 1;
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
