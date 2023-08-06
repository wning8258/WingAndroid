package com.wning.demo.kotlin

class ExtendTest(var name111: String) {


}


fun ExtendTest.printMsg() {
        println("打印 $name111")
}


fun main() {
        ExtendTest("小明").printMsg()
}