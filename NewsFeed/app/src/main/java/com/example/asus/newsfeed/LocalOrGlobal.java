package com.example.asus.newsfeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LocalOrGlobal extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_or_global);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        sharedPreferences=getSharedPreferences(KeyValue.MY_PREF,MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void globalclick(View view) {
        editor.putString(KeyValue.REGION,"global");
        editor.commit();
        Toast.makeText(getApplicationContext(),"Global",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getApplicationContext(),WorldTop.class);
        startActivity(intent);
    }

    public void localclick(View view) {
        editor.putString(KeyValue.REGION,"local");
        Intent intent=new Intent(getApplicationContext(),Country_selector.class);
        startActivity(intent);
        editor.commit();
        Toast.makeText(getApplicationContext(),"Local",Toast.LENGTH_SHORT).show();
    }

}
