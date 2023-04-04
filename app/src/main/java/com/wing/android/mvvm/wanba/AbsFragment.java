package com.wing.android.mvvm.wanba;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import io.reactivex.disposables.CompositeDisposable;

//import javax.inject.Inject;

/**
 * 原来的 BaseFragment 类 非业务代码
 * Created by wanba on 2016/4/28.
 */
public class AbsFragment extends Fragment  {
    private static final String TAG = "AbstractBaseFragment";

    protected CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    protected Gson gson;
    //是否在onStart注册rxbus
    protected boolean mRxbusAutoRegist = true;
//    protected EmptyViewManager mEmptyViewManager;

    private boolean isVisibleToUser;

    private long setTureTime;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        FragmentLifeCycleInjectManager.getInstance().registerPageLifeCycleCallbacks(this);
//        gson = WBGson.getInstance();
        initEmptyViewManager();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (view instanceof ViewGroup && null != mEmptyViewManager) {
//            mEmptyViewManager.bindGroupView((ViewGroup) view);
//        }
//
//        if (BuildConfig.DEBUG) {
//            DebugView.addView(this, view);
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (mRxbusAutoRegist) {
//            RxBus.get().register(this);
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (mRxbusAutoRegist) {
//            RxBus.get().unregister(this);
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.dispose();
        //        FragmentLifeCycleInjectManager.getInstance().unregisterPageLifeCycleCallbacks(this);
//        mEmptyViewManager = null;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
    }

    public boolean getVisibleToUser() {
        return this.isVisibleToUser;
    }

    public long getSetTureTime() {
        return setTureTime;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    /**
     * 显示输入法
     */
    protected void showInputMethod() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showToast(int resId) {
        ToastManager.showToast(getContext(), resId);
    }

    protected void showToast(String message) {
        ToastManager.showToast(getContext(), message);
    }

//    @Override
//    public boolean onBackPressed() {
//        return FragmentBackUtil.handleBackPress(this);
//    }

    private void initEmptyViewManager() {
//        mEmptyViewManager = EmptyViewManager.newInstance(getContext());
    }

//    public EmptyViewManager getEmptyViewManager() {
//        return mEmptyViewManager;
//    }

//    public void releaseEmptyViewManager() {
//        mEmptyViewManager = null;
//    }

    protected void loadImage(String url, ImageView imageView) {
        Glide.with(this)
                .load(url)
                .into(imageView);
    }


}
