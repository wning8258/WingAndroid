package object

class Main {
    static void main(String[] args) {
        println("应用程序正在启动")
        //初始化
        ApplicationManager.init()
        println("应用程序初始化完成")

        def person = PersonManager.createPerson("zhangsan", 6)
        println("the person name is ${person.name}, the age is ${person.age}")

    }
}
