package com.lukekorth.android_init;

import android.app.Application;

public class BaseApplication extends Application {

    private DebugTools mDebugTools;

    @Override
    public void onCreate() {
        super.onCreate();

        mDebugTools = new DebugTools();
        mDebugTools.setup(this);
    }

    public DebugTools getDebugTools() {
        return mDebugTools;
    }
}
