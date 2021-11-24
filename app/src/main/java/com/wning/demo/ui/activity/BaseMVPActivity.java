package com.wning.demo.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.wning.demo.service.presenter.BasePresenter;
import com.wning.demo.service.view.BaseView;


/**
 * Created by wning on 16/7/17.
 */

public abstract class BaseMVPActivity<V extends BaseView,T extends BasePresenter<V>> extends AppCompatActivity {


    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        presenter.attach((V)this);
    }


    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    public abstract T initPresenter();
}
