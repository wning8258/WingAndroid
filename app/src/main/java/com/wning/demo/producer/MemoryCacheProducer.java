package com.wning.demo.producer;

import com.guagua.modules.utils.LogUtils;
import com.wning.demo.MyApplication;

/**
 * Created by wning on 2018/3/27.
 */

public class MemoryCacheProducer implements Producer {

    private Producer inputProducer;

    public MemoryCacheProducer(Producer inputProducer){
        this.inputProducer=inputProducer;
    }

    @Override
    public void produceResult(Consumer consumer) {

        boolean find=false;

        if(MyApplication.getInstance().map.get(1)!=null){
            find=true;
        }

        LogUtils.i("wn","MemoryCacheProducer produceResult find "+find);

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
                LogUtils.i("wn","MemoryCacheProducer consumer onNewResult map put");
                MyApplication.getInstance().map.put(1,"aaa");
                super.onNewResult();
            }
        };
    }
}
