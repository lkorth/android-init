package com.lukekorth.android_init;

import android.app.Application;
import android.app.Fragment;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.lukekorth.mailable_log.MailableLog;
import com.squareup.leakcanary.RefWatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugTools implements Thread.UncaughtExceptionHandler {

    private RefWatcher mRefWatcher;
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    public void setup(Application application) {
        setStrictMode();

        MailableLog.init(application, true);

        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

        mRefWatcher = LeakLoggerService.setupLeakCanary(application);

        Stetho.initializeWithDefaults(application);
    }

    public static void watchForLeak(Fragment fragment) {
        ((BaseApplication) fragment.getActivity().getApplication())
                .getDebugTools()
                .mRefWatcher
                .watch(fragment);
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
