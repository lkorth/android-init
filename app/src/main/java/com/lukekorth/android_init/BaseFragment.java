package com.lukekorth.android_init;

import android.app.Fragment;

public class BaseFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugTools.watchForLeak(this);
    }
}
