package com.wning.demo.test.kotlin

class KotlinBlock {
    /**
     * //语法格式
    块名:(参数:参数类型) -> 返回值类型
     */
    fun test1(block1: () -> Unit) {
        block1()
    }

    fun test2(block: () -> String) {
        val result = block()
        println(result)
    }

    fun test3(block: (x: Int, y: Int) -> Int) {
        val result = block(1, 2)
        println(result)
    }
}

fun main() {
    var kotlinBlock = KotlinBlock()
    kotlinBlock.test1 {
        println("block aaa")
        println("block bbb")
    }

    kotlinBlock.test2 {
        println("block2")
        "block2 return" //最后一行是返回值
    }

    kotlinBlock.test3() {
        x,y  -> x + y
    }

}