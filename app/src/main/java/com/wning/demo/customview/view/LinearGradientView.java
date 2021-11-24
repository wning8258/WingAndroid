package com.wning.demo.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wing.android.R;

/**
 * Created by wning on 2018/3/15.
 * 从左往右线性渐变
 */

public class LinearGradientView extends View {

    private LinearGradient linearGradient;

    private Paint paint;
    private int layerId;
    private Xfermode xfermode;

    public LinearGradientView(Context context) {
        super(context);
        init();
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint=new Paint();
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);  // /** [Sa * Da, Sa * Dc] */
      //  paint.setXfermode(xfermode);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        layerId=canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ufo_background);
        canvas.drawBitmap(bitmap,0,0,null);

        linearGradient = new LinearGradient(0, 0.0f, getWidth(),0, new int[]{Color.BLACK,0}, null, Shader.TileMode.CLAMP);
        paint.setXfermode(xfermode);
        paint.setShader(linearGradient);
        canvas.drawRect(0, 0,getWidth(), getHeight(), paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }
}
