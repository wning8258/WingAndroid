package com.wing.android.lru.manager;

import androidx.fragment.app.FragmentActivity;

/**
 * 管理 RequestManager 构建出 RequestManager
 */
public class RequestManagerRetriever {

    public RequestManager get(FragmentActivity fragmentActivity) { // this == FragmentActivity
        return new RequestManager(fragmentActivity);
    }
}
