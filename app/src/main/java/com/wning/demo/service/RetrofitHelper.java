package com.wning.demo.service;

import com.guagua.qiqi.utils.QiQiHttpConfig;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wning on 2018/6/29.
 */

public class RetrofitHelper {

    private Retrofit mRetrofit;

    public static RetrofitHelper getInstance(){
        return Holder.sInstance;
    }
    private static class Holder{
        private static final RetrofitHelper sInstance=new RetrofitHelper();
    }

    private RetrofitHelper(){
        createRetrofit();
    }

    public RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }

    private void createRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://hall.m.qxiu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(genericClient())
                .build();
    }

    /**
     * 设置请求统一的header
     * @return
     */
    private OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        for(Map.Entry<String,String> entry : QiQiHttpConfig.getHeader().entrySet()){
                            builder.addHeader(entry.getKey(),entry.getValue());
                        }
                        Request request = builder.build();
                        return chain.proceed(request);
                    }
                })
                .build();
        return httpClient;
    }


}
