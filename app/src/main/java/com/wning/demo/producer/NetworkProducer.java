package com.wning.demo.producer;

import com.guagua.modules.utils.LogUtils;

/**
 * Created by wning on 2018/3/27.
 */

public class NetworkProducer implements Producer {
    @Override
    public void produceResult(com.wning.demo.producer.Consumer consumer) {

        boolean find=false;

        if(true){//网络获取到
            find=true;
        }

        LogUtils.i("wn","NetworkProducer produceResult find "+find);
        if(find){
            wrapConsumer(consumer).onNewResult();
        }
    }

    @Override
    public Consumer wrapConsumer(Consumer consumer) {
        return new DelegateConsumer(consumer){
            @Override
            public void onNewResult() {
                LogUtils.i("wn","NetworkProducer consumer onNewResult");
                super.onNewResult();
            }
        };
    }
}
