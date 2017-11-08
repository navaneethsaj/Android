package com.example.asus.json;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuPeriod extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_period);
    }
    public void goto1960(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void yearpickerintent(View view){
        Intent intent=new Intent(this,Yearwise.class);
        startActivity(intent);
    }
    public void about(View view){
        Toast.makeText(this,"Created By\nNavaneeth",Toast.LENGTH_LONG).show();
    }
}
