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

import android.os.Debug;
import android.os.SystemClock;
import android.util.Printer;

class LooperMonitor implements Printer {

    private static final int DEFAULT_BLOCK_THRESHOLD_MILLIS = 3000;

    private long mBlockThresholdMillis = DEFAULT_BLOCK_THRESHOLD_MILLIS;
    private long mStartTimestamp = 0;
    private long mStartThreadTimestamp = 0;
    private BlockListener mBlockListener = null;
    private boolean mPrintingStarted = false;
    private final boolean mStopWhenDebugging;

    public interface BlockListener {
        void onBlockEvent(long realStartTime,
                          long realTimeEnd,
                          long threadTimeStart,
                          long threadTimeEnd);
    }

    public LooperMonitor(BlockListener blockListener, long blockThresholdMillis, boolean stopWhenDebugging) {
        if (blockListener == null) {
            throw new IllegalArgumentException("blockListener should not be null.");
        }
        mBlockListener = blockListener;
        mBlockThresholdMillis = blockThresholdMillis;
        mStopWhenDebugging = stopWhenDebugging;
    }

    @Override
    public void println(String x) {
        if (mStopWhenDebugging && Debug.isDebuggerConnected()) {
            //如果在debug模式，不执行监听
            return;
        }
        if (!mPrintingStarted) {  //dispatchMesage前执行的println
            //记录开始时间
            mStartTimestamp = System.currentTimeMillis();
            // 返在当前线程运行的毫秒数。
            mStartThreadTimestamp = SystemClock.currentThreadTimeMillis();
            mPrintingStarted = true;
            //开始采集栈及cpu信息
            startDump();
        } else {  //dispatchMesage后执行的println
            // 获取结束时间
            final long endTime = System.currentTimeMillis();
            mPrintingStarted = false;
            //判断是否超过阈值
            if (isBlock(endTime)) {
                //回调监听
                notifyBlockEvent(endTime);
            }
            stopDump();
        }
    }

    //判断是否超过阈值(默认3s)
    private boolean isBlock(long endTime) {
        return endTime - mStartTimestamp > mBlockThresholdMillis;
    }

    private void notifyBlockEvent(final long endTime) {
        final long startTime = mStartTimestamp;
        final long startThreadTime = mStartThreadTimestamp;
        final long endThreadTime = SystemClock.currentThreadTimeMillis();
        HandlerThreadFactory.getWriteLogThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                mBlockListener.onBlockEvent(startTime, endTime, startThreadTime, endThreadTime);
            }
        });
    }

    /**
     * 当dispatchMessage前的println触发时，会执行dump的start方法，
     */
    private void startDump() {
        if (null != BlockCanaryInternals.getInstance().stackSampler) {
            BlockCanaryInternals.getInstance().stackSampler.start();
        }

        if (null != BlockCanaryInternals.getInstance().cpuSampler) {
            BlockCanaryInternals.getInstance().cpuSampler.start();
        }
    }

    /**
     * 当dispatchMessage后的println触发时，会执行dump的stop方法。
     */
    private void stopDump() {
        if (null != BlockCanaryInternals.getInstance().stackSampler) {
            BlockCanaryInternals.getInstance().stackSampler.stop();
        }

        if (null != BlockCanaryInternals.getInstance().cpuSampler) {
            BlockCanaryInternals.getInstance().cpuSampler.stop();
        }
    }
}