package com.example.asus.newsfeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Country_selector extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_selector);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        sharedPreferences=getSharedPreferences(KeyValue.MY_PREF,MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void india(View view) {
        editor.putString(KeyValue.MY_COUNTRY,"INDIA");
        editor.commit();
        Intent intent=new Intent(getApplicationContext(),Country_News.class);
        startActivity(intent);
    }

    public void usa(View view) {
        editor.putString(KeyValue.MY_COUNTRY,"USA");
        editor.commit();
        Intent intent=new Intent(getApplicationContext(),Country_News.class);
        startActivity(intent);
    }

    public void australia(View view) {
        editor.putString(KeyValue.MY_COUNTRY,"AUSTRALIA");
        editor.commit();
        Intent intent=new Intent(getApplicationContext(),Country_News.class);
        startActivity(intent);
    }

    public void italy(View view) {
        editor.putString(KeyValue.MY_COUNTRY,"ITALY");
        editor.commit();
        Intent intent=new Intent(getApplicationContext(),Country_News.class);
        startActivity(intent);
    }

    public void uk(View view) {
        editor.putString(KeyValue.MY_COUNTRY,"UK");
        editor.commit();
        Intent intent=new Intent(getApplicationContext(),Country_News.class);
        startActivity(intent);
    }
}
