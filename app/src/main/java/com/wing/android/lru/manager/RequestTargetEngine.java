package com.wing.android.lru.manager;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.wing.android.lru.cache.ActiveCache;
import com.wing.android.lru.cache.MemoryCache;
import com.wing.android.lru.cache.disk.my.DiskLruCacheImpl;
import com.wing.android.lru.fragment.LifecycleCallback;
import com.wing.android.lru.load_data.LoadDataManager;
import com.wing.android.lru.load_data.ResponseListener;
import com.wing.android.lru.resource.Key;
import com.wing.android.lru.resource.Value;
import com.wing.android.lru.resource.ValueCallback;
import com.wing.android.lru.util.Tool;


// callback == RequestTargetEngine  生命周期 感应的  onStart  onStop  onDestrory
public class RequestTargetEngine implements LifecycleCallback, ValueCallback, ResponseListener {

    private final String TAG = RequestTargetEngine.class.getSimpleName();

    // onStart
    @Override
    public void glideInitAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期之 已经开启了 初始化了....");
    }

    // onStop
    @Override
    public void glideStopAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期之 已经停止中 ....");
    }

    // onDestrory
    @Override
    public void glideRecycleAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期之 进行释放操作 缓存策略释放操作等  释放 活动缓存的所有资源 >>>>>> ....");

        // 活动缓存.释放操作();
        if (activeCache != null) {
            activeCache.recycleActive();  // 活动缓存 给释放掉
        }
    }

    private ActiveCache activeCache; // 活动缓存
    private MemoryCache memoryCache; // 内存缓存
    private DiskLruCacheImpl diskLruCache; // 磁盘缓存

    // Glide 获取 内存的 八分之一
    private final int MEMORY_MAX_SIXE = 1024 * 1024 * 60; // 内存缓存 的 maxSize

    /**
     * 构造函数
     */
    public RequestTargetEngine() {
        if (activeCache == null) {
            activeCache = new ActiveCache(this); // 回调给外界，Value资源不再使用了 设置监听
        }
        if (memoryCache == null) {
            memoryCache = new MemoryCache(MEMORY_MAX_SIXE); // 内存缓存
        }

        diskLruCache = new DiskLruCacheImpl(); // 初始化磁盘缓存
    }

    private String path;
    private Context glideContext;
    private String key; // ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a4668 (磁盘缓存用的key)
    private ImageView imageView;

    public void loadValueInitAction(String path, Context requestManagerContext) {
        this.path = path;
        this.glideContext = requestManagerContext;
        this.key = new Key(path).getKey();
    }

    /**
     * 专门给Value，不再使用，的回调接口
     *
     * 监听的方法（Value不再使用了）
     * @param key
     * @param value
     */
    @Override
    public void valueNonUseListener(String key, Value value) { // 只要此函函数掉用   把活动缓存的 Value移除了
        // 加入到 内存缓存
        if (key != null && value != null) {
            memoryCache.put(key, value);
        }
    }

    public void into(ImageView imageView) {
        this.imageView = imageView;

        Tool.checkNotEmpty(imageView); // 检测：释放是空
        Tool.assertMainThread(); // 检测：非主线程 抛出异常

        // 触发：缓存机制
        // TODO 加载资源  ---> 缓存机制  ---> HTTP/SD/ 加载成功后  ---> 把资源保存到缓存中
        Value value = cacheAction();

        if (value != null) {
            imageView.setImageBitmap(value.getBitmap()); // 缓存的显示图片
        }

    }

    /**
     * TODO 加载资源 ---》缓存机制 ---》网络/sd/加载资源成功后 ---》把资源保存到缓存中
     */
    private Value cacheAction() {
        // TODO 第一步：判断活动缓存释放有资源，如果有资源 就返回，   否则就继续往下找
        Value value = activeCache.get(key);
        if (null != value) {
            Log.d(TAG, "cacheAction: 本次加载的是在（活动缓存）中获取的资源>>>");
            return value;
        }

        // TODO 第二步：判断内存缓存是否有资源，如果有资源 剪切(内存 ---> 活动) 就返回，   否则就继续往下找
        value = memoryCache.get(key);
        if (null != value) {
            Log.d(TAG, "cacheAction: 本次加载的是在（内存缓存）中获取的资源>>>");

            // 移动操作 剪切（内存--->活动）
            activeCache.put(key, value); // 把内存缓存中的元素，加入到活动缓存中...
            memoryCache.remove(key); // 移除内存缓存

            return value;
        }

        // TODO 第三步：从磁盘缓存中你去找，如果找到了， 把磁盘缓存的元素 加入到 活动缓存中...
        value = diskLruCache.get(key);
        if (null != value) {
            Log.d(TAG, "cacheAction: 本次加载的是在（磁盘缓存）中获取的资源>>>");

            // 把磁盘缓存中的元素 ---- 加入 ---》 活动缓存中....   不是剪切 是复制
            activeCache.put(key, value);

            return value;
        }

        // TODO 第四步：真正去加载外部资源 数据模型LoadDat 去加载    HTTP / 本地io
        value = new LoadDataManager().loadResource(path, this, glideContext);
        if (value != null) {
            return value;
        }

        return null;  // 有意这样做的，为了后需好判断
    }

    /**
     * 外置资源 成功  回调
     * @param value
     */
    @Override
    public void responseSuccess(Value value) {
        if (null != value) {
            saveCache(key, value); // 调用 保存到 缓存中， 【加载成功情况下】

            imageView.setImageBitmap(value.getBitmap());  // 外置资源 成功  回调  显示给目标  显示图片
        }
    }

    /**
     * 外置资源加载成功后  保存到磁盘缓存
     */
    private void saveCache(String key, Value value) {
        Log.d(TAG, "saveCache: >>>>>>>>>>>>>>>>>>>>>>>>>> 加载外置资源成功后 ，保存到缓存中， key:" + key + " value:" + value);

        value.setKey(key);

        if (diskLruCache != null) {
            diskLruCache.put(key, value); // 保存到磁盘缓存中....

            // activeCache.put(key, value);  这个无所谓 自由控制了
        }
    }

    /**
     * 外置资源 失败  回调
     * @param e
     */
    @Override
    public void responseException(Exception e) {
        Log.d(TAG, "responseException: 加载外部资源失败 请检测 e:" + e.getMessage());
    }
}
