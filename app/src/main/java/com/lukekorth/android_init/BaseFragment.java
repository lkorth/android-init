package com.lukekorth.android_init;

import android.app.Fragment;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lukekorth.android_init.api.Api;

public class BaseFragment extends Fragment {

    protected Api mApi;
    protected FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApi = BaseApplication.getApplication(getActivity()).getApi();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.getApplication(getActivity()).getDebugTools().watchForLeak(this);
    }
}
