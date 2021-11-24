package com.wning.demo.test.kotlin

class KotlinList {
    val numbers = mutableListOf<Int>(1,2,3,4)
    fun test() {
        numbers.forEachIndexed { index, i ->
            println("$ index,$ i")
        }
    }

}

fun main() {
    var listTest = KotlinList()
    listTest.test()
}