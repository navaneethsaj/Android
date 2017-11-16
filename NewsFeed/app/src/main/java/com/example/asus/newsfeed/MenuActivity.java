package com.example.asus.newsfeed;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void backtolocal(View view) {
        Intent intent=new Intent(getApplicationContext(),LocalNews.class);
        startActivity(intent);
    }

    public void changecountry(View view) {
        Intent intent=new Intent(getApplicationContext(),Country_selector.class);
        startActivity(intent);
    }
}
