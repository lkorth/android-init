package com.lukekorth.android_init.debug;

import android.app.Application;

import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.HeapDump;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.slf4j.LoggerFactory;

public class LeakLoggerService extends DisplayLeakService {

    public static RefWatcher setupLeakCanary(Application application) {
        return LeakCanary.install(application, LeakLoggerService.class,
                AndroidExcludedRefs.createAppDefaults().build());
    }

    @Override
    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
        if (!result.leakFound || result.excludedLeak) {
            return;
        }

        LoggerFactory.getLogger("LeakCanary").warn(leakInfo);
    }
}
