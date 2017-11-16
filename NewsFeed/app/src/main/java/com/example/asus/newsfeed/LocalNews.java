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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class LocalNews extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView textView;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    public static String worldurl="https://newsapi.org/v1/articles?sortBy=top&apiKey=cb10a5ef6811467e9ab60e72210ec3cc";
    ArrayList<NewsObject> newslist=new ArrayList<>();
    ListView listView;
    Random random=new Random();
    int index;
    ArrayList<String> sourcelist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_news);
        progressBar=(ProgressBar)findViewById(R.id.loading);
        textView=(TextView)findViewById(R.id.text);
        listView=(ListView)findViewById(R.id.listview);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipelayout);
        sharedPreferences=getSharedPreferences(KeyValue.MY_PREF,MODE_PRIVATE);
        String jsonsourcelist=sharedPreferences.getString(KeyValue.SOURCE_JSONRESPONSE,"");
        String country=sharedPreferences.getString(KeyValue.MY_COUNTRY,"");
        String countrycode="";
        switch (country){
            case "INDIA":
                countrycode="in";
                break;
            case "USA":
                countrycode="us";
                break;
            case "UK":
                countrycode="gb";
                break;
            case "ITALY":
                countrycode="it";
                break;
            case "AUSTRALIA":
                countrycode="au";
                break;
        }
        //StringBuilder stringBuilder=new StringBuilder();
        sourcelist=Utils.getNewsSourceOfCountry(countrycode,jsonsourcelist);
        /*for (int i=0;i<sourcelist.size();++i){
            stringBuilder.append(sourcelist.get(i).toString());
        }
        */
        index=random.nextInt(sourcelist.size());
        getSourcesAsyncTask asyncTask=new getSourcesAsyncTask();
        asyncTask.execute(worldurl+"&source="+sourcelist.get(index).toString());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String url=newslist.get(position).getUrl();
                Intent intent=new Intent(getApplicationContext(), com.example.asus.newsfeed.Web.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (index==sourcelist.size()-1){
                    index=0;
                }
                else {
                    index++;
                }
                getSourcesAsyncTask asyncTask=new getSourcesAsyncTask();
                asyncTask.execute(worldurl+"&source="+sourcelist.get(index).toString());
            }
        });
    }

    public void refresh(View view) {
        if (index==sourcelist.size()-1){
            index=0;
        }
        else {
            index++;
        }
        swipeRefreshLayout.setRefreshing(true);
        getSourcesAsyncTask asyncTask=new getSourcesAsyncTask();
        asyncTask.execute(worldurl+"&source="+sourcelist.get(index).toString());
    }

    public void gotomenu(View view) {
        Intent intent=new Intent(getApplicationContext(),MenuActivity.class);
        startActivity(intent);
    }

    public class getSourcesAsyncTask extends AsyncTask<String, Void, ArrayList<NewsObject>> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Async started",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<NewsObject> doInBackground(String... strings) {
            URL url=Utils.createUrl(strings[0]);
            String jsonresponse=Utils.makeHttpRequest(url);
            newslist = Utils.jsonnewstoNewsArray(jsonresponse);
            return newslist;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsObject> newslist) {
            swipeRefreshLayout.setRefreshing(false);
            //Toast.makeText(getApplicationContext(),jsonresponse,Toast.LENGTH_SHORT).show();
            Log.d("Onpostexecute : ","OK");
            progressBar.setVisibility(View.GONE);
            if (newslist.size() == 0) {
                //textView.setText("No Local Content Available");
            } else {
                Log.d("else : ","OK");
                NewsAdapter newsAdapter=new NewsAdapter(getApplicationContext(),newslist);
                listView.setAdapter(newsAdapter);
                Log.d("elseend : ","OK");
                //Toast.makeText(getApplicationContext(),jsonresponse,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
