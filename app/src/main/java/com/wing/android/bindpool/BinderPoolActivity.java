package com.wing.android.bindpool;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.wing.android.databinding.ActivityBindPoolBinding;

public class BinderPoolActivity extends Activity {
    private static final String TAG = "BinderPoolActivity";

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBindPoolBinding inflate = ActivityBindPoolBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    doWork();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doWork() throws RemoteException {
        BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
        IBinder securityBinder = binderPool
                .queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        ;
        mSecurityCenter = (ISecurityCenter) SecurityCenterImpl
                .asInterface(securityBinder);
        Log.d(TAG, "visit ISecurityCenter");
        String msg = "helloworld-安卓";
        System.out.println("content:" + msg);
        try {
            String password = mSecurityCenter.encrypt(msg);
            System.out.println("encrypt:" + password);
            System.out.println("decrypt:" + mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "visit ICompute");
        IBinder computeBinder = binderPool
                .queryBinder(BinderPool.BINDER_COMPUTE);
        ;
        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            System.out.println("3+5=" + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
