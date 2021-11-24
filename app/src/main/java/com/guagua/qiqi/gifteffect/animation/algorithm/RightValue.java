package com.guagua.qiqi.gifteffect.animation.algorithm;


public class RightValue extends CaculationModel {

	private int speed;
	private float value;
	private float deep;

	private RightValue(float value) {
		this.value = value;
		this.deep = MathCommonAlg.rangeRandom(1, 10);
		if(deep <= 8){
			this.speed = MathCommonAlg.rangeRandom(2, 9);
		}
		else{
			this.speed = MathCommonAlg.rangeRandom(10, 15);
		}
	}

	@Override
	public float caculate(int time) {
		this.value = value + (time / m_unit) + speed;
		return value;
	}

	public static CaculationModel build(float value) {
		return new RightValue(value);
	}
}
