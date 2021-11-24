package com.guagua.qiqi.gifteffect.elements.level;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.guagua.qiqi.gifteffect.animation.algorithm.MathCommonAlg;
import com.guagua.qiqi.gifteffect.elements.BitmapOnDrawListener;
import com.guagua.qiqi.gifteffect.elements.BitmapShape;
import com.guagua.qiqi.gifteffect.elements.IScene;
import com.guagua.qiqi.gifteffect.elements.TextElement;
import com.guagua.qiqi.gifteffect.elf.ElfFactory;
import com.guagua.qiqi.gifteffect.util.BitmapUtils;
import com.wing.android.R;

/**
 * Created by jintao on 2015/7/2.
 */
public class LevelOpenGuardScene extends IScene implements BitmapOnDrawListener {
	private static final String TAG = "LevelOpenGuardScene";
	

	private String mMessage;
	private String mName;
	private Path clipPath;// 剪裁path
	// 守护等级
	private int level;

	public LevelOpenGuardScene(Context context, int number, int width, int height) {
		this(context, number, width, height, 1, "", "");
	}

	public LevelOpenGuardScene(Context context, int number, int width, int height, int level, String messgae, String name) {
		super(context, number, width, height);
		this.mName = name;
		this.mMessage = messgae;
		clipPath = new Path();
		this.level = level - 100;
		setmLastTime(9000);
	}

	public LevelOpenGuardScene(Context context, int width, int height) {
		this(context, 50, width, height);
	}

	private void initData() {
		Bitmap bitmap = BitmapUtils.decodeBitmap(mContext, R.drawable.openguard_text_bg);
		final int messageWidth = bitmap.getWidth();
		final int messageHeight = bitmap.getHeight();
		bitmap = BitmapUtils.decodeBitmap(mContext,
				mContext.getResources().getIdentifier("gg_guard_person_" + level, "drawable", mContext.getPackageName()));
		BitmapShape bitmapShape;

		int space = 0;
		final int boyWidth = bitmap.getWidth();
		final int boyHeight = bitmap.getHeight();
		Rect boyrect = new Rect((mWidth >> 1) - boyWidth - space, ((mWidth * 3 ) >> 2) - boyHeight - messageHeight * 2 , (mWidth >> 1) - space, ((mWidth * 3 ) >> 2) - messageHeight * 2);
		BitmapShape boy = new BitmapShape(bitmap, this);
		ElfFactory.endowOpenGuardbg(boy, boyrect.left, boyrect.top, 3000, 7000);
		addShape(boy);

		bitmap = BitmapUtils.decodeBitmap(mContext, R.drawable.princess);
		final int princessWidth = bitmap.getWidth();
		final int princessHeight = bitmap.getHeight();
		Rect princessrect = new Rect((mWidth >> 1) + space, ((mWidth * 3 ) >> 2) - princessHeight - messageHeight * 2, (mWidth >> 1) + space + princessWidth, ((mWidth * 3 ) >> 2) - messageHeight * 2);
		BitmapShape princess = new BitmapShape(bitmap, this);
		ElfFactory.endowOpenGuardbg(princess, princessrect.left, princessrect.top, 3000, 7000);
		addShape(princess);

		bitmap = BitmapUtils.decodeBitmap(mContext, R.drawable.openguard_text_bg);
		Rect messagerect = new Rect((mWidth - messageWidth) >> 1, boyrect.bottom, (mWidth + messageWidth) >> 1, boyrect.bottom + messageHeight);
		BitmapShape message = new BitmapShape(bitmap, this);
		ElfFactory.endowOpenGuardbg(message, messagerect.left, messagerect.top, 3000, 7000);
		addShape(message);

		if (sceneInfo != null) {
			TextElement element = new TextElement(this, messagerect, mMessage, mName);
			ElfFactory.endowOpenGuardbg(element, messagerect.left, messagerect.top, 3000, 7000);
			addShape(element);
		}

		// 初始化红心
		bitmap = BitmapUtils.decodeBitmap(mContext, R.drawable.gg_guard_red_heard);
		for (int i = 0; i < 2; i++) {
			BitmapShape b1 = new BitmapShape(bitmap, this);
			BitmapShape b2 = new BitmapShape(bitmap, this);
			BitmapShape b3 = new BitmapShape(bitmap, this);
			ElfFactory.endowOpenGuardRedHeart(b1, b2, b3, new Rect((mWidth >> 1) - 80, 0, (mWidth >> 1) + 80, ((mWidth * 3 ) >> 2) - princessHeight - messageHeight), 5000 + i * 1800, 1,
					bitmap.getWidth(), bitmap.getHeight());
			addShape(b1);
			addShape(b2);
			addShape(b3);
		}

		for (int i = 0; i < 12; i++) {
			bitmap = BitmapUtils.decodeBitmap(
					mContext,
					mContext.getResources().getIdentifier("level_openguard_flower" + MathCommonAlg.rangeRandom(1, 2), "drawable",
							mContext.getPackageName()));
			bitmapShape = new BitmapShape(bitmap, this);
			ElfFactory.endowOpenGuardFlower(bitmapShape, mHeight, mWidth, messagerect.top - bitmap.getHeight(), 0, 1000);
			addShape(bitmapShape);
		}

		bitmap = null;
	}

	@Override
	protected void onBeforeShow() {
		super.onBeforeShow();
		initData();
	}

	@Override
	protected void onAfterShow() {
		super.onAfterShow();
	}

	@Override
	public boolean onBitmapDraw(Canvas canvas, Matrix matrix, Paint paint, Bitmap bitmap, int timeDifference) {
		canvas.clipPath(clipPath);
		return true;
	}

	@Override
	public String toString() {
		return "LevelOpenGuardScene [sceneInfo=" + sceneInfo + "]";
	}
}
