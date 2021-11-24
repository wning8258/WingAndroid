package com.wning.demo.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.wing.android.R;


/**
 * Created by Administrator on 2016/6/8.
 */
public class XfermodeRoundImageView extends ImageView {

    /**
     * TYPE_CIRCLE / TYPE_ROUND
     */
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;

    /**
     * 图片
     */
    private Bitmap mSrc;

    /**
     * 圆角的大小
     */
    private int mRadius;

    /**
     * 控件的宽度
     */
    private int mWidth;
    /**
     * 控件的高度
     */
    private int mHeight;


    public XfermodeRoundImageView(Context context) {
        super(context,null);
    }

    public XfermodeRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);

//        int resourceId = typedArray.getResourceId(R.styleable.CustomImageView_src, 0);
//        mSrc= BitmapFactory.decodeResource(getResources(),resourceId);

        mRadius=typedArray.getDimensionPixelSize(R.styleable.CustomImageView_borderRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f,
                        getResources().getDisplayMetrics()));

        type=typedArray.getInt(R.styleable.CustomImageView_type,TYPE_CIRCLE);

        typedArray.recycle();

        mSrc=drawableToBitamp(getDrawable());
    }

    private Bitmap drawableToBitamp(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 圆形图片
     * @param source bitmap源
     * @param min  显示的大小
     * @return
     */
    private Bitmap createCircleImage(Bitmap source ,int min){


        /**
         * 长度如果不一致，按小的值进行压缩
         */
        source = Bitmap.createScaledBitmap(source, min, min, false);

        Paint paint=new Paint();
        paint.setAntiAlias(true);

        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(target);
        canvas.drawCircle(min/2,min/2,min/2,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));  //sa*da,sc*da

        canvas.drawBitmap(source,0,0,paint);

        return target;

    }

    private Bitmap createRoundConerImage(Bitmap source) {
        Paint paint=new Paint();
        paint.setAntiAlias(true);

     //   Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Bitmap target = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(target);

        RectF rectF=new RectF(0,0,source.getWidth(),source.getHeight());
        canvas.drawRoundRect(rectF,mRadius,mRadius,paint);


        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));  ///** [Sa * Da, Sc * Da] */
        canvas.drawBitmap(source,0,0,paint);

        return target;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int mode = MeasureSpec.getMode(widthMeasureSpec);
//        int size = MeasureSpec.getSize(widthMeasureSpec);
//
//        if(mode==MeasureSpec.EXACTLY){
//            mWidth=size;
//        }else{
//            // 由图片决定的宽
//            int desireByImg = getPaddingLeft() + getPaddingRight()
//                    + mSrc.getWidth();
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
//                    + mSrc.getHeight();
//
//            if (mode == MeasureSpec.AT_MOST)// wrap_content
//            {
//                mHeight = Math.min(desireByImg, size);
//            } else {
//                mHeight = desireByImg;
//            }
//        }
//
//        if(type==TYPE_CIRCLE){
//               int min = Math.min(mWidth, mHeight);
//               setMeasuredDimension(min,min);
//        }else {
//            setMeasuredDimension(mWidth, mHeight);
//        }

        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        if(type==TYPE_CIRCLE){
            int min = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(min,min);
        }
    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas)
    {
        switch (type)
        {
            // 如果是TYPE_CIRCLE绘制圆形
            case TYPE_CIRCLE:
             //   int min = Math.min(mWidth, mHeight);
                int min = Math.min(getMeasuredWidth(), getMeasuredHeight());


                canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
                break;
            case TYPE_ROUND:
                canvas.drawBitmap(createRoundConerImage(mSrc), 0, 0, null);
                break;

        }

    }


}
