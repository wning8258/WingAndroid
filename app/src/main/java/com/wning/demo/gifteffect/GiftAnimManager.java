package com.wning.demo.gifteffect;

import android.content.Context;
import android.view.View;

import com.guagua.modules.utils.LogUtils;
import com.guagua.qiqi.gifteffect.BaseSurfaceView;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/*************************************************************************************
* Module Name: 动画播放管理类
* File Name: GiftAnimManager.java
* Description: 实现播放、停止及动画队列
* Author: 薛文超
* 版权 2008-2015，金华长风信息技术有限公司
* 所有版权保护
* 这是金华长风信息技术有限公司未公开的私有源代码, 本文件及相关内容未经金华长风信息技术有限公司
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
***************************************************************************************/
public class GiftAnimManager {
	private static final String TAG = "GiftAnimPlayer";
	public static final int ePuzzle_V = 50;
	public static final int ePuzzle_X = 100;
	public static final int ePuzzle_XiaoLian = 200;
	public static final int ePuzzle_V520 = 520;
	public static final int ePuzzle_Xin = 1000;
	public static final int ePuzzle_V1314 = 1314;
	public static final int ePuzzle_V3344 = 3344;
	private static GiftAnimManager mInstance;
	private Context context;
	private Queue<GiftAnim> shapeQueue = new LinkedBlockingQueue<GiftAnim>();
	private Queue<GiftAnim> gifQueue = new LinkedBlockingQueue<GiftAnim>();
	private BaseSurfaceView baseSurfaceView;
	private volatile ToastGiftView curToastGiftView;//当前正在播放的GiftView

	private GiftAnimManager(Context context) {
		this.context = context;
	}

	public void setToastGiftContainer(BaseSurfaceView baseSurfaceView) {
		this.baseSurfaceView = baseSurfaceView;
	}

	public static GiftAnimManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new GiftAnimManager(context);
		}
		return mInstance;
	}

	public static void release() {
		mInstance = null;
	}

	/**
	 * 外部调用方法，播放一个动画
	 */
	public void playGift(GiftAnim giftAnim) {
		if (giftAnim.animType == GiftAnim.ANIM_SHAPE) {
			setData(giftAnim, curToastGiftView, shapeQueue);
		}
	}

	public boolean isToastPlaying() {
		if (curToastGiftView == null) {
			return false;
		}
		return curToastGiftView.getIsPlaying();
	}

	private void setData(GiftAnim giftAnim, View view, Queue<GiftAnim> queue) {
		if (!isToastPlaying()) {
			startPlay(giftAnim);
		}
		else {
			queue.add(giftAnim);
		}
	}

	private void startPlay(GiftAnim giftAnim) {
		if (giftAnim.num < 9) {//9个以下不显示特效
			return;
		}

		LogUtils.d(TAG, "startPlay");

		if (giftAnim.animType == GiftAnim.ANIM_GIF) {

		} else if (giftAnim.animType == GiftAnim.ANIM_SHAPE && giftAnim.effectLevel > 0) {
			if (baseSurfaceView == null)
				return;
			if (curToastGiftView == null) {
				curToastGiftView = new ToastGiftView(context, baseSurfaceView);
				curToastGiftView.setOnEndListener(new ToastGiftView.OnToastGiftEndListener() {
					@Override
					public void onEnd() {
						LogUtils.i(TAG, "stopPlay");
						playNext(GiftAnim.ANIM_SHAPE);
					}
				});
			}

			curToastGiftView.play(giftAnim);
		}
	}

	private void playNext(int animType) {
		if (!isToastPlaying() && gifQueue != null && shapeQueue != null) {
			LogUtils.i(TAG, "playNext");

			GiftAnim giftAnim = null;
			if (animType == GiftAnim.ANIM_GIF) {
				giftAnim = gifQueue.poll();
			}
			if (animType == GiftAnim.ANIM_SHAPE) {
				giftAnim = shapeQueue.poll();
			}
			if (giftAnim != null) {
				startPlay(giftAnim);
			}
		}
	}

	/**
	 * activity退出需要调用这个方法
	 */
	public void onDestroy() {
		if (curToastGiftView != null) {
			curToastGiftView.onDestory();
		}
		if (gifQueue != null) {
			gifQueue.clear();
			gifQueue = null;
		}
		if (shapeQueue != null) {
			shapeQueue.clear();
			shapeQueue = null;
		}
		
		//baseSurfaceView调用销毁
	}

	/**
	 * 播放礼物形状动画
	*/
	public void playShapeAnim(String senderName, String receiverName, int num, String unit, String giftSrc, int price) {
		if (giftSrc == null || num < 9) {
			return;
		}
		
		GiftAnim giftAnim = new GiftAnim();
		giftAnim.animType = GiftAnim.ANIM_SHAPE;
		giftAnim.num = num;
		giftAnim.resPath = giftSrc;
		giftAnim.sender = senderName;
		giftAnim.receiver = receiverName;
		giftAnim.unit = unit;
		giftAnim.effectLevel = GiftEffectLevelHelper.getInstance().getLevel(price, num);
		playGift(giftAnim);
	}

	/**
	 * 播放超级礼物
	 */
	public void playGiftSuperAnim(String giftSrc) {
		GiftAnim giftAnim = new GiftAnim();
		giftAnim.animType = GiftAnim.ANIM_GIF;
		giftAnim.resPath = giftSrc;
		playGift(giftAnim);
	}
}
