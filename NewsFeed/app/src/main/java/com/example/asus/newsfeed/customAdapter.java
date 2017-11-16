package com.example.asus.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/12/2017.
 */

public class customAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sources> sources=new ArrayList<>();
    LayoutInflater layoutInflater;
    public customAdapter(Context context,ArrayList<Sources> source) {
        this.context=context;
        this.sources=source;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sources.size();
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
        view=layoutInflater.inflate(R.layout.custom_adapter_layout,null);
        TextView textView1=(TextView)view.findViewById(R.id.textView1);
        TextView textView2=(TextView)view.findViewById(R.id.textView2);
        String name=sources.get(i).getName();
        String desc=sources.get(i).getDescription();
        textView1.setText(name);
        textView2.setText(desc);
        return view;
    }
}
