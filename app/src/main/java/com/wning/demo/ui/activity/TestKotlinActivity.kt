package com.wning.demo.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import com.wing.android.R
import com.wing.android.databinding.ActivityMain2Binding

class TestKotlinActivity : Activity(){

    lateinit var inflate: ActivityMain2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(R.layout.activity_main2)
        clickMethods()
    }

    /**
     * onclick各种简写
     */
    private fun clickMethods() {
        inflate.toolbar.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
            }
        })

        //由于Kotlin会根据上下文进行类型推导，我们可以使用更简化的lambda，来实现更简洁的语法
        inflate.toolbar.setOnClickListener({v:View? ->
            println(v?.javaClass)
        })

        //当lambda表达式作为函数调用的最后一个实参，可以将它放在括号外边
        inflate.toolbar.setOnClickListener(){v:View? ->
            println(v?.javaClass)
        }
        //当lambda是函数唯一的实参时，还可以将函数的空括号去掉：
        inflate.toolbar.setOnClickListener{v:View? ->
            println(v?.javaClass)
        }
        //跟局部变量一样，lambda参数的类型可以被推导处理，可以不显式的指定参数类型：
        inflate.toolbar.setOnClickListener{v ->
            println(v?.javaClass)
        }
        //当lambda表达式中只有一个参数，没有显示指定参数名称，并且这个参数的类型能推导出来时，会生成默认参数名称it
        inflate.toolbar.setOnClickListener{
            println(it.javaClass)
        }
    }
}