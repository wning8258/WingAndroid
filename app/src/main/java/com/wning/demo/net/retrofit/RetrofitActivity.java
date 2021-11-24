package com.wning.demo.net.retrofit;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wing.android.R;
import com.wning.demo.service.entity.Game;
import com.wning.demo.service.presenter.GamePresenter;
import com.wning.demo.service.view.GameView;
import com.wning.demo.ui.activity.BaseMVPActivity;
import com.wning.demo.utils.UIUtils;

public class RetrofitActivity extends BaseMVPActivity<GameView,GamePresenter> implements GameView,View.OnClickListener {

    private static final String TAG="RetrofitActivity";

    TextView tv_start;

    TextView tv_result;


    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_retrofit);

        tv_start= (TextView) findViewById(R.id.tv_start);
        tv_result= (TextView) findViewById(R.id.tv_result);
        tv_start.setOnClickListener(this);
    }

    @Override
    public GamePresenter initPresenter() {
        return new GamePresenter();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_start:
                presenter.getGameList();
                break;

        }
    }
    @Override
    public void showLoading() {

        dialog= UIUtils.showAnimLoading(this,false,false);

    }

    @Override
    public void hideLoading() {

        UIUtils.dismissAnimLoading(dialog);
    }
    @Override
    public void showResult(Game game) {
        tv_result.setText(game.toString());
    }
}
