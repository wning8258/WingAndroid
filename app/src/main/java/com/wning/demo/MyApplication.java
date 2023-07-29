package com.wning.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Debug;
import android.os.Trace;
import android.os.Debug;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.Wing;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.guagua.modules.utils.LogUtils;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mmkv.MMKV;
import com.umeng.commonsdk.UMConfigure;
import com.wing.android.BuildConfig;
import com.wing.android.R;
import com.wning.demo.net.volley.data.RequestManager;
import com.wning.demo.utils.DarkModeKt;
import com.wning.demo.utils.ServiceLocator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/30.
 */
public class MyApplication  extends MultiDexApplication {
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
//        /**
//         * 路径 Context.getExternalFilesDir(String).
//         * buffer size 默认为8M
//         */
//         Debug.startMethodTracing("WingAndroid");

        //traceview
        //Trace.beginSection("AppOncreate");

        mInstance=this;
        MMKV.initialize(this);

        ServiceLocator.appContext = this;

        registerActivityLifecycleCallbacks(ActivityTracker.INSTANCE);

        DarkModeKt.initNightMode();


        initImageLoader();

        initArouter();


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

        //Debug.stopMethodTracing();

        //Trace.endSection();
        BlockCanary.install(this, new AppBlockCanaryContext()).start();

        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         */
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        UMConfigure.init(this,"622b391e317aa877609205d0","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
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

    private void  initArouter() {
        if (LogUtils.ISDEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    class AppBlockCanaryContext extends BlockCanaryContext {
        private static final String TAG = "AppContext";

        @Override
        public String provideQualifier() {
            String qualifier = "";
            try {
                PackageInfo info = MyApplication.getInstance().getPackageManager()
                        .getPackageInfo(MyApplication.getInstance().getPackageName(), 0);
                qualifier += info.versionCode + "_" + info.versionName + "_YYB";
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "provideQualifier exception", e);
            }
            return qualifier;
        }

        @Override
        public String provideUid() {
            return "87224330";
        }

        @Override
        public String provideNetworkType() {
            return "4G";
        }

        @Override
        public int provideMonitorDuration() {
            return 9999;
        }

        @Override
        public int provideBlockThreshold() {
            return 500;
        }

        @Override
        public boolean displayNotification() {
            return BuildConfig.DEBUG;
        }

        @Override
        public List<String> concernPackages() {
            List<String> list = super.provideWhiteList();
            list.add("com.example");
            return list;
        }

        @Override
        public List<String> provideWhiteList() {
            List<String> list = super.provideWhiteList();
            list.add("com.whitelist");
            return list;
        }

        @Override
        public boolean stopWhenDebugging() {
            return true;
        }
    }
}
