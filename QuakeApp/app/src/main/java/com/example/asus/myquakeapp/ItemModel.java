package com.example.asus.myquakeapp;

/**
 * Created by ASUS on 11/5/2017.
 */

public class ItemModel {
    String magnitude;
    String place1;
    String place2;
    String date;
    String time;
    String url;

    public ItemModel(String magnitude, String place1, String place2, String date, String time, String url) {
        this.magnitude = magnitude;
        this.place1 = place1;
        this.place2 = place2;
        this.date = date;
        this.time = time;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getTime() {
        return time;
    }

    public String getPlace1() {
        return place1;
    }

    public String getPlace2() {
        return place2;
    }

    public String getDate() {
        return date;
    }
}
