package com.guagua.qiqi.gifteffect.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;

import com.guagua.qiqi.gifteffect.util.BitmapUtils;

/**
 * Created by yujintao on 15/7/1.
 */
public class NinepatchBitmapShape extends Element {
	private static final String TAG = NinepatchBitmapShape.class.getSimpleName();

	protected Bitmap mShape;
    private Rect mRect;
	private BitmapOnDrawListener drawListener;

	public NinepatchBitmapShape(Bitmap bitmap, IScene iscene, Rect rect) {
		super(iscene, ANIMATION_MODE_MATRIX);
		this.mShape = bitmap;
		this.mRect = rect;
		initAnchor();
	}

	@Override
	protected void draw(Canvas canvas, Matrix matrix, Paint paint, int timeDifference) {
		if (drawListener == null || drawListener.onBitmapDraw(canvas, matrix, paint, mShape, timeDifference)) {
//			canvas.drawBitmap(mShape, matrix, paint);
			NinePatch patch = new NinePatch(mShape, mShape.getNinePatchChunk(), null);  
	        patch.draw(canvas, mRect);
		}
	}

	public void setBitmapOnDrawListener(BitmapOnDrawListener drawListener) {
		this.drawListener = drawListener;
	}

	@Override
	protected float anchorScaleX() {
		return mShape.getWidth() / 2;
	}

	@Override
	protected float anchorScaleY() {
		return mShape.getHeight() / 2;
	}

	@Override
	protected float anchorRotationX() {
		return mShape.getWidth() / 2;
	}

	@Override
	protected float anchorRotationY() {
		return mShape.getHeight() / 2;
	}

	public float getBitmapWidth() {
		return mShape.getWidth();
	}

	public float getBitmapHeight() {
		return mShape.getHeight();
	}

	@Override
	protected void destroy() {
		super.destroy();
		BitmapUtils.destroy(mShape);
		mShape = null;
	}
}
