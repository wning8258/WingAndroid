package com.guagua.qiqi.gifteffect.elements;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Message;

import com.guagua.qiqi.gifteffect.animation.algorithm.TimeAlpha;
import com.guagua.qiqi.gifteffect.util.CommonUtils;

/*************************************************************************************
 * Module Name: GiftInfoElement</br> File Name: <b>GiftInfoElement.java</b></br> Description: </br> Author: 郁金涛</br> 版权
 * 2008-2015，金华长风信息技术有限公司</br> 所有版权保护 这是金华长风信息技术有限公司未公开的私有源代码, 本文件及相关内容未经金华长风信息技术有限公司 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
 ***************************************************************************************/
public class TextElement extends Element {

	public static final int INFO_TEXT_SIZE = 32;
    private String message = "开通真爱守护一个月";
    private String name = "大明哥 ";
    private Paint realPaint;
    private Paint eagePaint;
    private Paint namePaint;
    private Rect mContainerRect;
    private Rect mEageBoundRect;
    private float mEageTextWidth;
    private float mEageTextHeight;
    private Rect mRealBoundRect;
    private float mRealTextWidth;
    private float mRealTextHeight;
    private Rect mNameBoundRect;
    private float mNameTextWidth;
    private float mNameTextHeight;
    private TimeAlpha timeAlpha;

    public TextElement(IScene iScene, Rect rangeRect, String message, String name) {
        super(iScene);
        this.message = message;
        this.name = name;
        mContainerRect = rangeRect;
        setEnableMatrix(false);
        final int size = CommonUtils.getDensityFontSize((Activity) iScene.mContext, INFO_TEXT_SIZE);
        namePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        namePaint.setColor(0xffbc141c);
        namePaint.setTextAlign(Paint.Align.LEFT);
        namePaint.setTextSize(size);
        namePaint.setStyle(Style.FILL_AND_STROKE);
        namePaint.setStrokeWidth(0.5f);
        namePaint.setTypeface(Typeface.DEFAULT_BOLD);
		
        // 一个真正字体的paint.一个是描边的paint
        realPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        realPaint.setColor(0xff3d3d3d);
        realPaint.setTextAlign(Paint.Align.LEFT);
        realPaint.setTextSize(size);
        realPaint.setStyle(Style.FILL_AND_STROKE);
//        realPaint.setStrokeWidth(0.5f);
//		realPaint.setTypeface(Typeface.DEFAULT_BOLD);
        eagePaint = new Paint(realPaint);
        eagePaint.setStrokeWidth(2); // 描边宽度
        eagePaint.setStyle(Style.FILL_AND_STROKE); // 描边种类
        eagePaint.setFakeBoldText(true); // 外层text采用粗体
        eagePaint.setShadowLayer(1, 0, 0, 0); // 字体的阴影效果，可以忽略
        eagePaint.setColor(Color.WHITE);

        mEageBoundRect = new Rect();
        eagePaint.getTextBounds(message, 0, message.length(), mEageBoundRect);
        mEageTextWidth = mEageBoundRect.width();
        mEageTextHeight = mEageBoundRect.height();
        
        mNameBoundRect = new Rect();
        namePaint.getTextBounds(name, 0, name.length(), mNameBoundRect);
        mNameTextWidth = mNameBoundRect.width();
        mNameTextHeight = mNameBoundRect.height();

        mRealBoundRect = new Rect();
        realPaint.getTextBounds(message, 0, message.length(), mRealBoundRect);
        mRealTextWidth = mRealBoundRect.width();
        mRealTextHeight = mRealBoundRect.height();
        timeAlpha = TimeAlpha.build(0, 3000);
    }

    @Override
    protected void draw(Canvas canvas, Matrix matrix, Paint paint, int timeDifference) {
        canvas.save();
        eagePaint.setAlpha((int)timeAlpha.caculate(timeDifference));
        realPaint.setAlpha((int)timeAlpha.caculate(timeDifference));
        canvas.drawText(name, mContainerRect.left + mContainerRect.width() / 2 - (mEageTextWidth + mNameTextWidth) / 2, mContainerRect.top + mContainerRect.height() / 2
                + mNameTextHeight / 2 - 5, namePaint);
        canvas.drawText(message, mContainerRect.left + mContainerRect.width() / 2 - (mEageTextWidth - mNameTextWidth) / 2, mContainerRect.top + mContainerRect.height() / 2
                + mEageTextHeight / 2 - 5, eagePaint);
        canvas.drawText(message, mContainerRect.left + mContainerRect.width() / 2 - (mEageTextWidth - mNameTextWidth) / 2, mContainerRect.top + mContainerRect.height() / 2
                + mRealTextHeight / 2 - 5, realPaint);
        canvas.restore();
    }

    @Override
    protected void destroy() { 
        super.destroy();
    }
}
