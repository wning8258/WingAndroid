package object
/**
 * person类的管理器
 */
class PersonManager {
    static Person createPerson(String name,int age) {
        return Person.createPerson(name,age)
    }
}
