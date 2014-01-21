package com.example.exam2;

/**
 * Created with IntelliJ IDEA.
 * User: kris13
 * Date: 14.01.14
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */
public class ClientItem {
    public String mark;
    public String place;
    public String sign;
    public String telephone;
    public String time;
    public String courier;
    public String delivery;

    public ClientItem(String mark,String telephone, String place, String delivery, String time,String courier){
        this.place = place;
        this.mark = mark;
        this.delivery = delivery;
        this.telephone = telephone;
        this.time = time;
        this.courier = courier;
    }

    public String getCar(){
        return mark;
    }
    public String getTime(){
        return time;
    }
    public String getBox(){
        return courier;
    }
}
