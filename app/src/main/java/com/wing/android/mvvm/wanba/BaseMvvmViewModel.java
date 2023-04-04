package com.wing.android.mvvm.wanba;


import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Title: BaseMvvmViewModel<br>
 * Description: <br>
 *
 * @author fxd
 * @Modified by
 * @link fuxiaodan@moqipobing.com
 * @CreateDate 2020/4/6
 * @since JDK 1.8
 */
public class BaseMvvmViewModel<M extends BaseRepository> extends ViewModel implements IBaseViewModel {
    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    public CompositeDisposable mCompositeSubscription;
    protected M repository;
    private UIChangeLiveData uc;
    //RepositoryHelper helper = null;

    public BaseMvvmViewModel() {
        mCompositeSubscription = new CompositeDisposable();
        //helper = RepositoryBuilder.inject(this);
        createRepository();
    }

    public void addSubscribe(Disposable subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }
        mCompositeSubscription.add(subscription);
    }

    /**
     * 参数获取,能用bundle获取的要用bundle获取
     *
     * @param savedInstanceState
     * @param intent             Activity时有，用于获取Action或Uri等Activity专属参数
     * @param bundle             获取基本参数或对象时使用，Activity和Fragment都会有
     */
    public void initParam(Bundle savedInstanceState, Intent intent, Bundle bundle) {

    }

    protected void createRepository() {

    }

    public UIChangeLiveData getUC() {
        if (uc == null) {
            uc = new UIChangeLiveData();
        }
        return uc;
    }

    public void showDialog() {
        showDialog("请稍后...");
    }

    public void showDialog(String title) {
        uc.showDialogEvent.postValue(title);
    }

    public void dismissDialog() {
        uc.dismissDialogEvent.call();
    }

    public void showLoadingSuccess() {
        uc.loadSuccessEvent.postValue(null);
    }

    public void showLoadingFailed() {
        uc.loadFailedEvent.postValue(null);
    }

    public void showLoadingException(Throwable throwable) {
        uc.loadExceptionEvent.postValue(throwable);
    }

    public void showEmptyView(EmptyStatusBean emptyStatusBean) {
        uc.loadEmptyEvent.postValue(emptyStatusBean);
    }

    public void showEmptyView(int emptyStatus) {
        EmptyStatusBean emptyStatusBean = new EmptyStatusBean();
        emptyStatusBean.emptyStatus = emptyStatus;
        uc.loadEmptyEvent.postValue(emptyStatusBean);
    }

    public void hideEmptyView() {
        uc.hideEmptyEvent.postValue(null);
    }

    public void router(String url) {
        getUC().getRouterEvent().setValue(url);
    }

    public void showToast(String msg) {
        getUC().getToastStrEvent().setValue(msg);
    }

    public void showToast(int id) {
        getUC().getToastIdEvent().setValue(id);
    }

    /**
     * 关闭界面
     */
    public void finish() {
        uc.finishEvent.call();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //if (helper != null) {
        //    helper.clear(this);
        //}
        if (repository != null) {
            repository.clear();
        }
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (null != mCompositeSubscription) {
            mCompositeSubscription.dispose();
        }
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }

    public final class UIChangeLiveData extends SingleLiveEvent {
        private SingleLiveEvent<String> showDialogEvent;
        private SingleLiveEvent<Void> dismissDialogEvent;
        private SingleLiveEvent<Void> finishEvent;
        //LoadManager 加载成功
        private SingleLiveEvent<Void> loadSuccessEvent;
        private SingleLiveEvent<Void> loadFailedEvent;
        private SingleLiveEvent<Throwable> loadExceptionEvent;
        private SingleLiveEvent<EmptyStatusBean> loadEmptyEvent;
        private SingleLiveEvent<Void> hideEmptyEvent;
        private SingleLiveEvent<String> routerEvent;
        private SingleLiveEvent<String> toastStrEvent;
        private SingleLiveEvent<Integer> toastIdEvent;


        public SingleLiveEvent<String> getShowDialogEvent() {
            showDialogEvent = createLiveData(showDialogEvent);
            return showDialogEvent;
        }

        public SingleLiveEvent<Void> getDismissDialogEvent() {
            dismissDialogEvent = createLiveData(dismissDialogEvent);
            return dismissDialogEvent;
        }

        public SingleLiveEvent<Void> getFinishEvent() {
            finishEvent = createLiveData(finishEvent);
            return finishEvent;
        }

        public SingleLiveEvent<Void> getLoadingSuccessEvent() {
            loadSuccessEvent = createLiveData(loadSuccessEvent);
            return loadSuccessEvent;
        }

        public SingleLiveEvent<Void> getLoadingFailedEvent() {
            loadFailedEvent = createLiveData(loadFailedEvent);
            return loadFailedEvent;
        }


        public SingleLiveEvent<Throwable> getLoadingExceptionEvent() {
            loadExceptionEvent = createLiveData(loadExceptionEvent);
            return loadExceptionEvent;
        }

        public SingleLiveEvent<EmptyStatusBean> getLoadEmptyEvent() {
            loadEmptyEvent = createLiveData(loadEmptyEvent);
            return loadEmptyEvent;
        }

        public SingleLiveEvent<Void> getHideEmptyEvent() {
            hideEmptyEvent = createLiveData(hideEmptyEvent);
            return hideEmptyEvent;
        }

        public SingleLiveEvent<String> getRouterEvent() {
            routerEvent = createLiveData(routerEvent);
            return routerEvent;
        }

        public SingleLiveEvent<String> getToastStrEvent() {
            toastStrEvent = createLiveData(toastStrEvent);
            return toastStrEvent;
        }

        public SingleLiveEvent<Integer> getToastIdEvent() {
            toastIdEvent = createLiveData(toastIdEvent);
            return toastIdEvent;
        }

        private <T> SingleLiveEvent<T> createLiveData(SingleLiveEvent<T> liveData) {
            if (liveData == null) {
                liveData = new SingleLiveEvent<>();
            }
            return liveData;
        }

        @Override
        public void observe(LifecycleOwner owner, Observer observer) {
            super.observe(owner, observer);
        }
    }

}
