package com.example.asus.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        sharedPreferences = getSharedPreferences(KeyValue.MY_PREF, MODE_PRIVATE);
        if (isNetworkConnected()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (sharedPreferences.getString(KeyValue.REGION, "").equals("")) {
                        Intent intent = new Intent(getApplicationContext(), Country_selector.class);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(KeyValue.REGION,"local");
                        editor.commit();
                        startActivity(intent);
                    } else if (sharedPreferences.getString(KeyValue.REGION, "").equals("local") == true) {
                        if (sharedPreferences.getString(KeyValue.MY_COUNTRY,"").equals("")){
                            Intent intent = new Intent(getApplicationContext(), Country_selector.class);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(), LocalNews.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), sharedPreferences.getString(KeyValue.REGION, ""), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),WorldTop.class);
                        startActivity(intent);
                    }
                }
            }, 1200);
        }
        else {
            Toast.makeText(this,"Check internet connection",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
