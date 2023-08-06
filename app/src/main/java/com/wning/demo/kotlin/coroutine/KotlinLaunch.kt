package com.wning.demo.kotlin.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KotlinLaunch {

}

/**
 * //非阻塞，并行执行，因为 2 的执行时间短，2 先完成，运行在主线程
 * main before
main after
func2
func1
main end
 */
fun main(args: Array<String>) {
        println("main before")

        GlobalScope.launch {
            func1()
            func2()
        }

        println("main after")
        Thread.sleep(2000)
        println("main end")

}


    suspend fun func1() {
        delay(1500)
        println("1协程执行—${Thread.currentThread().name}_id_${Thread.currentThread().id}")
    }

    suspend fun func2() {
        delay(300)
        println( "2协程执行— ${Thread.currentThread().name}_id_${Thread.currentThread().id}")

    }





