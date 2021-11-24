package com.guagua.qiqi.gifteffect.elements.level;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;

import com.guagua.qiqi.gifteffect.animation.CaculateCommonHandle;
import com.guagua.qiqi.gifteffect.animation.algorithm.RangeCommon;
import com.guagua.qiqi.gifteffect.animation.algorithm.StaticValue;
import com.guagua.qiqi.gifteffect.elements.BitmapOnDrawListener;
import com.guagua.qiqi.gifteffect.elements.BitmapShape;
import com.guagua.qiqi.gifteffect.elements.CircleShape;
import com.guagua.qiqi.gifteffect.elements.GiftInfoElement;
import com.guagua.qiqi.gifteffect.elements.IScene;
import com.guagua.qiqi.gifteffect.elements.InfoPanel;
import com.guagua.qiqi.gifteffect.elf.ElfFactory;
import com.guagua.qiqi.gifteffect.util.BitmapUtils;
import com.guagua.qiqi.gifteffect.util.PXUtils;
import com.wing.android.R;

public class Level5_2Scene extends IScene implements BitmapOnDrawListener {
	private static final String TAG = "Level5Scene";

	private Path clipPath;//剪裁path
	private Rect rect;//素材范围

	public Level5_2Scene(Context context, int number, int width, int height) {
		super(context, number, width, height);
		clipPath = new Path();
		rect = new Rect();
		setmLastTime(4000);
	}

	public Level5_2Scene(Context context, int width, int height) {
		this(context, 50, width, height);
		clipPath = new Path();
		rect = new Rect();
		setmLastTime(4000);
	}

	private void initData() {
		final int downStarOffset = 500;//下坠星星的offsetTime
		final int downStarDuration = 1000;//下坠星星的time
		final int inforPanelOffset = 0;//downStarOffset + downStarDuration + 2000;
		final int inforPanelDuration= 4000;
		final int showTimeOffset = inforPanelOffset + 100;//横向光开始出现的
		final int rowLightShowTime = 3000;//幸运横向光，向上移动时间
		final int starLightsTimeOffset = inforPanelOffset - 500;//星蕴出现时间的offset
		final int starLightsTime = 3000;//星蕴持续时间time
		final int colLightoffset = downStarOffset;
		final int colLightDuration = 5000;
		final int assembleBitmapHeight = mHeight / 6;
		final int assembleBitmapWidth = mWidth;
		final InfoPanel infoPanel = new InfoPanel(this, assembleBitmapWidth, assembleBitmapHeight);
		CaculateCommonHandle caculateCommonHandle = new CaculateCommonHandle();
		caculateCommonHandle.c_x = StaticValue.build(0);
		caculateCommonHandle.c_y = StaticValue.build(mHeight / 2);//RangeCommon.build(mHeight, mHeight / 2, 300);
		caculateCommonHandle.c_rotation = StaticValue.build(0);
//		caculateCommonHandle.c_scale = StaticValue.build(1);
		caculateCommonHandle.c_scale_y = RangeCommon.build(0, 1, 500);
		caculateCommonHandle.c_scale_x = RangeCommon.build(0, 1, 500);
//		caculateCommonHandle.c_alpha=MulSimpleCal.build(new int[]{300,300,300,300,300},new float[]{100,-50,100,-50,150}, 0,MulEndMode.WITH_END_VALUE);//RangeCommon.build(0, 255,100);
		caculateCommonHandle.c_alpha = RangeCommon.build(0, 255, 1000);
		infoPanel.setCaculateCommonHandle(caculateCommonHandle);
		infoPanel.setStartOffset(inforPanelOffset);
		addShape(infoPanel);
		Rect infoPanelRect = null;
		Rect measureRect = null;
		Path clipPath = null;
		Bitmap bitmap;
		BitmapShape bitmapShape=null;
		
		bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.level_5_bg);
		if (bitmap != null) {
			//修改默认的北京基准点，默认居中
			final int bgWidth = bitmap.getWidth();
			final int bgHeight = bitmap.getHeight();
			infoPanelRect = infoPanel.setInfoPanelRect((assembleBitmapWidth - bgWidth) / 2, (assembleBitmapHeight - bgHeight) / 2, bgWidth
					+ (assembleBitmapWidth - bgWidth) / 2, bgHeight + (assembleBitmapHeight - bgHeight) / 2);
			measureRect = infoPanel.setMeasureRect(infoPanelRect.left , infoPanelRect.top ,
					infoPanelRect.right , infoPanelRect.bottom);
			Path temp = new Path();
			temp.addRoundRect(new RectF(measureRect),
					new float[] { PXUtils.dp2px(mContext, 12), PXUtils.dp2px(mContext, 14), PXUtils.dp2px(mContext, 12), PXUtils.dp2px(mContext, 12),
							PXUtils.dp2px(mContext, 40), PXUtils.dp2px(mContext, 40), PXUtils.dp2px(mContext, 36), PXUtils.dp2px(mContext, 28) },
					Direction.CW);
			clipPath = infoPanel.setClipPath(temp);

			
			//初始化背景数据
			BitmapShape bg = new BitmapShape(bitmap, this);
			ElfFactory.endowBackgroup(bg, 1f, infoPanelRect.left, infoPanelRect.top);
			
			//初始化背景里渐变的
			bitmap = BitmapUtils.decodeBitmap(mContext, R.drawable.level_5_bg_front);
			if (bitmap != null) {
				bitmapShape = new BitmapShape(bitmap, this);
				ElfFactory.endowBackgroupBack(bitmapShape, (assembleBitmapWidth - bitmap.getWidth()) / 2,
						(assembleBitmapHeight - bitmap.getHeight()) / 2, -.1f,0f);
				infoPanel.addBitmapElement(bitmapShape,true);
			}
			infoPanel.addInnerElement(bg);
			//初始化 剪裁区
//			initClipPathAndRect();
		}

//		初始化， 背景上面的随机的圆
		Shader shader=new RadialGradient(10,10,10,Color.BLUE,Color.RED,TileMode.CLAMP);
		for (int i = 0; i < 20; i++) {
			CircleShape circleShape = new CircleShape(this, 4);
			circleShape.setColor(Color.WHITE);
			ElfFactory.endowLevel5BubbleUp(circleShape,measureRect, mContext);
			infoPanel.addInnerElement(circleShape);
		}
//
		bitmap = BitmapUtils.decodeBitmap(mContext, R.drawable.level_5_star);
		if (bitmap != null) {
			Rect star = BitmapUtils.correctBitmapRect(bitmap, measureRect, 0.8f);
			for (int i = 0; i < 6; i++) {
				bitmapShape = new BitmapShape(bitmap, this);
				ElfFactory.endowAnyWhereAlpha2(bitmapShape, 0.8f, 4, star, 1);
				infoPanel.addInnerElement(bitmapShape);
			}
		}
		bitmap = BitmapUtils.decodeBitmap(mContext, R.drawable.level_5_yellow_line);
		if (bitmap != null) {
			bitmapShape = new BitmapShape(bitmap, this);
			ElfFactory.endowLevelCommonLine(bitmapShape, measureRect.left, measureRect.top - PXUtils.dp2px(mContext, 6), measureRect);
			infoPanel.addBitmapElement(bitmapShape,true);
		}
//
//		//初始化灯光，因为三个中心点一样，所以看成一个整体
//		//这里需要调整left和right的位置。
//
		Bitmap light = BitmapUtils.decodeBitmap(mContext, R.drawable.level_5_light);
		Bitmap lightBottom = BitmapUtils.decodeBitmap(mContext, R.drawable.level_5_light_bottom);
		Bitmap star = BitmapUtils.decodeBitmap(mContext, R.drawable.level_5_star);
		Bitmap starYellow = BitmapUtils.decodeBitmap(mContext, R.drawable.level_5_yellow_start);
		if (light != null && lightBottom != null && star != null) {
			for (int i = 0; i < 9; i++) {
				BitmapShape lightShape = new BitmapShape(light, this) {
					@Override
					protected float anchorRotationY() {
						return 0;
					}
				};
				BitmapShape lightBottomShape = new BitmapShape(lightBottom, this);
				BitmapShape star1Shape = new BitmapShape(star, this);
				BitmapShape star2Shape;
				star2Shape = new BitmapShape(starYellow, this);
				ElfFactory.endowLevel5Light(lightShape, lightBottomShape, star1Shape, star2Shape, measureRect, i, mContext);
				infoPanel.addInnerElement(lightShape);
				infoPanel.addInnerElement(lightBottomShape);
				infoPanel.addInnerElement(star1Shape);
				infoPanel.addInnerElement(star2Shape);
			}
		}
		bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.level_5_s);
		if (bitmap != null) {
			for (int i = 0; i < 5; i++) {
				bitmapShape = new BitmapShape(bitmap, this);
				ElfFactory.endowLevelStar(bitmapShape, measureRect.left + PXUtils.dp2px(mContext, 12 + i * 10), measureRect.top - PXUtils.dp2px(mContext, 3),
						mContext);
				infoPanel.addInnerElement(bitmapShape);
			}
		}
		if (sceneInfo != null) {
			GiftInfoElement element = new GiftInfoElement(this, sceneInfo,measureRect);
			infoPanel.addInnerElement(element);
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
		return "Level5Scene [sceneInfo=" + sceneInfo + "]";
	}
}
