package com.wning.demo.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 
 * <p>Copyright: Copyright (c) 2013</p>
 * 
 * <p>Company: 呱呱视频社区<a href="www.guagua.cn">www.guagua.cn</a></p>
 *
 * @description TODO 弱引用的handler，实现类必须为static，重写handleMessage(T reference, Message msg)方法即可
 *
 *
 * @author 王宁
 * @modify 
 * @version 1.0.0
 */
public  abstract class WeakReferenceHandler<T> extends Handler {
	private WeakReference<T> mReference;

	public WeakReferenceHandler(T reference) {
        super();
		mReference = new WeakReference<T>(reference);
	}
    public WeakReferenceHandler(T reference, Looper looper) {
        super(looper);
        mReference = new WeakReference<T>(reference);
    }

	@Override
	public final void handleMessage(Message msg) {
		if (mReference.get() == null)
			return;
		handleMessage(mReference.get(), msg);
	}

	protected abstract void handleMessage(T reference, Message msg);
}
