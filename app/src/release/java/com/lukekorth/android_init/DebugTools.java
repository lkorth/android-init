package com.lukekorth.android_init;

import android.app.Application;
import android.app.Fragment;

import okhttp3.Interceptor;

public class DebugTools {

    public void setup(Application application) {
        // noop
    }

    public static void watchForLeak(Fragment fragment) {
        // noop
    }

    public static Interceptor getNetworkInterceptor() {
        return null;
    }
}
