package com.artiomlevchuk.ripplenamicookies;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main2Activity extends AppCompatActivity {

    String url = "https://rwaves-stage.ripplenami.com/web/index.html#/maps/mine/";
    String sourceCookieUrl = "http://rwaves-stage.ripplenami.com/web/";
    String[] urls = {url, "https://rwaves-stage.ripplenami.com/web/login.html",
            "https://rwaves-stage.ripplenami.com/web/j_security_check",
            "https://rwaves-stage.ripplenami.com/web",
            "http://rwaves-stage.ripplenami.com/web/"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.d("COOKIE before", CookieManager.getInstance().getCookie(url));

        CookieManager.getInstance().removeAllCookie();
        String cookies = getIntent().getStringExtra("EXTRA_COOKIES");
        String[] pieces = cookies.split("; ");
        for (String cookie: pieces) {
            CookieManager.getInstance().setCookie(url, cookie);
        }
        Log.d("COOKIE  after", CookieManager.getInstance().getCookie(url));

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
        webView.loadUrl(url);
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            String cookies = CookieManager.getInstance().getCookie(url);
            Log.d("COOKIE start2", url + " " + cookies);
        }

    }
}
