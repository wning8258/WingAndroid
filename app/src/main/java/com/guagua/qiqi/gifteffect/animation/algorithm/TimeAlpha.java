package com.guagua.qiqi.gifteffect.animation.algorithm;

import android.util.Log;

/**
 * Created by jintao on 2015/7/3.
 */
public class TimeAlpha extends CaculationModel {


    private float value;
    private int totleTime;

    public TimeAlpha(float value, int time) {
        totleTime = time;
        this.value = value;
    }
    
    public static TimeAlpha build(float value, int time){
        return new TimeAlpha(value, time);
    }

    @Override
    public float caculate(int time) {
        value = (float) time / totleTime * 255;
        if(value > 255){
            value = 255;
        }
        return value;
    }

}
