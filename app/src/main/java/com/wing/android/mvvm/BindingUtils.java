package com.wing.android.mvvm;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

public class BindingUtils {

    private static final String TAG = "BindingUtils";

    @BindingAdapter("android:layout_width")
    public static void setLayoutWidth(View view, float width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) width;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View view, float height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) height;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter(value = {"loadUrl"},requireAll = true)
    public static void loadUrl(SimpleDraweeView simpleDraweeView,String url){
        if(!TextUtils.isEmpty(url)) {
            simpleDraweeView.setImageURI(Uri.parse(url));
        }else{
            simpleDraweeView.setImageURI((Uri) null);
        }
    }
    @BindingAdapter("isVisible")
    public static void isVisible(View view, boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    public  interface OnLoadMoreCommand{
        void execute();
    }
    public  interface OnRefreshCommand{
        void execute();
    }


    @BindingAdapter(value = {"onRefreshCommand","onLoadMoreCommand"},requireAll = false)
    public static void onLoadingData(SmartRefreshLayout smartRefreshLayout, final OnRefreshCommand refreshCommand,final OnLoadMoreCommand loadMoreCommand){
       smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
           @Override
           public void onRefresh(@NonNull RefreshLayout refreshLayout) {
               refreshCommand.execute();
           }
       });
        smartRefreshLayout.setOnLoadMoreListener((OnLoadMoreListener) (refreshLayout) -> loadMoreCommand.execute());
    }

//    @BindingAdapter(value = {"refreshEnd"})
//    public static void refreshEnd(SmartRefreshLayout smartRefreshLayout,boolean refreshEnd){
//        LogUtils.i(TAG,"refreshEnd :"+refreshEnd);
//        if(refreshEnd) {
//            smartRefreshLayout.finishRefresh();
//        }
//    }
//    @InverseBindingAdapter(attribute = "refreshEnd")
//    public void refreshEndAttrChanged(InverseBindingListener inverseBindingListener) {
//        if (inverseBindingListener == null) {
//            Log.e(TAG, "InverseBindingListener为空!");
//        } else {
//            Log.d(TAG, "setRefreshingAttrChanged");
//            inverseBindingListener.onChange();
//        }
//
//    }

    @BindingAdapter(value = {"loadMoreEnd","loadMoreSize"})
    public static void loadMoreEnd(SmartRefreshLayout smartRefreshLayout, boolean loadMoreEnd, int loadMoreSize){
        if(loadMoreEnd){
            if(loadMoreSize>0){
                smartRefreshLayout.finishLoadMore();
            }else{
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

}
