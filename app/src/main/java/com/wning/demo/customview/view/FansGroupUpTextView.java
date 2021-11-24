package com.wning.demo.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.guagua.modules.utils.Utils;
import com.wing.android.R;

/**
 * Created by wning on 2016/11/23.
 */

public class FansGroupUpTextView extends View {
    
    private Context mContext;

    int mWidth,mHeight;

    private Paint infoPaint,strokeInfoPaint;
    private Paint namePaint,strokeNamePaint;
    private Paint levelPaint,strokeLevelPaint;


    private Rect rect;

    private  Paint.FontMetricsInt fontMetrics;


    private int normalTextFont;
    private int selectTextFont;

    private int strokeWidth;


    private String infoPrefix="恭喜您,";
    private String info=" 粉丝等级升到";
    private String infoSuffix=" !";
    private String infoCan=" 您可以：";

    private String name;
    private String level;
    private int bmpRes;

    private Bitmap bitmap;

    private int infoPrefixLength,nameLength,infoLength,infoSuffixLength,levelLength,nameHeight, infoCanHeight;

    private int startX,startY;

    private int heightOffest;



    
    public FansGroupUpTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }

    private void init() {

        rect = new Rect();

        normalTextFont=Utils.dip2px(mContext,13);
        selectTextFont=Utils.dip2px(mContext,15);

        strokeWidth=Utils.dip2px(mContext,1);
        heightOffest=Utils.dip2px(mContext,10);

        infoPaint =new Paint();
        infoPaint.setAntiAlias(true);
        infoPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        infoPaint.setTextSize(normalTextFont);
        infoPaint.setColor(Color.WHITE);


        strokeInfoPaint =new Paint();
        strokeInfoPaint.setAntiAlias(true);
        strokeInfoPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        strokeInfoPaint.setTextSize(normalTextFont);
        strokeInfoPaint.setStrokeWidth(strokeWidth);
        strokeInfoPaint.setFakeBoldText(true); // 外层text采用粗体
        strokeInfoPaint.setShadowLayer(1, 0, 0, 0); // 字体的阴影效果，可以忽略
        strokeInfoPaint.setColor(getResources().getColor(R.color.qiq_room_fansgroup_toast_bg_stroke));

        namePaint=new Paint();
        namePaint.set(infoPaint);
        namePaint.setTextSize(selectTextFont);
        namePaint.setColor(getResources().getColor(R.color.qiq_room_fansgroup_toast_name_fill));

        strokeNamePaint=new Paint();
        strokeNamePaint.set(strokeInfoPaint);
        strokeNamePaint.setTextSize(selectTextFont);
        strokeNamePaint.setColor(getResources().getColor(R.color.qiq_room_fansgroup_toast_name_stroke));


        levelPaint=new Paint();
        levelPaint.set(infoPaint);
        levelPaint.setTextSize(selectTextFont);

        strokeLevelPaint=new Paint();
        strokeLevelPaint.set(strokeInfoPaint);
        strokeLevelPaint.setTextSize(selectTextFont);


        fontMetrics = strokeInfoPaint.getFontMetricsInt();
        strokeInfoPaint.getTextBounds(infoPrefix, 0, infoPrefix.length(), rect);
        infoPrefixLength=rect.width();

        strokeInfoPaint.getTextBounds(info, 0, info.length(), rect);
        infoLength=rect.width();

        strokeInfoPaint.getTextBounds(infoSuffix, 0, infoSuffix.length(), rect);
        infoSuffixLength=rect.width();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(TextUtils.isEmpty(name)){
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            return;
        }

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);



        int width = nameLength+infoLength+infoPrefixLength+infoSuffixLength+levelLength;
        int height;
        if(bmpRes>0) {
            height = nameHeight*2+heightOffest;
        }else{
            height = nameHeight ;
        }

        if(mode==MeasureSpec.EXACTLY){
            mWidth=size;
        }else{
            int totoalWidth = getPaddingLeft() + getPaddingRight()
                    + width;

            if (mode == MeasureSpec.AT_MOST)// wrap_content
            {
                mWidth = Math.min(totoalWidth, size);
            } else {
                mWidth = totoalWidth;
            }
        }

         mode = MeasureSpec.getMode(heightMeasureSpec);
         size = MeasureSpec.getSize(heightMeasureSpec);

        if(mode==MeasureSpec.EXACTLY){
            mHeight=size;
        }else{
            int totalHeight = getPaddingTop() + getPaddingBottom()
                    + height;

            if (mode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(totalHeight, size);
            } else {
                mHeight = totalHeight;
            }
        }

            setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(TextUtils.isEmpty(name)){
            return;
        }

        fontMetrics = infoPaint.getFontMetricsInt();
        infoPaint.getTextBounds(infoPrefix, 0, infoPrefix.length(), rect);
        startY =(mHeight -infoCanHeight- fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        startX=(mWidth-infoPrefixLength-nameLength-infoLength-infoSuffixLength-levelLength)/2;
        canvas.drawText(infoPrefix,startX, startY, strokeInfoPaint);  //恭喜您，
        canvas.drawText(infoPrefix,startX, startY, infoPaint);


        canvas.drawText(name,startX+infoPrefixLength, startY, strokeNamePaint);   //粉丝团名字
        canvas.drawText(name,startX+infoPrefixLength, startY, namePaint);

        canvas.drawText(info,startX+infoPrefixLength+nameLength, startY, strokeInfoPaint);//粉丝等级升到
        canvas.drawText(info,startX+infoPrefixLength+nameLength, startY, infoPaint);

        canvas.drawText(level,startX+infoPrefixLength+nameLength+infoLength, startY, strokeLevelPaint);//LV2
        canvas.drawText(level,startX+infoPrefixLength+nameLength+infoLength, startY, levelPaint);

        canvas.drawText(infoSuffix,startX+infoPrefixLength+nameLength+infoLength+levelLength, startY, strokeInfoPaint);//!
        canvas.drawText(infoSuffix,startX+infoPrefixLength+nameLength+infoLength+levelLength, startY, infoPaint);

        if(bmpRes>0) {
            canvas.drawText(infoCan, startX, startY + infoCanHeight+heightOffest, strokeInfoPaint);  //您可以
            canvas.drawText(infoCan, startX, startY + infoCanHeight+heightOffest, infoPaint);

            if(bitmap!=null) {
                canvas.drawBitmap(bitmap,(mWidth-bitmap.getWidth())/2, startY + infoCanHeight + heightOffest+fontMetrics.top, null);
            }
        }
    }

    public void setText(String name, String level, int bmpRes) {
        this.name=name;
        this.level=level;
        this.bmpRes=bmpRes;


        fontMetrics = strokeNamePaint.getFontMetricsInt();
        strokeNamePaint.getTextBounds(name, 0, name.length(), rect);
        nameLength=rect.width();
        nameHeight=rect.height();   //最大高度


        fontMetrics = strokeLevelPaint.getFontMetricsInt();
        strokeLevelPaint.getTextBounds(level, 0, level.length(), rect);
        levelLength=rect.width();

        if(bmpRes>0) {
            fontMetrics = strokeInfoPaint.getFontMetricsInt();
            strokeInfoPaint.getTextBounds(infoCan, 0, infoCan.length(), rect);
            infoCanHeight = rect.height();

            bitmap= BitmapFactory.decodeResource(getResources(),bmpRes);
        }else{
            infoCanHeight =0;
        }


        invalidate();
    }
}
