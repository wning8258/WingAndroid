package com.wing.android.lru.manager;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

public class Glide {

    private static Glide glide;

    public static Glide getInstance(RequestManagerRetriever retriever) {
        if (glide == null) {
            synchronized (Glide.class) {
                if (glide == null) {
                    glide = new Glide(retriever);
                }
            }
        }
        return glide;
    }

    private final RequestManagerRetriever retriever;

    private Glide(RequestManagerRetriever retriever) {
        this.retriever = retriever;
    }

    public static RequestManager with(FragmentActivity fragmentActivity) {
        return getRetriever(fragmentActivity).get(fragmentActivity);
    }

    // Glide 转变的开始
    private static RequestManagerRetriever getRetriever(Context context) {
        return Glide.get(context).getRetriever();
    }

    // Glide 是 new 出来的 -- > 转变
    private static Glide get(Context context) {
        return new GlideBuilder(context).build();
    }

    // 下面都是具体
    public RequestManagerRetriever getRetriever() {
        return retriever;
    }
}
