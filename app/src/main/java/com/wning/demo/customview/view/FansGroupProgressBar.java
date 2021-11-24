package com.wning.demo.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wing.android.R;


/**
 * 两头都是圆角的progressbar
 * Created by wning on 2016/11/17.
 */

public class FansGroupProgressBar extends View{

    private Context context;

    private Paint paint;
    private Paint paint_progress;
    private RectF rect_arcl ;
    private RectF rect_arcr ;
    private RectF rect;

    private Bitmap bitmap;

    private int radius;

    private int w,h;

    private int progress=10,max=100;

    public FansGroupProgressBar(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public FansGroupProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    private void init(){

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.qiqi_personal_fansgroup_pb_bg));
        paint.setStrokeWidth(1);

        paint_progress=new Paint();
        paint_progress.set(paint);
        paint_progress.setColor(getResources().getColor(R.color.qiqi_personal_fansgroup_pb_arc));

        bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.qiqi_personal_fansgroup_pb_progress);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w=w;
        this.h=h;
        radius=h/2;
    }

    private void setRect(int w, int h) {
        rect_arcl=new RectF(0, 0,radius*2,radius*2);
        int right=w-radius;
        if(right<=0){
            right=radius+1;
        }
        rect=new RectF(radius,0,right,h);
        rect_arcr=new RectF(right-radius, 0,w,radius*2);
    }


    private void drawBg(Canvas canvas){
        canvas.drawArc(rect_arcl, 90,180, false, paint);
        canvas.drawRect(rect,paint);
        canvas.drawArc(rect_arcr, 270,180, false, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setRect(w,h);
        drawBg(canvas);

        if(progress>0&&max>0) {
            canvas.drawBitmap(drawProgress(), 0, 0, null);
        }
    }

    private Bitmap drawProgress( ){

        int right= (int) ((float)progress/max*w);
        Bitmap target = Bitmap.createBitmap(right, h, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(target);

//        rect_bitmap=new RectF(0,0,right,h);
//        canvas.clipRect(rect_bitmap);

        setRect(right,h);

        drawBg(canvas);

        paint_progress.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));  //sa*da,sc*da

        canvas.drawBitmap(bitmap,0,0,paint_progress);
        return target;

    }


    public void setProgress(int progress,int max){
        this.progress=progress;
        this.max=max;
        invalidate();
    }
}
