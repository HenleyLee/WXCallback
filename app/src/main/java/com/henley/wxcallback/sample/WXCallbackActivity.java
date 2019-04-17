package com.henley.wxcallback.sample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
