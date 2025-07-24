package com.example.newsapp.ui

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.newsapp.R

class DetailActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Ambil URL dari intent
        val url = intent.getStringExtra("url")

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setBackgroundColor(resources.getColor(R.color.purple_500,theme))
        toolbar.setTitleTextColor(resources.getColor(R.color.white,theme))
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_back)
        // Setup WebView
        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true


        // Load URL ke WebView
        if (!url.isNullOrEmpty()) {
            webView.loadUrl(url)
        }
    }

    // Aksi saat tombol back ditekan
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
