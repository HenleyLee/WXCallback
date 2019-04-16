package com.henley.wxcallback.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.henley.wxcallback.annotation.WXCallback;

/**
 * WXCallback Activity
 */
@WXCallback(packageName = BuildConfig.APPLICATION_ID, wxEntry = true, wxPayEntry = true)
public class WXCallbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxcallback);
    }

}
