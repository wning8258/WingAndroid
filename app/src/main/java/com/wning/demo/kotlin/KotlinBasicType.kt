package com.wning.demo.kotlin

class KotlinBasicType {


}

fun main() {
    val a = 1
    val b = "123"
    val c: Float = 1F
    val d: Double = 1.0

    val e = "abc"
    val f = String("abc".toCharArray())
    println(e==f) //比较内容 true
    println(e===f)  //比较引用  false

    val c0 = intArrayOf(1,2,3,4,5)
    val c1= IntArray(5){it+1}  //it的值wei数组下标，表示值为下标+1

    var list1= arrayListOf(1,2,3)
    var list2 = ArrayList<String>()
    list2.add("1")
    list2.add("2")
    list2.add("3")

    var list = listOf("1",2,3)
    val numbers = mutableListOf<Int>(1,2,3,4)
    fun test() {
        numbers.forEachIndexed { index, i ->
            println("$ index,$ i")
        }
    }

}