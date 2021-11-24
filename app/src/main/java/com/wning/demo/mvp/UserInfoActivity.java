package com.wning.demo.mvp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wing.android.R;
import com.wning.demo.ui.activity.BaseMVPActivity;
import com.wning.demo.utils.UIUtils;

public class UserInfoActivity extends BaseMVPActivity<UserInfoView,UserInfoPresenter> implements UserInfoView, View.OnClickListener {

    private Button btn;

    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        btn =  findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public UserInfoPresenter initPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                presenter.requestUserInfo();
                break;
        }
    }

    @Override
    public void showResult() {

        Toast.makeText(getApplicationContext(),"finish",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoading() {

       dialog=UIUtils.showAnimLoading(this,false,false);

    }

    @Override
    public void hideLoading() {

        UIUtils.dismissAnimLoading(dialog);
    }
}