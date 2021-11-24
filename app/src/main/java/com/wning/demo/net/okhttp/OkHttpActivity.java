package com.wning.demo.net.okhttp;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.guagua.modules.utils.LogUtils;
import com.guagua.qiqi.utils.QiQiHttpConfig;
import com.guagua.qiqi.utils.SignHelper;
import com.guagua.qiqi.utils.UrlUtil;
import com.wning.demo.BaseActivity;
import com.wing.android.R;
import com.wing.android.databinding.ActivityOkhttpBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends BaseActivity<ActivityOkhttpBinding> implements View.OnClickListener {

    private static final String TAG = "OkHttpActivity";

    Button btn1;

    Button btn2;

    Button btn3;

    Button btn4;

    private OkHttpClient mClient;
    private Headers mHeaders;

    private String finalUrl;

    private String fileUrl = "http://wning8258.com/test/test.xlsx";

    CompositeDisposable compositeDisposable = new CompositeDisposable();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btn1=findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3=findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4=findViewById(R.id.btn4);
        btn4.setOnClickListener(this);

//        mClient= new OkHttpClient.Builder()
//                .build();
        mClient = genericClient();
//        makeHeader();
        String url = "http://hall.m.qxiu.com/system/gamelist.do?sign=A27C690D44185D92AE125AC428C35F1CE82180BD&roomid=223981&accesstoken=CBE9458707D491548BFA1E8FA4343ED3&id=107&openid=5FF7711FA6FCAB6E3A4044C04DB7CB6F";
        HashMap params = new HashMap();
        finalUrl = makeFinalUrl(url, params);
    }
    /**
     * 设置请求统一的header
     *
     * @return
     */
    public static OkHttpClient genericClient() {

        OkHttpClient client1=new OkHttpClient();


//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                //.cache()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request.Builder builder = chain.request().newBuilder();
////                        for (Map.Entry<String, String> entry : QiQiHttpConfig.getHeader().entrySet()) {
////                            builder.addHeader(entry.getKey(), entry.getValue());
////                        }
//                        Request request = builder.headers(Headers.of(QiQiHttpConfig.getHeader())).build();
//                        return chain.proceed(request);
//                    }
//                })
//                .build();

        return client1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void makeHeader() {
        Headers.Builder builder = new Headers.Builder();
        for (Map.Entry<String, String> entry : QiQiHttpConfig.getHeader().entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        mHeaders = builder.build();
    }

    private String makeFinalUrl(String url, HashMap params) {
        SignHelper.makePaySign(params);
        String finalUrl = UrlUtil.makeGetUrl(url, params);
        return finalUrl;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:  //get异步请求enqueue
                // Request request=new Request.Builder().headers(mHeaders).url(finalUrl).build();  //这个可以给某个请求设置header
                Request request = new Request.Builder().url(finalUrl).build();
                mClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.i(TAG, "onFailure 1" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtils.i(TAG, "onResponse 1" + response.body().string());
                    }
                });
                break;
            case R.id.btn2:  //get阻塞请求execute,使用rxjava2
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        Request request = new Request.Builder().url(finalUrl).build();
                        try {
                            Response response = mClient.newCall(request).execute();
                            String result = response.body().string();
                            LogUtils.i(TAG, "onResponse " + result);
                            LogUtils.i(TAG,"onResponse Thread :"+Thread.currentThread().getName());
                            emitter.onNext(result);
                            emitter.onComplete();
                        } catch (IOException e) {
                            emitter.onError(e);
                            e.printStackTrace();
                        }
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())  //指定observer所在的线程，包括map,flatmap等
                        .map(new Function<String, GameInfo>() {
                            @Override
                            public GameInfo apply(String s) throws Exception {

                                LogUtils.i(TAG,"map Thread :"+Thread.currentThread().getName());
                                GameInfo gameInfo = new Gson().fromJson(s, GameInfo.class);
                                return gameInfo;
                            }
                        })

                       .subscribe(new Observer<GameInfo>() {

                           private Disposable mDisposable;
                           @Override
                           public void onSubscribe(Disposable d) {
                               LogUtils.i(TAG,"onSubscribe :");
                               mDisposable=d;
                           }

                           @Override
                           public void onNext(GameInfo s) {
                               LogUtils.i(TAG,"onNext gameinfo:"+s);
                               LogUtils.i(TAG,"onNext Thread :"+Thread.currentThread().getName());
                           }

                           @Override
                           public void onError(Throwable e) {
                               LogUtils.i(TAG,"onError :"+e.getLocalizedMessage());
                               mDisposable.dispose();
                           }

                           @Override
                           public void onComplete() {
                               LogUtils.i(TAG,"onComplete :");
                               mDisposable.dispose();
                           }
                       });


                break;

            case R.id.btn3:  //post 异步请求
                FormBody formBody = new FormBody.Builder()
                        .build();

                Request request1 = new Request.Builder()
                        .url(finalUrl)
                        .post(formBody)
                        .build();

                mClient.newCall(request1).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.i(TAG, "onFailure 1" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtils.i(TAG, "onResponse 1" + response.body().string());
                    }
                });
                break;
            case R.id.btn4://rxjava2+okhttp下载文件
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                        Request request2 = new Request.Builder().url(fileUrl).build();

                        mClient.newCall(request2).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                LogUtils.i(TAG, "onFailure " + e.getLocalizedMessage());

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                LogUtils.i(TAG, "onResponse 1");
                                long total = response.body().contentLength();
                                float current = 0;
                                InputStream is = null;
                                byte[] buf = new byte[2048];
                                int len = 0;
                                FileOutputStream fos = null;
                                try {
                                    is = response.body().byteStream();
                                    File file = new File(Environment.getExternalStorageDirectory(), "test.xlsx");
                                    fos = new FileOutputStream(file);
                                    while ((len = is.read(buf)) != -1) {
                                        current += len;
                                        fos.write(buf, 0, len);
                                        emitter.onNext(current * 100 / total + "");
                                       // LogUtils.i(TAG, "download current : " + current + ",total :" + total);
                                    }
                                    fos.flush();
                                    LogUtils.i(TAG, "download success ");
                                    emitter.onComplete();
                                } catch (IOException e) {
                                    emitter.onError(e);
                                    LogUtils.printStackTrace(e);
                                    LogUtils.i(TAG, "download error");
                                } finally {
                                    try {
                                        if (is != null) is.close();
                                        if (fos != null) fos.close();
                                    } catch (IOException e) {
                                    }
                                }
                            }
                        });
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            Disposable disposable;
                            @Override
                            public void onSubscribe(Disposable d) {
                                LogUtils.i(TAG, "onSubscribe");
                                disposable=d;
                            }

                            @Override
                            public void onComplete() {
                                LogUtils.i(TAG, "onComplete");
                                disposable.dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.i(TAG, "onError");
                                disposable.dispose();
                                LogUtils.printStackTrace(e);
                            }

                            @Override
                            public void onNext(String s) {
                                LogUtils.i(TAG, "onNext "+s);
                            }
                        });

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
