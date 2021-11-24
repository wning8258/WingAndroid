package com.guagua.qiqi.gifteffect.animation.algorithm;

import com.guagua.qiqi.gifteffect.AnimationException;

/*************************************************************************************
* Module Name: ParabolicCal</br>
* File Name: <b>ParabolicCal.java</b></br>
* Description: 抛物线方程</br>
* Author: 郁金涛</br>
* 版权 2008-2015，浙江齐聚科技有限公司 </br>
* 所有版权保护
* 这是浙江齐聚科技有限公司 未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司 
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
***************************************************************************************/
public class ParabolicCal extends CaculationModel{

	public float a;
	public float b;
	public float c;
	
	public ParabolicCal(){
		
	}
	
	@Override
	public float caculate(int time) {
		throw new AnimationException("parabolic不能直接调用...");
	}
	
	
	
	
	

}
