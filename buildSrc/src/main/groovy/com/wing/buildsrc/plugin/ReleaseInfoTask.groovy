package com.wing.buildsrc.plugin

import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 自定义task,实现维护版本信息功能
 */
class ReleaseInfoTask extends DefaultTask {
    ReleaseInfoTask() {
        group = "wning"
        description = "updte the relase info"
    }
    /**
     * 被TaskAction注释的方法，会在执行阶段执行
     */
    @TaskAction
    void doAction() {
        updateInfo()
    }
    //真正的把Extension类中的信息，写入指定文件中
    private void updateInfo() {
        //获取将要写入的信息
        String versionCodeMsg = project.extensions.ReleaseInfo.versionCode
        String versionNameMsg = project.extensions.ReleaseInfo.versionName
        String versionInfoMsg = project.extensions.ReleaseInfo.versionInfo
        String fileName = project.extensions.ReleaseInfo.fileName

        def file = project.file(fileName)

        if (!file.exists()) {
            file.createNewFile()
        }

        def sw = new StringWriter()
        def xmlBuilder = new MarkupBuilder(sw)
        if (file.text != null && file.text.size() <= 0) {
            xmlBuilder.releases {
                release {
                    versionCode(versionCodeMsg)
                    versionName(versionNameMsg)
                    versionInfo(versionInfoMsg)
                }
            }
            file.withWriter { BufferedWriter writer ->
                writer.append(sw.toString())

            }
        } else {
            //已有根节点
            xmlBuilder.release {
                versionCode(versionCodeMsg)
                versionName(versionNameMsg)
                versionInfo(versionInfoMsg)
            }
            //将生成的xml数据插入到根节点之前
            def lines = file.readLines()
            def length = lines.size() - 1
            file.withWriter { writer ->
                lines.eachWithIndex { String line, int index ->
                    if (index != length) {
                        writer.append(line + "\r\n")
                    } else if (index == length ) {
                        writer.append('\t\n' + sw.toString() + '\n')
                        writer.append(line + "\r\n")

                    }
                }

            }
        }
        println "ReleaseInfoTask execute finish"

    }

}