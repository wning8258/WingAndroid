package com.wning.demo.customview.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.guagua.modules.utils.LogUtils;

/**
 * Created by wning on 2018/1/3.
 */

public class InterceptLayout extends LinearLayout {

    private static final String TAG="InterceptLayout";

    public InterceptLayout(Context context) {
        super(context);
    }

    public InterceptLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogUtils.i(TAG,"dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i(TAG,"dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.i(TAG,"dispatchTouchEvent AoCTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtils.i(TAG,"dispatchTouchEvent ACTION_CANCEL");
                break;
        }
        return super.dispatchTouchEvent(ev);
     //   return true;
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogUtils.i(TAG,"onInterceptTouchEvent ACTION_DOWN");
                break;
           //     return true; //拦截掉之后，后续所有的onInterceptTouchEvent都不再执行了（即父view拦截掉事件后之后，不再可能把事件重新交给子view）
            case MotionEvent.ACTION_MOVE:
                LogUtils.i(TAG,"onInterceptTouchEvent ACTION_MOVE");
                break;
               // return true;  //拦截掉之后，后续所有的onInterceptTouchEvent都不再执行了（即父view拦截掉事件后之后，不再可能把事件重新交给子view）
            case MotionEvent.ACTION_UP:
                LogUtils.i(TAG,"onInterceptTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtils.i(TAG,"onInterceptTouchEvent ACTION_CANCEL");
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.i(TAG, "onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i(TAG, "onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.i(TAG, "onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtils.i(TAG, "onTouchEvent ACTION_CANCEL");
                break;
        }
        return true;
    }
}
