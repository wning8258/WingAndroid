package com.wing.buildsrc.plugin

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils


class WingTransform extends Transform{


    @Override
    String getName() {
        /**
         * 在gradle的task中的名字 transformClassesWithWingForBaiduDebug
         */
        return "wing"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        //处理哪些文件
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {

        Collection<TransformInput> inputs = transformInvocation.inputs
        TransformOutputProvider outputProvider = transformInvocation.outputProvider


        //遍历inputs
        inputs.each {
            /**
             * Dir: /Users/wning/work/Android/workspace/WingAndroid/app/build/intermediates/javac/BaiduDebug/classes ,
             * dest: /Users/wning/work/Android/workspace/WingAndroid/app/build/intermediates/transforms/wing/Baidu/debug/122
             *
             * Dir: /Users/wning/work/Android/workspace/WingAndroid/app/build/tmp/kotlin-classes/BaiduDebug ,
             * dest: /Users/wning/work/Android/workspace/WingAndroid/app/build/intermediates/transforms/wing/Baidu/debug/123
             */
            //遍历directoryInputs
            it.directoryInputs.each { DirectoryInput directoryInput ->
                //处理directoryInputs
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                println("Dir: ${directoryInput.file} , dest: ${dest}")
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            /**
             * Jar: /Users/wning/.gradle/caches/transforms-3/3b457155deeb0046d83a2cb195e8c328/transformed/jetified-annotations-13.0.jar ,
             * dest: /Users/wning/work/Android/workspace/WingAndroid/app/build/intermediates/transforms/wing/Baidu/debug/114.jar
             */
            //遍历jarInputs
            it.jarInputs.each { JarInput jarInput ->
                //处理jarInputs
                def dest = outputProvider.getContentLocation(jarInput.name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                println("Jar: ${jarInput.file} , dest: ${dest}")

                FileUtils.copyFile(jarInput.file, dest)
            }
        }


//        super.transform(transformInvocation)
    }

}