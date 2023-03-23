package com.wing.android.lru.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.wing.android.lru.fragment.FragmentActivityFragmentManager;


public class RequestManager {

    private final String TAG = RequestManager.class.getSimpleName();

    private final String FRAGMENT_ACTIVITY_NAME = "Fragment_Activity_NAME";

    // 总的环境
    private Context requestManagerContext;

    private static RequestTargetEngine callback;

    private final int NEXT_HANDLER_MSG = 995465; // Handler 标记  发送一次空的Handler

    FragmentActivity fragmentActivity;

    // 可以管理生命周期 -- FragmentActivity是有生命周期方法
    public RequestManager(FragmentActivity fragmentActivity) {
        if (callback == null) {
            callback = new RequestTargetEngine();
        }

        this.fragmentActivity = fragmentActivity;
        requestManagerContext = fragmentActivity;

        // 开始绑定操作
        FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();

        // 拿到Fragment
        Fragment fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        if (null == fragment) { // 如果等于null，就要去创建Fragment
            fragment = new FragmentActivityFragmentManager(callback);


            // 调用commitNow行吗  一定要发空hanlder吗   会奔溃  同学说...

            // 添加到管理器 -- fragmentManager.beginTransaction().add..
            supportFragmentManager.beginTransaction().add(fragment, FRAGMENT_ACTIVITY_NAME).commitAllowingStateLoss(); // 提交
        }

        // 我已经把空白的Fragment 提交了
        // TODO 测试环节
        // 测试下面的话，这种测试，不能完全准确， 证明是不是排队状态
        Fragment fragment2 = supportFragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        Log.d(TAG, "RequestManager: fragment2" + fragment2); // null ： 还在排队中，还没有消费

        // 第一节课讲的  第二重保障  发送Handler  空的  啥事没有做
        mHandler.sendEmptyMessage(NEXT_HANDLER_MSG);
    }

    final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            /*Fragment fragment2 = fragmentActivity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
            Log.d(TAG, "Handler: fragment2" + fragment2); // 有值 ： 不在排队中，所以有值*/
            return false;
        }
    });

    /**
     * TODO 用户调用的
     * load 拿到要显示的图片路径
     * @param path
     * @return
     */
    public RequestTargetEngine load(String path) {
        // 空白的Handler 移除掉
        mHandler.removeMessages(NEXT_HANDLER_MSG);

        // 把值传递给 资源加载引擎
        callback.loadValueInitAction(path, requestManagerContext);

        return callback;
    }
}
