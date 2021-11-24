package com.guagua.qiqi.gifteffect.elements;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

import com.guagua.qiqi.gifteffect.SceneInfo;
import com.guagua.qiqi.gifteffect.animation.algorithm.CaculationModel;
import com.guagua.qiqi.gifteffect.util.BitmapUtils;
import com.guagua.qiqi.gifteffect.util.CommonUtils;
import com.guagua.qiqi.gifteffect.util.PXUtils;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************************
 * Module Name: GiftInfoElement</br>
 * File Name: <b>GiftInfoElement.java</b></br>
 * Description: </br>
 * Author: 郁金涛</br>
 * 版权 2008-2015，浙江齐聚科技有限公司 </br>
 * 所有版权保护
 * 这是浙江齐聚科技有限公司 未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司 
 * 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
 ***************************************************************************************/
public class GiftInfoElement extends Element {

    public static final String SONG = "  送  ";
    public static final String UNIT = "  个  ";
    public static final int INFO_TEXT_SIZE = 28;
    public static final int DENSITY_320=320;
    public static final int GIFT_BITMAP_SZIE=25;
    private Rect mBGRect;
    private SceneInfo info;
    private Rect textBounds;
    //	private Rect numberBounds;
    private Rect unitBounds;
    private Paint.FontMetrics fontMetrics;
    //	private Paint.FontMetrics numberFontMetrics;
    private float y;
    private float ux;

    //描边的paintffff22字体，bb5301描边
    //真正字体paint
    private Paint realPaint;
    //描边字体paint
    private Paint eagePaint;

    //数字的滚动区域
    private RectF numberRect;
    //图片
    private Bitmap[] bitmapsNumber;
    //数字的高度和宽度
    private int numberWidth;
    private int numberheight;
    //当前用户送的礼物个数的图片的bitmap容器
    List<Bitmap> mNumberBitmaps;
    //滚动计算的公式
    CaculationModel mCalModel;
    //显示的用户名字
    private String mUserName;
    //礼物的bitmap
    private Bitmap mGiftBitmap;
    private Rect mGiftBitmapRect;

    //内容宽度
    private Rect mContentRect;
    private int mContentWidth;
    //前面送礼人宽度
    private float mSenderWidth;
    private float mNumberWidth;
    public GiftInfoElement(IScene iScene, final SceneInfo info,Rect rangeRect) {
        super(iScene);
        setEnableMatrix(false);
        mUserName = CommonUtils.getSubStringByLimit(info.sender, 12);
        final int size=CommonUtils.getDensityFontSize((Activity) iScene.mContext,INFO_TEXT_SIZE);
        //一个真正字体的paint.一个是描边的paint
        realPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        realPaint.setColor(Color.RED);
        realPaint.setTextAlign(Paint.Align.LEFT);
        realPaint.setTextSize(size);
        realPaint.setStyle(Style.FILL_AND_STROKE);
        eagePaint = new Paint(realPaint);
        eagePaint.setStrokeWidth(2); // 描边宽度
        eagePaint.setStyle(Style.FILL_AND_STROKE); //描边种类
        eagePaint.setFakeBoldText(true); // 外层text采用粗体
        eagePaint.setShadowLayer(1, 0, 0, 0); //字体的阴影效果，可以忽略
        eagePaint.setColor(Color.WHITE);
        this.info = info;
        textBounds = new Rect();
        mBGRect =rangeRect;
        fontMetrics = new Paint.FontMetrics();
        realPaint.getTextBounds(mUserName + SONG, 0, mUserName.length(), textBounds);
        realPaint.getFontMetrics(fontMetrics);
        y = (mBGRect.height() + fontMetrics.top - fontMetrics.bottom) / 2 - fontMetrics.top;
        mSenderWidth=realPaint.measureText(mUserName + SONG);
        mContentWidth+=mSenderWidth;
        unitBounds = new Rect();
        realPaint.getTextBounds(UNIT, 0, UNIT.length(), unitBounds);
        ux = realPaint.measureText(UNIT);
        mContentWidth+=ux;

        //礼物bitmap属性
        mGiftBitmapRect=new Rect();
        mGiftBitmap = BitmapUtils.createBitmap(info.getGiftBitmap(),PXUtils.dp2px(mIScene.mContext, GIFT_BITMAP_SZIE),PXUtils.dp2px(mIScene.mContext, GIFT_BITMAP_SZIE));
        mGiftBitmapRect.left=0;
        mGiftBitmapRect.top=(mBGRect.height() - mGiftBitmap.getHeight()) / 2;
        mGiftBitmapRect.right=mGiftBitmap.getWidth();
        mGiftBitmapRect.bottom=mGiftBitmapRect.top+mGiftBitmap.getHeight();
        mContentWidth+=mGiftBitmap.getWidth();
        //初始化10个bitmap数字图片
        bitmapsNumber = new Bitmap[10];
        for (int i = 0; i < 10; i++) {
            bitmapsNumber[i] = BitmapUtils.decodeBitmap(iScene.mContext,
                    iScene.mContext.getResources().getIdentifier("number_" + i, "drawable", iScene.mContext.getPackageName()));
            numberheight = bitmapsNumber[i].getHeight();
            numberWidth = bitmapsNumber[i].getWidth();
        }
//		Logger.d("mgiftbitmap height 3 "+mGiftBitmap.getHeight()+" width "+mGiftBitmap.getWidth());
        numberRect = new RectF();
        mNumberBitmaps = new ArrayList<Bitmap>(5);
        int temp = info.num;
        do {
            mNumberBitmaps.add(bitmapsNumber[temp % 10]);
        }
        while ((temp /= 10) > 0);
        mNumberWidth=numberWidth * mNumberBitmaps.size();
        numberRect.left = 0;
        numberRect.top = (mBGRect.height() - numberheight) / 2;
        numberRect.right = mNumberWidth;
        numberRect.bottom = numberRect.top + numberheight;
        //内容宽度计算
        mContentWidth+=mNumberWidth;
        mContentRect=new Rect(mBGRect);
        mContentRect.left=(mBGRect.width()-mContentWidth)/2+mBGRect.left;
        mContentRect.right=mContentRect.left+mContentWidth;

    }

    private Paint testPaint = new Paint();

    @Override
    protected void draw(Canvas canvas, Matrix matrix, Paint paint, int timeDifference) {
        canvas.save();
        canvas.translate(mContentRect.left, mContentRect.top);
        //首先描绘送礼人信息
        canvas.drawText(mUserName + SONG,0, y, eagePaint);
        canvas.drawText(mUserName + SONG,0, y, realPaint);
        canvas.translate(mSenderWidth, 0);
        //滚动数字
        canvas.save();
        for (int i = mNumberBitmaps.size() - 1, j = 0, size = mNumberBitmaps.size(); i >= 0; i--) {
            canvas.drawBitmap(mNumberBitmaps.get(i), numberRect.left + j++ * numberWidth, numberRect.top, testPaint);
        }
        canvas.restore();
        //单位
        canvas.translate(mNumberWidth, 0);
        canvas.drawText(UNIT, 0, y, eagePaint);
        canvas.drawText(UNIT, 0, y, realPaint);
        //礼物
        canvas.translate(ux, 0);
        canvas.drawBitmap(mGiftBitmap, 0, mGiftBitmapRect.top, null);
        canvas.restore();
    }


    @Override
    protected void destroy() {
        super.destroy();
        BitmapUtils.destroy(mGiftBitmap);
        for(int i=0;i<10;i++){
            BitmapUtils.destroy(bitmapsNumber[i]);
        }
    }
}
