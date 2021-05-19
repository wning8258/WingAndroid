package com.wing.android.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.wing.android.databinding.ActivityMessengerBinding;
import com.wing.android.utils.MyConstants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";
    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivityMessengerBinding.inflate(getLayoutInflater()).getRoot());

        /**
         * 通过handler创建Messenger，接受服务器返回的消息
         */
        final Messenger replyToMessenger = new Messenger(new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MyConstants.MSG_FROM_SERVICE:
                        Log.i(TAG,"client receive msg from service :" +msg.getData().getString("reply"));
                        break;
                }
                super.handleMessage(msg);
            }
        });

        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                /**
                 * 通过binder创建Messenger，给服务器发送消息
                 */
                //拿到服务器的binder创建Messenger信使对象
                messenger = new Messenger(iBinder);
                //创建message
                Message message = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
                Bundle data = new Bundle();
                data.putString("msg", "hello, this is client");
                //告诉服务器，通过replyTo这个messenger信使返回数据
                message.replyTo = replyToMessenger;
                message.setData(data);
                //通过messenger发送给服务器
                try {
                    messenger.send(message);
                    Log.i(TAG, "client send message to server");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, Context.BIND_AUTO_CREATE);



    }
}