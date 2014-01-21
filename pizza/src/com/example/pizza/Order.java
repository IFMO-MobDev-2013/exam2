package com.example.pizza;

public class Order {
    String name;
    int cr, beg, len;

    public Order(String name, int beg, int len, int cr) {
        this.name = name;
        this.cr = cr;
        this.beg = beg;
        this.len = len;
    }
}
