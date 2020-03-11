package com.example.smartbrace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_link_webview.*

class LinkWebView: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_link_webview)

        //to load mobile version
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setUserAgentString("Android");
        webView.getSettings().setJavaScriptEnabled(true);

        //getting url
        val urlLink = intent.getStringExtra("link")

        //loading link in webView
        webView.loadUrl(urlLink)

    }
}