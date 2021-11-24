package com.guagua.qiqi.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/6/17.
 */
public class QiQiImageView extends SimpleDraweeView {

    private static final String TAG="QiQiImageView";

    public QiQiImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public QiQiImageView(Context context) {
        super(context);
    }

    public QiQiImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 加载网络图片
     * @param uri
     */
    public void setUrl(Uri uri) {
        setImageURI(uri);
    }

    /**
     * 取消加载图片
     */
    public void cancel(){
        setController(null);
    }


  //  public Drawable getCurrentDrawable() {
  //    return ImageUtils.getDrawable(this);
   // }

    public void setCurrentDrawable(Drawable drawable) {
        if(drawable==null)
            return;
        getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        getHierarchy().setImage(drawable,1f,true);
    }
}
