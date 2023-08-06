package com.wning.demo.ui.activity

import android.app.Activity
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.MessageQueue
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.guagua.modules.utils.LogUtils
import com.wing.android.IpcFragment
import com.wing.android.R
import com.wing.android.databinding.ActivityMain2Binding
import com.wning.demo.BaseActivity
import com.wning.demo.kotlin.coroutine.func1
import com.wning.demo.kotlin.coroutine.func1WithContext
import com.wning.demo.kotlin.coroutine.func2
import com.wning.demo.kotlin.coroutine.func2WithContext
import com.wning.demo.ui.fragment.AnimFragment
import com.wning.demo.ui.fragment.ArchitectureFragment
import com.wning.demo.ui.fragment.CustomViewFragment
import com.wning.demo.ui.fragment.NetworkFragment
import com.wning.demo.utils.currentDarkMode
import com.wning.demo.utils.getSystemDarkMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity2 : BaseActivity<ActivityMain2Binding>() {
    // lateinit用于var
    private lateinit var mFragmentManager: androidx.fragment.app.FragmentManager
    //延迟初始化，只有第一次调用的时候 才初始化，by lazy只用于val
    private val mDefaultFragment: androidx.fragment.app.Fragment by lazy {
        IpcFragment()
    }

    companion object {
        val TAG_VIEW = "view"
        val TAG_ANIM = "anim"
        val TAG_NETWORK = "network"
        val TAG_ARCHITECTURE = "architecture"  //MainActivity2.Companion.TAG_ARCHITECTURE
        const val TAG_IPC = "ipc"  //相当于java中的常量

        //真正的静态方法
        @JvmStatic
        fun staticMethod() {
            println("this is static method")
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.

        installSplashScreen()

        super.onCreate(savedInstanceState)
        //切换 splash screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (currentDarkMode != AppCompatDelegate.MODE_NIGHT_YES) {
                splashScreen.setSplashScreenTheme(R.style.Theme_App_Starting)
            } else {
                splashScreen.setSplashScreenTheme(R.style.Theme_App_Starting2)

            }
        }
        viewBinding.navigationView?.setItemIconTintList(null)
        setSupportActionBar(viewBinding.toolbar)
        val mToggle = ActionBarDrawerToggle(
            this,
            viewBinding.drawerLayout,
            viewBinding.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        mToggle.syncState()
        viewBinding.drawerLayout?.addDrawerListener(mToggle)
        setDrawerLeftEdgeSize(this, viewBinding.drawerLayout, 0.1f)

        //头部点击事件
        viewBinding.navigationView?.getHeaderView(0)?.setOnClickListener { LogUtils.i(TAG, "NavigationView head clicked") }
        //item点击事件
        viewBinding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.customView -> switchFragment(CustomViewFragment())
                R.id.anim -> switchFragment(AnimFragment())
                R.id.network -> switchFragment(NetworkFragment())
                R.id.architecture -> switchFragment(ArchitectureFragment())
                R.id.ipc -> switchFragment(IpcFragment())
            }
            menuItem.isChecked = true
            viewBinding.drawerLayout?.closeDrawers()
            true
        }
        /**
         * if (((MainActivity2)this).mFragmentManager == null) {
        FragmentManager var10001 = this.getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(var10001, "supportFragmentManager");
        this.mFragmentManager = var10001;
        }
         */
        if (!::mFragmentManager.isInitialized) {  //增加判空处理
            mFragmentManager = supportFragmentManager
        }

        //默认使用ipc

        mDefaultFragment.let {
            mFragmentManager!!.beginTransaction().show(it)
                .replace(R.id.content, it)
                .commit()
        }

//        testKotlin()


        //非静态匿名内部类发送延迟消息，然后立即点击返回按钮
//        Handler().postDelayed(Runnable { println() }, 10000)


        /**
         * com.wing.android                     I  main end
        2023-07-04 20:31:37.485  3641-3641  System.out
        com.wing.android
        I  1协程执行—main_id_2
        2023-07-04 20:31:37.787  3641-3641  System.out
        com.wing.android                     I  2协程执行— main_id_2
         */
//        GlobalScope.launch(Dispatchers.Main) {
//            func1()
//            func2()
//        }

        GlobalScope.launch {
            //withcontext 是串行执行的
            val one = func1WithContext()
            val two = func2WithContext(one)
            val sum = one+two
            println("两个方法返回值的和：${sum}")
        }
        println("main end")
    }

    suspend fun func1WithContext():Int = withContext(Dispatchers.IO) {
        delay(1500)
        println("1协程执行—${Thread.currentThread().name}_id_${Thread.currentThread().id}")
        1
    }

    suspend fun func2WithContext(one: Int):Int = withContext(Dispatchers.IO) {
        delay(300)
        println( "2协程执行— ${Thread.currentThread().name}_id_${Thread.currentThread().id}")
        2+one
    }

    suspend fun func1() {
        println("1协程执行—start")

        delay(1500)
        println("1协程执行—${Thread.currentThread().name}_id_${Thread.currentThread().id} end")
    }

    suspend fun func2() {
        println("2协程执行—start")

        delay(300)
        println( "2协程执行— ${Thread.currentThread().name}_id_${Thread.currentThread().id} end")

    }

    private fun testKotlin() {
        Looper.myQueue().addIdleHandler ({ ->
            println("queueIdle return true")
            true
        })

        Looper.myQueue().addIdleHandler {
            println("queueIdle return true")
            true
        }
        Looper.myQueue().addIdleHandler (object : MessageQueue.IdleHandler {
            override fun queueIdle(): Boolean {
                TODO("Not yet implemented")
                return true
            }
        })

        viewBinding.toolbar?.setOnClickListener ({ v ->
            v?.visibility = View.GONE
            println("queueIdle return true")

        })

        viewBinding.toolbar?.setOnClickListener { v ->
            v.visibility = View.GONE
            println("queueIdle return true")

        }

        viewBinding.toolbar?.setOnClickListener {
            println("11333")
            println("queueIdle return true")

        }

        viewBinding.toolbar?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                v?.visibility = View.GONE
            }
        })

    }

    // 根据所点列表项的下标，切换fragment
    fun switchFragment(fragmentId: Fragment) {
        viewBinding.drawerLayout!!.closeDrawers()
        if (mDefaultFragment === fragmentId) return
        val fragmentTransaction = mFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.content, fragmentId)
            .addToBackStack(fragmentId.javaClass.simpleName).commit()
        //mDefaultFragment = fragmentId
    }

    /**
     * drawerlayout 全屏滑动
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage
     */
    private fun setDrawerLeftEdgeSize(
        activity: Activity?,
        drawerLayout: DrawerLayout?,
        displayWidthPercentage: Float
    ) {
        if (activity == null || drawerLayout == null) return
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            val leftDraggerField = drawerLayout.javaClass.getDeclaredField("mLeftDragger") //Right
            leftDraggerField.isAccessible = true
            val leftDragger = leftDraggerField[drawerLayout] as ViewDragHelper

            // 找到 edgeSizeField 并设置 Accessible 为true
            val edgeSizeField = leftDragger.javaClass.getDeclaredField("mEdgeSize")
            edgeSizeField.isAccessible = true
            val edgeSize = edgeSizeField.getInt(leftDragger)

            // 设置新的边缘大小
            val displaySize = Point()
            activity.windowManager.defaultDisplay.getSize(displaySize)
            edgeSizeField.setInt(
                leftDragger, Math.max(
                    edgeSize, (displaySize.x *
                            displayWidthPercentage).toInt()
                )
            )
        } catch (e: Exception) {
        }
    }
}