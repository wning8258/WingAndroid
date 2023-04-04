package com.wing.android.mvvm.wanba;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


/**
 * Created by shengchao on 2019-09-18
 */
public class V2ApiResultLiveData<T> extends MutableLiveData<T> {
    private V2ApiResultObserver observer;

    @Override
    protected void onActive() {
        super.onActive();
        //WBLogger.d("onActive");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        //WBLogger.d("onInactive");
    }

    @Override
    public void removeObserver(@NonNull Observer<? super T> observer) {
        super.removeObserver(observer);
        this.observer = null;
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
        super.observe(owner, observer);
        if (observer instanceof V2ApiResultObserver) {
            this.observer = (V2ApiResultObserver) observer;
        } else {
            throw new IllegalArgumentException("you must be use V2ApiResultObserver");
        }

    }

    public V2ApiResultObserver getObserver() {
        return observer;
    }

    public void onCleared() {
        //WBLogger.d("onCleared");
        observer = null;
    }
}
