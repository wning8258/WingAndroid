package com.wning.demo.test.test.kotlin

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.wing.android.R

/**
 * Kotlin的构造函数分为主构造器（primary constructor）和次级构造器（secondary constructor）
 * 一、 Primary Constructor
    1. 写法一：
        class 类名 constructor(形参1, 形参2, 形参3){}

        class KotlinConstructor (username : String, age :Int){}

        - 关键字constructor：在Java中，构造方法名须和类名相同；而在Kotlin中，是通过constructor关键字来标明的，
        且对于Primary Constructor而言，它的位置是在类的首部（class header）而不是在类体中（class body）。
        - 关键字init：init{}它被称作是初始化代码块（Initializer Block），它的作用是为了Primary Constructor服务的，
        由于Primary Constructor是放置在类的首部，是不能包含任何初始化执行语句的，这是语法规定的，那么这个时候就有了init的用武之地，
        我们可以把初始化执行语句放置在此处，为属性进行赋值。
    2. 写法二（演变一）：
        a. 当constructor关键字没有注解和可见性修饰符作用于它时，constructor关键字可以省略（当然，如果有这些修饰时，是不能够省略的，
        并且constructor关键字位于修饰符后面）。那么上面的代码就变成：
            class KotlinConstructor2 (username : String, age :Int){}
        b. 初始化执行语句不是必须放置在init块中，我们可以在定义属性时直接将主构造器中的形参赋值给它。
            class KotlinConstructor3 (username : String, age :Int){
                private val username: String = username
                private val age: Int = age
            }
    3. 写法三（演变二）：
        这种在构造器中声明形参，然后在属性定义进行赋值，这个过程实际上很繁琐，有没有更加简便的方法呢？
        当然有，我们可以直接在Primary Constructor中定义类的属性。
        class KotlinConstructor4(private val username : String, private val age : Int){
        }
        如果类不包含其他操作函数，那么连花括号也可以省略
        class KotlinConstructor5(private val username : String, private val age : Int)
    4.    当我们定义一个类时，我们如果没有为其显式提供Primary Constructor，Kotlin编译器会默认为其生成一个无参主构造


二、 Secondary Constructor
    class User{
        private val username: String
        private var age: Int

        constructor(username: String, age: Int){
        this.username = username
        this.age = age
        }
    }
    和Primary Constructor相比，很明显的一点，Secondary Constructor是定义在类体中。
    第二，Secondary Constructor可以有多个，而Primary Constructor只会有一个。
    2. 要想实现属性的初始化，实际上主构造器已经能够应付多数情况了，为什么还需要次级构造器？
    主要原因是因为我们有时候是需要去继承框架中的类。如在Android中你自定义一个Button：
    class MyButton : AppCompatButton {
        constructor(context: Context) : this(context, null)
        constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.buttonStyle)
        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :super(context, attrs, defStyleAttr)
    }
    在这种情况下，你如果需要重写AppCompatButton的多个构造器，那么，就需要用到Secondary Constructor。同样，这里也有几点需要注意：
        可以看到，我们可以使用this关键字来调用自己的其他构造器，并且需要注意它的语法形式，次级构造器: this(参数列表)
        可以使用super关键字来调用父类构造器，当然这块内容我们放到继承那块再来介绍。


 */
class KotlinConstructor constructor(username1 : String, age :Int){
    val username: String
    val age: Int

    init{
        this.username = username1
        this.age = age
    }
    //jave code(变量为final的，不加init代码块会报错，会在构造函数时赋值)
   /* public KotlinConstructor(@NotNull String username, int age) {
        Intrinsics.checkParameterIsNotNull(username, "username");
        super();
        this.username = username;
        this.age = age;
    }*/
}
/**
 * 当constructor关键字没有注解和可见性修饰符作用于它时，constructor关键字可以省略（当然，如果有这些修饰时，是不能够省略的
 */
class KotlinConstructor2 (username : String, age :Int){
    private val username: String
    private val age: Int

    init{
        this.username = username
        this.age = age
    }
}

/**
 * 如果有可见性修饰符 修饰constructor，constructor不能省略
 */
class KotlinConstructor2_1 private constructor(username : String, age :Int){
    private val username: String
    private val age: Int

    init{
        this.username = username
        this.age = age
    }
}

class KotlinConstructor3 (username : String, age :Int){
    private val username: String = username
    private val age: Int = age
}


/**
 * 4.    当我们定义一个类时，我们如果没有为其显式提供Primary Constructor，Kotlin编译器会默认为其生成一个无参主构造
 */
class KotlinConstructor6(val username : String = "username111", age : Int) {
    val age = 444
}

/**
 * public final class KotlinConstructor6 {
private final int age;
@NotNull
private final String username;

public final int getAge() {
return this.age;
}

@NotNull
public final String getUsername() {
return this.username;
}

public KotlinConstructor6(@NotNull String username, int age) {
Intrinsics.checkNotNullParameter(username, "username");
super();
this.username = username;
this.age = 444;
}
 */
class User{
    private val username: String
    private var age: Int

    constructor(username: String, age: Int){
        this.username = username
        this.age = age
    }
}

class MyButton : AppCompatButton {
    constructor(context: Context) : this(context, null)  //必须使用:this语法
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.buttonStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :super(context, attrs, defStyleAttr) //必须使用:super语法
}

/**
 * 四个参数的次级构造调用三个参数的次级构造，而三个参数的次级构造又调用了主构造。换句话，
 * 次级构造会直接或者间接调用主构造
 *!!!!当主构造方法和次构造方法同时存在，次构造方法必须调用主构造方法!!!!!

 */
class Student constructor(username: String, age: Int) {
    private val username: String = username
    private var age: Int = age
    private var address: String
    private var isMarried: Boolean
    init {
        this.address = "Beijing"
        this.isMarried = false
    }
    constructor(username: String, age: Int, address: String) :this(username, age) {
        this.address = address
    }
    constructor(username: String, age: Int, address: String, isMarried: Boolean) : this(username, age, address) {

        this.isMarried = isMarried
    }
}

fun main() {
    val kotlinConstructor = KotlinConstructor("aaa1", 11)
    println("kotlinConstructor username :${kotlinConstructor.username}, age :${kotlinConstructor.age}")
    val kotlinConstructor6 = KotlinConstructor6("aaa6", 16)
    println("kotlinConstructor username :${kotlinConstructor6.username}, age :${kotlinConstructor6.age}")

    val kotlinConstructor61 = KotlinConstructor6(age = 15)
    println("kotlinConstructor username :${kotlinConstructor61.username}, age :${kotlinConstructor61.age}")


}