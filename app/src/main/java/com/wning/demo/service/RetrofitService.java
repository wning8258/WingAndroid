package com.wning.demo.service;

import com.wning.demo.service.entity.Game;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by wning on 2018/6/29.
 */

public interface RetrofitService {

    //  @GET("gamelist.do?sign=A27C690D44185D92AE125AC428C35F1CE82180BD&roomid=223981&accesstoken=CBE9458707D491548BFA1E8FA4343ED3&id=107&openid=5FF7711FA6FCAB6E3A4044C04DB7CB6F")
    @GET("system/gamelist.do")
    Observable<Game> getGameList(@QueryMap Map<String, String> params);
}
