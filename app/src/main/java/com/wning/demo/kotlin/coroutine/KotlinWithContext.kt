package com.wning.demo.kotlin.coroutine

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KotlinWithContext {

}

/**阻塞，顺序执行，第二个协程可以取得第一个协程的结果作为参数，运行在io 线程
main before
main after
1协程执行—DefaultDispatcher-worker-2_id_16
2协程执行— DefaultDispatcher-worker-1_id_15
两个方法返回值的和：4
main end
 */
fun main(args: Array<String>) {
    println("main before")
    GlobalScope.launch {
        //withcontext 是串行执行的
        val one = func1WithContext()
        val two = func2WithContext(one)
        val sum = one+two
        println("两个方法返回值的和：${sum}")
    }

    println("main after")
    Thread.sleep(1500)
    println("main end")

}



suspend fun func1WithContext():Int = withContext(Dispatchers.IO) {
    delay(1000)
    println("1协程执行—${Thread.currentThread().name}_id_${Thread.currentThread().id}")

    1
}

suspend fun func2WithContext(one: Int):Int = withContext(Dispatchers.IO) {
    delay(300)
    println( "2协程执行— ${Thread.currentThread().name}_id_${Thread.currentThread().id}")
    2+one
}



suspend fun one(): Int {
    println("one")

    delay(1000)
    return 1
}

suspend fun two(int: Int): Int {
    println("two")

    delay(500)
    return 2 + int
}


