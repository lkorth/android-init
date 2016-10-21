package com.lukekorth.auto_fi.debug;

import android.app.Application;
import android.app.Fragment;

import com.lukekorth.android_init.debug.DebugToolsInterface;

import okhttp3.OkHttpClient;

public class DebugTools implements DebugToolsInterface {

    @Override
    public void setup(Application application) {}

    @Override
    public void watchForLeak(Fragment fragment) {}

    @Override
    public void setNetworkInterceptor(OkHttpClient.Builder okHttpBuilder) {}
}
