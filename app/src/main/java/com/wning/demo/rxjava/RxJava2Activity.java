package com.wning.demo.rxjava;

import android.app.Dialog;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guagua.modules.utils.LogUtils;
import com.wing.android.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class RxJava2Activity extends AppCompatActivity  {

    private static final String TAG="RxJava2Activity";

    private RecyclerView recyclerView;


    private MyAdapter mAdapter;

    private Dialog dialog;

    private CompositeDisposable mCompositeDisposable;

    private static String[] titles={
            "Observable-Observer",
            "Flowable-Subscriber",
            "consumer",
            "map",
            "zip",
            "concat",
            "flatmap",
            "concatMap",
            "distinct",
            "filter",
            "timer",
            "interval",
            "doOnNext",
            "take",
            "single",
            "debounce",
            "merge",
            "reduce",
            "scan",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        mCompositeDisposable=new CompositeDisposable();
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCompositeDisposable!=null){
            mCompositeDisposable.clear();
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.recycler_view_item, parent, false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            holder.btn.setText(titles[position]);
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        case 0:  //create

                            Observable.create(observableEmitter -> { //lambda
                               observableEmitter.onNext("lambda 1111111111111111");
                            }).subscribe(new Observer<Object>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(Object o) {
                                    LogUtils.i(TAG,"onNext :"+o);

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                            Observable.create(new ObservableOnSubscribe<String>() {
                                @Override
                                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                    LogUtils.i(TAG," begin subscriber :"+emitter);
                                    emitter.onNext(" 1");
                                    emitter.onNext(" 2");
                                    emitter.onNext(" 3");
                                    emitter.onNext(" 4");
                                    emitter.onComplete();
                                    LogUtils.i(TAG," finish subscriber :"+emitter);
                                }
                            }).subscribe(new Observer<String>() {

                                Disposable disposable;

                                @Override
                                public void onSubscribe(Disposable d) {
                                    disposable=d;
                                    LogUtils.i(TAG,"onSubscribe :"+disposable);
                                }

                                @Override
                                public void onNext(String s) {
                                    LogUtils.i(TAG,"onNext :"+s);
                                }

                                @Override
                                public void onError(Throwable t) {
                                    LogUtils.i(TAG,"onError :"+t.getLocalizedMessage());
                                }

                                @Override
                                public void onComplete() {
                                    LogUtils.i(TAG,"onComplete disposable.isDisposed():"+disposable.isDisposed());
                                }
                            });

                            break;
                        case 1:  //Flowable
                           // Observable.just(" 11", " 22", " 33");
                            String[] strs={"111","222","333"};
                            //Observable.fromArray(strs).subscribe(createSubscriber());
                            Flowable.create(new FlowableOnSubscribe<String>() {
                                @Override
                                public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                                    LogUtils.i(TAG," begin subscriber :"+emitter);
                                    emitter.onNext(" 1");
                                    emitter.onNext(" 2");
                                    emitter.onNext(" 3");
                                    emitter.onNext(" 4");
                                    emitter.onComplete();
                                    LogUtils.i(TAG," finish subscriber :"+emitter);
                                }
                            }, BackpressureStrategy.DROP).subscribe(new Subscriber<String>() {
                                Subscription subscription;
                                @Override
                                public void onSubscribe(Subscription s) {
                                    subscription=s;
                                    LogUtils.i(TAG,"onSubcribe :"+subscription);
                                    s.request(Long.MAX_VALUE);
                                }

                                @Override
                                public void onNext(String s) {
                                    LogUtils.i(TAG,"onNext :"+s);
                                }

                                @Override
                                public void onError(Throwable t) {
                                    LogUtils.i(TAG,"onError :"+t.getLocalizedMessage());
                                }

                                @Override
                                public void onComplete() {
                                    LogUtils.i(TAG,"onComplete :");
                                }
                            });
                            break;
                        case 2:  //consumer(rxjava2 用 Consumer 和 BiConsumer 对 Action1 和 Action2 进行了替换)
                            Observable.create(new ObservableOnSubscribe<String>() {
                                @Override
                                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                    emitter.onNext("this is one");
                                    emitter.onNext("this is two");
                                    emitter.onComplete();
                                }
                            }).subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    LogUtils.i(TAG,"accept :"+s);
                                }
                            });
                            break;
                        case 3://map
                            Observable.create(new ObservableOnSubscribe<Integer>() {
                                @Override
                                public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                                    emitter.onNext(1);
                                    emitter.onNext(2);
                                    emitter.onNext(3);
                                }
                            }).map(new Function<Integer, String>() {
                                @Override
                                public String apply(Integer integer) throws Exception {
                                    return "it is :"+integer;
                                }
                            }).subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    LogUtils.i(TAG,"map accept :"+s);
                                }
                            });
                            break;
                        case 4: //zip zip 组合事件的过程就是分别从发射器 A 和发射器 B 各取出一个事件来组合，并且一个事件只能被使用一次，
                            // 组合的顺序是严格按照事件发送的顺序来进行的，所以上面截图中，可以看到，1 永远是和 A 结合的，2 永远是和 B 结合的。
                           // 最终接收器收到的事件数量是和发送器发送事件最少的那个发送器的发送事件数目相同
                            Observable.zip(Observable.create(new ObservableOnSubscribe<String>() {
                                @Override
                                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                    emitter.onNext("A");
                                    emitter.onNext("B");
                                    emitter.onNext("C");
                                }
                            }), Observable.create(new ObservableOnSubscribe<Integer>() {
                                @Override
                                public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                                    emitter.onNext(1);
                                    emitter.onNext(2);
                                    emitter.onNext(3);
                                    emitter.onNext(4);
                                }
                            }), new BiFunction<String, Integer, String>() {
                                @Override
                                public String apply(String s, Integer integer) throws Exception {
                                    return s+"---"+integer;//两个值相加，返回string
                                }
                            }).subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    LogUtils.i(TAG,"zip accept :"+s); //A1 B2 C3
                                }
                            });
                            break;
                        case 5: //concat 合并多个observerable，依次输出
                            Observable.concat(Observable.just(1,3,5),Observable.just(2,4,6))
                                    .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    LogUtils.i(TAG,"concat accept :"+integer);  //结果是135246
                                }
                            });
                            break;
                        case 6:  //flatmap
                            Observable.just("http://www.baidu.com/","http://www.qq.com/","http://www.iqiju.com/","http://www.sina.com/")
                                    .flatMap(new Function<String, ObservableSource<String>>() {
                                        @Override
                                        public ObservableSource<String> apply(final String url) throws Exception {
                                            return Observable.create(new ObservableOnSubscribe<String>() {
                                                @Override
                                                public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                                                    try {
                                                        URL urls = new URL(url);
                                                        String host = urls.getHost();
                                                        String address = InetAddress.getByName(host).toString();
                                                        int b = address.indexOf("/");  //www.iqiju.com/221.228.219.107
                                                        emitter.onNext(url+" : "+address.substring(b + 1));
                                                        String content ="Main Thread:" + ((RxJava2Activity.this.getMainLooper() == Looper.myLooper()) +
                                                                ", Thread Name:" + Thread.currentThread().getName());
                                                        LogUtils.i(TAG,"onNext thread :"+content);
                                                        emitter.onComplete();
                                                    } catch (UnknownHostException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            })
                                                    .subscribeOn(Schedulers.io());//现在IO线程用的不是同一线程
                                        }
                                    })
                                    //.subscribeOn(Schedulers.io())  //打开这行，注释掉上边那行的话，O线程用的都是同一线程
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            LogUtils.i(TAG,"onSubscribe ");
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            LogUtils.i(TAG,"onNext "+s);
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            LogUtils.i(TAG,"onerror "+e.getLocalizedMessage());
                                        }

                                        @Override
                                        public void onComplete() {
                                            LogUtils.i(TAG,"onComplete");
                                        }
                                    });
                            break;
                        case 7:  //concatMap,同flatmap，只不过concatMap是有序的
                            Observable.just("http://www.baidu.com/","http://www.qq.com/","http://www.iqiju.com/","http://www.sina.com/")
                                    .concatMap(new Function<String, ObservableSource<String>>() {
                                        @Override
                                        public ObservableSource<String> apply(final String url) throws Exception {
                                            return Observable.create(new ObservableOnSubscribe<String>() {
                                                @Override
                                                public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                                                    try {
                                                        URL urls = new URL(url);
                                                        String host = urls.getHost();
                                                        String address = InetAddress.getByName(host).toString();
                                                        int b = address.indexOf("/");  //www.iqiju.com/221.228.219.107
                                                        emitter.onNext(url+" : "+address.substring(b + 1));
                                                        String content ="Main Thread:" + ((RxJava2Activity.this.getMainLooper() == Looper.myLooper()) +
                                                                ", Thread Name:" + Thread.currentThread().getName());
                                                        LogUtils.i(TAG,"onNext thread :"+content);
                                                        emitter.onComplete();
                                                    } catch (UnknownHostException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            })
                                                    .subscribeOn(Schedulers.io());//现在IO线程用的不是同一线程
                                        }
                                    })
                                    //.subscribeOn(Schedulers.io())  //打开这行，注释掉上边那行的话，O线程用的都是同一线程
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            LogUtils.i(TAG,"onSubscribe ");
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            LogUtils.i(TAG,"onNext "+s);
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            LogUtils.i(TAG,"onerror "+e.getLocalizedMessage());
                                        }

                                        @Override
                                        public void onComplete() {
                                            LogUtils.i(TAG,"onComplete");
                                        }
                                    });
                            break;
                        case 8:  //distinct 去重
                            Observable.concat(Observable.just(1,3,5),Observable.just(1,5,6))
                                    .distinct()
                                    .subscribe(new Consumer<Integer>() {
                                        @Override
                                        public void accept(Integer integer) throws Exception {
                                            LogUtils.i(TAG,"distinct accept :"+integer);  //结果是1356
                                        }
                                    });
                            break;
                        case 9:  //filter过滤器
                            Observable.just(1,3,4,11,13,14)
                                    .filter(new Predicate<Integer>() {
                                        @Override
                                        public boolean test(Integer integer) throws Exception {
                                            return integer>10;
                                        }
                                    }).subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    LogUtils.i(TAG,"filter accept :"+integer);  //结果是11 13 14
                                }
                            });
                            break;
                        case 10://timer 执行一次
                            /**
                             * 结果：06-22 16:55:14.771 17824-17824/com.wning.demo I/RxJava2Activity: timer  :2018-06-22 16:55:14,main
                             06-22 16:55:16.914 17824-18293/com.wning.demo I/RxJava2Activity: timer long :0 , 2018-06-22 16:55:16,RxComputationThreadPool-1
                             */
                            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String form=sd.format(new Date());
                            LogUtils.i(TAG,"timer  :"+form+","+ Thread.currentThread().getName());

                            Observable.timer(2, TimeUnit.SECONDS)
                                    .subscribe(new Consumer<Long>() {
                                        @Override
                                        public void accept(Long aLong) throws Exception {
                                            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String form=sd.format(new Date());
                                            LogUtils.i(TAG,"timer long :"+aLong+" , "+form+","+ Thread.currentThread().getName());
                                        }
                                    });
                            break;
                        case 11://interval

                            /**
                             * 06-22 16:59:25.669 18668-18668/com.wning.demo I/RxJava2Activity: interval  :2018-06-22 16:59:25,main
                             06-22 16:59:28.803 18668-19227/com.wning.demo I/RxJava2Activity: interval long :0 , 2018-06-22 16:59:28,RxComputationThreadPool-1
                             06-22 16:59:30.804 18668-19227/com.wning.demo I/RxJava2Activity: interval long :1 , 2018-06-22 16:59:30,RxComputationThreadPool-1
                             06-22 16:59:32.803 18668-19227/com.wning.demo I/RxJava2Activity: interval long :2 , 2018-06-22 16:59:32,RxComputationThreadPool-1
                             06-22 16:59:34.805 18668-19227/com.wning.demo I/RxJava2Activity: interval long :3 , 2018-06-22 16:59:34,RxComputationThreadPool-1
                             06-22 16:59:36.804 18668-19227/com.wning.demo I/RxJava2Activity: interval long :4 , 2018-06-22 16:59:36,RxComputationThreadPool-1
                             *
                             */
                             sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                             form=sd.format(new Date());
                            LogUtils.i(TAG,"interval  :"+form+","+ Thread.currentThread().getName());

                            Disposable disposable = Observable.interval(3, 2, TimeUnit.SECONDS)
                                    .subscribe(new Consumer<Long>() {
                                        @Override
                                        public void accept(Long aLong) throws Exception {
                                            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String form = sd.format(new Date());
                                            LogUtils.i(TAG, "interval long :" + aLong + " , " + form + "," + Thread.currentThread().getName());
                                        }
                                    });
                            mCompositeDisposable.add(disposable);
                            break;
                        case 12: //doOnnext
                            Observable.just(1,2,3,4)
                                    .doOnNext(new Consumer<Integer>() {
                                        @Override
                                        public void accept(Integer integer) throws Exception {
                                            LogUtils.i(TAG,"doOnNext accept integer :"+integer);
                                        }
                                    }).subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    LogUtils.i(TAG," accept integer :"+integer);
                                }
                            });
                            break;
                        case 13://take 取前n个
                            Observable.just(11,22,33)
                                    .take(2)
                                    .subscribe(new Consumer<Integer>() {
                                        @Override
                                        public void accept(Integer integer) throws Exception {
                                            LogUtils.i(TAG,"take accept :"+integer);  //11 22
                                        }
                                    });
                            break;
                        case 14://single  可用于网络请求
                            Single.just(1).subscribe(new SingleObserver<Integer>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    LogUtils.i(TAG,"onSubscribe :"+d);
                                }

                                @Override
                                public void onSuccess(Integer integer) {
                                    LogUtils.i(TAG,"onSuccess :"+integer);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    LogUtils.i(TAG,"onError :"+e.getLocalizedMessage());
                                }
                            });
                            break;
                        case 15://debounce 当一个事件发送出来之后，在约定时间内没有再次发送这个事件，则发射这个事件，如果再次触发了，则重新计算时间。
                            Observable.create(new ObservableOnSubscribe<Integer>() {
                                @Override
                                public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                                    emitter.onNext(1); // skip
                                    Thread.sleep(400);
                                    emitter.onNext(2); // deliver
                                    Thread.sleep(505);
                                    emitter.onNext(3); // skip
                                    Thread.sleep(100);
                                    emitter.onNext(4); // deliver
                                    Thread.sleep(605);
                                    emitter.onNext(5); // deliver
                                    Thread.sleep(510);
                                    emitter.onComplete();
                                }
                            }).debounce(500,TimeUnit.MILLISECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<Integer>() {
                                        @Override
                                        public void accept(Integer integer) throws Exception {
                                            LogUtils.i(TAG,"debounce accept :"+integer);  //2 4 5
                                        }
                                    });
                            /**
                             * 第一个事件1发送出来以后过了400毫秒后发送出了第二个事件，此时不事件1不满足时间的条件被遗弃，然后重新计时；
                             2发出后休眠了505毫秒，超过了500毫秒，所以2被发射了出来，被观察者收到；
                             3发出来后又过了100毫秒4发出来，所以3被遗弃，从4重新计时，后又过了605毫秒下一个事件才发出，所以4被发射了出来；
                             同理，5之后的0.5秒内也没有再发出别的事件，所以最终5也被发射了出来。
                             类似一个弹簧，如果一个事件相当于挤压它一下的话，它回到初始状态需要一段时间，那如果一直有事件不断的挤压它，那它一直回不到初始状态，就一个事件也弹不出来。一旦有一段时间里面没有人挤压它，他就把最后一个弹出来了。周而复始
                             */

                            break;
                        case 16: //merge 合并多个observerable，合并输出
                            //注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
                            Observable.concat(Observable.just(1,2,3,4,5,6,7,8,9,10),
                                    Observable.just(11,12,13,14,15,16,17,18,19,20))
                                    .subscribe(new Consumer<Integer>() {
                                        @Override
                                        public void accept(Integer integer) throws Exception {
                                            LogUtils.i(TAG,"merge accept :"+integer);  //结果是135246
                                        }
                                    });
                            break;
                        case 17://reduce 操作符每次用一个方法处理一个值,可以有一个 seed 作为初始值
                            Observable.just(1,2,3,4)
                                    .reduce(100, new BiFunction<Integer, Integer, Integer>() {
                                        @Override
                                        public Integer apply(Integer integer, Integer integer2) throws Exception {
                                            return integer+integer2;
                                        }
                                    }).subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    LogUtils.i(TAG,"reduce accept :"+integer);  //110
                                }
                            });
                            break;
                        case 18://scan 操作符每次用一个方法处理一个值,可以有一个 seed 作为初始值
                            Observable.just(1,2,3,4)
                                    .scan(100, new BiFunction<Integer, Integer, Integer>() {
                                        @Override
                                        public Integer apply(Integer integer, Integer integer2) throws Exception {
                                            return integer+integer2;
                                        }
                                    }).subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    LogUtils.i(TAG,"scan accept :"+integer);  //100 101 103 106 110
                                }
                            });
                            break;

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }


    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView btn;

        public MyHolder(View itemView) {
            super(itemView);
            btn =  itemView.findViewById(R.id.btn);
        }
    }


    /*
    * 从Assets中读取图片
    */
    private Bitmap getImageFromAssetsFile(String fileName)
    {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try
        {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return image;

    }

    private List<Student> createStudents(){
        Course course1=new Course("语文");
        Course course2=new Course("数学");
        Course course3=new Course("英语");
        Student stu1=new Student("小明",Arrays.asList(course1,course2,course3));
        Student stu2=new Student("小红",Arrays.asList(course2,course3));
        return Arrays.asList(stu1,stu2);
    }
}
