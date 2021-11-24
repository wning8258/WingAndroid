package com.wning.demo.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.guagua.anim.util.AnimBitmapLoader;
import com.guagua.modules.utils.LogUtils;

/**
 * Created by Administrator on 2016/7/20.
 */
public class PullToRefreshBearView extends View {

    private static final String TAG ="PullToRefreshBearView" ;
    private Thread spriteThread;
    private long sleepTime = 100;
    private long totoalTime = 3000;



    private int state = 0;
    private final int START = 1;
    private final int SCALE = 2;
    private final int ALPHA = 3;
    private final int FINISH = 4;

    /**
     * 以宽为基准的比例（如宽为720，opt=720/720=1,宽为360则为opt=720/360=2）
     */
    private float opt = 1;

    private OnErrorListener mErrorListener;

    private int mWidth;

    private int mBitBearWidth,mBitBearHeight;

    private Bitmap[] bit_bears;


    private float x = 0, y = 0;
    private int index_bear=-1;


    private int offset=0;
    private boolean isBack=false;

    boolean running=false;


    private final int HANDLER_DRAW = 1;
    private final int HANDLER_LISTENER = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case HANDLER_DRAW:
                    invalidate();
                    break;
                case HANDLER_LISTENER:

                    break;
            }
        };
    };

    public PullToRefreshBearView(Context context) {
        super(context);
        init();
    }

    public PullToRefreshBearView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void initOpt(){
        // 适配参数
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);

        float width = wm.getDefaultDisplay().getWidth();
        opt = width / 720;
    }


    private void init() {
        initOpt();

        bit_bears = new Bitmap[8];
        try {
            for (int i = 0; i < bit_bears.length; i++) {
                bit_bears[i] = AnimBitmapLoader.getInstance().getBitmap(
                        getContext(), "loading/qiqi_home_pull_to_refresh_loading_" + (i+1)  + ".png", opt);
                if (bit_bears[i] == null) {
                    if (mErrorListener != null) {
                        mErrorListener.onError("礼物动画");
                    }
                    state = FINISH;// 解决内存不足无法解析图片而导致动画无法运行
                    break;
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        catch (OutOfMemoryError oe) {
            if (mErrorListener != null) {
                mErrorListener.onError("SRP Boom");
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
            isBack = false;
            x = 0;
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
                isBack = false;
                x = 0;
                if(spriteThread!=null){
                    spriteThread.interrupt();
                    spriteThread=null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Runnable run=new  Runnable() {
        public void run() {
            long curTime = 0;
          //    LogUtils.i(TAG,"runnable :"+running+" state : "+state);
            while (running) {
           //         LogUtils.i(TAG,"runnable while");
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
        offset= (int) (mWidth*sleepTime/totoalTime);
        LogUtils.i(TAG,"onSizeChanged : "+w+","+h+",offset :"+offset);
    }

    private void move() {
        index_bear++;
        if(!isBack) {
            if (index_bear >=bit_bears.length / 2) {
                index_bear = 0;
            }
        }else{
            if (index_bear >=bit_bears.length ) {
                index_bear = bit_bears.length / 2;
            }
        }

        if(!isBack){
            x+=offset;
            if(x+bit_bears[index_bear].getWidth()>=mWidth){
                x=mWidth-bit_bears[index_bear].getWidth();
                isBack=true;
                index_bear = bit_bears.length / 2;
            }
        }else{
            x-=offset;
            if(x<=0){
                x=0;
                isBack=false;
                index_bear=0;
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //   LogUtils.i(TAG,"onDraw x : "+x+",index : "+index_bear+","+Thread.currentThread());
        //熊
        if(index_bear>=0&&index_bear<=bit_bears.length-1&&running) {
            canvas.drawBitmap(bit_bears[index_bear], x, y, null);
        }
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


        // x = (getWidth() - mBitBearWidth ) / 2;
        mBitBearWidth=bit_bears[0].getWidth();
        mBitBearHeight=bit_bears[0].getHeight();

        x=0;
        y = (getHeight() - mBitBearHeight)/2;
        state = START;
       // start();
    }

    public interface OnErrorListener{
        /**
         * 由内存不够活内存溢出导致的动画没有播放错误
         */
        void onError(String errorInfo);
    }

}
