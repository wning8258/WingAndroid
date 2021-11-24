package com.wning.demo.architecture;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.wing.android.BR;

public class UserBean extends BaseObservable {
    private String name; //姓名
    private int age; //年龄

    public UserBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    @Bindable
    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }
}
