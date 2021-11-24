package com.wning.demo.gifteffect;

import java.util.ArrayList;

/*************************************************************************************
* Module Name: 礼物特效 金额 级别 选项的Bean</br>
* File Name: <b>GiftEffectLevelBean.java</b></br>
* Description: 实现主播的视频直播显示、鲜花、礼物、公屏的聊天面板</br>
* Author: 2012030022B</br>
* 版权 2008-2015，浙江齐聚科技有限公司</br>
* 所有版权保护
* 这是浙江齐聚科技有限公司未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
***************************************************************************************/
public class GiftEffectLevelBean extends BaseBean {

	private static final long serialVersionUID = 2513738733313226623L;
	public int coin; //所属金额
	public ArrayList<Integer> numbers = new ArrayList<Integer>();//所属数量

	/*
	 * 有的礼物没有1级特效,最小的特效也从4级(9个礼物)开始
	 * 那么1,2,3级的最少数量都设为9,程序会贪心找最大的级别.
	 * 比如香烟 写入: 0,9,9,33,99
	 * */
	public ArrayList<Integer> levelLimit = new ArrayList<Integer>();//每级别所需最少数量

	public int getLevel(int number) {
		int level = 0;
		for (int i = 0; i < levelLimit.size(); i++) {
			if (levelLimit.get(i) <= number) {
				level = i;
				continue;
			}
		}
		//暂时最高支持到7级
		if (level > 7) {
			level = 7;
		}
		return level;
	}
}
