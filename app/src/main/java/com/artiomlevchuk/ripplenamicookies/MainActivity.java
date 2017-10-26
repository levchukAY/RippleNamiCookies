package com.artiomlevchuk.ripplenamicookies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    String loginUrl = "https://rwaves-stage.ripplenami.com/web/login.html/";
    String mapUrl = "http://rwaves-stage.ripplenami.com/web/#/maps/mine/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSaveFormData(true);
        webView.setWebViewClient(new CustomWebViewClient());
        webView.clearCache(true);
        webView.clearFormData();
        webView.clearHistory();
        webView.clearMatches();
        webView.clearSslPreferences();
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(loginUrl);
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            String cookies = CookieManager.getInstance().getCookie(url);
            Log.d("COOKIE  start", url + " " + cookies);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String cookies = CookieManager.getInstance().getCookie(url);
            Log.d("COOKIE finish", url + " " + cookies);
            if (!TextUtils.isEmpty(url) && url.startsWith(mapUrl)) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("EXTRA_COOKIES", cookies);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }

    }

}
