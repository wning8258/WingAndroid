package com.wning.demo.test.kotlin

class KotlinBreakReturn {

    companion object {
        @JvmStatic
        fun testLabel() {
            for (i in 1..5) {
                // ……
                println("i :${i}")
                if (i == 3) {
                    break
                }
            }
            /**
             * 在 Kotlin 中任何表达式都可以用标签（label）来标记。
             * 标签的格式为标识符后跟 @ 符号，例如：abc@、fooBar@都是有效的标签（参见语法）。
             * 要为一个表达式加标签，我们只要在其前加标签即可。
             */
            for1@ for (i in 1..5) {
                // ……
                println("i :${i}")
                if (i == 3) {
                    break@for1
                }
            }
        }

        @JvmStatic
        fun testReturn() {
            listOf(1, 2, 3, 4, 5).forEach {
                //输出 1 2
                if (it == 3) return // 非局部直接返回到 foo() 的调用者
                println(it)
            }
            println("this point is unreachable")
        }

        @JvmStatic
        fun testContinue() {
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return@forEach //跳出到forEach(本质相当于continue）
                println(it)
            }
            println(" done with explicit label")
        }

        @JvmStatic
        fun testBreak() {
            run loop@{
                listOf(1, 2, 3, 4, 5).forEach {
                    if (it == 3) return@loop // 从传入 run 的 lambda 表达式非局部返回
                    print(it)
                }
            }
            print(" done with nested loop")
        }
    }


}

fun main() {
    //KotlinBreakReturn.testLabel();
    KotlinBreakReturn.testReturn();
    KotlinBreakReturn.testContinue();
    KotlinBreakReturn.testBreak();
}