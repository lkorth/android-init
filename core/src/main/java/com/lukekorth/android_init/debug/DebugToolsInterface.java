package com.lukekorth.android_init.debug;

import android.app.Application;
import android.app.Fragment;

import okhttp3.OkHttpClient;

public interface DebugToolsInterface {

    void setup(Application application);

    void watchForLeak(Fragment fragment);

    void setNetworkInterceptor(OkHttpClient.Builder okHttpBuilder);
}
