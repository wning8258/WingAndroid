package com.wing.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.wing.android.aidl.BookManagerActivity;
import com.wing.android.bindpool.BinderPoolActivity;
import com.wing.android.databinding.ActivityIpcBinding;
import com.wing.android.messenger.MessengerActivity;

/**
 * 进程间通信
 */
public class IpcFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = IpcFragment.class.getSimpleName();
    private ActivityIpcBinding mainBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainBinding = ActivityIpcBinding.inflate(inflater,container,false);
        View view = mainBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mainBinding.messengerBtn.setOnClickListener(this);
        mainBinding.aidlBookManagerBtn.setOnClickListener(this);
        mainBinding.binderPoolBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messenger_btn:
                startActivity(new Intent(getActivity(), MessengerActivity.class));
                /**
                 * getStackTrace :
                 *     dalvik.system.VMStack.getThreadStackTrace(Native Method)
                 *     java.lang.Thread.getStackTrace(Thread.java:1730)
                 *     com.wing.android.IpcFragment.onClick(IpcFragment.java:60)
                 *     android.view.View.performClick(View.java:7192)
                 *     android.view.View.performClickInternal(View.java:7166)
                 *     android.view.View.access$3500(View.java:824)
                 *     android.view.View$PerformClick.run(View.java:27592)
                 *     android.os.Handler.handleCallback(Handler.java:888)
                 *     android.os.Handler.dispatchMessage(Handler.java:100)
                 *     android.os.Looper.loop(Looper.java:213)
                 *     android.app.ActivityThread.main(ActivityThread.java:8169)
                 *     java.lang.reflect.Method.invoke(Native Method)
                 *     com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:513)
                 *     com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1101)
                 */
                //获取当前的堆栈
                StringBuilder sb = new StringBuilder();
                for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                    sb.append(stackTraceElement.toString())
                            .append("\r\n");
                }
                Log.i(TAG,"getStackTrace  :\n "+sb.toString());
                break;
            case R.id.aidl_book_manager_btn:
                startActivity(new Intent(getActivity(), BookManagerActivity.class));
                break;
            case R.id.binder_pool_btn:
                startActivity(new Intent(getActivity(), BinderPoolActivity.class));
                break;
        }
    }
}