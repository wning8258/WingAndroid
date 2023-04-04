package com.wning.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.wning.demo.utils.UIUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by Administrator on 2016/7/21.
 */
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    protected String TAG;

    protected T viewBinding;
    private Class<T> className;

    /**
     * 初始化UI
     */
    protected void initUI() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=getClass().getSimpleName();
        UIUtils.setCustomDensity(this,getApplication());
        try {
            //getGenericSuperclass()获得带有泛型的父类
            //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
            Type superClass = getClass().getGenericSuperclass();
            /**
             * 以main activity为例，
             * superClass :com.wning.com.wning.demo.BaseActivity<com.wning.demo.databinding.ActivityMain2Binding>
             */
            Log.i("BaseActivity", "superClass :" + superClass);

            /**
             * ParameterizedType是Type的子接口，表示一个有参数的类型，
             * 例如Collection<T>，Map<K,V>等。但实现上 ParameterizedType并不直接表示Collection<T>和Map<K,V>等，
             * 而是表示 Collection<String>和Map<String,String>等这种具体的类型
             *
             *getActualTypeArguments获取参数化类型的数组，泛型可能有多个
             *
             */
            className = (Class<T>) ((ParameterizedType) superClass).getActualTypeArguments()[0];
            /**
             * 以main activity为例，
             * getActualTypeArguments 0 className :class com.wning.demo.databinding.ActivityMain2Binding
             */
            Log.i("BaseActivity", "getActualTypeArguments 0 className :" + className);

            Method method = className.getDeclaredMethod("inflate", LayoutInflater.class);
            viewBinding = (T) method.invoke(null, getLayoutInflater());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(viewBinding.getRoot());

    }
}
