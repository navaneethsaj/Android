package com.example.asus.newsfeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class Country_News extends AppCompatActivity {
    ArrayList<Sources> sourcesArrayList=new ArrayList<>();
    ListView listView;
    TextView textView;
    Button button;
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country__news);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        listView=(ListView)findViewById(R.id.listview);
        textView=(TextView)findViewById(R.id.availablity);
        button=(Button)findViewById(R.id.button);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.countryswipe);
        getSourcesAsyncTask sourcesAsyncTask=new getSourcesAsyncTask();
        String url=KeyValue.SOURCES_URL;
        sharedPreferences=getSharedPreferences(KeyValue.MY_PREF,MODE_PRIVATE);
        String country=sharedPreferences.getString(KeyValue.MY_COUNTRY,"");
        String countrycode=Utils.getcountryCode(country);
        sourcesAsyncTask.execute(url+"&country="+countrycode);
    }


    public void localnews(View view) {
        Intent intent=new Intent(getApplicationContext(),LocalNews.class);
        startActivity(intent);
    }


    public class getSourcesAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            button.setText("Wait..");
            button.setEnabled(false);
            swipeRefreshLayout.setRefreshing(true);
            //Toast.makeText(getApplicationContext(),"Async started",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url=Utils.createUrl(strings[0]);
            String jsonresponse=Utils.makeHttpRequest(url);
            Log.d("doInBackground "," End");
            return jsonresponse;
        }

        @Override
        protected void onPostExecute(String jsonresponse) {
            button.setEnabled(true);
            button.setText("Go");
            editor=sharedPreferences.edit();
            swipeRefreshLayout.setRefreshing(false);
            editor.putString(KeyValue.SOURCE_JSONRESPONSE,jsonresponse);
            editor.commit();
            sourcesArrayList = Utils.jsonSourcetoArray(jsonresponse);
            if (sourcesArrayList.size() == 0) {
                textView.setText("No Local Content Available");
            } else {
                customAdapter adapter = new customAdapter(getApplicationContext(), sourcesArrayList);
                listView.setAdapter(adapter);
                //Toast.makeText(getApplicationContext(),jsonresponse,Toast.LENGTH_SHORT).show();
            }
            Log.d("Onpostexecute "," End");
        }
    }
}
