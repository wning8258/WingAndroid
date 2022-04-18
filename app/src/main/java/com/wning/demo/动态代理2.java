package com.wning.demo.test.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * https://www.jianshu.com/p/381256a35a90
 * https://www.jianshu.com/p/96516db00409
 */
public class 动态代理2 {
    public static void main(String[] args) {
        //要代理的真实对象
        People people = new Teacher();
        //代理对象的调用处理程序，我们将要代理的真实对象传入代理对象的调用处理的构造函数中，最终代理对象的调用处理程序会调用真实对象的方法
        InvocationHandler handler = new WorkHandler(people);
        /**
         * 通过Proxy类的newProxyInstance方法创建代理对象，我们来看下方法中的参数
         * 第一个参数：people.getClass().getClassLoader()，使用handler对象的classloader对象来加载我们的代理对象
         * 第二个参数：people.getClass().getInterfaces()，这里为代理类提供的接口是真实对象实现的接口，这样代理对象就能像真实对象一样调用接口中的所有方法
         * 第三个参数：handler，我们将代理对象关联到上面的InvocationHandler对象上
         */
        People proxy = (People) Proxy.newProxyInstance(people.getClass().getClassLoader(), people.getClass().getInterfaces(), handler);
        //System.out.println(proxy.toString());
        System.out.println(proxy.work());
    }
}

interface People {
    public String work();
}

class Teacher implements People {
    String name = "aaaaaaa";
    Teacher mTeacher;

    Teacher getTeacher() {
        if (mTeacher == null)
            mTeacher = new Teacher();
        return mTeacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String work() {
        System.out.println(name + "老师教书育人...");
        return "教书的是" + name + "老师";
    }
}


abstract class MethodInvokeDelegate {

    public Object beforeInvoke(Object target, Method method, Object[] args) {
        return null;
    }

    public Object afterInvoke(Object target, Method method, Object[] args, Object beforeInvoke, Object invokeResult) {
        if (beforeInvoke != null) {
            return beforeInvoke;
        }
        return invokeResult;
    }

}

class WorkHandler extends MethodInvokeDelegate implements InvocationHandler {

    //代理类中的真实对象
    private Object obj;

    @Override
    public Object beforeInvoke(Object target, Method method, Object[] args) {
        System.out.println("执行了beforeInvoke.......");
        return super.beforeInvoke(target, method, args);
    }

    @Override
    public Object afterInvoke(Object target, Method method, Object[] args, Object beforeInvoke, Object invokeResult) {
        System.out.println("执行了AfterInvoke.......");
        return super.afterInvoke(target, method, args, beforeInvoke, invokeResult);
    }

    //构造函数，给我们的真实对象赋值
    public WorkHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object before = beforeInvoke(obj, method, args);
        //在真实的对象执行之前我们可以添加自己的操作
        System.out.println("before invoke。。。");
        Object invoke = method.invoke(obj, args);
        //在真实的对象执行之后我们可以添加自己的操作
        System.out.println("after invoke。。。");
        return afterInvoke(obj, method, args, before, "哈哈哈");
    }

}


