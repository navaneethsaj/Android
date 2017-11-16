package com.example.asus.newsfeed;

import android.graphics.Bitmap;

/**
 * Created by ASUS on 11/14/2017.
 */

public class NewsObject {
    public String news_source;
    public String author;
    public String title;
    public String description;
    public String url;
    public String urltoimage;
    public Bitmap bitmap;
    public String publishedat;

    public NewsObject(String news_source, String author, String title, String description, String url, String urltoimage, String publishedAt, Bitmap bitmap) {
        this.news_source = news_source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urltoimage = urltoimage;
        this.bitmap = bitmap;
        this.publishedat = publishedAt;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getNews_source() {
        return news_source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrltoimage() {
        return urltoimage;
    }

    public String getPublishedat() {
        return publishedat;
    }
}
