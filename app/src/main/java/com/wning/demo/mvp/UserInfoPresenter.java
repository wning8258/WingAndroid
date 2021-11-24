package com.wning.demo.mvp;

import android.os.Handler;
import android.os.Looper;

import com.wning.demo.mvp.biz.UserInfoBiz;
import com.wning.demo.service.presenter.BasePresenter;

/**
 * Created by wning on 16/7/17.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoView> {

    private UserInfoBiz userInfoBiz;

    private Handler mHandler;

    public UserInfoPresenter() {
        mHandler=new Handler(Looper.getMainLooper());

        userInfoBiz=new UserInfoBiz(new UserInfoBiz.UserInfoBizListener() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //TODO  presenter中要时刻关注view是否为空
                        if(mView!=null) {
                            mView.hideLoading();
                            mView.showResult();
                        }
                    }
                });
            }

            @Override
            public void onfail() {

            }
        });
    }

    public void requestUserInfo(){
        mView.showLoading();
        userInfoBiz.requestUserInfo();
    }



}
