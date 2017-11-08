package com.example.asus.json;

/**
 * Created by ASUS on 11/3/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<MovieModel> movieList=new ArrayList<>();
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<MovieModel> countryList) {
        this.context = context;
        this.movieList = countryList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return movieList.size();
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
        view = inflter.inflate(R.layout.custom_layout, null);
        TextView movie = (TextView) view.findViewById(R.id.movies);
        TextView year = (TextView) view.findViewById(R.id.year);
        TextView director=(TextView) view.findViewById(R.id.director);
        TextView genre=(TextView) view.findViewById(R.id.genre);
        TextView duration=(TextView)view.findViewById(R.id.duration);
        TextView country=(TextView)view.findViewById(R.id.country);
        RatingBar ratingBar=(RatingBar)view.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        movie.setText(movieList.get(i).getMovie());
        year.setText(movieList.get(i).getYear());
        director.setText(movieList.get(i).getDirector());
        genre.setText(movieList.get(i).getStory());
        duration.setText(movieList.get(i).getScreenplay());
        country.setText(movieList.get(i).getCast());
        return view;
    }
}