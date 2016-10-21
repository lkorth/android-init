package com.lukekorth.android_init.debug;

import android.app.Application;
import android.app.Fragment;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lukekorth.mailable_log.MailableLog;
import com.squareup.leakcanary.RefWatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;

public class DebugTools implements DebugToolsInterface, Thread.UncaughtExceptionHandler {

    private RefWatcher mRefWatcher;
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    @Override
    public void setup(Application application) {
        MailableLog.init(application, true);

        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

        mRefWatcher = LeakLoggerService.setupLeakCanary(application);

        Stetho.initializeWithDefaults(application);

        setStrictMode();
    }

    @Override
    public void watchForLeak(Fragment fragment) {
        mRefWatcher.watch(fragment);
    }

    @Override
    public void setNetworkInterceptor(OkHttpClient.Builder okHttpBuilder) {
        okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Logger logger = LoggerFactory.getLogger("Exception");

        logger.error("thread.toString(): " + thread.toString());
        logger.error("Exception: " + ex.toString());
        logger.error("Exception stacktrace:");
        for (StackTraceElement trace : ex.getStackTrace()) {
            logger.error(trace.toString());
        }

        logger.error("");

        logger.error("cause.toString(): " + ex.getCause().toString());
        logger.error("Cause: " + ex.getCause().toString());
        logger.error("Cause stacktrace:");
        for (StackTraceElement trace : ex.getCause().getStackTrace()) {
            logger.error(trace.toString());
        }

        mDefaultExceptionHandler.uncaughtException(thread, ex);
    }

    private void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
