package com.lukekorth.android_init;

import android.app.Fragment;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class BaseFragment extends Fragment {

    protected FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugTools.watchForLeak(this);
    }
}
