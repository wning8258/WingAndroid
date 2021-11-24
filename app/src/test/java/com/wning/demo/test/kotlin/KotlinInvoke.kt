package com.wning.demo.test.kotlin

class KotlinInvoke {

    //lambda会转成functions函数
    var sum = {x:Int, y : Int ->
        println("Computing the sum of $x and $y...")
        x+y
    }
}

fun main() {
    val kotlinInvoke = KotlinInvoke()
    val sum = kotlinInvoke.sum(1, 2)
    println("sum ${sum}")

    //lambda表示调用invoke执行函数
    val sum2 = kotlinInvoke.sum.invoke(2, 3)
    println("sum2 ${sum2}")

}