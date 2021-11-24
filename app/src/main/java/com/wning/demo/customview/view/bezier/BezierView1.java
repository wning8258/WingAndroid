package com.wning.demo.customview.view.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/7/27.
 */
public class BezierView1 extends View{

    private Paint paint;

    private float midX,midY;

    public BezierView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        midX=500;
        midY=400;
    }

    private void initPaint() {
        paint=new Paint();
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Path path=new Path();
        path.moveTo(200,200);
        path.quadTo(midX,midY,800,200);
        canvas.drawPath(path,paint);

        canvas.drawPoint(midX,midY,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

       switch (event.getAction()){
         case MotionEvent.ACTION_MOVE:
                midX = event.getX();
                midY = event.getY();
                invalidate();
         }
       return true;


    }
}
