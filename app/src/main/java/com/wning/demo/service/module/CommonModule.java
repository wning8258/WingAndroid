package com.wning.demo.service.module;

import com.guagua.qiqi.utils.SignHelper;
import com.wning.demo.service.RetrofitHelper;
import com.wning.demo.service.RetrofitService;
import com.wning.demo.service.entity.Game;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wning on 2018/6/29.
 */

public class CommonModule {

    private static final RetrofitService mService= RetrofitHelper.getInstance().getServer();

    public CommonModule(){

    }

    public void getGameList(Observer<Game> observer){
        //?sign=A27C690D44185D92AE125AC428C35F1CE82180BD&roomid=223981&accesstoken=CBE9458707D491548BFA1E8FA4343ED3&openid=5FF7711FA6FCAB6E3A4044C04DB7CB6F
        HashMap<String,String > params=new HashMap<>();
        params.put("accesstoken","CBE9458707D491548BFA1E8FA4343ED3");
        params.put("openid","5FF7711FA6FCAB6E3A4044C04DB7CB6F");
        params.put("roomid","223981");
        params.put("id","107");
        SignHelper.addSign(params);

        mService.getGameList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
