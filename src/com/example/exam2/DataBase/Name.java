package com.example.exam2.DataBase;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 21.01.14
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
public class Name {
    private String name;
    private int number;

    public Name() {}
    public Name(String name, int number) {
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
