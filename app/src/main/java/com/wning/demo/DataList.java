package com.wning.demo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class DataList {

    private String title;

    private List<DataItem> list;

    public DataList(List<DataItem> list, String title) {
        this.list = list;
        this.title = title;
    }

    public DataList() {
    }

    public List<DataItem> getList() {
        return list;
    }

    public DataList setList(List<DataItem> list) {
        this.list = list;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DataList setTitle(String title) {
        this.title = title;
        return this;
    }
}
