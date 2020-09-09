package com.wing.android.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class AsmPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        System.out.println("========================");
        System.out.println("hello asm plugin!");
        System.out.println("========================");
    }
}