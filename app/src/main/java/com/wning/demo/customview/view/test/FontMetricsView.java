package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;

/*************************************************************************************
 * Module Name:
 * File Name: PathView.java
 * Description:
 * Author: 王宁
 * 版权 2008-2016，浙江齐聚科技有限公司
 * 所有版权保护
 * 这是浙江齐聚科技有限公司未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司
 * 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也,不允许任何形式的私自备份。
 *************************************************************************************/
public class FontMetricsView extends BaseCustomView {

    private Paint mPaint;
    private TextPaint textPaint ;

    public FontMetricsView(Context context) {
        super(context);
        init();
    }

    public FontMetricsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(30);

        textPaint = new TextPaint();
        textPaint.setTextSize(30);
        textPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float ascent = fontMetrics.ascent;
        float descent = fontMetrics.descent;
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;


        String str="ascent : "+ascent+",descent : "+descent+",top : "+top+",bottom : "+bottom;

        canvas.drawText(str,0,180,mPaint);
        float width = mPaint.measureText(str);  //获取文本的宽度

        Rect boundRect=new Rect();
        mPaint.getTextBounds(str,0,str.length(),boundRect);

        //textbounds永远比measureText返回的值小一点点

        canvas.drawText("measureText : "+width+",getTextBounds : "+boundRect.width(),0,280,mPaint);

  //      StaticLayout sl = new StaticLayout(str,textPaint,getWidth(), Layout.Alignment.ALIGN_NORMAL,1.0f,0.0f,true);
//        sl.draw(canvas);
    }

    @Override
    protected String setInfo() {
        return "FontMetrics getTextBounds measureText";
    }
}
