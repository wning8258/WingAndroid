package com.wning.demo.kotlin

class KotlinFun {
    val aaa = fun(x: Int): Boolean {
        return true
    }



    fun bar(p0:String,p1:Long):Any{ return 1}

    fun fo(){}
    fun foo(){}
    fun foo(p0:Int):String{return "1"}

    //函数引用
    var f1=::fo
    var f2:()->Unit = ::foo
    var f3:(Int)->String = ::foo

//    var fun1:Function<Int>=::fo
//    var fun2 :Function1<Int,String>={
//        return "123"
//    }

}




fun main() {
    val kotlinFun = KotlinFun()
    kotlinFun.aaa(1)
}