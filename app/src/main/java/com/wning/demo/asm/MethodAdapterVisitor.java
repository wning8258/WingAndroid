package com.wning.demo.asm;

import com.wning.demo.asm.TestAnnotation;

import org.objectweb.asm.MethodVisitor;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;
/**
 * AdviceAdapter: 子类
 * 对methodVisitor进行了扩展， 能让我们更加轻松的进行方法分析
 */
public  class  MethodAdapterVisitor  extends  AdviceAdapter {
    private  boolean inject;
    protected  MethodAdapterVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
    }

    /**
     * 自己处理的，只处理带有AsmTestAnnotation注释的方法
     * @param descriptor
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (Type.getDescriptor(TestAnnotation.class).equals(descriptor)) {
            System.out.println(descriptor);
            inject = true;
        }
        return  super.visitAnnotation(descriptor, visible);
    }

    private  int start;
    @Override
    protected  void onMethodEnter() {
        super.onMethodEnter();
        if (inject) {
            //    INVOKESTATIC java/lang/System.currentTimeMillis ()J
            //    LSTORE 1
            //执行完了怎么办？记录到本地变量中
            invokeStatic(Type.getType("Ljava/lang/System;"),
                    new  Method("currentTimeMillis", "()J"));
            start = newLocal(Type.LONG_TYPE);
            //创建本地 LONG类型变量
            //记录 方法执行结果给创建的本地变量
            storeLocal(start);
        }

    }
    @Override
    protected  void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        if (inject){
            //    INVOKESTATIC java/lang/System.currentTimeMillis ()J
            //    LSTORE 3
            invokeStatic(Type.getType("Ljava/lang/System;"),
                    new  Method("currentTimeMillis", "()J"));
            int end = newLocal(Type.LONG_TYPE);
            storeLocal(end);

            //    GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
            //    NEW java/lang/StringBuilder
            //    DUP
            //    INVOKESPECIAL java/lang/StringBuilder.<init> ()V
            //    LDC "execute time = "
            //    INVOKEVIRTUAL java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    LLOAD 3
            //    LLOAD 1
            //    LSUB
            //    INVOKEVIRTUAL java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
            //    LDC " ms"
            //    INVOKEVIRTUAL java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    INVOKEVIRTUAL java/lang/StringBuilder.toString ()Ljava/lang/String;
            //    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V

            getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType("Ljava/io" +
                    "/PrintStream;"));
            //分配内存 并dup压入栈顶让下面的INVOKESPECIAL 知道执行谁的构造方法创建StringBuilder
            newInstance(Type.getType("Ljava/lang/StringBuilder;"));
            dup();
            invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"),new  Method("<init>","()V"));
            visitLdcInsn("execute:");
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new  Method("append","(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
            //减法
            loadLocal(end);
            loadLocal(start);
            math(SUB,Type.LONG_TYPE);
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new  Method("append","(J)Ljava/lang/StringBuilder;"));
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new  Method("toString","()Ljava/lang/String;"));
            invokeVirtual(Type.getType("Ljava/io/PrintStream;"),new  Method("println","(Ljava/lang/String;)V"));
        }
    }
}

