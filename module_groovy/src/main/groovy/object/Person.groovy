package object
/**
 * groovy默认都是public类型，并且实现了GroovyObject接口
 * public class Person implements GroovyObject {
 */
class Person implements Action, Serializable{
    String name
    int age
    //使用def定义方法
    def increaseAge(int years) {
        this.age += years
    }

    @Override
    void eat() {

    }

    @Override
    void drink() {

    }

    @Override
    void play() {

    }

    def methodMissing(String name, Object args) {
        "the methodMessing is ${name},the args is ${args}"
    }


    /**
     * 一个方法找不到时，会调用它代替
     * @param name
     * @param args
     * @return
     */
    @Override
    Object invokeMethod(String name, Object args) {
        "the invokeMethod is ${name},the args is ${args}"
    }
}
