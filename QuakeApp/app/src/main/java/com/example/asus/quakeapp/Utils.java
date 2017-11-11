package com.example.asus.quakeapp;

import android.util.Log;

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
 * Created by ASUS on 11/10/2017.
 */

public class Utils {
    public static URL createUrl(String url_raw){
        URL url=null;
        try {
            url=new URL(url_raw);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static String makeHttpRequest(URL url){
        HttpURLConnection httpURLConnection;
        InputStream inputStream=null;
        StringBuilder stringBuilder=new StringBuilder();
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String jsonResponse=null;
        try {
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            inputStream=httpURLConnection.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream);
            bufferedReader=new BufferedReader(inputStreamReader);
            String out;
            while ((out=bufferedReader.readLine())!=null){
                stringBuilder.append(out);
            }
            jsonResponse=stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            //Log.d("JsonResponse : ",jsonResponse);

            return jsonResponse;
        }
    }
    public static ArrayList<ItemModel> jsonToObjectArray(String jsonResponse){
        ArrayList<ItemModel> itemModelsArray=new ArrayList<>();
        try {
            //Log.d("JsonToObjectAr Start : ",jsonResponse);
            JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONArray jsonArray=jsonObject.getJSONArray("features");
            for (int i=0;i<jsonArray.length();++i){
                try {
                    JSONObject data=jsonArray.getJSONObject(i);
                    JSONObject properties=data.getJSONObject("properties");
                    String url=properties.getString("url");
                    String magnitude=properties.getString("mag");
                    String unix=properties.getString("time");
                    String unixdate=Functions.unixtodate(unix);
                    String place=properties.getString("place");
                    String[] parts=place.split(" of ");
                    String string1;
                    String string2;
                    if (parts.length>1)
                    {
                        string1=parts[0]+"of";
                        string2=parts[1];
                    }
                    else
                    {
                        string1="Near";
                        string2=parts[0];
                    }
                    //Log.d("string1",string1);
                    //Log.d("string2",string2);
                    String date=unixdate.substring(0,10);
                    String time=unixdate.substring(10);
                    itemModelsArray.add(new ItemModel(magnitude,string1,string2,date,time,url));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            //Log.d("JsontoObjarr : ","Stopped");
            return itemModelsArray;
        }
    }
}
