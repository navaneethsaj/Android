package com.example.asus.newsfeed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ASUS on 11/12/2017.
 */

public class Utils {

    public static URL createUrl(String stringurl){
        URL url=null;
        try {
            url=new URL(stringurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        finally {
            return url;
        }
    }

    public static String makeHttpRequest(URL url){
        String jsonResponse=null;
        try {
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder=new StringBuilder();
            String out;
            while ((out = bufferedReader.readLine())!=null){
                stringBuilder.append(out);
            }
            jsonResponse=stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return jsonResponse;
        }
    }

    public static ArrayList<Sources> jsonSourcetoArray(String jsonResponse){
        ArrayList<Sources> sourcesArrayList=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            String status=jsonObject.getString("status");
            if(status.equals("ok")) {
                JSONArray jsonArray = jsonObject.getJSONArray("sources");
                Log.d("length", Integer.toString(jsonArray.length()));
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject source = jsonArray.getJSONObject(i);
                    try {
                        String id = source.getString("id");
                        String name = source.getString("name");
                        String description = source.getString("description");
                        String url = source.getString("url");
                        String category = source.getString("category");
                        String language = source.getString("language");
                        String country = source.getString("country");
                        sourcesArrayList.add(new Sources(id, name, description, url, category, language, country));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                 }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            return sourcesArrayList;
        }
    }
    public static ArrayList<NewsObject> jsonnewstoNewsArray(String jsonResponse){
        ArrayList<NewsObject> newsObjects=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONArray articles=jsonObject.getJSONArray("articles");
            for (int i=0;i<articles.length();++i){
                JSONObject news=articles.getJSONObject(i);
                try {

                        JSONObject sourceObject=news.getJSONObject("source");
                        String source_id=sourceObject.getString("id");
                        String source_name=sourceObject.getString("name");

                    String author;
                    if (news.getString("author").equals("null")) {
                        author = "NA";
                    } else {
                        author = news.getString("author");
                    }
                    String title;
                    if(news.getString("title").equals("null")){
                        title="NA";
                        continue;
                    }else {
                        title = news.getString("title");
                    }
                    String desc;
                    if (news.getString("description").equals("null")){
                        desc="NA";
                        continue;
                    }
                    else {
                        desc= news.getString("description");
                    }
                    String url;
                    if (news.getString("url").equals("null")){
                        url="NA";
                        continue;
                    }else {
                        url = news.getString("url");
                    }
                    String urltoimage;
                    if (news.getString("urlToImage").equals("null")){
                        urltoimage="NA";
                        continue;
                    }else{
                        urltoimage = news.getString("urlToImage");
                    }
                    String publishedat;
                    if (news.getString(("publishedAt")).equals("null")) {
                        publishedat = "NA";
                    } else {
                        publishedat = news.getString("publishedAt").substring(0, 10);
                    }
                    Bitmap bitmap = null;
                    Log.d("Bitmap start : ", "OK");
                    bitmap = BitmapFactory.decodeStream((InputStream) new URL(urltoimage).getContent());
                    Log.d("Bitmap end : ", "OK");
                    if (bitmap==null){
                        continue;
                    }
                    newsObjects.add(new NewsObject(source_id,source_name, author, title, desc, url, urltoimage, publishedat, bitmap));
                }finally {

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return newsObjects;
        }
    }

    public static ArrayList<String> getNewsSourceOfCountry(String countrycode,String sourcesjson){
        ArrayList<String> sources=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(sourcesjson);
            JSONArray jsonArray=jsonObject.getJSONArray("sources");
            for (int i=0;i<jsonArray.length();++i){
                JSONObject sourcepiece=jsonArray.getJSONObject(i);
                if (sourcepiece.getString("country").equals(countrycode)){
                    sources.add(sourcepiece.getString("id"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            return sources;
        }
    }

    public static String getcountryCode(String country){
        String countrycode=new String();
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
            case "ARGENTINA":
                countrycode="ar";
                break;
            case "BRAZIL":
                countrycode="br";
                break;
            case "CANADA":
                countrycode="ca";
                break;
            case "CHINA":
                countrycode="cn";
                break;
            case "GERMANY":
                countrycode="de";
                break;
            case "SPAIN":
                countrycode="es";
                break;
            case "FRANCE":
                countrycode="fr";
                break;
            case "HONG-KONG":
                countrycode="hk";
                break;
            case "IRELAND":
                countrycode="ie";
                break;
            case "ICELAND":
                countrycode="is";
                break;
            case "NETHERLANDS":
                countrycode="nl";
                break;
            case "NORWAY":
                countrycode="no";
                break;
            case "RUSSIA":
                countrycode="ru";
                break;
            case "SOUTH-AFRICA":
                countrycode="za";
                break;
            case "EL-SALVADOR":
                countrycode="sv";
                break;
        }
        return countrycode;
    }
}
