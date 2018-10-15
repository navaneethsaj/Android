package com.blazingapps.asus.easyhotspot;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Boolean turnon = true;
    TextView sharingtext;
    ImageView onoff,wirelessImg,facebook,instagram,twitter,playstore,aboutme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);
        twitter = findViewById(R.id.twitter);
        playstore = findViewById(R.id.playstore);
        onoff = findViewById(R.id.onoffbutton);
        wirelessImg = findViewById(R.id.wirelessimage);
        sharingtext = findViewById(R.id.sharingtext);
        aboutme = findViewById(R.id.aboutme);

        turnOnOffHotspot(this,turnon);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(getApplicationContext());
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getOpenTwitterIntent(getApplicationContext(), "navaneethkz");
                startActivity(i);
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.instagram.com/navaneethkz/");
                Intent insta = new Intent(Intent.ACTION_VIEW, uri);
                insta.setPackage("com.instagram.android");

                if (isIntentAvailable(getApplicationContext(), insta)){
                    startActivity(insta);
                } else{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/xxx")));
                }
            }
        });
        playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOnOffHotspot(getApplicationContext(),turnon);
            }
        });
        aboutme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),About.class);
                startActivity(intent);
            }
        });
    }
    public void turnOnOffHotspot(Context context, boolean isTurnToOn) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);

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
                turnon = !turnon;
                if (turnon){
                    Toast.makeText(getApplicationContext(),"Hotspot Stopped",Toast.LENGTH_LONG).show();
                    onoff.setImageResource(R.drawable.turnoff);
                    wirelessImg.setImageResource(R.drawable.wirelessoff);
                    sharingtext.setText("Not Sharing Network !");
                }else {
                    Toast.makeText(getApplicationContext(),"Hotspot Started",Toast.LENGTH_LONG).show();
                    onoff.setImageResource(R.drawable.turnon);
                    wirelessImg.setImageResource(R.drawable.wirelesson);
                    sharingtext.setText("Sharing Network Connection !");
                }
            }else {
                Log.d("operation result","failed to turn on hotspot");
                Toast.makeText(getApplicationContext(),"Phone Not Supported",Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String FACEBOOK_URL = "https://www.facebook.com/Navaneeth.Melur";
    public static String FACEBOOK_PAGE_ID = "Navaneeth.Melur";

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    public static Intent getOpenTwitterIntent(Context c, String Username) {

        try {
            c.getPackageManager().getPackageInfo("com.twitter.android", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name="+ Username));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + Username));
        }
    }

    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
