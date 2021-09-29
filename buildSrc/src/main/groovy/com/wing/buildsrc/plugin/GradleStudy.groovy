package com.wing.buildsrc.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * 插件必须实现Plugin<Project>
 */
class GradleStudy implements Plugin<Project>{

    /**
     * 插件被引入时要执行的方法
     * @param project 引用当前插件的Project
     */
    @Override
    void apply(Project project) {
        println("this is GradleStudy plugin ,current project is ${project.name}")
        //创建扩展属性
        project.extensions.create("ReleaseInfo",ReleaseInfoExtension)
        //创建task
        Task releaseInfoTask = project.tasks.create("ReleaseInfoTask", ReleaseInfoTask)

        def buildTask = project.tasks.getByName("build")
        if (buildTask != null) {
            /**
             * Task A is `finalizedBy` task B是如果每次执行Task A,Task B 都会在其之后执行.
             */
            //buildTask完执行之后，执行writeTask！！！！！！！
            buildTask.finalizedBy(releaseInfoTask)
        }

    }
}