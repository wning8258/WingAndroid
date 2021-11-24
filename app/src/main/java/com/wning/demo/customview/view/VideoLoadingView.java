package com.wning.demo.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.guagua.anim.util.AnimBitmapLoader;
import com.guagua.modules.utils.Utils;

/**
 * Created by Administrator on 2016/7/20.
 */
public class VideoLoadingView extends View {

    private static final String TAG ="VideoLoadingView" ;
    private Thread spriteThread;
    private long sleepTime = 100;



    private int state = 0;
    private final int START = 1;
    private final int SCALE = 2;
    private final int ALPHA = 3;
    private final int FINISH = 4;

    /**
     * 以宽为基准的比例（如宽为720，opt=720/720=1,宽为360则为opt=720/360=2）
     */
    private float opt = 1;
    private boolean running = false;
    private OnGiftListener mGiftListener;

    private int mWidth,mHeight;

    private int mBitBearWidth,mBitBearHeight;


    private Bitmap[] bit_bears;
    private Bitmap bit_shadow;
    private Bitmap bit_bg;
    private Bitmap bit_loadingText;
    private Bitmap bit_dot;
    private Bitmap bit_big_dust;
    private Bitmap bit_small_dust;


    private float x = 0, y = 0;
    private float x_text=0,y_text=0;
    private float x_dot=0,y_dot=0;
    private float x_dust_big=0,y_dust_big=0;
    private float x_dust_small=0,y_dust_small=0;
    private float scale_dust_big=0,scale_dust_small=0;
    private float rotate_dust=0;
    private int alpha_dust=1;
    private int index_bear;

    private float scaleX_shadow,scaleY_shadow;

    private float[] scale_shadows={1f,0.7f,0.6f,0.9f,0.8f,0.7f,0.8f};

    private Paint textPaint;
    private Paint dustPaint;

    private int loadingTextMargin;

    private int dotCount=1;

    private int offsetCount=-1;
    private int totalCount=7;



    private final int HANDLER_DRAW = 1;
    private final int HANDLER_LISTENER = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case HANDLER_DRAW:
                    invalidate();
                    break;
                case HANDLER_LISTENER:
                    if (mGiftListener != null) {
                        mGiftListener.onGiftAnimFinish();
                    }
                    break;
            }
        };
    };

    public VideoLoadingView(Context context) {
        super(context);
        init();
    }

    public VideoLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void initOpt(){
        // 适配参数
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);

        float width = wm.getDefaultDisplay().getWidth();
        opt = width / 720;
      //  LogUtils.i(TAG,"opt :"+opt);

        textPaint=new Paint();
        textPaint.setStrokeWidth(3);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.RED);

        dustPaint=new Paint();

        loadingTextMargin=Utils.dip2px(getContext(),3);

      //  LogUtils.e(TAG,"sin PI/2:"+Math.sin(Math.PI/2));   //1
      //  LogUtils.e(TAG,"sin PI:"+Math.sin(Math.PI));   //约等于0
    }

    private void init() {
        initOpt();
        bit_bears = new Bitmap[7];
        try {
            for (int i = 0; i < bit_bears.length; i++) {
                bit_bears[i] = AnimBitmapLoader.getInstance().getBitmap(
                        getContext(), "loading/qiqi_room_video_loading_bear" + i  + ".png", opt);
                if (bit_bears[i] == null) {
                    if (mGiftListener != null) {
                        mGiftListener.onError("礼物动画");
                    }
                    state = FINISH;// 解决内存不足无法解析图片而导致动画无法运行
                    break;
                }
            }
            bit_shadow=  AnimBitmapLoader.getInstance().getBitmap(getContext(),"loading/qiqi_room_video_loading_shadow.png", opt);
            bit_bg= AnimBitmapLoader.getInstance().getBitmap(getContext(),"loading/qiqi_room_video_loading_bg.png", opt);
            bit_loadingText= AnimBitmapLoader.getInstance().getBitmap(getContext(),"loading/qiqi_room_video_loading_text.png", opt);
            bit_dot= AnimBitmapLoader.getInstance().getBitmap(getContext(),"loading/qiqi_room_video_loading_dot.png", opt);
            bit_big_dust= AnimBitmapLoader.getInstance().getBitmap(getContext(),"loading/qiqi_room_video_loading_dust1.png", opt);
            bit_small_dust= AnimBitmapLoader.getInstance().getBitmap(getContext(),"loading/qiqi_room_video_loading_dust2.png", opt);

        }catch (Exception e) {
            e.printStackTrace();
        }
        catch (OutOfMemoryError oe) {
            if (mGiftListener != null) {
                mGiftListener.onError("SRP Boom");
            }
            state = FINISH;//如果内存不够导致解析图片没有，则不会播放动画
            System.gc();
//            if (mGiftListener != null) {
//                mGiftListener.end();
//            }
        }
    }





    public void start() {
        if(!running) {
            running = true;
            index_bear = -1;
            if (spriteThread == null) {
                spriteThread = new Thread(run);
                spriteThread.start();
            }
        }
    }

    public void stop(){
        try {
            if(running) {
                running = false;
                index_bear = -1;
                if(spriteThread!=null){
                    spriteThread.interrupt();
                    spriteThread=null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Runnable run = new Runnable() {
        public void run() {
            running = true;
          //  tranTimeStep = 0;
            long curTime = 0;
            while (running && state != FINISH) {
                curTime = System.currentTimeMillis();
               move();
                mHandler.sendEmptyMessage(HANDLER_DRAW);
                curTime = System.currentTimeMillis() - curTime;
                try {
                    if (curTime < sleepTime) {
                        Thread.sleep(sleepTime - curTime);
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
            // 添加动画结束回调
            mHandler.sendEmptyMessage(HANDLER_LISTENER);
        }
    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    private void move() {
        index_bear++;
        if(index_bear >=bit_bears.length){
            index_bear =0;
        }
        //阴影scale
        scaleX_shadow=scaleY_shadow=scale_shadows[index_bear];  //scale_shadows[index_bear]
        //文本坐标
        x_text=x+mBitBearWidth;
        y_text=y+(mBitBearHeight-bit_loadingText.getHeight())/2;

        x_dot=x_text+bit_loadingText.getWidth()+loadingTextMargin;
        y_dot=y_text+(bit_loadingText.getHeight()-bit_dot.getHeight())/2;


        dotCount++;
        if(dotCount>7){
            dotCount=2;
        }

        x_dust_small=x-bit_small_dust.getWidth()+Utils.dip2px(getContext(),10);
        x_dust_big=x_dust_small-bit_big_dust.getWidth();

        y_dust_big=y+mBitBearHeight-bit_big_dust.getHeight();
        y_dust_small=y+mBitBearHeight-bit_small_dust.getHeight();

        offsetCount++;
        if(offsetCount>=totalCount){
            offsetCount=0;
        }

        x_dust_big=x_dust_big-Utils.dip2px(getContext(),7)*offsetCount;
        y_dust_big= (float) (y_dust_big-Utils.dip2px(getContext(),7)*Math.sin(Math.PI-Math.PI*((float)offsetCount/totalCount)));
        x_dust_small=x_dust_small-Utils.dip2px(getContext(),7)*offsetCount;
        y_dust_small= (float) (y_dust_small-Utils.dip2px(getContext(),7)*Math.sin(Math.PI-Math.PI*((float)offsetCount/totalCount)));


        if(offsetCount>=0&&offsetCount<=2) {
            scale_dust_big = 0.4f * offsetCount + 0.2f;
            scale_dust_small = 0.3f * offsetCount + 0.4f;
            alpha_dust=255;

            rotate_dust=0;

        }else{
            scale_dust_big=1;
            scale_dust_small=1;

            alpha_dust= (int) (((totalCount-offsetCount)*0.3f-0.2f)*255);

            rotate_dust=-3* offsetCount+9;
        }

     //   LogUtils.i(TAG,"x_dust_big :"+x_dust_big+" offsetCount : "+offsetCount);
    }





    @Override
    protected void onDraw(Canvas canvas) {
        //背景颜色
        Rect srcRect=new Rect(0,0,bit_bg.getWidth(),bit_bg.getHeight());
        Rect desRect=new Rect(0,0,mWidth,mHeight);
        canvas.drawBitmap(bit_bg,srcRect,desRect, null);
        //加载中的文本
        canvas.drawBitmap(bit_loadingText,x_text,y_text,null);
        //dot
        for (int i = 0; i <dotCount/2; i++) {
            canvas.drawBitmap(bit_dot,x_dot+loadingTextMargin*i,y_dot,null);
        }
        //熊
        if(index_bear<=bit_bears.length-1&&index_bear>=0) {
            canvas.drawBitmap(bit_bears[index_bear], x, y, null);
        }

        //阴影
        Matrix matrix=new Matrix();
        matrix.setScale(scaleX_shadow,scaleY_shadow);
        float prex=x;
        float prey=y+bit_bears[index_bear].getHeight();
        //    float xOffset=(bit_bears[index_bear].getHeight()-bit_shadow.getHeight())/2;
        matrix.preTranslate(-bit_shadow.getWidth()/2, -bit_shadow.getHeight()/2);
        matrix.postTranslate(prex+bit_shadow.getWidth()/2+ Utils.dip2px(getContext(),5),prey+ bit_shadow.getHeight()/2- Utils.dip2px(getContext(),10));
        canvas.drawBitmap(bit_shadow,matrix,null);


        dustPaint.setAlpha(alpha_dust);
        matrix=new Matrix();
        matrix.setScale(scale_dust_big,scale_dust_big);
        //    float xOffset=(bit_bears[index_bear].getHeight()-bit_shadow.getHeight())/2;
        matrix.preTranslate(-bit_big_dust.getWidth()/2, -bit_big_dust.getHeight()/2);
        matrix.postTranslate(x_dust_big+bit_big_dust.getWidth()/2,y_dust_big+ bit_big_dust.getHeight()/2);
        matrix.postRotate(rotate_dust,bit_big_dust.getWidth()/2,bit_big_dust.getHeight()/2);
        canvas.drawBitmap(bit_big_dust,matrix,dustPaint);


        matrix=new Matrix();
        matrix.setScale(scale_dust_small,scale_dust_small);

        //    float xOffset=(bit_bears[index_bear].getHeight()-bit_shadow.getHeight())/2;
        matrix.preTranslate(-bit_small_dust.getWidth()/2, -bit_small_dust.getHeight()/2);
        matrix.postTranslate(x_dust_small+bit_small_dust.getWidth()/2,y_dust_small+ bit_small_dust.getHeight()/2);
        matrix.postRotate(rotate_dust,bit_small_dust.getWidth()/2,bit_small_dust.getHeight()/2);
        canvas.drawBitmap(bit_small_dust,matrix,dustPaint);
    }




//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int mode = MeasureSpec.getMode(widthMeasureSpec);
//        int size = MeasureSpec.getSize(widthMeasureSpec);
//
//        if(mode==MeasureSpec.EXACTLY){
//            mWidth=size;
//        }else{
//            // 由图片决定的宽
//            int desireByImg = getPaddingLeft() + getPaddingRight()
//                    + bit_bears[0].getWidth();
//
//            if (mode == MeasureSpec.AT_MOST)// wrap_content
//            {
//                mWidth = Math.min(desireByImg, size);
//            } else {
//                mWidth = desireByImg;
//            }
//        }
//
//         mode = MeasureSpec.getMode(heightMeasureSpec);
//         size = MeasureSpec.getSize(heightMeasureSpec);
//
//        if(mode==MeasureSpec.EXACTLY){
//            mHeight=size;
//        }else{
//            // 由图片决定的宽
//            int desireByImg = getPaddingTop() + getPaddingBottom()
//                    + bit_bears[0].getHeight();
//
//            if (mode == MeasureSpec.AT_MOST)// wrap_content
//            {
//                mHeight = Math.min(desireByImg, size);
//            } else {
//                mHeight = desireByImg;
//            }
//        }
//        setMeasuredDimension(mWidth,mHeight);
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (state != 0) {
            return;
        }
      //  init();
        if (state == FINISH) {
            return;
        }

        mBitBearWidth=bit_bears[0].getWidth();
        mBitBearHeight=bit_bears[0].getHeight();

        x = (getWidth() - mBitBearWidth- bit_loadingText.getWidth() ) / 2;
        y = (getHeight() - mBitBearHeight)/2;
        state = START;
        start();
    }

    public interface OnGiftListener{
        void onGiftAnimFinish();
        /**
         * 由内存不够活内存溢出导致的动画没有播放错误
         */
        void onError(String errorInfo);
    }

    @Override
    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        running = false;
    }
}
