package com.blazingapps.asus.easyhotspot;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String MYPREF = "myref";
    private static final String COUNT = "count";
    private static final String NOTRATED = "okay";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences(MYPREF,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putInt(COUNT,sharedPreferences.getInt(COUNT,0)+1);
        editor.commit();
        turnOnOffHotspot(this);
        if (sharedPreferences.getInt(COUNT,0) > 20 && sharedPreferences.getBoolean(NOTRATED,true)){
            launchMarket();
            Toast.makeText(getApplicationContext(),"You Seems to Enjoy Easy Hotspot\nPlease Rate Us in Playstore",Toast.LENGTH_LONG).show();
            editor.putBoolean(NOTRATED,false);
            editor.commit();
        }
        finish();
    }
    public void turnOnOffHotspot(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);

        boolean isTurnToOn = !isSharingWiFi(wifiManager);
        WifiApControl apControl = WifiApControl.getApControl(wifiManager);
        if (apControl != null) {

//             TURN OFF YOUR WIFI BEFORE ENABLE HOTSPOT
//            if (isWifiOn(context) && isTurnToOn) {
//              turnOnOffWifi(context, false);
//            }
            if (wifiManager.isWifiEnabled()){
                wifiManager.setWifiEnabled(false);
            }

            Boolean bool = apControl.setWifiApEnabled(apControl.getWifiApConfiguration(),
                    isTurnToOn);
            if (bool){
                Log.d("operation result","hotspot okay");
                if (!isTurnToOn){
                    Toast.makeText(getApplicationContext(),"Hotspot Stopped",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Hotspot Started",Toast.LENGTH_LONG).show();
                }
            }else {
                Log.d("operation result","failed to turn on hotspot");
                Toast.makeText(getApplicationContext(),"Phone Not Supported",Toast.LENGTH_LONG).show();
            }
        }
    }

    public static boolean isSharingWiFi(final WifiManager manager)
    {
        try
        {
            final Method method = manager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true); //in the case of visibility change in future APIs
            return (Boolean) method.invoke(manager);
        }
        catch (final Throwable ignored)
        {
        }

        return false;
    }
    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}
