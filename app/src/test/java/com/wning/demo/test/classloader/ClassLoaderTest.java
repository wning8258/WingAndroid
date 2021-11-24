package com.wning.demo.test.classloader;

public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        while(classLoader != null) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
            /**
             * sun.misc.Launcher$AppClassLoader@18b4aac2
             * sun.misc.Launcher$ExtClassLoader@19469ea2
             * (由于Bootstrap ClassLoader是由c/c++编写的，并不是java类，所以不能在java代码中获得引用)
             */
        }
    }
}
