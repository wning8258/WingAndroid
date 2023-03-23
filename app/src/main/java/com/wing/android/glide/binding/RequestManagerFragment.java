package com.wing.android.glide.binding;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.wing.android.glide.RequestManager;

// android.app  的 空白的Fragment  监听生命周期变化的
public class RequestManagerFragment extends Fragment {

    private static final String TAG = "SupportRMFragment";
    private final ActivityFragmentLifecycle lifecycle;
    @Nullable
    private RequestManager requestManager;

    public RequestManagerFragment() {
        this(new ActivityFragmentLifecycle());
    }

    @VisibleForTesting
    @SuppressLint("ValidFragment")
    public RequestManagerFragment(@NonNull ActivityFragmentLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public void setRequestManager(@Nullable RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @NonNull
    public ActivityFragmentLifecycle getGlideLifecycle() {
        return lifecycle;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // this.lifecycle.addListener(requestManager);
    }

    @Nullable
    public RequestManager getRequestManager() {
        return requestManager;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycle.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle.onDestroy();
    }
}
