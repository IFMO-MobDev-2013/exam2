package com.alimantu.exam2;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 21.01.14
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */
public class PizzaDelivery {
    private String name;
    private int number;

    public PizzaDelivery() {
    }

    public PizzaDelivery(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
