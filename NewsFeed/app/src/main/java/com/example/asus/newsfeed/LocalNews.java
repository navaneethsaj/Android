package com.example.asus.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class LocalNews extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView textView;
    String countrycode;
    SearchView searchView;
    ImageView refeshbutton;
    String jsonsourcelist;
    CacheContent newsCache;
    StringBuilder stringBuilder=new StringBuilder();
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    public static String worldurl="https://newsapi.org/v2/top-headlines?apiKey=cb10a5ef6811467e9ab60e72210ec3cc";
    ArrayList<NewsObject> newslist=new ArrayList<>();
    ListView listView;
    Random random=new Random();
    int index;
    ArrayList<String> sourcelist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_news);
        sharedPreferences=getSharedPreferences(KeyValue.MY_PREF,MODE_PRIVATE);
        progressBar=(ProgressBar)findViewById(R.id.loading);
        refeshbutton=(ImageView)findViewById(R.id.refresh);
        textView=(TextView)findViewById(R.id.text);
        listView=(ListView)findViewById(R.id.listview);
        searchView=(SearchView)findViewById(R.id.search_bar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipelayout);
        if (sharedPreferences.getString(KeyValue.IS_CACHED,"").equals("true")){
            Toast.makeText(this,"true_entered",Toast.LENGTH_SHORT).show();
            try {
                Log.d("try start", " ok");
                FileInputStream fileInputStream=new FileInputStream(new File(getApplicationContext().getFilesDir(),"cache.txt"));
                Log.d("FileInputstrm "," ok");
                ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
                Log.d("ObjectInputstrm "," Ok");
                newsCache=(CacheContent)objectInputStream.readObject();
                Log.d("Newscache "," OK");
                newslist=newsCache.getNewsObjectArrayListCache();
                Log.d("news list "," ok");
                NewsAdapter newsAdapter=new NewsAdapter(getApplicationContext(),newslist);
                listView.setAdapter(newsAdapter);
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        jsonsourcelist=sharedPreferences.getString(KeyValue.SOURCE_JSONRESPONSE,"");
        String country=sharedPreferences.getString(KeyValue.MY_COUNTRY,"");
        countrycode=Utils.getcountryCode(country);
        stringBuilder.append("&sources=");
        sourcelist=Utils.getNewsSourceOfCountry(countrycode,jsonsourcelist);
        for (int i=0;i<2;++i){ // There is some limit to the no of sources
            stringBuilder.append(sourcelist.get(i).toString());
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        index=random.nextInt(sourcelist.size());
        getSourcesAsyncTask asyncTask=new getSourcesAsyncTask();
        asyncTask.execute(worldurl+stringBuilder.toString());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String url=newslist.get(position).getUrl();
                Intent intent=new Intent(getApplicationContext(), com.example.asus.newsfeed.Web.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        searchView.setQueryHint("Search");
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                getSourcesAsyncTask asyncTask=new getSourcesAsyncTask();
                asyncTask.execute(KeyValue.QUERY_URL+"&q="+s);
                swipeRefreshLayout.setRefreshing(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(Animation.INFINITE);
                anim.setDuration(700);
                refeshbutton.startAnimation(anim);
                sourcelist=Utils.getNewsSourceOfCountry(countrycode,jsonsourcelist);
                stringBuilder=new StringBuilder();
                stringBuilder.append("&sources=");
                Collections.shuffle(sourcelist);
                for (int i=0;i<sourcelist.size() && i<2;++i){ /// There is some limit to the no of sources
                    stringBuilder.append(sourcelist.get(i).toString());
                    stringBuilder.append(",");
                }
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                getSourcesAsyncTask asyncTask=new getSourcesAsyncTask();
                asyncTask.execute(worldurl+stringBuilder.toString());
            }
        });
    }

    public void refresh(View view) {
        swipeRefreshLayout.setRefreshing(true);
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(700);
        refeshbutton.startAnimation(anim);
        sourcelist=Utils.getNewsSourceOfCountry(countrycode,jsonsourcelist);
        stringBuilder=new StringBuilder();
        stringBuilder.append("&sources=");
        Collections.shuffle(sourcelist);
        for (int i=0;i<sourcelist.size() && i<3;++i){ /// There is some limit to the no of sources
            stringBuilder.append(sourcelist.get(i).toString());
            stringBuilder.append(",");
        }
        getSourcesAsyncTask asyncTask=new getSourcesAsyncTask();
        asyncTask.execute(worldurl+stringBuilder.toString());
    }

    public void gotomenu(View view) {
        Intent intent=new Intent(getApplicationContext(),MenuActivity.class);
        startActivity(intent);
    }

    public class getSourcesAsyncTask extends AsyncTask<String, Void, ArrayList<NewsObject>> {

        @Override
        protected void onPreExecute() {
            refeshbutton.setClickable(false);
            searchView.setSubmitButtonEnabled(false);
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
            searchView.setSubmitButtonEnabled(true);
            refeshbutton.setClickable(true);
            //Toast.makeText(getApplicationContext(),jsonresponse,Toast.LENGTH_SHORT).show();
            Log.d("Onpostexecute : ","OK");
            progressBar.setVisibility(View.GONE);
            refeshbutton.setAnimation(null);
            if (newslist.size() == 0) {
                //textView.setText("No Local Content Available");
            } else {
                Log.d("else : ","OK");
                Collections.shuffle(newslist);
                NewsAdapter newsAdapter=new NewsAdapter(getApplicationContext(),newslist);
                listView.setAdapter(newsAdapter);
                Log.d("elseend : ","OK");
                //Toast.makeText(getApplicationContext(),jsonresponse,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        newsCache=new CacheContent(newslist);
        try {
            Log.d("FileOutputStarted "," OK");
            FileOutputStream fos = new FileOutputStream(new File(getApplicationContext().getFilesDir(),"cache.txt"));
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(newsCache);
            os.close();
            fos.close();
            Log.d("FIleOutputFinished "," OK");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sharedPreferences=getSharedPreferences(KeyValue.MY_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KeyValue.IS_CACHED,"true");
        editor.commit();
        Toast.makeText(this, "Stopped", Toast.LENGTH_LONG).show();
    }

    /*@Override
    protected void onRestart() {
        sharedPreferences=getSharedPreferences(KeyValue.MY_PREF,MODE_PRIVATE);
        String stored=sharedPreferences.getString(KeyValue.IS_CACHED,"");
        Toast.makeText(this,stored,Toast.LENGTH_LONG).show();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        sharedPreferences=getSharedPreferences(KeyValue.MY_PREF,MODE_PRIVATE);
        String stored=sharedPreferences.getString(KeyValue.IS_CACHED,"");
        Toast.makeText(this,stored,Toast.LENGTH_LONG).show();
        super.onResume();
    }*/
}
