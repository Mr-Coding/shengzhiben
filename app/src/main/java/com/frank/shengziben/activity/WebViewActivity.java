package com.frank.shengziben.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebViewClient;

import androidx.databinding.DataBindingUtil;

import com.frank.shengziben.R;
import com.frank.shengziben.Tools;
import com.frank.shengziben.databinding.ActivityWebViewBinding;

public class WebViewActivity extends Activity {
    private Intent intent;
    private ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Tools.setAndroidNativeLightStatusBar(this);
        }

        binding = DataBindingUtil.setContentView(this,R.layout.activity_web_view);

        intent = getIntent();

        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.setWebViewClient(new WebViewClient());
        binding.webview.loadUrl(intent.getData().toString());

    }
}
