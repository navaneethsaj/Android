package com.example.asus.quakeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {
    int backButtonCount;
    TextView fetching;
    ProgressBar progressBar;
    ListView earthquakelistview;
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=3";
    ArrayList<ItemModel> earthquakes=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        fetching=(TextView)findViewById(R.id.fetching);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        earthquakelistview=(ListView)findViewById(R.id.list);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                fetching.setText("Still Loading Please Wait .....");
            }
        }, 5000);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                fetching.setText("Almost There");
            }
        }, 10000);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                fetching.setText("Sorry For The Delay\nFetching Data");
            }
        }, 20000);
        earthQuakeAsyncTask asyncTask=new earthQuakeAsyncTask();
        asyncTask.execute(USGS_REQUEST_URL);

    }
    private class earthQuakeAsyncTask extends AsyncTask<String, Void, ArrayList<ItemModel>>{


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected ArrayList<ItemModel> doInBackground(String... strings) {
            URL url=Utils.createUrl(strings[0]);
            String jsonResponse=Utils.makeHttpRequest(url);
            return Utils.jsonToObjectArray(jsonResponse);
        }

        @Override
        protected void onPostExecute(ArrayList<ItemModel> data) {
            //Log.d("Adapter started : ", "OK" );
            fetching.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            earthquakes=data;
            final customAdapter adapter=new customAdapter(getApplicationContext(),earthquakes);
            earthquakelistview.setAdapter(adapter);
            earthquakelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ItemModel item= earthquakes.get(i);
                    //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                    String url = item.getUrl();
                    Intent intent = new Intent(getApplicationContext(),WebActivity.class);
                    //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
                    intent.putExtra("url",url);
                    startActivity(intent);
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1)
        {
            Intent intent=new Intent(getApplicationContext(),Exit.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press once again to close", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
