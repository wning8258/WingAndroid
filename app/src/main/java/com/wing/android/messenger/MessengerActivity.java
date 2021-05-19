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
import android.view.View;

import com.wing.android.databinding.ActivityMessengerBinding;
import com.wing.android.utils.MyConstants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";

    private Intent serviceIntent;

    private Messenger messenger;

    /**
     * 通过handler创建Messenger，接受服务器返回的消息
     */
    private  Messenger replyToMessenger = new Messenger(new Handler(){
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMessengerBinding inflate = ActivityMessengerBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());
        serviceIntent = new Intent(MessengerActivity.this, MessengerService.class);

        final ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, final IBinder binder) {


                try {
                    binder.linkToDeath(new IBinder.DeathRecipient() {
                        @Override
                        public void binderDied() {
                            Log.i(TAG,"service died");
                            binder.unlinkToDeath(this, 0);
                        }
                    }, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }


                /**
                 * 通过binder创建Messenger，给服务器发送消息
                 */
                //拿到服务器的binder创建Messenger信使对象
                Log.i(TAG, "client onServiceConnected binder :" +binder);

                messenger = new Messenger(binder);
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
            public void onBindingDied(ComponentName name) {
                Log.i(TAG," onBinderDied name :" + name);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i(TAG," onServiceDisconnected name :" + componentName);

            }
        };

        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);



        inflate.mockCrashBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //创建message
                Message message = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
                Bundle data = new Bundle();
                data.putString("msg", "client sendMessage2");
                //告诉服务器，通过replyTo这个messenger信使返回数据
                message.replyTo = replyToMessenger;
                message.setData(data);
                //通过messenger发送给服务器

                try {
                    messenger.send(message);
                    Log.i(TAG, "client send message to server2");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}