package com.wning.demo.customview.view.bezier;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.wing.android.R;

/**
 * Created by Administrator on 2016/7/28.
 */
public class BezierView2 extends View{

    private Paint paint;
    private Paint textPaint;

    private Path path;

    private float mX;
    private float mY;
    private float offset = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    private int type;
    private static final int TYPE_LINE = 0;
    private static final int TYPE_BEZIER= 1;



    public BezierView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BezierView2);
        type=typedArray.getInt(R.styleable.BezierView2_lineType,0);
        typedArray.recycle();
    }

    private void init() {
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);


        textPaint=new Paint();
        textPaint.setStrokeWidth(3);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.RED);

        path=new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
            String text;
            if(type==TYPE_LINE) {
               text="直接划线";
            }else{
                text="贝塞尔曲线划线";
            }

            Rect bounds = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), bounds);
            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
            int baseline =getMeasuredHeight()/ 2 +(fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            canvas.drawText(text,getMeasuredWidth() / 2 - bounds.width() / 2, baseline, textPaint);
          //  canvas.drawText(text, 0, 0, textPaint);

       canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
               float x= event.getX();
               float y= event.getY();
                mX=x;
                mY=y;
                path.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                float endx=event.getX();
                float endy=event.getY();
                float preX = mX;
                float preY = mY;
                float dx = Math.abs(endx - preX);
                float dy = Math.abs(endy - preY);
                if (dx >= offset || dy >= offset) {
                    // 贝塞尔曲线的控制点为起点和终点的中点
                    float cX = (endx + preX) / 2;
                    float cY = (endy + preY) / 2;
                    if(type==TYPE_BEZIER) {
                        //三个点为：上一条线的中点，上一条线的终点，当前线的中点（最后一条线的中点到终点是没有画出来的）
                        path.quadTo(preX, preY, cX, cY);
                        Log.i("wing","quadTo cX :"+cX+" cY "+cY);
                    }else{
                        path.lineTo(endx, endy);
                        Log.i("wing","line endx :"+endx+" endy "+endy);
                    }
                    mX = endx;
                    mY = endy;
                }
                break;
        }
        invalidate();
        return true;
    }
}
