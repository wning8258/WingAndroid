package com.wning.demo.asm;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<? extends A> list1 = new ArrayList<B>();	// 协变——父类引用指向子类
        list1 = new ArrayList<C>();
//        list1.add(new Object());  // 错误，容器不可写，不能放入任何值（null 除外）
//        list1.add(new B());  // 错误，容器不可写，不能放入任何值（null 除外）
        A a = list1.get(1);	// wor

        List<? super B> list = new ArrayList<A>();	// 逆变——子类引用指向父类
//        list.add(new A());	  // 编译错误，集合中放入的元素类型只能为 B 及 B 子类型
        list.add(new B());    // work
        Object b = list.get(0);  // work 可读，但无类型都是 Object
    }


}


class A {}
class B extends A {}
class C extends A {}