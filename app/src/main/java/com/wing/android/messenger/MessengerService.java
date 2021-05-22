package com.wing.android.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.wing.android.utils.MyConstants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessengerService extends Service {
    public static final String TAG = "MessengerService";

    /**
     * 通过
     */
    public   static Messenger messenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_CLIENT:
                    Log.i(TAG, "server receive msg from client:" + msg.getData().getString("msg"));

                    Messenger messenger = msg.replyTo;
                    Message relpyMessage = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "嗯，你的消息我已经收到，稍后会回复你。");
                    relpyMessage.setData(bundle);
                    try {
                        messenger.send(relpyMessage);
                        Log.i(TAG, "server send message to client");

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //这里需要messenger的binder

        Log.i(TAG, "server onBind binder :" + messenger.getBinder());

        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "server onCreate");

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "server onStart");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "server onStartCommand");
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "server onUnbind");
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "server onDestroy");
    }
}
