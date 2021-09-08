package file

def file = new File("../../HelloGroovy.iml")
//方法在ResourceGroovyMethods中
/**
 * eachline每行输出
 */
file.eachLine {
    //println(it)
}

/**
 * getText()直接获取text
 */
def text = file.getText()
//println(text)
/**
 * readLines()直接读取所有的行
 */
List<String> lines = file.readLines()
//println(lines)
/**
 * withReader 通过reader 读取文件内容
 */
file.withReader { BufferedReader reader ->
    char[] buffer = new char[100]
    reader.read(buffer)
    //println(buffer)
}
/**
 * 通过withReader withWriter实现读写文件 (groovy处理了流关闭，不用再在finally里自己关闭)
 * @param srcPath
 * @param destPath
 * @return
 */
def copy(String srcPath , String destPath) {
    try {
        def destFile = new File(destPath)
        if (!destFile.exists()) {
            destFile.createNewFile()
        }
        new File(srcPath).withReader { BufferedReader reader ->
            //获取所有的行
            def lines = reader.readLines()
            destFile.withWriter { BufferedWriter writer ->
                lines.each { String line ->
                    writer.append(line + "\r\n")
                }
            }
        }
        return true
    } catch (Exception e) {
    }
    return false
}

def result = copy("../../HelloGroovy.iml", "../../HelloGroovy2.iml")
println(result)

