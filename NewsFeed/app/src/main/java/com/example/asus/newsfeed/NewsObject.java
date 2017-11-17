package com.example.asus.newsfeed;

import android.graphics.Bitmap;

/**
 * Created by ASUS on 11/14/2017.
 */

public class NewsObject {
    public String source_id;
    public String source_name;
    public String author;
    public String title;
    public String description;
    public String url;
    public String urltoimage;
    public Bitmap bitmap;
    public String publishedat;

    public NewsObject(String source_id,String source_name, String author, String title, String description, String url, String urltoimage, String publishedAt, Bitmap bitmap) {
        this.source_id = source_id;
        this.source_name=source_name;
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

    public String getSource_id() {
        return source_id;
    }

    public String getSource_name() {
        return source_name;
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
