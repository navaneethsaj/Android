package com.example.asus.newsfeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        imageView=(ImageView)findViewById(R.id.countrypic);
        SharedPreferences sharedPreferences=getSharedPreferences(KeyValue.MY_PREF,MODE_PRIVATE);
        String country=sharedPreferences.getString(KeyValue.MY_COUNTRY,"");
        switch (country){
            case "INDIA":
                imageView.setImageResource(R.drawable.indiaflag);
                break;
            case "USA":
                imageView.setImageResource(R.drawable.usaflag);
                break;
            case "UK":
                imageView.setImageResource(R.drawable.ukflag);
                break;
            case "ITALY":
                imageView.setImageResource(R.drawable.italyflag);
                break;
            case "AUSTRALIA":
                imageView.setImageResource(R.drawable.austrailiaflag);
                break;
        }
    }

    public void backtolocal(View view) {
        finish();
    }

    public void changecountry(View view) {
        Intent intent=new Intent(getApplicationContext(),Country_selector.class);
        startActivity(intent);
    }

    public void gotocredits(View view) {
        Intent intent=new Intent(this,Credits.class);
        startActivity(intent);
    }
}
