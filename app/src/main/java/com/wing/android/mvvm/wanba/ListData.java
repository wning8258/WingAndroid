package com.wing.android.mvvm.wanba;

import java.util.List;

/**
 * Created on 2020/12/23
 * list容器
 *
 * @author daiqicheng
 */
public class ListData<T> {
    public List<T> list;

    public boolean isLoadMore = false;

    public ListData(List<T> list, boolean isLoadMore) {
        this.list = list;
        this.isLoadMore = isLoadMore;
    }
}
