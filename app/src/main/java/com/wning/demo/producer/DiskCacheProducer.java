package com.wning.demo.producer;

import com.guagua.modules.utils.LogUtils;
import com.wning.demo.MyApplication;

/**
 * Created by wning on 2018/3/27.
 */

public class DiskCacheProducer implements Producer {

    private Producer inputProducer;

    public DiskCacheProducer(Producer inputProducer){
        this.inputProducer=inputProducer;
    }

    @Override
    public void produceResult(com.wning.demo.producer.Consumer consumer) {
        boolean find=false;
       if(MyApplication.getInstance().map.get(2)!=null){
            find=true;
        }
        LogUtils.i("wn","DiskCacheProducer produceResult find "+find);

        if(find){
            wrapConsumer(consumer).onNewResult();
            return;
        }

        inputProducer.produceResult(wrapConsumer(consumer));
    }

    @Override
    public Consumer wrapConsumer(Consumer consumer) {
        return new DelegateConsumer(consumer){
            @Override
            public void onNewResult() {
                LogUtils.i("wn","DiskCacheProducer Consumer onNewResult map put");
                MyApplication.getInstance().map.put(2,"bbb");
                super.onNewResult();
            }
        };
    }
}
