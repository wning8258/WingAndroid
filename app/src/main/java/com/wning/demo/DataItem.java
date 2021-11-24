package com.wning.demo;

/**
 * Created by Administrator on 2016/7/14.
 */
public class DataItem {

    private String title;

    private Class clazz;

    public DataItem(String title, Class clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    public DataItem() {
    }

    public Class getClazz() {
        return clazz;
    }

    public DataItem setClazz(Class clazz) {
        this.clazz = clazz;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DataItem setTitle(String title) {
        this.title = title;
        return this;
    }
}
