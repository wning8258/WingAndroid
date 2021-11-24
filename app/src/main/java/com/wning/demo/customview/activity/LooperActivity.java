package com.wning.demo.customview.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.guagua.modules.utils.LogUtils;
import com.wning.demo.BaseActivity;
import com.wing.android.R;
import com.wing.android.databinding.ActivityLooperBinding;

public class LooperActivity extends BaseActivity<ActivityLooperBinding> implements View.OnClickListener {

    Button test;


    private Handler mMainHandler,mChildHandler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        test=findViewById(R.id.test);
        test.setOnClickListener(this);


        mMainHandler =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                LogUtils.i(TAG,"Main Thread Got an incoming message from the child thread - "
                        + (String) msg. obj);
            }
        };

        new ChildThead().start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChildHandler.getLooper().quit();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.test:
                Message childMsg = mChildHandler.obtainMessage();
                childMsg. obj = mMainHandler.getLooper().getThread().getName() + " says Hello";
                mChildHandler.sendMessage(childMsg);

                LogUtils.i(TAG,"Main Thead Send a message to the child thread - " + (String)childMsg. obj);
                break;
        }
    }

    private class ChildThead extends Thread{
        @Override
        public void run() {

            Looper.prepare();

            mChildHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    LogUtils.i(TAG,"Child Thread Got an incoming message from the main thread - " + (String)msg.obj);


                    LogUtils.i(TAG,"Child Thread do something......................");

                    try {

                        //在子线程中可以做一些耗时的工作
                        sleep(2000);


                        Message toMain = mMainHandler.obtainMessage();
                        toMain. obj = "This is " + this.getLooper().getThread().getName() +
                                ".  Did you send me \"" + (String)msg.obj + "\"?";

                        mMainHandler.sendMessage(toMain);

                        LogUtils.i(TAG,"Child Thread Send a message to the main thread - " + (String)toMain.obj);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };

            Looper.loop();

        }
    }
}
