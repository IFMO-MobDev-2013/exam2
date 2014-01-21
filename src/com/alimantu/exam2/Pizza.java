package com.alimantu.exam2;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 21.01.14
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class Pizza implements Serializable {

    private String pizzaType;
    private String time;
    private String deliver;
    private String telNumber;

    public Pizza() {
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;

    }

    public String getDeliver() {
        return deliver;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public Pizza(String pizzaType, String time, String deliver) {

        this.pizzaType = pizzaType;
        this.time = time;
        this.deliver = deliver;
    }


}
