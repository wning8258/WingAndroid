package com.wning.demo.producer;

/**
 * Created by wning on 2018/3/27.
 */

public class DelegateConsumer implements Consumer {

    private  Consumer mConsumer;


    public DelegateConsumer(Consumer consumer) {
        mConsumer = consumer;
    }

    public Consumer getConsumer() {
        return mConsumer;
    }



    @Override
    public void onNewResult() {
        mConsumer.onNewResult();
    }

    @Override
    public void onFailResult() {
        mConsumer.onNewResult();
    }
}
