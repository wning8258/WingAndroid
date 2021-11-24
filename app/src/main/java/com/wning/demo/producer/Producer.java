package com.wning.demo.producer;

/**
 * Created by wning on 2018/3/27.
 */

public interface Producer {
    void produceResult(Consumer consumer);
    Consumer wrapConsumer(Consumer consumer);
}
