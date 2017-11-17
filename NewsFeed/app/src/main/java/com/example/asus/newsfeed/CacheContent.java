package com.example.asus.newsfeed;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ASUS on 11/17/2017.
 */

public class CacheContent implements Serializable{
    ArrayList<NewsObject> newsObjectArrayListCache;

    public CacheContent(ArrayList<NewsObject> newsObjectArrayListCache) {
        this.newsObjectArrayListCache = newsObjectArrayListCache;
    }

    public ArrayList<NewsObject> getNewsObjectArrayListCache() {
        return newsObjectArrayListCache;
    }
}
