package com.wing.android

import android.app.Application
import com.networkbench.agent.impl.NBSAppAgent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NBSAppAgent.setLicenseKey("0f8afd35dcc74acd87c34adb64382409")
                .withLocationServiceEnabled(true).start(this.applicationContext) //Appkey 请从官网获取
    }
}