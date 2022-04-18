package com.wning.demo;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class 动态代理 {
    public interface ITest {
        @WingGET("/heiheihei")
        public void add(int a, int b);
    }

    public static void main(String[] args) {


        ITest iTest = (ITest) Proxy.newProxyInstance(ITest.class.getClassLoader(), new Class<?>[]{ITest.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Integer a = (Integer) args[0];
                Integer b = (Integer) args[1];
                System.out.println("方法名：" + method.getName());
                System.out.println("参数：" + a + " , " + b);
                WingGET get = method.getAnnotation(WingGET.class);
                System.out.println("注解：" + get.value());
                return null;
            }
        });
        iTest.add(3, 5);
    }


}

/**
 * @Retention：
 * （1）.SOURCE:在源文件中有效（即源文件保留）编译成class文件将舍弃该注解。
 * （2）.CLASS:在class文件中有效（即class保留） 编译成dex文件将舍弃该注解。
 * （3）.RUNTIME:在运行时有效（即运行时保留） 运行时可见。
 *
 * @Target ：
 * 说明了注解所修饰的对象范围,也就是我们这个注解是用在那个对象上面的：注解可被用于 packages、types（类、接口、枚举、注解类型）
 * 、类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）。
 * 在注解类型的声明中使用了target可更加明晰其修饰的目标。以下属性是多选状态，我们可以定义多个注解作用域，比如：
 * （1）.CONSTRUCTOR:用于描述构造器。
 * （2）.FIELD:用于描述域也就是类属性之类的。
 * （3）.LOCAL_VARIABLE:用于描述局部变量。
 * （4）.METHOD:用于描述方法。
 * （5）.PACKAGE:用于描述包。
 * （6）.PARAMETER:用于描述参数。
 * （7）.TYPE:用于描述类、接口(包括注解类型) 或enum声明。
 *
 * 作者：那个人
 * 链接：https://juejin.cn/post/6844903665875025927
 * 来源：稀土掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
@interface WingGET {

    String value() default "";
}
