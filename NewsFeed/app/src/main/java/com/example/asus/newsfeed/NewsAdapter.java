package com.example.asus.newsfeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ASUS on 11/14/2017.
 */

public class NewsAdapter extends BaseAdapter {

    Context context;
    ArrayList<NewsObject> newsObjects=new ArrayList<>();
    LayoutInflater layoutInflater;

    public NewsAdapter(Context context, ArrayList<NewsObject> newsObjects) {
        this.context = context;
        this.newsObjects = newsObjects;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=layoutInflater.inflate(R.layout.news_adapter_layout,null);
        //TextView textView1=(TextView)view.findViewById(R.id.source);
        TextView textView2=(TextView)view.findViewById(R.id.author);
        TextView textView3=(TextView)view.findViewById(R.id.title);
        TextView textView4=(TextView)view.findViewById(R.id.description);
        //TextView textView5=(TextView)view.findViewById(R.id.url);
        TextView textView7=(TextView)view.findViewById(R.id.published);
        ImageView imageView=(ImageView)view.findViewById(R.id.urltoimage);
        String source=newsObjects.get(i).getNews_source();
        String author=newsObjects.get(i).getAuthor();
        String title=newsObjects.get(i).getTitle();
        String desc=newsObjects.get(i).getDescription();
        String url=newsObjects.get(i).getUrl();
        //String imageurl=newsObjects.get(i).getUrltoimage();
        String published=newsObjects.get(i).getPublishedat();
        Bitmap bitmap=newsObjects.get(i).getBitmap();
        //textView1.setText(source);
        textView2.setText(author);
        textView3.setText(title);
        textView4.setText(desc);
        //textView5.setText(url);
        textView7.setText(published);
        imageView.setImageBitmap(bitmap);
        return view;
    }

}
