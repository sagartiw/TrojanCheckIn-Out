package com.team13.trojancheckin_out.Layouts;

public class Product {
    private int id;
    private String title;
    private int current;
    private int capacity;

    public Product(int id, String title, int current, int capacity) {
        this.id = id;
        this.title = title;
        this.current = current;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCurrent() {
        return current;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPercent() {

        double cur = (double) current;
        double cap = (double) capacity;
        double perc = (cur/cap)*100;
        int percent = (int) perc;
        return percent;
    }

}
