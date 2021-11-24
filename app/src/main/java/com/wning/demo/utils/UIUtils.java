package com.wning.demo.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.guagua.modules.utils.LogUtils;
import com.wning.demo.widget.AnimLoadingDialog;

import androidx.annotation.NonNull;

/**
 * Created by wning on 16/7/17.
 */

public class UIUtils {

    private static final String TAG="UIUtils";

    /**
     * 设置dialog
     */
    public static Dialog showAnimLoading(Context context, boolean canWatchOutsideTouch, boolean dimBehindEnabled) {
        AnimLoadingDialog dialog = new AnimLoadingDialog(context).setWatchOutsideTouch(canWatchOutsideTouch).setDimBehindEnabled(dimBehindEnabled)
                .setCentered(true);
        dialog.show();
        LogUtils.i(TAG, "loadingDialog show context = " + context + ", dialog = " + dialog);
        return dialog;
    }

    public static void dismissAnimLoading(Dialog dialog) {
        LogUtils.i(TAG, "loadingDialog dismiss dialog = " + dialog);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 适配：修改设备密度
     */
    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            // 防止系统切换后不起作用
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        float targetDensity = appDisplayMetrics.widthPixels / 360;
        // 防止字体变小
        float targetScaleDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;

    }
}
