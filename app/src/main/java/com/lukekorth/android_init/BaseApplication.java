package com.lukekorth.android_init;

import android.app.Application;
import android.content.Context;

import com.google.gson.GsonBuilder;
import com.lukekorth.android_init.api.Api;
import com.lukekorth.android_init.api.UserAgentInterceptor;
import com.lukekorth.android_init.debug.DebugTools;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApplication extends Application {

    private DebugTools mDebugTools;
    private Api mApi;

    public static BaseApplication getApplication(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mDebugTools = new DebugTools();
        mDebugTools.setup(this);
    }

    public Api getApi() {
        if (mApi == null) {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.addInterceptor(new UserAgentInterceptor());
            mDebugTools.setNetworkInterceptor(okHttpBuilder);

            mApi = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation()
                            .create()))
                    .client(okHttpBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Api.class);
        }

        return mApi;
    }

    public DebugTools getDebugTools() {
        return mDebugTools;
    }
}
