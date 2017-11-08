package com.example.asus.quakeapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by ASUS on 11/5/2017.
 */

public class customAdapter extends BaseAdapter{
    Context context;
    ArrayList<ItemModel> earthquakelist;
    LayoutInflater inflater;
    public customAdapter(Context applicationContext,ArrayList<ItemModel> itemModelArrayList) {
        this.context=applicationContext;
        this.earthquakelist=itemModelArrayList;
        inflater=LayoutInflater.from(applicationContext);
    }

    @Override
    public int getCount() {
        return earthquakelist.size();
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
        view=inflater.inflate(R.layout.custom_layout,null);
        TextView textView1=(TextView)view.findViewById(R.id.magnitude);
        TextView textView2=(TextView)view.findViewById(R.id.place1);
        TextView textView3=(TextView)view.findViewById(R.id.place2);
        TextView textView4=(TextView)view.findViewById(R.id.date);
        TextView textView5=(TextView)view.findViewById(R.id.time);
        GradientDrawable magnitudeCircle = (GradientDrawable) textView1.getBackground();
        String magnitude=earthquakelist.get(i).getMagnitude();
        String place1=earthquakelist.get(i).getPlace1();
        String place2=earthquakelist.get(i).getPlace2();
        String date=earthquakelist.get(i).getDate();
        String time=earthquakelist.get(i).getTime();
        int magnitudeColor=Functions.getMagnitudeColor(Double.parseDouble(magnitude),context);
        magnitudeCircle.setColor(magnitudeColor);
        textView1.setText(magnitude);
        textView2.setText(place1);
        textView3.setText(place2);
        textView4.setText(date);
        textView5.setText(time);
        return view;
    }
}
