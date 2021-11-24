package com.wning.demo.customview.view;

import android.content.Context;
import android.graphics.Point;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.guagua.modules.utils.LogUtils;

/**
 * Created by wning on 2018/3/9.
 */

public class ViewDragHelperLayout extends LinearLayout{

    private static final String TAG="ViewDragHelperLayout";

    private ViewDragHelper mDragHelper;

    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;

    private Point mAutoBackOriginPos = new Point();  //保存mAutoBackView的起始坐标

    public ViewDragHelperLayout(Context context) {
        super(context);
        init();
    }

    public ViewDragHelperLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackOriginPos.x=mAutoBackView.getLeft();
        mAutoBackOriginPos.y=mAutoBackView.getTop();
    }

    @Override
    public void computeScroll() {  //为了让settleCapturedViewAt滑动
        if(mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private void init() {
        //敏感度  helper.mTouchSlop = (int) (helper.mTouchSlop * (1 / sensitivity));
        mDragHelper =ViewDragHelper.create(this, 1f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
              //  return true;
                return child==mDragView||child==mAutoBackView;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mDragHelper.captureChildView(mEdgeTrackerView,pointerId);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //left：x坐标的位置，dx：每次移动了多少
                LogUtils.i(TAG,"left :"+left +",dx :"+dx);
                int leftBound=getPaddingLeft();
                int rightBuound=getWidth()-getPaddingRight()-leftBound-child.getWidth();
                int newLeft=Math.min(Math.max(left,leftBound),rightBuound);//只限在父布局内部移动
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                LogUtils.i(TAG,"top :"+top +",dy :"+dy);
                int topBound=getPaddingTop();
                int bottomBound=getHeight()-getPaddingBottom()-topBound-child.getHeight();
                int newTop=Math.min(Math.max(top,topBound),bottomBound);//只限在父布局内部移动
                return newTop;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {  //手指释放时回调
                if(releasedChild==mAutoBackView){
                    mDragHelper.settleCapturedViewAt(mAutoBackOriginPos.x,mAutoBackOriginPos.y);
                    invalidate();
                }
            }

            @Override
            public int getViewHorizontalDragRange(View child)
            {
                return getMeasuredWidth()-child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child)
            {
                return getMeasuredHeight()-child.getMeasuredHeight();
            }
        });
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
         mDragHelper.processTouchEvent(event);
         return true;
    }
}
