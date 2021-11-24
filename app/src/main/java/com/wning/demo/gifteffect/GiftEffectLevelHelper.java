package com.wning.demo.gifteffect;

import java.util.ArrayList;
import java.util.HashMap;

/*************************************************************************************
* Module Name: 礼物特效等级换算帮助类</br>
* File Name: <b>GiftEffectLevelHelper.java</b></br>
* Description: </br>
* Author: gxm</br>
* 版权 2008-2015，浙江齐聚科技有限公司 </br>
* 所有版权保护
* 这是浙江齐聚科技有限公司 未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司 
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
***************************************************************************************/
public class GiftEffectLevelHelper {

	//金额 和 数量列表的Map
	private HashMap<Integer, GiftEffectLevelBean> coinListMap = new HashMap<Integer, GiftEffectLevelBean>();
//	private ArrayList<Integer> costSection = new ArrayList<Integer>();
	public static GiftEffectLevelHelper instance = null;

	private GiftEffectLevelHelper() {
		init();
	}

	private void init() {

//		costSection = createArrayList(2000, 20000, 33000, 44000, 505000, 660000, 770000);

		GiftEffectLevelBean gift50 = new GiftEffectLevelBean();
		gift50.coin = 50;
		gift50.numbers = createArrayList(-1,1, 33, 99, 288, 521, 1314, 6666, 9999);
		gift50.levelLimit = createArrayList(0, 33, 99, 288, 521, 1314, 6666, 9999);
		coinListMap.put(50, gift50);

		GiftEffectLevelBean gift100 = new GiftEffectLevelBean();
		gift100.coin = 100;
		gift100.numbers = createArrayList(-1,1, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		gift100.levelLimit = createArrayList(0, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		coinListMap.put(100, gift100);

		GiftEffectLevelBean gift300 = new GiftEffectLevelBean();
		gift300.coin = 300;
		gift300.numbers = createArrayList(-1,1, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		gift300.levelLimit = createArrayList(0, 9, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		coinListMap.put(300, gift300);

		GiftEffectLevelBean gift900 = new GiftEffectLevelBean();
		gift900.coin = 900;
		gift900.numbers = createArrayList(-1,1, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		gift900.levelLimit = createArrayList(0, 9, 9, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		coinListMap.put(900, gift900);

		GiftEffectLevelBean gift1880 = new GiftEffectLevelBean();
		gift1880.coin = 1880;
		gift1880.numbers = createArrayList(-1,1, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		gift1880.levelLimit = createArrayList(0, 9, 9, 9, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		coinListMap.put(1880, gift1880);

		GiftEffectLevelBean gift5200 = new GiftEffectLevelBean();
		gift5200.coin = 5200;
		gift5200.numbers = createArrayList(-1,1, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		gift5200.levelLimit = createArrayList(0, 9, 9, 9, 9, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		coinListMap.put(5200, gift5200);

		GiftEffectLevelBean gift13140 = new GiftEffectLevelBean();
		gift13140.coin = 13140;
		gift13140.numbers = createArrayList(-1,1, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		gift13140.levelLimit = createArrayList(0, 9, 9, 9, 9, 9, 9, 33, 99, 288, 521, 1314, 6666, 9999);
		coinListMap.put(13140, gift13140);
	}

	public static GiftEffectLevelHelper getInstance() {
		if (instance == null) {
			instance = new GiftEffectLevelHelper();
		}
		return instance;
	}

	/*
	 *  注意 要添加 其它礼物金额的容错.
	 *  有一步按钱来算的推荐级别
	 * */

	//通过礼物金额和数量返回Level
	public int getLevel(int price, int num) {
		if (coinListMap.containsKey(price)) {
			return coinListMap.get(price).getLevel(num);
		}
		else { //非常规金额
			if (price == 0) { //免费库存, 9个及以上显示1级特效
				if(num>=9){
					return 1;
				}else{
					return 0;
				}
			}

			//return getOtherCoinLevel(price, num);
			//暂时不按钱,按100的区间来
			return coinListMap.get(100).getLevel(num);
		}
	}

	//通过礼物金额返回可选择的礼物列表
	public ArrayList<Integer> getGiftNumberList(int price) {
		if (coinListMap.containsKey(price)) {
			return coinListMap.get(price).numbers;
		}
		else { //非常规金额
				//金额为0的免费礼物区间 - 
			if (price == 0) {
				//返回100元的区间 (10 个)
				return coinListMap.get(100).numbers;
			}
			//其它金额区间,按钱数返推
			//return createOtherCoinNumber(price);
			//暂时不按钱,按100的区间来
			return coinListMap.get(100).numbers;
		}
	}

	//其它金额礼物的数量列表
//	private ArrayList<Integer> createOtherCoinNumber(int price) {
//		ArrayList<Integer> section = new ArrayList<Integer>();
//		section.add(1);
//		for (Integer cost : costSection) {
//			float currentFloat = (cost+0.0f) / price;
//			//余数增1
//			int currentNumber = currentFloat > (int) currentFloat ? (int) currentFloat + 1 : (int) currentFloat;
//			if ((!section.contains(currentNumber)) && currentNumber > 0) {
//				section.add(currentNumber);
//			}
//
//		}
//
//		return section;
//	}

	//其它金额礼物等级判断
//	private int getOtherCoinLevel(int price, int num) {
//		int money = price * num;
//		int level = 0;
//		for (int i = 0; i < costSection.size(); i++) {
//			int curMoney = costSection.get(i);
//			if (curMoney <= money) {
//				level = i + 1;
//			}
//		}
//
//		return level;
//	}

	public static <T> ArrayList<T> createArrayList(T... elements) {
		ArrayList<T> list = new ArrayList<T>();
		for (T element : elements) {
			list.add(element);
		}
		return list;
	}
}
