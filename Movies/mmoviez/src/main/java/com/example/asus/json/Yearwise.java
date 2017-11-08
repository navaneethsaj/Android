package com.example.asus.json;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Yearwise extends AppCompatActivity {

    InputStream in1,in2,in3,in4,in5,in6;
    EditText editTextfrom;
    ListView listView;
    String from;
    BufferedReader reader1,reader2,reader3,reader4,reader5,reader6;
    ArrayList<MovieModel> movieList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearwise);
        listView=(ListView)findViewById(R.id.listyear);
        editTextfrom=(EditText)findViewById(R.id.yearz);
    }
    public void periodrender(String from,String to){
        while (true){
            try {
                String movie=reader1.readLine();
                String year=reader2.readLine();
                String director=reader3.readLine();
                String genre=reader4.readLine();
                String duration=reader5.readLine() + " minutes";
                String country=reader6.readLine();
                if (movie!=null){
                    if (year.compareTo(from) >=0 && year.compareTo(to) <= 0) {
                        movieList.add(new MovieModel(movie, year, director, genre, duration, country));
                    }
                }
                else {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void go(View view){
        movieList=new ArrayList<>();
        try {
            in1 = this.getAssets().open("allmovies.txt");
            in2 = this.getAssets().open("allyear.txt");
            in3 = this.getAssets().open("alldirectors.txt");
            in4 = this.getAssets().open("allgenre.txt");
            in5 = this.getAssets().open("allduration.txt");
            in6 = this.getAssets().open("allcountry.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader1 = new BufferedReader(new InputStreamReader(in1));
        reader2 = new BufferedReader(new InputStreamReader(in2));
        reader3 = new BufferedReader(new InputStreamReader(in3));
        reader4 = new BufferedReader(new InputStreamReader(in4));
        reader5 = new BufferedReader(new InputStreamReader(in5));
        reader6 = new BufferedReader(new InputStreamReader(in6));
        from=editTextfrom.getText().toString();
        Toast.makeText(this,"Loaded",Toast.LENGTH_SHORT).show();
        periodrender(from,from);
        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),movieList);
        listView.setAdapter(customAdapter);

        //TO hide keypad after button click
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);


    }
    public void clear(View view){
        listView.setAdapter(null);
    }

}
