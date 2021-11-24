package com.wning.demo.test.kotlin
class KotlinTest2 {
    fun aaa() {
        println("111");
    }
}

fun main() {
    KotlinTest2().aaa();
}

class Outer {
    private val bar: Int = 1
    class Nested {
        fun foo() = 2
    }
}

val demo = Outer.Nested().foo() // == 2