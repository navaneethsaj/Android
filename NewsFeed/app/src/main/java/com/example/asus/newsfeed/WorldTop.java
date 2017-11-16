package com.example.asus.newsfeed;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class WorldTop extends AppCompatActivity {
    public static String worldurl="https://newsapi.org/v1/articles?sortBy=top&apiKey=cb10a5ef6811467e9ab60e72210ec3cc";
    ArrayList<NewsObject> newslist=new ArrayList<>();
    ListView listView;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_top);
        Random random=new Random();
        index=random.nextInt(7);
        String source=KeyValue.sources[index];
        listView=(ListView)findViewById(R.id.listview);
        getSourcesAsyncTask asyncTask=new getSourcesAsyncTask();
        asyncTask.execute(worldurl+"&source="+source);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String url=newslist.get(position).getUrl();
                Intent intent=new Intent(getApplicationContext(), com.example.asus.newsfeed.Web.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
    }


    public void refresh(View view) {
        if (index==6){
            index=0;
        }
        else {
            index++;
        }
        String source=KeyValue.sources[index];
        getSourcesAsyncTask asyncTask2=new getSourcesAsyncTask();
        asyncTask2.execute(worldurl+"&source="+source);
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
            //Toast.makeText(getApplicationContext(),jsonresponse,Toast.LENGTH_SHORT).show();
            Log.d("Onpostexecute : ","OK");
            if (newslist.size() == 0) {
                //textView.setText("No Local Content Available");
            } else {
                Log.d("else : ","OK");
                NewsAdapter newsAdapter=new NewsAdapter(getApplicationContext(),newslist);
                listView.setAdapter(newsAdapter);
                //Toast.makeText(getApplicationContext(),jsonresponse,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
