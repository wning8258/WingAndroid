package com.wing.android.mvvm.wanba;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2020/9/9
 * 数据层基类，数据加载封装
 *
 * @author daiqicheng
 */
public class BaseRepository {

    public CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    public void addSubscribe(Disposable subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }
        mCompositeSubscription.add(subscription);
    }

    public void clear() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }
}
