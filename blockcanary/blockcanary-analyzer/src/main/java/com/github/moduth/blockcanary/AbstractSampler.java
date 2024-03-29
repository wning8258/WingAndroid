/*
 * Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.moduth.blockcanary;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@link AbstractSampler} sampler defines sampler work flow.
 */
abstract class AbstractSampler {

    private static final int DEFAULT_SAMPLE_INTERVAL = 300;

    protected AtomicBoolean mShouldSample = new AtomicBoolean(false);
    protected long mSampleInterval;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //抽象方法
            doSample();
            //继续执行采集
            //300ms dump一次，可在context中设置
            if (mShouldSample.get()) {
                HandlerThreadFactory.getTimerThreadHandler()
                        .postDelayed(mRunnable, mSampleInterval);
            }
        }
    };

    public AbstractSampler(long sampleInterval) {
        if (0 == sampleInterval) {
            sampleInterval = DEFAULT_SAMPLE_INTERVAL;
        }
        mSampleInterval = sampleInterval;
    }

    public void start() {
        if (mShouldSample.get()) {
            return;
        }
        mShouldSample.set(true);
        //通过一个HandlerThread延时执行了mRunnable
        HandlerThreadFactory.getTimerThreadHandler().removeCallbacks(mRunnable);
        /**
         * provideBlockThreshold() * 0.8f
         * 这个延迟 是阀值的80%（有可能dump出来的堆栈 不是真正block的堆栈）
         */
        HandlerThreadFactory.getTimerThreadHandler().postDelayed(mRunnable,
                BlockCanaryInternals.getInstance().getSampleDelay());
    }

    public void stop() {
        //设置控制变量
        if (!mShouldSample.get()) {
            return;
        }
        mShouldSample.set(false);
        //取消handler消息
        HandlerThreadFactory.getTimerThreadHandler().removeCallbacks(mRunnable);
    }

    abstract void doSample();
}
