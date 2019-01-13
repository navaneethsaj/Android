package com.blazingapps.asus.easyhotspot;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private static final String MYPREF = "myref";
    private static final String COUNT = "count";
    private static final String NOTRATED = "okay";
    private static final int MY_PERMISSIONS_REQUEST = 99;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        sharedPreferences=getSharedPreferences(MYPREF,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putInt(COUNT,sharedPreferences.getInt(COUNT,0)+1);
        editor.commit();
        if (sharedPreferences.getInt(COUNT,0) > 6 && sharedPreferences.getBoolean(NOTRATED,true)){
            launchMarket();
            Toast.makeText(getApplicationContext(),"Please Rate Us\nSo, we can improve",Toast.LENGTH_LONG).show();
            editor.putBoolean(NOTRATED,false);
            editor.commit();
        }

        if (settingPermission()) {
            toggleHotspot();
        }
        finish();
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
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    public void toggleHotspot(){

        String APP_OPEN = "APP_OPEN";
        String DEVICE_NAME = "DEVICE_NAME";
        String DEVICE_MAN = "DEVICE_MANUFACTURE";
        String DEVICE_BRAND = "DEVICE_BRAND";
        String DEVICE = "DEVICE";
        String DEVICE_ID = "DEVICE_ID";
        String OS_API = "OS_API";
        String OS_RELEASE = "OS_RELEASE";
        String BASE_OS = "BASE_OS";

        String deviceName = android.os.Build.MODEL;
        String deviceMan = android.os.Build.MANUFACTURER;
        String deviceBrand = Build.BRAND;
        String device = Build.DEVICE;
        String deviceID = Build.ID;
        int osApi = Build.VERSION.SDK_INT;
        String osRelease = Build.VERSION.RELEASE;
        String baseOs = Build.VERSION.BASE_OS;

        //Log.d("TAGZZ",deviceName+"~"+deviceMan+"~"+osApi+"~"+osRelease+"~"+baseOs+"~"+deviceBrand+"~"+deviceID+"~"+device);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wifiConfiguration = new WifiConfiguration();

        wifiConfiguration.SSID = "EasyHotspot";
        wifiConfiguration.preSharedKey="123456789";
        wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);


        Method method;
        if (wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(false);
        }
        boolean action= !isSharingWiFi(wifiManager);
        try {
            method = wifiManager.getClass().getDeclaredMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            method.invoke(wifiManager, wifiConfiguration, action);
            if (action){
                Toast.makeText(getApplicationContext(),"Password : 123456789",Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString(APP_OPEN, "Hotspot Started");

                bundle.putString(DEVICE,device);
                bundle.putString(DEVICE_BRAND,deviceBrand);
                bundle.putString(DEVICE_ID,deviceID);
                bundle.putString(DEVICE_MAN,deviceMan);
                bundle.putString(DEVICE_NAME,deviceName);
                bundle.putInt(OS_API,osApi);
                bundle.putString(OS_RELEASE,osRelease);
                bundle.putString(BASE_OS,baseOs);

                mFirebaseAnalytics.logEvent("TetherInit", bundle);

            }else {
                Bundle bundle = new Bundle();
                bundle.putString(APP_OPEN, "Hotspot Stopped");

                bundle.putString(DEVICE,device);
                bundle.putString(DEVICE_BRAND,deviceBrand);
                bundle.putString(DEVICE_ID,deviceID);
                bundle.putString(DEVICE_MAN,deviceMan);
                bundle.putString(DEVICE_NAME,deviceName);
                bundle.putInt(OS_API,osApi);
                bundle.putString(OS_RELEASE,osRelease);
                bundle.putString(BASE_OS,baseOs);

                mFirebaseAnalytics.logEvent("TetherInit", bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Log.d("TAGZZ",e.getLocalizedMessage());
            Bundle bundle = new Bundle();
            bundle.putString(APP_OPEN, "Exception");

            bundle.putString(DEVICE,device);
            bundle.putString(DEVICE_BRAND,deviceBrand);
            bundle.putString(DEVICE_ID,deviceID);
            bundle.putString(DEVICE_MAN,deviceMan);
            bundle.putString(DEVICE_NAME,deviceName);
            bundle.putInt(OS_API,osApi);
            bundle.putString(OS_RELEASE,osRelease);
            bundle.putString(BASE_OS,baseOs);

            mFirebaseAnalytics.logEvent("Exception", bundle);
            Toast.makeText(getApplicationContext(),"RunTime Exception",Toast.LENGTH_SHORT).show();
        }finally {

        }
    }

    public boolean settingPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, MY_PERMISSIONS_REQUEST);
                Toast.makeText(getApplicationContext(),"Grant Permission And Restart App",Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),"Password : 123456789",Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),"You Can Change Password In System Settings If you need",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Grant Permission And Restart App",Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),"Password : 123456789",Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),"You Can Change Password In System Settings If you need",Toast.LENGTH_LONG).show();
                return false;
            }
            else
            {
                return true;
            }
        }
        else {
            return true;
        }
    }
}
