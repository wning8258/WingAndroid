package com.wning.demo.test.kotlin

class KotlinBase1 {

    fun whenTest(x : Int) : Unit{
        when(x) {
            1 -> {
                println("whenTest x = 1")
            }
            2 -> {
                println("whenTest x = 2")
            }
            else -> { // 注意这个块
                print("whenTest x 不是 1 ，也不是 2")
            }
        }
    }

    fun forTest() {
        for (i in 1..4) print(i) // 输出“1234”
        println()
    }

    fun lambdaTest() {
        val sumLambda: (Int, Int) -> (Int) = {x,y -> x+y}
        val sumLambda2 = {x : Int,y : Int -> x+y}
        println(sumLambda(1,2))  // 输出 3
    }
}



fun main() {
    val base1 = KotlinBase1()
    base1.whenTest(1)
    base1.forTest()
    base1.lambdaTest()
}