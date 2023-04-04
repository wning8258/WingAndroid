package com.wing.android.mvvm.wanba;

import androidx.collection.ArrayMap;
import androidx.lifecycle.ViewModel;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by shengchao on 2019-10-09
 */
public class WBViewModel extends ViewModel {
    private ArrayMap<Class, V2ApiResultLiveData> arrayMap;
    public CompositeDisposable mCompositeSubscription;

    public WBViewModel() {
        arrayMap = new ArrayMap<>();
        mCompositeSubscription = new CompositeDisposable();
    }

    /**
     * 获取指定livedata
     * @param cls
     * @param <T>
     * @return
     */
    public synchronized <T> V2ApiResultLiveData<T> getLiveData(Class<T> cls) {
        if (arrayMap.containsKey(cls)) {
            return arrayMap.get(cls);
        } else {
            V2ApiResultLiveData<T> arg = new V2ApiResultLiveData<T>();
            arrayMap.put(cls, arg);
            return arg;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (null != mCompositeSubscription) {
            mCompositeSubscription.dispose();
        }
        if (null != arrayMap && arrayMap.size() > 0) {
            for (Map.Entry<Class, V2ApiResultLiveData> entry : arrayMap.entrySet()) {
                entry.getValue().onCleared();
            }
        }
    }
}
