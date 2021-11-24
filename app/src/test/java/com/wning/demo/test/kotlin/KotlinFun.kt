package com.wning.demo.test.kotlin

class KotlinFun {
    val aaa = fun(x: Int): Boolean {
        return true
    }
}

fun main() {
    val kotlinFun = KotlinFun()
    kotlinFun.aaa(1)
}