package com.wing.buildsrc.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

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
    }
}