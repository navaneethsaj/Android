package com.example.asus.newsfeed;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/12/2017.
 */

public class Sources {
    private String id;
    private String name;
    private String description;
    private String  url;
    private String category;
    private String language;
    private String country;
    private ArrayList<String > sortBysAvailable =new ArrayList<>();

    public Sources(String id, String name, String description, String url, String category, String language, String country, ArrayList<String> sortBysAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
        this.sortBysAvailable = sortBysAvailable;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public ArrayList<String> getSortBysAvailable() {
        return sortBysAvailable;
    }
}
