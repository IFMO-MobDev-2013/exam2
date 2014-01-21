package com.example.AfterExam1;

public class Order {
    String pizzaName;
    String telephoneNumber;
    String timeToClient;
    String pizzaPreferTime;
    String curier;


    public Order(String pizzaName, String telephoneNumber, String timeToClient, String pizzaPreferTime, String curier) {
        this.pizzaName = pizzaName;
        this.telephoneNumber = telephoneNumber;
        this.timeToClient = timeToClient;
        this.pizzaPreferTime = pizzaPreferTime;
        this.curier = curier;
    }
}
