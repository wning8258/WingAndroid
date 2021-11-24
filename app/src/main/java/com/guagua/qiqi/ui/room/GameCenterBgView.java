package com.guagua.qiqi.ui.room;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.guagua.modules.utils.Utils;
import com.wing.android.R;

/**
 * Created by wning on 2016/10/19.
 */

public class GameCenterBgView extends View {

    private Paint paint;
    private RectF rect ;
    private RectF rect_arc;
    private RectF rect2;

    private PorterDuffXfermode clear;
    private PorterDuffXfermode src;
    private Paint bgPaint;

    public GameCenterBgView(Context context) {
        super(context);
        init();
    }

    public GameCenterBgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    int w,h;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setRect(w,h);


    }

    private void setRect(int w, int h) {
        this.w=w;
        this.h=h;
        rect = new RectF(0, 0, w, h- Utils.dip2px(getContext(),25));
        //  rect_corner = new RectF(w-Utils.dip2px(getContext(),10),h- Utils.dip2px(getContext(),35), w, h- Utils.dip2px(getContext(),25));
        rect_arc = new RectF(w- Utils.dip2px(getContext(),50), h- Utils.dip2px(getContext(),50), w, h);
        rect2=new RectF(w- Utils.dip2px(getContext(),20),h- Utils.dip2px(getContext(),20)- Utils.dip2px(getContext(),25),
                w,h- Utils.dip2px(getContext(),25));
    }


    private void init() {

        clear=new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        src=new PorterDuffXfermode(PorterDuff.Mode.SRC);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.qiqi_room_game_bg));
        paint.setAlpha((int) (255*0.6));
        paint.setStrokeWidth(1);

        bgPaint=new Paint();
        bgPaint.set(paint);

         path2=new Path();
    }
    Path path2;

    private Bitmap drawBg(){

        Bitmap target = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(target);
       // paint.setXfermode(clear);
       // canvas.drawPaint(bgPaint);
       // bgPaint.setXfermode(src);
        canvas.drawRoundRect(rect, Utils.dip2px(getContext(),10), Utils.dip2px(getContext(),10),bgPaint);
        bgPaint.setXfermode(src);
        canvas.drawArc(rect_arc, 0,360, false, bgPaint);

        return target;
    }

    @Override
    protected void onDraw(Canvas canvas) {


        //canvas.drawBitmap(drawBg(),0,0,paint);


        canvas.drawRoundRect(rect, Utils.dip2px(getContext(),10), Utils.dip2px(getContext(),10),paint);
        canvas.drawArc(rect_arc, 0,180, false, paint);


         path2.reset();

//        path2.moveTo(w-Utils.dip2px(getContext(),11), h-Utils.dip2px(getContext(),25));//设置Path的起点
//        path2.quadTo(w, h-Utils.dip2px(getContext(),25),w,h-Utils.dip2px(getContext(),25)- Utils.dip2px(getContext(),11)); //设置贝塞尔曲线的控制点坐标和终点坐标
//        path2.lineTo(getWidth(), getHeight()-Utils.dip2px(getContext(),25));
//
//        path2.close();

        path2.addArc(rect2,0,90);
        path2.lineTo(w, h- Utils.dip2px(getContext(),25));
        path2.close();


        canvas.drawPath(path2, paint);//画出贝塞尔曲线
        super.onDraw(canvas);
    }


}
