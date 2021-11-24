package com.wning.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.Wing;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.guagua.modules.utils.LogUtils;
import com.networkbench.agent.impl.NBSAppAgent;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.wing.android.R;
import com.wning.demo.net.volley.data.RequestManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/30.
 */
@Wing
public class MyApplication  extends Application{
    //图片最大缓存大小15MB
    private static final int MAX_DISK_CACHE_SIZE = 15 * 1024 * 1024;
    private DisplayImageOptions defaultDisplayImageOptions;
    private static MyApplication mInstance;
    public static MyApplication getInstance() {
        return mInstance;
    }
    public Map<Integer,String> map=new HashMap();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;

        NBSAppAgent.setLicenseKey("0f8afd35dcc74acd87c34adb64382409")
                .withLocationServiceEnabled(true).start(this); //Appkey 请从官网获取

        initImageLoader();

        if (LogUtils.ISDEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        RequestManager.init(this);
        Fresco.initialize(getApplicationContext());

//        ImagePipelineConfig imagePipelineConfig=ImagePipelineConfig
//                .newBuilder(getApplicationContext())
//               // .setBitmapsConfig()
//                //....
//                .build();
//       //一定要new一下factory,PipelineDraweeControllerBuilderSupplier初始化的时候使用到了ImagePipelineFactory对象
//       // ImagePipelineFactory factory=new ImagePipelineFactory(imagePipelineConfig);
//        //factory.getImagePipeline();
//
//        Fresco.initialize(getApplicationContext(),imagePipelineConfig);
    }

    private void initImageLoader() {
        int memoryCacheSize;
        int threadPoolSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
            memoryCacheSize = (memClass / 4) * 1024 * 1024;
            threadPoolSize=3;
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
            threadPoolSize=2;
        }

        defaultDisplayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.gg_icon_default_head)
                .showImageForEmptyUri(R.drawable.gg_load_fail_big).showImageOnFail(R.drawable.gg_load_fail_big).cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true).build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(memoryCacheSize).denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(MAX_DISK_CACHE_SIZE).tasksProcessingOrder(QueueProcessingType.LIFO)
                .threadPoolSize(threadPoolSize)
                .memoryCache(new LRULimitedMemoryCache(memoryCacheSize))
                .defaultDisplayImageOptions(defaultDisplayImageOptions).writeDebugLogs().build();
        ImageLoader.getInstance().init(configuration);
    }
}
