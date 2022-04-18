package com.wning.demo.test.test.kotlin

/**
 * https://blog.csdn.net/u013064109/article/details/78786646
 */
class KotlinLetAlsoApply {
    fun test() {
        val person: Person = Person("zhangsan")
        val name = person.let {
            it.age
            it.country
            println("it.name ${it.name}")
            it.name  //最后一行的值作为返回值
        }

        val name2 =person.run {
            age
            this.country
            println("name2 ${name}")
            name //最后一行的值作为返回值
        }
        /**
         * let和run一样，只是it和this的区别
         * with 需要判空
         */
        if (person == null) return
        with(person) {
            age
            country
            println("name3 ${name}")
            name //最后一行的值作为返回值
        }
        /**
         * also和apply一样，只是it和this的区别
         * 都返回this
         */
        val person2 = person.also {
            it.age
            it.country
            println("it.name4 ${it.name}")
            it.name
        }
        val person3 = person.apply {
            age
            country
            println("name5 ${name}")
            name
        }
    }

}

/**
 * JvmOverloads 用于自动生成重载方法，前提是字段必须有值
 */
data class Person @JvmOverloads constructor(val name:String = "name", val age:Int = 0, val country:String = "China")