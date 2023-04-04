package com.wing.android.mvvm.wanba;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;


/**
 * 屏幕工具类
 *
 * @author wanba
 */
public class DisplayUtil {


    public static Context mContext;
    public static final int TOAST_STANDARD_HEIGHT_DP = 80;

    public static void initContext(Context context) {
        mContext = context;
    }

    /**
     * 获取手机屏幕高度,以px为单位
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(@NonNull Context context) {
        if (null == context) {
            context = mContext;
        }

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取手机屏幕宽度，以px为单位
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(@NonNull Context context) {
        if (null == context) {
            context = mContext;
        }

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static String getScreenWidthHeight(@NonNull Context context) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int wScreen = dm.widthPixels;
        int hScreen = dm.heightPixels;

        return "" + wScreen + "x" + hScreen;
    }

    /**
     * 返回程序window宽度
     *
     * @return
     */
    public static int getWindowWidth(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getWidth();
    }

    /**
     * 返回程序window高度，不包括通知栏和标题栏
     *
     * @return
     */
    public static int getWindowContentHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
    }

    /**
     * 返回程序window高度，不包括通知栏
     *
     * @return
     */
    public static int getWindowHeight(Context context) {
        return getScreenHeight(context) - getStatusBarHeight(context);
    }

    /**
     * 返回屏幕像素密度
     *
     * @param context
     * @return
     */
    public static float getPixelDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 返回状态栏高度
     * 不对外暴露，获取状态栏请使用ThemeUtil的getStatusBarHeight方法
     */
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static Rect getScreenHeightWithoutSystemUI(Window window) {
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect;
    }

    /**
     * 标题栏高度
     */
    public static int getTitleBarHeight(Activity activity) {
        return getScreenHeight(activity) - getWindowContentHeight(activity) - getStatusBarHeight(activity);
    }

    /**
     * 单位转换，将dip转换为px
     *
     * @param dp
     * @return
     */
    public static int dip2px(@NonNull Context context, float dp) {
        if (null == context) {
            context = mContext;
        }

        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 单位转换，将px转换为dip
     *
     * @param px
     * @return
     */
    public static int px2dip(@NonNull Context context, float px) {
        if (null == context) {
            context = mContext;
        }

        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(@NonNull Context context, float spValue) {
        if (null == context) {
            context = mContext;
        }

        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 宽高百分比
     */
    public static int getWidthWithPercentInScreen(Context context, int percent) {
        return (getScreenWidth(context) * percent) / 100;
    }

    public static int getHeightWithPercentInScreen(Context context, int percent) {
        return (getScreenHeight(context) * percent) / 100;
    }

    /**
     * 屏幕密度
     */
    public static float getDensity(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        float density = metric.density;
        return density;
    }

    /**
     * 获取当前屏幕的宽度 dp值
     *
     * @param activity
     * @return
     */
    public static float getScreenWidthDp(Activity activity) {
        return getScreenWidth(activity) / getDensity(activity);
    }

    /**
     * 十英寸pad
     *
     * @param activity
     * @return
     */
    public static boolean isTenInchPad(Activity activity) {
        return DisplayUtil.getScreenWidthDp(activity) >= 720f;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 锁屏判断
     */
    public static boolean screenIsClose(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
        return flag;
    }

    /**
     * 返回包括虚拟键在内的总的屏幕高度
     * 即使虚拟按键显示着，也会加上虚拟按键的高度
     */
    public static int getTotalScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {

            }
        }
        return size.y;
    }

    public static boolean isLandscape(Context context) {
        //获取设置的配置信息
        Configuration configuration = context.getResources().getConfiguration();
        return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

}
