package file

import object.Person

/**
 * 保存object到文件 withObjectOutputStream
 * @param object
 * @param destPath
 * @return
 */
def saveObject (Object object, String destPath) {
    try {
        //首先创建目标文件
        def destFile = new File(destPath)
        if (!destFile.exists()) {
            destFile.createNewFile()
        }
        //写对象到文件
        destFile.withObjectOutputStream { ObjectOutputStream out ->
            out.writeObject(object)
        }
        return true
    } catch (Exception e) {
        e.printStackTrace()
    }
    return false
}
/**
 * 从文件中读取object  withObjectInputStream
 * @param path
 * @return
 */
def readObject (String path) {
    def object = null
    try{
        def file = new File (path)
        if (file == null || !file.exists()) return null
        println("file not null")
        //从文件中读取对象
        file.withObjectInputStream { ObjectInputStream input ->
            object = input.readObject()
        }
    }catch (Exception e) {
        e.printStackTrace()
3    }
    return object
}


def person = new Person(name :'zhangsan', age : 26)
saveObject(person, "../../person.bin")
def object = (Person)readObject("../../person.bin")
println("read the person name is ${object.name}, age is ${object.age}")


def file = new File("../../HelloGroovy.iml")
file.newObjectInputStream();//这种必须要自己关闭流！！！！！！！！

