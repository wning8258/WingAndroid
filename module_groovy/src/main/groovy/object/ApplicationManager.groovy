package object
/**
 * 模拟一个应用的管理类
 */
class ApplicationManager {
    static void init() {
        //全局扩展方法
        ExpandoMetaClass.enableGlobally()
        //为第三方类添加方法
        Person.metaClass.static.createPerson = {
            String name,int age ->
                new Person(name:name,age:age)
        }
    }
}
