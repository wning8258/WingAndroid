package com.wning.demo.service.presenter;

import android.content.Context;

import com.guagua.modules.utils.LogUtils;
import com.wning.demo.service.entity.Game;
import com.wning.demo.service.module.CommonModule;
import com.wning.demo.service.view.GameView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wning on 2018/6/29.
 */

public class GamePresenter extends BasePresenter<GameView>{

    private static final String TAG="GamePresenter";

    private Context context;
    private CommonModule mCommonModule;

    public GamePresenter(){
        mCommonModule= new CommonModule();
    }

    public void getGameList(){
        if(mView!=null){
            mView.showLoading();
        }
        mCommonModule.getGameList(new Observer<Game>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Game game) {

                if(mView!=null){
                    mView.hideLoading();
                    mView.showResult(game);
                }
                LogUtils.i(TAG,"onNext game:"+game);
                LogUtils.i(TAG,"onNext thread :"+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                if(mView!=null){
                    mView.hideLoading();
                }
                LogUtils.i(TAG,"onError :"+e.getMessage());
            }

            @Override
            public void onComplete() {
                if(mView!=null){
                    mView.hideLoading();
                }
                LogUtils.i(TAG,"onComplete");
            }
        });

    }
}
