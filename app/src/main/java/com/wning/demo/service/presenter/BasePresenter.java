package com.wning.demo.service.presenter;


import com.wning.demo.service.view.BaseView;

/**
 * Created by wning on 16/7/17.
 */

public abstract class BasePresenter<T extends BaseView>{

    public T mView;

    public void attach(T mView){
        this.mView = mView;
    }

    public void detach(){
        mView = null;
    }
}
