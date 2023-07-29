package com.wning.demo.utils

import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import com.wning.demo.ActivityTracker
import com.wning.demo.ui.activity.MainActivity2


var currentDarkMode: Int = 0
    get() = AppCompatDelegate.getDefaultNightMode()
    set(value) {
        field = value
        AppCompatDelegate.setDefaultNightMode(value)
        AppInfoSPManager.getInstance().darkMode = value
        ActivityTracker.foreground?.let {
            val intent = Intent(it, MainActivity2::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            it.startActivity(intent)
        }
    }

val allDarkModes = arrayListOf(AppCompatDelegate.MODE_NIGHT_NO,AppCompatDelegate.MODE_NIGHT_YES,AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

//切换夜间模式
fun switchMode(toMode:Int) {
    if (currentDarkMode == toMode || toMode !in allDarkModes) {
        return
    }
    currentDarkMode = toMode
}

fun initNightMode() {
    AppCompatDelegate.setDefaultNightMode(AppInfoSPManager.getInstance().darkMode)
    //更新 appcontext,使其支持获取黑夜模式的color、drawable
    val res: Resources = AppContext.resources
    val configuration = Configuration(res.configuration)
    val filter = res.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()

    configuration.uiMode = when (AppCompatDelegate.getDefaultNightMode()) {
        AppCompatDelegate.MODE_NIGHT_NO -> Configuration.UI_MODE_NIGHT_NO or filter
        AppCompatDelegate.MODE_NIGHT_YES -> Configuration.UI_MODE_NIGHT_YES or filter
        else -> res.configuration.uiMode
    }
//    ServiceLocator.appContext = AppContext.createConfigurationContext(configuration)
}

fun getSystemDarkMode():Int {
    if ((AppContext.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager).nightMode == UiModeManager.MODE_NIGHT_YES) {
        return AppCompatDelegate.MODE_NIGHT_YES
    }
    return AppCompatDelegate.MODE_NIGHT_NO
}
