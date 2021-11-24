package com.wning.demo.test.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AsmTest {
    public static void main(String[] args) {
        try {
            String path = "/Users/freelf/Android/workspace/WingDemo/app/src/test/java/com/wning/demo/test/asm/";
            String outPath = "/Users/freelf/Android/workspace/WingDemo/app/src/test/java2/com/wning/demo/test/asm/";
            /**
             * 1、准备待分析的class
             */
            FileInputStream fis = new FileInputStream(path + "Main.class");
            /**
             * 2、执行分析与插桩
             */
            //class字节码的读取与分析引擎
            ClassReader cr = new ClassReader(fis);
            // 写出器 COMPUTE_FRAMES 自动计算所有的内容，后续操作更简单
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            //分析，处理结果写入cw EXPAND_FRAMES：栈图以扩展格式进行访问
            cr.accept(new ClassAdapterVisitor(cw), ClassReader.EXPAND_FRAMES);
            /**
             * 3、获得结果并输出
             */
            byte[] newClassBytes = cw.toByteArray();
            File file = new File(outPath);
            file.mkdirs();
            FileOutputStream fos = new FileOutputStream(outPath + "Main.class");
            fos.write(newClassBytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}