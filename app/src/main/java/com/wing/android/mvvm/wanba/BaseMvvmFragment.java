package com.wing.android.mvvm.wanba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created on 2020/12/11
 * 适配MVVM的Fragment
 * 支持懒加载 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT方式
 *
 * @author daiqicheng
 */
public abstract class BaseMvvmFragment<V extends ViewDataBinding, VM extends BaseMvvmViewModel> extends AbsFragment {
    private static final String LOADING_DIALOG_TAG = "progress_dialog";
    public static final int LOADING_TIME_OUT = 10000;
    private boolean isFirstLoad = true;

//    protected WBLoadingManager loaderManager;
//
//    private WBLoadingDialog mLoadingDialog;
    protected V binding;
    protected VM viewModel;
    private int viewModelId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面接受的参数方法
        initParam();
        initViewModel();
        if (viewModel != null) {
            viewModel.initParam(savedInstanceState, null, getArguments());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getContentViewId(), container, false);
        setupView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
    }

    @Override
    public void onResume() {
        super.onResume();
        lazyLoad();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    protected void lazyLoad() {
        initData(isFirstLoad);
        isFirstLoad = false;
    }

    public boolean isFirstLoad() {
        return isFirstLoad;
    }


    private void initViewModel() {
        viewModel = createViewModel();
        if (viewModel == null) {
            Class modelClass;
            //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                //ViewModel对应的class对象
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseMvvmViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        viewModelId = initVariableId();
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
        //通过 binding.setLifecycleOwner(this);就可以替代observer的过程
        binding.setLifecycleOwner(this);
    }

    public VM createViewModel() {
        return null;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }

    /**
     * 注册ViewModel与View的契约UI回调事件
     **/
    protected void registorUIChangeLiveDataCallBack() {
        initLoading();
        //加载对话框显示
        viewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showLoadingDialog(LOADING_TIME_OUT, title);
            }
        });
        //加载对话框消失
        viewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                dismissLoadingDialog();
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                getActivity().finish();
            }
        });
        //加载成功
        viewModel.getUC().getLoadingSuccessEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
//                if (loaderManager != null) {
//                    loaderManager.showLoadingPage(WBLoadingManager.TYPE_SUCCESS);
//                }
            }
        });

        //onFailed
        viewModel.getUC().getLoadingFailedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
//                if (loaderManager != null) {
//                    loaderManager.showLoadingPage(WBLoadingManager.TYPE_ERROR);
//                }
            }
        });

        //onException
        viewModel.getUC().getLoadingExceptionEvent().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
//                if (loaderManager != null) {
//                    if (!NetworkUtils.isNetworkAvailable(getContext())) {
//                        if (loaderManager.isSuccessPage()) {
//                            ToastManagerWrapper.showToast(R.string.base_loading_toast_offline);
//                        } else {
//                            loaderManager.showLoadingPage(WBLoadingManager.TYPE_OFFLINE);
//                        }
//                    } else {
//                        //服务器无法连接或超时
//                        loaderManager.showLoadingPage(WBLoadingManager.TYPE_ERROR);
//                        ErrorViewHolder errorViewHolder = loaderManager.getViewHolder(ErrorViewHolder.class);
//                        TextView title = errorViewHolder.findViewById(R.id.desc_title);
//                        title.setText(NetworkUtils.getExceptionText(throwable));
//                    }
//                }
            }
        });
        //显示空态图
        viewModel.getUC().getLoadEmptyEvent().observe(this, new Observer<EmptyStatusBean>() {
            @Override
            public void onChanged(@Nullable EmptyStatusBean emptyStatusBean) {
//                if (emptyStatusBean == null || getEmptyViewManager() == null) {
//                    return;
//                }
                showEmptyView(emptyStatusBean);

            }
        });

        //隐藏空态图
        viewModel.getUC().getHideEmptyEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
//                if (getEmptyViewManager() == null) {
//                    return;
//                }
                hideEmptyView();
            }
        });

        //路由跳转
        viewModel.getUC().getRouterEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String url) {
//                WBRouter.router(getActivity(), url);
            }
        });

        //toast 字符串
        viewModel.getUC().getToastStrEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                ToastManager.showToast(getActivity(),msg);
            }
        });

        //toast id
        viewModel.getUC().getToastIdEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer id) {
                if (id != null) {
                    ToastManager.showToast(getActivity(),id);
                }
            }
        });
    }

    public void showEmptyView(int emptyStatus) {
//        if (getEmptyViewManager() == null) {
//            return;
//        }
//        getEmptyViewManager().showEmpty(emptyStatus);
    }

    public void showEmptyView(EmptyStatusBean emptyStatusBean) {
//        if (emptyStatusBean == null || getEmptyViewManager() == null) {
//            return;
//        }
//        getEmptyViewManager().showEmpty(emptyStatusBean.emptyStatus);
    }

    public void hideEmptyView() {
//        if (getEmptyViewManager() == null) {
//            return;
//        }
//        getEmptyViewManager().hideEmpty();
    }



    public void showLoading() {
//        if (null == mLoadingDialog) {
//            mLoadingDialog = new WBLoadingDialog(getActivity());
//        }
//        mLoadingDialog.show();
    }

    public void dismissLoading() {
//        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
//            mLoadingDialog.dismiss();
//        }
    }

    /**
     * Shows the loading progress dialog
     */
    public void showLoadingDialog(int timeout) {
        if (getActivity() != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(LOADING_DIALOG_TAG);
            if (prev != null) {
                ft.remove(prev);
            }

            // Create and show the dialog
//            DialogFragment newFragment = WBLoadingDialogFragment.newInstance(timeout);
//            newFragment.show(getActivity().getSupportFragmentManager(), LOADING_DIALOG_TAG);
        }
    }

    public void showLoadingDialog(int timeout, String tips) {
        if (getActivity() != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(LOADING_DIALOG_TAG);
            if (prev != null) {
                ft.remove(prev);
            }

            // Create and show the dialog
//            DialogFragment newFragment = WBLoadingDialogFragment.newInstance(timeout, tips);
//            newFragment.show(getActivity().getSupportFragmentManager(), LOADING_DIALOG_TAG);
        }
    }

    /**
     * Dismiss the loading progress dialog
     */
    public void dismissLoadingDialog() {
        if (getActivity() != null) {
            DialogFragment prev = (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag(LOADING_DIALOG_TAG);
            if (prev != null) {
                prev.dismissAllowingStateLoss();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (isPermission()) {
//            WBPermissionsDispatcher.onRequestPermissionsResult(getActivity(), requestCode, grantResults);
        }
    }

    public boolean isPermission() {
        return false;
    }

    /**
     * 接收参数在这里写
     */
    public abstract void initParam();

    protected abstract void initLoading();

    @LayoutRes
    protected abstract int getContentViewId();

    protected abstract void setupView(@NonNull View view);

    protected abstract void initData(boolean firstLoad);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * LiveData设置observable统一放到这里
     */
    public abstract void initViewObservable();
}
