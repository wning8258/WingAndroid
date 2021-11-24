package com.wning.demo.gifteffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.guagua.modules.utils.LogUtils;
import com.guagua.qiqi.gifteffect.BaseSurfaceView;
import com.guagua.qiqi.gifteffect.SceneInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wing.android.R;

public class ToastGiftView extends FrameLayout implements ImageLoadingListener, BaseSurfaceView.OnPlaySceneEndListener {
	private static final String TAG = "ToastGiftView";
	private Handler handler = new Handler();
	private static final float BITMAP_SIZE = 40;//图片显示大小（长宽）
	private GiftAnim giftAnim;
	private ViewGroup viewGroup;
	private Bitmap giftBitmap;
	private boolean isPlaying = false;
	private boolean hasComplete = false;
	private OnToastGiftEndListener onShapeAnimEndListener;
	private OnToastGiftPlayListener onShapeAnimPlayListener;
	private BaseSurfaceView baseSurfaceView;

	//自定义options，关闭内存缓存，不然在小内存手机很容易内存溢出
	private static DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();

	public ToastGiftView(Context context, ViewGroup vg) {
		super(context);
		viewGroup = vg;
	}

	public ToastGiftView(Context context, BaseSurfaceView baseSurfaceView) {
		super(context);
		this.baseSurfaceView = baseSurfaceView;
		this.baseSurfaceView.setPlaySceneEndListener(this);
	}

	public void play(GiftAnim giftAnim) {
		isPlaying = true;
		hasComplete = false;
		this.giftAnim = giftAnim;
		ImageLoader.getInstance().loadImage(giftAnim.resPath, displayImageOptions, this);
	}

	public boolean getIsPlaying() {
		return isPlaying;
	}

	/*************begin 公聊区播放定额礼物动画************/
	private AnimationSet enter_set;
	private TranslateAnimation enter_transanim;
	private AlphaAnimation enter_anim;
	private AlphaAnimation out_anim;
	private LayoutInflater inflater;
	private int lastViewGroupHeight;
	private static int[] numberDrawables = new int[] { R.drawable.gg_number_zero, R.drawable.gg_number_one, R.drawable.gg_number_two,
			R.drawable.gg_number_three,
            R.drawable.gg_number_four, R.drawable.gg_number_five, R.drawable.gg_number_six, R.drawable.gg_number_seven,
			R.drawable.gg_number_eight, R.drawable.gg_number_nine };

	/**
	 * 显示礼物Toast
	 * (赠送者) 送 (接收者) (数量) (礼物单位) (礼物图片)
	 */
	public void playGiftToast() {
		LogUtils.d(TAG, "添加礼物场景：playGiftToast");
		if (giftAnim != null && giftBitmap != null && giftAnim.effectLevel > 0) {
			SceneInfo info = giftAnim.convert();
			info.setGiftBitmap(giftBitmap);
			baseSurfaceView.addScene(info);
			LogUtils.d(TAG, "添加礼物场景：" + info.toString());
		}
		else {
			if (onShapeAnimEndListener != null) {
				isPlaying = false;
				LogUtils.i(TAG, "手动onShapeAnimEndListener called end");
				onShapeAnimEndListener.onEnd();
			}
		}

//		String senderName = limitString(giftAnim.sender, 7);
//		String receiverName = limitString(giftAnim.receiver, 7);
//		int num = giftAnim.num;
//		String unit = giftAnim.unit;
//
//		//Toast giftToast = new Toast(context);
//		if (inflater == null) {
//			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		}
//		final View view = inflater.inflate(R.layout.gg_room_gift_toast, null);
//		TextView senderTv = (TextView) view.findViewById(R.id.tv_gift_sender);
//		TextView receiverTv = (TextView) view.findViewById(R.id.tv_gift_receiver);
//		ImageView imgIv = (ImageView) view.findViewById(R.id.tv_gift_src);
//		View rootView = view.findViewById(R.id.gg_gift_toast_root);
//		
//		/**Toast背景选择**/
//		if (num >= 9 && num <= 98) {
//			rootView.setBackgroundResource(R.drawable.room_gift_toast_bg_blue);
//		}
//		else if (num >= 99 && num <= 1313) {
//			rootView.setBackgroundResource(R.drawable.room_gift_toast_bg_pink);
//		}
//		else {
//			rootView.setBackgroundResource(R.drawable.room_gift_toast_bg_yellow);
//		}
//
//		LinearLayout numLayout = (LinearLayout) view.findViewById(R.id.gg_room_gift_number_container);
//		//********拼装Number
//		boolean addMode = false;
//		int mode = 1000000; //100万数字支持
//		do {
//			int flag = (num / mode);
//			if (flag == 0 && addMode == false) {//等于0 而且是首位
//			}
//			else {//不等于0
//				addMode = true;
//				ImageView iv = new ImageView(getContext());
//				iv.setImageResource(numberDrawables[flag]);
//				numLayout.addView(iv, -2, -2);
//			}
//			num = num % mode;
//			mode = mode / 10;
//		}
//		while (mode > 0);
//
//		//拼装Number结束
//		senderTv.setText(senderName);
//		receiverTv.setText(receiverName);
//		imgIv.setImageBitmap(giftBitmap);
//		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
//		params.gravity = Gravity.CENTER_HORIZONTAL;
//		params.topMargin = 10;
//
//		int viewGroupHeight = viewGroup.getMeasuredHeight();
//		if (enter_set == null) {
//			lastViewGroupHeight = viewGroupHeight;
//			enter_anim = new AlphaAnimation(0, 1);
//			enter_anim.setDuration(1000);
//			enter_transanim = new TranslateAnimation(0, 0, (viewGroupHeight - Utils.dip2px(context, 60)), 0);
//			enter_transanim.setDuration(1000);
//			enter_set = new AnimationSet(true);
//			enter_set.addAnimation(enter_anim);
//			enter_set.addAnimation(enter_transanim);
//		}
//		view.setAnimation(enter_set);
//		view.startAnimation(enter_set);
//		if (viewGroupHeight > lastViewGroupHeight) {
//			//重置进入动画
//			enter_set = null;
//		}
//		viewGroup.addView(view, params);
//		baseSurfaceView.addScene();
//		handler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
////				if (out_anim == null) {
////					out_anim = new AlphaAnimation(1, 0);
////					out_anim.setDuration(500);
////				}
////				view.setAnimation(out_anim);
////				view.startAnimation(out_anim);
////				view.setVisibility(View.GONE);
////				if (viewGroup != null) {
////					viewGroup.removeView(view);
////				}
////				handler.removeCallbacks(this);
//
//			}
//		}, 1500);
//		handler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				isPlaying = false;
//				handler.removeCallbacks(this);
//				if (onShapeAnimEndListener != null) {
//					LogUtils.i("", "onShapeAnimEndListener called end");
//					onShapeAnimEndListener.onEnd();
//				}
//			}
//		}, 2100);
//
//		if (onShapeAnimPlayListener != null) {
//			onShapeAnimPlayListener.onPlay(giftAnim, giftBitmap);
//		}

	}

	/**
	 * 将大于6个字符的换成 limit -1 个字符加...
	 * 如: ABCDEFG => ABCDE... 
	 * @param limit 最大长度
	*/
	private String limitString(String oriStr, int limit) {
		String result = oriStr;
		if (oriStr.length() > limit) {
			result = oriStr.substring(0, limit - 1) + "...";
		}
		return result;
	}

	/*************end 公聊区播放定额礼物动画************/
	@Override
	public void onLoadingStarted(String imageUri, View view) {
		LogUtils.d(TAG, "onLoadingStarted : " + imageUri);
	}

	@Override
	public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
		LogUtils.d(TAG, "load image fail : " + imageUri + "  reason " + failReason.getType().toString());
		isPlaying = false;
	//	String des = "failType" + QiQiLogAgent.QUALITY_MAP_SEPARATER + failReason.getType().toString();
		//QiQiLogAgent.getInstance().onQuality(GiftAnimationLoadErrorEvent.ID, des);
		//加载失败,播放下一个.
		if (onShapeAnimEndListener != null) {
			onShapeAnimEndListener.onEnd();
		}
	}

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		giftBitmap = convertBitmap(loadedImage, BITMAP_SIZE, BITMAP_SIZE);
		LogUtils.d(TAG, "onLoadingComplete " + imageUri);
		playGiftToast();
	}

	@Override
	public void onLoadingCancelled(String imageUri, View view) {
		LogUtils.d(TAG, "load image cancell ," + imageUri);
		isPlaying = false;
	//	String des = "description" + QiQiLogAgent.QUALITY_MAP_SEPARATER + "加载失败cancle";
	//	QiQiLogAgent.getInstance().onQuality(GiftAnimationLoadErrorEvent.ID, des);
		//加载失败,播放下一个.
		if (onShapeAnimEndListener != null) {
			onShapeAnimEndListener.onEnd();
		}
	}

	/**
	 * 图片缩放
	 */
	public Bitmap convertBitmap(Bitmap bitmap, float xSize, float ySize) {
		float scaX = xSize / bitmap.getWidth();
		float scaY = ySize / bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale(scaX, scaY);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}

	/*************播放开始与结束的回调*****************/

	//动画播放开始监听
	public interface OnToastGiftPlayListener {
		public void onPlay(GiftAnim giftAnim, Bitmap giftBmp);
	}

	//动画播放结束的监听接口
	public interface OnToastGiftEndListener {
		public void onEnd();
	}

	/**
	 * 设置动画播放结束的监听
	 * @author: Xue Wenchao
	 * @param onShapeAnimEndListener
	 * @return: void
	 */
	public void setOnEndListener(OnToastGiftEndListener onShapeAnimEndListener) {
		this.onShapeAnimEndListener = onShapeAnimEndListener;
	}

	/**
	 * 设置动画播放开始的监听
	 * @author: gxm
	 * @param listener
	 * @return: void
	 */
	public void setOnPlayListener(OnToastGiftPlayListener listener) {
		this.onShapeAnimPlayListener = listener;
	}

	public void onDestory() {
		if (handler != null) {
			handler.removeCallbacksAndMessages(null);
		}
	}

	@Override
	public void onPlayEnd() {
		isPlaying = false;
		if (onShapeAnimEndListener != null) {
			LogUtils.i("ToastGiftView", "onShapeAnimEndListener called end");
			onShapeAnimEndListener.onEnd();
		}
	}
}
