package com.guagua.qiqi.gifteffect.animation.algorithm;

import android.util.Log;

/**
 * Created by yujintao on 15/7/4. 范围时间内进行增长，速率逐渐变慢或者变快dratio主要是控制衰变的时间长度的曲线 ratioX^index + s;最小值
 */
public class Decay2Stop extends Decay2 {

    private int total;
    private float value;
    public Decay2Stop(float ratio, float index, float startingValue, int total) {
        super(ratio, index, startingValue);
        this.total = total - MathCommonAlg.rangeRandom(0, 200);
    }

    public Decay2Stop(float startingValue) {
        super(startingValue);
    }

    @Override
	public float caculate(int time) {
//	    if(value >= total){
//	        return value;
//	    }
//	    else{
	        value = super.caculate(time);
		    return value;
//	    }
	}
}
