package com.wing.android;

import android.app.Application;

import com.networkbench.agent.impl.NBSAppAgent;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NBSAppAgent.setLicenseKey("0f8afd35dcc74acd87c34adb64382409")
                .withLocationServiceEnabled(true).start(this.getApplicationContext());//Appkey 请从官网获取

    }
}
