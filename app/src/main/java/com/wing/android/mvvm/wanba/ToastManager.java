package com.wing.android.mvvm.wanba;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.wing.android.R;

import java.lang.reflect.Field;

/**
 * Created by zhangdi on 15/3/10.
 * 统计管理Toast
 */
public class ToastManager {

    private static String oldMsg;
    private static long oneTimeStamp;
    private static long twoTimeStamp;

    private static Field sField_TN;
    private static Field sField_TN_Handler;

    static {
        try {
            sField_TN = Toast.class.getDeclaredField("mTN");
            sField_TN.setAccessible(true);
            sField_TN_Handler = sField_TN.getType().getDeclaredField("mHandler");
            sField_TN_Handler.setAccessible(true);
        } catch (Exception e) {
        }
    }

    private static void hook(Toast toast) {
        try {
            Object tn = sField_TN.get(toast);
            Handler preHandler = (Handler) sField_TN_Handler.get(tn);
            sField_TN_Handler.set(tn, new SafelyHandlerWarpper(preHandler));
        } catch (Exception e) {
        }
    }

    public static void show(Context context, int resId) {
        show(context, context.getString(resId));
    }

    public static void show(final Context context, @NonNull final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                /**
                 Toast.makeText(WBContext.getContext(), text, Toast.LENGTH_SHORT).show();
                 **/
                showToast(context, text);
            }
        });
    }

    public static void showDelay(final Context context, @NonNull final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 Toast.makeText(WBContext.getContext(), text, Toast.LENGTH_SHORT).show();
                 **/
                showToast(context, text);
            }
        }, 1000);
    }

    public static void show(final Context context, @NonNull final String text, final int gravity) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                /**
                 Toast toast = Toast.makeText(WBContext.getContext(), text, Toast.LENGTH_SHORT);
                 toast.setGravity(gravity, 0, 0);
                 toast.show();
                 **/
                //UE说统一位置显示
                showToast(context, text);
            }
        });
    }

    public static void show(final Context context, @NonNull final String text, final int gravity, final int offSetY) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                /**
                 Toast toast = Toast.makeText(WBContext.getContext(), text, Toast.LENGTH_SHORT);
                 toast.setGravity(gravity, 0, offSetY);
                 toast.show();
                 **/
                //UE说统一位置显示
                showToast(context, text);
            }
        });
    }

    public static void showNoRepeatForTwoSecond(final Context context, @NonNull final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                twoTimeStamp = System.currentTimeMillis();
                if (TextUtils.equals(text, oldMsg)) {
                    if (twoTimeStamp - oneTimeStamp > 2000) {
                        /**
                         Toast.makeText(WBContext.getContext(), text, Toast.LENGTH_SHORT).show();
                         **/
                        showToast(context, text);
                    }
                } else {
                    oldMsg = text;
                    /**
                     Toast.makeText(WBContext.getContext(), text, Toast.LENGTH_SHORT).show();
                     **/
                    showToast(context, text);
                }
                oneTimeStamp = twoTimeStamp;
            }
        });
    }

    public static void showNoRepeat(final Context context, @NonNull final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                twoTimeStamp = System.currentTimeMillis();
                if (TextUtils.equals(text, oldMsg)) {
                    if (twoTimeStamp - oneTimeStamp > 4000) {
                        /**
                         Toast.makeText(WBContext.getContext(), text, Toast.LENGTH_SHORT).show();
                         **/
                        showToast(context, text);
                    }
                } else {
                    oldMsg = text;
                    /**
                     Toast.makeText(WBContext.getContext(), text, Toast.LENGTH_SHORT).show();
                     **/
                    showToast(context, text);
                }
                oneTimeStamp = twoTimeStamp;
            }
        });
    }

    public static void showToast(Context context, int resId) {
        String message = context.getResources().getString(resId);
        showToast(context, message);
    }

    public static void showToast(final Activity activity, int resId) {
        if (activity == null) {
            return;
        }
        final String message = activity.getResources().getString(resId);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(activity, message);
            }
        });
    }

    public static void showToast(Context context, int resId, int time) {
        String message = context.getResources().getString(resId);
        showCustomToast(context, message, time);
    }

    public static void showToast(Context context, String message) {
        showShortToast(context, message);
    }

    public static void showToast(final Activity activity, final String message) {
        if (activity == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showShortToast(activity, message);
            }
        });
    }

    public static void showToast(Context context, String message, int time) {
        showCustomToast(context, message, time);
    }

    public static void showShortToast(Context context, String message) {
        showCustomToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context, String message) {
        showCustomToast(context, message, Toast.LENGTH_LONG);
    }

    public static void showLongToast(Context context, int resId) {
        String message = context.getResources().getString(resId);
        showCustomToast(context, message, Toast.LENGTH_LONG);
    }

    private static void showCustomToast(Context context, final String message, int time) {
        final Toast toast = new Toast(context.getApplicationContext());
        //只处理7.1.1
        if (Build.VERSION.SDK_INT == 25) {
            hook(toast);
        }
//        TextView tvContent =
//                (TextView) View.inflate(context.getApplicationContext(), R.layout.basic_base_layout_custom_toast, null);
//        tvContent.setText(message);
//        toast.setView(tvContent);
//        if (time <= 0) {
//            toast.setDuration(Toast.LENGTH_SHORT);
//        } else {
//            toast.setDuration(time);
//        }
//        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0,
//                DisplayUtil.dip2px(context.getApplicationContext(), DisplayUtil.TOAST_STANDARD_HEIGHT_DP));
//        toast.show();
        if (time > 1) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, time);
        }
    }

    private static class SafelyHandlerWarpper extends Handler {
        private Handler impl;

        SafelyHandlerWarpper(Handler impl) {
            this.impl = impl;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
            }
        }

        @Override
        public void handleMessage(Message msg) {
            impl.handleMessage(msg); //需要委托给原Handler执行
        }
    }
}
