package com.example.asus.myquakeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toast.makeText(this,"LOADING",Toast.LENGTH_SHORT).show();
        String url=getIntent().getStringExtra("url");
        //Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        webView=(WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewController());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
