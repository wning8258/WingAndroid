package com.wing.android.mvvm.wanba;

import static java.lang.Integer.MAX_VALUE;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.wning.demo.BaseActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * 一个拥有DataBinding框架的基Activity
 * 这里根据项目业务可以换成你自己熟悉的BaseActivity, 但是需要继承RxAppCompatActivity,方便LifecycleProvider管理生命周期
 */
public abstract class BaseMvvmActivity<V extends ViewDataBinding, VM extends BaseMvvmViewModel> extends BaseActivity implements IBaseView {
    public static final int LOADING_MAX_TIME_OUT = MAX_VALUE;
    private static final String LOADING_DIALOG_TAG = "progress_dialog";
    protected V binding;
    protected VM viewModel;
//    protected WBLoadingManager loaderManager;
    private int viewModelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        //页面接受的参数方法
        initParam();
        if (viewModel != null) {
            viewModel.initParam(savedInstanceState, getIntent(), getIntent() != null ? getIntent().getExtras() : null);
        }
        initUI();
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //页面数据初始化方法
        initData();
        //皮肤注册
        initSkin();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding.unbind();
        }
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        viewModelId = initVariableId();
        viewModel = initViewModel();
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
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
        //通过 binding.setLifecycleOwner(this);就可以替代observer的过程
        binding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }


    /**
     * 注册ViewModel与View的契约UI回调事件
     **/
    protected void registorUIChangeLiveDataCallBack() {
//        loaderManager = initLoading();
        //加载对话框显示
        viewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showDialog(title);
            }
        });
        //加载对话框消失
        viewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
//                dismissLoadingDialog();
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                finish();
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
//                    if (!NetworkUtils.isNetworkAvailable(BaseMvvmActivity.this)) {
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
//                if (emptyStatusBean == null || getEmptyManager() == null) {
//                    return;
//                }
                showEmptyView(emptyStatusBean);

            }
        });

        //隐藏空态图
        viewModel.getUC().getHideEmptyEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
//                if (getEmptyManager() == null) {
//                    return;
//                }
                hideEmptyView();
            }
        });

        //路由跳转
        viewModel.getUC().getRouterEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String url) {
//                WBRouter.router(BaseMvvmActivity.this, url);
            }
        });

        //toast 字符串
        viewModel.getUC().getToastStrEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                ToastManager.showToast(getApplicationContext(),msg);
            }
        });

        //toast id
        viewModel.getUC().getToastIdEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer id) {
                if (id != null) {
                    ToastManager.showToast(getApplicationContext(),id);
                }
            }
        });
    }


    @Override
    public void initParam() {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

    public void showDialog(String title) {
//        showLoadingDialog(LOADING_MAX_TIME_OUT, title);
    }


    /**
     * 初始化loading
     *
     * @return
     */
//    public abstract WBLoadingManager initLoading();

    public void showLoadingSuccess() {
//        if (loaderManager != null) {
//            loaderManager.showLoadingPage(WBLoadingManager.TYPE_SUCCESS);
//        }
    }

    public void showEmptyView(int emptyStatus) {
//        if (getEmptyManager() == null) {
//            return;
//        }
//        getEmptyManager().showEmpty(emptyStatus);
    }

    public void showEmptyView(EmptyStatusBean emptyStatusBean) {
//        if (emptyStatusBean == null || getEmptyManager() == null) {
//            return;
//        }
//        getEmptyManager().showEmpty(emptyStatusBean.emptyStatus);
    }

    public void hideEmptyView() {
//        if (getEmptyManager() == null) {
//            return;
//        }
//        getEmptyManager().hideEmpty();
    }


}
