package com.example.exam;

/**
 * Created by Kirill on 14.01.14.
 */
public class Pizza {
    String name, phone, speed, time, adress;
    int cur;
     String id;
    public Pizza(String name, String phone, String speed, String time, int cur, String id, String adress) {
        this.name = name;
        this.speed = speed;
        this.time = time;
        this.cur = cur;
        this.phone = phone;
        this.id = id;
        this.adress = adress;
    }
}
