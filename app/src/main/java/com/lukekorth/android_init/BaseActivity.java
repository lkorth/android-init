package com.lukekorth.android_init;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lukekorth.android_init.api.Api;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;

    protected Api mApi;
    protected FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mApi = BaseApplication.getApplication(this).getApi();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    protected int getLayout() {
        return R.layout.activity_main;
    }
}
