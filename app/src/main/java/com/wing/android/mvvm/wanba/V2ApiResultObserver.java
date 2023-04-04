package com.wing.android.mvvm.wanba;

import androidx.lifecycle.Observer;

/**
 * Created by shengchao on 2019-09-18
 * @author shengchao
 * @param <T> javabean
 */
public interface V2ApiResultObserver<T> extends Observer<T> {
    void onFail(int code, String msg, T t);

    void onException(Throwable throwable);

}
