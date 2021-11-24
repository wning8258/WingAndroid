package com.wning.demo.customview.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.guagua.modules.utils.LogUtils;

/**
 * Created by wning on 2018/1/3.
 */

public class InterceptView extends View {

    private static final String TAG="InterceptView";

    public InterceptView(Context context) {
        super(context);
    }

    public InterceptView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtils.i(TAG, "onTouchListener ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        LogUtils.i(TAG, "onTouchListener ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtils.i(TAG, "onTouchListener ACTION_UP");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        LogUtils.i(TAG, "onTouchListener ACTION_CANCEL");
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            //    getParent().requestDisallowInterceptTouchEvent(true);
                LogUtils.i(TAG, "onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i(TAG, "onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
               // getParent().requestDisallowInterceptTouchEvent(false);
                LogUtils.i(TAG, "onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtils.i(TAG, "onTouchEvent ACTION_CANCEL");
                break;
        }
        return true;
    }
}
