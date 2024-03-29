package com.wing.android.plugin

import com.wing.buildsrc.plugin.HelloExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AsmPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        System.out.println("========================================================================");
        System.out.println("hello asm plugin! + 0.0.4");
        System.out.println("========================================================================");

        //
        def extension = project.extensions.create("hello", HelloExtension)

        project.afterEvaluate {
            println "Hello ${extension.name}"
        }
    }
}