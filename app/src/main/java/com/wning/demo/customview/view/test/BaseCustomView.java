package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wning on 2017/3/2.
 */

public abstract  class BaseCustomView extends View {

    protected String info;

    public BaseCustomView(Context context) {
        super(context);
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        info=setInfo();
        drawInfo(canvas);
    }

    protected abstract String setInfo();

    private final void drawInfo(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        Paint textPaint=new Paint();
        textPaint.setTextSize(30);
        textPaint.setColor(Color.RED);
        String text=info+"("+getClass().getSimpleName()+".java)";
        canvas.drawText(text,0,text.length(),0,50,textPaint);
    }

    protected Paint getPaint(){
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        return paint;
    }
}
