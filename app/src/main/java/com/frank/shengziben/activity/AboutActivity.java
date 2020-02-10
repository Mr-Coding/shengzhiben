package com.frank.shengziben.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.frank.shengziben.Pay;
import com.frank.shengziben.R;

public class AboutActivity extends Activity {
    private Activity activity;
    private LinearLayout weixinpaypanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        activity = this;
        weixinpaypanel = findViewById(R.id.weixinpay_panel);

        weixinpaypanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weixinpaypanel.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.alipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pay.aLi(activity,"FKX07485N6VROUOMUYEI40");
            }
        });

    }
}
