package com.wning.demo.test.kotlin

class KotlinStatic {

    companion object {
        val TAG_VIEW = "view"  //KotlinStatic.Companion.getTAG_VIEW()
        val TAG_ANIM = "anim"
        val TAG_NETWORK = "network"
        val TAG_ARCHITECTURE = "architecture"
        const val TAG_IPC = "ipc"  //相当于java中的常量  KotlinStatic.TAG_IPC

        //真正的静态方法  KotlinStatic.staticMethod();
        @JvmStatic
        fun staticMethod() {
            println("this is static method")
        }
        //通过companion调用   KotlinStatic.Companion.method();
        fun method() {
            println("this is static method")
        }
    }
}

class A{

}