package com.wning.demo.utils

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson

/**
 * Copyright (c) 2014-2023 Zuoyebang, All rights reserved.
 *
 * @fileoverview 依赖注入器。负责创建和存储业务类的依赖项，然后按需提供这些依赖
 * @author huangjinpeng | huangjinpeng@zuoyebang.com
 * @version: 1.0.0 | 2023-06-02 | huangjinpeng  // 初始版本。
 *
 * @description // 附加说明
 *  1) google对于依赖注入的最佳做法：https://developer.android.com/training/dependency-injection#di-alternatives
 */
@SuppressLint("StaticFieldLeak")
object ServiceLocator {

    /**
     * app上下文
     */
    lateinit var appContext: Context

    /**
     * Gson解析器
     */
    val gson by lazy { Gson() }


}

val AppContext: Context inline get() = ServiceLocator.appContext
