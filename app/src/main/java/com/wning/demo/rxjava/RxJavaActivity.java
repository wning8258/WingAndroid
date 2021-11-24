package com.wning.demo.rxjava;//package com.wning.com.wning.demo.rxjava;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.res.AssetManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.guagua.modules.utils.LogUtils;
//import com.wing.android.R;
//import com.wning.demo.logprint.LogPrinter;
//import com.wning.com.wning.demo.utils.UIUtils;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.List;
//
//import butterknife.BindView;
//import rx.Observable;
//import rx.Subscriber;
//import rx.Subscription;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Action0;
//import rx.functions.Action1;
//import rx.functions.Func1;
//import rx.schedulers.Schedulers;
//
//public class RxJavaActivity extends AppCompatActivity  {
//
//    private static final String TAG="RxJavaActivity";
//
//    private RecyclerView recyclerView;
//
//
//    private MyAdapter mAdapter;
//
//    private Dialog dialog;
//
//    private static String[] titles={
//            "create",
//            "just/from",
//            "subscribe action",
//            "schedulers",
//            "map",
//            "flatmap",
//            "doOnSubscribe",
//            "from action"
//    };
//
//    private Subscription subscription;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rx_java);
//        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        mAdapter = new MyAdapter();
//        recyclerView.setAdapter(mAdapter);
//    }
//
//
//
//    private Subscriber<String> createSubscriber(){
//         final Subscriber<String> subscriber=new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                LogUtils.i(TAG,"onCompleted :");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtils.i(TAG,"onError :"+e);
//            }
//
//            @Override
//            public void onNext(String s) {
//                LogUtils.i(TAG,"onNext :"+s);
//            }
//
//            @Override
//            public void onStart() {
//                LogUtils.i(TAG,"onStart");
//            }
//
//        };
//        return subscriber;
//    }
//
//
//    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {
//
//        @Override
//        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.recycler_view_item, parent, false);
//            MyHolder holder = new MyHolder(view);
//            return holder;
//        }
//
//        @Override
//        public void onBindViewHolder(MyHolder holder, final int position) {
//            holder.btn.setText(titles[position]);
//            holder.btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Subscriber<String> subscriber = createSubscriber();
//                    Observable<String> observable;
//                    LogPrinter.clear();
//                    switch (position){
//                        case 0:  //create
//                            observable=Observable.create(new Observable.OnSubscribe<String>() {
//                                @Override
//                                public void call(Subscriber<? super String> subscriber) {
//                                    //subscriber转换为SafeSubscriber类型
//                                    LogUtils.i(TAG," begin subscriber :"+subscriber);
//                                    subscriber.onNext(" 1");
//                                    subscriber.onNext(" 2");
//                                    subscriber.onNext(" 3");
//                                    subscriber.onNext(" 4");
//                                    subscriber.onCompleted();
//                                    LogUtils.i(TAG," finish subscriber :"+subscriber);
//                                }
//                            });
//                            subscription = observable.subscribe(subscriber);
//                            break;
//                        case 1:  //just/from
//                            observable= Observable.just(" 11", " 22", " 33");
//                            //        String[] strs={"111","222","333"};
//                            //        Observable.from(strs);
//                            subscription = observable.subscribe(subscriber);
//                            break;
//                        case 2: //subscribe action
//                            Action0 action0=new Action0() {
//                                @Override
//                                public void call() {
//                                     LogUtils.i(TAG,"onCompleted");
//                                }
//                            };
//
//                            Action1<String> action1=new Action1<String>() {
//                                @Override
//                                public void call(String s) {
//                                     LogUtils.i(TAG,"onNext");
//                                }
//                            };
//
//                            Action1<Throwable> action2=new Action1<Throwable>() {
//                                @Override
//                                public void call(Throwable throwable) {
//                                     LogUtils.i(TAG,"onerror");
//                                }
//                            };
//
//                            observable= Observable.just(" 111", " 222", " 333");
//                            subscription = observable.subscribe(action1);
//                           // observable.subscribe(action1,action2);
//                          //  observable.subscribe(action1,action2,action0);
//                            break;
//                        case 3:  //schedulers
//                            subscription=Observable.create(new Observable.OnSubscribe<String>() {
//                            @Override
//                            public void call(Subscriber<? super String> subscriber) {
//                                //使用scheduler时，subscriber转换为OperatorSubscribeOn类型
//                                 LogUtils.i(TAG," begin subscriber :"+subscriber+" thread :"+Thread.currentThread());  //io线程
//                                subscriber.onNext(" 1");  //主线程
//                                subscriber.onNext(" 2"); //主线程
//                                subscriber.onNext(" 3");//主线程
//                                subscriber.onNext(" 4");//主线程
//                                subscriber.onCompleted();
//                                 LogUtils.i(TAG," finish subscriber :"+subscriber+" thread :"+Thread.currentThread());//io线程
//                            }
//                        }).subscribeOn(Schedulers.io())  //
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribe(new Subscriber<String>() {
//                                        @Override
//                                        public void onStart() {
//                                             LogUtils.i(TAG,"onStart thread :"+Thread.currentThread());
//                                        }
//
//                                        @Override
//                                        public void onCompleted() {
//                                             LogUtils.i(TAG,"onCompleted thread :"+Thread.currentThread());
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//
//                                        }
//
//                                        @Override
//                                        public void onNext(String s) {
//                                             LogUtils.i(TAG,"s :"+s+" thread :"+Thread.currentThread());
//                                        }
//                                    });
//                            break;
//                        case 4:  //map
//
////                            new Thread(new Runnable() {
////                                @Override
////                                public void run() {
//                                    subscription=Observable
//                                            .create(new Observable.OnSubscribe<String>() {
//                                                @Override
//                                                public void call(Subscriber<? super String> subscriber) {
//                                                     LogUtils.i(TAG,"call thread ："+Thread.currentThread());
//                                                    subscriber.onStart();
//                                                    subscriber.onNext("gift.png");  //主线程
//                                                    subscriber.onCompleted();
//                                                }
//                                             })
//                                            .map(new Func1<String, Bitmap>() {  //Func1是有返回值的
//                                                @Override
//                                                public Bitmap call(String filename) {
//                                                     LogUtils.i(TAG,"map thread ："+Thread.currentThread());
//                                                    try {
//                                                        Thread.sleep(2000);
//                                                    } catch (InterruptedException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                    return getImageFromAssetsFile(filename);
//                                                }
//                                            })
//                                            .subscribeOn(Schedulers.io())  //指定subscribe
//                                            .observeOn(AndroidSchedulers.mainThread()) //指定observer
//                                              .subscribe(new Subscriber<Bitmap>() {
//                                                Dialog dialog;
//                                                @Override
//                                                public void onStart() {  //subscribe方法调用时，所在的线程，不能指定线程
//                                                    /**
//                                                     * onstart在subscribe执行时就被调用，所以发生在subscribe方法所在的线程，
//                                                     */
//                                                  //  java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
//                                                     dialog = UIUtils.showAnimLoading(RxJavaActivity.this, false, false);
//                                                    Toast.makeText(getApplicationContext(),"onstart",Toast.LENGTH_SHORT).show();
//                                                }
//
//                                                @Override
//                                                public void onCompleted() {  //mainThread
//                                                    UIUtils.dismissAnimLoading(dialog);
//                                                    Toast.makeText(getApplicationContext(),"onCompleted",Toast.LENGTH_SHORT).show();
//                                                }
//
//                                                @Override
//                                                public void onError(Throwable e) {
//
//                                                }
//
//                                                @Override
//                                                public void onNext(Bitmap bitmap) {  //mainThread
//                                                    AlertDialog.Builder builder=new AlertDialog.Builder(RxJavaActivity.this);
//                                                    ImageView view=new ImageView(getApplicationContext());
//                                                    view.setImageBitmap(bitmap);
//                                                    builder.setView(view);
//                                                    builder.create().show();
//                                                }
//                                            });
//                         //       }
//                         //   }).start();
//
//                            break;
//                        case 5: //flatmap
//                            List<Student> students = createStudents();
//                            Observable
//                                    .from(students)
//                                    .flatMap(new Func1<Student, Observable<Course>>() {
//                                        @Override
//                                        public Observable<Course> call(Student student) {
//                                             LogUtils.i(TAG,"student : "+student.getName());
//                                            return Observable.from(student.getCourses());
//                                        }
//                                    })
//                                    .subscribe(new Action1<Course>() {
//                                        @Override
//                                        public void call(Course course) {
//                                             LogUtils.i(TAG,"course : "+course.getName());
//                                        }
//                                    });
//                            break;
//                        case 6:  //doOnSubscribe
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    subscription= Observable.just("gift.png")
//                                            .subscribeOn(Schedulers.io())
//                                            .map(new Func1<String, Bitmap>() {
//                                                @Override
//                                                public Bitmap call(String filename) {
//                                                     LogUtils.i(TAG,"map thread ："+Thread.currentThread());
//                                                    try {
//                                                        Thread.sleep(2000);
//                                                    } catch (InterruptedException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                    return getImageFromAssetsFile(filename);
//                                                }
//                                            })
//                                            .doOnSubscribe(new Action0() {
//                                                @Override
//                                                public void call() {
//                                                     LogUtils.i(TAG,"doOnSubscribe thread ："+Thread.currentThread());
//                                                     dialog=UIUtils.showAnimLoading(RxJavaActivity.this,false,false);
//                                                }
//                                            }).subscribeOn(AndroidSchedulers.mainThread())
//                                            .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribe(new Subscriber<Bitmap>() {
//                                        @Override
//                                        public void onCompleted() {
//                                            UIUtils.dismissAnimLoading(dialog);
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//
//                                        }
//
//                                        @Override
//                                        public void onNext(Bitmap bitmap) {
//                                            AlertDialog.Builder builder=new AlertDialog.Builder(RxJavaActivity.this);
//                                            ImageView view=new ImageView(getApplicationContext());
//                                            view.setImageBitmap(bitmap);
//                                            builder.setView(view);
//                                            builder.create().show();
//                                        }
//                                    });
//                                }
//                            }).start();
//                            break;
//                        case 7:
//                            String[] names={"aaa","bbb","ccc"};
//                            Observable.from(names).subscribe(new Action1<String>() {
//                                @Override
//                                public void call(String s) {
//                                    LogUtils.i(TAG,"name: "+s);
//                                }
//                            });
//                            break;
//                    }
//                    if(subscription!=null&&subscription.isUnsubscribed()){
//                        subscription.unsubscribe();
//                    }
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return titles.length;
//        }
//
//
//    }
//
//    class MyHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.btn)
//        TextView btn;
//
//        public MyHolder(View itemView) {
//            super(itemView);
//            btn = (TextView) itemView.findViewById(R.id.btn);
//        }
//    }
//
//
//    /*
//    * 从Assets中读取图片
//    */
//    private Bitmap getImageFromAssetsFile(String fileName)
//    {
//        Bitmap image = null;
//        AssetManager am = getResources().getAssets();
//        try
//        {
//            InputStream is = am.open(fileName);
//            image = BitmapFactory.decodeStream(is);
//            is.close();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        return image;
//
//    }
//
//    private List<Student> createStudents(){
//        Course course1=new Course("语文");
//        Course course2=new Course("数学");
//        Course course3=new Course("英语");
//        Student stu1=new Student("小明",Arrays.asList(course1,course2,course3));
//        Student stu2=new Student("小红",Arrays.asList(course2,course3));
//        return Arrays.asList(stu1,stu2);
//    }
//}
