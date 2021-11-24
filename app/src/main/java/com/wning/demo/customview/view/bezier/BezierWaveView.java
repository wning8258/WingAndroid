package com.wning.demo.customview.view.bezier;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2016/8/3.
 */
public class BezierWaveView extends View{

    private Paint mPaint;
    private Path mPath;
    private int mItemWaveLength = 400;

    private int dx=0;
    private  ValueAnimator valueAnimator;

    public BezierWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnim();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int originY = 300;
        int halfWaveLen = mItemWaveLength/2;
        mPath.moveTo(-mItemWaveLength+dx,originY);
        for (int i = -mItemWaveLength;i<=getWidth()+mItemWaveLength;i+=mItemWaveLength){
            //参数所有的x,y坐标都是相对于rQuadTo上次的终点的相对坐标
            mPath.rQuadTo(halfWaveLen/2,-50,halfWaveLen,0);
            mPath.rQuadTo(halfWaveLen/2,50,halfWaveLen,0);
        }

        canvas.drawPath(mPath,mPaint);
    }

    private void startAnim(){
        valueAnimator = ValueAnimator.ofInt(0, mItemWaveLength);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx= (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    private void stopAnim(){
        if(valueAnimator!=null&&valueAnimator.isRunning()){
            valueAnimator.cancel();
        }
    }

}
