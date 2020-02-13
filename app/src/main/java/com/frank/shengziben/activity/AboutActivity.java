package com.frank.shengziben.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.frank.shengziben.Pay;
import com.frank.shengziben.R;
import com.frank.shengziben.Tools;
import com.frank.shengziben.databinding.ActivityAboutBinding;

public class AboutActivity extends Activity {
    private Activity activity;
    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Tools.setAndroidNativeLightStatusBar(this);
        }

        binding = DataBindingUtil.setContentView(this,R.layout.activity_about);

        binding.tvVersion.setText("Version:"+Tools.getVersionName(this));

        activity = this;

        binding.weixinpayPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        });

        binding.weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.weixinpayPanel.setVisibility(View.VISIBLE);
            }
        });

        binding.alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pay.aLi(activity,"FKX07485N6VROUOMUYEI40");
            }
        });

    }
}
