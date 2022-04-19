package com.wing.android.asm;

public class AsmTest {
    @AsmMethod
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Thread.sleep(2000);
        long endTime = System.currentTimeMillis();
        System.out.println("execute time = " + (endTime - startTime) + " ms");
        // L0
        //    LINENUMBER 6 L0
        //    INVOKESTATIC java/lang/System.currentTimeMillis ()J
        //    LSTORE 1

        // L2
        //    LINENUMBER 8 L2
        //    INVOKESTATIC java/lang/System.currentTimeMillis ()J
        //    LSTORE 3

        //  L3
        //    LINENUMBER 9 L3
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

        // L5
        //    LOCALVARIABLE args [Ljava/lang/String; L0 L5 0
        //    LOCALVARIABLE startTime J L1 L5 1
        //    LOCALVARIABLE endTime J L3 L5 3
        //    MAXSTACK = 6
        //    MAXLOCALS = 5

    }
}
